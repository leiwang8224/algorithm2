package DP;

/**
 * Created by leiwang on 4/24/18.
 */

/**
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

 The robot can only move either down or right at any point in time. The robot is trying to reach
 the bottom-right corner of the grid (marked 'Finish' in the diagram below).

 How many possible unique paths are there?
 */
public class TotalPathCount {
    public static void main(String[] args) {
        System.out.println(numOfPaths(2,3));
        System.out.println(numOfPathsDP(2, 3));

    }

    private static int numOfPaths(int m, int n) {
        if (m== 0 && n == 0) return 0; // cell (0,0)
        if (m== 0 || n == 0) return 1; // first row/col
        return numOfPaths(m-1, n) + numOfPaths(m,n-1);
    }

    private static int numOfPathsDP(int m, int n) {
        int dp[][] = new int[m+1][n+1];
        for (int row = 1; row <= m; row++) {
            dp[row][0] = 1;
        }
        for (int col = 1; col <= n; col++) {
            dp[0][col] = 1;
        }

        // populating other cells
        for (int row = 1; row <= m; row++) {
            for (int col = 1; col <= n; col++) {
                dp[row][col] = dp[row-1][col] + dp[row][col-1];
            }
        }
        for (int[] row : dp)
            System.out.println(java.util.Arrays.toString(row));
        return dp[m][n];
    }
}
