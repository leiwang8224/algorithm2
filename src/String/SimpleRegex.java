package String;

public class SimpleRegex {
/**
 * Can you write a method to analyze some strings with regular expressions and return true if the
 * expression matches with the word? Expressions supported are:
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 */
    public static void main(String[] args) {

    }

    private static boolean evaluate(String source, String pattern) {
        if (source == null || pattern == null) {
            throw new IllegalArgumentException();
        }

        if (pattern.length() == 0) return source.length() == 0;

        // length 1 is special case
        if(pattern.length() == 1 || pattern.charAt(1) != '*') {
            if (source.length() < 1 || (pattern.charAt(0) != '.' && source.charAt(0) != pattern.charAt(0)))
                return false;
            return evaluate(source.substring(1),pattern.substring(1));
        } else {
            int len = source.length();

            int i = -1;
            while (i < len && (i < 0 || pattern.charAt(0) == '.' || pattern.charAt(0) == source.charAt(i))) {
                if (evaluate(source.substring(i+1),pattern.substring(2))) return  true;
                i++;
            }
            return false;
        }
    }
}
