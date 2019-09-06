package CTCI.RecursionAndDP;

import java.util.ArrayList;

/**
 * Robot starting at the upper left corner of grid with r rows and c columns.
 * The robot can only move in two directions, right and down, but certain cells are
 * off limits such that the robot cannot step on them. Design algorithm to find a
 * path for the robot from the top left to bottom right.
 */
public class RobotInAGrid {
    public static void main(String[] args) {

    }

    static class Point {
        int row, col;
        Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }

        @Override
        public boolean equals(Object o) {
            if ((o instanceof Point) && (((Point)o).row == this.row ) &&
                (((Point) o).col) == this.col) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * To find a path from the origin, we just work backwards. Starting from the last cell,
     * we try to find a path to each of its adjacent cells. 
     * @param maze
     * @return
     */
    static ArrayList<Point> getPath(boolean[][] maze) {
        if (maze == null || maze.length == 0) return null;
        ArrayList<Point> path = new ArrayList<>();
        if (getPath(maze, maze.length - 1, maze[0].length - 1, path)) {
            return path;
        }
        return null;
    }

    private static boolean getPath(boolean[][] maze, int row, int col, ArrayList<Point> path) {
        // if out of bounds or not available return
        if (col < 0 || row < 0 || !maze[row][col]) return false;

        boolean isAtOrigin = (row == 0) && (col == 0);

        // if there is a path from the start to my current location, add my location
        if (isAtOrigin ||
            getPath(maze, row, col - 1, path) ||
            getPath(maze, row - 1, col, path)) {
            Point p = new Point(row, col);
            path.add(p);
            return true;
        }
        return false;
    }


}
