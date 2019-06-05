package Hash;

import java.util.HashMap;

//Given a String input, find the length of the longest substring that is made up
// of non-repeating characters. For ex, the longest substrings without repeated
// characters in “BCEFGHBCFG” are “CEFGHB” and “EFGHBC”, with length = 6.
// In the case of "FFFFF", the longest substring is "F" with a length of 1.
public class LongestNonRepeatingSubstring {
    public static void main(String[] args) {

    }

    private static int longestNRSubstringLen(String input) {
        if (input == null)
            return 0;
        char[] array = input.toCharArray();
        int prev = 0;

        HashMap<Character, Integer> characterMap = new HashMap<>();

        for (int index = 0; index < array.length; index++) {
            if (!characterMap.containsKey(array[index])) {
                characterMap.put(array[index], index);
            } else {
                prev = Math.max(prev, characterMap.size());
                index = characterMap.get(array[index]);
                characterMap.clear();
            }
        }

        return Math.max(prev, characterMap.size());
    }
}
