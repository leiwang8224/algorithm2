package Recursion;

import java.util.Arrays;

/**
 * Created by leiwang on 5/7/18.
 */
public class FindAllPermutations {
    public static void main(String[] args) {
        permute(0, 8, new boolean[8], new int[8]);

    }

    /**
     * Building a permutation one element at a time. At each step we will pick
     * an unused element for the current position and move on to the next
     *
     * @param p
     * @param num
     * @param used
     * @param permutation
     */
    private static void permute(int p, int num, boolean[] used, int[] permutation) {
        if (p == num) {
            System.out.println(Arrays.toString(permutation));
            return;
        }

        // try every unused element in the current position
        for (int i = 0; i < num; i ++) {
            if (!used[i]) {
                used[i] = true;
                permutation[p] = i;
                permute(p + 1, num, used, permutation);
                used[i] = false;
            }
        }
    }

    private static boolean nextPermutation(int[] nums) {
        int n = nums.length;

        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i+1]) i --;

        if (i == -1) return false;

        int x = nums[i];
        int j = n - 1;
        while (j > i && nums[j] <= x) j--;

        nums[i] = nums[j];
        nums[j] = x;

        Arrays.sort(nums, i + 1, n);
        return true;
    }
}
