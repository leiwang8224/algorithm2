package DP;

/**
 * Created by leiwang on 3/25/18.
 */

/**
 * Uses memoization
 */
public class MinCostTravel {
    private static int cost[][] = new int[][]{  {1, 2, 3},
                                                {4, 8, 5},
                                                {6, 2, 9}};
    public static void main (String args[]) {
        System.out.println("the min cost of traveling from 2 to 2 is " + calculateMinCost(2,2));
//        System.out.println(minCost(cost));
//        System.out.println(minCostDP(cost));
//        System.out.println(minCostRec(cost,2,2));

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

    private static int minCost(int cost[][]){
        return minCostRec(cost, 0, N-1);
    }

    static int INF = Integer.MAX_VALUE, N = 4;
    private static int minCostRec(int cost[][], int source, int destination) {
        // if source is the same as dest
        // or dest is next to source
        if (source == destination || source + 1 == destination)
            return cost[source][destination];

        // init min cost as direct ticket from
        // source to destination
        int min = cost[source][destination];

        // try every intermediate vertex to find min
        for (int i = source + 1; i < destination; i++) {
            int c = minCostRec(cost, source, i) + minCostRec(cost, i, destination);
            if (c < min)
                min = c;
        }
        return min;
    }

    private static int minCostDP(int cost[][]) {
        // dist[i] stores the minimum cost to reach station i
        // from station 0
        int dist[] = new int[N];
        for (int i = 0; i < N; i ++) {
            dist[i] = INF;
        }
        dist[0] = 0;

        // go through every station and check if using it
        // as an intermediate station and gives better path
        for (int i = 0; i < N; i ++) {
            for (int j = i + 1; j < N; j ++) {
                if (dist[j] > dist[i] + cost[i][j])
                    dist[j] = dist[i] + cost[i][j];
            }
        }

        return dist[N-1];
    }
}
