package Arrays;

import java.util.PriorityQueue;

/**
 * Created by leiwang on 4/13/18.
 */
/*
You have k lists of sorted integers in ascending order.
Find the smallest range that includes at least one number from each of the k lists.

We define the range [a,b] is smaller than range [c,d] if b-a < d-c or a < c if b-a == d-c.

 */
public class SmallestRange {
//    Input:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
//    Output: [20,24]
//    Explanation:
//    List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
//    List 2: [0, 9, 12, 20], 20 is in range [20,24].
//    List 3: [5, 18, 22, 30], 22 is in range [20,24].
    public static void main(String[] args) {
        int[][] nums = new int[][]{{4,10,15,24,26}, {0,9,12,20}, {5,18,22,30}};
        System.out.println(java.util.Arrays.toString(smallestRange(nums)));
        System.out.println(java.util.Arrays.toString(smallestRange2(nums)));
        System.out.println(java.util.Arrays.toString(smallestRange3(nums)));
        System.out.println(java.util.Arrays.toString(smallestRange4(nums)));


    }

//    The naive approach is to consider every pair of elements,
//    nums[i][j]nums[i][j] and nums[k][l]nums[k][l] from amongst the given lists and
//    consider the range formed by these elements. For every range currently considered,
//    we can traverse over all the lists to find if atleast one element from these lists
//    can be included in the current range. If so, we store the end-points of the current
//    range and compare it with the previous minimum range found, if any, satisfying the
//    required criteria, to find the smaller range from among them.
//
//    Once all the element pairs have been considered as the ranges, we can obtain the required minimum range.
    // Time O(n3)
    // Space O(1)
    private static int[] smallestRange(int[][] nums) {
        int minx = 0, miny = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                for (int k = i; k < nums.length; k++) {
                    for (int l = (k == i ? j : 0); l < nums[k].length; l++) {
                        int min = Math.min(nums[i][j], nums[k][l]);
                        int max = Math.max(nums[i][j], nums[k][l]);
                        int n, m;
                        for (m = 0; m < nums.length; m++) {
                            for (n = 0; n < nums[m].length; n++) {
                                if (nums[m][n] >= min && nums[m][n] <= max)
                                    break;
                            }
                            if (n == nums[m].length)
                                break;
                        }
                        if (m == nums.length) {
                            if (miny - minx > max - min || (miny - minx == max - min && minx > min)) {
                                miny = max;
                                minx = min;
                            }
                        }
                    }
                }
            }
        }
        return new int[] {minx, miny};
    }

    //    In the last approach, we consider every possible range and then traverse over every list to check if
    //    atleast one of the elements from these lists lies in the required range. Instead of doing this
    //    traversal for every range, we can make use of Binary Search to find the index of the element just
    //    larger than(or equal to) the lower limit of the range currently considered.
    //
    //    If all the elements in the current list are lesser than this lower limit, we'll get the index as
    //    nums[k].lengthnums[k].length for the k^{th}k​th
    //    ​​list being currently checked. In this case, none of the elements of the current list lies in the current range.
    //
    //    On the other hand, if all the elements in this list are larger than this lower limit,
    //    we'll get the index of the first element(minimum) in the current list. If this element happens to be
    //    larger than the upper limit of the range currently considered, then also, none of the elements of the
    //    current list lies within the current range.
    //
    //    Whenever a range is found which satisfies the required criteria, we can compare it with the minimum range
    //    found so far to determine the required minimum range.
    // Time: O(N2log(k))
    // Space: O(1)
    private static int[] smallestRange2(int[][] nums) {
        int minx = 0, miny = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                for (int k = i; k < nums.length; k++) {
                    for (int l = (k == i ? j : 0); l < nums[k].length; l++) {
                        int min = Math.min(nums[i][j], nums[k][l]);
                        int max = Math.max(nums[i][j], nums[k][l]);
                        int n, m;
                        for (m = 0; m < nums.length; m++) {
                            n = java.util.Arrays.binarySearch(nums[m], min);
                            if (n < 0)
                                n = -1 - n;
                            if (n == nums[m].length || nums[m][n] < min || nums[m][n] > max)
                                break;
                        }
                        if (m == nums.length) {
                            if (miny - minx > max - min || (miny - minx == max - min && minx > min)) {
                                miny = max;
                                minx = min;
                            }
                        }
                    }
                }
            }
        }
        return new int[] {minx, miny};
    }

