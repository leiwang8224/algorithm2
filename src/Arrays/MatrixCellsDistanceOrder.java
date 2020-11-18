package Arrays;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

//LC1030
public class MatrixCellsDistanceOrder {
    public static void main(String[] args) {
        int[][] result1 = cellsDistOrder(2,2,0,1);
        int[][] result2 = allCellsDistOrder(2,3,1,2);
        int[][] result3 = allCellsDistOrderBFS(2, 2, 0,1);
        for (int[] row : result1) {
            System.out.println(java.util.Arrays.toString(row));
        }
        System.out.println();
        for (int[] row : result2) {
            System.out.println(java.util.Arrays.toString(row));
        }
        System.out.println();
        for (int[] row : result3) {
            System.out.println(java.util.Arrays.toString(row));
        }
    }

    // brute force solution
    // 1. put the cells into a map of <distance, coordinates>
    // 2. iterate through the map and calculate the single dimension coordinates
    public static int[][] cellsDistOrder(int R, int C, int r0, int c0) {
        // <distance, coordinates>
        HashMap<Integer, java.util.List<Integer>> map = new HashMap<>();
        java.util.List<Integer> coordinateList = null;

        for (int row = 0; row < R; row++) {
            for (int col = 0; col < C; col++) {
                int distance = Math.abs(r0-row) + Math.abs(c0-col);
                //save coordinates as x*col+y
                int coordinate = row * C + col;
                if (!map.containsKey(distance))
                    coordinateList = new ArrayList<>();
                else
                    coordinateList = map.get(distance);
                coordinateList.add(coordinate);
                map.put(distance, coordinateList);
            }
        }

        int[][] result = new int[R*C][2];
        int index = 0;

        for (Integer key : map.keySet()) {
            for (int keyIndex = 0; keyIndex < map.get(key).size(); keyIndex++) {
                int coord = map.get(key).get(keyIndex);
                int x = coord / C;
                int y = coord % C;
                result[index++] = new int[]{x,y};
            }
        }
        return result;

    }

    // use count sort, given that the range for dist is small
    public static int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
        int[] counter = new int[R + C + 1];
        for (int row = 0; row < R; row++) {
            for (int col = 0; col < C; col++) {
                // find distance for each cell with respect to r0, c0
                int dist = Math.abs(row - r0) + Math.abs(col - c0);
                // store the dist into hash, dist is not 0 indexed so add 1
                counter[dist + 1] += 1;
            }
        }

        // sum accumulate, counter[i] = counter[i-1] + counter[i]
        for (int i = 1; i < counter.length; i++) {
            counter[i] += counter[i - 1];
        }

        // second dimension does not have to be specified(dynamic)
        int[][] result = new int[R * C][];
        for (int row = 0; row < R; row++) {
            for (int col = 0; col < C; col++) {
                int dist = Math.abs(row - r0) + Math.abs(col - c0);
                result[counter[dist]] = new int[] { row, col };
                counter[dist]++;
            }
        }

        return result;
    }

    // use queue and perform bfs to traverse all cells
    public static int[][] allCellsDistOrderBFS(int R, int C, int r0, int c0) {
        int[][] result = new int[R*C][2];
        int index = 0;

        java.util.Queue<int[]> queue = new LinkedList<>();

        // add initial pos to queue and mark visited
        queue.offer(new int[]{r0, c0});
        boolean[][] visited = new boolean[R][C];
        visited[r0][c0] = true;

        int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};

        while (!queue.isEmpty()) {
            int[] curPos = queue.poll();
            result[index++] = curPos;
            // try all 4 directions and check if valid
            for (int[] dir : directions) {
                int[] nextPos = {curPos[0] + dir[0], curPos[1] + dir[1]};
                // if we go out of bound or land on a cell already visited, go back to top of loop
                if (outOfBound(nextPos[0], nextPos[1], R, C) || visited[nextPos[0]][nextPos[1]]) continue;
                // nextPos is valid position so add to the queue and mark visited
                queue.offer(nextPos);
                visited[nextPos[0]][nextPos[1]] = true;
            }
        }
        return result;
    }

    private static boolean outOfBound(int r, int c, int R, int C) {
        return r < 0 || r >= R || c < 0 || c>= C;
    }


}
