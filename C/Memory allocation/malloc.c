#include <errno.h>
#include <pthread.h>
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "malloc.h"
#include "printing.h"



static pthread_mutex_t mutex;
header freelistSentinels[N_LISTS];
header * lastFencePost;
void * base;
header * osChunkList [MAX_OS_CHUNKS];
size_t numOsChunks = 0;
static void init (void) __attribute__ ((constructor));

// Helper functions for manipulating pointers to headers
static inline header * get_header_from_offset(void * ptr, ptrdiff_t off);
static inline header * get_left_header(header * h);
static inline header * ptr_to_header(void * p);

// Helper functions for allocating more memory from the OS
static inline void initialize_fencepost(header * fp, size_t left_size);
static inline void insert_os_chunk(header * hdr);
static inline void insert_fenceposts(void * raw_mem, size_t size);
static header * allocate_chunk(size_t size);

// Helper functions for freeing a block
static inline void deallocate_object(void * p);
static inline void coalescing(header * p);
static inline int find_index(size_t free);


// Helper functions for allocating a block
static inline header * allocate_object(size_t raw_size);
static inline void delete(header * free);
static inline void insert(header * free);
static inline bool check_consecutive_chunk(header * newblock);


// Helper functions for verifying that the data structures are structurally 
// valid
static inline header * detect_cycles();
static inline header * verify_pointers();
static inline bool verify_freelist();
static inline header * verify_chunk(header * chunk);
static inline bool verify_tags();

static void init();

static bool isMallocInitialized;

/**
	return a pointer to a header offset bytes from pointer
 */
static inline header * get_header_from_offset(void * ptr, ptrdiff_t off) {
	return (header *)((char *) ptr + off);
}

/**
 	return header to the right of h
 */
header * get_right_header(header * h) {
	return get_header_from_offset(h, get_size(h));
}

/**
 	return header to the right of h
 */
inline static header * get_left_header(header * h) {
  return get_header_from_offset(h, -h->left_size);
}

/**
 	Fenceposts are marked as always allocated and may need to have
 */
inline static void initialize_fencepost(header * fp, size_t left_size) {
	set_state(fp,FENCEPOST);
	set_size(fp, ALLOC_HEADER_SIZE);
	fp->left_size = left_size;
}

/**
 	Helper function to maintain list of chunks from the OS for debugging
 */
inline static void insert_os_chunk(header * hdr) {
  if (numOsChunks < MAX_OS_CHUNKS) {
    osChunkList[numOsChunks++] = hdr;
  }
}

/**
 	given a chunk of memory insert fenceposts at the left and 
 */
inline static void insert_fenceposts(void * raw_mem, size_t size) {
  // Convert to char * before performing operations
  char * mem = (char *) raw_mem;

  // Insert a fencepost at the left edge of the block
  header * leftFencePost = (header *) mem;
  initialize_fencepost(leftFencePost, ALLOC_HEADER_SIZE);

  // Insert a fencepost at the right edge of the block
  header * rightFencePost = get_header_from_offset(mem, size - ALLOC_HEADER_SIZE);
  initialize_fencepost(rightFencePost, size - 2 * ALLOC_HEADER_SIZE);
}

/**
	Allocate another chunk from the OS and prepare to insert it
 */
static header * allocate_chunk(size_t size) {
  void * mem = sbrk(size);
  
  insert_fenceposts(mem, size);
  header * hdr = (header *) ((char *)mem + ALLOC_HEADER_SIZE);
  set_state(hdr, UNALLOCATED);
  set_size(hdr, size - 2 * ALLOC_HEADER_SIZE);
  hdr->left_size = ALLOC_HEADER_SIZE;
  return hdr;
}

/**
 	Helper allocate an object given a raw request size from the user
 */

