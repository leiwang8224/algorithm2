package Graph;

import java.util.LinkedList;

//Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
// An island is surrounded by water and is formed by connecting adjacent lands
// horizontally or vertically. You may assume all four edges of the grid are all
// surrounded by water.
//
//        Example 1:
//
//        11110
//        11010
//        11000
//        00000
public class FindIslands {
    public static void main(String args[]) {
        System.out.println("number of islands DFS " + numIslandsDFS(generateMap()));
        System.out.println("number of islands BFS " + numIslandsBFS(generateMap()));

        NumberofIslands numberofIslands = new NumberofIslands();
        numberofIslands.explore(generateMap(),0,0);
        System.out.println("number of islands " + numberofIslands.numIslands(generateMap()));

        Solution soln = new Solution();
        System.out.println("number of islands soln1 " + soln.numIslands(generateMap()));

        Solution2 soln2 = new Solution2();
        System.out.println("number of islands soln2 " + soln2.numIslands(generateMap()));

        System.out.println("countIslands "+ countIslands(generateMap()));
        System.out.println("numIslands " + numIslands(generateMap()));

    }

    static int[][] generateMap() {
        return new int[][] { {1, 1, 1, 1, 0},
                {1, 1, 0, 1, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 0, 0, 1}
        };
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
     * Clear solution
     * find the island labelled 1
     * clear the rest of the surrounding lands labelled 1
     */
    public static class Solution2 {
        public int numIslands(int[][] grid) {
            int count = 0;

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == 1) {
                        // island found, increment the count
                        count++;
                        // clear the rest of the land
                        clearRestOfLand(grid, i, j);
                    }
                }
            }
            return count;
        }

        private void clearRestOfLand(int[][] grid, int i, int j) {
            // if index is out of bounds, return
            if (i < 0 || j < 0 ||
                i >= grid.length ||
                j >= grid[i].length ||
                grid[i][j] == 0) return;

            // where the clear happens
            grid[i][j] = 0;

            // recursive call to i+1, i-1, j+1, j-1
            clearRestOfLand(grid, i+1, j);
            clearRestOfLand(grid, i-1, j);
            clearRestOfLand(grid, i, j+1);
            clearRestOfLand(grid, i, j-1);
            return;
        }
    }
    /**
     * method does not write to the original map
     * using a separate array to keep the index offset(+1, 0, -1)
     * then perform dfs
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
                    // traverse the surrounding lands (1's)
                    dfs(map,row,col,visited);
                    count ++;
                }
            }
        }
        return count;
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
            // if index is within range then traverse further (recursive call)
            // check surrounding 8 blocks
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

    /**
     * Simply makr the grid as 0 if it's 1
     */
    public static class Solution {

        private int n;
        private int m;

        public int numIslands(int[][] grid) {
            int count = 0;
            n = grid.length;
            if (n == 0) return 0;
            m = grid[0].length;
            for (int i = 0; i < n; i++){
                for (int j = 0; j < m; j++)
                    if (grid[i][j] == 1) {
                        // mark grid as 0 if it was 1
                        DFSMarking(grid, i, j);
                        ++count;
                    }
            }
            return count;
        }

        private void DFSMarking(int[][] grid, int i, int j) {
            if (i < 0 || j < 0 || i >= n || j >= m || grid[i][j] != 1) return;
            grid[i][j] = 0;
            DFSMarking(grid, i + 1, j);
            DFSMarking(grid, i - 1, j);
            DFSMarking(grid, i, j + 1);
            DFSMarking(grid, i, j - 1);
        }

    }

    /**
     * For any problem I work on, I will try to generalize some reusable template
     * out for future use. We have limited time during interview and too much to
     * worry about, so having some code template to use is very handy. For this
     * problem, although it is easier and probably suggested to use BFS, but Union
     * find also comes handy and can be easily extended to solve Island 2 and
     * Surrounded regions.

     I separate all the union find logic in a separate class and use 1d version to
     make the code clear. I also use a 2d array for the 4 direction visit.
     int[][] distance = {{1,0},{-1,0},{0,1},{0,-1}};
     */
    static int[][] distance = {{1,0},{-1,0},{0,1},{0,-1}};
    public static int numIslands(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0)  {
            return 0;
        }
        UnionFind uf = new UnionFind(grid);
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    for (int[] d : distance) {
                        int x = i + d[0];
                        int y = j + d[1];
                        if (x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y] == 1) {
                            int id1 = i*cols+j;
                            int id2 = x*cols+y;
                            uf.union(id1, id2);
                        }
                    }
                }
            }
        }
        return uf.count;
    }

    static class UnionFind {
        int[] father;
        int m, n;
        int count = 0;
        UnionFind(int[][] grid) {
            m = grid.length;
            n = grid[0].length;
            father = new int[m*n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        int id = i * n + j;
                        father[id] = id;
                        count++;
                    }
                }
            }
        }
        public void union(int node1, int node2) {
            int find1 = find(node1);
            int find2 = find(node2);
            if(find1 != find2) {
                father[find1] = find2;
                count--;
            }
        }
        public int find (int node) {
            if (father[node] == node) {
                return node;
            }
            father[node] = find(father[node]);
            return father[node];
        }
    }

    /**
     * The algorithm works as follow:

     Scan each cell in the grid.
     If the cell value is ‘1’ explore that island.
     Mark the explored island cells with ‘x’.
     Once finished exploring that island, increment islands counter.

     The arrays dx[], dy[] store the possible moves from the current cell. Two land cells [‘1’]
     are considered from the same island if they are horizontally or vertically adjacent
     (possible moves (-1,0),(0,1),(0,-1),(1,0)). Two ‘1’ diagonally adjacent are not considered
     from the same island.
     */
    public static class NumberofIslands {
        int[] dx = {-1,0,0,1};
        int[] dy = {0,1,-1,0};
        public int numIslands(int[][] grid) {
            if(grid==null || grid.length==0) return 0;
            int islands = 0;
            for(int i=0;i<grid.length;i++) {
                for(int j=0;j<grid[i].length;j++) {
                    if(grid[i][j]==1) {
                        explore(grid,i,j);
                        islands++;
                    }
                }
            }
            return islands;
        }
        public void explore(int[][] grid, int i, int j) {
            grid[i][j]='x';
            for(int d=0;d<dx.length;d++) {
                if(i+dy[d]<grid.length && i+dy[d]>=0 && j+dx[d]<grid[0].length && j+dx[d]>=0 && grid[i+dy[d]][j+dx[d]]==1) {
                    explore(grid,i+dy[d],j+dx[d]);
                }
            }
        }
    }

    /**
     * dfs method
     * @param grid
     * @return
     */
    public static int numIslandsDFS(int[][] grid) {
        int count=0;
        for(int i=0;i<grid.length;i++)
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j]==1){
                    dfsFill(grid,i,j);
                    count++;
                }
            }
        return count;
    }
    private static void dfsFill(int[][] grid,int i, int j){
        if(i>=0 && j>=0 && i<grid.length && j<grid[0].length&&grid[i][j]==1){
            grid[i][j]=0;
            dfsFill(grid, i + 1, j);
            dfsFill(grid, i - 1, j);
            dfsFill(grid, i, j + 1);
            dfsFill(grid, i, j - 1);
        }
    }

    /**
     * bfs method
     * @param grid
     * @return
     */
    public static int numIslandsBFS(int[][] grid) {
        int count=0;
        for(int i=0;i<grid.length;i++)
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j]==1){
                    bfsFill(grid,i,j);
                    count++;
                }
            }
        return count;
    }
    private static void bfsFill(int[][] grid,int x, int y){
        grid[x][y]=0;
        int n = grid.length;
        int m = grid[0].length;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        int code = x*m+y;
        queue.offer(code);
        while(!queue.isEmpty())
        {
            code = queue.poll();
            int i = code/m;
            int j = code%m;
            if(i>0 && grid[i-1][j]==1)    //search upward and mark adjacent '1's as '0'.
            {
                queue.offer((i-1)*m+j);
                grid[i-1][j]=0;
            }
            if(i<n-1 && grid[i+1][j]==1)  //down
            {
                queue.offer((i+1)*m+j);
                grid[i+1][j]=0;
            }
            if(j>0 && grid[i][j-1]==1)  //left
            {
                queue.offer(i*m+j-1);
                grid[i][j-1]=0;
            }
            if(j<m-1 && grid[i][j+1]==1)  //right
            {
                queue.offer(i*m+j+1);
                grid[i][j+1]=0;
            }
        }
    }

}
