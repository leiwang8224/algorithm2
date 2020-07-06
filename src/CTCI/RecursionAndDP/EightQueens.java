package CTCI.RecursionAndDP;

import java.util.ArrayList;

/**
 * Write an algorithm to print all ways of arranging eight queens on an 8x8 chess board
 * so that none of them share the same row, column, or diagonal. In this case, 'diagonal'
 * means all diagonals, not just the two that bisect the board.
 */
public class EightQueens {
    public static void main(String[] args) {
        ArrayList<Integer[]> results = new ArrayList<>();
        Integer[] columns = new Integer[GRID_SIZE];
        clear(columns);
        placeQueens(0, columns, results);
//        printBoards(results);
        System.out.println(results.size());
    }

    /**
     * Picture the queen that is placed last, which we will assume is on row 8. (this is
     * okay assumption to make since the ordering of placing the queens is irrevelant).
     * On which cell in row 8 is this queen? there are eight possibilities, one for
     * each column.
     * So if we want to know all valid ways of arranging 8 queens on an 8x8 chess board,
     * it would be:
     *  ways to arrange 8 queens on an 8x8 board =
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,0) +
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,1) +
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,2) +
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,3) +
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,4) +
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,5) +
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,6) +
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,7)
     *
     *  we can compute each one of these using a very similar approach:
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,3) and (6,0) +
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,3) and (6,1) +
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,3) and (6,2) +
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,3) and (6,3) +
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,3) and (6,4) +
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,3) and (6,5) +
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,3) and (6,6) +
     *      ways to arrange 8 queens on an 8x8 board with queen at (7,3) and (6,7)
     *
     *  note that we don't need to consider combinations with queens at (7,3) and (6,3),
     *  since this is a violation of the requirement that every queen is in its own row,
     *  column and diagonal.
     */
    static int GRID_SIZE = 8;

    /**
     * Observe that since each row can only have one queen, we don't need to store our board
     * as a full 8x8 matrix. We only need a single array where column[r] = c indicates
     * that row r has a queen at column c.
     * Iterate through the rows by recursion and verify placement of the queens.
     * @param rowToPlaceQueen: row to check for validity
     * @param boardColumns: Array that represents the whole board and location of the queens.
     *                    Ex: rows[0] = 1, position [0,1] has queen
     * @param results: final results
     */
    static void placeQueens(int rowToPlaceQueen,
                            Integer[] boardColumns,
                            ArrayList<Integer[]> results) {
        // add the whole column if at the end of the rows
        if (rowToPlaceQueen == GRID_SIZE) results.add(boardColumns.clone()); // found valid placement
        else {
            // for each column in rowToPlaceQueen, try placing the queen
            for(int colIndexForQueen = 0; colIndexForQueen < GRID_SIZE; colIndexForQueen++) {
                if (checkValid(boardColumns, rowToPlaceQueen, colIndexForQueen)) {
                    // since position is valid we will place on board
                    boardColumns[rowToPlaceQueen] = colIndexForQueen;
                    printBoard(boardColumns);
                    // recursive on the row number
                    placeQueens(rowToPlaceQueen + 1,
                                boardColumns,
                                results);
                } // else do nothing
            }
        }
    }

    /**
     * Check if (row, column) is a valid spot for a queen by checking if there is a
     * queen in the same column or diagonal. We don't need to check it for queens in the
     * same row because the calling placeQueen only attempts to place one queen at
     * a time. We know this row is empty
     * @param boardColumns: whole board represented as an array of columns.
     *                    Ex: rows[0] = 1, or [0,1] has a queen.
     * @param queenPosRowIndex: possible queen position in row
     * @param queenPosColIndex: possible queen position in column
     * @return
     */
    private static boolean checkValid(Integer[] boardColumns,
                                      int queenPosRowIndex,
                                      int queenPosColIndex) {
        // iterate through each row from 0 up to queenPosRowIndex
        // in the board and find corresponding columns for queen position
        for (int rowIndexToIterate = 0;
             rowIndexToIterate < queenPosRowIndex;
             rowIndexToIterate++) {
            int colIndexAtCurrentRow = boardColumns[rowIndexToIterate];
            // check if (rowIndex, columnIndex) invalidates (row, col) as a queen spot

            // check if rows have a queen in the same column
            if (queenPosColIndex == colIndexAtCurrentRow)
                return false;

            // check diagonals: if the distance between the columns equals the distance
            // between the rows, then they are in the same diagonal.
            int columnDistance = Math.abs(colIndexAtCurrentRow - queenPosColIndex);

            // row > rowIndex, so no need for abs
            int rowDistance = queenPosRowIndex - rowIndexToIterate;

            // diagonal Queen found
            if (columnDistance == rowDistance)
                return false;
        }
        return true;

    }

    private static void clear(Integer[] cols) {
        for (int i = 0; i < GRID_SIZE; i++)
            cols[i] = -1;
    }

    private static void printBoard(Integer[] cols) {
        drawLine();
        for (int row = 0; row < GRID_SIZE; row++) {
            System.out.print("|");
            for (int col = 0; col < GRID_SIZE; col++) {
                if(cols[row] == col) {
                    System.out.print("Q|");
                } else {
                    System.out.print(" |");
                }
            }
            System.out.print("\n");
            drawLine();
        }
        System.out.println();
    }

    private static void drawLine() {
        StringBuilder line = new StringBuilder();
        for (int row = 0; row < GRID_SIZE*2+1; row++)
            line.append('-');
        System.out.println(line.toString());
    }

    private static void printBoards(ArrayList<Integer[]> boards) {
        for (int row = 0; row < boards.size(); row++) {
            Integer[] board = boards.get(row);
            printBoard(board);
        }
    }


}
