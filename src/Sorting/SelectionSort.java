package Sorting;

/**
 * Created by leiwang on 3/24/18.
 */
//arr[] = 64 25 12 22 11
//
//// Find the minimum element in arr[0...4]
//// and place it at beginning
//        11 25 12 22 64
//
//// Find the minimum element in arr[1...4]
//// and place it at beginning of arr[1...4]
//        11 12 25 22 64
//
//// Find the minimum element in arr[2...4]
//// and place it at beginning of arr[2...4]
//        11 12 22 25 64
//
//// Find the minimum element in arr[3...4]
//// and place it at beginning of arr[3...4]
//        11 12 22 25 64
    // Time: O(n2)
    // Space: O(1)
public class SelectionSort {
    public static void main(String args[]) {
        int[] intArray = new int[]{2, 4, 5, 6, 3, 4, 2, 3432, 3543, 43, 54, 432, 123, 54, 65, 35, 234, 234, 46, 234, 45, 435, 56, 576, 67, 765, 87, 657, 6, 7, 5};
        sort(intArray);
        System.out.println("result " + java.util.Arrays.toString(intArray));
    }

    private static void sort(int[] intArray) {
        int n = intArray.length;
        //one by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i ++) {
            // find min element in the unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j ++) {
                if (intArray[j] < intArray[min_idx]) {
                    min_idx = j;
                }
            }
            // swap found min element with first element
            int temp = intArray[min_idx];
            intArray[min_idx] = intArray[i];
            intArray[i] = temp;
        }
    }

    private static void sort2(int arr[]) {
        int n = arr.length;

        for (int i = 0; i < n-1; i ++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j ++) {
                if (arr[j] < arr[minIdx])
                    minIdx = j;
            }

            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }
}