//
//    We'll discuss about the implementation used in the current approach along with the idea behind it.
//    This approach makes use of an array of pointers, nextnext, whose length is equal to the number of given lists.
//    In this array, next[i]next[i] refers to the element which needs to be considered next in the (i-1)^{th}(i−1)​th
//    ​​list. The meaning of this will become more clearer when we look at the process.
//
//    We start by initializing all the elements of nextnext to 0. Thus, currently, we are considering the first(minimum)
//    element among all the lists. Now, we find out the index of the list containing the maximum(max_imaxi
//    ​​ ) elements from amongst the elements currently pointed by nextnext. The range formed by these maximum and minimum
//    elements surely contains atleast one element from each list.
//
//    But, now our objective is to minimize this range. To do so, there are two options: Either decrease the maximum
//    value or increase the minimum value.
//
//    Now, the maximum value can't be reduced any further, since it already corresponds to the minimum value
//    in one of the lists. Reducing it any further will lead to the exclusion of all the elements of this
//    list(containing the last maximum value) from the new range.
//
//    Thus, the only option left in our hand is to try to increase the minimum value. To do so, we now need to
//    consider the next element in the list containing the last minimum value. Thus, we increment the entry at
//    the corresponding index in nextnext, to indicate that the next element in this list now needs to be considered.
//
//    Thus, at every step, we find the maximum and minimum values being pointed currently, update the nextnext
//    values appropriately, and also find out the range formed by these maximum and minimum values to find out the
//    smallest range satisfying the given criteria.
//
//    While doing this process, if any of the lists gets completely exhausted, it means that the minimum value
//    being increased for minimizing the range being considered can't be increased any further, without causing
//   the exclusion of all the elements in atleast one of the lists. Thus, we can stop the search process whenever any
//    list gets completely exhausted.
//
//    We can also stop the process, when all the elements of the given lists have been exhausted.
//
//    Summarizing the statements above, the process becomes:
//
//    1. Initialize nextnext array(pointers) with all 0's.
//
//    2. Find the indices of the lists containing the minimum(min_imin​i
//                                                                 ​​ ) and the maximum(max_imax
//    ​i
//    ​​ ) elements amongst the elements pointed by the nextnext array.
//
//    If the range formed by the maximum and minimum elements found above is larger than the previous maximum range,
//    update the boundary values used for the maximum range.
//
//    Increment the pointer nums[min_i]nums[min
//    ​i
//    ​​ ].
//
//    Repeat steps 2 to 4 till any of the lists gets exhausted.
//
//    The animation below illustrates the process for a visual understanding of the process.
    // Time O(n*m)
    // Space O(m)
    private static int[] smallestRange3(int[][] nums) {
        int minx = 0, miny = Integer.MAX_VALUE;
        int[] next = new int[nums.length];
        boolean flag = true;
        for (int i = 0; i < nums.length && flag; i++) {
            for (int j = 0; j < nums[i].length && flag; j++) {
                int min_i = 0, max_i = 0;
                for (int k = 0; k < nums.length; k++) {
                    if (nums[min_i][next[min_i]] > nums[k][next[k]])
                        min_i = k;
                    if (nums[max_i][next[max_i]] < nums[k][next[k]])
                        max_i = k;
                }
                if (miny - minx > nums[max_i][next[max_i]] - nums[min_i][next[min_i]]) {
                    miny = nums[max_i][next[max_i]];
                    minx = nums[min_i][next[min_i]];
                }
                next[min_i]++;
                if (next[min_i] == nums[min_i].length) {
                    flag = false;
                }
            }
        }
        return new int[] {minx, miny};
    }

//    In the last approach, at each step, we update the pointer corresponding to the current minimum element and
//    traverse over the whole nextnext array to determine the new maximum and minimum values. We can do some
//    optimization here, by making use of a simple observation.
//
//    Whenever we update a single entry of nextnext to consider the new maximum and minimum values(if we already
//    know the last maximum and minimum values), all the elements to be considered for finding the maximum and
//    minimum values remain the same except the new element being pointed by a single updated entry in nextnext.
//    This new entry is certainly larger than the last minimum value(since that was the reasoning behind the updation).
//
//    Thus, we can't be sure whether this is the new minimum element or not. But, since it is larger than the
//    last value being considered, it could be a potential competitor for the new maximum value. Thus, we can
//    directly compare it with the last maximum value to determine the current maximum value.
//
//    Now, we're left with finding the minimum value iteratively at every step. To avoid this iterative process,
//    a better idea is to make use of a Min-Heap, which stores the values being pointed currently by the nextnext array.
//    Thus, the minimum value always lies at the top of this heap, and we need not do the iterative search process.
//
//    At every step, we remove the minimum element from this heap and find out the range formed by the current
//    maximum and minimum values, and compare it with the minimum range found so far to determine the required minimum
//    range. We also update the increment the index in nextnext corresponding to the list containing this minimum
//    entry and add this element to the heap as well.
//
//    The rest of the process remains the same as the last approach.
    // Time: O(N * log(M))
    // Space: O(M)
    private static int[] smallestRange4(int[][] nums) {
        int minx = 0, miny = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int[] next = new int[nums.length];
        boolean flag = true;
        PriorityQueue< Integer > min_queue = new PriorityQueue < Integer > ((i, j) -> nums[i][next[i]] - nums[j][next[j]]);
        for (int i = 0; i < nums.length; i++) {
            min_queue.offer(i);
            max = Math.max(max, nums[i][0]);
        }
        for (int i = 0; i < nums.length && flag; i++) {
            for (int j = 0; j < nums[i].length && flag; j++) {
                int min_i = min_queue.poll();
                if (miny - minx > max - nums[min_i][next[min_i]]) {
                    minx = nums[min_i][next[min_i]];
                    miny = max;
                }
                next[min_i]++;
                if (next[min_i] == nums[min_i].length) {
                    flag = false;
                    break;
                }
                min_queue.offer(min_i);
                max = Math.max(max, nums[min_i][next[min_i]]);
            }
        }
        return new int[] { minx, miny};
    }
}
