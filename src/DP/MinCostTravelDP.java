package DP;

/**
 * Created by leiwang on 3/25/18.
 */
public class MinCostTravelDP {
    public static int cost[][] = new int[][]
            {{1, 2, 3},
             {4, 8, 5},
             {6, 2, 9}};
    public static void main (String args[]) {
        System.out.println("minCost for first " + calculateMinCost(cost));
        System.out.println("minCost for second " + calculateMinCostDP(cost,new int[cost.length][cost[0].length]));

    }

    private static int calculateMinCost(int[][] cost) {
        int dp[] = new int[cost.length];
        dp[0] = 0;
        dp[1] = cost[0][1];

        for (int i = 2; i < dp.length; i ++) {
            dp[i] = cost[0][i];
            for (int j = 1; j < i; j++) {
                if (dp[i] > dp[j] + cost[i][j])
                    dp[i] = dp[j] + cost[i][j];
                    // minCost to i = minCost to j + cost of j to i
            }
        }
        return dp[dp.length - 1];
    }

    private static int calculateMinCostDP(int[][] cost, int[][] memo) {
        memo[0][0] = cost[0][0];
        // top row
        for (int j = 1; j < memo[0].length; j ++) {
            memo[0][j] = memo[0][j-1] + cost[0][j];
        }
        // left col
        for (int i = 1; i < memo.length; i ++) {
            memo[i][0] = memo[i-1][0] + cost[i][0];
        }
        // other cells
        for (int i = 1; i < memo.length; i ++) {
            for (int j = 1; j < memo[0].length; j ++) {
                memo[i][j] = Math.min(memo[i-1][j],memo[i][j-1]) + cost[i][j];
            }
        }
        return memo[memo.length-1][memo[0].length-1];
    }
}
