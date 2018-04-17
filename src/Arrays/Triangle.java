package Arrays;

import java.util.ArrayList;
import java.util.List;

//Given a triangle, find the minimum path sum from top to bottom. Each step you may
//move to adjacent numbers on the row below.
//
// For example, given the following triangle
//
// [
// [2],
// [3,4],
// [6,5,7],
// [4,1,8,3]
// ]
//
//
//
// The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
public class Triangle {
    public static void main(String args[]) {
        List<Integer> firstRow = new ArrayList<>();
        List<Integer> secondRow = new ArrayList<>();
        List<Integer> thirdRow = new ArrayList<>();
        List<Integer> fourthRow = new ArrayList<>();
        firstRow.add(2);
        secondRow.add(3);
        secondRow.add(4);
        thirdRow.add(6);
        thirdRow.add(5);
        thirdRow.add(7);
        fourthRow.add(4);
        fourthRow.add(1);
        fourthRow.add(8);
        fourthRow.add(3);
        List<List<Integer>> triangleInput = new ArrayList<>();
        triangleInput.add(firstRow);
        triangleInput.add(secondRow);
        triangleInput.add(thirdRow);
        triangleInput.add(fourthRow);

        System.out.println(minTotal(triangleInput));
        System.out.println(minTotal2(triangleInput));

    }
//    This problem is quite well-formed in my opinion. The triangle has a tree-like structure,
//    which would lead people to think about traversal algorithms such as DFS. However, if you look closely,
//    you would notice that the adjacent nodes always share a ‘branch’. In other word, there are overlapping
//    subproblems. Also, suppose x and y are ‘children’ of k. Once minimum paths from x and y to the bottom
//    are known, the minimum path starting from k can be decided in O(1), that is optimal substructure.
//    Therefore, dynamic programming would be the best solution to this problem in terms of time complexity.
//
//    What I like about this problem even more is that the difference between ‘top-down’ and ‘bottom-up’ DP
//    can be ‘literally’ pictured in the input triangle. For ‘top-down’ DP, starting from the node on the very
//    top, we recursively find the minimum path sum of each node. When a path sum is calculated, we store it in
//    an array (memoization); the next time we need to calculate the path sum of the same node, just retrieve
//    it from the array. However, you will need a cache that is at least the same size as the input triangle
//    itself to store the pathsum, which takes O(N^2) space. With some clever thinking, it might be possible
//    to release some of the memory that will never be used after a particular point, but the order of the
//    nodes being processed is not straightforwardly seen in a recursive solution, so deciding which part
//    of the cache to discard can be a hard job.
//
//    ‘Bottom-up’ DP, on the other hand, is very straightforward: we start from the nodes on the bottom row;
//    the min pathsums for these nodes are the values of the nodes themselves. From there, the min pathsum at
//    the ith node on the kth row would be the lesser of the pathsums of its two children plus the value of itself, i.e.:
//
//    minpath[k][i] = min( minpath[k+1][i], minpath[k+1][i+1]) + triangle[k][i];
//
//    Or even better, since the row minpath[k+1] would be useless after minpath[k] is computed,
//    we can simply set minpath as a 1D array, and iteratively update itself:
//
//    For the kth level:
//    minpath[i] = min( minpath[i], minpath[i+1]) + triangle[k][i];
//
//    Thus, we have the following solution
    private static int minTotal(List<List<Integer>> triangle) {
        if (triangle.size() == 0)
            return 0;

        for (int i = triangle.size() - 2; i >= 0; i --) {
            for (int j = 0; j <= i; j ++) {
                List<Integer> nextRow = triangle.get(i+1);
                int sum = Math.min(nextRow.get(j), nextRow.get(j+1))+ triangle.get(i).get(j);
                triangle.get(i).set(j,sum);
            }
        }

        return triangle.get(0).get(0);

    }

    private static int minTotal2(List<List<Integer>> triangle) {
        int[] dp = new int[triangle.size()+1];
        for(int i=triangle.size()-1;i>=0;i--){
            for(int j=0;j<triangle.get(i).size();j++){
                dp[j] = Math.min(dp[j],dp[j+1])+triangle.get(i).get(j);
            }
        }
        return dp[0];
    }

    // transform top to bottom to bottom to top
    private int minimumTotal1(List<List<Integer>> triangle) {
        int rowNum = triangle.size();
        int[] dp = new int[rowNum];
        for (int i = 0; i < triangle.get(rowNum - 1).size(); i++) {
            dp[i] = triangle.get(rowNum - 1).get(i);
        }
        for (int row = rowNum - 2; row >= 0; row--) {// for each layer
            for (int col = 0; col <= row; col++) {
                dp[col] = Math.min(dp[col], dp[col + 1])
                          + triangle.get(row).get(col);
            }
        }
        return dp[0];
    }

    private int minimumTotal(List<List<Integer>> triangle) {
        int rowNum = triangle.get(triangle.size() - 1).size();
        int colNum = triangle.size();
        int[][] dp = new int[rowNum][colNum];
        int i = 0;
        for (Integer n : triangle.get(colNum - 1)) {
            dp[rowNum - 1][i++] = n;
        }
        for (int row = rowNum - 2, m = 0; row >= 0; row--, m++) {
            for (int col = 0; col <= colNum - 2 - m; col++) {
                dp[row][col] = Math.min(dp[row + 1][col], dp[row + 1][col + 1])
                               + triangle.get(row).get(col);
            }
        }
        return dp[0][0];
    }
}
