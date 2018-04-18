package DP;

/**
 * Created by leiwang on 4/17/18.
 */
public class LongestPalinSubstring {
    public static void main(String[] args) {

    }
//    Algorithm
//
//    We could see that the longest common substring method
//    fails when there exists a reversed copy of a non-palindromic
//    substring in some other part of SS. To rectify this, each time
//    we find a longest common substring candidate, we check if the
//    substring’s indices are the same as the reversed substring’s
//    original indices. If it is, then we attempt to update the longest
//    palindrome found so far; if not, we skip this and find the next
//    candidate.
//
//    This gives us an O(n^2)O(n2) Dynamic Programming solution which uses
//    O(n^2)O(n​2) space
    public static String longestPalin(String s) {
        StringBuilder reversedStr = new StringBuilder();
        for (int index = s.length()-1; index > 0; index--) {
            reversedStr.append(s.charAt(index));
        }
        getLCS(s, reversedStr.toString(), s.length(), reversedStr.length());
        //TODO implement this
        return "";

    }
    public static int getLCS(String str1, String str2, int str1Length, int str2Length) {
        if (str1Length == 0 || str2Length == 0)
            return 0;

        if (str1.charAt(str1Length-1) == str2.charAt(str2Length-1)) // last char same
        {
            return 1 + getLCS(str1, str2, str1Length - 1, str2Length - 1);
        } else {
            return getMax(getLCS(str1, str2, str1Length, str2Length-1), getLCS(str1, str2, str1Length-1, str2Length));
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
