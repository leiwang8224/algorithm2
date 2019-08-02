package DFS;

import java.util.LinkedList;
import java.util.Queue;

//You have a map that marks the location of a treasure island.
// Some of the map area has jagged rocks and dangerous reefs.
// Other areas are safe to sail in. There are other explorers
// trying to find the treasure. So you must figure out a shortest
// route to the treasure island.
//
//        Assume the map area is a two dimensional grid,
// represented by a matrix of characters. You must start from
// the top-left corner of the map and can move one block up,
// down, left or right at a time. The treasure island is marked
// as X in a block of the matrix. X will not be at the top-left
// corner. Any block with dangerous rocks or reefs will be marked
// as D. You must not enter dangerous blocks. You cannot leave
// the map area. Other areas O are safe to sail in. The top-left
// corner is always safe. Output the minimum number of steps to
// get to the treasure.
//
//        Example:
//
//        Input:
//        [['O', 'O', 'O', 'O'],
//        ['D', 'O', 'D', 'O'],
//        ['O', 'O', 'O', 'O'],
//        ['X', 'D', 'D', 'O']]
//
//        Output: 5
//        Explanation: Route is (0, 0), (0, 1), (1, 1), (2, 1), (2, 0), (3, 0) The minimum route takes 5 steps.
public class BFSTreasureIslandTraversal {
    private static final int[][] DIRS = {{1,0}, {0,1}, {-1,0}, {0,-1}};
    public static void main(String[] args) {
        char[][] grid = {{'O', 'O', 'O', 'O'},
                {'D', 'O', 'D', 'O'},
                {'O', 'O', 'O', 'O'},
                {'X', 'D', 'D', 'O'}};
        System.out.println(minSteps(grid));
    }

    private static int minSteps(char[][] grid) {
        Queue<BFSShortestDistBetweenXAndY.Point> q = new LinkedList<>();
        q.offer(new BFSShortestDistBetweenXAndY.Point(0,0));
        grid[0][0] = 'D'; //mark as visited
        
        for (int steps = 1; !q.isEmpty(); steps++) {
            for (int size = q.size(); size > 0; size--) {
                BFSShortestDistBetweenXAndY.Point p = q.poll();
                
                for (int[] dir : DIRS) {
                    int row = p.r + dir[0];
                    int col = p.c + dir[1];
                    
                    if (isSafe(grid, row, col)) {
                        if (grid[row][col] == 'X') return steps;
                        grid[row][col] = 'D';
                        q.offer(new BFSShortestDistBetweenXAndY.Point(row, col));
                    }
                }
            }
        }
        return -1;
    }

    private static boolean isSafe(char[][] grid, int row, int col) {
        return row >= 0 &&
                row < grid.length &&
                col >= 0 &&
                col < grid[0].length &&
                grid[row][col] != 'D';
    }


}
