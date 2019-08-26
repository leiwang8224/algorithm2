package String;

import java.util.Arrays;

// Given a string S, check if the letters can be rearranged
// so that two characters that are adjacent to each other are not the same.
//
//        If possible, output any possible result.  If not possible, return the empty string.
//
//        Example 1:
//
//        Input: S = "aab"
//        Output: "aba"
//        Example 2:
//
//        Input: S = "aaab"
//        Output: ""
public class ReorganizeString {
    public static void main(String[] args) {
        System.out.println(reorgString("aab"));

    }

    private static String reorgString (String str) {
        int len = str.length();
        int[] counts = new int[26];
        // perform encoding counts[i] = 100 * (actual count) + i
        // add 100 to the char ASCII value
        // (0 values are where there are no letters)
        // ex: 'z' => counts[25] = 100, 'a' => counts[0] = 100, rest all 0's
        // ex: 2 'z' => counts[25] = 200
        for (char c : str.toCharArray()) {
            counts[c - 'a'] += 100;
        }

        // add the index on top of it to specify what letter
        for (int i = 0; i < 26; i++) {
            counts[i] += i;
        }

        Arrays.sort(counts);

        char[] ans = new char[len];
        int alternatingIndex = 1;
        for (int code : counts) {
            // decode
            int countPerLetter = code / 100;
            // get char back from ASCII encoding,
            // remainder is the actual number
            char ch = (char) ('a' + (code % 100));
            // if number of occurrences of some char is greater
            // than (N + 1) / 2, the task is impossible, aka. can't alternate
            if (countPerLetter > (len + 1) / 2) return "";

            // interleave the chars
            for (int index = 0; index < countPerLetter; index++) {
                // if we are outside of the max index
                // set index = 0 and set the first ch
                if (alternatingIndex >= len) alternatingIndex = 0;
                ans[alternatingIndex] = ch;
                alternatingIndex += 2;
            }
        }

        return String.valueOf(ans);
    }
}
