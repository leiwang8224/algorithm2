package DFS;

/**
 * Created by leiwang on 3/25/18.
 */
public class MinCostTravel {
    public static int cost[][] = new int[][]{
            {1, 2, 3},
            {4, 8, 5},
            {6, 2, 9}};
    public static void main(String args[]) {
       System.out.println("the min cost of traveling from 0 to 2 is " + calculateMinCost(0,2));
    }

    //TODO I don't understand this one, output is wrong
    private static int calculateMinCost(int source, int destination) {
        if (source == destination || source == destination-1) {
            return cost[source][destination];
        }

        int minCost = cost[source][destination];
        // try every intermediate station
        for (int i = source+1; i < destination; i ++) {
            // Mincost of going from s to i
            // and Mincost of going from i to d
            int temp = calculateMinCost(source, i) +
                       calculateMinCost(i, destination);
            if (temp < minCost) {
                minCost = temp;
            }
        }
        return minCost;
    }

}
