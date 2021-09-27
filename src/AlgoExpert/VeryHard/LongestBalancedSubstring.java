package AlgoExpert.VeryHard;

import java.util.Stack;

public class LongestBalancedSubstring {
    // O (n^3) time | O(n) space where n is the length of the input string
    public int longestBalancedSubstring(String string) {
        int maxLen = 0;

        for (int i = 0; i < string.length(); i++) {
            // only look at even length substrings
            for (int j = i + 2; j < string.length(); j+=2) {
                if (isBalancedUsingStack(string.substring(i, j))) {
                    int curLen = j - i;
                    maxLen = Math.max(maxLen, curLen);
                }
            }
        }

        return maxLen;
    }

    boolean isBalancedUsingStack(String string) {
        Stack<Character> openParensStack = new Stack<>();

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c == '(') {
                openParensStack.push('(');
            } else if (openParensStack.size() > 0) {
                openParensStack.pop();
            } else {
                return false;
            }
        }
        return openParensStack.size() == 0;
    }

    // O(n) time | O(n) space where n is the length of the input string
    int longestBalancedSubstringStack(String string) {
        int maxLen = 0;
        Stack<Integer> idxStack = new Stack<>();
        idxStack.push(-1); // need -1 index to compensate for the 0th opening

        for (int endIdx = 0; endIdx < string.length(); endIdx++) {
            if (string.charAt(endIdx) == '(') {
                idxStack.push(endIdx);
            } else {
                idxStack.pop();
                if (idxStack.size() == 0) { // equal size open and close
                    idxStack.push(endIdx);
                } else {
                    int balancedSubstringStartIdx = idxStack.peek();
                    // subtract next element in the stack from the curIdx
                    // since the next element in the stack is where the substring starts(startIdx)
                    int curLen = endIdx - balancedSubstringStartIdx;
                    maxLen = Math.max(maxLen, curLen);
                }
            }
        }
        return maxLen;
    }

    //O(n) time | O(1) space where n is the length of the input string
    int longestBalancedSubstringCount(String string) {
        int maxLen = 0;

        int openingCount = 0;
        int closingCount = 0;

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);

            if (c == '(') {
                openingCount += 1;
            } else {
                closingCount += 1;
            }

            if (openingCount == closingCount) {
                maxLen = Math.max(maxLen, closingCount * 2);
            } else if (closingCount > openingCount) {
                openingCount = 0;
                closingCount = 0;
            }
        }

        openingCount = 0;
        closingCount = 0;

        for (int i = string.length() - 1; i > 0; i--) {
            char c = string.charAt(i);

            if (c == '(') {
                openingCount += 1;
            } else {
                closingCount += 1;
            }

            if (openingCount == closingCount) {
                maxLen = Math.max(maxLen, openingCount * 2);
            } else if (openingCount > closingCount) {
                openingCount = 0;
                closingCount = 0;
            }
        }
        return maxLen;
    }

    // O(n) time | O(1) space
    int longestBalancedSubstringByDirection(String string) {
        return Math.max(getLongestBalancedInDirection(string, true),
                getLongestBalancedInDirection(string, false));
    }

    private int getLongestBalancedInDirection(String string, boolean leftToRight) {
        char openingParens = leftToRight ? '(' : ')';
        int startIdx = leftToRight ? 0 : string.length() - 1;
        int step = leftToRight ? 1 : -1;

        int maxLen = 0;

        int openingCount = 0;
        int closingCount = 0;

        int idx = startIdx;
        while (idx >= 0 && idx < string.length()) {
            char c = string.charAt(idx);

            if (c == openingParens) {
                openingCount ++;
            } else {
                closingCount++;
            }

            if (openingCount == closingCount) {
                maxLen = Math.max(maxLen, closingCount * 2);
            } else if (closingCount > openingCount) {
                openingCount = 0;
                closingCount = 0;
            }

            idx += step;
        }
        return maxLen;
    }
}
