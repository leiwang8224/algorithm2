package Arrays;

import java.util.ArrayList;

public class FindSpiral {
    public static void main(String[] args) {

        // only goes into the totalRow = 1 if statement
        int[][] array = new int[][] {{1,2,3,4,5}, {6,7,8,9,10}, {11,12,13,14,15}, {16,17,18,19,20}, {21,22,23,24,25}};

        // only goes into the totalCol = 1 if statement
        int[][] array3 = new int[][] {{1,2,3}, {6,7,8}, {11,12,13}, {16,17,18}, {21,22,23}};

        printArrayList(findSpiral(array));

    }

    private static void printArrayList(ArrayList<Integer> list) {
        for (Integer num : list) {
            System.out.println(num);
        }
    }

    private static ArrayList<Integer> findSpiral(int[][] arr) {
        ArrayList<Integer> spiralOrder = new ArrayList<>();
        if (arr == null || arr.length == 0) return spiralOrder;

        int totalRows = arr.length, totalCols = arr[0].length;
        int row = 0, col = 0;

        while (totalRows > 0 && totalCols > 0) {
            System.out.println("totalRows = " + totalRows + " totalCols = " + totalCols);
            /** this section is for indexing through the inner ring **/
            /** note that the final index is totalRows instead of totalRows-1 **/
            if (totalRows == 1) { // at second row
                System.out.println("in totalRow = 1");
                for (int index = 0; index < totalCols; index++) {
                    spiralOrder.add(arr[row][col++]);
                }
                break; // exit while loop
            } else if (totalCols == 1) { // at second column
                System.out.println("in totalCol = 1");
                for (int index = 0; index < totalRows; index++) {
                    spiralOrder.add(arr[row++][col]);
                }
                break; // exit while loop
            }

            /** this section is for indexing through the outer rings**/
            /** note that the final index is totalRows-1 instead of totalRows **/
            // index through the columns and increment cols
            for (int index = 0; index < totalCols-1; index++) {
                System.out.println("index through the columns and increment cols");
                spiralOrder.add(arr[row][col++]);
            }

            // index through the rows and increment rows
            for (int index = 0; index < totalRows-1; index++) {
                System.out.println("index through the rows and increment rows");
                spiralOrder.add(arr[row++][col]);
            }

            //index through the columns again and decrement cols
            for (int index = 0; index < totalCols-1; index++) {
                System.out.println("index through the columns again and decrement cols");
                spiralOrder.add(arr[row][col--]);
            }

            //index through the rows again and decrement rows
            for (int index = 0; index < totalRows-1; index++) {
                System.out.println("index through the rows again and decrement rows");
                spiralOrder.add(arr[row--][col]);
            }

            row++;
            col++;
            totalRows = totalRows - 2;
            totalCols = totalCols - 2;
        }

        return spiralOrder;
    }
}
