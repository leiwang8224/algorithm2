package DP;

/**
 * Created by leiwang on 3/25/18.
 */
public class WaysToWin {
    public static void main(String args[]) {
        System.out.println(waysToWin(13));
    }

    /**
     * Consider a game where the player can score 3, 5 or 10
     * points in one move. Given total score N, find number of ways
     * to reach N.
     */
    private static int waysToWin(int n) {
        int dp[] = new int[n+1];
        dp[0] = 1;
        for (int i = 1; i <= n; i ++) {
            // add the score to the array element
            if (i - 3 >= 0) {
                dp[i] += dp[i-3];
            }
            if (i - 5 >= 0) {
                dp[i] += dp[i-5];
            }
            if (i - 10 >= 0) {
                dp[i] += dp[i-10];
            }
        }
        return dp[n];
    }
}
