package Others;

import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

//We have a list of points on the plane.  Find the K closest points to the origin (0, 0).
//
//        (Here, the distance between two points on a plane is the Euclidean distance.)
//
//        You may return the answer in any order.  The answer is guaranteed to be unique
//        (except for the order that it is in.)
//
//        Example 1:
//
//        Input: points = [[1,3],[-2,2]], K = 1
//        Output: [[-2,2]]
//        Explanation:
//        The distance between (1, 3) and the origin is sqrt(10).
//        The distance between (-2, 2) and the origin is sqrt(8).
//        Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
//        We only want the closest K = 1 points from the origin, so the answer is just [[-2,2]].

public class KClosestToOrigin {
    public static void main(String[] args) {
        int[][] points = new int[][] {{0,1}, {1,0}};

        int[][] result = kClosest(points,2);
        int[][] result2 = kClosest2(points,2);
        int[][] result3 = kClosestQuickSort(points, 2);
        int[][] result4 = kClosest3(points,2);

        for (int[] r : result) {
            System.out.println(Arrays.toString(r));
        }

        for (int[] r : result2) {
            System.out.println(Arrays.toString(r));
        }

        for (int[] r : result3) {
            System.out.println(Arrays.toString(r));
        }

        for (int[] r : result4) {
            System.out.println(Arrays.toString(r));
        }
        

    }

    //TODO does not work since hashmap does not provide distinct keys!! (ex: sum could be the same with different coordinates)
    private static int[][] kClosest(int[][] pts, int K) {
        int[][] result = new int[K][2];
        double[] sums = new double[pts.length];

        HashMap<Double, int[]> map = new HashMap<>();

        int index = 0;
        for (int[] point : pts) {
            sums[index] = Math.sqrt(point[0] * point[0] + point[1] * point[1]);
            map.put(sums[index], new int[]{point[0], point[1]});
            index++;
        }

        for (Double key : map.keySet()) {
            System.out.println(key + " " + Arrays.toString(map.get(key)));
        }

        Arrays.sort(sums);

        for (int i = 0; i < K; i++) {
            result[i] = map.get(sums[i]);
        }

        return result;
    }

    // O(N log (N))
//    The advantages of this solution are short, intuitive and easy to implement.
//    The disadvatages of this solution are not very efficient and have to know
    //all of the points previously, and it is unable to deal with real-time(online)
    //case, it is an off-line solution.
    private static int[][] kClosest2(int[][] pts, int K) {
        int N = pts.length;
        int[] dists = new int[N];

        for (int i = 0; i < N; i++)
            dists[i] = dist(pts[i]);

        // sort distance then retrieve
        // calculate dist again to get the coordinates
        Arrays.sort(dists);
        int distK = dists[K-1];

        int[][] result = new int[K][2];

        int t = 0;

        for (int i = 0; i < N; i++) {
            // calculating dist second time
            if (dist(pts[i]) <= distK) {
                result[t++] = pts[i];
            }
        }

        return result;
    }

    private static int dist(int[] pt) {
        return pt[0] * pt[0] + pt[1] * pt[1];
    }

    // O(N) because quick select complexity is O(N)
//    The advantage of this solution is it is very efficient.
//    The disadvatage of this solution are it is neither
    //    an online solution nor a stable one. And the K
    //    elements closest are not sorted in ascending order.
    private static int[][] kClosestQuickSort(int[][] pts, int K) {
        int len = pts.length;
        int l = 0;
        int r = len = 1;

        while (l <= r) {
            int mid = helper(pts, l, r);
            if (mid == K) break;
            if (mid < K) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return Arrays.copyOfRange(pts, 0, K);
    }

    private static int helper(int[][] pts, int l, int r) {
        int[] pivot = pts[l];
        while ( l < r) {
            // l < r and right point > pivot
            while(l < r && compare(pts[r], pivot) >= 0) r--;
            pts[l] = pts[r];

            // l < r and left point < pivot
            while(l < r && compare(pts[l], pivot) <= 0) l++;
            pts[r] = pts[l];
        }
        pts[l] = pivot;
        return l;
    }

    private static int compare(int[] pt1, int[] pt2) {
        return pt1[0] * pt1[0] + pt1[1] * pt1[1] - pt2[0] * pt2[0] - pt2[1] * pt2[1];
    }

//    The advantage of this solution is it can deal with real-time(online) stream
//    data. It does not have to know the size of the data previously.
//    The disadvatage of this solution is it is not the most efficient solution.
    private static int[][] kClosest3(int[][] points, int K) {
        PriorityQueue<int[]>
                pq = new PriorityQueue<int[]>((p1, p2) ->
                                                      p2[0] * p2[0] +
                                                      p2[1] * p2[1] -
                                                      p1[0] * p1[0] -
                                                      p1[1] * p1[1]);

        for (int[] p : points) {
            pq.offer(p);
            if (pq.size() > K) {
                pq.poll();
            }
        }

        int[][] result = new int[K][2];

        while (K > 0) {
            result[--K] = pq.poll();
        }

        return result;

    }
}
