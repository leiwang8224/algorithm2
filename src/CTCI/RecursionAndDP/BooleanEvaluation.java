package CTCI.RecursionAndDP;

import java.util.HashMap;

/**
 * Given a boolean expression consist of the symbols 0 (false), 1 (true), and (AND), | (OR), and ^ (XOR),
 * and a desired boolean result value result, implement a function to count the number of ways of parenthesizing
 * the expression such that it evaluates to result.
 *
 * Example:
 * countEval("1^0|0|1", false) -> 2
 * countEval("0&0&0&1^1|0" true) -> 10
 */
public class BooleanEvaluation {
    public static void main(String[] args) {

    }

    /**
     * Consider an expression like 0^0&0^1|1 and the target result true. How can we break down
     * countEval(0^0&0^1|1, true) into smaller problems?
     *
     * We could essentially iterate through each possible place to put a parenthesis.
     *
     * countEval(0^0&0^1|1, true) =
     *  countEval(0^0&0^1|1 where paren around char 1, true)
     * +countEval(0^0&0^1|1 where paren around char 3, true)
     * +countEval(0^0&0^1|1 where paren around char 5, true)
     * +countEval(0^0&0^1|1 where paren around char 7, true)
     *
     * Now what? Let's look at just one of those expressions - the paren around char 3. This gives
     * us (0^0)&(0^1).
     *
     * In order to make that expression true, both the left and the right sides must be true.
     * So:
         * left = "0^0"
         * right = "0^1|1"
         * countEval(left & right, true) = countEval(left, true) * countEval(right, true)
     * The reason we multiply the results of the left and right sides is that each
     * result from the two sides cna be paired up with each other to form a unique
     * combination.
     *
     * Each of those terms can now be decomposed into smaller problems in a similar
     * process.
     *
     * What happens when we have an "|"(OR)? Or an "^"(XOR)?
     *
     * If it's an OR, then either the left or the right side must be true - or both.
     *      countEval(left | right, true) = countEval(left, true) * countEval(right, false)
     *                                    + countEval(left, false) * countEval(right, true)
     *                                    + countEval(left, true) * countEval(right, true)
     *
     * If it's an XOR, then the left or the right side can be true, but not both.
     *      countEval(left ^ right, true) = countEval(left, true) * countEval(right, false)
     *                                    + countEval(left, false) * countEval(right, true)
     *
     * What if we were trying to make the result false instead? We can switch up the logic
     * from above:
     *      countEval(left & right, false) = countEval(left, true) * countEval(right, false)
     *                                     + countEval(left, false) * countEval(right, true)
     *                                     + countEval(left, false) * countEval(right, false)
     *      countEval(left | right, false) = countEval(left, false) * countEval(right, false)
     *      countEval(left ^ right, false) = countEval(left, false) * countEval(right, false)
     *                                     + countEval(left, true) * countEval(right, true)
     *
     * Alternatively, we can just use the same logic from the above and subtract it out from
     * the total number of ways of evaluating the expression.
         * totalEval(left) = countEval(left, true) + countEval(left, false)
         * totalEval(right) = countEval(right, true) + countEval(right, false)
         * totalEval(expression) = totalEval(left) * totalEval(right)
         * countEval(expression, false) = totalEval(expression) - countEval(expression, true)
     */
    static int count = 0;
    static boolean stringToBool(String c) {
        return c.equals("1") ? true : false;
    }

