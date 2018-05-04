package EPI_BOOK;

import Arrays.InsertIntervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by leiwang on 5/3/18.
 */

/**
 * Apply permutation in perm to array nums
 * ex. perm {2,0,1,3} applied to nums {a,b,c,d} returns {b,c,a,d} (1,2,0,3)
 */
public class ApplyPermutationToArray {
    public static void main(String[] args) {
        List<Integer> perm = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();

        perm.add(2);
        perm.add(0);
        perm.add(1);
        perm.add(3);

        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);

        System.out.println("perm " + Arrays.toString(perm.toArray()));
        applyPermutation(perm, nums);
        System.out.println(Arrays.toString(nums.toArray()));

        perm.set(0,0);
        perm.set(1,1);
        perm.set(2,2);
        perm.set(3,3);

        System.out.println("perm " + Arrays.toString(perm.toArray()));
        applyPermutation2(perm, nums);
        System.out.println(Arrays.toString(nums.toArray()));
    }

    /**
     * Time O(n)
     * Space O(1)
     * @param perm
     * @param nums
     */
    private static void applyPermutation(List<Integer> perm, List<Integer> nums) {
        for (int i = 0; i < nums.size(); i++) {
            // check if element at index i has not been moved by checking
            // if perm.get(i) is nonnegative.
            int next = i;
            while (perm.get(next) >= 0) {
                Collections.swap(nums, i, perm.get(next));
                int temp = perm.get(next);
                // subtracts perm.size() from an entry in perm to make it negative
                // which indicates the corresponding move has been performed
                perm.set(next, perm.get(next) - perm.size());
                next = temp;
            }
        }

        // restore perm
        for (int i = 0; i < perm.size(); i++) {
            perm.set(i, perm.get(i) + perm.size());
        }
    }

    private static void applyPermutation2(List<Integer> perm, List<Integer> nums) {
        for (int i = 0; i < nums.size(); i ++) {
            // Traverse the cycle to see if i is the min element
            boolean isMin = true;
            int j = perm.get(i);
            while (j != i) {
                if (j < i) {
                    isMin = false;
                    break;
                }
                j = perm.get(j);
            }
            if (isMin) {
                cyclicPermutation(i, perm, nums);
            }
        }
    }

    private static void cyclicPermutation(int start, List<Integer> perm, List<Integer> nums) {
        int i = start;
        int temp = nums.get(start);
        do {
            int nextI = perm.get(i);
            int nextTemp = nums.get(nextI);
            nums.set(nextI, temp);
            i = nextI;
            temp = nextTemp;
        } while (i != start);
    }
}
