package DP;

// On a staircase, the i-th step has some non-negative cost cost[i] assigned (0 indexed).
//
//         Once you pay the cost, you can either climb one or two steps. You need to find
//         minimum cost to reach the top of the floor, and you can either start from the
//         step with index 0, or the step with index 1.
//
//         Example 1:
//
//         Input: cost = [10, 15, 20]
//         Output: 15
//         Explanation: Cheapest is start on cost[1], pay that cost and go to the top.

import java.util.Arrays;

public class MinCostClimbStairs {
    public static void main(String[] args) {
        int[] cost = new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        System.out.println(minCostClimbingStairs(cost));
        System.out.println(minCostClimbingStairsMemo(cost));
        System.out.println(minCostClimbingStairsDP(cost));
    }

    // recursion method
    private static int minCostClimbingStairs(int[] cost) {
        return Math.min(minClimb(cost,0), minClimb(cost,1));
    }

    private static int minClimb(int[] cost, int i) {
        // end the recursion when the index is last 2 steps
        System.out.println("recurse i = " + i + " element = " + cost[i]);
        if (i == cost.length - 1 || i == cost.length - 2) return cost[i];
        System.out.println("adding cost + 1");
        return Math.min(minClimb(cost, i+1), minClimb(cost,i+2)) + cost[i];
    }

    // memo method
    private static int minCostClimbingStairsMemo (int[] cost) {
        int[] memo = new int[cost.length];
        findMin(cost, memo, 0);
        return Math.min(memo[0],memo[1]);
    }

    private static int findMin(int[] cost, int[] memo, int i) {
        if (i == cost.length -1 || i == cost.length - 2) return cost[i];

        if (memo[i] > 0) return memo[i];

        int min1 = findMin(cost, memo, i + 1);
        int min2 = findMin(cost, memo, i + 2);
        memo[i] = Math.min(min1, min2) + cost[i];
        return memo[i];
    }

    // finally the DP version
    private static int minCostClimbingStairsDP(int[] cost) {
        if (cost == null || cost.length == 0) return 0;

        if (cost.length == 1) return cost[0];

        int[] dp = new int[cost.length];

        // initialize with the first and second step costs
        dp[0] = cost[0]; dp[1] = cost[1];
        for (int i = 2; i < dp.length; i++) {
            dp[i] = Math.min(dp[i - 2], dp[i - 1]) + cost[i];
        }

        System.out.println(Arrays.toString(dp));
        return Math.min(dp[dp.length - 1], dp[dp.length - 2]);
    }

    
}
