package BitManip;
/**
 * Given a non-empty array of integers, every element appears three times except for one, which appears exactly once. Find that single one.

 Note:

 Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

 Example 1:

 Input: [2,2,3,2]
 Output: 3

 */
public class SingleNumber2 {
    public static void main(String args[]) {
        int[] nums = new int[]{1,1,1,2,2,2,3,3,3,4,5,5,5,6,6,6};

        System.out.println("single number is " + singleNumber(nums, nums.length));
        System.out.println("single number is " + singleNumber2(nums, nums.length));

    }

    private static int singleNumber(int[] nums, int length) {
        int[] count = new int[32];
        int result = 0;
        for (int i = 0; i < 32; i ++) {
            for (int j = 0; j < length; j ++) {
                if (((nums[j] >> i) & 1) >= 1) {
                    count[i]++;
                }
            }
            // store in binary domain the number of occurrences
            // count[i] % 3 is equal to 0 or 1, when equal to 1 that's the only occurrence
            // then left shifting the bits yields the actual number that occurred once
            // (convert back to decimal from binary domain)
            System.out.println((count[i] % 3) + " left shift " + i + " actual = " + ((count[i] % 3) << i));
            result = result | ((count[i] % 3) << i);
        }
        return result;
    }

    /**
     * Improve this using three bitmask variables
     * 1. ones as a bitmask to represent the ith bit had appeared once
     * 2. twos as a bitmask to represent the ith bit had appeared twice
     * 3. threes as a bitmask to represent the ith bit had appeared three times
     * When the ith bit had appeared for the third time, clear the ith bit of both ones
     * and twos to 0. The final answer will be the value of ones.
     * @param nums
     * @param length
     * @return
     */
    private static int singleNumber2(int[] nums, int length) {
        int ones = 0, twos = 0, threes = 0;
        for (int i = 0; i < length; i ++) {
            twos |= ones & nums[i];
            ones ^= nums[i];
            threes = ones & twos;
            ones &= ~threes;
            twos &= ~threes;
        }
        return ones;
    }
}
