package AlgoExpert.VeryHard;
import java.util.*;
public class KnuttMorrisPratt {
    // O(n + m) time | O(m) space where m is substring length and n is string length
    boolean knuthMorrisPrattAlgorithm(String string, String substring) {
        int[] pattern = buildPattern(substring);
        return doesMatch(string, substring, pattern);
    }

    private boolean doesMatch(String string, String substring, int[] pattern) {
        int stringIdx = 0;
        int substringIdx = 0;

        while (stringIdx + substring.length() - substringIdx <= string.length()) {
            if (string.charAt(stringIdx) == substring.charAt(substringIdx)) {
                if (substringIdx == substring.length() - 1) return true; //substring idx is at end
                stringIdx++;
                substringIdx++;
            } else if (substringIdx > 0) { // not matching and firstIdx != 0
                substringIdx = pattern[substringIdx - 1] + 1;
            } else {
                stringIdx++;
            }
        }
        return false;
    }

    private int[] buildPattern(String substring) {
        int[] pattern = new int[substring.length()];
        Arrays.fill(pattern, -1);

        int firstIdx = 0;
        int nextIdx = 1;
        while (nextIdx <substring.length()) {
            // found matching letter in the substring
            if (substring.charAt(nextIdx) == substring.charAt(firstIdx)) {
                pattern[nextIdx] = firstIdx; // store the index of the match
                nextIdx ++;
                firstIdx ++;
            } else if (firstIdx > 0) { // not matching and firstIdx != 0, goto the index firstIdx - 1
                                        // where pattern[firstIdx] is the matching char
                firstIdx = pattern[firstIdx - 1] + 1;
            } else { // nextIdx = 0
                nextIdx++;
            }
        }
        return pattern;
    }


}
