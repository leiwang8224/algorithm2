package AlgoExpert.Medium;

import static Math.NextPermutation.swap;

public class ThreeNumberSort {
    public static void main(String[] args) {

    }

    // O(n) time | O(1) space
    /**
     * Bucket sort variation
     */
    private int[] threeNumberSort(int[] array, int[] order) {
        int[] valueCounts = new int[] {0, 0, 0};

        // count the number of elements for each order element
        for (int element : array) {
            int orderIdx = getIndexOrder(order, element);
            valueCounts[orderIdx] += 1;
        }

        // for each order number get the order and count
        // essentially swap the elements in the array such that it reflects the order
        for (int orderIndex = 0; orderIndex < 3; orderIndex++) {
            int orderVal = order[orderIndex];
            int count = valueCounts[orderIndex];

            // get the indice where to start placing the orderVal
            // this is calculated given the counts for the number of elements with x before
            int numElementsBefore = getSum(valueCounts, orderIndex);
            // for each number of counts
            for (int n = 0; n < count; count++) {
                int curIdx = numElementsBefore + n;
                array[curIdx] = orderVal;
            }
        }
        return array;
    }

    private static int getIndexOrder(int[] order, int element) {
        for (int i = 0; i < order.length; i++) {
            if (order[i] == element) {
                return i;
            }
        }
        return -1;
    }

    private static int getSum(int[] valueCounts, int endIndice) {
        int sum = 0;
        for (int i = 0; i < endIndice; i++) sum+= valueCounts[i];
        return sum;
    }

    // O(n) time | O(1) space
    /**
     * just need the first and third value and swap if necessary
     * middle value does not need to be searched. This is heavily
     * leveraged on the fact that there are three numbers for the order array.
     * swap for the first index by iterating forward
     * swap for the third index by iterating backwards
     */
    private int[] threeNumberSort2(int[] array, int[] order) {
        int firstValue = order[0];
        int thirdValue = order[2];

        // firstIdx increments whenever we find the value for firstIdx in the array
        int firstIdx = 0;
        // move the appropriate first section numbers to first section
        for (int idx = 0; idx < array.length; idx++) {
            if (array[idx] == firstValue) {
                swap(array, firstIdx, idx);
                firstIdx+=1;
            }
        }

        // thirdIdx decrements whenever we find the value for thirdIdx in the array
        int thirdIdx = array.length - 1;
        // move the appropriate third section numbers to third section
        for (int idx = array.length - 1; idx >= 0; idx--) {
            if (array[idx] == thirdValue) {
                swap(array, idx, thirdIdx);
                thirdIdx -= 1;
            }
        }
        return array;
    }

    // O(n) time | O(1) space

    /**
     * divide the array into 3 subarrays, using three pointers, first, second and third
     * move first and second from the beginning and third from the end, iterate till
     * they meet in the middle, swap if necessary
     */
    private int[] threeNumberSort3(int[] array, int[] order) {
        int firstValue = order[0];
        int secondValue = order[1];

        // keep track of the indices where the values are stored
        int firstIdx = 0;
        int secondIdx = 0;
        int thirdIdx = array.length - 1;

        while (secondIdx <= thirdIdx) {
            int valueFromSecondIdx = array[secondIdx];

            // value from second idx should be in the first subarray
            // move forward first and second idx
            if (valueFromSecondIdx == firstValue) {
                swap(array, firstIdx, secondIdx);
                firstIdx ++;
                secondIdx ++;
                // value from second idx is in the second subarray, just inc second idx
            } else if (valueFromSecondIdx == secondValue) {
                secondIdx ++;
                // value from the second idx should be in the third subarray
                // move backward third idx
            } else {
                swap(array, secondIdx, thirdIdx);
                thirdIdx --;
            }
        }
        return array;
    }
}
