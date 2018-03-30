package DP;

/**
 * Created by leiwang on 3/25/18.
 */
public class EditDistance {
    public static void main(String args[]) {
//        String str1 = "CAR";
//        String str2 = "CAN";
        String str1 = "MART";
        String str2 = "KARMA";
        System.out.println("edit Distance = " + editDistance(str1, str2, str1.length(), str2.length()));
    }

    private static int editDistance(String str1, String str2, int str1Length, int str2Length) {
        int dp[][] = new int[str1Length+1][str2Length+1];
        for (int j = 0; j <= str1Length; j ++) // top row
            dp[j][0] = j;
        for (int i = 0; i <= str2Length; i ++) // left col
            dp[0][i] = i;

        for (int i = 1; i <= str1Length; i ++) {
            for (int j = 1; j <= str2Length; j ++) {
                if (str1.charAt(i-1) == str2.charAt(j-1))
                    dp[i][j] = dp[i-1][j-1];
                else
                    dp[i][j] = Math.min(dp[i][j-1],Math.min(dp[i-1][j],dp[i-1][j-1])) + 1;
            }
        }

        return dp[str1Length][str2Length];


    }
}
