package DP;

/**
 * Created by leiwang on 3/25/18.
 */

/**
 * Find length of the longest substring of a given string of digits,
 * such that sum of digits in the first half and second half is the same
 */
public class MaxSubstringLength {
    public static void main(String args[]) {
        String str = "8283749304344428374";
        int [][] dp = new int[str.length()][str.length()];
        System.out.println(maxSubstringLengthDP(str, dp));
    }

    private static int maxSubstringLengthDP(String str, int[][] dp) {
        int n = str.length();
        int maxLen = 0;
        // lower diagonal of matrix not used (i > j)
        for (int i = 0; i < n; i ++) {
            dp[i][i] = str.charAt(i) - '0';
        }

        for (int len = 2; len <= n; len ++) {
            // pick i and j for current subset
            for (int i = 0; i < n - len + 1; i ++) {
                // i = 0,1,2,3,... len = 2,3,4,...
                int j = i + len - 1; // end of subset
                int k = len / 2; // halfway
                dp[i][j] = dp[i][j-k] + dp[j-k+1][j]; // first half + second half

                // update if len is even
                // sums are same and len is more than maxLen
                if (len % 2 == 0 && dp[i][j-k] == dp[j-k+1][j] && len > maxLen)
                    maxLen = len;
            }
        }
        return maxLen;
    }
}
