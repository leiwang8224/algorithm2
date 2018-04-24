package DP;

/**
 * Created by leiwang on 4/8/18.
 */
public class IsInterleaved {
    static int charIndex = 0;

    public static void main(String args[]) {
        String str1 = "abc";
        String str2 = "df";
        String strCombo = "adbfc";

        System.out.println(isInterleavedDP(str1, str2, strCombo));
    }

    //TODO not working
    private static boolean isInterleaved(String str1, String str2, String strCombo) {
        if (str1.isEmpty() && str2.isEmpty() && strCombo.isEmpty())
            return true;

        if (strCombo.isEmpty()) // strCombo is empty
            return false;     // str1 or str2 or both is not empty

        if (str1.isEmpty() && str2.isEmpty())
            return false;

        boolean first = false;
        boolean second = false;

        if (str1.charAt(0) == (strCombo.charAt(0)))
            first = isInterleaved(str1.substring(charIndex++),str2,strCombo.substring(charIndex++));
        if (str2.charAt(0) == (strCombo.charAt(0)))
            second = isInterleaved(str1,str2.substring(charIndex++),strCombo.substring(charIndex++));

        return first || second;
    }

    private static boolean isInterleavedDP(String str1, String str2, String strCombo) {
        int str1Length = str1.length();
        int str2Length = str2.length();

        if (strCombo.length() != str1Length + str2Length)
            return false;

        boolean dp[][] = new boolean[str1Length+1][str2Length+1];

        dp[0][0] = true;

        // first column
        for (int row = 1; row <= str1Length; row ++) {
            if (str1.charAt(row-1) != strCombo.charAt(row-1))
                dp[row][0] = false;
            else
                dp[row][0] = dp[row-1][0];
        }

        // first row
        for (int col = 1; col <= str2Length; col ++) {
            if (str2.charAt(col-1) != strCombo.charAt(col-1))
                dp[0][col] = false;
            else
                dp[0][col] = dp[0][col-1];
        }

        for (int row = 1; row <= str1Length; row ++) {
            for (int col = 1; col <= str2Length; col ++) {
                // char of combo same as A but not B
                if (str1.charAt(row-1) == strCombo.charAt(row+col-1) && str2.charAt(col-1) != strCombo.charAt(row + col - 1)) {
                    dp[row][col] = dp[row-1][col];
                }
                // char of combo same as B but not A
                else if (str1.charAt(row-1) != strCombo.charAt(row+col-1) && str2.charAt(col-1) == strCombo.charAt(row+col-1)) {
                    dp[row][col] = dp[row][col-1];
                }
                else if (str1.charAt(row-1) == strCombo.charAt(row + col -1) && str2.charAt(col-1) == strCombo.charAt(row + col -1)) {
                    dp[row][col] = dp[row-1][col] || dp[row][col-1];
                }
                else {
                    dp[row][col] = false;
                }
            }
        }
        return dp[str1Length][str2Length];
    }
}
