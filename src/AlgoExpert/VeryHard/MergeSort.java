package AlgoExpert.VeryHard;

public class MergeSort {
    // Best: O(nlog(n)) time | O(nlog(n)) space
    // Average: O(nlog(n)) time | O(nlog(n)) space
    // Worst: O(nlog(n)) time | O(nlog(n)) space
    public static int[] mergeSortRecurseDivide(int[] array) {
        // return when we reach a size of 1 or smaller
        if (array.length <= 1) return array;

        // divide array into 2 and get copies of left and right half
        int middleIdx = array.length / 2;
        int[] leftHalf = java.util.Arrays.copyOfRange(array,0, middleIdx); //copy to middleIdx-1
        int[] rightHalf = java.util.Arrays.copyOfRange(array, middleIdx, array.length); //copy to array.length-1
        // call on left and right copies, populate new array and return with merged array
        return mergeArraysInToOne(mergeSortRecurseDivide(leftHalf), mergeSortRecurseDivide(rightHalf));
    }

    private static int[] mergeArraysInToOne(int[] leftHalf, int[] rightHalf) {
        // new sorted array should be size of both halves
        int[] sortedArray = new int[leftHalf.length + rightHalf.length];

        int sortedArrayIdx = 0;
        int leftHalfIdx = 0;
        int rightHalfIdx = 0;

        // iterate through left half and right half at the same time and merge into one array
        while (leftHalfIdx < leftHalf.length && rightHalfIdx < rightHalf.length) {
            // store left half if it's smaller else right half
            if (leftHalf[leftHalfIdx] <= rightHalf[rightHalfIdx]) {
                sortedArray[sortedArrayIdx++] = leftHalf[leftHalfIdx++];
            } else {
                sortedArray[sortedArrayIdx++] = rightHalf[rightHalfIdx++];
            }
        }

        // if there are still numbers in leftHalf
        while (leftHalfIdx < leftHalf.length) {
            sortedArray[sortedArrayIdx++] = leftHalf[leftHalfIdx++];
        }

        // if there are still numbers in rightHalf
        while (rightHalfIdx < rightHalf.length) {
            sortedArray[sortedArrayIdx++] = rightHalf[rightHalfIdx++];
        }

        return sortedArray;
    }

    // Best: O(nlog(n)) time | O(n) space
    // Average: O(nlog(n)) time | O(n) space
    // Worst: O(nlog(n)) time | O(n) space
    /**
     * Use pointers instead of copies of arrays
     * Similar to Tower Of Hanoi Algorithm
     * TODO: Not sure I understand this
     */
    static int[] mergeSortPtr(int[] array) {
        if (array.length <= 1) return array;

        int[] auxArray = array.clone();
        mergeSortPtr(array, 0, array.length - 1, auxArray);
        return array;
    }

    private static void mergeSortPtr(int[] mainArray, int startIdx, int endIdx, int[] auxArray) {
        // base case: when we reach one element in the array
        if (startIdx == endIdx) return;

        int middleIdx = (startIdx + endIdx) / 2;
        // exchange auxArray with mainArray
        mergeSortPtr(auxArray, startIdx, middleIdx, mainArray);
        mergeSortPtr(auxArray, middleIdx + 1, endIdx, mainArray);
        doMerge(mainArray, startIdx, middleIdx, endIdx, auxArray);
    }

    private static void doMerge(int[] mainArray, int startIdx, int middleIdx, int endIdx, int[] auxArray) {
        int mainArrayIdx = startIdx;
        int leftHalfStartIdx = startIdx;
        int rightHalfStartIdx = middleIdx + 1;

        while (leftHalfStartIdx <= middleIdx && rightHalfStartIdx <= endIdx) {
            if (auxArray[leftHalfStartIdx] <= auxArray[rightHalfStartIdx]) {
                mainArray[mainArrayIdx++] = auxArray[leftHalfStartIdx++];
            } else {
                mainArray[mainArrayIdx++] = auxArray[rightHalfStartIdx++];
            }
        }

        while (leftHalfStartIdx <= middleIdx) mainArray[mainArrayIdx++] = auxArray[leftHalfStartIdx++];

        while (rightHalfStartIdx <= endIdx) mainArray[mainArrayIdx++] = auxArray[rightHalfStartIdx++];
    }
}
