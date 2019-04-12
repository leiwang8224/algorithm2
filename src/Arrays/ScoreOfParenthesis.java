package Arrays;

import java.util.Stack;
//                      Given a balanced parentheses string S, compute the score of the string based on the following rule:
//
//                              () has score 1
//                              AB has score A + B, where A and B are balanced parentheses strings.
//                              (A) has score 2 * A, where A is a balanced parentheses string.
//
//
//
//                              Example 1:
//
//                              Input: "()"
//                              Output: 1
//
//                              Example 2:
//
//                              Input: "(())"
//                              Output: 2
//
//                              Example 3:
//
//                              Input: "()()"
//                              Output: 2
//
//                              Example 4:
//
//                              Input: "(()(()))"
//                              Output: 6

public class ScoreOfParenthesis {
    public static void main(String[] args) {

    }

//    Call a balanced string primitive if it cannot be partitioned into two non-empty balanced strings.
//
//    By keeping track of balance (the number of ( parentheses minus the number of ) parentheses), we
//    can partition S into primitive substrings S = P_1 + P_2 + ... + P_n. Then, score(S) = score(P_1)
//    + score(P_2) + ... + score(P_n), by definition.
//
//    For each primitive substring (S[i], S[i+1], ..., S[k]), if the string is length 2, then the
//    score of this string is 1. Otherwise, it's twice the score of the substring (S[i+1], S[i+2], ..., S[k-1]).
    private static int scoreOfParenthesis(String s) {
        return findScore(s, 0, s.length());
    }

    private static int findScore(String s, int i, int length) {
        // Score of balanced string s[i:j]
        int ans = 0, bal = 0;

        // Split string into primitives
        for (int k = i; k < length; k++) {
            bal += s.charAt(k) == '(' ? 1 : -1;
            if (bal == 0) {
                if (k - i == 1) ans++;
                else ans += 2 * findScore(s, i + 1, k);
                i = k + 1;
            }
        }
        return ans;
    }


//    Every position in the string has a depth - some number of matching parentheses surrounding it.
    //    For example, the dot in (()(.())) has depth 2, because of these parentheses: (__(.__))
//
//    Our goal is to maintain the score at the current depth we are on. When we see an opening bracket,
    //    we increase our depth, and our score at the new depth is 0. When we see a closing bracket,
    //    we add twice the score of the previous deeper part - except when counting (), which has a score of 1.
//
//    For example, when counting (()(())), our stack will look like this:
//
//            [0, 0] after parsing (
//    [0, 0, 0] after (
//            [0, 1] after )
//            [0, 1, 0] after (
//    [0, 1, 0, 0] after (
//            [0, 1, 1] after )
//    [0, 3] after )
//            [6] after )

    private static int scoreOfParenthesisStack(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0); // the score of current frame

        for (char c : s.toCharArray()) {
            if (c == '(')
                stack.push(0);
            else {
                int v = stack.pop();
                int w = stack.pop();
                stack.push(w + Math.max(2 * v, 1));
            }
        }
    return stack.pop();
    }

//    Intuition
//
//    The final sum will be a sum of powers of 2, as every core (a substring (), with score 1)
    //    will have it's score multiplied by 2 for each exterior set of parentheses that contains that core.
//
//    Algorithm
//
//    Keep track of the balance of the string, as defined in Approach #1. For every ) that immediately
    //    follows a (, the answer is 1 << balance, as balance is the number of exterior set of parentheses that contains this core.

    private static int scoreOfParenthesis2(String s) {
        int ans = 0, bal = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                bal ++;
            } else {
                bal --;
                if (s.charAt(i-1) == '(')
                    ans += 1 << bal;
            }
        }

        return ans;
    }
}
