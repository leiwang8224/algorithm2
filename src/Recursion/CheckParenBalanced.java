package Recursion;

/**
 * Created by leiwang on 3/28/18.
 */
public class CheckParenBalanced {
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
    }

    /**
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
        } else if (isOpen(input.charAt(0))) {
            return isBalanced(input.substring(1), input.charAt(0) + stack);
        } else if (isClose(input.charAt(0))) {
            return !stack.isEmpty() && isMatching(stack.charAt(0), input.charAt(0)) &&
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

    private static boolean isClose(char c) {
        return close.indexOf(c) != -1;
    }

    private static boolean isOpen(char c) {
        return open.indexOf(c) != -1;
    }
}
