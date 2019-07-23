package DP;

import java.util.Stack;

/**
 * Created by leiwang on 3/25/18.
 */
public class MaxSubArraySum {
    public static void main(String args[]) {
        int[] array = new int[] {-2,3,5,3,2,4,45,2,3,45,6,64,2,4};
        int[][] array2D = new int[][] {{1,2,3}, {4,5,6}, {7,8,9}, {10,11,12}};

        System.out.println("brute force method = " + maxSubArraySumForce(array, array.length));
        System.out.println("kadane's method = " + maxSubArraySumKadaneAlgorithm(array,array.length));
        System.out.println("Returning max with indices = " + java.util.Arrays.toString(maxContinguousSeq(array)));

        System.out.println("2D array max sum = " + matrixMaxSumDp2D(array2D));
        System.out.println("2D array max sum = " + matrixMaxSumDfsIterative(array2D));
        System.out.println("2D array max sum = " + matrixMaxSumDfsRecursive(array2D));


    }

//    Given an array of integers consisting of both positive and negative
//    numbers, find the contiguous subsequence that has the maximum sum among
//    all subsequences in the array (click the red text to learn more about
//    subsequences). Write a method that takes in an array of integers arr
//    and returns an array res containing 3 integers in the following format:
//    res[0] = max sum
//    res[1] = starting index of the subsequence
//    res[2] = ending index of the subsequence
    private static int[] maxContinguousSeq(int[] arr) {
        // keep track stats on current trend
        int currStartIndex = 0;
        int curEndIndex = 0;
        int curSum = 0;

        // keep track stats on global trend
        int maxStartIndex = 0;
        int maxEndIndex = -1;
        int maxSum = 0;

        if (arr.length > 0) {
            curSum = arr[0];
            maxSum = arr[0];
            maxEndIndex = 0;
        }

        for (int index = 1; index < arr.length; index++) {
            int sum = arr[index] + curSum;

            // if the max sum plus the current item is less than the
            // item, then we should set max sum to be the current item
            // also set start and end index to the index of the number
            if (arr[index] > sum) {
                currStartIndex = index;
                curEndIndex = index;
                // the element is greater than sum so set sum to be element
                curSum = arr[index];
            } else {
                // otherwise include the current item into our subsequence
                // increment end index to include the current item and add to curSum
                curEndIndex ++;
                // add the element
                curSum += arr[index];
            }

            //if the sum of our subsequence is greater than global max so far
            if (curSum > maxSum) {
                maxSum = curSum;    // update the global max subsequence
                maxStartIndex = currStartIndex;
                maxEndIndex = curEndIndex;
            }
        }

        int[] result = {maxSum, maxStartIndex,maxEndIndex};
        return result;
    }

    //Recursive method = M(n) = max(M(n-1) + A[n], A[n])

    private static int maxSubArraySumForce(int[] array, int length) {
        int maxSum = 0;
        int tempSum = 0;
        for (int i = 0; i < length; i ++) {
            tempSum = array[i];
            for (int j = i + 1; j < length; j ++) {
                tempSum += array[j];
                if (tempSum > maxSum) {
                    maxSum = tempSum;
                }
            }
        }
        return maxSum;
    }

    private static int matrixMaxSumDfsRecursive(int[][] grid) {
        return dfs(0,0,grid);
    }

    private static int dfs(int row, int col, int[][] grid) {
        // return last element in the matrix
        if (row == grid.length-1 && col == grid[0].length-1) {
            return grid[row][col];
        }

        // if not at the end of the grid, recurse below and recurse to the right grid
        // take max of two and return
        if (row < grid.length-1 && col < grid[0].length-1) {
            System.out.println("row = " + row + " col = " + col + " still in the middle");
            int r1 = grid[row][col] + dfs(row+1, col, grid);
            int r2 = grid[row][col] + dfs(row, col+1, grid);
            return Math.max(r1,r2);
        }

        // if not at the end of the row but at the end of the column,
        // since we are at the border, only need to recurse to one direction (down)
        // add current element and recurse on next row
        if (row < grid.length-1) {
            System.out.println("row = " + row + " col = " + col + " not at end of row");
            return grid[row][col] + dfs(row+1, col, grid);
        }

        // if not at the end of the column but at the end of the row,
        // since we are at the border, only need to recurse to one direction (right)
        // add current element and recurse on next col
        if (col < grid[0].length-1) {
            System.out.println("row = " + row + " col = " + col + " not at end of col");
            return grid[row][col] + dfs(row, col+1, grid);
        }

        return 0;
    }

