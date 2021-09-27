package AlgoExpert.Hard;
public class QuickSelect {
    private static int quickSelect(int[] array, int k) {
        int indexToReturn = k - 1;
        return quickSelect(array, 0, array.length - 1, indexToReturn);
    }

    // does not use recursion like quickSort()
    // Best: O(n) time | O(1) space
    // Average: O(n) time | O(1) space
    // Worst: O(n^2) time | O(1) space
    private static int quickSelect(int[] array, int startIdx, int endIdx, int indexToReturn) {
        while (true) {
            if (startIdx > endIdx) {
                throw new RuntimeException("Your algorithm should never arrive here");
            }
            int privotIdx = startIdx;
            int leftIdx = startIdx + 1;
            int rightIdx = endIdx;

            while(leftIdx <= rightIdx) {
                if (array[leftIdx] > array[privotIdx] &&
                        array[rightIdx] < array[privotIdx]) {
                    swap(leftIdx, rightIdx, array);
                }
                if (array[leftIdx] <= array[privotIdx]) {
                    leftIdx++;
                }
                if (array[rightIdx] >= array[privotIdx]) {
                    rightIdx--;
                }
            }
            // swap pivot with right index when left index > right index
            swap(privotIdx, rightIdx, array);

            // if we are at the k-1th index then the pivot to the left is sorted
            // and pivot to the right is sorted, we just return the value
            // rightIdx is the pivot point
            if (rightIdx == indexToReturn) {
                return array[rightIdx];
            } else if(rightIdx < indexToReturn) {
                // if the pivot point is left of k-1, then we know every thing
                // to the left the pivot is sorted.
                // just need to focus on searching on the right subarray of pivot
                startIdx = rightIdx + 1;
            } else {
                // else focus on the left subarray of the pivot to keep searching
                endIdx = rightIdx - 1;
            }
        }
    }

    private static void swap(int leftIdx, int rightIdx, int[] array) {
        int temp = array[leftIdx];
        array[leftIdx] = array[rightIdx];
        array[rightIdx] = temp;
    }

    public static int quickselectMySol(int[] array, int k) {
        if (array.length == 1) return array[0];
        // Write your code here.
        return quickSelectMySol(array, k, 0, array.length-1);
    }

    private static int quickSelectMySol(int[] array, int k, int startIdx, int endIdx) {
        if (startIdx > endIdx) return -1;

        int pivot = startIdx;
        int left = startIdx+1;
        int right = endIdx;

        while (left <= right) {
            if (array[left] > array[pivot] && array[right] < array[pivot]) {
                swap(left, right, array);
            }

            if (array[left] <= array[pivot]) {
                left++;
            }

            if (array[right] >= array[pivot]) {
                right--;
            }
        }

        swap(right, pivot, array);
        if (right == k-1) return array[right];
        boolean isLeftSmallerSize = right - 1 - startIdx < endIdx - right + 1 ? true : false;
        if (isLeftSmallerSize) {
            int sol = quickSelect(array, k, startIdx, right-1);
            if (sol != -1) return sol;
            return quickSelect(array, k, right+1, endIdx);
        } else {
            int sol2 = quickSelect(array, k, right+1, endIdx);
            if (sol2 != -1) return sol2;
            return quickSelect(array, k, startIdx, right-1);
        }
    }
}
