public class FirstLastoccurence {
    public static void main(String[] args) {
        int[] A = {-2,1,1,4,4,4,6,7,9,10};
        int target = 4;

        int left = 0;
        int right = A.length-1;
//        int mid = (left+(right-left+1)/2);
//        int mid = (left+right)/2;
        while(left < right){
            int mid = left+((right)-left)/2;
            if(A[mid] > target){
                right = mid-1;
            }else if(A[mid] == target && target < A[mid+1] || target == A[A.length-1]){
                System.out.println(mid);
                break;
            }
            else{
                left = mid;
            }
//            mid = ((left+right))/2;
        }

//            System.out.println(right);

    }
}
