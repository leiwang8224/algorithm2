package BitManip;

public class SingleNumber3 {
    public static void main(String args[]) {
        int[] nums = new int[] {1,2,1,3,2,5};
        //should reutnr [3,5] everything else are repeated twice
        singleNumber(nums);
    }

    /**
     * Once again, we need to use XOR to solve this problem. But this time, we need to do it in two passes:

     •In the first pass, we XOR all elements in the array, and get the XOR of the two numbers we need to find.
     Note that since the two numbers are distinct, so there must be a set bit (that is, the bit with value ‘1’)
     in the XOR result. Find out an arbitrary set bit (for example, the rightmost set bit).

     •In the second pass, we divide all numbers into two groups, one with the aforementioned bit set,
     another with the aforementinoed bit unset. Two different numbers we need to find must fall into thte two distrinct groups.
     XOR numbers in each group, we can find a number in either group.
     Complexity:
     •Time: O (n)
     •Space: O (1)
     * @param nums
     * @return
     */
    private static int[] singleNumber(int[] nums) {
        // Pass 1:
        // Get XOR of the two numbers we need to find
        int diff = 0;
        for (int num : nums) {
            diff ^= num;
        }
        // Get last set bit
        diff &= -diff;

        // Pass 2:
        int[] rets = {0, 0}; // this array stores the two numbers we will return
        for (int num : nums) {
            if ((num & diff) == 0) // the bit is not set
            {
                rets[0] ^= num;
            } else // the bit is set
            {
                rets[1] ^= num;
            }
        }
        return rets;
    }
}
