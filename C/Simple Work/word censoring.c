
 #include <stdlib.h>
 
void censorWord(char * str, char * badword)
{
int lenstr = 0;
        while (str[lenstr] != '\0'){
                lenstr++;
        }
        int lenbad = 0;
        while (badword[lenbad] != '\0'){
                lenbad++;
        }
        /*lenbad--;*/
        int a;
        int countbad = 0;
         
        for (a = 0; a < lenstr; a++){
                if (a == 0 && str[a] == badword[countbad]){
                        countbad = 1;
                }
                else if (str[a] == badword[countbad]){
                        countbad++;
                }
                else {
                        countbad = 0;
                }
                if (lenbad == countbad){
                        if (str[a - countbad] == ' '||str[a-countbad+1]==' '){
                                if (str[a + 1] == ' ' || str[a + 1] == '\0'){
                                        int b;
                                        for (b = lenbad - 1; b > -1; b--){
                                                str[a - b] = 'X';
                                        }
                                }
 }
                        countbad = 0;
                }
        }
}


	