    static int countEval(String s, boolean result) {
        count++;

        // if there is no chars left, return false (0 in int)
        if (s.length() == 0) return 0;
        // if there is one char left, return the value of the char (1 for true, 0 for false)
        if (s.length() == 1) return stringToBool(s) == result ? 1 : 0;

        int ways = 0;

        // iterate through alternate numbers to pick out left and right substrings
        for (int index = 1; index < s.length(); index += 2) {
            // pick out the char and take left substring and right substring
            char c = s.charAt(index);
            String leftSubstring = s.substring(0, index);
            String rightSubstring = s.substring(index + 1, s.length());
            int leftTrue = countEval(leftSubstring, true);
            int leftFalse = countEval(leftSubstring, false);
            int rightTrue = countEval(rightSubstring, true);
            int rightFalse = countEval(rightSubstring, false);
            int total = (leftTrue + leftFalse) * (rightTrue + rightFalse);

            int totalTrue = 0;

            if (c == '^') {
                // required: one true and one false
                totalTrue = leftTrue * rightFalse + leftFalse * rightTrue;
            } else if (c == '&') {
                // required: both true
                totalTrue = leftTrue * rightTrue;
            } else if (c == '|') {
                // required: anything but both false
                totalTrue = leftTrue * rightTrue + leftFalse * rightTrue + leftTrue * rightFalse;
            }

            int subWays = result ? totalTrue : total - totalTrue;
            ways += subWays;
        }

        return ways;
    }

    /**
     * Note that the tradeoff of computing the false results from the true ones, and
     * of computing the {leftTrue, rightTrue, leftFalse, and rightFalse} values upfront,
     * is a small amount of extra work in some cases. For example, if we are looking for
     * the ways that an AND (&) can result in true, we never would have needed the leftFalse
     * and rightFalse results. Likewise, if we are looking for the ways that an OR (|) can
     * result in false, we never would have needed the leftTrue and rightTrue results.
     *
     * Our current code is blind to what we do and don't actually need to do and instead
     * just computes all of the values. This is probably a reasonable tradeoff to make (especially
     * given the constraints of whiteboard coding) as it makes our code substantially shorter
     * and less tedious to write. Whichever approach you make, you should discuss the tradeoffs
     * with your interviewer.
     *
     * That said, there are more important optimizations we can make.
     *
     * Optimized solutions:
     * If we follow the recursive path, we will note that we end up doing the same computation
     * repeatedly.
     * Consider the expression 0^0&0^1|1 and these recursion paths:
     * - Add parens around char 1. (0)^(0&0^1|1)
     *      - Add parens around char 3. (0)^((0)&(0^1|1))
     * - Add parens around char 3. (0^0)&(0^1|1)
     *      - Add parens around char 1. ((0)^(0))&(0^1|1)
     *
     * Although these two expressions are different, they have a similar component: (0^1|1).
     * We should reuse our effort on this.
     *
     * We can do this by using memoization, or a hash table. We just need to store the
     * result of countEval(expression, result) for each expression and result. If we see
     * an expression that we have calculated before, we just return it from the cache.
     */
    static int countEval(String s, boolean result, HashMap<String, Integer> memo) {
        count ++;
        if (s.length() == 0) return 0;
        if (s.length() == 1) return stringToBool(s) == result ? 1 : 0;
        if (memo.containsKey(result + s)) return memo.get(result + s);

        int ways = 0;

        for (int index = 1; index < s.length(); index+=2) {
            char c = s.charAt(index);
            String left = s.substring(0, index);
            String right = s.substring(index + 1, s.length());
            int leftTrue = countEval(left, true, memo);
            int leftFalse = countEval(left, false, memo);
            int rightTrue = countEval(right, true, memo);
            int rightFalse = countEval(right, false, memo);
            int total = (leftTrue + leftFalse) * (rightTrue + rightFalse);

            int totalTrue = 0;
            if (c == '^') {
                totalTrue = leftTrue * rightFalse + leftFalse * rightTrue;
            } else if (c == '&') {
                totalTrue = leftTrue * rightTrue;
            } else if (c == '|') {
                totalTrue = leftTrue * rightTrue + leftFalse * rightTrue + leftTrue * rightFalse;
            }

            int subWays = result ? totalTrue : total - totalTrue;
            ways += subWays;
        }

        memo.put(result + s, ways);
        return ways;
    }

    static int countEval2(String s, boolean result) {
         count++;
         if (s.length() == 0) return 0;
         if (s.length() == 1) return stringToBool(s) == result ? 1 : 0;

         int ways = 0;

         for (int index = 1; index < s.length(); index += 2) {
             char c = s.charAt(index);
             String left = s.substring(0, index);
             String right = s.substring(index + 1, s.length());
             
         }
         return ways;
    }


}
