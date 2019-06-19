package DFS;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

//Given an m x n matrix filled with non-negative integers, use depth first search to find the
// maximum sum along a path from the top-left of the grid to the bottom-right. Return this
// maximum sum. The direction of movement is limited to right and down.
public class MatrixMaxSumDfs {
    public static void main(String[] args) {
        int[][] grid = new int[][] {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        int[][] grid2 = new int[][] {{1,2,3},{4,5,6},{7,8,9}};
        System.out.println(matrixMaxSumDfsStack(grid));
        System.out.println(matrixMaxSumDfsStack(grid2));

        System.out.println(matrixMaxSumDfs(grid));
        System.out.println(matrixMaxSumDfs(grid2));
        System.out.println(matrixMaxSumRecurse(grid,0,0));
        System.out.println(matrixMaxSumRecurse(grid2,0,0));
        System.out.println(matrixMaxSumDp(grid));
        System.out.println(matrixMaxSumDp(grid2));
    }

    private static int matrixMaxSumDfs(int[][] grid) {
        class TravelNode {
            int row;
            int col;
            int nodeSum;

            TravelNode(int r, int c, int sum, int[][] grid) {
                row = r;
                col = c;
                sum += grid[r][c];
                nodeSum = sum;
            }
        }

        int maxSum = Integer.MIN_VALUE;
        int rows = grid.length;
        int cols = grid[0].length;
        if (rows < 2 && cols < 2) return grid[0][0];
        else {
            Deque<TravelNode> stack = new LinkedList<>();
            stack.addFirst(new TravelNode(0,0,0,grid));
            while (!stack.isEmpty()) {
                TravelNode t = stack.removeFirst();
                //update maxSum if the last node is reached
                if (t.row == rows-1 && t.col == cols - 1) {
                    if (t.nodeSum > maxSum)
                        maxSum = t.nodeSum;
                } else {
                    // go right
                    if (t.col < cols-1) {
                        stack.addFirst(new TravelNode(t.row, t.col+1, t.nodeSum, grid));
                    }
                    // go down
                    if (t.row < rows-1) {
                        stack.addFirst(new TravelNode(t.row + 1, t.col, t.nodeSum, grid));
                    }
                }
            }
        }
        return maxSum;
    }

    private static int matrixMaxSumDfsStack(int[][] grid) {
        class TravelNode {
            int row;
            int col;
            int nodeSum;

            TravelNode(int r, int c, int sum, int[][] grid) {
                row = r;
                col = c;
                sum += grid[r][c];
                nodeSum = sum;
            }
        }
        int maxSum = Integer.MIN_VALUE;
        int rows = grid.length;
        int cols = grid[0].length;
        if (rows < 2 && cols < 2) return grid[0][0];
        else {
            Stack<TravelNode> stack = new Stack<>();
            stack.push(new TravelNode(0,0,0,grid));
            while (!stack.isEmpty()) {
                TravelNode t = stack.pop();
                //update maxSum if the last node is reached
                if (t.row == rows-1 && t.col == cols - 1) {
                    if (t.nodeSum > maxSum)
                        maxSum = t.nodeSum;
                } else {
                    // go right if not at last column
                    if (t.col < cols-1) {
                        stack.push(new TravelNode(t.row, t.col+1, t.nodeSum, grid));
                    }
                    // go down if not at last row
                    if (t.row < rows-1) {
                        stack.push(new TravelNode(t.row + 1, t.col, t.nodeSum, grid));
                    }
                }
            }
        }
        return maxSum;
    }

    private static int matrixMaxSumRecurse(int[][] grid, int row, int col) {
        if (row == grid.length-1 && col == grid[0].length-1) {
            return grid[row][col];
        }

        if (row < grid.length-1 && col < grid[0].length-1) {
            int goingDown = grid[row][col] + matrixMaxSumRecurse(grid,row + 1, col);
            int goingRight = grid[row][col] + matrixMaxSumRecurse(grid, row, col + 1);
            return Math.max(goingDown,goingRight);
        }

        // if not at the last row but at last col
        if (row < grid.length-1) {
            return grid[row][col] + matrixMaxSumRecurse(grid,row+1,col);
        }

        // if not at the last col but at last row
        if (col < grid[0].length-1) {
            return grid[row][col] + matrixMaxSumRecurse(grid,row,col+1);
        }

        return 0;
    }

    private static int matrixMaxSumDp(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int rowNum = grid.length;
        int colNum = grid[0].length;

        int[][] dp = new int[rowNum][colNum];
        dp[0][0] = grid[0][0];

        //init top row
        for (int col = 1; col < colNum; col++) {
            dp[0][col] = dp[0][col-1] + grid[0][col];
        }

        //init leftmost col
        for (int row = 1; row < rowNum; row++) {
            dp[row][0] = dp[row-1][0] + grid[row][0];
        }

        // fill up the dp array
        for (int row = 1; row < rowNum; row++) {
            for (int col = 1; col < colNum; col++) {
                if (dp[row-1][col] > dp[row][col-1]) {
                    dp[row][col] = grid[row][col] + dp[row-1][col];
                } else {
                    dp[row][col] = grid[row][col] + dp[row][col-1];
                }
            }
        }

        return dp[rowNum-1][colNum-1];
    }
}
