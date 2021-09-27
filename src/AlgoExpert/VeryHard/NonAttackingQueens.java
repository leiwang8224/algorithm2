package AlgoExpert.VeryHard;

import org.omg.CORBA.INTERNAL;

import java.util.HashSet;

public class NonAttackingQueens {
    // Lower bound: O(n!) time | O(n) space - where n is the input number
    /**
     * Go down row by row and check which column to place the Q (check validity)
     * Validity checking does not need to include if in the same row as we iterate row number
     * increment the valid placement counter if the position is valid
     */
    public int nonAttackingQueens(int boardSize) {
        // Each index of 'columnPlacements' represents a row of the chessboard,
        // and the value at each index is the column(on the relevant row) where
        // a queen is currently placed.
        int[] columnPlacementsArray = new int[boardSize];
        // compress board data down to one dim array:
        // [col placement for row0, col placement for row1, col placement for row2, col placement for row3 ...]
        // iterate row from 0 to boardSize
        return getNumberOfNonAttackingQueenPlacements(0, columnPlacementsArray, boardSize);
    }

    private int getNumberOfNonAttackingQueenPlacements(int row,
                                                       int[] columnPlacements,
                                                       int boardSize) {
        if(row == boardSize) return 1; // base case to return 1 non attacking queen

        int validPlacementsCount = 0;
        // iterate col from 0 to boardSize
        for (int col = 0; col < boardSize; col++) {
            if (isNonAttackingPlacement(row, col, columnPlacements)) {
                columnPlacements[row] = col;
                validPlacementsCount += getNumberOfNonAttackingQueenPlacements(row + 1,
                                                                                columnPlacements,
                                                                                boardSize);
            }
        }

        return validPlacementsCount;
    }

    // As 'row' tends to 'n', this becomes an O(n) time operation
    // check for same column and same diagonal placements
    private boolean isNonAttackingPlacement(int curRow, int curCol, int[] columnPlacements) {
        // iterate from row 0 to current row
        for (int prevRow = 0; prevRow < curRow; prevRow++) {
            int columnPlacementFromPrevRow = columnPlacements[prevRow];
            boolean sameColumn = (columnPlacementFromPrevRow == curCol); // can't be in the same col
            // check horizontal distance is same as vertical distance? abs(x2-x1) == abs(y2-y1)
            // or checking to see if diagonals of each other
            boolean onDiagonal = Math.abs(columnPlacementFromPrevRow - curCol) == (curRow - prevRow);
            if (sameColumn || onDiagonal) return false;
        }
        return true;
    }

    // Upper Bound: O(n!) time | O(n) space - where n is the input number
    public int nonAttackingQueens2(int n) {
        // keep track of the blocked columns, upper diagonals,
        // and lower diagonals given a placed queen
        HashSet<Integer> blockedCols = new HashSet<>();
        HashSet<Integer> blockedUpDiagonals = new HashSet<>();
        HashSet<Integer> blockedDownDiagonals = new HashSet<>();
        return getNumberOfNonAttackingQueenPlacements(0,
                                                        blockedCols,
                                                        blockedUpDiagonals,
                                                        blockedDownDiagonals,
                                                        n);
    }

    private int getNumberOfNonAttackingQueenPlacements(int row,
                                                       HashSet<Integer> blockedCols,
                                                       HashSet<Integer> blockedUpDiagonals,
                                                       HashSet<Integer> blockedDownDiagonals,
                                                       int boardSize) {
        if (row == boardSize) return 1;

        int validPlacements = 0;
        for (int col = 0; col < boardSize; col++) {
            if (isNonAttackingPlacement(row, col, blockedCols, blockedUpDiagonals, blockedDownDiagonals)) {
                placeQueen(row, col, blockedCols, blockedUpDiagonals, blockedDownDiagonals);
                validPlacements += getNumberOfNonAttackingQueenPlacements(row + 1,
                                                                         blockedCols,
                                                                         blockedUpDiagonals,
                                                                         blockedDownDiagonals,
                                                                         boardSize);
                removeQueen(row, col, blockedCols, blockedUpDiagonals, blockedDownDiagonals);
            }
        }

        return validPlacements;
    }

    public boolean isNonAttackingPlacement(int row,
                                           int col,
                                           HashSet<Integer> blockedCols,
                                           HashSet<Integer> blockedUpDiagonals,
                                           HashSet<Integer> blockedDownDiagonals) {
        if (blockedCols.contains(col)) {
            return false;
        } else if (blockedUpDiagonals.contains(row + col)) { // upper diagonals = row + col
            return false;
        } else if (blockedDownDiagonals.contains(row - col)) { // lower diagonals = row - col
            return false;
        }

        return true;
    }

    public void placeQueen(int row,
                           int col,
                           HashSet<Integer> blockedCols,
                           HashSet<Integer> blockedUpDiagonals,
                           HashSet<Integer> blockedDownDiagonals) {
        blockedCols.add(col);
        blockedUpDiagonals.add(row + col);
        blockedDownDiagonals.add(row - col);
    }

    public void removeQueen(int row,
                            int col,
                            HashSet<Integer> blockedCols,
                            HashSet<Integer> blockedUpDiagonals,
                            HashSet<Integer> blockedDownDiagonals) {
        blockedCols.remove(col);
        blockedUpDiagonals.remove(row + col);
        blockedDownDiagonals.remove(row - col);
    }

}
