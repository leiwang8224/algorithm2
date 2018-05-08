package Recursion;

import java.util.Arrays;

/**
 * Created by leiwang on 5/7/18.
 */
public class FindAllCombinations {
    public static void main(String[] args) {
        combination(8, 0, 0, new int[8], 4);
    }

    /**
     * Limit the size of the subset to sizeSubset
     * @param num
     * @param p
     * @param size
     * @param combo
     * @param sizeSubset
     */
    private static void combination(int num, int p, int size, int[] combo, int sizeSubset) {
        // prune away useless branches
        if (num - p < sizeSubset - size) return;

        if (size == sizeSubset) {
            System.out.println(Arrays.toString(combo));
            return;
        }

        // skip
        combination(num, p + 1, size, combo, sizeSubset);
        // use
        combo[size] = p;
        combination(num, p + 1, size + 1, combo, sizeSubset);
    }
}