    /**
     * This method is a duplicate, shown in another class.
     * This is just for reference for comparison with the DP method
     * @param grid
     * @return
     */
    private static int matrixMaxSumDfsIterative(int[][] grid) {
        class TravelNode {
            int row;
            int col;
            int nodeSum;

            TravelNode(int r, int c, int sum, int[][] grid) {
                row = r;
                col = c;
                //note that the sum up to the current grid is stored here
                sum += grid[r][c];
                nodeSum = sum;
            }
        }

        int maxSum = Integer.MIN_VALUE;
        int rows = grid.length;
        int cols = grid[0].length;

        // base case just to return the first element
        if (rows < 2 && cols < 2) return grid[0][0];
        else {
            Stack<TravelNode> stack = new Stack<>();

            stack.push(new TravelNode(0,0,0,grid));
            while (!stack.isEmpty()) {
                TravelNode t = stack.pop();
                System.out.println("row = " + t.row + " col = " + t.col + " sum value = " + t.nodeSum);
                //update maxSum if the last node is reached
                if (t.row == rows-1 && t.col == cols - 1) {
                    if (t.nodeSum > maxSum)
                        maxSum = t.nodeSum;
                } else { // go right then go down and add info to stack
                    // go right
                    if (t.col < cols-1) {
                        stack.push(new TravelNode(t.row, t.col+1, t.nodeSum, grid));
                    }
                    // go down
                    if (t.row < rows-1) {
                        stack.push(new TravelNode(t.row + 1, t.col, t.nodeSum, grid));
                    }
                }
            }
        }
        return maxSum;
    }

    /**
     * Since you can only go down and right, leverage this information to
     * populate the first column and first row (accumulate):
     * ex: cell(0,2) = cell(0,1) + cell(0,0)
      * @param grid
     * @return
     */
    private static int matrixMaxSumDp2D(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int numberRows = grid.length, numberCols = grid[0].length;

        // memo array size is the same as the original array
        int[][] memo = new int[numberRows][numberCols];

        // set first element the same as original array
        memo[0][0] = grid[0][0];

        // pre fill first column
        for (int row = 1; row < numberRows; row++) {
            // previous memo plus current grid element (row above)
            memo[row][0] = memo[row-1][0] + grid[row][0];
        }

        // pre fill first row
        for (int col = 1; col < numberCols; col++) {
            // previous memo plus current grid element (column to left)
            memo[0][col] = memo[0][col-1] + grid[0][col];
        }

        // fill remaining cells using grid and memo
        for (int row = 1; row < numberRows; row++) {
            for (int col = 1; col < numberCols; col++) {
                // take max of the left and upper element and add to original array
                // store to current grid
                memo[row][col] = grid[row][col] + Math.max(memo[row-1][col], memo[row][col-1]);
            }
        }

        return memo[numberRows-1][numberCols-1];
    }

    /**
     * using Kadane's algorithm
     * @param array
     * @param length
     * @return
     */
    private static int maxSubArraySumKadaneAlgorithm(int[] array, int length) {
        int maxSumSoFar = 0;
        int maxSumEndingHere = 0;
        for (int i = 0; i < length; i++) {
            maxSumEndingHere = maxSumEndingHere + array[i];
            if (maxSumEndingHere < 0)
                maxSumEndingHere = 0;
            if (maxSumSoFar < maxSumEndingHere)
                maxSumSoFar = maxSumEndingHere;
        }
        return maxSumSoFar;
    }
}
