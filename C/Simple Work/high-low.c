#include <stdio.h>
int average(int,int);
int main()
{
    int num;	
    int i=0;
    int high=100;
    int low=1;
    char c;
    //int appro = average(high,low);
    printf("Welcome to the High Low game...\n");
    printf("Think of a number between 1 and 100 and press <enter>\n\"");
//    scanf("%d", &num);
    getchar();
//int i=0;
    do{
    i++;
    int appro = average(high,low);
    printf("Is it higher than %d? (y/n)\n\"", appro);
    scanf(" %c", &c);
    if(c=='n'){
        high = appro-1;
    }else if(c=='y'){
	low = appro+1;
}
else{
        printf("Type y or n\n");
    }
if(appro>=100 && c=='y'){
	printf("\n>>>>>> The number is %d\n\n", appro+1);
	printf("Do you want to continue playing (y/n)?\"");
	scanf(" %c", &c);
	if(c=='y'){
		printf("Think of a number between 1 and 100 and press <enter>\n");
		getchar();
		high=100;
		low=1;
		continue;
}

	else{
		printf("Thanks for playing!!!\n");
		break;
}
}
if(high<low){
//	if(i<7){
//	printf("\n>>>>>> The number is %d\n\n", appro+1);
//	printf("Do you want to continue playinh (y/n)?\"");
//	scanf(" %c",&c);
//	if(c=='y'){
//	printf("Think of a number between 1 and 100 and press <enter>\n");
//	high=100;
//	low=1;
//	continue;
//}else{
//	printf("Thanks for playing!!!\n");
//	break;
//}
//}
        printf("\n>>>>>> The number is %d\n\n", low);
        printf("Do you want to continue playing (y/n)?\"");
        scanf(" %c",&c);
        if(c=='y'){
            printf("Think of a number between 1 and 100 and press <enter>\n");
            getchar();
            high=100;
            low=1;
            continue;
        }else{
            printf("Thanks for playing!!!\n");
            break;
        }
    }
    
}while(1>0);
//	printf(">>>>>> The number is %d\n", high);
    return 0;
}
int average(int a,int b){
    return (a+b)/2;
}


