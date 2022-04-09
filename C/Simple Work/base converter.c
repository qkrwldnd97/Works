 #include <string.h>
 #include <stdio.h>
 #include <stdlib.h>
 #include <math.h>
 
 int ctoi(char a){
     if(a == '0')
          {return 0;}
      else if(a =='1')
         {return 1;}
     else if(a == '2') {
         return 2;
     }else if(a == '3') {
         return 3;
     } else if(a == '4') {
          return 4;
     } else if(a == '5') {
          return 5;
     } else if(a == '6') {
          return 6;
     } else if(a == '7') {
          return 7;
     } else if(a == '7') {
          return 7;
     } else if(a == '9') {
          return 9;
     } else if(a == 'A') {
          return 10;
     } else if(a == 'B') {
          return 11;
     } else if(a == 'C') {
          return 12;
     } else if(a == 'D') {
          return 13;
     } else if(a == 'E') {
          return 14;
     } else if(a == 'F') {
          return 15;
     }
 }
 float my_pow(float a, float b) {
     float r = a;
     if(b>0){
         while(--b)
         r *=a;
     }else if(b<0){
         while(++b)
         r *=a;
         r=1/r;
     }else{
         r = 1;
     }
     return r;

 }
 int main(int argc, char ** argv) {
     char base_digits[25]= {'0','1','2','3','4','5','6','7','8', '9', 'A', 'B', 'C', 'D', 'E',  'F','G', 'H', 'I', 'J', 'K', 'L', 'M', 'N','O'};

     if(argc < 4)
     {
         printf("Usage:  convert <basefrom> <baseto> <number>\n");
         exit(1);
     }

     int num[64];
     int base_from = atoi(argv[1]);
     int base_to = atoi(argv[2]);


     printf("Number read in base %d: %s\n", base_from, argv[3]);

     int base_ten = 0;
     int i = 0;
     int j = 0;
     for (i = strlen(argv[3])-1; i >= 0; i--) {
         if (ctoi(argv[3][i]) > base_from){
             printf("Wrong digit in number.\n");
             return 1;
         }
         base_ten += ctoi(argv[3][i]) * (int)my_pow(base_from, j);
         j++;
     }

     printf("Converted to base 10: %d\n", base_ten);

     int converted_num[128];
     i = 0;
     while(base_ten != 0) {
         converted_num[i] = base_ten % base_to;
         base_ten = base_ten / base_to;
         i++;
     }
     i--;
     printf("Converted to base %d: ", base_to);
     for( ; i>=0; i--)
     {
         printf("%c", base_digits[converted_num[i]]);
     }
     printf("\n");
     return 0;

 }

