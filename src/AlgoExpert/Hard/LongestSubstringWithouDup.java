package AlgoExpert.Hard;

import java.util.*;

public class LongestSubstringWithouDup {
    //startIdx = max(startIdx, lastSeen[char]+1)
    //O(n) time | O(min(n, a)) where n is the len of the string
    // and a is the length of nonrepeating substring

    /**
     * Keep a running map of the latest chars observed in the string
     * If there are duplicates update the map with the latest, do not
     * need to restart a new map when duplicate is observed, simply update
     * the index on the existing char.
     * vars to update:Black
     * - longestInterval{}
     * - lastSeen map
     * Note that when updating startIdx, we want to take max(startIdx, map.get(curChar) + 1)
     * this ensures that we keep the latest startIdx and "ignore the index before starting with new startIdx"
     */
    private static String longestSubstrWithoutDup(String str) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        // keep track of longest interval and start index
        int[] longestInterval = {0, 1}; // use 1 since the shortest substring with no dup is 1
        int startIdx = 0;
        for (int endIdx = 0; endIdx < str.length(); endIdx++) {
            char curChar = str.charAt(endIdx);
            // if we have seen it before we need to reset startIdx
            if (lastSeen.containsKey(curChar)) {
                // update startIdx if we have seen the char before
                // startIdx might be after the lastSeen position so take max
                startIdx = Math.max(startIdx, lastSeen.get(curChar) + 1);
            }
            // new interval is longer, so update the longest interval
            // update the longestInterval if newest one is longer
            // note we check this for every index (not only when we see duplicates)
            if (longestInterval[1] - longestInterval[0] < endIdx - startIdx + 1) {
                longestInterval = new int[] {startIdx, endIdx + 1}; // add 1 to endIdx for substring() op in the end
            }
            // update the lastSeen map with the newest value observed, regardless
            lastSeen.put(curChar, endIdx);
        }
        String result = str.substring(longestInterval[0], longestInterval[1]);
        return result;
    }

    public static String longestSubstringWithoutDuplicationMySol(String str) {
        HashMap<Character, Integer> map = new HashMap<>();
        int[] indices = new int[2];
        int startIdx = 0;
        for (int endIdx = 0; endIdx < str.length(); endIdx++) {
            if (map.get(str.charAt(endIdx)) != null) {
                startIdx = Math.max(startIdx, map.get(str.charAt(endIdx)) + 1);
            }

            if (endIdx - startIdx > indices[1] - indices[0]) {
                indices[0] = startIdx;
                indices[1] = endIdx;
            }

            map.put(str.charAt(endIdx), endIdx);
        }
        // Write your code here
        return str.substring(indices[0], indices[1] + 1);
    }
}
