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

        System.out.println(longestPalSubstr2(str));
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


//    Dynamic Programming:
//
//1) Create a 2D memo pad -
//
//    boolean[][] memo = new boolean[len][len]
//
//    where len is the String's length. We'll use this memo pad to denote positions on the String,
    //and whether or not the substring between the two positions is a palindrome. In particular,
    //memo[start][finish] is true if the String is a palindrome between the indices start and finish.
//
//2) Create 2 variables that will be laters used to obtain the substring -
//
//    int maxSubstrLen = 1;
//    int maxSubstrStartIndex = 0;
//
//
//3) Since each character in the string can be thought of as a palindromic substring,
    // mark the diagonal on the matrix as true :
//
//    for(int i = 0; i < len; i++){
//        memo[i][i] = true;
//    }
//
//
//4) Do the same for substrings of length 2 so that we have enough data to perform a bottom up traversal :
//
//    for(int i = 0; i < len-1; i++){
//        if(str.charAt(i) == str.charAt(i+1)){
//            memo[i][i+1] = true;
//            maxSubstrLen = 2;
//            maxSubstrStartIndex = i;
//        }
//    }
//
//
//5) Now use the memo array to check for substrings of length > 2. The check for palindromicity will look like this :
//
//    if(memo[i+1][j-1] && str.charAt(i) == str.charAt(j)){
//        memo[i][j] = true;
//        maxSubstrLen = l;
//        maxSubstrStartIndex = i;
//    }
//
//
//   That is, the memo pad is used to look up if the interior boundary is a palindrome.
    //   If true, then you check the boundary characters, and if they are the same,
    //   update the variables accordingly. That check can be summarized by this if condition -
//
//            if(memo[i+1][j-1] && str.charAt(i) == str.charAt(j))
//
//
//6) Return the substring -
//
//            return str.substring(maxSubstrStartIndex, maxSubstrStartIndex + maxSubstrLen);
//

    private static String longestPalSubstr2(String str) {
        if (str == null || str.length() < 2) return str;

        int len = str.length();

        // memo[start][finish] is true if the String is palindrome
        // between .charAt(start) and .charAt(finish)
        boolean[][] memo = new boolean[len][len];
        int maxSubstrLen = 1;
        int maxSubstrStartIndex = 0;

        // mark all length 1 substrings as palindromes
        // mark all elements across the matrix as true
        for (int index = 0; index < len; index++) {
            memo[index][index] = true;
        }

        // selectively mark all length 2 substrings as palindromes
        // in a single pass
        for (int index = 0; index < len - 1; index++) {
            // if two consecutive chars are the same
            if (str.charAt(index) == str.charAt(index+1)) {
                // mark the element next to the across element as true
                memo[index][index+1] = true;
                maxSubstrLen = 2;
                maxSubstrStartIndex = index;
            }
        }

        // scan for substrings of length of substring > 2 and length of substring < len.
        // Remember that memo[1][] represents the starting char
        // str.charAt(1) and memo[][1] represents the ending char
        // in our check.
        for (int lenOfSubstring = 3; lenOfSubstring <= len; lenOfSubstring++) {
            // Run the check until you reach the end of the string.
            // i is the start index
            for (int startIndex = 0; startIndex + lenOfSubstring-1 < len; startIndex++) {
                // j is the end index
                int endIndex = startIndex + lenOfSubstring - 1;

                // in a string 'abba', if we are at the second 'b' -> 'abb',
                // 'abba' is a palindrome only if 'bb' were a palindrome and 'a'=='a'
                // at the opposite ends of the string. Can be translated to the following
                // condition using the memo pad:
                // the substring is a palindrome iff: str.charAt(startIndex+1) to
                // str.charAt(endIndex-1) is palindrome
                if (memo[startIndex+1][endIndex-1] && str.charAt(startIndex) == str.charAt(endIndex)) {
                    memo[startIndex][endIndex] = true;
                    maxSubstrLen = lenOfSubstring;
                    maxSubstrStartIndex = startIndex;
                }
            }
        }

        return str.substring(maxSubstrStartIndex, maxSubstrStartIndex + maxSubstrLen);
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
