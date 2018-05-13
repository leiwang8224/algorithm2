package Recursion;

import java.util.Stack;

/**
 * Created by leiwang on 3/28/18.
 */
public class CheckParenBalanced {
    // sort of like a library for parens
    static String open = "([<{";
    static String close = ")]>}";

    public static void main(String[] args) {
        String[] tests = {
                "()[]<>{}",
                "(<",
                "]}",
                "()<",
                "(][)",
                "{(X)[XY]}",
        };
        for (String s : tests) {
            System.out.println(s + " = " + isBalanced(s, ""));
        }

        System.out.println("other method");
        for (String s : tests) {
            System.out.println(s + " = " + areParenthesisBalanced(s.toCharArray()));
        }

        System.out.println("another method");
        for (String s : tests) {
            System.out.println(s + " = " + checkParenthBalanced(s.toCharArray()));
        }
    }

    /**
     *
     1) Declare a character stack S.
     2) Now traverse the expression string exp.
     a) If the current character is a starting bracket (‘(‘ or ‘{‘ or ‘[‘) then push it to stack.
     b) If the current character is a closing bracket (‘)’ or ‘}’ or ‘]’) then pop from stack and
     if the popped character is the matching starting bracket then fine else parenthesis are not balanced.
     3) After complete traversal, if there is some starting bracket left in stack then “not balanced”

     * FUNCTION isBalanced(String input, String stack) : boolean
         IF isEmpty(input)
         RETURN isEmpty(stack)
         ELSE IF isOpen(firstChar(input))
         RETURN isBalanced(allButFirst(input), stack + firstChar(input))
         ELSE IF isClose(firstChar(input))
         RETURN NOT isEmpty(stack) AND isMatching(firstChar(input), lastChar(stack))
         AND isBalanced(allButFirst(input), allButLast(stack))
         ELSE
         ERROR "Invalid character"
     * @param input
     * @param stack
     * @return
     */
    private static boolean isBalanced(String input, String stack) {
        if (input.isEmpty()) {
            return stack.isEmpty();
        } else if (isOpen(input.charAt(0))) { // left parens at char 0
            // increment the position of the string by 1, add char at 0 to stack (in front)
            return isBalanced(input.substring(1), input.charAt(0) + stack);
        } else if (isClose(input.charAt(0))) { // right parens at char 0
            return !stack.isEmpty() &&
                    // get the char from stack and compare to current char from input
                    // compare the index
                    isMatching(stack.charAt(0), input.charAt(0)) &&
                    isBalanced(input.substring(1), stack.substring(1));
        }
        return isBalanced(input.substring(1), stack);
//        return
//                input.isEmpty() ? stack.isEmpty() :
//                    isOpen(input.charAt(0)) ? isBalanced(input.substring(1), input.charAt(0) + stack) :
//                            isClose(input.charAt(0)) ? !stack.isEmpty() &&
//                                    isMatching(stack.charAt(0), input.charAt(0)) &&
//                                    isBalanced(input.substring(1), stack.substring(1)) :
//                                    isBalanced(input.substring(1), stack);
    }

    private static boolean isMatching(char chOpen, char chClose) {
        return open.indexOf(chOpen) == close.indexOf(chClose);
    }

    /**
     * returns true if char exists in close
     * @param c
     * @return
     */
    private static boolean isClose(char c) {
        return close.indexOf(c) != -1;
    }

    /**
     * returns true if char exists in open
     * @param c
     * @return
     */
    private static boolean isOpen(char c) {
        return open.indexOf(c) != -1;
    }


    /**
     * Use a stack to keep track of the chars
     */
    static class stack
    {
        int top=-1;
        char items[] = new char[100];

        void push(char x)
        {
            if (top == 99)
            {
                System.out.println("Stack full");
            }
            else
            {
                items[++top] = x;
            }
        }

        char pop()
        {
            if (top == -1)
            {
                System.out.println("Underflow error");
                return '\0';
            }
            else
            {
                char element = items[top];
                top--;
                return element;
            }
        }

        boolean isEmpty()
        {
            return (top == -1) ? true : false;
        }
    }

    /* Returns true if character1 and character2
       are matching left and right Parenthesis */
    static boolean isMatchingPair(char character1, char character2)
    {
        if (character1 == '(' && character2 == ')')
            return true;
        else if (character1 == '{' && character2 == '}')
            return true;
        else if (character1 == '[' && character2 == ']')
            return true;
        else
            return false;
    }

    static boolean checkParenthBalanced(char exp[]) {
        Stack<Character> s = new Stack<>();

        for (int i = 0; i < exp.length; i ++) {
            if (open.indexOf(exp[i]) != -1) {
                s.push(exp[i]);
            }

            if (close.indexOf(exp[i])!= -1) {
                if (s.isEmpty())
                    return false;
                //TODO check here
                else if (!s.isEmpty() &&
                        (open.indexOf(s.pop()) == -1 || open.indexOf(s.pop()) != close.indexOf(exp[i]))) {
                    return false;
                }
            }
        }

        if (s.isEmpty())
            return true;
        else
            return false;
    }
    /* Return true if expression has balanced
       Parenthesis */
    static boolean areParenthesisBalanced(char exp[])
    {
       /* Declare an empty character stack */
        stack st=new stack();

       /* Traverse the given expression to
          check matching parenthesis */
        for(int i=0;i<exp.length;i++)
        {

          /*If the exp[i] is a starting
            parenthesis then push it*/
            if (exp[i] == '{' || exp[i] == '(' || exp[i] == '[')
                st.push(exp[i]);

          /* If exp[i] is an ending parenthesis
             then pop from stack and check if the
             popped parenthesis is a matching pair*/
            if (exp[i] == '}' || exp[i] == ')' || exp[i] == ']')
            {

              /* If we see an ending parenthesis without
                 a pair then return false*/
                if (st.isEmpty())
                {
                    return false;
                }

             /* Pop the top element from stack, if
                it is not a pair parenthesis of character
                then there is a mismatch. This happens for
                expressions like {(}) */
                else if ( !isMatchingPair(st.pop(), exp[i]) )
                {
                    return false;
                }
            }

        }

       /* If there is something left in expression
          then there is a starting parenthesis without
          a closing parenthesis */

        if (st.isEmpty())
            return true; /*balanced*/
        else
        {   /*not balanced*/
            return false;
        }
    }
}
