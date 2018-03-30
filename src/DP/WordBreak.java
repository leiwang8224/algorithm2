package DP;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by leiwang on 3/28/18.
 */
public class WordBreak {
    public static void main(String args[]) {
        String str = "leetcode";
        Set<String> set = new HashSet<>();
        set.add("leet");
        set.add("code");
    }

    /**
     * Naive approach O(n^2)
     * @param str
     * @param dict
     * @return
     */
    private boolean wordBreak(String str, Set<String> dict) {
        return wordBreakHelper(str, dict, 0);
    }

    private boolean wordBreakHelper(String str, Set<String> dict, int start) {
        if (start == str.length()) {
            return true;
        }

        for (String a : dict) {
            int len = a.length();
            int end = start + len;

            // end index should be <= string length
            if (end > str.length())
                continue;

            if (str.substring(start, start+len).equals(a)) {
                if (wordBreakHelper(str, dict, start + len))
                    return true;
            }
        }
        return false;
    }

    /**
     * DP O(length of str * length dict)
     * @param str
     * @param dict
     * @return
     */
    private static boolean wordBreakDP(String str, Set<String> dict) {
        boolean[] t = new boolean[str.length()+1];
        t[0] = true; // need initial state

        for (int i = 0; i < str.length(); i ++) {
            // should continue from match position
            if (!t[i])
                continue;

            for (String a: dict) {
                int len = a.length();
                int end = i + len;
                if (end > str.length())
                    continue;

                if (t[end]) continue;

                if (str.substring(i, end).equals(a)) {
                    t[end] = true;
                }
            }
        }
        return t[str.length()];
    }

    /**
     * Direct approach with O(N^2)
     * @param str
     * @param dict
     * @return
     */
    private boolean wordBreak3(String str, Set<String> dict) {
        int[] pos = new int[str.length() + 1];

        Arrays.fill(pos, -1);

        pos[0] = 0;

        for (int i = 0; i < str.length(); i ++) {
            if (pos[i] != -1) {
                for (int j = i +1; j <= str.length(); j++) {
                    String sub = str.substring(i, j);
                    if (dict.contains(sub)) {
                        pos[j] = i;
                    }
                }
            }
        }
        return pos[str.length()] != -1;
    }


}
