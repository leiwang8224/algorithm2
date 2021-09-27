package Arrays;

import java.util.Comparator;

public class NonOverlappingIntervals {
    public static void main(String[] args) {
        int[][] input = new int[][]{{1,2}, {2,3}, {3,4}, {1,3}};
        int ans = eraseOverlapIntervals(input);
        System.out.println(ans);
    }

    /**
     * Actually, the problem is the same as "Given a collection of
     * intervals, find the maximum number of intervals that are
     * non-overlapping." (the classic Greedy problem: Interval Scheduling).
     * With the solution to that problem, guess how do we get the minimum
     * number of intervals to remove? : )
     *
     * Sorting Interval.end in ascending order is O(nlogn),
     * then traverse intervals array to get the maximum number of
     * non-overlapping intervals is O(n). Total is O(nlogn).
     */
    private static int eraseOverlapIntervals(int[][] intervals) {
        java.util.Arrays.sort(intervals, new intervalComparator());
        int endOfPrevInterval = intervals[0][1];
        int nonOverlappingIntervals = 1;

        for (int index = 1; index < intervals.length; index++) {
            // beginning of current interval is greater than
            // or equal to end of previous interval
            if (intervals[index][0] >= endOfPrevInterval) {
                // update end of prev interval with the current end interval
                endOfPrevInterval = intervals[index][1];
                // inc count
                nonOverlappingIntervals++;
            }
        }
        // returns the number of overlapping intervals
        // that needs to be removed
        return intervals.length - nonOverlappingIntervals;
    }

    static class intervalComparator implements Comparator<int[]> {
        public int compare(int[] a, int[] b) {
            return a[1] - b[1];
        }
    }

    public static int eraseOverlapIntervals2(int[][] intervals) {
        if(intervals == null || intervals.length == 0) return 0;
        java.util.Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

        int end = intervals[0][1];
        int count = 1;

        for(int i = 1; i < intervals.length; i++){
            if(intervals[i][0] >= end){
                end = intervals[i][1];
                count++;
            }
        }

        return intervals.length - count;
    }
}
