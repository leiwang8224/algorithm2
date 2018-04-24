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
        System.out.println("result 1");
        for (int[] integers : list) {
            System.out.println(Arrays.toString(integers));
        }
        System.out.println("result 2");
        List<int[]> list2 = getSkyline2(nums);
        for (int[] integers : list2) {
            System.out.println(Arrays.toString(integers));
        }
        System.out.println("result 3");
        List<int[]> list3 = getSkyline3(nums);
        for (int[] integers : list3) {
            System.out.println(Arrays.toString(integers));
        }
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

//      1. we can visit all points in order;
//      2. when points have the same value, higher height will shadow the lower one;
//      3. we know whether current point is a start point or a end point based on the
//         sign of its height;
    public static List<int[]> getSkyline2(int[][] buildings) {
        List<int[]> result = new ArrayList<>();
        List<int[]> height = new ArrayList<>();
        for(int[] b:buildings) {
            // start point has negative height value
            height.add(new int[]{b[0], -b[2]});
            // end point has normal height value
            height.add(new int[]{b[1], b[2]});
        }

        // sort $height, based on the first value, if necessary, use the second to
        // break ties
        Collections.sort(height, (a, b) -> {
            if(a[0] != b[0])
                return a[0] - b[0];
            return a[1] - b[1];
        });

        // Use a maxHeap to store possible heights
        Queue<Integer> pq = new PriorityQueue<>((a, b) -> (b - a));

        // Provide a initial value to make it more consistent
        pq.offer(0);

        // Before starting, the previous max height is 0;
        int prev = 0;

        // visit all points in order
        for(int[] h:height) {
            if(h[1] < 0) { // a start point, add height
                pq.offer(-h[1]);
            } else {  // a end point, remove height
                pq.remove(h[1]);
            }
            int cur = pq.peek(); // current max height;

            // compare current max height with previous max height, update result and
            // previous max height if necessary
            if(prev != cur) {
                result.add(new int[]{h[0], cur});
                prev = cur;
            }
        }
        return result;
    }

    public static List<int[]> getSkyline3(int[][] buildings) {
        if (buildings.length == 0)
            return new LinkedList<int[]>();
        return recurSkyline(buildings, 0, buildings.length - 1);
    }

    private static LinkedList<int[]> recurSkyline(int[][] buildings, int p, int q) {
        if (p < q) {
            int mid = p + (q - p) / 2;
            return merge(recurSkyline(buildings, p, mid),
                         recurSkyline(buildings, mid + 1, q));
        } else {
            LinkedList<int[]> rs = new LinkedList<int[]>();
            rs.add(new int[] { buildings[p][0], buildings[p][2] });
            rs.add(new int[] { buildings[p][1], 0 });
            return rs;
        }
    }

    private static LinkedList<int[]> merge(LinkedList<int[]> l1, LinkedList<int[]> l2) {
        LinkedList<int[]> rs = new LinkedList<int[]>();
        int h1 = 0, h2 = 0;
        while (l1.size() > 0 && l2.size() > 0) {
            int x = 0, h = 0;
            if (l1.getFirst()[0] < l2.getFirst()[0]) {
                x = l1.getFirst()[0];
                h1 = l1.getFirst()[1];
                h = Math.max(h1, h2);
                l1.removeFirst();
            } else if (l1.getFirst()[0] > l2.getFirst()[0]) {
                x = l2.getFirst()[0];
                h2 = l2.getFirst()[1];
                h = Math.max(h1, h2);
                l2.removeFirst();
            } else {
                x = l1.getFirst()[0];
                h1 = l1.getFirst()[1];
                h2 = l2.getFirst()[1];
                h = Math.max(h1, h2);
                l1.removeFirst();
                l2.removeFirst();
            }
            if (rs.size() == 0 || h != rs.getLast()[1]) {
                rs.add(new int[] { x, h });
            }
        }
        rs.addAll(l1);
        rs.addAll(l2);
        return rs;
    }

}
