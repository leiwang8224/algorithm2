package DP;

//LC-300
public class LongestIncreasingSubsequence {
    public static void main(String[] args) {
        System.out.println(lengthOfLISDP(new int[]{10,9,2,5,3,7,101,18}));
        System.out.println(lengthOfLIS(new int[]{10,9,2,5,3,7,101,18}));
        System.out.println(lengthOfLIS2(new int[]{10,9,2,5,3,7,101,18}));
        System.out.println(lengthOfLISDPBinarySearch(new int[]{10,9,2,5,3,7,101,18}));

    }

    private static int lengthOfLIS(int[] nums) {
        return lengthOfLIS(nums, Integer.MIN_VALUE, 0);
    }

    // O(2^N) for recursion tree
    // O(N) for memory
    private static int lengthOfLIS(int[] nums, int prevValue, int curPos) {
        if (curPos == nums.length) return 0;

        int taken = 0;

        // check to see if increase (nums[i] - nums[i-1] > 0)
        if (nums[curPos] > prevValue) {
            taken = 1 + lengthOfLIS(nums, nums[curPos], curPos+1);
        }

        // not increasing so do not add the curValue, use prevValue
        int nottaken = lengthOfLIS(nums,prevValue, curPos+1);

        return Math.max(taken, nottaken);
    }

    private static int lengthOfLIS2(int[] nums) {
        int[][] memo = new int[nums.length+1][nums.length];
        for (int[] row : memo) {
            java.util.Arrays.fill(row, -1);
        }
        return lengthOfLIS2(nums, -1, 0, memo);
    }

    // time O(n^2)
    // space O(n^2)
    //    memo[i][j] represents the length of the LIS possible using nums[i] as the previous element
    //    considered to be included/not included in the LIS, with nums[j] as the current element
    //    considered to be included/not included in the LIS.
    // prevIndex and curIndex is used to keep track of whether taking the current number or not
    // only prevIndex and curIndex is needed since we only care about the most recent two elements
    private static int lengthOfLIS2(int[] nums, int prevIndex, int curIndex, int[][] memo) {
        // reached the end so return 0
        if (curIndex == nums.length) return 0;

        // if length is greater or equal to 0 then return it
        if (memo[prevIndex+1][curIndex] >= 0) return memo[prevIndex+1][curIndex];

        int taken = 0;

        if (prevIndex < 0 || nums[curIndex] > nums[prevIndex]) {
            // pass in curIndex for prevIndex when taken
            taken = 1 + lengthOfLIS2(nums, curIndex, curIndex+1, memo);
        }

        // pass in prevIndex for prevIndex when not taken
        int nottake = lengthOfLIS2(nums, prevIndex, curIndex+1, memo);

        // record the longest length
        memo[prevIndex+1][curIndex] = Math.max(taken, nottake);
        return memo[prevIndex+1][curIndex];
    }

