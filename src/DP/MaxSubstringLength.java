package DP;

/**
 * Created by leiwang on 3/25/18.
 */

/**
 * Find length of the longest substring of a given string of digits,
 * such that sum of digits in the first half and second half is the same
 */
public class MaxSubstringLength {
    public static void main(String args[]) {
        String str = "8283749304344428374";
        int [][] dp = new int[str.length()][str.length()];
        System.out.println(maxSubstringLengthDP(str, dp));
        System.out.println(findLength(str));
        System.out.println(findLengthDP(str,str.length()));
        System.out.println(findLengthDP2(str,str.length()));
    }

    /**
     * brute force method
     * Time O(n3)
     * @param str
     * @return
     */
    static int findLength(String str) {
        int n = str.length();
        int maxlen = 0; // init result

        // choose starting point of every substring
        for (int i = 0; i < n; i ++) {
            // choose ending point of even length substring
            for (int j = i + 1; j < n; j += 2) {
                // find length of current substr
                int length = j - i + 1;

                // calculate left and right sums
                // for current substr
                int leftSum = 0, rightSum = 0;
                for (int k = 0; k < length / 2; k ++) {
                    leftSum += (str.charAt(i + k) - '0');
                    rightSum += (str.charAt(i + k + length/2) - '0');
                }

                // update result if needed
                if (leftSum == rightSum && maxlen < length)
                    maxlen = length;
            }
        }
        return maxlen;
    }

    // O(n2) time
    // O(n2) space
    private static int maxSubstringLengthDP(String str, int[][] dp) {
        int n = str.length();
        int maxLen = 0;
        // lower diagonal of matrix not used (i > j)
        for (int i = 0; i < n; i ++) {
            dp[i][i] = str.charAt(i) - '0';
        }

        for (int len = 2; len <= n; len ++) {
            // pick i and j for current subset
            for (int i = 0; i < n - len + 1; i ++) {
                // i = 0,1,2,3,... len = 2,3,4,...
                int j = i + len - 1; // end of subset
                int k = len / 2; // halfway
                dp[i][j] = dp[i][j-k] + dp[j-k+1][j]; // first half + second half

                // update if len is even
                // sums are same and len is more than maxLen
                if (len % 2 == 0 && dp[i][j-k] == dp[j-k+1][j] && len > maxLen)
                    maxLen = len;
            }
        }
        return maxLen;
    }

    // O(n2) time
    // O(n) space, using one dimension array instead of two
    private static int findLengthDP(String str, int length) {
        // store cumulative sum from
        // first digit to nth digit
        int sum[] = new int[length+1];
        sum[0] = 0;

        // store cumulative sum of digits
        // from first to last digit
        for (int i = 1; i <= length; i ++) {
            //convert chars to int
            sum[i] = (sum[i-1] + str.charAt(i-1)-'0');
        }

        int ans = 0; //init result

        // consider all even length substrings one by one
        for (int len = 2; len <= length; len += 2) {
            for (int i = 0; i <= length-len; i ++) {
                int j = i + len -1;
                // sum of first and second half
                // is same then update ans
                if (sum[i+len/2] - sum[i] == sum[i+len] - sum[i+len/2])
                    ans = Math.max(ans,len);
            }
        }
        return ans;
    }

    // O(n2) time
    // O(1) space
    private static int findLengthDP2(String str, int length) {
        int ans = 0;

        // consider all possible midpoints one by one
        for (int i = 0; i <= length - 2; i++) {
            // for current midpoint i, keep expanding substring on
            // both sides, if sum of both sides becomes equal update ans
            int l = i, r = i +1;

            // init left and right sum
            int lsum = 0, rsum = 0;

            // move on both sides till indexes go out of bounds
            while (r < length && l >= 0) {
                lsum += str.charAt(l) - '0';
                rsum += str.charAt(r) - '0';
                if (lsum == rsum)
                    ans = Math.max(ans, r-l+1);
                l--;
                r++;
            }
        }
        return ans;
    }
}
