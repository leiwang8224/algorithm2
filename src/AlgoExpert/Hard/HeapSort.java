package AlgoExpert.Hard;

public class HeapSort {
    // O(nlog(n)) time | O(1) space
    // siftdown is called n times and the siftdown is log(n) so it's nlog(n)
    // siftdown goes to the right
    // siftup goes to the left
    public static int[] heapSort(int[] array) {
        buildMaxHeap(array);
        // iterate backwards, swap the first element with last element
        // and perform siftDown to push the first element down the array
        // to maintain heap properties
        // iterate till there is one element left (n to 1 inclusive)
        for (int endIdx = array.length - 1; endIdx >0; endIdx--) {
            swap(0, endIdx, array); // by swapping you get the largest element to the last index
            siftDown(0, endIdx - 1, array); // NOTE :sift down from 0 to the index before last
                                                        // index (skip last element since it's already sorted)
        }
        return array;
    }

    private static void buildMaxHeap(int[] array) {
        int firstParentIdx = (array.length - 2) / 2;
        for (int idx = firstParentIdx; idx >= 0; idx --) {
            siftDown(idx, array.length - 1, array);
        }
    }

    private static void siftDown(int curIdx,
                                 int endIdx,
                                 int[] heap) {
        int childOneIdx = curIdx * 2 + 1;
        while (childOneIdx <= endIdx) {
            int childTwoIdx = curIdx * 2 + 2 <= endIdx ? //NOTE the important difference between heap and heapsort is here
                                                         //heap goes all the way to the end of array whereas heapsort just
                                                         //goes to the end of the heap, beyond heap is sorted array which
                                                         // we don't want to include in the heap sort
                                        curIdx * 2 + 2 : -1;
            int idxLargest;
            if (childTwoIdx != -1 && heap[childTwoIdx] > heap[childOneIdx]) {
                idxLargest = childTwoIdx;
            } else {
                idxLargest = childOneIdx;
            }

            if (heap[idxLargest] > heap[curIdx]) {
                swap(curIdx, idxLargest, heap);
                curIdx = idxLargest;
                childOneIdx = curIdx * 2 + 1;
            } else {
                return;
            }
        }
    }

    private static void swap(int i, int j, int[] array) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
