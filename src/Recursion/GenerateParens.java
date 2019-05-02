package Recursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by leiwang on 3/27/18.
 */

/**
 * generate the parenthesis based on the input number
 * ex: input = 3
 *  ()()()
    ()(())
    (()())
    (())()
    ((()))
 */
public class GenerateParens {
    public static void main(String args[]) {
        Set<String> set1 = generateParens(3);
        ArrayList<String> set2 = generateParens2(3);
        System.out.println("generate paren 1");
        for (String str : set1)
            System.out.println(str);
        System.out.println("generate paren 2");
        for (String str : set2)
            System.out.println(str);
        System.out.println("generate paren 3");
        for (String str : generateParens3(3))
            System.out.println(str);
        System.out.println("generate paren 4");
        ArrayList<String> result4 = new ArrayList<>();
        genParenthesis(3,3,new StringBuilder(),result4);
        for (String str : result4) {
            System.out.println(str);
        }

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

    private static ArrayList<String> generateParens3(int count) {
        ArrayList<String> result = new ArrayList<>();
        if (count > 0)
            generateParens3(count,count,"",result);
        return result;
    }

    private static void generateParens3(int left, int right, String tmp, ArrayList<String> result) {
        if (left == 0 && right == 0)
            result.add(tmp);
        else {
            if (left > 0) {
                System.out.println("left" + tmp);
                generateParens3(left-1, right, tmp+"(", result);
            }

            // we can insert a right parenthesis as long as it won't
            // lead to syntax error. we get a syntax error if there
            // are more right parenthesis than left.
            if (right > left) {
                System.out.println("right" + tmp);
                generateParens3(left, right-1, tmp + ")", result);
            }
        }
        return;
    }

    //TODO figure out why this does not work the same as using string?
    private static void genParenthesis (int left, int right, StringBuilder sb, ArrayList<String> result) {
        if (left == 0 && right == 0) {
            result.add(sb.toString());
        } else {
            if (left > 0) {
                System.out.println("left " + sb.toString());
                genParenthesis(left-1, right, sb.append("("),result);
            }

            if (right > left) {
                System.out.println("right " + sb.toString());
                genParenthesis(left, right-1, sb.append(")"),result);
            }
        }
        return;
    }
}
