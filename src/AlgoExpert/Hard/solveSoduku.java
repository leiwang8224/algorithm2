package AlgoExpert.Hard;

import CTCI.RecursionAndDP.RobotInAGrid;

import java.util.*;
public class solveSoduku {
    // O(1) time | O(1) space
    ArrayList<ArrayList<Integer>> solveSoduku(ArrayList<ArrayList<Integer>> board) {
        solvePartialSodoku(0,0, board);
        return board;
    }

    private boolean solvePartialSodoku(int row, int col, ArrayList<ArrayList<Integer>> board) {
        int curRow = row;
        int curCol = col;
        if (curCol == board.get(curRow).size()) {
            curRow += 1;
            curCol = 0;
            if (curRow == board.size()) {
                return true; // board completed
            }
        }

        if (board.get(curRow).get(curCol) == 0) {
            return tryDigitsAtPosition(curRow, curCol, board);
        }

        return solvePartialSodoku(curRow, curCol + 1, board);
    }

    private boolean tryDigitsAtPosition(int curRow, int curCol, ArrayList<ArrayList<Integer>> board) {
        for (int digit = 1; digit < 10; digit ++) {
            if (isValidAtPosition(digit, curRow, curCol, board)) {
                board.get(curRow).set(curCol, digit);
                if (solvePartialSodoku(curRow, curCol + 1, board)) {
                    return true;
                }
            }
        }

        board.get(curRow).set(curCol, 0);
        return false;
    }

    private boolean isValidAtPosition(int value, int curRow, int curCol, ArrayList<ArrayList<Integer>> board) {
        boolean rowIsValid = !board.get(curRow).contains(value);
        boolean colIsValid = true;

        for (int r = 0; r < board.size(); r++) {
            if (board.get(r).get(curCol) == value)
                colIsValid = false;
        }

        if (!rowIsValid || !colIsValid) return false;

        // check subgrid constraints
        int subgridRowStart = (curRow / 3) * 3;
        int subgridColStart = (curCol / 3) * 3;

        for (int rowIdx = 0; rowIdx < 3; rowIdx++) {
            for (int colIdx = 0; colIdx < 3; colIdx++) {
                int rowToCheck = subgridRowStart + rowIdx;
                int colToCheck = subgridColStart + colIdx;
                int existingValue = board.get(rowToCheck).get(colToCheck);

                if (existingValue == value) return false;
            }
        }
        return true;
    }
}
