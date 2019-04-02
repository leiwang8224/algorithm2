package DP;
//We partition a row of numbers A into at most K adjacent (non-empty) groups,
//then our score is the sum of the average of each group. What is the largest score we can achieve?
//
//Note that our partition must use every number in A, and that scores are not necessarily integers.

//Example:
//        Input:
//        A = [9,1,2,3,9]
//        K = 3
//        Output: 20
//        Explanation:
//        The best choice is to partition A into [9], [1, 2, 3], [9]. The answer is 9 + (1 + 2 + 3) / 3 + 9 = 20.
//        We could have also partitioned A into [9, 1], [2], [3, 9], for example.
//        That partition would lead to a score of 5 + 2 + 6 = 13, which is worse.

import java.util.Arrays;

public class MaxAvgSumPartition {
    final static int MAX = 10;
    static double[][] memo = new double[MAX][MAX];

    public static void main(String[] args) {
        int[] test = new int[]{9,1,2,3,9};
        System.out.println(maxAvgSumPartRecurse(test, 3));
    }

//    memo[n][k] = max(memo[n][k], score(memo, i, A, k-1) + average(i, j))
//            for all i from n-1 to 1 .
    private static double maxAvgSumPartRecurse(int[] array, int k) {
        int n = array.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += array[i];

            //storing averages from starting to each i (first column)
            memo[i+1][1] = sum / (i + 1);
        }
        return score(n, array, k);
    }

    private static double score(int n, int[] array, int k) {
        if (memo[n][k] > 0) {
            System.out.println("at top return memo[n][k] > 0 " + " n = " + n + " k = " + k);
            int i = 0;
            for (double[] row : memo) {
                System.out.println(Arrays.toString(row));
                if (i == 20 || memo[n][k] == 20)
                    break;
                i++;
            }
            return memo[n][k];
        }

        double sum = 0;
        // starting from end and go forward to the front
        for (int i = n - 1; i > 0; i--) {
            // add element to array to calculate average
            sum += array[i];
            memo[n][k] = Math.max(memo[n][k],
                                // add average to the existing
                                score(i, array, k-1) + sum / (n - i));
        }
        System.out.println("at bottom return " + " n = " + n + " k = " + k);
        for (double[] row : memo) {
            if (memo[n][k] == 20)
                break;
            System.out.println(Arrays.toString(row));
        }
        return memo[n][k];
    }

    // dp[k][i] max avg sum of first i elements partitioned into k groups
    // stage: groups
    // init:
    // dp[1][i] = avg(a[0] ~ a[i-1])
    // Transition:
    // dp[k][i] = max(dp[k-1][j] + avg(a[j+1],a[i])
    // answer dp[K][n]
    private static double largestSumOfAvgDP(int[] array, int k) {
        int n = array.length;

        // storing prefix sums
        double preSum[] = new double[n+1];
        preSum[0] = 0;

        for (int i = 0; i < n; i ++) {
            preSum[i+1] = preSum[i] + array[i];
        }

        // for each i to n storing averages
        double[] dp = new double[n];
        double sum = 0;

        for (int i = 0; i < n; i ++) {
            dp[i] = (preSum[n] - preSum[i]) / (n - i);
        }

        for (int kk = 0; kk < k - 1; kk++) {
            for (int i = 0; i < n; i++) {
                for (int j = i+1; j < n; j++) {
                    dp[i] = Math.max(dp[i], // take max of dp table [i] vs avg from j to i add table [j]
                            (preSum[j] - preSum[i]) / (j-i) + dp[j]);
                }
            }
        }
        return dp[0];
    }
}
