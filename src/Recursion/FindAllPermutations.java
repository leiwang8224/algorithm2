package Recursion;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leiwang on 5/7/18.
 */

/**
 * find all permutations of a number
 * ex: input = 2
 * [0, 1]
   [1, 0]
 */
public class FindAllPermutations {
    public static void main(String[] args) {
        int num = 2;
        permute(0, num, new boolean[num], new int[num]);

        System.out.println(getLetterPermutations("ABC"));

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
                // indicating index is used
                used[i] = true;
                // put the index in the permutation
                permutation[p] = i;
                // perform next permutation by adding 1
                permute(p + 1, num, used, permutation);
                // revert used index back to false
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

    private static ArrayList<String> getLetterPermutations(String str) {
        ArrayList<String> permutations = new ArrayList<>();
        if (str == null) return null; //error case
        else if (str.length() == 0) {
            //base case, add empty string to the arraylist and return
            permutations.add("");
            return permutations;
        }

        char firstChar = str.charAt(0);  // get the first char
        String remainderOfString = str.substring(1); // remove the first char

        // recurse on the rest of the string (without the first char)
        ArrayList<String> words = getLetterPermutations(remainderOfString);

        System.out.println(words);
        for(String word : words) {
            for (int index = 0; index <= word.length(); index++) {
                // essentially put the first char in each position in the string
                // starting from first char
                permutations.add(insertCharAt(word, firstChar, index));
            }
        }
        return permutations;
    }

    private static String insertCharAt(String word, char charToInsert, int index) {
        // get first char
        String start = word.substring(0,index);
        // get rest of string
        String end = word.substring(index);

        // insert the char in between the first char and rest string
        return start + charToInsert + end;
    }


}
