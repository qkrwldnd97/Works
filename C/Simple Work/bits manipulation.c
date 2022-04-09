 void printBits(unsigned int bitmap)
  {   
     
      int a = 31;
      int b;
      while (a >= 0){
         b = bitmap >> a;
         if (b & 1) {
             printf("1");
         }
         else { 
             printf("0");
         }
         a--;
     }
     printf("\n");
     a = 31;
     while (a >= 0){
         printf("%d", a % 10);
         a--;
     }
     printf("\n");
 }
 // Sets the ith bit in *bitmap with the value in bitValue (0 or 1)
 void setBitAt( unsigned int *bitmap, int i, int bitValue ) {
    
     if (bitValue == 1){
         *bitmap |= 1 << i;
     }
     else { 
         *bitmap &= ~(1 << i);
     }
 }

 // It returns the bit value (0 or 1) at bit i
 int getBitAt( unsigned int bitmap, unsigned int i) {
 
     int a = bitmap >> i;
     if (a & 1){
         return 1;
     }
     else {
         return 0;
     }

 }
 // It returns the number of bits with a value "bitValue".
 // if bitValue is 0, it returns the number of 0s. If bitValue is 1, it returns the number of 1s.
 int countBits( unsigned int bitmap, unsigned int bitValue) {

     int a;
     int total = 0;
     int b = 31;
     while (b >= 0){
         a = bitmap >> b;
         if (bitValue){
             if (a & 1){
                 total++;
             }
         }
         else {
             if (!(a & 1)){
                 total++;
             }
         }
         b--;
     }
     return total;

 }
 // It returns the number of largest consecutive 1s in "bitmap".
 // Set "*position" to the beginning bit index of the sequence.
 int maxContinuousOnes(unsigned int bitmap, int * position) {

	int arr[40][2],i,j,in,count,max;
	unsigned int bitmask=1;
	for(i=0,j=0,in=0,count=0,max=0;(i<32)&&(bitmask=(1<<i));i++){
	  if(bitmask&bitmap){
			count++;
	  }else{
		  if(count>0){
		    arr[j][0]=count;
		    arr[j++][1]=i-count;
		  }
		  count=0;
		}	
	}
	for(i=0;i<j;i++)
		if(arr[i][0]>arr[max][0])
			max=i;
	*position=arr[max][1];
	return arr[max][0];
}

