package Arrays;

public class RotateImage {
    public static void main(String[] args) {
        int[][] matrix = new int[][] {{1,2,3},{4,5,6},{7,8,9}};
        int[][] matrix2 = new int[][] {{1,2,3},{4,5,6},{7,8,9}};
        int[][] matrix3 = new int[][] {{1,2,3},{4,5,6},{7,8,9}};

        System.out.println("original matrix");
        printMatrix(matrix);

        rotateImage(matrix);
        System.out.println("After rotation complete");
        printMatrix(matrix);

        rotate(matrix2);
        System.out.println("After rotation complete");
        printMatrix(matrix2);

        rotate2(matrix3);
        System.out.println("After rotation complete");
        printMatrix(matrix3);

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
        // transpose operation
        for(int row = 0; row<matrix.length; row++){
            for(int col = row; col<matrix[0].length; col++){
                int temp = 0;
                temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }
        System.out.println("after 1st for loop (transpose) ");
        for (int i = 0; i < matrix.length; i ++) {
            System.out.println(java.util.Arrays.toString(matrix[i]));
        }

        // flip vertically
        for(int row =0 ; row<matrix.length; row++){
            for(int col = 0; col<matrix[0].length/2; col++){
                System.out.println("i = " + row + " j = " + col);
                int temp = 0;
                temp = matrix[row][col];
                matrix[row][col] = matrix[row][matrix[0].length-1-col];
                matrix[row][matrix[0].length-1-col] = temp;
            }
        }
        System.out.println("after 2nd for loop (flip vertically)");
        for (int i = 0; i < matrix.length; i ++) {
            System.out.println(java.util.Arrays.toString(matrix[i]));
        }
    }

    // The idea is to rotate the matrix layer by layer
    // 1 2 3
    // 4 5 6
    // 7 8 9

    // 7 2 1
    // 4 5 6
    // 9 8 3

    // 7 4 1
    // 8 5 2
    // 9 6 3
    private static void rotate(int[][] matrix) {
        int matrixLength =matrix.length;
        // row is i, col is j
        for (int row = 0; row < matrixLength / 2; row++)
            for (int col = row; col < matrixLength - row - 1; col++) {
                int tmp=matrix[row][col];
                // row to column transformation
                matrix[row][col]=matrix[matrixLength - row - 1][col];
                matrix[matrixLength - col - 1][row]=matrix[matrixLength - row - 1][matrixLength - col - 1];
                matrix[matrixLength - row - 1][matrixLength - col - 1]=matrix[col][matrixLength - row - 1];
                matrix[col][matrixLength - row - 1]=tmp;
            }
    }

    public static void rotate2(int[][] matrix) {
        if (matrix == null || matrix.length <= 1) {
            return;
        }
        int n = matrix.length;
        // take transpose of the matrix
        for (int row = 0; row < n; row++) {
            for (int col = row; col < n; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }

        for (int row = 0; row < n; row++) {
            int head = 0;
            int tail = n - 1;
            // exchange head and tail (vertical flip)
            while (head < tail) {
                int temp = matrix[row][head];
                matrix[row][head] = matrix[row][tail];
                matrix[row][tail] = temp;
                head++;
                tail--;
            }
        }
    }
}
