package AlgoExpert.Medium;

import java.util.ArrayList;
import java.util.List;

public class SpiralTraverse {
    public static void main(String[] args) {

    }

    // O(n) time | O(n) space

    /**
     * The way to think about this problems is to set boundaries, start and ending indices
     * then reduce the range on the limits in each loop iteration until starting and ending
     * points meet
     */
    private static java.util.List<Integer> spiralTraverse(int[][] array) {
        if (array.length == 0) return new ArrayList<>();

        java.util.List<Integer> result = new ArrayList<>();
        int startRow = 0;
        int endRow = array.length-1;
        int startCol = 0;
        int endCol = array[0].length-1;

        // you do want startRow and endRow to meet, startCol and endCol to meet
        // need equal as well because we do want to evaluate when startRow == endRow, startCol == endCol
        while (startRow <= endRow && startCol <= endCol) {
            // include endCol
            for (int col = startCol; col <= endCol; col++) {
                result.add(array[startRow][col]);
            }

            // include endRow
            for (int row = startRow + 1; row <= endRow; row++) {
                result.add(array[row][endCol]);
            }

            // include startCol
            for (int col = endCol - 1; col >= startCol; col--) {
                // Handle the edge case when there is a single row
                // in the middle of the matrix. In this case, we don't
                // want to double count the values in this row, which
                // we have already counted in the first for loop above.
                if (startRow == endRow) break;
                result.add(array[endRow][col]);
            }

            // do not include the startRow
            for (int row = endRow -1; row > startRow; row--) {
                // Handle the edge case when there is a single column
                // in the middle of the matrix. IN this case, we don't
                // want to double count the values in this column, which
                // we have already counted in the second for loop above.
                if (startCol == endCol) break;
                result.add(array[row][startCol]);
            }

            // compress the startRow, endRow, startCol, endCol till they meet
            // in the middle
            startRow++;
            endRow--;
            startCol++;
            endCol--;
        }
        return result;
    }

    // O(n) time | O(n) space
    private static java.util.List<Integer> spiralTraverse2(int[][] array) {
        if (array.length == 0) return new ArrayList<>();

        java.util.List<Integer> result = new ArrayList<>();
        spiralFill(array,
                0,
                array.length-1,
                0,
                array[0].length-1,
                result);
        return result;
    }

    private static void spiralFill(int[][] array,
                                   int startRow,
                                   int endRow,
                                   int startCol,
                                   int endCol,
                                   List<Integer> result) {
        if (startRow > endRow || startCol > endCol) return;

        for (int col = startCol; col <= endCol; col++) {
            result.add(array[startRow][col]);
        }

        for (int row = startRow+1; row <= endRow; row++) {
            result.add(array[row][endCol]);
        }

        for (int col = endCol - 1; col >= startCol; col--) {
            // Handle the edge case when there is a single row
            // in the middle of the matrix. In this case, we don't
            // want to double count the values in this row, which
            // we have already counted in the first for loop above.
            if (startRow == endRow) break;
            result.add(array[endRow][col]);
        }

        for (int row = endRow -1; row >= startRow; row--) {
            if (startCol == endCol) break;
            result.add(array[row][startCol]);
        }
        spiralFill(array,
                startRow + 1,
                endRow-1,
                startCol+1,
                endCol-1,
                result);
    }

}
