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
//        permute(0, num, new boolean[num], new int[num]);

        ArrayList<String> result = new ArrayList<>();
        System.out.println(getLetterPermutations("ABC"));
        permuteLetters("ABC",0,2, result);
        System.out.println(result);

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
    private static void permuteNumbers(int p, int num, boolean[] used, int[] permutation) {
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
                permuteNumbers(p + 1, num, used, permutation);
                // revert used index back to false
                used[i] = false;
            }
        }
    }


    /**
     * Recursion based on the remainder of the string used for current state.
     * Note that this does not use the index of the string for state representation.
     * @param str
     * @return
     */
    private static ArrayList<String> getLetterPermutations(String str) {
        ArrayList<String> permutations = new ArrayList<>();
        System.out.println("begin with arrayList "+ permutations + " str = " + str);
        if (str == null) return null; //error case
        else if (str.length() == 0) {
            //base case, add empty string to the arraylist and return
            //the permutation of empty string is empty string
            permutations.add("");
            return permutations;
        }

        char firstChar = str.charAt(0);  // get the first char
        String remainderOfString = str.substring(1); // remove the first char

        // recurse on the rest of the string (without the first char)
        // cache the remainderOfString on to the heap, so that it can be restored when recurse call returns
        ArrayList<String> words = getLetterPermutations(remainderOfString);

        System.out.println("in the middle of method arrayList = " + words + " remainderOfString = " + remainderOfString);
        // iterate through the arraylist of permutations and insert the first char
        // at each index
        for(String word : words) {
            for (int index = 0; index <= word.length(); index++) {
                // essentially put the first char in each position in the string
                // starting from first char
                permutations.add(insertCharAt(word, firstChar, index));
            }
        }
        System.out.println("end with arrayList " + permutations + " str = " + str);
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

    /**
     *                                              ABC
     *                        /                     |                               \
     *                      ABC                    BAC                              CBA
     *             (swap A with A)          (swap A with B)                 (swap A with C)
     *             /           \                /      \                        /         \
     *         ABC            ACB             BAC       BCA                   CBA         CAB
     *(swap B with B) (swap B with C) (swap A with A) (swap A with C) (swap B with B) (swap B with A)
     * @param str
     * @param left
     * @param right
     */
    private static void permuteLetters(String str, int left, int right, ArrayList<String> result) {
        if (left == right) {
            System.out.println(str);
            result.add(str);
        } else {
            // need to include the rightmost index (right is index, not length)
            for (int index = left; index <= right; index++) {
                // basically swap each index with the left index value
                str = swap(str,left,index);
                permuteLetters(str,left+1,right,result);
                str = swap(str,left,index);
            }
        }
    }

    /**
     * Swap chars from left index to right index and vice versa
     * @param str
     * @param leftIndex
     * @param rightIndex
     * @return
     */
    private static String swap(String str, int leftIndex, int rightIndex) {
        char temp;
        char[] charArray = str.toCharArray();
        temp = charArray[leftIndex];
        charArray[leftIndex] = charArray[rightIndex];
        charArray[rightIndex] = temp;
        return String.valueOf(charArray);
    }


}
