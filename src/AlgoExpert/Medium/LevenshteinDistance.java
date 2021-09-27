package AlgoExpert.Medium;

public class LevenshteinDistance {
    public static void main(String[] args) {

    }

    // O(nm) time | O(nm) space
    private static int levenshteinDist(String str1, String str2) {
        int[][] dp = new int[str1.length()+1][str2.length()+1];

        // first row and first col are empty strings, therefore
        // it takes 0 edits for the (0,0) index
        // 1 edit for (0,1) index, 2 edit for (0,2) index, etc.
        for (int row = 0; row < dp.length; row++) {
            dp[row][0] = row;
        }

        // same as row
        for (int col = 0; col < dp[0].length; col++) {
            dp[0][col] = col;
        }

        for (int row = 1; row < dp.length; row++) {
            for (int col = 1; col < dp[0].length; col++) {
                if (str1.charAt(row-1) == str2.charAt(col-1)) {
                    dp[row][col] = dp[row-1][col-1];
                } else {
                    dp[row][col] = Math.min(dp[row-1][col-1], Math.min(dp[row-1][col], dp[row][col-1])) + 1;
                }
            }
        }

        return dp[dp.length-1][dp[0].length-1];
    }

    // O(nm) time | O(min(n, m)) space
    private static int levenshteinDist2(String str1, String str2) {
        String small = str1.length() < str2.length() ? str1 : str2;
        String big = str1.length() >= str2.length() ? str1 : str2;
        int[] evenEdits = new int[small.length() + 1];
        int[] oddEdits = new int[small.length() + 1];

        for (int j = 0; j < small.length() + 1; j++) {
            evenEdits[j] = j;
        }

        int[] curEdits;
        int[] prevEdits;
        for (int i = 1; i < big.length() + 1; i++) {
            if ( i % 2 == 1) {
                curEdits = oddEdits;
                prevEdits = evenEdits;
            } else {
                curEdits = evenEdits;
                prevEdits = oddEdits;
            }
            curEdits[0] = i;
            for (int j = 1; j < small.length() + 1; j++) {
                if (big.charAt(i - 1) == small.charAt(j - 1)) {
                    curEdits[j] = prevEdits[j-1];
                } else {
                    curEdits[j] =
                            1 + Math.min(prevEdits[j-1], Math.min(prevEdits[j], curEdits[j - 1]));
                }
            }
        }
        return big.length() % 2 == 0 ? evenEdits[small.length()] : oddEdits[small.length()];
    }
}
