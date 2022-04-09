#include <stdio.h>
#include <stdlib.h>

#define MAXWORD 200
char word[MAXWORD];
int wordLength;

// It returns the next word from fd.
// If there are no more more words it returns NULL. 
char * nextword(FILE * fd) {
  	int c;
	
	while(( c = fgetc(fd)) != EOF){
		wordLength = 0;
	
	while(c != EOF && c != ' ' && c !='\n' && c !='\t'&&c !='\r'){
		word[wordLength] = c;
		wordLength++;
		c = fgetc(fd);
		if (c ==' '|| c == '\n' || c == '\n' || c == '\t' || c == '\r'){
			word[wordLength] = '\0';
			return word;
}

}
}
	// While it is not EOF read char
		// While it is not EOF and it is a non-space char
		// store character in word.
		// if char is not in word return word so far.
		//
	// Return null if there are no more words
	
	return NULL;
}


