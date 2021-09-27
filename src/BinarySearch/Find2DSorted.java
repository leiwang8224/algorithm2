package BinarySearch;

/**
 * Created by leiwang on 3/24/18.
 */
//Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
//
//        Integers in each row are sorted from left to right.
//        The first integer of each row is greater than the last integer of the previous row.
//
//        Example 1:
//
//        Input:
//        matrix = [
//        [1,   3,  5,  7],
//        [10, 11, 16, 20],
//        [23, 30, 34, 50]
//        ]
//        target = 3
//        Output: true

//
        //1) Start with top right element
//        2) Loop: compare this element e with x
//        ….i) if they are equal then return its position
//        …ii) e < x then move it to down (if out of bound of matrix then break return false) ..
//        iii) e > x then move it to left (if out of bound of matrix then break return false)
//        3) repeat the i), ii) and iii) till you find element or returned false
    // time complexity O(m+n)
public class Find2DSorted {
    public static void main (String args[]) {
        int[][] array = new int[][] { {1,2,3,4},
                                      {5,6,7,8},
                                      {9,10,11,12},
                                      {13,14,15,16}};
        search(array,4,5);
    }

    private static void search(int[][] array, int n, int key) {
        int row = 0, col = n -1; // set index for top right element
        while (row < n && col >= 0) {
            if (array[row][col] == key) {
                System.out.println(key + " found at row " + row + " col " + col);
                return;
            }
            if (array[row][col] > key) {
                col --;
            } else {
                row ++;
            }
        }

        System.out.println(key + " not found");
        return;
    }

//    We start search the matrix from top right corner ( row[0], col[end] ),
//    initialize the current position to top right corner,
//    if the target is greater than the value in current position,
//    then the target can not be in entire row of current position
//    because the row is sorted, if the target is less than the value
//    in current position, then the target can not in the entire
//    column because the column is sorted too. We can rule out one
//    row or one column each time, so the time complexity is O(m+n).
    public static boolean searchMatrix(int[][] matrix, int target) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int endCol = matrix[0].length-1;
        int row = 0;
        // trick is searching start from the right corner of the matrix
        while(endCol >= 0 && row <= matrix.length-1) {
            if(target == matrix[row][endCol]) {
                return true;
            } else if(target < matrix[row][endCol]) {
                endCol--;
            } else if(target > matrix[row][endCol]) {
                row++;
            }
        }
        return false;
    }
}
