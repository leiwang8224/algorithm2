package AlgoExpert.Medium;

import java.util.HashMap;
import java.util.Stack;

public class BalancedBrackets {
    public static void main(String[] args) {

    }

    /**
     * Form a string for open brackets and closing brackets and use indexOf
     * to find whether a bracket exists (lookup in string returns -1 if char does not exist in string).
     * Form a hashmap for storing the matching brackets, so that when a
     * closing bracket appears, we can look for the corresponding opening bracket.
     * 1. push onto stack if it's open bracket
     * 2. else if not a closing bracket
     *   - check if stack is empty, if it is return false (We should be expecting a corresponding open bracket in stack)
     *   - check if there is corresponding opening bracket in the stack, if so, pop stack
     *   - else we did not find the corresponding opening bracket in stack so return false
     * 3. stack should be empty in the end of the loop
     */
    private static boolean balancedBrackets(String str) {
        // strings for fast lookup to see if it's a bracket (opening or closed)
        String openBrackets = "([{";
        String closeBrackets = ")]}";
        // hashmap for fast lookup for opening bracket given closing bracket
        HashMap<Character, Character> matchingBracketsDict = new HashMap<>();

        matchingBracketsDict.put(')', '(');
        matchingBracketsDict.put(']', '[');
        matchingBracketsDict.put('}', '{');

        // keeping track of all the opening brackets in stack
        Stack<Character> stack = new Stack<>();

        for (int index = 0; index < str.length(); index++) {
            char letter = str.charAt(index);
            // check if letter part of opening brackets, push into stack
            if (openBrackets.indexOf(letter) != -1) {
                stack.push(letter);
                // else if letter a closing bracket
            } else if (closeBrackets.indexOf(letter) != -1) {
                // incoming letter is closing bracket but nothing left in stack, return not balanced
                if (stack.isEmpty()) return false;
                // if there is an opening bracket in the stack then
                // we have balanced brackets, pop from stack
                if (stack.peek() == matchingBracketsDict.get(letter)) {
                    stack.pop();
                } else {
                    // stack does not have the matching opening bracket
                    return false;
                }
            }
            // all other letters just ignore
        }
        return stack.size() == 0;
    }
}
