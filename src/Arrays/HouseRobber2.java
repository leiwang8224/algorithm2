package Arrays;

/**
 * Created by leiwang on 4/15/18.
 */
//After robbing those houses on that street, the thief has found himself a
// new place for his thievery so that he will not get too much attention.
// This time, all houses at this place are arranged in a circle. That means
// the first house is the neighbor of the last one. Meanwhile, the security
// system for these houses remain the same as for those in the previous street.
//
// Given a list of non-negative integers representing the amount of money of
// each house, determine the maximum amount of money you can rob tonight without
// alerting the police.
public class HouseRobber2 {

    public static void main(String args[]) {

    }

    private int rob(int[] nums) {
        return Math.max(rob(nums, 0, nums.length-2), rob(nums, 1, nums.length-1));
    }

    private int rob(int[] nums, int lo, int hi) {
        int preRob = 0, preNotRob = 0, rob = 0, notRob = 0;
        for (int i = lo; i <= hi; i++) {
            rob = preNotRob + nums[i];
            notRob = Math.max(preRob, preNotRob);

            preNotRob = notRob;
            preRob = rob;
        }
        return Math.max(rob, notRob);
    }
}
