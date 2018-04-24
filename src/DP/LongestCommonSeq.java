package DP;

/**
 * Created by leiwang on 4/8/18.
 */
public class LongestCommonSeq {
    static int table[][];
    public static void main(String args[]) {
        String str1 = "ABCD";
        String str2 = "AEBD";
        table = new int[str1.length()+1][str2.length()+1];
        System.out.println(getLCS(str1, str2, str1.length(), str2.length()));
        System.out.println(getLCSMemo(str1,str2,str1.length(),str2.length()));
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

        return table[str1Index][str2Index];
    }
}
