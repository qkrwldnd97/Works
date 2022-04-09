
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void mymemdump(FILE * fd, char * p , int len) {
    // p[0] will return the element 0 
    // p[1] will return the element 1 and so on
    
    int i;
    fprintf(fd, "0x%016lX: ", (unsigned long) p); // Print address of the beginning of p. You need to print it every 16 bytes

    for (i=0; i < len; i++) {
        int c = p[i]&0xFF; // Get value at [p]. The &0xFF is to make sure you truncate to 8bits or one byte.

        // Print first byte as hexadecimal
        fprintf(fd, "%02X ", c);

        // Print first byte as character. Only print characters >= 32 that are the printable characters.
        fprintf(fd, "%c ", (c>=32)?c:'.');

       if (i % 16 == 0 ) {
        fprintf(fd,"\n");
      }
    }

}

