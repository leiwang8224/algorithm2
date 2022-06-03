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
//
//        ArrayList<String> result = getCombPerms("ABC",0);
//        System.out.println(result);
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
    private static void createSubset(ArrayList<ArrayList<Integer>> list,
                                     ArrayList<Integer> subList,
                                     int[] origArray,
                                     int startIndex) {
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

    private static void createSubsetSimple(ArrayList<ArrayList<Integer>> result,
                                           ArrayList<Integer> subList,
                                           int[] origArray,
                                           int idx) {
        if (idx == origArray.length) {
            result.add(new ArrayList<>(subList));
            return;
        }

        subList.add(origArray[idx]);
        createSubsetSimple(result, subList, origArray, idx+1);
        subList.remove(subList.size()-1);
        createSubsetSimple(result, subList, origArray, idx + 1);
    }



//1. Initialize an ArrayList to store all the permutations.
//
//2. Check for the base conditions i.e. if the input is null,
    // return the input itself. If the input is a single
    // Character or an empty String, add it to the list.
//
//3. Separate out the first character and recursively find the
    // permutation of the remainder String.
//
//4. Iterate and insert the separated character at every position
    // in all the words obtained from the recursive call.
//
//5. Return the list of all the words.

    private static ArrayList<String> getCombPerms(String str, int level) {
        //initialize array for result
        ArrayList<String> permutations = new ArrayList<>();

        //check for base conditions, input is null, input has one char
        if (str == null) return null; // error case
        else if (str.length() == 1) {
            System.out.println(getIndentationFromLevel(level) + "adding the one char and return " + str);
            permutations.add(str);
            return permutations;
        }

        // separate out the first char and recurse on remaining
        String firstChar = str.substring(0,1);

        // this is the diff between combperms and perms (perms do not add the one char)
        permutations.add(firstChar);

        String remainder = str.substring(1);

        System.out.println(getIndentationFromLevel(level) + "calling recursion on remainder = " + remainder);
        // recurse on remainder and get list of words back
        ArrayList<String> words = getCombPerms(remainder, level + 1);

        // also this is the diff between combperms and perms (add all of the words)
        // now add all of the words in word list to the permutations
        permutations.addAll(words); // add permutations without first char
        
        // insert the first char into each possible position in current
        // permutation of words list
        for (String word : words) {
            System.out.println(getIndentationFromLevel(level) + "for word = " + word);
            for (int index = 0; index <= word.length(); index++) {
                System.out.println(getIndentationFromLevel(level) + "inserting char " + firstChar + " in " +
                                   word + " at " + index + " which makes " + insertCharAt(word, firstChar, index));
                // for each word in the word list, insert the letter at each position
                // and add to the permutations list
                permutations.add(insertCharAt(word, firstChar, index));
            }
        }

        return permutations;
    }

    private static String getIndentationFromLevel(int level) {
        StringBuilder str = new StringBuilder();
        for (int index = 0; index < level; index++) {
            str.append("    ");
        }
        return str.toString();
    }

//    private static ArrayList<String> getLetterPermutations(String str) {
//        ArrayList<String> permutations = new ArrayList<>();
//        System.out.println("begin with arrayList "+ permutations + " str = " + str);
//        if (str == null) return null; //error case
//        else if (str.length() == 0) {
//            //base case, add empty string to the arraylist and return
//            //the permutation of empty string is empty string
//            permutations.add("");
//            return permutations;
//        }
//
//        char firstChar = str.charAt(0);  // get the first char
//        String remainderOfString = str.substring(1); // remove the first char
//
//        // recurse on the rest of the string (without the first char)
//        // cache the remainderOfString on to the heap, so that it can be restored when recurse call returns
//        ArrayList<String> words = getLetterPermutations(remainderOfString);
//
//        System.out.println("in the middle of method arrayList = " + words + " remainderOfString = " + remainderOfString);
//        // iterate through the arraylist of permutations and insert the first char
//        // at each index
//        for(String word : words) {
//            for (int index = 0; index <= word.length(); index++) {
//                // essentially put the first char in each position in the string
//                // starting from first char
//                permutations.add(insertCharAt(word, firstChar, index));
//            }
//        }
//        System.out.println("end with arrayList " + permutations + " str = " + str);
//        return permutations;
//    }


    private static String insertCharAt(String word, String ch, int index) {
        String start = word.substring(0, index);
        String end = word.substring(index);
        return start + ch + end;
    }
}


