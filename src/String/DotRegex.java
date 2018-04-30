package String;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an array of strings, write a method to return just words that matches with a pattern
 * ocntaining '.' regular expression.
 */
public class DotRegex {
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(evaluate(new String[]{"abc","cde"},"abc")));
    }

    //Time: O(N*M)
    //Space: O(N)
    static private String[] evaluate(String[] words, String pattern) {
        if (words == null || pattern == null)
            throw new IllegalArgumentException();
        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (matchDotRegex(word,pattern)) {
                result.add(word);
            }
        }
        return result.toArray(new String[result.size()]);
    }

    static private boolean matchDotRegex(String word, String pattern) {
        if (Math.abs(word.length() - pattern.length()) > 1)
            return false;
        else if (word.isEmpty() && pattern.isEmpty())
            return true;
        else if (pattern.charAt(0) == '.')
            return matchDotRegex(word.substring(1),pattern.substring(1));
        else {
            boolean partialMatch = word.charAt(0) == pattern.charAt(0);
            return partialMatch && matchDotRegex(word.substring(1),pattern.substring(1));
        }
    }

}
