package Arrays;

import java.util.ArrayList;

public class FindSpiral {
    public static void main(String[] args) {

        int[][] array = new int[][] {{1,2,3}, {4,5,6}, {7,8,9}};
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
            if (totalRows == 1) {
                for (int index = 0; index < totalCols; index++) {
                    spiralOrder.add(arr[row][col++]);
                }
                break; // exit while loop
            } else if (col == 1) {
                for (int index = 0; index < totalCols; index++) {
                    spiralOrder.add(arr[row++][col]);
                }
                break; // exit while loop
            }

            for (int index = 0; index < totalCols-1; index++) {
                spiralOrder.add(arr[row][col++]);
            }

            for (int index = 0; index < totalRows-1; index++) {
                spiralOrder.add(arr[row++][col]);
            }

            for (int index = 0; index < totalCols-1; index++) {
                spiralOrder.add(arr[row][col--]);
            }

            for (int index = 0; index < totalRows-1; index++) {
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
