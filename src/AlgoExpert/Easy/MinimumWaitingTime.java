package AlgoExpert.Easy;

public class MinimumWaitingTime {
    public static void main(String[] args) {

    }

    // O(nlogn) time | O(1) space where n is number of queries
    private static int minWaitingTime(int[] queries) {
        // it's necessary to sort from smallest to largest
        // as putting the smallest queries in the beginning will ensure
        // shortest time to reach the last query
        java.util.Arrays.sort(queries);


        // think of it like when reaching query[i], you have n-i queries
        // left, all of which will have to wait query[i] time, therefore
        // total wait time += duration for current query * queries left
        int totalWaitingTime = 0;
        for (int idx = 0; idx < queries.length; idx++) {
            int duration = queries[idx];
            int quriesLeft = queries.length - (idx+1);
            totalWaitingTime += duration * quriesLeft;
        }

        return totalWaitingTime;
    }
}
