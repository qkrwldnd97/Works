// JAVA Code for Find maximum (or minimum)
// sum of a subarray of size k
import java.util.*;

class GFG {

    // Returns maximum sum in a subarray of size k.
    public static int maxSum(int arr[], int n, int k)
    {

        int res = 0;
//        for (int i=0; i<k; i++)
//            res += arr[i];

        int index=0;
 
        int curr_sum = res;
        for (int i=k; i<n; i++)
        {
            curr_sum += arr[i] - arr[i-k];
            if(res <= curr_sum){
                res = curr_sum;
                index = i;
            }

        }
        System.out.println(index);
        System.out.println(index-k+1);
        return res;
    }

    /* Driver program to test above function */
    public static void main(String[] args)
    {
        int arr[] = {2,5,3,8,9,0,1,2};
        int k = 3;
        int n = arr.length;
        System.out.println(maxSum(arr, n, k));
    }
}
