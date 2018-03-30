package Arrays;

/**
 * Created by leiwang on 3/26/18.
 */
public class FindIslandPerimeter {
    public static void main(String args[]) {
        int[][] array = new int[][] {{0,1,0,0},
                {1,1,1,0},
                {0,1,0,0},
                {1,1,0,0}};
       System.out.println("perimeter of island is " + findArea(array));
    }

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
