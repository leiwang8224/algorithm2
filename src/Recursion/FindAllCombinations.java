package Recursion;

import java.util.Arrays;

/**
 * Created by leiwang on 5/7/18.
 */

/**
 * Find all distinct combinations of a number
 * ex: input largestSize = 4
 * [1, 2, 3, 4]
   [0, 2, 3, 4]
   [0, 1, 3, 4]
   [0, 1, 2, 4]
   [0, 1, 2, 3]
 */
public class FindAllCombinations {
    public static void main(String[] args) {
        int largestSize = 3;
        combination(5, 0, 0, new int[largestSize], largestSize);
    }

    /**
     * Limit the size of the subset to sizeSubset
     * @param num target sum
     * @param p add up from 0
     * @param index index starting from 0
     * @param combo combinations
     * @param sizeSubset largest size for the subset
     */
    private static void combination(int num, int p, int index, int[] combo, int sizeSubset) {
        // prune away useless branches
        if (num - p < sizeSubset - index) return;

        if (index == sizeSubset) {
            System.out.println(Arrays.toString(combo));
            return;
        }

        // skip by not incrementing the index
        combination(num, p + 1, index, combo, sizeSubset);

        combo[index] = p;

        // use by incrementing the index
        combination(num, p + 1, index + 1, combo, sizeSubset);
    }
}
