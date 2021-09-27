package AlgoExpert.Medium;

public class SelectionSort {
    public static void main(String[] args) {

    }

    // Best: O(n^2) time | O(1) space
    // Average: O(n^2) time | O(1) space
    // Worst: O(n^2) time | O(1) space

    /**
     * Iterate through the array by dividing up into unsorted and sorted array
     * starting sorted[0] and unsorted[1..n] until sorted[n-2] and unsorted[n-1]
     */
    private static int[] selectionSort(int[] array) {
        if (array.length == 0) return new int[] {};

        int sortedIndex = 0;
        while (sortedIndex < array.length - 1) {
            // just need to keep track of the smallest index
            // don't need to keep track smallest number
            int smallestIndexUnsorted = sortedIndex;
            for (int unsortedIndex = sortedIndex + 1;
                 unsortedIndex < array.length;
                 unsortedIndex++) {
                if (array[smallestIndexUnsorted] > array[unsortedIndex]) {
                    smallestIndexUnsorted = unsortedIndex;
                }
            }
            swap(sortedIndex, smallestIndexUnsorted, array);
            sortedIndex++;
        }
        return array;
    }

    private static void swap(int i, int j, int[] array) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
