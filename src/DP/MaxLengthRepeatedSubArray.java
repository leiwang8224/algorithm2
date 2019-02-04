package DP;

import java.util.Arrays;

//Given two integer arrays A and B, return the maximum length of an subarray that appears in both arrays.
//
//        Example 1:
//
//        Input:
//        A: [1,2,3,2,1]
//        B: [3,2,1,4,7]
//        Output: 3
public class MaxLengthRepeatedSubArray {
    public static void main(String[] args) {
        int[] firstArray = new int[]{1,2,3,2,1};
        int[] secondArray = new int[]{3,2,1,4,7};

        System.out.println(findLength(firstArray,secondArray));
        System.out.println(findLengthMemo(firstArray,secondArray));

    }

    private static int findLength(int A[], int B[]) {
        if (A == null || B == null) return 0;
        int m = A.length;
        int n = B.length;
        int max = Integer.MIN_VALUE;

        int[][] dp = new int[m+1][n+1];

        // iterate through each element and add one to count
        // if the element is the same
        for (int i = 1; i < m+1; i++) {
            for (int j = 1; j < n+1; j++) {
                if (A[i-1] == B[j-1]) {
                    dp[i][j] = dp[i-1][j-1]+1;
                }
                max = Math.max(dp[i][j], max);
            }
        }
        //[0, 0, 0, 0, 0, 0]
        //[0, 0, 0, 1, 0, 0]
        //[0, 0, 1, 0, 0, 0]
        //[0, 1, 0, 0, 0, 0]
        //[0, 0, 2, 0, 0, 0]
        //[0, 0, 0, 3, 0, 0]

        for (int[] each : dp) {
            System.out.println(Arrays.toString(each));
        }
        return max;
    }

    private static int findLengthMemo(int[] A, int[] B) {
        int[] max = new int[1];
        memo(A.length-1,B.length-1,A,B, new int[A.length][B.length], max);
        return max[0];

    }

    private static int memo(int i, int j, int[] A, int[] B, int[][] dp, int[] max) {
        if (i < 0 || j < 0) return 0;

        if(A[i] ==B[j]) {
            dp[i][j] = memo(i - 1, j - 1, A, B, dp, max) + 1;
        }
        System.out.println("**********");
        for (int[] ele : dp) {
            System.out.println(Arrays.toString(ele));
        }
        System.out.println("**********");

        // if (A[i] != B[j]) then compare (i-1,j) and (i,j-1)
        memo(i - 1, j, A, B, dp, max);
        memo(i, j - 1, A, B, dp, max);
        max[0] = Math.max(max[0],dp[i][j]);
        return dp[i][j];
    }

}
