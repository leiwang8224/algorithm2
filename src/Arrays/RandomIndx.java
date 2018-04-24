package Arrays;

import java.util.Random;

/**
 * Created by leiwang on 4/14/18.
 */

/**
 * Probably low likelyhood to be in the interview
 */
public class RandomIndx {
//    Given an array of integers with possible duplicates, randomly output the
//    index of a given target number. You can assume that the given target number must exist in the array.
//int[] nums = new int[] {1,2,3,3,3};
//    Solution solution = new Solution(nums);
//
//// pick(3) should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
//    solution.pick(3);
//
//// pick(1) should return 0. Since in the array only nums[0] is equal to 1.
//    solution.pick(1);
    public static void main(String[] args)
    {
        int nums[] = new int[]{1,2,3,3,3};
        Solution soln = new Solution(nums);
        System.out.println("pick random " + soln.pick(3));

    }

    private static class Solution {
        private int[] nums;
        Random rdn;
        public Solution(int[] nums) {
            this.nums = nums;
            rdn = new Random();
        }

        int pick(int target) {
            int result = -1;
            int count = 0;
            for (int i = 0; i < nums.length; i++) {
                // if the pick is not target, just keep looping
                if (nums[i] != target)
                    continue;
                // if the pick is 0, set resulting index to i
                if (rdn.nextInt(++count) == 0)
                    result = i;
            }
            return result;
        }

    }
//    private static int pick(int target) {
//
//    }
}
