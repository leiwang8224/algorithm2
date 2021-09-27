package AlgoExpert.Medium;

import java.util.ArrayList;
import java.util.List;

public class MinHeap {
    public static void main(String[] args) {

    }

    /**
     * currentNode → i
     *
     * childOne → 2i + 1
     *
     * childTwo → 2i + 2
     *
     * parentNode → floor((i-1)/2)
     *
     * parentNode < curIdx
     * childOne and childTwo > curIdx
     * Heap is NOT a sorted array!! Heap Property: parent is always greater/less than children
     */
    static class MinHeapImpl {
        java.util.List<Integer> heap = new ArrayList<Integer>();

        public MinHeapImpl(java.util.List<Integer> array) {
            heap = buildHeap(array);
        }

        // o(n) time | O(1) space
        // - get first parent index from the bottom
        // - iterate from first parent idx backwards to 0 and sift down each element
        private List<Integer> buildHeap(List<Integer> array) {
            // index of last non-leaf node
            // (lastIdx - 1) / 2 = (array.size() - 2) / 2
            int parentIdxOfLastNode = (array.size() - 2) / 2;

            // perform reverse order traversal from last non-leaf
            // node and heapify each node
            for (int curIdx = parentIdxOfLastNode; curIdx >= 0; curIdx --) {
                siftDown(curIdx, array.size()-1, array);
            }
            return array;
        }

        // O(log(n)) time | O(1) space
        // - need to determine which of the two children are smaller
        // - perform swap with the smaller of two children
        // - update curIdx and child one idx for next iteration
        private void siftDown(int parentIdx, int endIdx, List<Integer> array) {
            // get child one index of curIdx
            int childOneIdx = parentIdx * 2 + 1;
            // while childOneIdx is valid
            while (childOneIdx <= endIdx) {
                // find child two index if it exists, if child two index is outside of the bounds
                // then it doesn't exist ( no second child)
                int childTwoIdx = parentIdx * 2 + 2 <= endIdx ? parentIdx * 2 + 2 : -1;
                // find index to swap
                int idxSmallestOfTwoChildren;

                // get index of the smallers of the two children
                if (childTwoIdx != -1 && array.get(childTwoIdx) <
                        array.get(childOneIdx)) {
                    idxSmallestOfTwoChildren = childTwoIdx;
                } else {
                    idxSmallestOfTwoChildren = childOneIdx;
                }

                // if smallest of two children is smaller than curIdx element
                // perform swap (idxSmallestOfTwoChildren > curIdx)
                if (array.get(idxSmallestOfTwoChildren) < array.get(parentIdx)) {
                    swap(array, parentIdx, idxSmallestOfTwoChildren);
                    // set curIdx to the smallest idx for next while loop iteration
                    parentIdx = idxSmallestOfTwoChildren;
                    // calculate childOne idx for next iteration
                    childOneIdx = parentIdx * 2 + 1;
                } else {
                    return;
                }
            }
        }

        // O(log(n)) time | O(1) space
        private void siftUp(int childIdx, List<Integer> heap) {
            // get index of parent, which is less than curIdx
            int parentIdx = (childIdx - 1) / 2;
            // iterate upwards (backwards) and perform swap
            while (childIdx > 0 && heap.get(childIdx) < heap.get(parentIdx)) {
                // swap and move curIdx up to parent idx
                swap(heap, childIdx, parentIdx);
                childIdx = parentIdx;
                // calculate the next parent idx
                parentIdx = (childIdx - 1) / 2;
            }
        }

        private int peek() {
            return heap.get(0);
        }

        // removes the head node (first element of list)
        // to do this we need to remove the top node but need to
        // put it last before performing the remove. There is no way
        // to remove from top node and heapify
        // always swap the first element and last element first
        // then remove the last element
        // perform sift down operation
        private int remove() {
            // swap beginning and end elements, this is the only way to heapify the heap
            // removing the top node and we will not be able to heapify
            swap(heap, 0, heap.size() - 1);
            // set value to remove to the end of the list
            int valueToRemove = heap.get(heap.size() - 1);
            // remove the last item from list
            heap.remove(heap.size() - 1);
            // call siftdown to down propagate from 0 indice
            siftDown(0, heap.size()-1, heap);
            return valueToRemove;
        }

        // add to the list and sift up from the last element
        private void insert(int value) {
            heap.add(value);
            // sift up from the last element since it was
            // added to the tail of list
            siftUp(heap.size() - 1, heap);
        }

        private void swap(List<Integer> array, int i, int j) {
            int temp = heap.get(j);
            heap.set(j, heap.get(i));
            heap.set(i, temp);
        }
    }
}
