package BinarySearch;

/**
 * Created by leiwang on 3/24/18.
 */
//
//1) Start with top right element
//        2) Loop: compare this element e with x
//        ….i) if they are equal then return its position
//        …ii) e < x then move it to down (if out of bound of matrix then break return false) ..
//        iii) e > x then move it to left (if out of bound of matrix then break return false)
//        3) repeat the i), ii) and iii) till you find element or returned false
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
}
