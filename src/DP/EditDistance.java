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
        System.out.println("edit Distance = " + editDistanceRecurse(str1, str2, 0, 0));
    }

    //TODO this one has problem
    private static int editDistanceRecurse(String str1, String str2, int str1Index, int str2Index) {
        if (str1 == null || str1.length() == 0)
            return str2.length();

        if (str2 == null || str2.length() == 0)
            return str1.length();

        if (str1.charAt(str1Index) == str2.charAt(str2Index)) {
            return editDistance(str1,str2,str1Index+1,str2Index+1);
        }

        int d, u, i;
        d = editDistance(str1,str2,str1Index+1,str2Index);
        u = editDistance(str1,str2,str1Index+1,str2Index+1);
        i = editDistance(str1,str2,str1Index,str2Index+1);

        return Math.min(d,Math.min(u,i)) + 1;
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
