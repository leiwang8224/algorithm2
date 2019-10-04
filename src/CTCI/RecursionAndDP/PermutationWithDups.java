package CTCI.RecursionAndDP;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Write method to compute all permutations of a string whose chars
 * are not necessarily unique. The list of permutations should not have dups.
 */
public class PermutationWithDups {
    public static void main(String[] args) {
        String s = "aabbccc";
        ArrayList<String> result = printPerms(s);
        System.out.println("Count: " + result.size());
        for (String r : result) {
            System.out.println(r);
        }

    }

    /**
     * One simple way is to do the same work to check if a permutation has been created before
     * and then, if not, add it to the list. A simple hash table will do the trick here.
     * This solution will take O(N!) time in the worst case.
     * While it's true that we can't beat this worst case time, we should be able to design
     * an algorithm to beat this in many cases. Consider a string with all duplicate chars.
     * It would take a long time even through there is only one unique permutation.
     * Ideally, we would like to only create the unique permutations, rather than
     * create every permutation and then ruling out the duplicates.
     * We can start with computing the count of each letter with hash table. For a string
     * such as aabbbbc, this would be:
     * a -> 2 | b -> 4 | c -> 1
     * Let's imagine generating a permutation of this string represented as a hash table.
     * The first choice we make is whether to use a, b, or c as the first char. After that,
     * we have a subproblem to solve: find all permutations of the remaining chars, and append
     * those to the already picked 'prefix'.
     * P(a->2 | b->4 | c->1) = {a + P(a->1 | b->4 | c->1)} +
     *                         {b + P(a->2 | b->3 | c->1)} +
     *                         {c + P(a->2 | b->4 | c->0)}
     * P(a->1 | b->4 | c->1) = {a + P(a->0 | b->4 | c->1)} +
     *                         {b + P(a->1 | b->3 | c->1)} +
     *                         {c + P(a->1 | b->4 | c->0)}
     * P(a->2 | b->3 | c->1) = {a + P(a->1 | b->2 | c->1)} +
     *                         {b + P(a->2 | b->2 | c->0)} +
     *                         {c + P(a->2 | b->3 | c->0)}
     * P(a->2 | b->4 | c->0) = {a + P(a->1 | b->4 | c->0)} +
     *                         {b + P(a0>2 | b->3 | c->0)}
     * Eventually we will reach to no chars remaining
     */

    /**
     * Build frequency table of all the chars in the string
     */
    private static HashMap<Character, Integer> buildFreqTable(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            if (!map.containsKey(c)) {
                map.put(c, 0);
            }
            map.put(c, map.get(c) + 1);
        }

        return map;
    }

    private static void printPerms(HashMap<Character, Integer> freqTable,
                                   String prefix,
                                   int numCharsRemaining,
                                   ArrayList<String> result) {
        if (numCharsRemaining == 0) {
            result.add(prefix);
            return;
        }

        /**
         * for each char in the freq table, if the number of chars is more than 0
         * decrement the count for that char and recurse. Then put the original count
         * back in to the hashtable
         * for loop is used to recurse on each char in the frequency table
         */
        for (Character c : freqTable.keySet()) {
            int count = freqTable.get(c);
            if (count > 0) {
                freqTable.put(c, count-1);
                System.out.println("freqTable before recurse" + freqTable.toString() + " current char = " + c + " prefix = " + prefix);
                printPerms(freqTable,
                           prefix + c,                          // first char appended (pick the first letter and recurse on the rest, hence freqTable.put(c,count-1)
                           numCharsRemaining - 1,   // decrement number of chars remaining
                           result);
                System.out.println("freqTable after recurse " + freqTable.toString()+ " current char = " + c+ " prefix = " + prefix);
                freqTable.put(c, count);        // update the table by taking the count back
                System.out.println("freqTable after recurse recover " + freqTable.toString()+ " current char = " + c+ " prefix = " + prefix);
            }
        }
    }

    private static ArrayList<String> printPerms(String s) {
        ArrayList<String> result = new ArrayList<>();
        HashMap<Character, Integer> map = buildFreqTable(s);
        printPerms(map, "", s.length(), result);
        return result;
    }
}
