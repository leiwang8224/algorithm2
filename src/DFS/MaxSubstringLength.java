package DFS;

/**
 * Created by leiwang on 3/25/18.
 */
public class MaxSubstringLength {
    public static void main(String[] args) {

        String str = "123456789987654321";
        System.out.println(maxSubstringLength(str));
    }

    /**
     * Find length of the longest substring of a given string of digits,
     * such that sum of digits in the first half and second half is the same
     * @param str
     * @return
     */
    private static int maxSubstringLength(String str) {
        int n = str.length();
        int maxLen = 0;

        // i = starting index of substring
        for (int i = 0; i < n; i ++) {
            // j = end index of substring
            for (int j = i + 1; j < n; j+=2) {
                int len = j - i + 1; // current length
                if (maxLen >= len) {
                    continue;
                }

                int lSum = 0, rSum = 0;

                for (int k = 0; k < len / 2; k ++) {
                    lSum += str.charAt(i+k) - '0';
                    rSum += str.charAt(i + k + len / 2) - '0';
                }

                if (lSum == rSum)
                    maxLen = len;
            }
        }
        return maxLen;
    }
}
