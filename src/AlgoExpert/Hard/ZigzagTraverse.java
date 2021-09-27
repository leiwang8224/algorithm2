package AlgoExpert.Hard;
import java.util.*;
public class ZigzagTraverse {

    // O (n) time | O(n) space
    /**
     * while not going out of bounds:
     * left/bottom of array
     * - check if bottom first (check if we can go down)
     * - Move to the right if can't go down
     * else not left or bottom of array
     *   - going down left
     * top/right of array
     * - check if we are at the utmost right side of array (check if we can go right)
     * - Move to down if can't move right
     * else not top or right of array
     *   - going up right
     */
    private static List<Integer> zigzagTraverse(List<List<Integer>> array) {
        // get dimension limits for the array
        int lastRow = array.size() - 1;
        int lastCol = array.get(0).size() - 1;
        List<Integer> result = new ArrayList<>();
        int row = 0;
        int col = 0;

        // create flag for going down
        boolean goingDownDiagonal = true;

        // out of bounds is when we are at the last element and can't go further
        // everything within the while loop needs to keep the indexing inside the array
        // if goingDown
        //  CHECK limits on the left bottom side
        //  else go down to the left bottom corner
        // else (going Up)
        //  CHECK limits on the right top side
        //  else go up to the right top corner

        // goingDown
        // - left or bottom
        //   - set goingDown to false
        //   - move to right if at bottom
        //   - move down if not bottom
        // - anywhere else not left or bottom (top or right)
        //   - move diagonally to left bottom corner
        // !goingDown
        // - top or right
        //   - set goingDown to true
        //   - move down if at extreme right
        //   - move right if not at extreme right
        // - anywhere else not top or right (left or bottom)
        //   - move diagonally to right top corner
        while (!isOutOfBounds(row, col, lastRow, lastCol)) {
            result.add(array.get(row).get(col));
            // down, right or left bottom;
            if (goingDownDiagonal) { // goingDown = true so set to false
                // if on left or bottom of the array
                if (col == 0 || row == lastRow) {
                    goingDownDiagonal = false;
                    if (row == lastRow) {
                        // move to the right since we can't go down
                        col++;
                    } else { // we can go down so go down
                        // move down
                        row++;
                    }
                    // already moved right or moved down, can't go down in next
                    // iteration, change direction
                } else { // top or right
                    // going down to the left bottom corner when not at left or bottom of array
                    row++;
                    col--;
                }
            } else { // down, right or right top;
                // if on the top or right of array
                if (row == 0 || col == lastCol) {
                    goingDownDiagonal = true; // flip direction
                    // can always go down
                    if (col == lastCol) {
                        // go down if can't go right
                        row++;
                    } else {
                        // go right
                        col++;
                    }
                } else { // left or bottom
                    // going up to the right top corner
                    row--;
                    col++;
                }
            }
        }
        return result;
    }

    private static boolean isOutOfBounds(int row, int col, int height, int width) {
        return row < 0 || row > height || col < 0 || col > width;
    }
}
