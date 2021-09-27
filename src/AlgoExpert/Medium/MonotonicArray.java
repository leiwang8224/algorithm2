package AlgoExpert.Medium;

import java.awt.image.DataBufferInt;

public class MonotonicArray {
    public static void main(String[] args) {

    }


    // O(n) time | O(1) space - where n is length of the array
    /**
     * 1. find diff between first two elements and use it for direction (+/-)
     * 2. iterate from index 2 to end of array
     *    - check if direction is 0, no direction specified, then use previous subtraction as direction
     *    - check if direction changes, if it does, return false
     * 3. return true after iterating through all elements
     */
    private static boolean isMonotonic(int[] array) {
        if (array.length <= 2) return true;

        int direction = array[1] - array[0];

        for (int index = 2; index < array.length; index++) {
            // don't know direction yet since first two are same elements
            if (direction == 0) {
                direction = array[index] - array[index-1];
                continue;
            }

            if (breaksDirection(direction, array[index-1], array[index]))
                return false;
        }
        return true;
    }

    private static boolean breaksDirection(int direction,
                                           int prev,
                                           int current) {
        int diff = current - prev;
        if (direction > 0) return diff < 0;
        return diff > 0;
    }

    // O(n) time | O(1) space - where n is the length of array
    /**
     * More simplified way of looking at the problem
     * Ask the question is the array either nonDec or nonInc?
     * 1. start initialize nonDec and nonInc as true
     * 2. iterate from index 1 to n
     *    - flip nonDec or nonInc (or both) using separate if checks
     * 3. return nonDec || nonInc
     */
    private static boolean isMonotonic2(int[] array) {
        boolean isNonDec = true;
        boolean isNonInc = true;

        for (int index = 1; index < array.length; index++) {
            if (array[index] < array[index-1])
                isNonDec = false;
            if (array[index] > array[index-1])
                isNonInc = false;
        }
        return isNonDec || isNonInc;
    }
}
