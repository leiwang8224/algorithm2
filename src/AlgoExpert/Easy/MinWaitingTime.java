package AlgoExpert.Easy;
import java.util.*;
public class MinWaitingTime {
    // O(nlogn) time | O(1) space where n is the number of queries
    int minWaitingTime(int[] queries) {
        Arrays.sort(queries);

        int totalWaitingTime = 0;
        for (int idx = 0; idx < queries.length; idx++) {
            int duration = queries[idx];
            int queriesLeft = queries.length - (idx+1);
            totalWaitingTime += duration * queriesLeft;
        }
        return totalWaitingTime;
    }
}
