package AlgoExpert.Medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RiverSizes {
    public static void main(String[] args) {

    }

    // Time O(w * h) | Space O(N)

    /**
     * main method traverses each cell in the matrix and reject visited cells
     */
    private static java.util.List<Integer> riverSizes(int[][] matrix) {
        java.util.List<Integer> result = new ArrayList<>();
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (visited[row][col]) { // skip if visited
                    continue;
                }
                // traverse nearby cells to find paths for river
                // you want to explore EVEN if it's not river (0)
                traverseNeighbors(row, col, matrix, visited, result);
            }
        }
        return result;
    }

    private static boolean posValid(int row, int col, int[][] matrix, boolean[][] visited) {
        if (row < 0 ||
                row >= matrix.length ||
                col < 0 ||
                col >= matrix[0].length ||
                visited[row][col] == true) {
            return false;
        }
        return true;
    }

    // after pop, check visited and check if land, continue to next loop if it is
    //
    private static void traverseNeighbors(int row,
                                          int col,
                                          int[][] matrix,
                                          boolean[][] visited,
                                          List<Integer> sizes) {
        int curRiverSize = 0;
        // start the dfs by pushing the starting pos onto stack
        Stack<Integer[]> nodesToExplore = new Stack<>();
        nodesToExplore.push(new Integer[]{row, col});
        while (!nodesToExplore.empty()) {
            Integer[] curNode = nodesToExplore.pop();
            row = curNode[0];
            col = curNode[1];
            // if new location is visited goto next iteration
            if (visited[row][col]) {
                continue;
            }
            // mark as visited
            visited[row][col] = true;
            // if new location is land goto next iteration
            if (matrix[row][col] == 0) {
                continue;
            }
            // increment river size
            curRiverSize++;
            // add to the list if it's a valid position (not visited and not outside of the boundaries)
            List<Integer[]> unvisitedNeighbors = getNeighborsUnvisited(row, col, matrix, visited);
            for (Integer[] neighbor : unvisitedNeighbors) {
                nodesToExplore.push(neighbor);
            }
        }
        // only add if river size is greater than 0
        if (curRiverSize > 0) sizes.add(curRiverSize);
    }

    // check for border restrictions and visited
    private static List<Integer[]> getNeighborsUnvisited(int row, int col, int[][] matrix, boolean[][] visited) {
        List<Integer[]> unvisitedNeighbors = new ArrayList<>();
        // try all 4 directions that are valid positions
        if (row > 0 && !visited[row-1][col]) {
            unvisitedNeighbors.add(new Integer[] { row - 1, col});
        }

        if (row < matrix.length-1 && !visited[row+1][col]) {
            unvisitedNeighbors.add(new Integer[] {row + 1, col});
        }

        if (col > 0 && !visited[row][col-1]) {
            unvisitedNeighbors.add(new Integer[] {row, col-1});
        }

        if (col < matrix[0].length -1 && !visited[row][col+1]) {
            unvisitedNeighbors.add(new Integer[] {row, col+1});
        }

        return unvisitedNeighbors;
    }

    /**
     * Not Tested
     */
    private static void traverseNodeDirections(int row, int col, int[][] matrix, boolean[][] visited, List<Integer> result) {
        int curRiverSize = 0;
        Stack<Integer[]> nodesToExplore = new Stack<>();
        nodesToExplore.push(new Integer[]{row, col});
        int[][] directions = new int[][]{{0,1}, {1,0}, {0,-1}, {-1,0}};

        while (!nodesToExplore.empty()) {
            Integer[] curNode = nodesToExplore.pop();
            row = curNode[0];
            col = curNode[1];
            // check if visited again
            if (visited[row][col]) {
                continue;
            }
            visited[row][col] = true;
            // if not part of the river just continue
            if (matrix[row][col] == 0) {
                continue;
            }
            curRiverSize++;
            // add to the list if it's a valid position (not visited and not outside of the boundaries)
            for (int[] direction : directions) {
                row = row + direction[0];
                col = col + direction[1];
                if (posValid(row, col, matrix, visited)) {
                    nodesToExplore.push(new Integer[]{row, col});
                }
            }
        }
        if (curRiverSize > 0) result.add(curRiverSize);
    }
}
