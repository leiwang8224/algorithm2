package AlgoExpert.Medium;
import java.util.*;
public class MergeOverlappingIntervals {
    // O(nlog(n)) time | O(n) space
    int[][] mergeOverlappingIntervals(int[][] intervals) {
        // sort the intervals by starting value
        int[][] sortedIntervals = intervals.clone();
        Arrays.sort(sortedIntervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> mergedIntervals = new ArrayList<>();
        int[] curInterval = sortedIntervals[0];
        mergedIntervals.add(curInterval);

        for (int[] nextInterval : sortedIntervals) {
            int curIntervalEnd = curInterval[1];
            int nextIntervalStart = nextInterval[0];
            int nextIntervalEnd = nextInterval[1];

            if (curIntervalEnd >= nextIntervalStart) {
                curInterval[1] = Math.max(curIntervalEnd, nextIntervalEnd);
            } else {
                curInterval = nextInterval;
                mergedIntervals.add(curInterval);
            }
        }
        return mergedIntervals.toArray(new int[mergedIntervals.size()][]);
    }

}
