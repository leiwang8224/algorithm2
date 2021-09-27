package AlgoExpert.VeryHard;
import java.util.*;

public class SquareOfZeros {
   // O(n^4) | O(n^3) space where n is the height and width of the matrix
    boolean squareOfZeros(List<List<Integer>> matrix) {
       int lastIdx = matrix.size() - 1;
       Map<String, Boolean> cache = new HashMap<>();
       return hasSquareOfZerosRecursive(matrix, 0, 0, lastIdx, lastIdx, cache);
   }

   // r1 is the top row, c1 is the left col
    // r2 is the bottom row, c2 is the right col
    private boolean hasSquareOfZerosRecursive(List<List<Integer>> matrix,
                                              int leftTopCornerRow,
                                              int leftTopCornerCol,
                                              int leftBottomCornerRow,
                                              int rightBottomCornerRow,
                                              Map<String, Boolean> cache) {
        if (leftTopCornerRow >= leftBottomCornerRow || leftTopCornerCol >= rightBottomCornerRow) return false;

        String key = String.valueOf(leftTopCornerRow) + '-' +
                String.valueOf(leftTopCornerCol) + '-' +
                String.valueOf(leftBottomCornerRow) + '-' +
                String.valueOf(rightBottomCornerRow);
        if (cache.containsKey(key)) return cache.get(key);

        cache.put(key,
                isSquareOfZeroes(matrix,
                        leftTopCornerRow,
                        leftTopCornerCol,
                        leftBottomCornerRow,
                        rightBottomCornerRow)
                        || hasSquareOfZerosRecursive(matrix,  // find all the squares
                        leftTopCornerRow + 1,
                        leftTopCornerCol + 1,
                        leftBottomCornerRow - 1,
                        rightBottomCornerRow - 1, cache)
                || hasSquareOfZerosRecursive(matrix,
                        leftTopCornerRow,
                        leftTopCornerCol + 1,
                        leftBottomCornerRow - 1,
                        rightBottomCornerRow, cache)
                || hasSquareOfZerosRecursive(matrix,
                        leftTopCornerRow + 1,
                        leftTopCornerCol,
                        leftBottomCornerRow,
                        rightBottomCornerRow - 1, cache)
                || hasSquareOfZerosRecursive(matrix,
                        leftTopCornerRow + 1,
                        leftTopCornerCol + 1,
                        leftBottomCornerRow,
                        rightBottomCornerRow, cache)
                || hasSquareOfZerosRecursive(matrix,
                        leftTopCornerRow,
                        leftTopCornerCol,
                        leftBottomCornerRow - 1,
                        rightBottomCornerRow - 1, cache));

        return cache.get(key);
    }

    private Boolean isSquareOfZeroes(List<List<Integer>> matrix,
                                     int topRow,
                                     int leftCol,
                                     int bottomRow,
                                     int rightCol) {
        // if any of the row element or col element is non zero return immediately
        for (int row = topRow; row < bottomRow + 1; row++) {
            if (matrix.get(row).get(leftCol) != 0 || matrix.get(row).get(rightCol) != 0) return false;
        }

        for (int col = leftCol; col < rightCol + 1; col++) {
            if (matrix.get(topRow).get(col) != 0 || matrix.get(bottomRow).get(col) != 0) return false;
        }

        return true;
    }

    // O(n^4) time | O(1) space - where n is the height and width of the matrix
    boolean squareOfZerosIterative(List<List<Integer>> matrix) {
        int n = matrix.size();
        // treat each (row, col) point as the top left corner of the square
        for (int topRowOfSquare = 0; topRowOfSquare < n; topRowOfSquare++) {  // n
            for (int leftColOfSquare = 0; leftColOfSquare < n; leftColOfSquare++) {  // n^2
                int squareLen = 2; // a square of zeroes has to be at least 2 x 2
                // get coordinates of the bottom row and right col of the square
                while (squareLen <= n - leftColOfSquare && squareLen <= n - topRowOfSquare) {  // n^3
                    int bottomRowOfSquare = topRowOfSquare + squareLen -1;
                    int rightColOfSquare = leftColOfSquare + squareLen -1;
                    if (isSquareOfZeroes(matrix,    // n^4
                                        topRowOfSquare,
                                        leftColOfSquare,
                                        bottomRowOfSquare,
                                        rightColOfSquare)) return true;
                    squareLen++;
                }
            }
        }
        return false;
    }

    // precompute the zeroes to the right and zeroes below any cell
    class InfoMatrixItem {
        int numZeroesBelow;
        int numZerosRight;
        InfoMatrixItem(int numZeroesBelow, int numZerosRight) {
            this.numZeroesBelow = numZeroesBelow;
            this.numZerosRight = numZerosRight;
        }
    }

    // O(n^3) time | O(n^3) space - where n is the height and width of the matrix
    boolean squareOfZerosDPRecursive(List<List<Integer>> matrix) {
        List<List<InfoMatrixItem>> infoMatrix = preComputedNumOfZeroes(matrix);
        int lastIdx = matrix.size() - 1;
        Map<String, Boolean> cache = new HashMap<>();
        return hasSquareOfZerosDPRecursive(infoMatrix, 0, 0, lastIdx, lastIdx, cache);
    }

