import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Recommender{

    int swaps, compares;
    int[] inversionCounts;
    String[] products;

    public Recommender(){
        swaps = 0;
        compares = 0;
    }

    public int getComapares() {
        return compares;
    }

    public int getSwaps() {
        return swaps;
    }

    private boolean compare(int a ,int b){
        compares++;
        return a <= b;

    }

    private void swap(int[] arr, int index1, int index2){
        swaps++;
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;

        String tempS = products[index1];
        products[index1] = products[index2];
        products[index2] = tempS;

    }

    /**
     * This function is for the calculate inversion counts of each option's.
     */
    public int[] inversionCounts(String dataset, String[] options) {
        // TODO
        int size = 0;
        int[] inversioncount = new int[options.length];
        HashTable data = new HashTable();
        try {
            data.load(dataset);
        }catch (Exception e){
            e.printStackTrace();
        }
        for(int i=0; i<options.length; i++){
            if(data.get(options[i]) != null){
                int end = data.get(options[i]).value.depRating.length;
                inversioncount[size++] = merge(data.get(options[i]).value.depRating, end);
            }

        }
        return inversioncount;
    }
    private static int merge(int[] n, int size){
        return mergesort(n,0,size-1);
    }
    private static int mergesort(int[] n, int low, int high){
        if(low == high){
            return 0;
        }
        int middle = (low + high) / 2;
        int inversion = 0;
        inversion += mergesort(n, low, middle);
        inversion += mergesort(n, middle+1, high);
        inversion += merge(n, low,high);
        return inversion;
    }
    private static int merge(int[] n, int low, int high){
        int middle = (low + high) / 2;
        int size = high - low + 1;
        int[] temp = new int[size];
        int current = 0;
        int j = middle+1, i = low;
        int inversion = 0;
        while(!(i > middle) && !(j>high)){
            if(n[i] > n[j]){
                temp[current++] = n[j++];
                inversion += middle - i + 1;
            }else{
                temp[current++] = n[i++];
            }
        }
        while(!(i>middle)){
            temp[current++] = n[i++];
        }
        while(!(j>high)){
            temp[current++] = n[j++];
        }
        System.arraycopy(temp, 0, n, low, high-low + 1);
        return inversion;
    }

    /**
     * Get the sequence of recommendation from the dataset by sorting the inverse count.
     * */
    public String[] recommend(String dataset, String recentPurchase, String[] options) {
        products = options.clone();
        // TODO
        HashTable aa = new HashTable();
        try {
            aa.load(dataset);
        }catch(Exception e){
            e.printStackTrace();
        }
        HashTable.Pair temp = aa.get(recentPurchase);
        int[] numIn = inversionCounts(dataset, options);
        int a = merge(temp.value.depRating, temp.value.depRating.length);
        for(int i=0; i<numIn.length; i++){
            numIn[i] -= a;
            numIn[i] = Math.abs(numIn[i]);
        }
        sort(numIn,0,numIn.length-1);
        return products;

    }
    private int quick(int[] n, int low, int high){
        int pivot = n[high];
        int i = low-1;
        for(int j=low; j<high; j++){
            if(compare(n[j], pivot)){
                i++;
                swap(n, i, j);
            }
        }
        swap(n, i+1, high);
        return i+1;
    }
    private void sort(int[] n, int low, int high){
        if(low<high){
            int a = quick(n, low, high);
            sort(n, low, a-1);
            sort(n, a+1, high);
        }
    }


    public static void main(String[] args) {
        HashTable hash = new HashTable();
        String filename = "test.txt";
        Recommender a = new Recommender();
        String[] options = {"dPhone3", "tPhone3", "gPhone1"}; // 2, 1, 4
//        int[] arr = {3,1,2,-1};
        int[] abc = a.inversionCounts(filename, options);
        String[] aaa = a.recommend(filename, "ePhone3", options); // 2
//        int aaa = merge(arr, arr.length);
        for(int i=0; i<aaa.length; i++){
            System.out.print(aaa[i] + " ");
        }
        System.out.println();
    }

}
