package Recursion;

/**
 * Created by leiwang on 4/3/18.
 */
public class NQueens {
    static final int N = 4;
    public static void main(String args[]) {
        solveNQAllSoln();
    }

    private static boolean solveNQUtilAllSoln(int board[][], int col) {
        // base case if all queens are placed
        if (col == N) {
            printSolution(board);
            return true;
        }

        // consider this column and try placing this
        // queen in all rows one by one
        boolean res = false;
        for (int row = 0; row < N; row++) {
            // check if queen can be placed on board[row][col]
            if (isSafe(board, row, col)) {
                // place this queen in board[row][col]
                board[row][col] = 1;

                // make result true if any placement is possible
                res = solveNQUtilAllSoln(board, col + 1) || res;

                // if placing queen in board[row][col]
                // doesn't lead to a solution, then
                // remove queen from board[row][col]
                board[row][col] = 0;  // backtrack
            }
        }

        return res;
    }

    private static void printSolution(int[][] board) {
        int k = 1;
        System.out.println("Solution " + k);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j ++) {
                System.out.print(board[i][j]);
            }
            System.out.print("\n");
        }
        k++;

    }

    private static boolean solveNQUtil(int board[][], int col) {
        // base case, if all queens are placed then
        // return true
        if (col >= N)
            return true;
        
        // consider this column and try placing this queen
        // in all rows one by one
        for (int i = 0; i < N; i++) {
            // check if queen can be placed on
            // board[i][col]
            if (isSafe(board, i, col)) {
                // place this queen in board[i][col]
                board[i][col] = 1;

                // recur to place rest of queens
                if (solveNQUtil(board, col + 1))
                    return true;

                // if placing queen in board[i][col]
                // doesn't lead to a solution then
                // remove queen from board[i][col]
                board[i][col] = 0; // backtrack
            }
        }

        // if queen can not be placed in any row
        // in this column col, then return false
        return false;
    }

    /**
     * Check to see if a queen can be placed on
     * board[row][col]. Only need to check the left
     * side for attacking queens, given that
     * "col" queens are already placed in columns
     * 0 to col-1
     * @param board
     * @param row
     * @param col
     * @return
     */
    private static boolean isSafe(int[][] board, int row, int col) {
        int i, j;

        // check this row on left side
        for (i = 0; i < col; i++)
            if (board[row][i] == 1)
                return false;

        // check upper diagonal on left side
        for (i = row, j = col; i >=0 && j >=0; i--,j--)
            if (board[i][j] == 1)
                return false;

        // check lower diagonal on left side
        for (i = row, j = col; j>=0 && i <N; i++, j--)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    private void printSoln(int board[][]) {
        for (int row = 0; row < N; row ++) {
            for (int col = 0; col < N; col ++) {
                System.out.print(" " + board[row][col] + " ");
                System.out.println();
            }
        }
    }

    /**
     * Note that this only returns one of the solutions
     * There are multiple solutions to the problem
     * @return
     */
    private boolean solveNQ() {
        int board[][] = new int[N][N];

        if (solveNQUtil(board, 0) == false)
        {
            System.out.print("Soln does not exist");
            return false;
        }
        printSoln(board);
        return true;
    }

    private static void solveNQAllSoln() {
        int board[][] = new int[N][N];
        if (solveNQUtilAllSoln(board, 0) == false)
        {
            System.out.println("soln does not exist");
            return;
        }
        return;
    }
}
