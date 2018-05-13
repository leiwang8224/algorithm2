package DP;

/**
 * Created by leiwang on 4/8/18.
 */
public class LongestCommonSeq {
    static int table[][];
    public static void main(String args[]) {
        String str1 = "ABCDB";
        String str2 = "AEBDB";
        table = new int[str1.length()+1][str2.length()+1];
        for (int i = 0; i < table.length; i ++) {
            for (int j = 0; j < table[0].length; j ++) {
                table[i][j] = -1;
            }
        }
        System.out.println(getLCS(str1, str2, str1.length(), str2.length()));
        System.out.println(getLCSMemo(str1, str2, str1.length(), str2.length()));
        printLCS(str1,str2,str1.length(),str2.length());
        System.out.println(lcsRecurse(str1,str2));
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

    private static int getLCSMemo(String str1, String str2, int str1Index, int str2Index) {
        if (str1Index == 0 || str2Index == 0)
            return 0;

        if (table[str1Index][str2Index] != -1)
            return table[str1Index][str2Index];

        if (str1.charAt(str1Index-1) == str2.charAt(str2Index - 1))
            table[str1Index][str2Index] = 1 + getLCSMemo(str1, str2, str1Index-1, str2Index - 1);
        else
            table[str1Index][str2Index] = getMax(getLCSMemo(str1, str2, str1Index, str2Index-1),
                                                getLCSMemo(str1, str2, str1Index-1, str2Index));

//        for (int i = 0; i < table.length; i++) {
//            for (int j = 0; j < table[0].length; j++) {
//                System.out.print(table[i][j]);
//            }
//            System.out.println();
//        }
        return table[str1Index][str2Index];
    }

    private static void printLCS(String str1, String str2, int str1Length, int str2Length) {
        // populate the LCSCount array
        int len = getLCSMemo(str1,str2,str1.length(),str2.length());

        // array to store the char in LCS
        char[] lcs = new char[len+1];
        // start from bottom right corner
        int i = str1Length-1, j = str2Length-1;

        // continue until we hit the top or left wall
        while (i > 0 && j > 0) {
            // if current char in str1 and str2 are equal
            // then it is part of LCS
            if (str1.charAt(i) == str2.charAt(j)) {
                lcs[len] = str1.charAt(i);
                i--;
                j--;
                len--;
            }
            // if not equal, find larger of the two
            // and go in direction of larger value
            else if (table[i-1][j] > table[i][j-1]) {
                i--;
            } else {
                j--;
            }
        }
        System.out.println("LCS is " + java.util.Arrays.toString(lcs));
    }

    /**
     * Brute force method with recursion
     * @param str1
     * @param str2
     * @return
     */
    private static int lcsRecurse(String str1, String str2) {
        // base case empty string
        if (str1.length() == 0 || str2.length() == 0) return 0;
        int len1 = str1.length();
        int len2 = str2.length();

        // solve recursively

        // corresponding last chars match
        if (str1.charAt(len1 - 1) == str2.charAt(len2 - 1))
            // recurse from end to the beginning, note the -1 in length
            return 1 + lcsRecurse(str1.substring(0,str1.length()-1),
                                 str2.substring(0,str2.length()-1));

        // corresponding chars do not match
        else
            //take the max of the recursion results
            return Math.max(lcsRecurse(str1, str2.substring(0, str2.length()-1)),
                    lcsRecurse(str1.substring(0,str1.length()-1),str2));
    }
}
