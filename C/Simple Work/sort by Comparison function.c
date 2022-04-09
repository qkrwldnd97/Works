#include "mysort.h"
#include <alloca.h>
#include <assert.h>
#include <string.h>
#include <stdlib.h>

//
// Sort an array of element of any type
// it uses "compFunc" to sort the elements.
// The elements are sorted such as:
//

void mysort( int n,                      // Number of elements
	     int elementSize,            // Size of each element
	     void * array,               // Pointer to an array
	     int ascending,              // 0 -> descending; 1 -> ascending
	     CompareFunction compFunc )  // Comparison function.
{
	// Add your code here
	int i=0;
    int j=0;
    void * swap = malloc(elementSize);
    if (ascending == 1){
        for(i=0;i<(n-1); i++)
        {
            for(j=0;j<n-i-1;j++)
            {
                void * current = (char *)array+(elementSize*j);
            	void * next = (char *)array+(elementSize*(j+1));
            	if(compFunc(current,next)>=0){
                    memcpy(swap,current,elementSize);
                    memcpy(current,next,elementSize);
                    memcpy(next,swap,elementSize);
                }
            }
        }
    }   
	else{
        for (i=0;i<(n-1);i++)
        {
            for (j=0;j<n-i-1;j++)
            {
            void *current = (char *)array+(elementSize*j);
            void *next = (char *)array+(elementSize*(j+1));
            if ( compFunc(current, next)<=0)
                {
                    memcpy(swap,current,elementSize);
                    memcpy(current,next,elementSize);
                    memcpy(next,swap,elementSize);
                }
            }
        }
    }   
}




