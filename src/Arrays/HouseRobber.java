package Arrays;

import java.util.Arrays;

/**
 * Created by leiwang on 4/15/18.
 */
//You are a professional robber planning to rob houses along a street.
// Each house has a certain amount of money stashed, the only constraint
// stopping you from robbing each of them is that adjacent houses have security
// system connected and it will automatically contact the police if two adjacent
// houses were broken into on the same night.
//
// Given a list of non-negative integers representing the amount of money of
// each house, determine the maximum amount of money you can rob tonight without
// alerting the police.
public class HouseRobber {
    public static void main(String args[]) {
        // any one state is dependent on the previous state
        // this is where DP comes in
        // state could be indicated by: steal or not to steal
        // for ex: first number is 32, if steal, we have value of 32, if not we have 0
        int nums[] = new int[] { 32,43,2,3,2,4,3,232,43,23,3,43,23};
        // DP table
        //not steal | steal                   nums      steal col       not steal col
        //        [0, 0]
        //        [0, 32] <- nums[0] + dp[0]   32       32 + 0 = 32     max(0, 0) = 0
        //        [32, 43]                     43       43 + 0 = 43     max(0, 32) = 32
        //        [43, 34]                      2       2 + 32 = 34     max(32,43) = 43
        //        [43, 46]                      3       3 + 43 = 46     max(43,34) = 43
        //        [46, 45]                      2       2 + 43 = 45     max(43,46) = 46
        //        [46, 50]                      4
        //        [50, 49]                      3
        //        [50, 282]                   232
        //        [282, 93]                    43
        //        [282, 305]                   23
        //        [305, 285]                    3
        //        [305, 348]                   43
        //        [348, 328]                   23

        // The logical thought process is:
        // If I don't steal from current house, I get the max value from the prev house (steal or not steal)
        // If I steal from the current house, I get the value of the current house + the value I get from not stealing prev house
        System.out.println("max profit from stealing house = " + robDP(nums));
        System.out.println("max profit from stealing house2 = " + robTwoVars(nums));
        System.out.println("max profit from stealing house recurse = " + robRecursive(nums));
        System.out.println("max profit from stealing house memo = " + robMemo(nums));
    }

    /**
     * Recursive Top-down
     * Brute force method which fails time limitations
     * A robber has 2 options: a) rob current house i; b) don't rob current house.
     * If an option "a" is selected it means she can't rob previous i-1 house but can
     * safely proceed to the one before previous i-2 and gets all cumulative loot that follows.
     * If an option "b" is selected the robber gets all the possible loot from robbery of i-1
     * and all the following buildings.
     * So it boils down to calculating what is more profitable:
     *     robbery of current house + loot from houses before the previous
     *     loot from the previous house robbery and any loot captured before that
     * @param nums
     * @return
     */
    private static int robRecursive(int[] nums) {
        return rob(nums, nums.length-1);
    }

    private static int rob(int[] nums, int i) {
        if (i < 0) return 0;
        // pick one from the previous two
        // if (i-2) is picked then the current num is picked as well
        // if (i-1) is picked then no need to add current num
        return Math.max(rob(nums, i - 2) + nums[i], rob(nums, i-1));
    }

    static int[] memo;

    /**
     * Recursive / memo top down
     * time O(n), space O(n)
     * @param nums
     * @return
     */
    private static int robMemo(int[] nums) {
        memo = new int[nums.length+1];
        Arrays.fill(memo, -1);
        return robMemoHelper(nums, nums.length-1);
    }

    private static int robMemoHelper(int[] nums, int index) {
        if (index < 0) {
            return 0;
        }
        if (memo[index] >= 0) {
            return memo[index];
        }
        int result = Math.max(rob(nums, index - 2) + nums[index], rob(nums, index - 1));
        memo[index] = result;
        return result;
    }

    private static int robDP(int[] num) {
        // n+1 rows and 2 columns, first row is 0
//        dp[i][1] means we rob the current house and dp[i][0] means we don't,
        int[][] dp = new int[num.length + 1][2];
        for (int row = 1; row <= num.length; row++) {
            // current element is max of prev 2 elements (first col and second col)
            // take the max of take and not take, not taking the current cell
            dp[row][0] = Math.max(dp[row - 1][0], dp[row - 1][1]);
            // taking the current cell, so add num[row-1] to the sum so far (dp[row-1][0])
            // rob current house = do not rob prev house + current house
            dp[row][1] =  dp[row - 1][0] + num[row - 1];
        }

        for (int[] row : dp) {
            System.out.println(Arrays.toString(row));
        }
        return Math.max(dp[num.length][0], dp[num.length][1]);
    }

    // example:
    // Two states: steal or not steal
    // nums(n1):      1,2,5,2,1,3
    // steal(s1):     1,2,6,4,7,9 -> n1(index) + s2(index-1)
    // not steal(s2): 0,1,2,6,6,7 -> max(s1,s2)
    // func : r[i] = nr[i-1] + num
    //        nr[i] = max(nr[i-1],r[i-1])

    private static int robTwoVars(int[] nums) {
        int curRobSum = 0;
        int prevRobSum = 0;
        for (int num : nums) {
            int maxRobNotRob = Math.max(prevRobSum, curRobSum);
            // rob the current num
            curRobSum = prevRobSum + num;
            // not rob the current num
            prevRobSum = maxRobNotRob;
        }
        return Math.max(curRobSum, prevRobSum);
    }

    public static int robExplain(int[] nums)
    {
        int ifRobbedPrevious = 0; 	// max money can get if rob current house
        int ifDidntRobPrevious = 0; // max money can get if not rob current house

        // We go through all the values, we maintain two counts, 1) if we rob this cell, 2) if we didn't rob this cell
        for(int i=0; i < nums.length; i++)
        {
            // If we rob current cell, previous cell shouldn't be robbed. So, add the current value to previous one.
            int currRobbed = ifDidntRobPrevious + nums[i];

            // If we don't rob current cell, then the count should be max of the previous cell robbed and not robbed
            int currNotRobbed = Math.max(ifDidntRobPrevious, ifRobbedPrevious);

            // Update values for the next round
            ifDidntRobPrevious  = currNotRobbed;
            ifRobbedPrevious = currRobbed;
        }

        return Math.max(ifRobbedPrevious, ifDidntRobPrevious);
    }
}
