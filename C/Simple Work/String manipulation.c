
#include <stdlib.h>
#include "mystring.h"


int mystrlen(char * s) {
	int i,count;
	count=0;
	for(i=0; s[i];i++){
		if(s[i] != '\0'){
			count++;
}
}
	return count;
}

char * mystrcpy(char * dest, char * src) {
	int i=0;
	while(src[i]!='\0'){
		dest[i]=src[i];
		i++;
}
dest[i]='\0';
return dest;
}

char * mystrcat(char * dest, char * src) {
	int i=0, j=0;
	while(dest[i] != '\0'){
		i++;
}
	while(src[j]!='\0'){
		dest[i]=src[j];
		i++;
		j++;
}
dest[i]='\0';
	return dest;
}

int mystrcmp(char * s1, char * s2) {
	int i;
	for(i=0; s1[i]!=0 && s2[i]!=0; i++){
	if(s1[i] > s2[i]){
		return 1;
}
	if(s1[i] < s2[i]){
		return -1;
}
}
if(s1[i]!=0){
	return 1;
}
if(s2[i]!=0){
	return -1;
}
return 0;
}
char * mystrstr(char * hay, char * needle) {
	if(!*needle){
	return hay;
	}
	char *s1 = (char*)hay;
	while(*s1){
		char *s11 = s1, *s2 = (char*)needle;
		while(*s1 && *s2 && *s1 == *s2){
			s1++;
			s2++;
}
	if(!*s2){
		return s11;
}
	s1 = s11 +1;
}
  return NULL;
}

char * mystrdup(char * s) {
	int i=0;
	char *dest = (char*)malloc(5*sizeof(int));
	for(i=0;i<sizeof(s);i++){
		dest[i]=s[i];
}
	return dest;	
}

char * mymemcpy(char * dest, char * src, int n)
{
	int i=0;
	char *src2= (char*)src;
	char *dest2= (char*)dest;
	for(i=0; i<n; i++){
		dest2[i] = src2[i];
}	
return dest2;
}

