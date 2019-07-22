package Arrays;

public class LargestSquare {
    public static void main(String[] args) {
        char[][] input = new char[3][4];
        input[0][0] = '1';
        input[0][1] = '1';
        input[0][2] = '0';
        input[0][3] = '1';
        input[1][0] = '1';
        input[1][1] = '1';
        input[1][2] = '0';
        input[1][3] = '1';
        input[2][0] = '1';
        input[2][1] = '1';
        input[2][2] = '1';
        input[2][3] = '1';

        System.out.println(largestSquareDP(input));
        System.out.println(largestSquareBruteForce(input));

    }

//    Let the given binary matrix be M[R][C]. The idea of the algorithm is to
//    construct an auxiliary size matrix S[][] in which each entry S[i][j]
//    represents size of the square sub-matrix with all 1s including M[i][j]
//    where M[i][j] is the rightmost and bottommost entry in sub-matrix.
    private static int largestSquareDP(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;

        int numRows = matrix.length;
        int numCols = matrix[0].length;

        int[][] t = new int[numRows][numCols];

        // left col
        for (int index = 0; index < numRows; index ++) {
            t[index][0] = Character.getNumericValue(matrix[index][0]);
        }

        // top row
        for (int index = 0; index < numCols; index ++) {
            t[0][index] = Character.getNumericValue(matrix[0][index]);
        }

        // cells inside
        for (int row = 1; row < numRows; row++) {
            for (int col = 1; col < numCols; col++) {
                if (matrix[row][col] == '1') {
                    int min = Math.min(t[row-1][col], t[row-1][col-1]);
                    min = Math.min(min, t[row][col-1]);
                    t[row][col] = min + 1;
                } else {
                    t[row][col] = 0;
                }
            }
        }

        // find the max entry
        int max = 0;
        //get maximal length
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (t[row][col] > max) {
                    max = t[row][col];
                }
            }
        }

        return max * max;
    }

//
//    We use a variable to contain the size of the largest square found so far
//    and another variable to store the size of the current, both initialized
//    to 0. Starting from the left uppermost point in the matrix, we search for a
//    1. No operation needs to be done for a 0. Whenever a 1 is found, we try to
//    find out the largest square that can be formed including that 1. For this,
//    we move diagonally (right and downwards), i.e. we increment the row index
//    and column index temporarily and then check whether all the elements of that
//    row and column are 1 or not. If all the elements happen to be 1, we move
//    diagonally further as previously. If even one element turns out to be 0,
//    we stop this diagonal movement and update the size of the largest square.
//    Now we, continue the traversal of the matrix from the element next to the
//    initial 1 found, till all the elements of the matrix have been traversed.
    private static int largestSquareBruteForce(char[][] matrix) {
        int numRows = matrix.length;
        int numCols = numRows > 0 ? matrix[0].length : 0;

        int maxsqlen = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (matrix[row][col] == '1') {
                    int sqlen = 1;
                    boolean flag = true;
                    while (sqlen + row < numRows && sqlen + col < numCols && flag) {
                        for (int k = col; k <= sqlen + col; k++) {
                            if (matrix[row + sqlen][k] == '0') {
                                flag = false;
                                break;
                            }
                        }

                        for (int k = row; k <= sqlen + row; k++) {
                            if (matrix[k][col + sqlen] == '0') {
                                flag = false;
                                break;
                            }
                        }

                        if (flag) sqlen++;
                    }
                    if (maxsqlen < sqlen) {
                        maxsqlen = sqlen;
                    }
                }
            }
        }
        return maxsqlen * maxsqlen;
    }
}
