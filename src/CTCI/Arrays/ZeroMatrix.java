package CTCI.Arrays;

import java.util.Arrays;

// write algorithm to zero out all elements on the rows and columns if an element is 0
public class ZeroMatrix {
    public static void main(String[] args) {
        int[][] matrix = new int[][] {{1,2,3,0,4}, {0,3,0,2,1}, {2,3,10,2,1}, {2,3,10,2,1}, {2,3,10,2,1}};
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
            System.out.println();
        }

        zeroOutWrong(matrix);
        
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }

    }

    // note does not take into account of corner cases and zeros out whole matrix
    private static void zeroOutWrong(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] == 0) {
                    for (int rowInside = 0; rowInside < matrix.length; rowInside++) {
                        matrix[rowInside][col] = 0;
                    }

                    for (int colInside = 0; colInside < matrix[0].length; colInside++) {
                        matrix[row][colInside] = 0;
                    }
                }
            }
        }
    }

    private static void zeroRow(int[][] matrix, int row) {
        for (int col = 0; col < matrix[0].length; col++) {
            matrix[row][col] = 0;
        }
    }

    private static void zeroCol(int[][] matrix, int col) {
        for (int row = 0; row < matrix.length; row++) {
            matrix[row][col] = 0;
        }
    }

    private static void setZeros(int[][] matrix) {
        boolean rowHasZero = false;
        boolean colHasZero = false;

        // check if first row has a zero
        for (int col = 0; col < matrix[0].length; col++) {
            if (matrix[0][col] == 0) {
                rowHasZero = true;
                break;
            }
        }

        // check if first col has zero
        for (int row = 0; row < matrix.length; row++) {
            if (matrix[row][0] == 0) {
                colHasZero = true;
                break;
            }
        }

        // check for zeros in the rest of the array and mark zeros at
        // first element if there are zeros in the middle
        for (int row = 1; row < matrix.length; row++) {
            for (int col = 1; col < matrix[0].length; col++) {
                if (matrix[row][col] == 0) {
                    matrix[row][0] = 0;
                    matrix[0][col] = 0;
                }
            }
        }

        // zero rows based on values in first col cell
        for (int row = 1; row < matrix.length; row++) {
            if (matrix[row][0] == 0) zeroRow(matrix,row);
        }

        // zero col based on values in first row cell
        for (int col = 1; col < matrix[0].length; col++) {
            if (matrix[0][col] == 0) zeroCol(matrix,col);
        }

        if (rowHasZero) zeroRow(matrix,0);
        if (colHasZero) zeroCol(matrix,0);

        
    }


}
