package DP;

/**
 * Created by leiwang on 3/25/18.
 */

/**
 * Uses memoization
 */
public class MinCostTravel {
    private static int cost[][] = new int[][]{{1, 2, 3},
            {4, 8, 5},
            {6, 2, 9}};
    public static void main (String args[]) {
        System.out.println("the min cost of traveling from 2 to 2 is " + calculateMinCost(2,2));


    }

    private static int calculateMinCost(int source, int destination) {
        int memo[][] = new int[cost.length][cost[0].length];
        if (source == destination || source == destination - 1) {
            return cost[source][destination];
        }

        if (memo[source][destination] == 0) {
            int minCost = cost[source][destination];
            for (int i = source + 1; i < destination; i ++) {
                int temp = calculateMinCost(source, i) + calculateMinCost(i, destination);
                if (temp < minCost) {
                    minCost = temp;
                }
            }
            // store minCost in cache
            memo[source][destination] = minCost;
        }
        return memo[source][destination];
    }

}
