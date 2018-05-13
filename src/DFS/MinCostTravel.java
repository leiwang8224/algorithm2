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
        System.out.println("the min cost of traveling from 0 to 2 is " + minCost(cost));
    }

//    minCost(0, N-1) = MIN { cost[0][n-1],
//            cost[0][1] + minCost(1, N-1),
//            minCost(0, 2) + minCost(2, N-1),
//        ........,
//        minCost(0, N-2) + cost[N-2][n-1] }
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

    // A recursive function to find the shortest path from
    // source 's' to destination 'd'.
    static int minCostRec(int cost[][], int s, int d)
    {
        // If source is same as destination
        // or destination is next to source
        if (s == d || s+1 == d)
            return cost[s][d];

        // Initialize min cost as direct ticket from
        // source 's' to destination 'd'.
        int min = cost[s][d];

        // Try every intermediate vertex to find minimum
        for (int i = s+1; i<d; i++)
        {
            int c = minCostRec(cost, s, i) +
                    minCostRec(cost, i, d);
            if (c < min)
                min = c;
        }
        return min;
    }

    // This function returns the smallest possible cost to
    // reach station N-1 from station 0. This function mainly
    // uses minCostRec().
    static int minCost(int cost[][])
    {
        return minCostRec(cost, 0, 2);
    }


}
