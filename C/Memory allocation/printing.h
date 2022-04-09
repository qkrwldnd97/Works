#ifndef PRINTING_H
#define PRINTING_H

typedef void (*printFormatter)(header *);

/* Functions defining a format to print */
void basic_print(header * block);
void print_list(header * block);
void print_object(header * block);
void print_status(header * block);

/* Functions to print the freelist and boundary tag data structures taking a
 * one of the above printing functions as a function pointer
 */
void freelist_print(printFormatter pf);
void tags_print(printFormatter pf);

/* Helpers */
void print_sublist(printFormatter pf, header * start, header * end);
void print_pointer(void * p);

#endif // PRINTING_H
