package DFS;

import java.util.Deque;
import java.util.LinkedList;

//Given an m x n matrix filled with non-negative integers, use depth first search to find the
// maximum sum along a path from the top-left of the grid to the bottom-right. Return this
// maximum sum. The direction of movement is limited to right and down.
public class MatrixMaxSumDfs {
    public static void main(String[] args) {
        int[][] grid = new int[][] {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        int[][] grid2 = new int[][] {{1,2,3},{4,5,6},{7,8,9}};
        System.out.println(matrixMaxSumDfs(grid));
        System.out.println(matrixMaxSumDfs(grid2));

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
}
