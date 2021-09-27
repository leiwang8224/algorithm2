package AlgoExpert.Easy;

public class BubbleSort {
    public static void main(String[] args) {

    }

    // Best: O(n) time | O(1) space
    // Average: O(n^2) time | O(1) space
    // Worst: O(n^2) time | O(1) space
    private static int[] bubbleSort(int[] array) {
        if(array.length == 0) return new int[] {};
        boolean isSorted = false;
        int numTimesIterateArray = 0;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < array.length-1-numTimesIterateArray; i++) {
                if (array[i] > array[i+1]) {
                    swap(i, i+1, array);
                    isSorted = false;
                }
            }
            numTimesIterateArray++;
        }
        return array;
    }

    private static void swap(int i, int j, int[] array) {
        int temp = array[j];
        array[j] = array[i];
        array[i] = temp;
    }
}
