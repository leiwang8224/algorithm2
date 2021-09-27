package Hash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by leiwang on 4/2/18.
 */
public class LengthOfLongestSubstring {
    public static void main(String args[]) {
        String str = "abbbebcbcbs";
        System.out.println("length of the longest = " + lengthOfLongestSubstr(str));
    }

    /**
     * Maintain a set of numbers that does not repeat
     * add the new char at the end of the string if it does not exist in the set
     * remove the old char at the front of the string if the set does have the number
     */
    private static int lengthOfLongestSubStrWindow(String str) {
        Set<Character> set = new HashSet<>();
        int result = 0, startIndex = 0, endIndex = 0;
        while (startIndex < str.length() && endIndex < str.length()) {
            // try to extend the range[startIndex, endIndex]
            if (!set.contains(str.charAt(endIndex))) {
                set.add(str.charAt(endIndex++));
                result = Math.max(result, endIndex - startIndex);
            } else {
                set.remove(str.charAt(startIndex++));
            }
        }

        return result;
    }

    private static int lengthOfLongestSubstr(String str) {
        if (str.length() == 0) return 0;

        HashMap<Character, Integer> map = new HashMap<>();
        int max = 0;
        for (int i = 0, j = 0; i < str.length(); i++) {
            if (map.containsKey(str.charAt(i))) {
                j = Math.max(j, map.get(str.charAt(i)) + 1);
            }
            map.put(str.charAt(i), i);
            max = Math.max(max, i - j + 1);
        }
        return max;
    }
}
