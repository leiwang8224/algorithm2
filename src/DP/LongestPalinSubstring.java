package DP;

/**
 * Created by leiwang on 4/17/18.
 */

/**
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

 Example 1:

 Input: "babad"
 Output: "bab"
 Note: "aba" is also a valid answer.

 Example 2:

 Input: "cbbd"
 Output: "bb"
 */
public class LongestPalinSubstring {
    public static void main(String[] args) {
        String str = "babad";
        System.out.println(longestPalin2(str));
        System.out.println(longestPalSubstr(str));

    }

    //Time O(n2)
    //Space O(1)
    public static int longestPalSubstr(String str) {
        int maxLength = 1;
        int start = 0;
        int len = str.length();

        int low, high;

        // one by one consider every char as center
        // point of even and length palindromes
        for (int i = 1; i < len; i++) {
            // find the longest even length palindrome with
            // center points as i -1 and i
            low = i - 1;
            high = i;
            while (low >= 0 && high < len && str.charAt(low) == str.charAt(high)) {
                if (high - low + 1 > maxLength) {
                    start = low;
                    maxLength = high - low + 1;
                }
                --low;
                ++high;
            }

            // find the longest odd length palindrome with
            // center point as i
            low = i - 1;
            high = i + 1;
            while (low >= 0 && high < len && str.charAt(low) == str.charAt(high)) {
                if (high - low + 1 > maxLength) {
                    start = low;
                    maxLength = high - low + 1;
                }
                -- low;
                ++ high;
            }
        }
        System.out.println("longest palindrome substring is ");
        printSubStr(str, start, start + maxLength - 1);
        return maxLength;
    }

    private static void printSubStr(String str, int low, int high) {
        System.out.println(str.substring(low, high + 1));
    }

    public static int getLCS(String str1, String str2, int str1Length, int str2Length) {
        if (str1Length == 0 || str2Length == 0)
            return 0;

        if (str1.charAt(str1Length-1) == str2.charAt(str2Length-1)) // last char same
        {
            return 1 + getLCS(str1, str2, str1Length - 1, str2Length - 1);
        } else {
            return getMax(getLCS(str1, str2, str1Length, str2Length-1),
                          getLCS(str1, str2, str1Length-1, str2Length));
        }
    }

    private static int getMax(int x, int y) {
        return (x > y)? x : y;
    }

//    In fact, we could solve it in O(n^2)O(n​2) time using only constant space.
//
//    We observe that a palindrome mirrors around its center. Therefore,
//    a palindrome can be expanded from its center, and there are only 2n - 12n−1 such centers.

//    You might be asking why there are 2n - 12n−1 but not nn centers?
//    The reason is the center of a palindrome can be in between two letters.
//    Such palindromes have even number of letters (such as \textrm{''abba''}”abba”)
//    and its center are between the two \textrm{'b'}’b’s.
    private static String longestPalin2(String s) {
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);

    }

    private static int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }
}
