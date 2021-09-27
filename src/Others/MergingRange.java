package Others;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// A Range Module is a module that tracks ranges of numbers. Range modules
// are used extensively when designing scalable online game maps with
// millions of players. Your task is to write a method - mergeIntervals
// that takes in an ArrayList of integer Interval s (aka ranges), and
// returns an ArrayList of sorted Interval s where all overlapping
// intervals have been merged. The Interval class is available by clicking Use Me.
//
//        Note:
//        a) [1,3] represents an interval that includes 1, 2 and 3.
//        b) Intervals should be sorted based on the value of start
//
//        Examples:
//        Input: [ [1,3], [2,5] ], Output: [ [1,5] ]
//        Input: [ [3,5], [1,2] ], Output: [ [1,2], [3,5] ]
public class MergingRange {
    public static void main(String[] args) {
        Interval interval1 = new Interval(0,3);
        Interval interval2 = new Interval(2,4);
        Interval interval3 = new Interval(5,6);
        Interval interval4 = new Interval(7,10);
        Interval interval5 = new Interval(1,10);
        ArrayList<Interval> intervals = new ArrayList<>();
        intervals.add(interval1);
        intervals.add(interval2);
        intervals.add(interval3);
        intervals.add(interval4);
        intervals.add(interval5);

        ArrayList<Interval> result = mergeIntervals(intervals);

        for (Interval res : result) {
            System.out.println(res.toString());
        }

    }

    private static int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) return intervals;

        // sort by ascending starting point
        java.util.Arrays.sort(intervals, (i1, i2) -> Integer.compare(i1[0], i2[0]));

        java.util.List<int[]> result = new ArrayList<>();
        int[] newInterval = intervals[0];
        result.add(newInterval);
        for (int[] interval : intervals) {
            if (interval[0] <= newInterval[1]) // overlapping intervals, move the end if needed
                newInterval[1] = Math.max(newInterval[1], interval[1]);
            else {                              // disjoint intervals, add the new interval to the list
                newInterval = interval;
                result.add(newInterval);
            }
        }
        return result.toArray(new int[result.size()][]);
    }

    public static ArrayList<Interval> mergeIntervals(ArrayList<Interval> intervalsList) {
        if (intervalsList == null || intervalsList.size() < 2) {
            return intervalsList;
        } else {
            ArrayList<Interval> out = new ArrayList<>();

            // first sort by the starting point of the interval
            Collections.sort(intervalsList, new Comparator<Interval>() {
                @Override
                public int compare(Interval interval1, Interval interval2) {
                    return Integer.compare(interval1.start, interval2.start);
                }
            });

            Interval prev = intervalsList.get(0);
            for (int i = 1; i < intervalsList.size(); i++) {
                Interval cur = intervalsList.get(i);

                // important to check the start of current and end of prev
                if (cur.start <= prev.end) {
                    // it's overlapping, so merge (basically update the prev)
                    // use prev.start as start since it's sorted based on start
                    // then end on the max end
                    prev = new Interval(prev.start, Math.max(cur.end, prev.end));
                } else {
                    // otherwise add and update curInterval
                    // the next element is starting at another point outside of
                    // the previous interval - not overlapping
                    out.add(prev);  // add prev, not add cur
                    prev = cur; // update the prev with cur
                }
            }
            // remember to add the last remaining prev (cur)
            out.add(prev);
            return out;
        }
    }

    private static class Interval {
        int start;
        int end;
        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return start + "," + end;
        }
    }

}

