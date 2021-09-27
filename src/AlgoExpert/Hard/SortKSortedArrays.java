package AlgoExpert.Hard;

import java.util.*;

public class SortKSortedArrays {
    // O(nlog(k)) time | O(k) space - where n is the number
    // of elements in the array and k is how far away elements
    // are from their sorted position
    int[] sortKSortedArray(int[] array, int k) {
        List<Integer> heapValues = new ArrayList<>();
        for (int i = 0; i < Math.min(k + 1, array.length); i++) {
            heapValues.add(array[i]);
        }
        
        MinHeap minHEapWithKElements = new MinHeap(heapValues);

        int nextIdxToInsertElement = 0;
        for (int idx = k+1; idx < array.length; idx++) {
            int minElement = minHEapWithKElements.remove();
            array[nextIdxToInsertElement] = minElement;
            nextIdxToInsertElement += 1;

            int curElement = array[idx];
            minHEapWithKElements.insert(curElement);
        }

        while (!minHEapWithKElements.isEmpty()) {
            int minElement = minHEapWithKElements.remove();
            array[nextIdxToInsertElement] = minElement;
            nextIdxToInsertElement += 1;
        }

        return array;
    }
    
    class MinHeap {
        List<Integer> heap = new ArrayList<>();

        MinHeap(List<Integer> array) {
            heap = buildHeap(array);
        }


        // O(n) time | O(1) space
        private List<Integer> buildHeap(List<Integer> array) {
            int firstParentIdx = (array.size() - 2) / 2;
            for (int curIdx = firstParentIdx; curIdx >= 0; curIdx--) {
                siftDown(curIdx, array.size() - 1, array);
            }
            return array;
        }

        private void siftDown(int curIdx, int endIdx, List<Integer> heap) {
            int childOneIdx = curIdx * 2 + 1;
            while (childOneIdx <= endIdx) {
                int childTwoIdx = curIdx * 2 + 2 <= endIdx ? curIdx * 2 + 2 : -1;
                int idxToSwap;
                if (childTwoIdx != -1 && heap.get(childTwoIdx) < heap.get(childOneIdx)) {
                    idxToSwap = childTwoIdx;
                } else {
                    idxToSwap = childOneIdx;
                }

                if (heap.get(idxToSwap) < heap.get(curIdx)) {
                    swap(curIdx, idxToSwap, heap);
                    curIdx = idxToSwap;
                    childOneIdx = curIdx * 2 + 1;
                } else {
                    return;
                }
            }
        }

        void siftUp(int curIdx, List<Integer> heap) {
            int parentIdx = (curIdx - 1) / 2;
            while (curIdx > 0 && heap.get(curIdx) < heap.get(parentIdx)) {
                swap(curIdx, parentIdx, heap);
                curIdx = parentIdx;
                parentIdx = (curIdx - 1) / 2;
            }
        }

        int peek() {
            return heap.get(0);
        }

        int remove() {
            swap(0, heap.size() - 1, heap);

            int valueToRemove = heap.get(heap.size() - 1);
            heap.remove(heap.size() - 1);
            siftDown(0, heap.size() - 1, heap);
            return valueToRemove;
        }

        void insert(int value) {
            heap.add(value);
            siftUp(heap.size()-1, heap);
        }

        void swap(int i, int j, List<Integer> heap) {
            Integer temp = heap.get(j);
            heap.set(j, heap.get(i));
            heap.set(i, temp);
        }

        boolean isEmpty() {
            return heap.size() == 0;
        }
    }
}