static inline void delete(header * free){
	//delete from the freelist
 	free->next->prev = free->prev;
 	free->prev->next = free->next;
}
static inline void insert(header * free){
	//add free to freelist
 	int revised_index;
 	int revised_size = get_size(free)-ALLOC_HEADER_SIZE;
 	for(int i=0; i<N_LISTS; i++){
 		int min = (i*sizeof(size_t))+1;
 		int max = ((i+1)*sizeof(size_t));
 		if(revised_size >= min && revised_size <= max){
 			revised_index = i;
 			break;
 		}else if(revised_size >= (((N_LISTS-1)*sizeof(size_t))+1)){
 			revised_index = N_LISTS-1;
 			break;
 		}
 	}

 	header * freelist = &freelistSentinels[revised_index];
 	free->next = freelist->next;
 	free->prev = freelist;
 	freelist->next->prev = free;
 	freelist->next = free;

}
static inline bool check_consecutive_chunk(header * newblock){
	int index=0;
	header * free = get_left_header(lastFencePost);
	
	if((((char *)free + get_size(free) +ALLOC_HEADER_SIZE) == ((char *)newblock- ALLOC_HEADER_SIZE))){ //when two chunks are consecutive
		return true;
	}else{
		return false;
	}
}
static inline header * allocate_object(size_t raw_size) {
 (void) raw_size;
 if(raw_size == 0){
 	return NULL;
 }
 raw_size = (raw_size+(sizeof(size_t)-1)) & (~(sizeof(size_t)-1));
 
 if(raw_size <= sizeof(size_t)){
 	raw_size = 16;
 }
 int index = raw_size/sizeof(size_t) -1;
 if(index > N_LISTS-1){
 	index = N_LISTS-1;
 }

 //find fit
 header * free = &freelistSentinels[index];
 while(index < N_LISTS){
 	if(free == free->next){
 		index++;
 		free = &freelistSentinels[index];
 	}else if(get_size(free) >= (raw_size+ALLOC_HEADER_SIZE)){
 		break;
 	}else{
 		free = free->next;
 	}
 }
 	//check if no available block in freelist
 	if(index > N_LISTS-1 || free == free->next){
 		header * newblock = allocate_chunk(ARENA_SIZE);
 		if(!check_consecutive_chunk(newblock)){
 			header * prevFencePost = get_header_from_offset(newblock, -ALLOC_HEADER_SIZE);
  			insert_os_chunk(prevFencePost);	
  			insert(newblock);
 		}else{
 			header * oldblock = get_left_header(lastFencePost);
 			if(get_state(oldblock)==UNALLOCATED){
 				int newsize = get_size(newblock);
 				delete(oldblock);
 				newblock = (header *)((char *)oldblock - (2*ALLOC_HEADER_SIZE) + get_size(oldblock));
 				set_state(newblock, UNALLOCATED);
 				set_size(newblock, newsize + get_size(oldblock) + 2*ALLOC_HEADER_SIZE);
 				get_right_header(newblock)->left_size = get_size(newblock);
 				lastFencePost = get_right_header(newblock);
 				insert(newblock);
 			}else{
 				header * oldblock = lastFencePost;
 				int newsize = get_size(newblock);
 				newblock = (header *)((char *)oldblock - (ALLOC_HEADER_SIZE) + get_size(oldblock));
 				set_size(newblock, newsize + 2*ALLOC_HEADER_SIZE);
 				set_state(newblock, UNALLOCATED);
 				get_right_header(newblock)->left_size = get_size(newblock);
 				lastFencePost = get_right_header(newblock);
 				insert(newblock);
 			}
 			
 		}
		return allocate_object(raw_size);
 	}
 		
 	//split a block if I have to
 	header * allocated = (header *)((char *)free + get_size(free) - (raw_size+ALLOC_HEADER_SIZE));
 	if((get_size(free) - raw_size - ALLOC_HEADER_SIZE) >= 32){
 		//check whether the index of splitted block changes
 		if(find_index(get_size(free) - raw_size - ALLOC_HEADER_SIZE) == find_index(get_size(free)-ALLOC_HEADER_SIZE)){
 			set_size(free, get_size(free) - raw_size - ALLOC_HEADER_SIZE);
 			get_right_header(free)->left_size = get_size(free);
 		}else{
 			delete(free);
 			set_size(free, get_size(free) - raw_size - ALLOC_HEADER_SIZE);
 			insert(free);
 			get_right_header(free)->left_size = get_size(free);
 		}
 		set_size(allocated, raw_size+ALLOC_HEADER_SIZE);
 		set_state(allocated, ALLOCATED);
 		allocated->left_size = get_size(free);
 		get_right_header(allocated)->left_size = get_size(allocated);
 		// insert(free);
 	}else{
 		delete(free);
 		set_state(free, ALLOCATED);
 		get_right_header(free)->left_size = get_size(free);
 		return (header *)free->data;
 	}
 
 	return (header *)allocated->data;
}

