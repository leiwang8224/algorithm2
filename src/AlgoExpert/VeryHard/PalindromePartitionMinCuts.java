package AlgoExpert.VeryHard;
import java.util.*;

public class PalindromePartitionMinCuts {
    // O(n^3) time | O(n^2) space
    public int palindromePartioningMinCuts(String str) {
        boolean[][] palindromes = new boolean[str.length()][str.length()];
        // O(n^2)
        for (int rowStartIdx = 0; rowStartIdx < str.length(); rowStartIdx++) {
            for (int colEndIdx = rowStartIdx; colEndIdx < str.length(); colEndIdx++) {
                palindromes[rowStartIdx][colEndIdx] =
                        isPalindrome(str.substring(rowStartIdx, colEndIdx+1));
            }
        }

        int[] numMinCuts = new int[str.length()];
        Arrays.fill(numMinCuts, Integer.MAX_VALUE);

        // for each index find the minCuts needed
        for (int curIdx = 0; curIdx < str.length(); curIdx++) {
            if (palindromes[0][curIdx]) { // if substring is palindrome, we need 0 cuts
                numMinCuts[curIdx] = 0;
            } else {
                // substring not a palindrome, but can we insert
                // a cut to make it palindrome?
                // tentatively put the prevIdx + 1 into current idx element
                numMinCuts[curIdx] = numMinCuts[curIdx - 1] + 1;
                // for each index take substring from index 1
                // to curIdx and see if we can make palindrome O(n)
                for (int prevIdx = 1; prevIdx < curIdx; prevIdx++) {
                    // if prev substring is palindrome && prev numMinCuts + 1 < cur numMinCuts
                    // set curMinCuts = prev numMinCuts + 1
                    if (palindromes[prevIdx][curIdx] && numMinCuts[prevIdx-1] + 1 < numMinCuts[curIdx]) {
                        numMinCuts[curIdx] = numMinCuts[prevIdx - 1] + 1;
                    }
                }
            }
        }
        return numMinCuts[str.length() - 1];
    }

    private boolean isPalindrome(String str) {
        int leftIdx = 0;
        int rightIdx = str.length() - 1;
        while (leftIdx < rightIdx) {
            if (str.charAt(leftIdx) != str.charAt(rightIdx)) {
                return false;
            }
            leftIdx++;
            rightIdx--;
        }
        return true;
    }

    //O(n^2) time | O(n^2) space because we got rid of isPalindrome method
    public int palindromePartioningMinCutsNoIsPalindrome(String str) {
        boolean[][] palindromes = new boolean[str.length()][str.length()];
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < str.length(); j++) {
                if (i == j) {
                    palindromes[i][j] = true; // diagonals are true
                                              // (substring with len of 1 is palindrome)
                } else {
                    palindromes[i][j] = false;
                }
            }
        }

        // for substring length 2 or more
        for (int length = 2; length < str.length() + 1; length++) {
            for (int startIdx = 0; startIdx < str.length() - length + 1; startIdx++) {
                int endIdx = startIdx + length - 1;
                if (length == 2) {
                    palindromes[startIdx][endIdx] =
                            (str.charAt(startIdx) == str.charAt(endIdx));
                } else {
                    //length longer than 2: check cur char is same && the chars in middle between
                    // startIdx and endIdx are palindrome
                    palindromes[startIdx][endIdx] = (str.charAt(startIdx) == str.charAt(endIdx) &&
                            palindromes[startIdx + 1][endIdx - 1]);
                }
            }
        }

        // populate the cuts array just like the first method
        int[] cuts = new int[str.length()];
        Arrays.fill(cuts, Integer.MAX_VALUE);
        for (int i = 0; i < str.length(); i++) {
            if (palindromes[0][i]) {
                cuts[i] = 0;
            } else {
                cuts[i] = cuts[i - 1] + 1;
                for (int j = 1; j < i; j++) {
                    if (palindromes[j][i] && cuts[j - 1] + 1 < cuts[i]) {
                        cuts[i] = cuts[j - 1] + 1;
                    }
                }
            }
        }
        return cuts[str.length() - 1];
    }
}
