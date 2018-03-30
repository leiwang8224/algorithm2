package Recursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by leiwang on 3/27/18.
 */
public class GenerateParens {
    public static void main(String args[]) {
        generateParens(3);
    }

    /**
     * for n = 2: (()) ()()
     * for n = 3:
     * (()) -> (()())  inserted pair after 1st left paren
     *      -> ((()))  inserted pair after 2nd left parent
     *      -> ()(())  inserted pair at beginning of string
     * ()() -> (())()  inserted pair after 1st left paren
     *      -> ()(())  inserted pair after 2nd left paren
     *      -> ()()()  inserted pair at beginning of string
     * Not efficient as we are wasting time generating duplicate strings
     * @param remaining
     * @return
     */
    private static Set<String> generateParens(int remaining) {
        Set<String> set = new HashSet<>();
        if (remaining == 0) {
            set.add("");
        } else {
            Set<String> prev = generateParens(remaining - 1);
            for (String str : prev) {
                for (int i = 0; i < str.length(); i++) {
                    if (str.charAt(i) == '(') {
                        String s = insertInside(str, i);
                        set.add(s);
                    }
                }
            set.add("()" + str);
            }
        }
        return set;
    }

    private static String insertInside(String str, int leftIndex) {
        String left = str.substring(0, leftIndex + 1);
        String right = str.substring(leftIndex + 1, str.length());
        return left + "()" + right;
    }

    /**
     * More efficient by keeping track of number of left and right paren
     * 2 criterias for using left or right paren
     * - Left Paren: As long as we haven't used up all the left paren, we can always insert a left paren
     * - Right Paren: We can insert right paren as long as there is no syntax error, there is syntax error
     * when there is more right paren than left
     * @param list
     * @param leftRem
     * @param rightRem
     * @param str
     * @param count
     */
    private static void addParen(ArrayList<String> list, int leftRem, int rightRem, char[] str, int count) {
        if (leftRem < 0 || rightRem < leftRem) return;
        if (leftRem == 0 && rightRem == 0) {
            String s = String.copyValueOf(str);
            list.add(s);
        } else {
            // add left paren, if there are any left paren left
            if (leftRem > 0) {
                str[count] = '(';
                addParen(list, leftRem - 1, rightRem, str, count + 1);
            }

            // add right paren, if expression is valid
            if (rightRem > leftRem) {
                str[count] = ')';
                addParen(list, leftRem, rightRem - 1, str, count + 1);
            }
        }
    }

    private static ArrayList<String> generateParens2(int count) {
        char[] str = new char[count*2];
        ArrayList<String> list = new ArrayList<>();
        addParen(list, count, count, str, 0);
        return list;
    }
}
