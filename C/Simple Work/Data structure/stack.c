
#include <stdio.h>
#include "stack.h"
#include <stdlib.h>

int top=0, top2 = -1;
double stack[MAXSTACK];

void stack_clear() 
{
  top = 0;
}

double stack_pop()
{
	// Add implementation here`
	double pop = stack[top2];
	stack[top2] = '\0';
	top2--;
	top--;
	if(top2<=0){
		top = 0;
	}
	return pop;
}

void stack_push(double val)
{
	// Add implementation here
	top2++;
	stack[top2] = val;
	top++;
}

void stack_print()
{
	printf("Stack:\n");
	if(stack[0] == '\0'){
		printf("Stack is empty");
}	
	for(int i=0;i<top;i++){
		printf("%d: %f\n", i, stack[i]);
}		
}

int stack_top()
{
  return top;
}

int stack_max()
{
  return MAXSTACK;
}

int stack_is_empty()
{
  return top == 0;
}