    private List<List<InfoMatrixItem>> preComputedNumOfZeroes(List<List<Integer>> matrix) {
        List<List<InfoMatrixItem>> infoMatrix = new ArrayList<>();
        for (int row = 0; row < matrix.size(); row++) {
            List<InfoMatrixItem> inner = new ArrayList<>();
            for (int col = 0; col < matrix.get(row).size(); col++) {
                int numZeroesAtCurCell = matrix.get(row).get(col) == 0 ? 1 : 0; //num zeroes is 1 if cur cell is 0
                inner.add(new InfoMatrixItem(numZeroesAtCurCell, numZeroesAtCurCell));
            }
            infoMatrix.add(inner);
        }

        // iterate through the array from bottom right corner and add up all the zeroes below and to the right
        int lastIdx = matrix.size() - 1;
        for (int row = lastIdx; row >= 0; row--) { // go up
            for (int col = lastIdx; col >= 0; col--) { // go left
                if (matrix.get(row).get(col) == 1) continue; // if the cell is 1, skip (0)
                // at this point the cell is 0 so add up the number of zeroes below and to the right
                if (row < lastIdx) {
                    infoMatrix.get(row).get(col).numZeroesBelow +=
                            infoMatrix.get(row + 1).get(col).numZeroesBelow;
                }

                if (col < lastIdx) {
                    infoMatrix.get(row).get(col).numZerosRight +=
                            infoMatrix.get(row).get(col + 1).numZerosRight;
                }
            }
        }
        return infoMatrix;
    }

    boolean hasSquareOfZerosDPRecursive(List<List<InfoMatrixItem>> matrix,
                                        int topLeftCornerRow,
                                        int topLeftCornerCol,
                                        int topRightCornerRow,
                                        int topRightCornerCol,
                                        Map<String, Boolean> cache) {
        if (topLeftCornerRow >= topRightCornerRow || topLeftCornerCol >= topRightCornerCol) return false;

        String key = String.valueOf(topLeftCornerRow) + '-' + String.valueOf(topLeftCornerCol) + '-' + String.valueOf(topRightCornerRow) + '-' + String.valueOf(topRightCornerCol);
        if (cache.containsKey(key)) return cache.get(key);

        cache.put(key,
                isSquareOfZeroesDP(matrix, topLeftCornerRow, topLeftCornerCol, topRightCornerRow, topRightCornerCol) ||
                        hasSquareOfZerosDPRecursive(matrix, topLeftCornerRow + 1, topLeftCornerCol + 1, topRightCornerRow - 1, topRightCornerCol - 1, cache)
                        || hasSquareOfZerosDPRecursive(matrix, topLeftCornerRow,topLeftCornerCol + 1, topRightCornerRow - 1, topRightCornerCol, cache)
                        || hasSquareOfZerosDPRecursive(matrix, topLeftCornerRow + 1, topLeftCornerCol, topRightCornerRow, topRightCornerCol - 1, cache)
                        || hasSquareOfZerosDPRecursive(matrix, topLeftCornerRow + 1, topLeftCornerCol + 1, topRightCornerRow, topRightCornerCol, cache)
                        || hasSquareOfZerosDPRecursive(matrix, topLeftCornerRow, topLeftCornerCol, topRightCornerRow - 1, topRightCornerCol - 1, cache));

        return cache.get(key);
    }

    // check if the number of zeroes on all 4 sides are greater than or equal to the length of square of interest
    boolean isSquareOfZeroesDP(List<List<InfoMatrixItem>> infoMatrix,
                               int topLeftCornerRow,
                               int topLeftCornerCol,
                               int topRightCornerRow,
                               int topRightCornerCol) {
        int squareLen = topRightCornerCol - topLeftCornerCol + 1;
        // considering the given point is the top left corner of the square
        boolean hasTopBorder = infoMatrix.get(topLeftCornerRow).get(topLeftCornerCol).numZerosRight >= squareLen;
        boolean hasLeftBorder = infoMatrix.get(topLeftCornerRow).get(topLeftCornerCol).numZeroesBelow >= squareLen;
        boolean hasBottomBorder = infoMatrix.get(topRightCornerRow).get(topLeftCornerCol).numZerosRight >= squareLen;
        boolean hasRightBorder = infoMatrix.get(topLeftCornerRow).get(topRightCornerCol).numZeroesBelow >= squareLen;
        return hasTopBorder && hasLeftBorder && hasBottomBorder && hasRightBorder;
    }


    // O(n^3) time | O(n^2) space where n is the height and width of matrix
    boolean squareOfZeroesDPIterative(List<List<Integer>> matrix) {
        List<List<InfoMatrixItem>> infoMatrix = preComputedNumOfZeroes(matrix);
        int sizeOfMatrix = matrix.size();
        for (int topRow = 0; topRow < sizeOfMatrix; topRow ++) {
            for (int leftCol = 0; leftCol < sizeOfMatrix; leftCol ++) {
                int squareLen = 2;  // a square of zeroes has to be at least 2 x 2 so we start from 2 to n
                while(squareLen <= sizeOfMatrix - leftCol && squareLen <= sizeOfMatrix - topRow) {
                    int bottomRow = topRow + squareLen - 1;
                    int rightCol = leftCol + squareLen - 1;
                    if (isSquareOfZeroesDP(infoMatrix, topRow, leftCol, bottomRow, rightCol)) return true;
                    squareLen++;
                }
            }
        }
        return false;
    }

}
