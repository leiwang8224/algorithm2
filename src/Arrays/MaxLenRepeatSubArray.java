package Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//LC-718
public class MaxLenRepeatSubArray {
    public static void main(String[] args) {
        int[] A = new int[] {1,2,3,2,1};
        int[] B = new int[] {3,2,1,4,7};
        System.out.println(findLengthBruteForce(A, B));
        System.out.println(findLengthDP(A, B));
        System.out.println(findLengthDP2(A, B));
    }

    // time O(M* N * min(M,N)) where M, N are lengths of A, B
    // space O(N)
    private static int findLengthBruteForce(int[] A, int[] B) {
        int ans = 0;
        // map <element in index, list of indices>
        Map<Integer, ArrayList<Integer>> Bstarts = new HashMap<>();

        // iterate through B to form map
        for (int bIndex = 0; bIndex < B.length; bIndex++) {
//     * if (map.get(key) == null) {
//     *     V newValue = mappingFunction.apply(key);
//     *     if (newValue != null)
//     *         map.put(key, newValue);
            Bstarts.computeIfAbsent(B[bIndex], x -> new ArrayList<>()).add(bIndex);
        }
        System.out.println(Bstarts);

        for (int aIndex = 0; aIndex < A.length; aIndex++) {
            if (Bstarts.containsKey(A[aIndex])) {
                for (int listItem : Bstarts.get(A[aIndex])) {
                    int subIndex = 0;
                    while (aIndex + subIndex < A.length &&
                            listItem + subIndex < B.length &&
                            A[aIndex + subIndex] == B[listItem + subIndex]) {
                        subIndex++;
                    }
                    ans = Math.max(ans, subIndex);
                }
            }
        }
        return ans;
    }

    //time O(M * N)
    //space O(M*N)
    private static int findLengthDP(int[] A, int[] B) {
        int ans = 0;
        int[][] memo = new int[A.length+1][B.length+1];
        // for each element in A compare with elements in B
        // iterate from end of array to front
        for (int row = A.length -1; row >=0; --row) {
            for (int col = B.length-1; col >=0; --col) {
                if (A[row] == B[col]) {
                    memo[row][col] = memo[row+1][col+1]+1;
                    if (ans < memo[row][col]) ans = memo[row][col];
                }
            }
        }
        return ans;
    }
    //time O(M * N)
    //space O(M*N)
    // 2 dim dp to track the locations where the chars are the same
    // 2 dim is needed since there are 2 arrays to keep track of
    private static int findLengthDP2(int[] A, int[] B) {
        if(A == null||B == null) return 0;
        int max = 0;
        //dp[row][col] is the length of longest
        //common subarray ending with A[row] and B[col]
        int[][] dp = new int[A.length + 1][B.length + 1];
        //NOTE that the loop goes beyond the last index of original array
        // since dp[][] dp = new int[A.length+1][B.length+1]
        for(int row = 0;row <= A.length;row++){
            for(int col = 0;col <= B.length;col++){
                // first row and first col is 0
                if(row == 0 || col == 0){
                    dp[row][col] = 0;
                } else {
                    // if A and B element are the same, increment count and take max
                    // use row-1 and col-1 because 0th row and 0th col are all 0's
                    if(A[row - 1] == B[col - 1]){
                        dp[row][col] = 1 + dp[row - 1][col - 1];
                        max = Math.max(max,dp[row][col]);
                    }
                }
            }
        }
        return max;
    }
}
