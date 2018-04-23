package Arrays;

public class RotateImage {
    public static void main(String[] args) {
        int[][] matrix = new int[][] {{1,2,3},{4,5,6},{7,8,9}};
        System.out.println("original matrix");
        printMatrix(matrix);

        rotateImage(matrix);
        System.out.println("After rotation complete");
        printMatrix(matrix);

        rotate(matrix);
        System.out.println("After rotation complete");
        printMatrix(matrix);

        rotate2(matrix);
        System.out.println("After rotation complete");
        printMatrix(matrix);

    }

    private static void printMatrix(int[][] nums) {
        for (int i = 0; i < nums.length; i ++)
            System.out.println(java.util.Arrays.toString(nums[i]));
    }

//    The idea was firstly transpose the matrix and then flip it symmetrically. For instance,
//
//        1  2  3
//        4  5  6
//        7  8  9
//
//    after transpose, it will be swap(matrix[i][j], matrix[j][i])
//
//        1  4  7
//        2  5  8
//        3  6  9
//
//    Then flip the matrix horizontally. (swap(matrix[i][j], matrix[i][matrix.length-1-j])
//
//        7  4  1
//        8  5  2
//        9  6  3
    private static void rotateImage(int[][] matrix) {
        for(int i = 0; i<matrix.length; i++){
            for(int j = i; j<matrix[0].length; j++){
                int temp = 0;
                temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        System.out.println("after 1st for loop (transpose) ");
        for (int i = 0; i < matrix.length; i ++) {
            System.out.println(java.util.Arrays.toString(matrix[i]));
        }
        for(int i =0 ; i<matrix.length; i++){
            for(int j = 0; j<matrix[0].length/2; j++){
                System.out.println("i = " + i + " j = " + j);
                int temp = 0;
                temp = matrix[i][j];
                matrix[i][j] = matrix[i][matrix[0].length-1-j];
                matrix[i][matrix[0].length-1-j] = temp;
            }
        }
        System.out.println("after 2nd for loop (flip vertically)");
        for (int i = 0; i < matrix.length; i ++) {
            System.out.println(java.util.Arrays.toString(matrix[i]));
        }
    }

    private static void rotate(int[][] matrix) {
        int n=matrix.length;
        // row is i, col is j
        for (int i=0; i<n/2; i++)
            for (int j=i; j<n-i-1; j++) {
                int tmp=matrix[i][j];
                matrix[i][j]=matrix[n-i-1][j];
                matrix[n-j-1][i]=matrix[n-i-1][n-j-1];
                matrix[n-i-1][n-j-1]=matrix[j][n-i-1];
                matrix[j][n-i-1]=tmp;
            }
    }

    public static void rotate2(int[][] matrix) {
        if (matrix == null || matrix.length <= 1) {
            return;
        }
        int n = matrix.length;
        // take transpose of the matrix
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        for (int i = 0; i < n; i++) {
            int head = 0;
            int tail = n - 1;
            // exchange head and tail (vertical flip)
            while (head < tail) {
                int temp = matrix[i][head];
                matrix[i][head] = matrix[i][tail];
                matrix[i][tail] = temp;
                head++;
                tail--;
            }
        }
    }
}
