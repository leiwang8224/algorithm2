package AlgoExpert.Hard;
import java.util.*;
public class LongestCommonSubsequence {
    // O(nm) time | O(nm) space
    // simplest solution
    private static List<Character> LCS4(String str1, String str2) {
        int[][] longestLenArray = new int[str2.length() + 1][str1.length() + 1];
        for (int str2RowIdx = 1; str2RowIdx < str2.length() + 1; str2RowIdx++) {
            for (int str1ColIdx = 1; str1ColIdx < str1.length() + 1; str1ColIdx++) {
                // if string matches at char take upper left corner value + 1
                // (take the max length at previous char for both strings)
                if(str2.charAt(str2RowIdx - 1) == str1.charAt(str1ColIdx - 1)) {
                    longestLenArray[str2RowIdx][str1ColIdx] = longestLenArray[str2RowIdx-1][str1ColIdx-1] + 1;
                } else { // does not match, take max(left, above)
                    // (take max of the max length at char before str1 and char before str2
                    longestLenArray[str2RowIdx][str1ColIdx] =
                            Math.max(longestLenArray[str2RowIdx-1][str1ColIdx],
                                    longestLenArray[str2RowIdx][str1ColIdx-1]);
                }
            }
        }
        return buildSequence2(longestLenArray, str1);
    }

    // extract the chars that were selected from str1
    private static List<Character> buildSequence2(int[][] longestLenArray, String str1) {
        List<Character> sequences = new ArrayList<>();
        int str2RowIdx = longestLenArray.length - 1;
        int str1ColIdx = longestLenArray[0].length - 1;
        while (str2RowIdx != 0 && str1ColIdx != 0) {
            // find the letters by backtracking through the array, remove chars from each string one by one
            // first row then column until there is diff between rows
            // if same value as above, dec row
            if (longestLenArray[str2RowIdx][str1ColIdx] == longestLenArray[str2RowIdx-1][str1ColIdx]) {
                str2RowIdx--; // if same value as prev char in str2
            } else if (longestLenArray[str2RowIdx][str1ColIdx] == longestLenArray[str2RowIdx][str1ColIdx-1]) {
                str1ColIdx--; // if same value as prev char in str1
            } else { // not same as above nor left (match chars)
                // add chars in reverse direction
                sequences.add(0, str1.charAt(str1ColIdx - 1));
                str2RowIdx--;
                str1ColIdx--;
            }
        }
        return sequences;
    }

    // O(nm * min(n, m) time | O(nm * min(n, m)) space
    private static List<Character> LCS(String str1, String str2) {
        // 3dims, row, col, list of characters
        List<List<List<Character>>> lcs = new ArrayList<>();
        // initialize the 3 dim list
        for (int row = 0; row < str2.length() + 1; row++) {
            lcs.add(new ArrayList<List<Character>>());
            for (int col = 0; col < str1.length() + 1; col++) {
                lcs.get(row).add(new ArrayList<>());
            }
        }

        // upper left corner represent the prev char for both strings, if match use this and add the new char
        // take max of upper and left if not match
        for (int row = 1; row < str2.length() + 1; row++) {
            for (int col = 1; col < str1.length() + 1; col++) {
                // check if chars are the same, -1 because of the array length is +1
                if (str2.charAt(row - 1) == str1.charAt(col - 1)) {
                    // concatenate lcs[row-1][col-1] to str2[row-1]
                    // lcs[row][col] = lcs[row -1][col-1] + [str2[row -1]]
                    // get a copy of leftUpperCorner list of chars (LCS)
                    List<Character> upperLeftCornerList = new ArrayList<>(lcs.get(row - 1).get(col - 1));
                    // set current element to be the upper left corner element
                    lcs.get(row).set(col, upperLeftCornerList);
                    // add the new char that was matched between str1 and str2
                    lcs.get(row).get(col).add(str2.charAt(row - 1));
                } else {
                    // lcs[row][col] = max(lcs[row-1][col], lcs[row][col-1], key = len)
                    // if not equal then check the sizes of the left and upper element
                    // and get the element with the larger size list
                    if (lcs.get(row - 1).get(col).size() > lcs.get(row).get(col - 1).size()) {
                        lcs.get(row).set(col, lcs.get(row - 1).get(col));
                    } else {
                        lcs.get(row).set(col, lcs.get(row).get(col - 1));
                    }
                }
            }
        }
        return lcs.get(str2.length()).get(str1.length());
    }

