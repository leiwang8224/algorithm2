package Sorting;

import java.util.Arrays;

/**
 * Created by leiwang on 3/21/18.
 */
//MergeSort(arr[], l,  r)
//        If r > l
//        1. Find the middle point to divide the array into two halves:
//        middle m = (l+r)/2
//        2. Call mergeSort for first half:
//        Call mergeSort(arr, l, m)
//        3. Call mergeSort for second half:
//        Call mergeSort(arr, m+1, r)
//        4. Merge the two halves sorted in step 2 and 3:
//        Call merge(arr, l, m, r)
public class MergeSort {
    public static void main(String args[]) {
        int[] intArray = new int[] {2,4,5,6,3,4,2,3432,3543,43,54,432,123,54,65,35,234,234,46,234,45,435,56,576,67,765,87,657,6,7,5};
        sort(intArray, 0, intArray.length - 1);
        System.out.println(Arrays.toString(intArray));
    }

    private static void sort(int[] intArray, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            int m = (startIndex + endIndex) / 2;

            sort(intArray, startIndex, m);
            sort(intArray, m+1, endIndex);

            merge(intArray, startIndex, m, endIndex);
        }
    }
    private static void merge(int[] intArray, int startIndex, int middleIndex, int endIndex) {
        int n1 = middleIndex - startIndex + 1;
        int n2 = endIndex - middleIndex;

        int leftArray[] = new int[n1];
        int rightArray[] = new int[n2];

        // copy data to temp array
        for (int i = 0; i < n1; i ++) {
            leftArray[i] = intArray[startIndex + i];
        }

        for (int j = 0; j < n2; j ++) {
            rightArray[j] = intArray[middleIndex + 1 + j];
        }

        // merge the temp arrays

        int i = 0; int j = 0;

        // initial index of the merged array
        int k = startIndex;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j])
            {
                intArray[k] = leftArray[i];
                i ++;
            } else {
                intArray[k] = rightArray[j];
                j ++;
            }
            k++;
        }

        // copy remain elements if any
        while (i < n1) {
            intArray[k] = leftArray[i];
            i ++;
            k ++;
        }

        while (j < n2) {
            intArray[k] = rightArray[j];
            j ++;
            k ++;
        }

    }
}
