package DP;

import java.util.Arrays;

/**
 * Created by leiwang on 3/25/18.
 */
public class EditDistance {
    public static void main(String args[]) {
//        String str1 = "CAR";
//        String str2 = "CAN";
        String str1 = "KART";
        String str2 = "KARMA";
        System.out.println("edit Distance = " + editDistance(str1, str2, str1.length(), str2.length())); // returns 3
        System.out.println("edit Distance = " + editDistanceRecurse(str1, str2, 0, 0));  // returns 2
        System.out.println("edit Distance = " + editDistanceRecurse2(str1, str2, str1.length(), str2.length())); // returns 3
        System.out.println("edit Distance = " + editDistance2(str1, str2));
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
        System.out.println("d = " + d + " u = " + u + " i = " + i);
        return Math.min(d,Math.min(u,i)) + 1;
    }

    private static int editDistanceRecurse2(String str1, String str2, int str1Length, int str2Length) {
        if (str1Length == 0)
            return str2Length;
        if (str2Length == 0)
            return str1Length;

        // if last chars of two strings are the same, nothing
        // much to do. Ignore last chars and get count for
        // remaining strings
        if (str1.charAt(str1Length-1) == str2.charAt(str2Length-1))
            return editDistanceRecurse2(str1, str2, str1Length-1, str2Length-1);

        // if last chars are not same, consider all three
        // ops on last char of first string, recursively
        // compute min cost for all three ops and take min of three
        return 1 + Math.min(editDistanceRecurse2(str1, str2, str1Length, str2Length-1),                     //insert
                            Math.min(editDistanceRecurse2(str1, str2, str1Length-1, str2Length),            //remove
                                     editDistanceRecurse2(str1, str2, str1Length-1, str2Length-1)));//replace
    }

    private static int editDistance(String str1, String str2, int str1Length, int str2Length) {
        int dp[][] = new int[str1Length+1][str2Length+1];
        for (int row = 0; row <= str1Length; row ++) // top row
            dp[row][0] = row;
        for (int col = 0; col <= str2Length; col ++) // left col
            dp[0][col] = col;

        for (int row = 1; row <= str1Length; row ++) {
            for (int col = 1; col <= str2Length; col ++) {
                if (str1.charAt(row-1) == str2.charAt(col-1))
                    dp[row][col] = dp[row-1][col-1];
                else
                    // else if not equal set to min of three cells + 1
                    dp[row][col] = Math.min(dp[row][col-1],
                                            Math.min(dp[row-1][col],dp[row-1][col-1])) + 1;
            }
        }
        System.out.println("dp table");
        for (int[] num : dp)
            System.out.println(java.util.Arrays.toString(num));

        return dp[str1Length][str2Length];
    }

    private static int editDistance2(String a, String b) {
        int lengthA = a.length();
        int lengthB = b.length();

        int[][] memo = new int[lengthA+1][lengthB+1];
        //pre-fill first row and column ex, 0,1,2,3,4,5...
        for (int row = 1; row <= lengthA; row++) {
            memo[row][0] = row;
        }
        for (int col = 1; col <= lengthB; col++) {
            memo[0][col] = col;
        }

        //traverse and fill cells
        for (int row = 1; row <= lengthA; row++) {
            char chA = a.charAt(row-1);
            for (int col = 1; col <= lengthB; col++) {
                char chB = b.charAt(col-1);
                if (chA == chB) {
                    memo[row][col] = memo[row-1][col-1];
                } else {
                    // cell left corner
                    int replaceDist = 1 + memo[row-1][col-1];
                    // cell left of the current cell
                    int insertDist = 1 + memo[row][col-1];
                    // cell above the current cell
                    int deleteDist = 1 + memo[row-1][col];
                    // find min of three cells
                    int minDist = Math.min(replaceDist,Math.min(insertDist,deleteDist));
                    memo[row][col] = minDist;
                }
            }
        }

        for (int[] row : memo) {
            System.out.println(Arrays.toString(row));
        }
        return memo[lengthA][lengthB];
    }
}
