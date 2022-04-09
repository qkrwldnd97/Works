

 #include <stdlib.h>
 
 
int isPalindrome(char * str)
{
    int i=0;
	while(str[i] != '\0'){
		i++;
	}
	int j=0;
	int constant = i;
	i--;
	for(j=0;j<constant; j++){
		while(str[j] == '\n' || str[j] == '\r' || str[j] == '\t' || str[j] == ' '){
				++j;
}
		while(str[i] == '\n' || str[i] == '\r' || str[i] == '\t' || str[i] == ' '){
				i--;
}
		if(str[j] != str[i]){
			return 0;
}
	i--;
}
return 1;
}
  
 