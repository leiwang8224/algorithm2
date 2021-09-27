package AlgoExpert.Easy;

public class SelectionSort {
    public static void main(String[] args) {

    }

    // Best: O(n^2) time | O(1) space
    // Avg: O(n^2) time | O(1) space
    // Worst: O(n^2) time | O(1) space
    private static int[] selectionSort(int[] array) {
        if (array.length == 0) return new int[]{};

        int startIndex = 0;
        // iterate to the index before the last index (last index is the last number)
        while (startIndex < array.length-1) {
            int smallestNumberIndex = startIndex;
            // iterate through unsorted list (starting from startIndex+1)
            for (int unsortedIndex = startIndex+1;
                 unsortedIndex < array.length;
                 unsortedIndex++) {
                if (array[smallestNumberIndex] > array[unsortedIndex]) {
                    smallestNumberIndex = unsortedIndex;
                }
            }
            swap(startIndex, smallestNumberIndex, array);
            startIndex++;
        }
        return array;
    }

    private static void swap(int i, int j, int[] array) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
