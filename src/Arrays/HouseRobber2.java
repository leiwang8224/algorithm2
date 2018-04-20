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

    /**
     * Gets rid of the DP array since all we care about is the prev state
     * @param nums original array
     * @param lo starting value
     * @param hi end value
     * @return
     */
    private static int rob(int[] nums, int lo, int hi) {
        // initial state
        int preRob = 0, preNotRob = 0, rob = 0, notRob = 0;
        for (int i = lo; i <= hi; i++) {
            rob = preNotRob + nums[i];      //rob_rf[i] = nrob_rf[i-1] + nums[i]
            notRob = Math.max(preRob, preNotRob);  //nrob_rf[i] = max(nrob_rf[i-1],rob_rf[i-1])

            preNotRob = notRob;             //update nrob_rf[i-1] with nrob_rf[i]
            preRob = rob;                   //update rob_rf[i-1] with rob_rf[i]

        }
        return Math.max(rob, notRob);
    }
    // Add another set of states to the DP problem (r_nrf,nr_nrf)
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
            rob_rf[i] = nrob_rf[i-1]+nums[i];       // if rob the current one, can't rob the prev one
            nrob_rf[i] = Math.max(nrob_rf[i-1], rob_rf[i-1]);   // if not rob the current one, take max of prev
            rob_nrf[i] = nrob_nrf[i-1]+nums[i];     // if rob the current one, can't rob the prev one
            nrob_nrf[i] = Math.max(nrob_nrf[i-1], rob_nrf[i-1]);// if not rob the current one, take the max of prev
        }
//        int[] nums = new int[]{34,2,3,23,34,23,2,3,2,324,334,32,32,3};
        //rob_rf nrob_rf rob_nrf nrob_nrf   nums
//        34,       0,      0,       0      34     -> first row init values
//        2,       34,      2,       0      2
//        37,      34,      3,       2      3
//        57,      37,      25,      3      23
//        71,      57,      37,     25      34
//        80,      71,      48,     37      23
//        73,      80,      39,     48      2
//        83,      80,      51,     48      3
//        82,      83,      50,     51      2
//        407,     83,      375,    51      324
//        417,     407,     385,    375     334
//        439,     417,     407,    385     32
//        449,     439,     417,    407     32
//        442,     449,     410,    417     3
        System.out.println("rob_rf "+"nrob_rf "+"rob_nrf "+"nrob_nrf");
        for (int i = 0; i < nums.length; i ++) {
            System.out.println(rob_rf[i] + "," + nrob_rf[i] + "," + rob_nrf[i] + "," + nrob_nrf[i]);
        }
        return Math.max(nrob_rf[n-1],rob_nrf[n-1]);

    }
}
