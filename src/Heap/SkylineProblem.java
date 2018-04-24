package Heap;

import java.util.*;

/**
 * Created by leiwang on 4/1/18.
 */
//https://leetcode.com/problems/the-skyline-problem/discuss/61192/Once-for-all-explanation-with-clean-Java-code(O(n2)time-O(n)-space)
//for position in sorted(all start points and all end points)
//        if this position is a start point
//        add its height
//        else if this position is a end point
//        delete its height
//        compare current max height with previous max height, if different, add
//        current position together with this new max height to our result, at the
//        same time, update previous max height to current max height;
public class SkylineProblem {
    public static void main(String args[]) {
        int[][] nums = new int[][] {{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}};
        List<int[]> list = getSkyline(nums);
        for (int[] integers : list) {
            java.util.Arrays.toString(integers);
        }
        //TODO not working, output nothing
    }

    public static List<int[]> getSkyline(int[][] buildings) {
        List<int[]> result = new ArrayList<>();
        List<int[]> height = new ArrayList<>();
        for(int[] b:buildings) {
            // start point has negative height values
            height.add(new int[]{b[0], -b[2]});
            // end point has normal height values
            height.add(new int[]{b[1], b[2]});
        }

        // sort height based on first value
        // use second to break ties
        Collections.sort(height, (a, b) -> {
            if (a[0] != b[0])
                return a[0] - b[0];
            return a[1] - b[1];
        });

        // use maxHeap to store heights
        Queue<Integer> pq = new PriorityQueue<>((a, b) -> (b - a));

        // provide initial value to make it more consistent
        pq.offer(0);

        // before starting the previous max height value is 0
        int prev = 0;

        // visit all points in order
        for(int[] h:height) {
            if(h[1] < 0) {  // a start point, add height
                pq.offer(-h[1]);
            } else {
                pq.remove(h[1]); // a end point, remove height
            }
            int cur = pq.peek(); // current max height

            // compare current max height with previous max
            // height, update previous max height if necessary
            if(prev != cur) {
                result.add(new int[]{h[0], cur});
                prev = cur;
            }
        }
        return result;
    }

}
