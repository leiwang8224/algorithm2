package AlgoExpert.Medium;

public class LongestPalindromicSubstring {
    public static void main(String[] args) {

    }

    // O(n^3) time | O(n) space

    /**
     * Generate all combination of substrings from original string
     * All you need is to maintain the longest string, the length info is contained
     */
    private static String longestPalindromicSub(String str) {
        String longest = "";
        // O(n^2) here
        for (int startIndex = 0; startIndex < str.length(); startIndex++) {
            for (int endIndex = startIndex; endIndex < str.length(); endIndex++) {
                String substr = str.substring(startIndex, endIndex + 1);
                if (substr.length() > longest.length() && isPalindrome(substr)) {
                    longest = substr;
                }
            }
        }
        return longest;
    }

    // O(n) here
    // you have to start from both sides of the string and iterate towards the middle
    private static boolean isPalindrome(String substr) {
        int leftIdx = 0;
        int rightIdx = substr.length()-1;
        while (leftIdx < rightIdx) {
            if (substr.charAt(leftIdx) != substr.charAt(rightIdx)) {
                return false;
            }
            leftIdx++;
            rightIdx--;
        }
        return true;
    }

    // O(n^2) time | O(n) space
    /**
     * Find odd and even palindromes and store the start and end indices into array.
     * update the longest start and ending points as you traverse through the array
     * The simplification to O(n^2) instead of O(n^3) comes from there is one for loop
     * and the expansion of the string is done in one sweep instead of a separate method
     * that iterates through the substring again.
     */
    private static String longestPalindromic(String str) {
        // starting off with the first letter of the string
        int[] curLongestStartEnd = {0, 1};
        for (int index = 1; index < str.length(); index++) {
            // for odd number there is a middle point
            int[] odd = getLongestPalindromeFrom(str,
                    index - 1,
                    index + 1);
            // for even number there is no middle point, it's between chars
            int[] even = getLongestPalindromeFrom(str,
                    index - 1,
                    index);
            // check if odd expand is longer than even expand and take the max
            int[] longestStartEnd = odd[1] - odd[0] > even[1] - even[0] ? odd : even;
            // replace curLongestStartEnd with the longest if it's greater
            curLongestStartEnd =
                    curLongestStartEnd[1] - curLongestStartEnd[0] > longestStartEnd[1] - longestStartEnd[0]
                    ? curLongestStartEnd :longestStartEnd;
        }
        return str.substring(curLongestStartEnd[0], curLongestStartEnd[1]);
    }

    /**
     * returns the longest palindrome with start index and end index
     */
    private static int[] getLongestPalindromeFrom(String str,
                                                  int leftIdxToCompare,
                                                  int rightIdxToCompare) {
        while (leftIdxToCompare >= 0 && rightIdxToCompare < str.length()) {
            if (str.charAt(leftIdxToCompare) != str.charAt(rightIdxToCompare)) {
                break;
            }
            leftIdxToCompare--;
            rightIdxToCompare++;
        }
        // returns the start index and end index as an array
        return new int[] {leftIdxToCompare + 1, rightIdxToCompare};
    }
}
