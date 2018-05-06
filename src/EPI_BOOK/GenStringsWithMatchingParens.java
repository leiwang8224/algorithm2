package EPI_BOOK;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leiwang on 5/4/18.
 */

/**
 * write a program that takes as input a number and returns all the strings with that
 * number of matched pairs of parens.
 */
public class GenStringsWithMatchingParens {
    public static void main(String[] args) {
        List<String> result = generateBalancedParentheses(2);
        for (String str : result)
            System.out.println(str);

    }

    private static List<String> generateBalancedParentheses(int numPairs) {
        List<String> result = new ArrayList<>();
        generateBalancedParenthesesHelper(numPairs, numPairs, "", result);
        return result;
    }

    private static void generateBalancedParenthesesHelper(int numLeftParensNeeded, int numRightParensNeeded, String validPrefix, List<String> result) {
        if (numRightParensNeeded == 0) {
            result.add(validPrefix);
            return;
        }

        if (numLeftParensNeeded > 0) { // able to insert '('
            generateBalancedParenthesesHelper(numLeftParensNeeded - 1,
                                            numRightParensNeeded,
                                            validPrefix + "(", result);
        }

        if (numLeftParensNeeded < numRightParensNeeded) { // able to insert ')'
            generateBalancedParenthesesHelper(numLeftParensNeeded,
                                            numRightParensNeeded - 1,
                                            validPrefix + ")", result);
        }
    }
}
