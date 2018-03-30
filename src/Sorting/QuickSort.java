package Sorting;

import java.util.Arrays;

/**
 * Created by leiwang on 3/21/18.
 */
//quickSort(arr[], low, high)
//        {
//        if (low < high)
//        {
//        /* pi is partitioning index, arr[pi] is now
//           at right place */
//        pi = partition(arr, low, high);
//
//        quickSort(arr, low, pi - 1);  // Before pi
//        quickSort(arr, pi + 1, high); // After pi
//        }
//        }

public class QuickSort {
    public static void main(String args[]) {
        int[] intArray = new int[] {2,4,5,6,3,4,2,3432,3543,43,54,432,123,54,65,35,234,234,46,234,45,435,56,576,67,765,87,657,6,7,5};

        sort(intArray, 0, intArray.length - 1);
        System.out.println(Arrays.toString(intArray));
    }

    private static int partition(int[] intArray, int startIndex, int endIndex) {
        int pivot = intArray[endIndex];
        int i = startIndex - 1;

        for (int j = startIndex; j < endIndex; j ++) {
            // if current element is smaller than or equal to pivot
            if (intArray[j] <= pivot) {
                i ++;
                // swap intArray[i] with intArray[j]
                int temp = intArray[i];
                intArray[i] = intArray[j];
                intArray[j] = temp;
            }
        }
        //swap intArray[i+1] and intArray[endIndex] (or pivot)
        int temp = intArray[i+1];
        intArray[i+1] = intArray[endIndex];
        intArray[endIndex] = temp;

        return i + 1;
    }

    private static void sort(int[] intArray, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            // pi is partitioning index
            int pi = partition(intArray, startIndex, endIndex);

            // recursively sort elements before
            // partition and after partition
            sort(intArray, startIndex, pi - 1);
            sort(intArray, pi + 1, endIndex);
        }
    }


}