/**
 	Helper to get the header from a pointer allocated with malloc
 */
static inline header * ptr_to_header(void * p) {
  return (header *)((char *) p - ALLOC_HEADER_SIZE); //sizeof(header));
}

/**
 	Helper to manage deallocation of a pointer returned by the user
 */
static inline void deallocate_object(void * p) {
  //freeing NULL
  if(p == NULL){
  	return;
  }
  //check double freeing
  header * target = (header *)((char *)p - ALLOC_HEADER_SIZE);
  if(get_state(target) != ALLOCATED){
  	fprintf(stderr,"Double Free Detected\n");
  	assert(false);
  	return;
  }
  set_state(target, UNALLOCATED);
  insert(target);
  coalescing(target);
  

}
static inline int find_index(size_t free){
 	int revised_size = (int)free-ALLOC_HEADER_SIZE;
 	for(int i=0; i<N_LISTS; i++){
 		int min = (i*sizeof(size_t))+1;
 		int max = ((i+1)*sizeof(size_t));
 		if(revised_size >= min && revised_size <= max){
 			return i;
 			
 		}else if(revised_size >= (((N_LISTS-1)*sizeof(size_t))+1)){
 			return N_LISTS-1;
 		}
	}
	return 0;
}
static inline void coalescing(header * p){
	if(get_state(get_left_header(p)) == UNALLOCATED && get_state(get_right_header(p)) != UNALLOCATED){
		//when left is UNALLOCATED
		header * left = get_left_header(p);
		header * cur = p;
		
		//check where to put coalesced block (check if the coalesce block belongs to different index)
		if(find_index(get_size(left) + get_size(cur)) == find_index(get_size(left))){
			//delete current header
			delete(cur);
 			//coaelscing
 			set_size(left, get_size(cur) + get_size(left));
 			get_right_header(left)->left_size = get_size(left);
 		}else{
 			delete(left);
 			delete(cur);
 			set_size(left, get_size(cur) + get_size(left));
 			get_right_header(left)->left_size = get_size(left);
 			insert(left);
 		}

	}else if(get_state(get_left_header(p)) != UNALLOCATED && get_state(get_right_header(p))== UNALLOCATED){
		//when right is UNALLOCATED
		header * right = get_right_header(p);
		header * cur = p;
		
		if(find_index(get_size(right) + get_size(cur)) == find_index(get_size(cur))){
			delete(right);
			set_size(cur, get_size(cur) + get_size(right));
			get_right_header(cur)->left_size = get_size(cur);
		}else{
			delete(right);
			delete(cur);
			set_size(cur, get_size(cur) + get_size(right));
			get_right_header(cur)->left_size = get_size(cur);
			insert(cur);
		}		


	}else if(get_state(get_left_header(p)) == UNALLOCATED && get_state(get_right_header(p)) == UNALLOCATED){
		//when right and left is UNALLOCATED
		header * right = get_right_header(p);
		header * left = get_left_header(p);
		header * cur = p;
		
		if(find_index(get_size(right) + get_size(cur) +get_size(left)) == find_index(get_size(left))){
			delete(right);
			delete(cur);
			set_size(left, get_size(cur) + get_size(right) + get_size(left));
			get_right_header(left)->left_size = get_size(left);
		}else{
			delete(right);
			delete(cur);
			delete(left);
			set_size(left, get_size(left)+get_size(cur) + get_size(right));
			get_right_header(left)->left_size = get_size(left);
			insert(left);
		}	
		
	} else{
		//when right and left is ALLOCATED
		//do nothing
		return;
	}
}
/**
 	Helper to detect cycles in the free list
 */
