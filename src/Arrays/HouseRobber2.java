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
        int[] nums = new int[]{34,2,3,23,34,23,2,3,2,324,334,32,32,3};
        System.out.println("rob = " + rob(nums));
        System.out.println("rob 2 = " + rob2(nums));

    }

    private static int rob(int[] nums) {
        return Math.max(rob(nums, 0, nums.length-2), rob(nums, 1, nums.length-1));
    }

    private static int rob(int[] nums, int lo, int hi) {
        int preRob = 0, preNotRob = 0, rob = 0, notRob = 0;
        for (int i = lo; i <= hi; i++) {
            rob = preNotRob + nums[i];
            notRob = Math.max(preRob, preNotRob);

            preNotRob = notRob;
            preRob = rob;
        }
        return Math.max(rob, notRob);
    }
    // Add another set of states to the DP problem
    // ex:
    // num:     1,2,3,5,2
    // r_rf:    1,2,4,7,6 -> Not valid because you can't steal from last and first index
    // nr_rf:   0,1,2,4,7 -> valid
    // r_nrf:   0,2,3,7,5 -> valid
    // nr_nrf:  0,0,2,3,7 -> valid

    // r_rf: rob with rob first house
    // nr_rf: not rob with rob first house
    // r_nrf: rob with not rob first house
    // nr_nrf: not rob with not rob first house

    private static int rob2(int[] nums) {
        // if rob first, last one has to be non robbed, vice versa
        if (nums == null || nums.length == 0) return 0;
        int n = nums.length;
        if (n == 1) return nums[0];
        int[] rob_rf = new int[n];
        int[] nrob_rf = new int[n];
        int[] rob_nrf = new int[n];
        int[] nrob_nrf = new int[n];
        rob_rf[0] = nums[0];

        for (int i = 1; i < nums.length; i ++) {
            rob_rf[i] = nrob_rf[i-1]+nums[i];
            nrob_rf[i] = Math.max(nrob_rf[i-1], rob_rf[i-1]);
            rob_nrf[i] = nrob_nrf[i-1]+nums[i];
            nrob_nrf[i] = Math.max(nrob_nrf[i-1], rob_nrf[i-1]);
        }

        return Math.max(nrob_rf[n-1],rob_nrf[n-1]);

    }
}
