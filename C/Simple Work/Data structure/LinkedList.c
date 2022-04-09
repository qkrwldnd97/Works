
#include <assert.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "LinkedList.h"

//
// Initialize a linked list
//
void llist_init(LinkedList * list)
{
        list->head = NULL;
}

//
// It prints the elements in the list in the form:
// 4, 6, 2, 3, 8,7
//
void llist_print(LinkedList * list) {
        
        ListNode * e;

        if (list->head == NULL) {
                printf("{EMPTY}\n");
                return;
        }

        printf("{");

        e = list->head;
        while (e != NULL) {
                printf("%d", e->value);
                e = e->next;
                if (e!=NULL) {
                        printf(", ");
                }
        }
        printf("}\n");
}

//
// Appends a new node with this value at the beginning of the list
//
void llist_add(LinkedList * list, int value) {
        // Create new node
        ListNode * n = (ListNode *) malloc(sizeof(ListNode));
        n->value = value;
        
        // Add at the beginning of the list
        n->next = list->head;
        list->head = n;
}

//
// Returns true if the value exists in the list.
//
int llist_exists(LinkedList * list, int value) {
        ListNode * current = (ListNode *) malloc(sizeof(ListNode));
        if(list->head == NULL){
                return 0;
        }
        current = list->head;
        while(current!=NULL){
                if(current->value == value){
                        return 1;
                }
                current = current->next;
        }
        return 0;
}

//
// It removes the entry with that value in the list.
//
int llist_remove(LinkedList * list, int value) {
        ListNode * current;
        if(list->head == NULL){
                return 0;
        }
        // ListNode * current;
        current = list->head;
        if(current->value == value){
                list->head = current->next;
        }
        while(current->next != NULL){
                if(current->next->value == value){
                        current->next = current->next->next;
                        return 1;
                }
                current = current->next;
        }
        return 0;
}

//
// It stores in *value the value that correspond to the ith entry.
// It returns 1 if success or 0 if there is no ith entry.
//
int llist_get_ith(LinkedList * list, int ith, int * value) {
        ListNode * current;
        int count=0;
        current = list->head;
        if(list->head == NULL){
                return 0;
        } while(current != NULL && count <ith){
                current = current->next;
                count++;
        }
        if(count == ith){
                *value = current->value;
                return 1;
        }
        return 0;
}

//
// It removes the ith entry from the list.
// It returns 1 if success or 0 if there is no ith entry.
//
int llist_remove_ith(LinkedList * list, int ith) {
        if(list->head == NULL){
                return 0;
        }
        ListNode * current = list->head;
        int count=0;
        while(current != NULL && count<ith-1){
                count++;
                current = current->next;
        }
        if(count == ith-1){
                current->next = current->next->next;
                return 1;
        }
        return 0;
}

//
// It returns the number of elements in the list.
//
int llist_number_elements(LinkedList * list) {
        ListNode * current=(ListNode *) malloc(sizeof(ListNode));
        int count=0;
        current = list->head;
        if(current == NULL){
                return 0;
        }
        while(current!=NULL){
                count++;
                current = current->next;
        }
        return count;
}


//
// It saves the list in a file called file_name. The format of the
// file is as follows:
//
// value1\n
// value2\n
// ...
//
int llist_save(LinkedList * list, char * file_name) {
        ListNode * current;
        FILE * fd = fopen(file_name,"wb");
        current = list->head;
        if(fd == NULL){
                return 0;
        }
        while(current != NULL){
                fprintf(fd, "%d\n", current->value);
                current = current->next;
        }
        fclose(fd);
        return 0;
}

//
// It reads the list from the file_name indicated. If the list already has entries, 
// it will clear the entries.
//
int llist_read(LinkedList * list, char * file_name) {
        ListNode * current;
        FILE * fd = fopen(file_name,"r");
        current = list->head;
        int a=0;
        if(fd == NULL){
                return 0;
        }
        fscanf(fd,"%d", &a);
        while(!feof(fd)){
                llist_add(list, a);
                fscanf(fd,"%d",&a);
        }
        return 1;
}


//
// It sorts the list. The parameter ascending determines if the
// order si ascending (1) or descending(0).
//
void llist_sort(LinkedList * list, int ascending) {
        ListNode * current;
        current = list->head;
        int llen = llist_number_elements(list);
        int a = 0;
        while (a < llen){
                int aa = 0;
                while (aa < llen - a){
                        ListNode * current2 = current;
                        int aaa = 0;
                        while (aaa < aa){
                                current2 = current2->next;
                                aaa++;
                        }
                        if (ascending){
                                if (current->value > current2->value){
                                        int count = current->value;
                                        current->value = current2->value;
                                        current2->value = count;
                                }
                        }
                        else{
                                if (current->value < current2->value){
                                        int count = current->value;
                                        current->value = current2->value;
                                        current2->value = count;
                                }
                        }
                        aa++;
                }
                current = current->next;
                a++;
        }
}

//
// It removes the first entry in the list and puts value in *value.
// It also frees memory allocated for the node
//
int llist_remove_first(LinkedList * list, int * value) {
        ListNode * current;
        current= list->head;
        if(current == NULL){
                return 0;
        }
        *value = current->value;
        list->head=current->next;
        free(current);
        return 1;
}

//
// It removes the last entry in the list and puts value in *value/
// It also frees memory allocated for node.
//
int llist_remove_last(LinkedList * list, int *value) {
        ListNode * current;
        current = list -> head;
        if(current == NULL){
                return 0;
        }
        while(current->next->next != NULL){
                current = current->next;
        }
        *value = current->next->value;
        free(current->next);
        current->next = NULL;
  return 1;
}

//
// Insert a value at the beginning of the list.
// There is no check if the value exists. The entry is added
// at the beginning of the list.
//
void llist_insert_first(LinkedList * list, int value) {
        llist_add(list,value);
}

//
// Insert a value at the end of the list.
// There is no check if the name already exists. The entry is added
// at the end of the list.
//
void llist_insert_last(LinkedList * list, int value) {
        ListNode * current;
        current= list->head;
        if(current == NULL){
                return;
        }
        while(current->next != NULL){
                current = current->next;
        }
        ListNode * current2=(ListNode *) malloc(sizeof(ListNode));
        current2->value = value;
        current2->next = NULL;
        current->next = current2;
}

//
// Clear all elements in the list and free the nodes
//
void llist_clear(LinkedList *list)
{
        ListNode * current;
        current= list->head;
        while(current != NULL){
                //next = current->next;
                free(current);
                current = current->next;
        }
        list->head = NULL;
}
