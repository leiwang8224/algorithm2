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
        System.out.println(getLetterPermutations("ABC",0));
        permuteLetters("ABC",0,2, result);
        System.out.println(result);
        result.clear();

        permutations("","ABC",result);
        System.out.println(result);
        System.out.println(permutationsIterative("ABC"));

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
    private static ArrayList<String> getLetterPermutations(String str, int level) {
        ArrayList<String> permutations = new ArrayList<>();
        System.out.println(getIndentationFromLevel(level) + "begin with arrayList "+ permutations + " str = " + str);
        if (str == null) return null; //error case
        else if (str.length() == 0) {
            //base case, add empty string to the arraylist and return
            //the permutation of empty string is empty string
            permutations.add("");
            return permutations;
        }

        char firstChar = str.charAt(0);  // get the first char
        String remainderOfString = str.substring(1); // remove the first char

        System.out.println(getIndentationFromLevel(level) + "start recursion on remainder = "+ remainderOfString + " str = " + str);

        // recurse on the rest of the string (without the first char)
        // cache the remainderOfString on to the heap, so that it can be restored when recurse call returns
        ArrayList<String> words = getLetterPermutations(remainderOfString, level + 1);

        System.out.println(getIndentationFromLevel(level) + "returning from recursion arrayList = " + words + " remainderOfString = " + remainderOfString);
        // iterate through the arraylist of permutations and insert the first char
        // at each index
        for(String word : words) {
            for (int index = 0; index <= word.length(); index++) {
                // essentially put the first char in each position in the string
                // starting from first char
                permutations.add(insertCharAt(word, firstChar, index));
            }
        }
        System.out.println(getIndentationFromLevel(level) + "end with arrayList " + permutations + " str = " + str);
        return permutations;
    }

    private static String getIndentationFromLevel(int level) {
        StringBuilder str = new StringBuilder();
        for (int index = 0; index < level; index++) {
            str.append("    ");
        }
        return str.toString();
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

    private static void permutations(String candidate, String remaining, ArrayList<String> result) {
        if (remaining.length() == 0) result.add(candidate);

        for (int index = 0; index < remaining.length(); index++) {
            // for each new candidate add the remaining char at each index
            String newCandidate = candidate + remaining.charAt(index);

            // note substring when beginning = end, no substring is selected (empty substring)
            String newRemaining = remaining.substring(0, index) + remaining.substring(index + 1);

            // for each index recurse on the new candidate and remaining letters
            permutations(newCandidate,newRemaining,result);
        }
    }

    private static ArrayList<String> permutationsIterative(String str) {
        // empty arraylist to store partial permutations
        ArrayList<String> partial = new ArrayList<>();

        // init list with the first char of the string
        partial.add(String.valueOf(str.charAt(0)));

        // do for every char of the specified string
        for (int index = 1; index < str.length(); index++) {

            //consider previously constructed partial permutation one by one

            // iterate backwards to avoid concurrent modification
            for (int backWardIndex = partial.size() - 1; backWardIndex >= 0; backWardIndex--) {
                // remove current partial permutation from arraylist
                String s = partial.remove(backWardIndex);

                // insert next char of the specified string in all
                // possible positions of current partial permutation.
                // Then insert each of these newly constructed string in the list
                for (int subIndex = 0; subIndex <= s.length(); subIndex++) {
                    partial.add(s.substring(0, subIndex) +
                                str.charAt(index) +
                                s.substring(subIndex));
                }
            }
        }

        return partial;
    }


}
