package CTCI.RecursionAndDP;

import java.util.ArrayList;

/**
 * Write a method to compute all permutations of a string of unique chars.
 */
public class PermutationsWithoutDups {
    public static void main(String[] args) {
        ArrayList<String> list = getPerms("ab");
        ArrayList<String> list2 = getPerms2("ab");
        ArrayList<String> list3 = callGetPerms3("ab");
        System.out.println("There are " + list.size() + " permutations");
        for (String s : list) {
            System.out.println(s);
        }

        for (String s : list2) {
            System.out.println(s);
        }

        for (String s : list3) {
            System.out.println(s);
        }
        
    }

    /**
     * Build from permutations of first n-1 chars
     * Build from base case:
     * The only permutation of a1 is the string a1, so:
     * P(a1) = a1
     * Case: permutaitons of a1a2
     * P(a1,a2) = a1a2 and a2a1
     * Case: permutations of a1a2a3
     * P(a1,a2,a3) = a1,a2,a3, a1,a3,a2, a2,a1,a3, a2,a3,a1, a3,a1,a2, a3,a2,a1,
     * P(a1,a2,a3,a4) = take all permutations of a1,a2,a3 and add a4 into all possible locations.
     */
    private static ArrayList<String> getPerms(String str) {
        if (str == null) return null;

        ArrayList<String> permutations = new ArrayList<>();

        if (str.length() == 0) { // base case
            permutations.add("");
            return permutations;
        }

        char first = str.charAt(0); // get first char
        String remainder = str.substring(1); // remove first char
        // recurse on the rest of the chars after removing first char
        // return the permutations list when the str is empty
        ArrayList<String> words = getPerms(remainder);   // a1,a2 or a1,a2,a3 or a1

        // for each word combo, insert first char at all possible positions
        // and add to permutations
        for (String word : words) {
            for (int index = 0; index <= word.length(); index++) {
                String s = inserCharAt(word, first, index);
                permutations.add(s);
            }
        }
        return permutations;
        
    }

    private static String inserCharAt(String word, char charToInsert, int index) {
        String start = word.substring(0, index);
        String end = word.substring(index);
        return start + charToInsert + end;
    }

    /**
     * Building from permutations of all n-1 char substrings
     * Base case: single char strings
     * The only permutation of a1 is the string a1 so:
     * P(a1) = a1
     * Case two char strings
     * P(a1a2) = a1a2 and a2a1
     * P(a2a3) = a2a3 and a3a2
     * P(a1a3) = a1a3 and a3a1
     * Case three char strings
     * In essence we just need to try each char as the first char and then append the permutations
     * P(a1a2a3) = {a1 + P(a2a3)} + {a2 + P(a1a3)} + {a3 + P(a1a2)}
     * {a1 + P(a2a3)} -> a1a2a3, a1a3a2
     * {a2 + P(a1a3)} -> a2a1a3, a2a3a1
     * {a3 + P(a1a2)} -> a3a1a2, a3a2a1
     * @param remainder
     * @return
     */
    private static ArrayList<String> getPerms2(String remainder) {
        ArrayList<String> result = new ArrayList<>();

        // base case
        if (remainder.length() == 0) {
            result.add(""); // be sure to return empty string
            return result;
        }
        
        for (int index = 0; index < remainder.length(); index++) {
            // remove char index and find permutations of remaining chars
            String before = remainder.substring(0, index);
            // note that the after chops off the first letter of the string
            String after = remainder.substring(index+1);
            System.out.println("before is " + before + " after is " + after + " char is " + remainder.charAt(index) + " index is " + index);

            // note that since this is recursive, index does not get incremented
            // after the recursion returns. Essentially we are chopping off the first char
            // of the string and putting it in the char var. Recurse on the remainder string
            // without the first char.
            // Once index reaches above 0, before will have letters. Ex: index = 1, before = first letter
            // Example:
            //            before is  after is bcde char is a index is 0
            //            before is  after is cde char is b index is 0
            //            before is  after is de char is c index is 0
            //            before is  after is e char is d index is 0
            //            before is  after is  char is e index is 0
            ArrayList<String> partialsList = getPerms2(before + after);
            char charAtIndexPrefix = remainder.charAt(index);

            // prepend char index to each perm (pass the perms back up the stack)
            for (String stringInPartials : partialsList) {
                result.add(charAtIndexPrefix + stringInPartials);
            }
        }
        return result;
    }

    /**
     * As an alternative to getPerms2, instead of passing the permutations back up the stack,
     * we can push the prefix down the stack. When we get to the bottom(base case),
     * prefix holds a full permutation.
     * @param prefix
     * @param remainder
     * @param result
     * @return
     */
    private static void getPerms3(String prefix, String remainder, ArrayList<String> result) {
        if (remainder.length() == 0) result.add(prefix);
        int len = remainder.length();

        for (int index = 0; index < len; index++) {
            String before = remainder.substring(0, index);
            String after = remainder.substring(index + 1, len);
            char charAtIndex = remainder.charAt(index);
            // prefix is the char at the index, push the prefix down the stack
            getPerms3(prefix + charAtIndex, before + after, result);
        }
    }

    private static ArrayList<String> callGetPerms3(String str) {
        ArrayList<String> result = new ArrayList<>();
        getPerms3("", str, result);
        return result;
    }

}
