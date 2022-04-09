public class Matrix {
    public boolean isSymmetric(int[][] matrix) {
        if (matrix.length != matrix[0].length) {
            return false;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != matrix[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isDiagonal(int[][] matrix) {
        if (matrix.length != matrix[0].length) {
            return false;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i != j && matrix[i][j] != 0) {
                    return false;
                }

            }
        }

        return true;
    }

    public boolean isIdentity(int[][] matrix) {
        if (matrix.length != matrix[0].length) {
            return false;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i != j && matrix[i][j] != 1) {
                    return false;
                } else if (i == j && matrix[i][j] == 0) {
                    return true;
                } else
                    break;
            }
        }return false;
    }



    public boolean isUpperTrianglar(int[][] matrix) {
        if (matrix.length != matrix[0].length) {
            return false;
        }
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j <= matrix.length; j++) {
                    if (i > j && matrix[i][j] != 0)
                        return false;
                }
            }return true;
        }




    public boolean isTridigonal(int[][] matrix) {
        if (matrix.length != matrix[0].length) {
            return false;
        }
        for (int i = 0; i <= matrix.length - 1; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (2 <= Math.abs(i - j) && matrix[i][j] != 0)
                    return false;
            }
        }
        return true;
    }




    public static void main(String[] args) {
        Matrix arr = new Matrix();
        //int[][] matrix = {{1,1,0,0},{1,1,1,0},{0,1,1,1},{0,0,1,1}};
        int[][] matrix = {
                {1,1,0},
                {1,1,1},
                {0,1,1}};

        if (arr.isSymmetric(matrix)) {
            System.out.println("true");
        }
        else {
            System.out.println("false");
        }

        if (arr.isDiagonal(matrix)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
        if (arr.isIdentity(matrix)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
        if (arr.isUpperTrianglar(matrix)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
        if (arr.isTridigonal(matrix)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
