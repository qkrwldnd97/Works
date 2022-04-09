
#include <string.h>
#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <math.h>

#include "rpn.h"
#include "nextword.h"
#include "stack.h"

double rpn_eval(char * fileName, double x) {

	FILE *fd;
	fd = fopen(fileName, "r");
	if(fd == NULL){
		printf("Could not open file %s\n", fileName);
		exit(1);
	}

	char *word;
	double d1,d2,d3,operate;
	int c = 0;

	while((word = nextword(fd))!='\0'){
		d1 = atof(word);

		if (strcmp(word, "+") == 0){
			if (c < 2){
				printf ("Stack underflow\n");
				exit(1);
			}
			d3 = stack_pop();
			d2 = stack_pop();
			operate = d2 + d3;
			stack_push(operate);
			c--;
		}
		
		else if (strcmp(word, "-") == 0){
			if (c < 2){
				printf ("Stack underflow\n");
				exit(1);
			}
			d3 = stack_pop();
			d2 = stack_pop();
			operate = d2 - d3;
			stack_push(operate);
			c--;
		}

		else if (strcmp(word, "*") == 0){
			if (c < 2){
				printf ("Stack underflow\n");
				exit(1);
			}
			d3 = stack_pop();
			d2 = stack_pop();
			operate = d3 * d2;
			stack_push(operate);
			c--;
		}
		else if (strcmp(word, "/") == 0){
			if (c < 2){
				printf ("Stack underflow\n");
				exit(1);
			}
			d3 = stack_pop();
			d2 = stack_pop();
			operate = d2/d3;
			stack_push(operate);
			c--;
		}

		else if (strcmp(word, "sin") == 0){
			if (c < 1){
				printf ("Stack underflow\n");
				exit(1);
			}
			d3 = stack_pop();
			operate = sin(d3);
			stack_push(operate);
		}

		else if (strcmp(word, "cos") == 0){
			if (c < 1){
				printf ("Stack underflow\n");
				exit(1);
			}
			d3 = stack_pop();
			operate = cos(d3);
			stack_push(operate);
		}

		else if (strcmp(word, "pow") == 0){
			if (c < 2){
				printf ("Stack underflow\n");
				exit(1);
			}
			d3 = stack_pop();
			d2 = stack_pop();
			operate = pow(d2, d3);
			stack_push(operate);
			c--;
		}

		else if (strcmp(word, "log") == 0){
			if (c < 1){
				printf ("Stack underflow\n");
				exit(1);
			}
			d3 = stack_pop();
			operate = log(d3);
			stack_push(operate);
		}

		else if (strcmp(word, "exp") == 0){
			if (c < 1){
				printf ("Stack underflow\n");
				exit(1);
			}
			d3 = stack_pop();
			operate = exp(d3);
			stack_push(operate);
		}
		
		else if (strcmp(word, "x") == 0){
			stack_push(x);
			c++;
		}
		else {
			stack_push(d1);
			c++;
		}
	}
	
	if (stack_top() > 2){
		printf ( "Elements remain in the stack\n");
		exit(1);
	}
	
 	double result = stack_pop();
	return result;
}

