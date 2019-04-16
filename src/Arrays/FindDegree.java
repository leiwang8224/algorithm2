package Arrays;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leiwang on 4/13/18.
 */


    /*
Problem Statement:
Given a non-empty array of non-negative integers nums, the degree of this
array is defined as the maximum frequency of any one of its elements.

Your task is to find the smallest possible length of a (contiguous) subarray
of nums, that has the same degree as nums.

Example 1:

Input: [1, 2, 2, 3, 1] -> 2,2
Output: 2
Explanation:
The input array has a degree of 2 because both elements 1 and 2 appear twice.
Of the subarrays that have the same degree:
[1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
The shortest length is 2. So return 2.

Example 2:

Input: [1,2,2,3,1,4,2] -> 2,2,3,1,4,2,
Output: 6

     */
public class FindDegree {
    public static void main(String args[]) {

        int[] nums = new int[]{1, 2, 2, 3, 1};

        System.out.println("final result = "+findShortestSubArray(nums));

        System.out.println("final Solution = " + findShortestSubArraySoln(nums));

        System.out.println("final result 2 = " + findShortestSubArray2(nums));

        System.out.println("final result 3 = " + findShortestSubArray3(nums));
    }

    //Just record the index of each number in a hash map and find the degree
    // of the array. Second iteration to find the shortest index range for
    // the number with the largest frequency.
    private static int findShortestSubArray(int[] nums) {
        int degree = 0, n = nums.length, minSize = n;
        // map contains the array value as key, freq as the value
        Map<Integer, Integer> map = new HashMap<>();
        // map2 contains the array value as key, num range of INDEX as the value
        Map<Integer, Integer[]> map2 = new HashMap<>();

        for (int i=0;i<n;i++) {
            // record the frequency of each element in the array
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);

            // calculate the degree as max of all freq
            degree = Math.max(degree, map.get(nums[i]));

            // put the number range of INDEX for each value in the array into map2
            if (map2.get(nums[i]) == null) map2.put(nums[i], new Integer[2]);
            Integer[] numRange = map2.get(nums[i]);
            // put index in numRange array
            if (numRange[0] == null) numRange[0] = i;
            numRange[1] = i;
        }

        System.out.println("map values");
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
        System.out.println("map2 values");
        for (Map.Entry<Integer, Integer[]> entry : map2.entrySet()) {
            System.out.println(entry.getKey() + "," + Arrays.toString( entry.getValue()));
        }
        System.out.println("degree = " + degree);

        // find the entry in the map with the degree desired
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() != degree) continue;
            Integer[] range = map2.get(entry.getKey());
            // find the minimum size (smallest frequency value)
            minSize = Math.min(minSize, range[1] - range[0] + 1);
        }
        return minSize;
    }

    //    Intuition and Algorithm
    //
    // An array that has degree d, must have some element x occur d times.
    // If some subarray has the same degree, then some element x (that occured d times),
    // still occurs d times. The shortest such subarray would be from the first occurrence
    // of x until the last occurrence.
    //
    // For each element in the given array, let's know left, the index of its first
    // occurrence; and right, the index of its last occurrence. For example, with
    // nums = [1,2,3,2,5] we have left[2] = 1 and right[2] = 3.
    //
    // Then, for each element x that occurs the maximum number of times,
    // right[x] - left[x] + 1 will be our candidate answer,
    // and we'll take the minimum of those candidates.
    private static int findShortestSubArraySoln(int[] nums) {
        Map<Integer, Integer> left = new HashMap(),
                right = new HashMap(), count = new HashMap();

        // for nums = [1,2,2,3,1]
        // iteration 1, left(1,0) right(1,4), count(1,2)
        // iteration 2, left(2,1) right(2,2), count(2,2)
        // iteration 3, left(3,3) right(3,3), count(3,1)
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (left.get(x) == null) left.put(x, i);
            right.put(x, i);
            count.put(x, count.getOrDefault(x, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : left.entrySet()) {
            System.out.println("left " + entry.getKey() + "," + entry.getValue());
        }
        for (Map.Entry<Integer, Integer> entry : right.entrySet()) {
            System.out.println("right " + entry.getKey() + "," + entry.getValue());
        }
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            System.out.println("count " + entry.getKey() + "," + entry.getValue());
        }

        int ans = nums.length;
        int degree = Collections.max(count.values());

        // go through count to find the entry with degree and subtract left from right
        for (int x: count.keySet()) {
            if (count.get(x) == degree) {
                ans = Math.min(ans, right.get(x) - left.get(x) + 1);
            }
        }
        return ans;
    }

    // for nums = [1,2,2,3,1]
    //        1,[2, 0, 4]
    //        2,[2, 1, 2]
    //        3,[1, 3, 3]
    // the first element in array is degree,
    // second is first index of this key,
    // third is last index of this key
    private static int findShortestSubArray2(int[] nums) {
        if (nums.length == 0 || nums == null) return 0;
        Map<Integer, int[]> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++){
            if (!map.containsKey(nums[i])){
                map.put(nums[i], new int[]{1, i, i});
                // the first element in array is degree,
                // second is first index of this key,
                // third is last index of this key
            } else {
                int[] temp = map.get(nums[i]);
                temp[0]++;
                temp[2] = i;
            }
        }

        for (Map.Entry<Integer, int[]> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "," + Arrays.toString(entry.getValue()));
        }

        int degree = Integer.MIN_VALUE, res = Integer.MAX_VALUE;
        for (int[] value : map.values()){
            if (value[0] > degree){
                degree = value[0];
                res = value[2] - value[1] + 1;
            } else if (value[0] == degree){
                res = Math.min( value[2] - value[1] + 1, res);
            }
        }
        return res;
    }

    private static int findShortestSubArray3(int[] nums) {
        Map<Integer, Integer> left = new HashMap(),
                right = new HashMap(), count = new HashMap();

        // left put the element and index, right put the element and index
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            // store leftmost and rightmost index and value
            if (left.get(x) == null) left.put(x, i);
            right.put(x, i);
            // count put element and freq
            count.put(x, count.getOrDefault(x, 0) + 1);
        }

        int ans = nums.length;
        // find degree = max freq
        int degree = Collections.max(count.values());
        for (int x: count.keySet()) {
            // find the element with the freq desired
            if (count.get(x) == degree) {
                // take min of length of the array, index from left and right
                ans = Math.min(ans, right.get(x) - left.get(x) + 1);
            }
        }
        return ans;
    }
}
