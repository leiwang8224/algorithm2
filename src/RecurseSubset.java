import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leiwang on 3/10/18.
 */

/**
 * Given a set of distinct integers, nums, return all possible subsets (the power set).

 Note: The solution set must not contain duplicate subsets.

 Example:

 Input: nums = [1,2,3]
 Output:
 [
 [3],
 [1],
 [2],
 [1,2,3],
 [1,3],
 [2,3],
 [1,2],
 []
 ]


 */
public class RecurseSubset {
    public static void main(String args[]) {
        int[] nums = new int[]{1,2,3,4};
        Arrays.sort(nums);
        ArrayList<ArrayList<Integer>> newList = new ArrayList<>();
        createSubset(newList, new ArrayList<>(), nums, 0);
        for (ArrayList<Integer> list : newList) {
            System.out.println(Arrays.toString(list.toArray()));
        }

        ArrayList<String> result = getCombPerms("ABC");
        System.out.println(result);
    }

    /**
     * Idea is to pick a number of a group of numbers recursively
     * ex: {1,2,3} pick 2, then {1,3} pick 1, {3} pick 3 ...
     * also need to know the state of the previous recursion by using startIndex
     * @param list
     * @param subList
     * @param origArray
     * @param startIndex
     */
    private static void createSubset(ArrayList<ArrayList<Integer>> list, ArrayList<Integer> subList, int[] origArray, int startIndex) {
        System.out.println("enter createPermutation start = " + startIndex);
        list.add(new ArrayList<>(subList));
        System.out.println("list added new subList");
        // note that startIndex is incremented every iteration
        for (int i = startIndex; i < origArray.length; i ++) {
            System.out.println("enter for loop with subList = " + Arrays.toString(subList.toArray()));

            subList.add(origArray[i]);
            System.out.println("after added subList = " + Arrays.toString(subList.toArray()));
            // increment startIndex by 1 and recurse
            createSubset(list, subList, origArray, i + 1);
            System.out.println("return from recursion ");
            subList.remove(subList.size()-1);
            System.out.println("end of for loop after removal subList = " + subList);
        }
    }

    private static ArrayList<String> getCombPerms(String str) {
        ArrayList<String> permutations = new ArrayList<>();
        if (str == null) return null; // error case
        else if (str.length() == 1) {
            permutations.add(str);
            return permutations;
        }

        String firstChar = str.substring(0,1);
        permutations.add(firstChar);

        String remainder = str.substring(1);
        // recurse on remainder
        ArrayList<String> words = getCombPerms(remainder);

        // insert the first char into each possible position in permutation
        for (String word : words)
            for (int index = 0; index <= word.length(); index++)
                permutations.add(insertCharAt(word, firstChar, index));
        permutations.addAll(words); // add permutations without the first char
        return permutations;
    }

    private static String insertCharAt(String word, String ch, int index) {
        String start = word.substring(0, index);
        String end = word.substring(index);
        return start + ch + end;
    }
}


