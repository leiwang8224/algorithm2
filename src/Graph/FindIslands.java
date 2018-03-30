package Graph;

/**
 * Created by leiwang on 3/24/18.
 */
public class FindIslands {
    public static void main(String args[]) {
        int map[][]=  new int[][] {{1, 1, 0, 0, 0},
                {0, 1, 0, 0, 1},
                {1, 0, 0, 1, 1},
                {0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1}
        };

        countIslands(map);

        int total = 0;
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] == 1) {
                    total ++;
                    findIslands(map,row,col);
                }
            }
        }
    }

    /**
     * Note that this method writes to the original map
     * @param map
     * @param row
     * @param col
     */
    private static void findIslands(int[][] map, int row, int col) {
        try {
            if (map[row][col] == 1) {
                map[row][col] = 0;
                findIslands(map, row + 1, col);
                findIslands(map, row, col + 1);
                findIslands(map, row - 1, col);
                findIslands(map, row, col - 1);
                findIslands(map, row + 1, col + 1);
                findIslands(map, row - 1, col - 1);
                findIslands(map, row + 1, col - 1);
                findIslands(map, row - 1, col + 1);
            }
        } catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Array out of bounds");
        }

    }

    /**
     * method does not write to the original map
     * @param map
     * @return
     */
    private static int countIslands(int[][] map) {
        boolean visited[][] = new boolean [map.length][map[0].length];
        int count = 0;
        for (int row = 0; row < map.length; row ++) {
            for (int col = 0; col < map[0].length; col ++) {
                // if cell in the map is 1 and not visited, then new
                // island found. Visit all cells in this island and
                // increment island count
                if (map[row][col] == 1 && !visited[row][col]) {
                    dfs(map,row,col,visited);
                    count ++;
                }
            }
        }
        return 0;
    }

    /**
     * performs dfs on 2D array, it only considers the 8 adjacent vertices
     * @param map
     * @param row
     * @param col
     * @param visited
     */
    private static void dfs(int[][] map, int row, int col, boolean[][] visited) {
        // used to retrieve the row and col number (offsets)
        int[] rowNumber = new int[]{-1,-1,-1,0,0,1,1,1};
        int[] colNumber = new int[]{-1,0,1,-1,1,-1,0,1};

        visited[row][col] = true;

        for (int k = 0; k < 8; k ++) {
            if (isValid(map, row + rowNumber[k], col + colNumber[k], visited))
                dfs(map, row + rowNumber[k], col + colNumber[k], visited);
        }
    }

    /***
     * check to see if value can be used in the dfs
     * @param map
     * @param row
     * @param col
     * @param visited
     * @return
     */
    private static boolean isValid(int[][] map, int row, int col, boolean[][] visited) {
        // row number and col number is in range
        // map value is 1 and not visited
        return row >= 0 && row < map.length &&
                col >= 0 && col < map[0].length &&
                map[row][col] == 1 && !visited[row][col];
    }
}