    // O(nm * min(n, m)) time | O((min(n, m))^2) space
    private static List<Character> LCS2(String str1, String str2) {
        String small = str1.length() < str2.length() ? str1 : str2;
        String big = str1.length() >= str2.length() ? str1 : str2;
        List<List<Character>> evenLcs = new ArrayList<>();
        List<List<Character>> oddLcs = new ArrayList<>();

        for (int row = 0; row < small.length() + 1; row ++) {
            evenLcs.add(new ArrayList<>());
        }

        for (int col = 0; col < small.length() + 1; col++) {
            oddLcs.add(new ArrayList<>());
        }

        for (int i = 1; i < big.length() + 1; i++) {
            List<List<Character>> curLcs;
            List<List<Character>> prevLcs;

            if (i % 2 == 1) {
                curLcs = oddLcs;
                prevLcs = evenLcs;
            } else {
                curLcs = evenLcs;
                prevLcs = oddLcs;
            }
            for (int j = 1; j < small.length() + 1; j++) {
                if (big.charAt(i - 1) == small.charAt(j - 1)) {
                    List<Character> copy = new ArrayList<>(prevLcs.get(j - 1));
                    curLcs.set(j, copy);
                    curLcs.get(j).add(big.charAt(i - 1));
                } else {
                    if (prevLcs.get(j).size() > curLcs.get(j - 1).size()) {
                        curLcs.set(j, prevLcs.get(j));
                    } else {
                        curLcs.set(j, curLcs.get(j - 1));
                    }
                }
            }
        }
        return big.length() % 2 == 0 ? evenLcs.get(small.length()) : oddLcs.get(small.length());
    }

    // O(nm) time | O(nm) space
    private static List<Character> LCS3(String str1, String str2) {
        int[][][] lcs = new int[str2.length() + 1][str1.length() + 1][];
        for (int i = 0; i < str2.length() + 1; i++) {
            for (int j = 0; j < str1.length() + 1; j++) {
                lcs[i][j] = new int[] {0,0,0,0};
                // first element: store the character that matches
                // second element: len of LCS
                // third and fourth, last ith and jth index used for backtracking the matrix
            }
        }

        for (int i = 1; i < str2.length() + 1; i++) {
            for (int j = 1; j < str1.length() + 1; j++) {
                if (str2.charAt(i - 1) == str1.charAt(j - 1)) {
                    int[] newEntry = {(int) str2.charAt(i - 1), // the char that matched
                                        lcs[i-1][j-1][1] + 1,   // last lcs value + 1
                                        i - 1,                  // last lcs index i
                                        j - 1};                 // last lcs index j
                    lcs[i][j] = newEntry;
                } else {
                    // if len of lcs is greater from row above, store the info from row above
                    if (lcs[i - 1][j][1] > lcs[i][j-1][1]) {
                        // nothing matched, so use -1 to indicate no match
                        int[] newEntry = {-1, lcs[i-1][j][1], i - 1, j};
                        lcs[i][j] = newEntry;
                    } else { // else store the info from col to the left
                        int[] newEntry = {-1, lcs[i][j-1][1], i, j - 1};
                        lcs[i][j] = newEntry;
                    }
                }
            }
        }
        return buildSequence(lcs);
    }

    private static List<Character> buildSequence(int[][][] lcs) {
        List<Character> sequence = new ArrayList<>();
        // start from the last element of the matrix
        int i = lcs.length - 1;
        int j = lcs[0].length - 1;
        // backtrack
        while (i != 0 && j != 0) {
            int[] curEntry = lcs[i][j];
            if (curEntry[0] != -1) { // character match (there is a char stored)
                sequence.add(0, (char) curEntry[0]); // add the char to sequence
            }
            i = curEntry[2]; // backtrack up the matrix table
            j = curEntry[3];
        }
        return sequence;
    }


}
