package AlgoExpert.VeryHard;
import java.util.*;

public class MergeSortedArray {
    // O(nk) time | O(n + k) space where n is the total
    // number of  array elements and k is the number of arrays
    public List<Integer> mergeSortedArrays(List<List<Integer>> arrays) {
        List<Integer> sortedList = new ArrayList<>();
        // returns an immutable list which contains n copies of given object
        // stores the position of k arrays when traversing, starting at index 0
        List<Integer> elementIdxs = new ArrayList<Integer>(Collections.nCopies(arrays.size(), 0));
        // O(n) for the while loop
        while (true) {
            List<Item> smallestItemsFromAllArrays = new ArrayList<>();
            // O(k) for the loop
            for (int whichArrayIdx = 0; whichArrayIdx < arrays.size(); whichArrayIdx++) {
                List<Integer> eachArray = arrays.get(whichArrayIdx);
                int elementIdx = elementIdxs.get(whichArrayIdx);
                // if at any point we are out of bounds of the array, just skip
                if (elementIdx == eachArray.size()) continue;
                smallestItemsFromAllArrays.add(new Item(whichArrayIdx, eachArray.get(elementIdx)));
            }
            if (smallestItemsFromAllArrays.size() == 0) break; // reached the end on all arrays
            // get smallest number from k arrays
            Item smallestItem = getMinValue(smallestItemsFromAllArrays);
            sortedList.add(smallestItem.num);
            // increment the index for the corresponding array
            elementIdxs.set(smallestItem.arrayIdx, elementIdxs.get(smallestItem.arrayIdx) + 1);
        }
        return sortedList;
    }

    private Item getMinValue(List<Item> smallestItems) {
        int minValueIdx = 0;
        for (int i = 1; i < smallestItems.size(); i++) {
            if (smallestItems.get(i).num < smallestItems.get(minValueIdx).num)
                minValueIdx = i;
        }
        return smallestItems.get(minValueIdx);
    }

    class Item {
        int arrayIdx; // array index (which array)
        int num;      // the actual number
        Item(int arrayIdx, int num) {
            this.arrayIdx = arrayIdx;
            this.num = num;
        }
    }

    // log(k) remove from heap, log(k) to insert heap, build heap is O(k) time
    // O(nlog(k) + K) time | O(n + k) space - where n is the total
    // number of array elements and k is the number of  arrays
    public List<Integer> mergeSortedArraysUsingHeap(List<List<Integer>> arrays) {
        List<Integer> sortedList = new ArrayList<>();
        List<HeapItem> smallestItems = new ArrayList<>();

        // put the first element of each array into smallestItems (initialize the heap)
        for (int whichArrayIdx = 0; whichArrayIdx < arrays.size(); whichArrayIdx++) {
            smallestItems.add(new HeapItem(whichArrayIdx, 0, arrays.get(whichArrayIdx).get(0)));
        }

        // create the heap based on smallestItems
        MinHeap minHeap = new MinHeap(smallestItems);
        // remove from minHeap to add to sortedList, increment elementIdx when the item is removed
        while (!minHeap.isEmpty()) {
            // note smallestItem keeps track of the array index, element index and the value
            HeapItem smallestItem = minHeap.remove();
            sortedList.add(smallestItem.num);
            // reached the end of array
            if (smallestItem.elementIdx == arrays.get(smallestItem.arrayIdx).size()-1) continue;
            // increment element index in the array with the minimum and add minimum to heap
            minHeap.insert(new HeapItem(smallestItem.arrayIdx,
                            smallestItem.elementIdx+1,  // increment the element idx
                                      arrays.get(smallestItem.arrayIdx).get(smallestItem.elementIdx+1)));
        }
        return sortedList;
    }

    // Use data structure to encapsulate the array index and element index information
    class HeapItem {
        int arrayIdx;
        int elementIdx;
        int num;
        HeapItem(int arrayIdx, int elementIdx, int num) {
            this.arrayIdx = arrayIdx;
            this.elementIdx = elementIdx;
            this.num = num;
        }
    }

    class MinHeap {
        java.util.List<HeapItem> heap = new ArrayList<>();

        public MinHeap(java.util.List<HeapItem> array) {
            heap = buildHeap(array);
        }

        // o(n) time | O(1) space
        // - get first parent index from the bottom
        // - iterate from first parent idx backwards to 0 and sift down each element
        private List<HeapItem> buildHeap(List<HeapItem> array) {
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
        private void siftDown(int parentIdx, int endIdx, List<HeapItem> array) {
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
                if (childTwoIdx != -1 && array.get(childTwoIdx).num <
                        array.get(childOneIdx).num) {
                    idxSmallestOfTwoChildren = childTwoIdx;
                } else {
                    idxSmallestOfTwoChildren = childOneIdx;
                }

                // if smallest of two children is smaller than curIdx element
                // perform swap (idxSmallestOfTwoChildren > curIdx)
                if (array.get(idxSmallestOfTwoChildren).num < array.get(parentIdx).num) {
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
        private void siftUp(int childIdx, List<HeapItem> heap) {
            // get index of parent, which is less than curIdx
            int parentIdx = (childIdx - 1) / 2;
            // iterate upwards (backwards) and perform swap
            while (childIdx > 0 && heap.get(childIdx).num < heap.get(parentIdx).num) {
                // swap and move curIdx up to parent idx
                swap(heap, childIdx, parentIdx);
                childIdx = parentIdx;
                // calculate the next parent idx
                parentIdx = (childIdx - 1) / 2;
            }
        }

        private HeapItem peek() {
            return heap.get(0);
        }

        boolean isEmpty() {
            return heap.size() == 0;
        }

        // removes the head node (first element of list)
        // to do this we need to remove the top node but need to
        // put it last before performing the remove. There is no way
        // to remove from top node and heapify
        // always swap the first element and last element first
        // then remove the last element
        // perform sift down operation
        private HeapItem remove() {
            // swap beginning and end elements, this is the only way to heapify the heap
            // removing the top node and we will not be able to heapify
            swap(heap, 0, heap.size() - 1);
            // set value to remove to the end of the list
            HeapItem valueToRemove = heap.get(heap.size() - 1);
            // remove the last item from list
            heap.remove(heap.size() - 1);
            // call siftdown to down propagate from 0 indice
            siftDown(0, heap.size()-1, heap);
            return valueToRemove;
        }

        // add to the list and sift up from the last element
        private void insert(HeapItem value) {
            heap.add(value);
            // sift up from the last element since it was
            // added to the tail of list
            siftUp(heap.size() - 1, heap);
        }

        private void swap(List<HeapItem> array, int i, int j) {
            HeapItem temp = heap.get(j);
            heap.set(j, heap.get(i));
            heap.set(i, temp);
        }
    }
}
