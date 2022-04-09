import java.io.File;

public class Recursion {

    public static int determinant(int[][] matrix){
        int d;
        int r=matrix.length;
        int c=matrix[0].length;
        if(r==c&&r==1){
            return matrix[0][0];
        }
        else{
            d=0;
            for(int i=0; i<r;i++){
                int[][] two= new int[r-1][c-1];
                int remove_r=0;
                int remove_c=i;
                int p=0;
                for(int j=1;j<r;j++){
                    int q=0;
                    for(int k=0;k<c;k++){
                        if(k!=i) {
                            two[j-1][q] = matrix[j][k];
                            q++;
                        }
                    }
                }
                int num=matrix[0][i]*determinant(two);
                if(i%2==0){
                    d+=num;
                }
                else{
                    d-=num;
                }
            }
            return d;
        }

    }
    public static int filecount(File f){
        if(!f.isDirectory()){
            return 1;
        }
        int num=0;
        File[] files =f.listFiles();
        if(files != null){
            for(File b : files){
                num+=filecount(b);
            }
        }
        return num;
    }
}
