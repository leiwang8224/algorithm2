package Arrays;

//Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
//
//        You may assume that the intervals were initially sorted according to their start times.
//
//        Example 1:
//
//        Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
//        Output: [[1,5],[6,9]]
//
//        Example 2:
//
//        Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,9]
//        Output: [[1,2],[3,10],[12,16]]
//        Explanation: Because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].
//

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InsertIntervals {
    public static void main(String[] args) {
        Interval interval1 = new Interval(1,2);
        Interval interval2 = new Interval(3,5);
        Interval interval3 = new Interval(6,7);
        Interval interval4 = new Interval(8,10);
        Interval interval5 = new Interval(12,16);
        Interval addInterval = new Interval(4,9);

        List<Interval> list = new ArrayList<>();
        list.add(interval1);
        list.add(interval2);
        list.add(interval3);
        list.add(interval4);
        list.add(interval5);

        System.out.println(java.util.Arrays.toString(insert(list, addInterval).toArray()));
        System.out.println(java.util.Arrays.toString(insert2(list, addInterval).toArray()));

    }

    public static class Interval {
        int start;
        int end;
        Interval() {
            start = 0;
            end = 0;
        }
        Interval(int s, int e) {
            start = s;
            end = e;
        }
        @Override
        public String toString() {
            return start + "," + end;
        }
    }

    private static List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        System.out.println("intervals = " + Arrays.toString(intervals.toArray()));
        System.out.println("new interval = " + newInterval.toString());
        List<Interval> result = new LinkedList<>();
        int i = 0;
        // add all the intervals ending before newInterval starts
        // new interval start > intervals.get(i).end
        while (i < intervals.size() && intervals.get(i).end < newInterval.start)
            result.add(intervals.get(i++));
        System.out.println("add all intervals ending before new interval starts " + java.util.Arrays.toString(result.toArray()));
        // merge all overlapping intervals to one considering newInterval
        while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
            // expand the min and max to the encompass all of the ranges
            // this performs a UNION of the intervals
            newInterval = new Interval( // we could mutate newInterval here also
                                        Math.min(newInterval.start, intervals.get(i).start),
                                        // start = min(newInterval.start, intervals.get(i).start)
                                        Math.max(newInterval.end, intervals.get(i).end));
                                        // end = max(newInterval.end, intervals.get(i).end)
            i++;
        }

        result.add(newInterval); // add the union of intervals we got
        System.out.println("after adding the new interval " + java.util.Arrays.toString(result.toArray()));
        // add all the rest (end of all the intervals)
        while (i < intervals.size()) result.add(intervals.get(i++));
        return result;
    }

    private static List<Interval> insert2(List<Interval> intervals, Interval newInterval) {
        List<Interval> result = new ArrayList<Interval>();
        for (Interval i : intervals) {
            // for each interval that ends before new interval, add interval to result
            if (newInterval == null || i.end < newInterval.start) {
                result.add(i);
            }
            // for each interval that starts after new interval ends, add new interval to result
            // then add the interval
            // set new interval to null
            else if (i.start > newInterval.end) {
                result.add(newInterval);
                result.add(i);
                newInterval = null;
            } else {
                // perform UNION on the intervals
                // else new interval start = min(newInterval.start, interval.start)
                // new interval end = max(newInterval.end, interval.end)
                newInterval.start = Math.min(newInterval.start, i.start);
                newInterval.end = Math.max(newInterval.end, i.end);
            }
        }
        if (newInterval != null)
            result.add(newInterval);
        return result;
    }

}
