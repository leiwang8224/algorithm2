package DP;

/**
 * Created by leiwang on 3/25/18.
 */
/**
 There are N stations on route of a train. The train goes from station 0 to N-1.
 The ticket cost for all pair of stations (i, j) is given where j is greater than i.
 Find the minimum cost to reach the destination.
 **/

public class MinCostTravelDPRecurse {
    public static void main(String args[]) {
        int cost[][] = new int[][]{
                {1, 2, 3},
                {4, 8, 5},
                {6, 2, 9}};

        int memo[][] = new int[cost.length][cost[0].length];
        System.out.println("first minPathCost = " + minPathCost(cost, memo, 0, 2));
        System.out.println("second minPathCostDFS = " + minPathCostDFS(cost, 0, 2));
    }

    /**
     * Min path cost with memo
     * @param cost
     * @param memo
     * @param row
     * @param col
     * @return
     */
    private static int minPathCost(int[][] cost, int[][] memo, int row, int col) {
        if (memo[row][col] != 0)
            return memo[row][col];

        if (row == 0 && col == 0) {
            memo[row][col] = cost[row][col];
        } else if (row == 0) {// first row
            memo[row][col] = minPathCost(cost,memo,row,col-1) + cost[0][col];
        } else if (col == 0) {// first col
            memo[row][col] = minPathCost(cost, memo, row - 1, col) + cost[row][0];
        } else {
            int x = minPathCost(cost, memo, row - 1, col);
            int y = minPathCost(cost, memo, row, col - 1);
            memo[row][col] = Math.min(x,y) + cost[row][col];
        }
        return memo[row][col];
    }

    /**
     * Min path cost with DFS
     * @param cost
     * @param row
     * @param col
     * @return
     */
    private static int minPathCostDFS(int[][] cost, int row, int col) {
        if (row == 0 && col == 0)
            return cost[row][col];
        if (row == 0) // first row
            return minPathCostDFS(cost, row, col-1) + cost[0][col];
        if (col == 0) // first col
            return minPathCostDFS(cost, row -1, col) + cost[row][0];
        int x = minPathCostDFS(cost, row -1, col);
        int y = minPathCostDFS(cost, row, col -1);
        return Math.min(x,y) + cost[row][col];
    }
}
