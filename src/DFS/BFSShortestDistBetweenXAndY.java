package DFS;

//Give a grid, and there are X and Y in this grid. find the shortest distance between X and Y.
//
//        Example 1:
//
//        Input:
//        [[X,0,0],
//        [0,Y,0],
//        [X,Y,0]]
//        Output: 1
//
//        Example 2:
//
//        Input:
//        [[X,X,0],
//        [0,0,Y],
//        [Y,0,0]]
//        Output: 2

import java.util.LinkedList;
import java.util.Queue;

public class BFSShortestDistBetweenXAndY {
    private static final int[][] DIRS = {{0,1}, {1,0}, {-1,0}, {0,-1}};  // up, down, left, right
    public static void main(String[] args) {
        char[][] grid = {
                {'X', '0', '0'},
                {'0', 'Y', '0'},
                {'X', 'Y', '0'}
        };

        System.out.println(minDist(grid));
        System.out.println(bfsFindShortest(grid));

        char[][] grid2 = {
                {'X', 'X', '0'},
                {'0', '0', 'Y'},
                {'Y', '0', '0'}
        };

        System.out.println(minDist(grid2));
        System.out.println(bfsFindShortest(grid2));
    }

    private static int minDist(char[][] grid) {
        Queue<Point> queueOfXCoords = getCoordOfXFromGrid(grid);
        for (int dist = 1; !queueOfXCoords.isEmpty(); dist++) {
            // iterate through the queue
            for (int size = queueOfXCoords.size(); size > 0; size--) {
                Point p = queueOfXCoords.poll();

                // apply DIRS to the directions
                for (int[] direction : DIRS) {
                    // x (col)
                    int r = p.r + direction[0];
                    // y (row)
                    int c = p.c + direction[1];

                    // check if grid[r][c] is valid
                    if (isSafe(grid, r, c)) {
                        // return dist if we find 'Y'
                        if (grid[r][c] == 'Y') return dist;
                        // else set cell to 'X'
                        grid[r][c] = 'X';
                        // put coordinate of X in queue
                        queueOfXCoords.offer(new Point(r,c));
                    }
                }
            }
        }
        return -1;
    }

    private static boolean isSafe(char[][] grid, int r, int c) {
        return r >= 0 &&
               r < grid.length &&
               c >= 0 &&
               c < grid[0].length &&
               grid[r][c] != 'X';
    }

    /**
     * Get the coordinates of the 'X' in the grid
     * @param grid
     * @return
     */
    private static Queue<Point> getCoordOfXFromGrid(char[][] grid) {
        Queue<Point> xs = new LinkedList<>();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 'X') {
                    xs.add(new Point(r,c));
                }
            }
        }
        return xs;
    }

    private static class Point {
        int r, c;
        Point (int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    private static int bfsFindShortest(char[][] grid) {
        int shortest = Integer.MAX_VALUE;
        boolean[][] visited;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'X') {
                    visited = new boolean[grid.length][grid[i].length];
                    int distance = bfs(grid, i, j, 0, visited);
                    shortest = Math.min(shortest,distance);
                }
            }
        }
        return shortest;
    }

    private static int bfs(char[][] c, int i, int j, int length, boolean[][] visited) {
        LinkedList<Point> q = new LinkedList<>();
        q.add(new Point(i,j));
        int distance = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int k = 0; k < size; k++) {
                Point current = q.poll();
                visited[current.r][current.c] = true;
                int up = current.r - 1;

                if (up >= 0) {
                    if (!visited[up][current.c]) {
                        if (c[up][current.c] == 'Y') return distance + 1;
                        else q.add(new Point(up, current.c));
                    }
                }

                int down = current.r + 1;
                if (down < c.length) {
                    if (!visited[down][current.c]) {
                        if (c[down][current.c] == 'Y') return distance + 1;
                        else q.add(new Point(down, current.c));
                    }
                }

                int left = current.c - 1;
                if (left >= 0) {
                    if (!visited[current.r][left]) {
                        if (c[current.r][left] == 'Y') return distance + 1;
                        else q.add(new Point(current.r, left));
                    }
                }

                int right = current.c + 1;
                if (right < c[i].length) {
                    if (!visited[current.r][right]) {
                        if (c[current.r][right] == 'Y') return distance + 1;
                        else q.add(new Point(current.r, right));
                    }
                }
            }
            distance++;
        }
        return Integer.MAX_VALUE;
    }

    
}