    //O(n^2) time
    //O(n) space
    // This method relies on the fact that the longest increasing subsequence possible up to the ith index
    // in a given array is independent of the elements coming later on in the array. Thus, if we know the
    // length of the LIS up to ith index, we can figure out the length of the LIS possible by including the
    // (i + 1)th element based on the element with indices j such that 0 <= j <= (i+1).
    // We make the use of a dp array to store the required data. dp[i] represents the length of the longest
    // increasing subsequence possible considering the array elements up to the ith index only, by necessarily
    // including the ith element. IN order to find out dp[i], we need to try to append the current element
    // (nums[i]) in every possible increasing subsequences up to the (i-1)th index, including
    // (i-1)th index, such that the new sequence formed by adding the current element is also an increasing
    // subsequence. dp[i] = max(dp[j]) + 1
    private static int lengthOfLISDP(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dpMaxLength = new int[nums.length];
        dpMaxLength[0] = 1; // or initialize all 1's in the array, the minimum number of LIS is 1 when array length > 0
        int resultLIS = 1;

        // for each indice i, iterate from 0 index to i
        // Note that 2 find max operations are needed to find:
        // 1. maximum length up to indexI
        // 2. maximum overall length for the whole array
        for (int indexI = 1; indexI < dpMaxLength.length; indexI++) {
            int maxUptoIndexFrom0ToIndexI = 0;
            // iterate from 0 to indexI and find the maxLength of increasing subsequence
            for (int indexFrom0ToIndexI = 0; indexFrom0ToIndexI < indexI; indexFrom0ToIndexI++) {
                // number after is bigger than number before (monotonically increasing)
                if (nums[indexI] > nums[indexFrom0ToIndexI]) {
                    // get the max length at indexFrom0ToIndexI and add 1 equals the max length at index
                    maxUptoIndexFrom0ToIndexI = Math.max(maxUptoIndexFrom0ToIndexI, dpMaxLength[indexFrom0ToIndexI]);
                }
                System.out.println("inner for loop: " + java.util.Arrays.toString(dpMaxLength));
            }
            // REMEMBER to add 1 to the maxLengthSoFar and set to dp table
            // the +1 is for the current element, indexFrom0ToIndexI + 1 = index
            dpMaxLength[indexI] = maxUptoIndexFrom0ToIndexI + 1;
            // max of resultLIS and maxLengthSoFar + 1
            resultLIS = Math.max(resultLIS, dpMaxLength[indexI]);
            System.out.println("outer for loop: " + java.util.Arrays.toString(dpMaxLength));
        }
        return resultLIS;
    }

    // O(nlogn) time
    // O(n) space
    // In this approach, we scan the array from left to right. We also make use of a dp array intialized with all 0s.
    // This dp array is meant to store the increasing subsequence formed by including the currently encountered element.
    // while traversing the nums array, we keep on filling the dp array with the elements encoutnered so far.
    // For the element corresponding to the jth index(nums[j]), we determine its correct position in the dp array by
    // making use of a binary search and also insert it at the correct position. An important point to be noted is
    // that for binary search, we consider only that portion of the dp array in which we have made the updates by inserting
    // some elements at their correct positions. Thus only the elements up to the ith index in the dp array can dertermine
    // the position of the current element in it. Since the element enters its correction position(i) in an ascending order
    // in the dp array, the subsequence formed so far in it is surely an increasing subsequence. Whenever this position
    // index i becomes equal to the length of the LIS formed so far, it means we need to update the len as len = len + 1
    // Note: dp array does not result in the longest increasing subsequence, but length of dp array will give you length
    // of LIS.
    //    Consider the example:
    //
    //    input: [0, 8, 4, 12, 2]
    //
    //    dp: [0]
    //
    //    dp: [0, 8]
    //
    //    dp: [0, 4]
    //
    //    dp: [0, 4, 12]
    //
    //    dp: [0 , 2, 12] which is not the longest increasing subsequence, but length of dp array results in
    //    length of Longest Increasing Subsequence.
    //    Note: Arrays.binarySearch() method returns index of the search key, if it is contained in the array,
    //    else it returns (-(insertion point) - 1). The insertion point is the point at which the key would
    //    be inserted into the array: the index of the first element greater than the key, or a.length if
    //    all elements in the array are less than the specified key.
    private static int lengthOfLISDPBinarySearch(int[] nums) {
        int[] dp = new int[nums.length];
        int longestIncSubLength = 0;
        for (int num : nums) {
            // search for num in dp
            int indexNum = java.util.Arrays.binarySearch(dp, 0, longestIncSubLength, num);
            if (indexNum < 0) {
                // if num doesn't exist, this is the index to insert
                indexNum = -(indexNum + 1);
            }
            // insert the num into dp at the location found/or not found
            dp[indexNum] = num;
            if (indexNum == longestIncSubLength) {
                longestIncSubLength++;
            }
        }
        return longestIncSubLength;
    }
}
