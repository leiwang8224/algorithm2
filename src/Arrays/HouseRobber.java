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
        System.out.println("max profit from stealing house = " + rob(nums));
        System.out.println("max profit from stealing house2 = " + rob2(nums));

    }

    private static int rob(int[] num) {
        // n+1 rows and 2 columns, first row is 0
        int[][] dp = new int[num.length + 1][2];
        for (int i = 1; i <= num.length; i++) {
            // current element is max of prev 2 elements (first col and second col)
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            dp[i][1] = num[i - 1] + dp[i - 1][0];
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

    private static int rob2(int[] nums) {
        int rob = 0;
        int notRob = 0;
        for (int num : nums) {
            int pre = Math.max(notRob, rob);
            rob = notRob + num;
            notRob = pre;
        }
        return Math.max(rob, notRob);
    }
}
