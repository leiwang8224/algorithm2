package DP;

/**
 * Created by leiwang on 3/25/18.
 */
public class MaxSubArraySum {
    public static void main(String args[]) {
        int[] array = new int[] {-2,3,5,3,2,4,45,2,3,45,6,64,2,4};
        int[][] array2D = new int[][] {{1,2,3}, {4,5,6}, {7,8,9}, {10,11,12}};

        System.out.println("brute force method = " + maxSubArraySumForce(array, array.length));
        System.out.println("kadane's method = " + maxSubArraySumKadaneAlgorithm(array,array.length));
        System.out.println("2D array max sum = " + matrixMaxSumDp2D(array2D));
    }

    //Recursive method = M(n) = max(M(n-1) + A[n], A[n])

    private static int maxSubArraySumForce(int[] array, int length) {
        int maxSum = 0;
        int tempSum = 0;
        for (int i = 0; i < length; i ++) {
            tempSum = array[i];
            for (int j = i + 1; j < length; j ++) {
                tempSum += array[j];
                if (tempSum > maxSum) {
                    maxSum = tempSum;
                }
            }
        }
        return maxSum;
    }

    private static int matrixMaxSumDp2D(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int row = grid.length, col = grid[0].length;

        // memo array size is the same as the original array
        int[][] memo = new int[row][col];

        // set first element the same as original array
        memo[0][0] = grid[0][0];

        // pre fill first column
        for (int i = 1; i < row; i++) {
            // previous memo plus current grid element (row above)
            memo[i][0] = memo[i-1][0] + grid[i][0];
        }

        // pre fill first row
        for (int j = 1; j < col; j++) {
            // previous memo plus current grid element (column to left)
            memo[0][j] = memo[0][j-1] + grid[0][j];
        }

        // fill remaining cells using grid and memo
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                // take max of the left and upper element and add to original array
                // store to current grid
                memo[i][j] = grid[i][j] + Math.max(memo[i-1][j], memo[i][j-1]);
            }
        }

        return memo[row-1][col-1];
    }

    /**
     * using Kadane's algorithm
     * @param array
     * @param length
     * @return
     */
    private static int maxSubArraySumKadaneAlgorithm(int[] array, int length) {
        int maxSumSoFar = 0;
        int maxSumEndingHere = 0;
        for (int i = 0; i < length; i++) {
            maxSumEndingHere = maxSumEndingHere + array[i];
            if (maxSumEndingHere < 0)
                maxSumEndingHere = 0;
            if (maxSumSoFar < maxSumEndingHere)
                maxSumSoFar = maxSumEndingHere;
        }
        return maxSumSoFar;
    }
}
