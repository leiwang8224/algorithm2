package AlgoExpert.Medium;
import java.util.*;
public class MinimumPassesMatrix {
    //O(w*h) time | O(w*h) space - where w is the
    // width of the matrix and h is the height
    int minPassesOfMatrix(int[][] matrix) {
        int passes = convertNegatives(matrix);
        return (!containsNegative(matrix)) ? passes -1 : -1;
    }

    private boolean containsNegative(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                if (value < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private int convertNegatives(int[][] matrix) {
        ArrayList<int[]> nextPassQueue = getAllPositivePositions(matrix);
        int passes = 0;

        while (nextPassQueue.size() > 0) {
            ArrayList<int[]> curPassQueue = nextPassQueue;
            nextPassQueue = new ArrayList<>();

            while (curPassQueue.size() > 0) {
                // In java, removing elements from the start of a list is
                // O(n) time operation. To make this an O(1) time operation,
                // we could use a more legitimate queue structure.
                // For our time complexity analysis, we will assume this runs
                // in O(1) time. Also, for this particular solution we could
                // actually just turn this queue into a stack and replace .remove(0)
                // with the constant time pop() operation
                int[] vals = curPassQueue.remove(0);
                int curRow = vals[0];
                int curCol = vals[1];

                ArrayList<int[]> adjacentPositions = getAdjacentPositions(curRow,
                                                                        curCol,
                                                                        matrix);

                for (int[] position : adjacentPositions) {
                    int row = position[0];
                    int col = position[1];

                    int value = matrix[row][col];
                    if (value < 0) {
                        matrix[row][col] *= -1;
                        nextPassQueue.add(new int[] {row, col});
                    }
                }
            }

            passes++;
        }
        return passes;
    }

    private ArrayList<int[]> getAllPositivePositions(int[][] matrix) {
        ArrayList<int[]> positivePositions = new ArrayList<>();

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                int value = matrix[row][col];
                if (value > 0) {
                    positivePositions.add(new int[] {row, col});
                }
            }
        }
        return positivePositions;
    }

    ArrayList<int[]> getAdjacentPositions(int row, int col, int[][] matrix) {
        ArrayList<int[]> adjacentPositions = new ArrayList<>();

        if (row > 0) {
            adjacentPositions.add(new int[] {row-1, col});
        }

        if (row < matrix.length - 1) {
            adjacentPositions.add(new int[] {row + 1, col});
        }

        if (col > 0) {
            adjacentPositions.add(new int[] {row, col - 1});
        }

        if (col < (matrix[0].length - 1)) {
            adjacentPositions.add(new int[] {row, col + 1});
        }
        return  adjacentPositions;
    }

    //O(w*h) time | O(w * h) space where w is the width of the
    // matrix and h is the height
    int minPassesOfMatrix2(int[][] matrix) {
        int passes = convertNegatives2(matrix);
        return (!containsNegative2(matrix)) ? passes - 1 : -1;
    }

    private boolean containsNegative2(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                if (value < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private int convertNegatives2(int[][] matrix) {
        ArrayList<int[]> queue = getAllPositivePositions2(matrix);
        int passes = 0;

        while (queue.size() > 0) {
            int curSize = queue.size();

            while (curSize > 0) {
                // In Java, removing elements from the start of a list is an O(n) time operation
                // to make this an O(1) time operation, we could use a more legitimate queue structure
                // for our time complexity analysis, we will assume this runs in O(1) time
                int[] vals = queue.remove(0);
                int curRow = vals[0];
                int curCol = vals[1];

                ArrayList<int[]> adjacentPos = getAdjacentPositions2(curRow, curCol, matrix);
                for (int[] position : adjacentPos) {
                    int row = position[0];
                    int col = position[1];

                    int value = matrix[row][col];
                    if(value < 0) {
                        matrix[row][col] *= -1;
                        queue.add(new int[] {row, col});
                    }
                }
                curSize --;
            }
            passes++;
        }
        return passes;
    }

    private ArrayList<int[]> getAdjacentPositions2(int curRow, int curCol, int[][] matrix) {
        ArrayList<int[]> adjacentPos = new ArrayList<>();
        if (curRow > 0) {
            adjacentPos.add(new int[]{curRow - 1, curCol});
        }
        if (curRow < matrix.length -1) {
            adjacentPos.add(new int[] {curRow + 1, curCol});
        }

        if (curCol > 0) {
            adjacentPos.add(new int[] {curRow, curCol - 1});
        }
        if (curCol < (matrix[0].length - 1)) {
            adjacentPos.add(new int[]{curRow, curCol + 1});
        }
        return adjacentPos;
    }

    private ArrayList<int[]> getAllPositivePositions2(int[][] matrix) {
        ArrayList<int[]> positivePos = new ArrayList<>();

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                int value = matrix[row][col];
                if (value > 0) {
                    positivePos.add(new int[]{row, col});
                }
            }
        }
        return positivePos;
    }
}
