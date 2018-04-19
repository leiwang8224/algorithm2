package Arrays;

/**
 * Created by leiwang on 3/26/18.
 */

/**
 * You are given a map in form of a two-dimensional integer grid where
 * 1 represents land and 0 represents water. Grid cells are connected
 * horizontally/vertically (not diagonally). The grid is completely
 * surrounded by water, and there is exactly one island
 * (i.e., one or more connected land cells). The island doesn't have "lakes"
 * (water inside that isn't connected to the water around the island).
 * One cell is a square with side length 1. The grid is rectangular,
 * width and height don't exceed 100. Determine the perimeter of the island.

 Example:

 [[0,1,0,0],
 [1,1,1,0],
 [0,1,0,0],
 [1,1,0,0]]

 Answer: 16
 */
public class FindIslandPerimeter {
    public static void main(String args[]) {
        int[][] array = new int[][] {{0,1,0,0},
                                    {1,1,1,0},
                                    {0,1,0,0},
                                    {1,1,0,0}};
       System.out.println("perimeter of island is " + findArea(array));
    }

    /**
     * Whenever encounter a 1, count as island then check:
     * - right side for neighbor
     * - below for neighbor
     * Final calculation based on Area = #Islands * 4 - #Neighbors * 2
     * @param array
     * @return
     */
    private static int findArea(int[][] array) {
        int islands = 0, neighbors = 0;
        for (int row = 0; row < array.length; row ++) {
            for (int col = 0; col < array[0].length; col ++) {
                if (array[row][col] == 1) {
                    islands ++;
                    if (row < array.length - 1 && array[row+1][col] == 1) {
                        // below has neighbor
                        neighbors++;
                    }
                    if (col < array[0].length - 1 && array[row][col+1] == 1) {
                        // right side has neighbor
                        neighbors++;
                    }
                }
            }
        }
        return islands * 4 - neighbors * 2;
    }
}
