package CTCI.RecursionAndDP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Implement an algorithm to print all valid (properly opened and closed) combos of n pairs of parenthesis
 */
public class Parens {
    public static void main(String[] args) {
        Set<String> list = generateParens(4);
        for (String s : list) {
            System.out.println(s);
        }
        
        ArrayList<String> list2 = generateParens2(4);
        StringBuilder result = new StringBuilder();

        for (String s : list2) {
            result.append(s+",");
        }
        System.out.println(result.toString());

    }

    private static String insertInside(String str, int leftIndex) {
        String left = str.substring(0, leftIndex + 1);
        String right = str.substring(leftIndex + 1);
        return  left + "()" + right;
    }

    /**
     * Apply a recursive approach where we build the solution for f(n) by adding
     * pairs of parenthesis to f(n-1).
     * Consider n = 3:
     * (()())   ((()))  ()(())  (())()  ()()()
     * How might we build this from n = 2?
     * (()) ()()
     * We can do this by inserting a pair of parenthesis inside every existing pair
     * of parenthesis, as well as one at the beginning of the string.
     * Any other places that we could insert parenthesis, such as at the end
     * of the string, would reduce to the earlier cases.
     *
     * So we have the following:
     * (()) -> (()())   inserted pair after 1st left paren
     *      -> ((()))   inserted pair after 2nd left paren
     *      -> ()(())   inserted pair at beginning of string
     * ()() -> (())()   inserted pair after 1st left paren
     *      -> ()(())   inserted pair after 2nd left paren
     *      -> ()()()   inserted pair at beginning of string
     * We will need to check duplicate values before adding a string to the list.
     *
     * @param remaining
     * @return
     */
    private static Set<String> generateParens(int remaining) {
        Set<String> set = new HashSet<>();
        if (remaining == 0) {
            set.add("");
        } else {
            // start from empty set then self generate ()
            // then recursion gets back the () and operate on that
            Set<String> prev = generateParens(remaining - 1);

            /**
             * for printing purpose only ****
             */
            StringBuilder prevSet = new StringBuilder();
            for (String element : prev) {
                prevSet.append(element + ",");
            }
            System.out.println("string set = " + prevSet.toString());
            /*******************************/
            for (String str : prev) {
                System.out.println("selecting string " + str + " from " + prevSet.toString());
                for (int index = 0; index < str.length(); index++) {
                    System.out.println("selecting char " + str.charAt(index) + " at index " + index + " in the string " + str);
                    if (str.charAt(index) == '(') {
                        String s= insertInside(str, index);
                        // Add s to set if it is not already in there.
                        // Note: HashSet automatically checks for duplicates
                        // before adding so an explicit check is not necessary
                        System.out.println("after inserting () " + s + " at position " + index);
                        set.add(s);
                    }
                }
                // first iteration, we just add () to empty string to start off
                set.add("()" + str);
            }
        }

        return set;
    }

    /**
     * A more efficient method is not have to come up with duplicate strings.
     * We can avoid this duplicate string issue by building the string from scratch.
     * Under this approach, we add left and right parens, as long as our expression stays valid.
     * On each recursive call, we have the index for a particular char in the string. we need
     * to select either a left or right paren. When can we use a left paren, and when can we use a right
     * paren?
     * 1. Left paren: as long as we haven't used up all the left parentheses, we can always insert a left paren
     * 2. Right Paren: we can insert a right paren as long as it won't lead to a syntax error.
     *    When will we get a syntax error? We will get a syntax error if there are more right paren than left.
     * So, we simply keep track of the number of left and right parens allowed. If there are left parens
     * remaining, we will insert a left paren and recurse. If there are more right parens remaining than left
     * (ie. if there are more left parens in use than right parens), then we will insert a right paren and recurse.
     * @param list
     * @param leftRemaining
     * @param rightRemaining
     * @param charArray
     * @param index
     */
    private static void addParen(ArrayList<String> list,
                                 int leftRemaining,
                                 int rightRemaining,
                                 char[] charArray,
                                 int index) {
        // left remaining has surpassed right remaining
        if (leftRemaining < 0 || rightRemaining < leftRemaining) return; // invalid state

        // out of left and right parenthesis
        if (leftRemaining == 0 && rightRemaining == 0) {
            // use a copy of the str so we don't add the pointer of the string
            list.add(String.copyValueOf(charArray));
        } else {
            charArray[index] = '(';   // add left and recurse
            addParen(list, leftRemaining -1, rightRemaining, charArray, index+1);

            charArray[index] = ')';   // add right and recurse
            addParen(list, leftRemaining, rightRemaining-1, charArray, index+1);
        }
    }

    private static ArrayList<String> generateParens2(int count) {
        char[] str  = new char[count * 2];
        ArrayList<String> list = new ArrayList<>();
        addParen(list, count, count, str, 0);
        return list;
    }
    
}
