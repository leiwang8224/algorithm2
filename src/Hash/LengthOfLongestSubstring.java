package Hash;

import java.util.HashMap;

/**
 * Created by leiwang on 4/2/18.
 */
public class LengthOfLongestSubstring {
    public static void main(String args[]) {

    }

    private int lengthOfLongestSubstr(String str) {
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
