
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char ** argv) {
	int c;
	int lines=0;
	char *fileName = argv[1];
	FILE *fd = fopen(fileName,"r");
 	// Add your implementation here
	do{
		c=fgetc(fd);
		if(c=='\n'){
			lines++;
}
}while(c!=EOF);
	printf("Program that counts lines.\n");
	printf("Total lines: %d\n",lines);
	exit(0);
}
