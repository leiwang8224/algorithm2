package AlgoExpert.Hard;

public class QuickSort {
    private static int[] quickSortRecursion(int[] array) {
        quickSortRecursion(array, 0, array.length -1);
        return array;
    }

    // Best: O(nlog(n)) time | O(log(n)) space
    // Avg: O(nlog(n)) time | O(log(n)) space
    // Worst: O(n^2) time | O(log(n)) space
    // NOTE: rightIdx will always be the new pivot point
    // setup start and endIdx and setup the pivot, left and right
    // setup while loop for left < right
    // when left and right crosses, swap right with pivot
    // use right as the new pivot and find shorter array to the left or right of rightIdx
    // perform recursion on the smaller subarray first before recurse on the larger subarray
    private static void quickSortRecursion(int[] array, int startIdx, int endIdx) {
        if (startIdx >= endIdx) return;
        // pivot is first index element
        int pivotIdx = startIdx;
        int leftIdx = startIdx + 1;
        int rightIdx = endIdx;

        // find where leftIdx value > pivot and rightIdx value < pivot to swap
        while (rightIdx >= leftIdx) {
            // left > pivot && right < pivot, swap
            if (array[leftIdx] > array[pivotIdx] &&
               array[rightIdx] < array[pivotIdx]) {
                swap(leftIdx, rightIdx, array);
            }
            // left <= pivot which means left is sorted, increment left
            if (array[leftIdx] <= array[pivotIdx]) {
                leftIdx ++;
            }
            // right >= pivot which means right is sorted, dec right
            if (array[rightIdx] >= array[pivotIdx]) {
                rightIdx--;
            }
        }
        // swap pivot and right index(new pivot placed)
        swap(pivotIdx, rightIdx, array);
        // find which subarray is smaller and perform quicksort on that subarray
        // this ensures log(n) calls on the call stack
        // note after the swap rightIdx is on the left side
        // so left subarray = rightIdx - 1 - startIdx
        // right subarray = endIdx - (rightIdx + 1)
        boolean leftSubarrayIsSmaller =
                rightIdx - 1 - startIdx < endIdx - (rightIdx + 1); // NOTE rightIdx is pivot so need to adjust by 1
        if (leftSubarrayIsSmaller) { //rightIdx is now the new pivot
            // now rightIdx is the pivot index so left
            // subarray is start to rightIdx-1, recurse on left subarray
            quickSortRecursion(array, startIdx, rightIdx - 1); //left
            // recurse on right subarray
            quickSortRecursion(array, rightIdx + 1, endIdx); //right
        } else {
            quickSortRecursion(array, rightIdx + 1, endIdx); //right
            quickSortRecursion(array, startIdx, rightIdx - 1); // left
        }
    }

    private static void swap(int i, int j, int[] array) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