static inline header * detect_cycles() {
  for (int i = 0; i < N_LISTS; i++) {
    header * freelist = &freelistSentinels[i];
    for (header * slow = freelist->next, * fast = freelist->next->next; 
         fast != freelist; 
         slow = slow->next, fast = fast->next->next) {
      if (slow == fast) {
        return slow;
      }
    }
  }
  return NULL;
}

/**
 	Helper to verify that there are no unlinked previous or next pointers in the free list
 */
static inline header * verify_pointers() {
  for (int i = 0; i < N_LISTS; i++) {
    header * freelist = &freelistSentinels[i];
    for (header * cur = freelist->next; cur != freelist; cur = cur->next) {
      if (cur->next->prev != cur || cur->prev->next != cur) {
        return cur;
      }
    }
  }
  return NULL;
}

static inline bool verify_freelist() {
  header * cycle = detect_cycles();
  if (cycle != NULL) {
    fprintf(stderr, "Cycle Detected\n");
    print_sublist(print_object, cycle->next, cycle);
    return false;
  }

  header * invalid = verify_pointers();
  if (invalid != NULL) {
    fprintf(stderr, "Invalid pointers\n");
    print_object(invalid);
    return false;
  }

  return true;
}

/**
 	Helper to verify that the sizes in a chunk from the OS are correct
 */
static inline header * verify_chunk(header * chunk) {
	if (get_state(chunk) != FENCEPOST) {
		fprintf(stderr, "Invalid fencepost\n");
		print_object(chunk);
		return chunk;
	}
	
	for (; get_state(chunk) != FENCEPOST; chunk = get_right_header(chunk)) {
		if (get_size(chunk)  != get_right_header(chunk)->left_size) {
			fprintf(stderr, "Invalid sizes\n");
			print_object(chunk);
			return chunk;
		}
	}
	
	return NULL;
}

/**
 	For each chunk allocated by the OS verify that the boundary tags are consistent
 */
static inline bool verify_tags() {
  for (size_t i = 0; i < numOsChunks; i++) {
    header * invalid = verify_chunk(osChunkList[i]);
    if (invalid != NULL) {
      return invalid;
    }
  }

  return NULL;
}

/**
 	Initialize mutex lock and prepare an initial chunk of memory for allocation
 */
static void init() {
  // Initialize mutex for thread safety
  pthread_mutex_init(&mutex, NULL);

#ifdef DEBUG
  // Manually set printf buffer so it won't call malloc when debugging the allocator
  setvbuf(stdout, NULL, _IONBF, 0);
#endif // DEBUG

  // Allocate the first chunk from the OS
  header * block = allocate_chunk(ARENA_SIZE);

  header * prevFencePost = get_header_from_offset(block, -ALLOC_HEADER_SIZE);
  insert_os_chunk(prevFencePost);

  lastFencePost = get_header_from_offset(block, get_size(block));

  // Set the base pointer to the beginning of the first fencepost in the first
  // chunk from the OS
  base = ((char *) block) - ALLOC_HEADER_SIZE; //sizeof(header);

  // Initialize freelist sentinels
  for (int i = 0; i < N_LISTS; i++) {
    header * freelist = &freelistSentinels[i];
    freelist->next = freelist;
    freelist->prev = freelist;
  }

  // Insert first chunk into the free list
  header * freelist = &freelistSentinels[N_LISTS - 1];
  freelist->next = block;
  freelist->prev = block;
  block->next = freelist;
  block->prev = freelist;
}

/* 
 * External interface
 */
void * my_malloc(size_t size) {
  pthread_mutex_lock(&mutex);
  header * hdr = allocate_object(size); 
  pthread_mutex_unlock(&mutex);
  return hdr;
}

void * my_calloc(size_t nmemb, size_t size) {
  return memset(my_malloc(size * nmemb), 0, size * nmemb);
}

void * my_realloc(void * ptr, size_t size) {
  void * mem = my_malloc(size);
  memcpy(mem, ptr, size);
  my_free(ptr);
  return mem; 
}

void my_free(void * p) {
  pthread_mutex_lock(&mutex);
  deallocate_object(p);
  pthread_mutex_unlock(&mutex);
}

bool verify() {
  return verify_freelist() && verify_tags();
}
