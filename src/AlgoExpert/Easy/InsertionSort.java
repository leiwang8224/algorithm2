package AlgoExpert.Easy;

public class InsertionSort {
    public static void main(String[] args) {

    }

    /**
     * Divide the array into 2 sections, one sorted and one unsorted
     * sorted array starts with one element and unsorted array is the rest of the array
     * 1. iterate start from index 1 and proceed to the end of array
     * 2. iterate backwards as long as the prev index is larger (perform swap)
     */
    // Best: O(n) time | O(1) space
    // Average: O(n^2) time | O(1) space
    // Worst: O(n^2) time | O(1) space
    private static int[] insertionSort(int[] array) {
        if (array.length == 0) return new int[] {};
        // start at index 1 and iterate forward
        // for each forward index compare to elements before the forward index and iterate backwards
        for (int forwardIndex = 1; forwardIndex < array.length; forwardIndex++) {
            int backwardIndex = forwardIndex;
            // while the backward index moves backward as long as the element at prev index is larger
            // perform the swap between the smaller element at index ahead with bigger element at lower index
            while (backwardIndex > 0 &&
                    array[backwardIndex] < array[backwardIndex-1]) {
                swap(backwardIndex,backwardIndex -1, array);
                backwardIndex--;
            }
        }
        return array;
    }

    private static void swap(int j, int i, int[] array) {
        int temp = array[j];
        array[j] = array[i];
        array[i] = temp;
    }
}
