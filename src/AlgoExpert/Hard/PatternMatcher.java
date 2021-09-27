package AlgoExpert.Hard;

import java.util.*;
public class PatternMatcher {
    // O(n^2 + m) time | O(n + m) space
    // n^2 in the main method and m in the sub-methods
    // n = len of string
    // m = len of pattern
    // extract the substrings represented by x and y
    // Essentially build up the x and y representation until we match the string given
    // vary length of x from 1 to len(str) and calculate y length
    // build up the string based on length of x, y and start position of y
    public static String[] patternMatcher(String pattern, String str) {
        if (pattern.length() > str.length()) return new String[]{};

        char[] newPattern = convertToArraySwapYAndXIfNotStartWithX(pattern);
        // did we perform the swap?
        boolean didSwapXY = newPattern[0] != pattern.charAt(0);

        // counts of x and y
        Map<Character, Integer> countsXY = new HashMap<>();
        countsXY.put('x', 0);
        countsXY.put('y', 0);

        // update counts and get first Y pos
        int firstYPosInPattern = populateCountsMapAndGetYPos(newPattern, countsXY);

        // check to see if there are y's in the pattern
        if (countsXY.get('y') != 0) {
            // vary the length of X and calculate len of Y
            for (int xLen = 1; xLen < str.length(); xLen++) {
                // len of y = len of string - length of x * number of x's / number of y's
                double yLen =
                        ((double) str.length() - (double) xLen * (double) countsXY.get('x'))
                                / (double) countsXY.get('y');
                // if len of Y is negative or len of Y is not whole number, skip
                // check validity of len of Y
                if (yLen <= 0 || yLen % 1 != 0) continue;
                // absolute y start idx in the string
                int yStartIdxString = firstYPosInPattern * xLen;
                String xString = str.substring(0, xLen);
                String yString = str.substring(yStartIdxString, yStartIdxString + (int) yLen);

                // use the pattern to build up the potential x and y representations
                String potentialMatch = buildPotentialMatch(newPattern,
                                                    xString,
                                                    yString);
                if (str.equals(potentialMatch)) {
                    // reverse the y and x switch
                    return didSwapXY ? new String[] {yString, xString} :
                                        new String[] {xString, yString};
                }
            }
        } else { // there are no y's only x's
            double lenOfX = str.length() / countsXY.get('x');
            if (lenOfX % 1 == 0) { // lenOfX not a decimal (an integer)
                String x = str.substring(0, (int)lenOfX);
                String potentialMatch = buildPotentialMatch(newPattern, x, "");
                if (str.equals(potentialMatch))
                    return didSwapXY ? new String[] {"", x} : new String[] {x, ""};
            }
        }
        return new String[] {};
    }

    /**
     * Switch y with x if the pattern starts with y
     */
    private static char[] convertToArraySwapYAndXIfNotStartWithX(String pattern) {
        char[] patternLetters = pattern.toCharArray();
        if (pattern.charAt(0) == 'x') {
            return patternLetters;
        }

        // first letter is not x, swap x and y's
        for (int i = 0; i < patternLetters.length; i++) {
            if (patternLetters[i] == 'x') {
                patternLetters[i] = 'y';
            } else {
                patternLetters[i] = 'x';
            }
        }
        return patternLetters;
    }

    /**
     * get number of x and y's and put into map
     * and return the position of the first y
     */
    private static int populateCountsMapAndGetYPos(char[] pattern,
                                                   Map<Character, Integer> counts) {
        int firstYPos = -1;
        for (int i = 0; i < pattern.length; i++) {
            char c = pattern[i];
            counts.put(c, counts.get(c) + 1);
            if (c == 'y' && firstYPos == -1) {
                firstYPos = i;
            }
        }
        return firstYPos;
    }

    /**
     * given the pattern and the the potential x and y, build the string
     */
    private static String buildPotentialMatch(char[] pattern,
                                              String x,
                                              String y) {
        StringBuilder potentialMatch = new StringBuilder();
        for (char c : pattern) {
            if (c == 'x') {
                potentialMatch.append(x);
            } else {
                potentialMatch.append(y);
            }
        }
        return potentialMatch.toString();
    }
}
