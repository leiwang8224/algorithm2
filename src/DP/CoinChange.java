package DP;

/**
 * Created by leiwang on 3/26/18.
 */
public class CoinChange {
    public static void main(String args[]) {
        int[] coins = new int[]{1,5,10,25};
        System.out.println(findCoinChange(coins, 16));
    }

    private static int findCoinChange(int[] coins, int target) {
        if (target < 1) return 0;
        return coinChange(coins, target, new int[target]);

    }

    private static int coinChange(int[] coins, int target, int[] dp) {
        if (target < 0) return -1;
        if (target == 0) return 0;
        if (dp[target-1] != 0) return dp[target-1];

        int min = Integer.MAX_VALUE;

        for (int coin : coins) {
            int res = coinChange(coins, target - coin, dp);
            if (res >= 0 && res < min) {
                min = 1 + res;
            }
        }
        dp[target - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
        return dp[target -1];
    }
}
