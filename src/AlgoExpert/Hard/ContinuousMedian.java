package AlgoExpert.Hard;

import java.util.*;
import java.util.function.BiFunction;

public class ContinuousMedian {
    // Lower Half in MaxHeap, Greater Half in Min Heap
    //
    private static class ContinuousMedianHandler {
        Heap lowerMaxHeap = new Heap(Heap::MAX_HEAP_FUNC, new ArrayList<>());
        Heap upperMinHeap = new Heap(Heap::MIN_HEAP_FUNC, new ArrayList<>());

        double median = 0;

        void insert(int insertValue) {
            // insert into lower max heap if empty
            // or the number < first element lower max heap
            if (lowerMaxHeap.length == 0 || insertValue < lowerMaxHeap.peek()) {
                lowerMaxHeap.insert(insertValue);
            } else {
                upperMinHeap.insert(insertValue);
            }
            // need to rebalance the heaps if one is greater than the
            // other one by 2 elements (needs to keep within 1 element of each other)
            rebalanceHeaps();
            updateMedian();
        }

        /**
         * update the median after balancing the heaps so that a new median is always
         * calculated after insertion of new element
         */
        private void updateMedian() {
            if (lowerMaxHeap.length == upperMinHeap.length) {
                median = ((double) lowerMaxHeap.peek() + (double) upperMinHeap.peek()) / 2;
            } else if (lowerMaxHeap.length > upperMinHeap.length) {
                // gets the first element of the heap
                median = lowerMaxHeap.peek();
            } else {
                median = upperMinHeap.peek();
            }
        }

        /**
         * If lengths of the heaps are equal to 2, insert one into the other
         * to balance the heaps
         */
        private void rebalanceHeaps() {
            if (lowerMaxHeap.length - upperMinHeap.length == 2) {
                upperMinHeap.insert(lowerMaxHeap.remove());
            }else if (upperMinHeap.length - lowerMaxHeap.length == 2) {
                lowerMaxHeap.insert(upperMinHeap.remove());
            }
        }
    }

    static class Heap {
        List<Integer> heap = new ArrayList<>();
        BiFunction<Integer, Integer, Boolean> comparisonFunc;
        // need length so we can rebalance the lower and upper heap if needed
        int length;

        // BiFunctions defined here, which is a function
        // that takes two parameters and output one
        static Boolean MAX_HEAP_FUNC(Integer a, Integer b) {
            return a > b;
        }
        static Boolean MIN_HEAP_FUNC(Integer a, Integer b) {
            return a < b;
        }

        // converts input array into heap given the func (max or min heap)
        Heap(BiFunction<Integer, Integer, Boolean> func,
             List<Integer> arrayList) {
            comparisonFunc = func;
            heap = buildHeap(arrayList); // pass in empty new list (either min or max heap)
            length = heap.size();
        }

        //start at the end of the array and move backwards towards the front.
        //At each iteration, you sift an item down until it is in the correct location.
        List<Integer> buildHeap(List<Integer> arrayList) {
            // get first parent and sift down from first parent index
            int firstParentIdx = (arrayList.size() - 2) / 2;
            for (int curIdx = firstParentIdx; curIdx >= 0; curIdx--) {
                // sift down from the end of the tree to the top of the tree
                siftDown(curIdx, arrayList.size() - 1, arrayList);
            }
            return arrayList;
        }

        // move value to the right of array (getting children or down the tree)
        // move smaller value down
        // O(n)
        void siftDown(int curIdx, int endIdx, List<Integer> heap) {
            int childOneIdx = curIdx * 2 + 1;
            while (childOneIdx <= endIdx) {
                int childTwoIDx = curIdx * 2 + 2 <=endIdx ? curIdx * 2 + 2:-1;
                int idxToSwap; // smaller or larger of the two to swap
                if (childTwoIDx != -1) {
                    if (comparisonFunc.apply(heap.get(childTwoIDx), heap.get(childOneIdx))) {
                        idxToSwap = childTwoIDx;
                    } else {
                        idxToSwap = childOneIdx;
                    }
                } else { // childTwoIdx = -1 then idxToSwap needs to be childOneIdx
                    idxToSwap = childOneIdx;
                }
                // element at idxToSwap is smaller / larger than the element at curIdx
                if (comparisonFunc.apply(heap.get(idxToSwap), heap.get(curIdx))) {
                    swap(curIdx, idxToSwap, heap);
                    curIdx = idxToSwap;
                    childOneIdx = curIdx * 2 + 1;
                } else {
                    return;
                }
            }
        }

        // move value to the left of the array (getting parent or up the tree)
        // move bigger values up
        // O(nlog(n))
        void siftUp (int curIdx, List<Integer> heap) {
            // get parent of curIdx
            int parentIdx = (curIdx - 1) / 2;
            while (curIdx > 0) { // NOTE we are checking curIdx not parentIdx
                if (comparisonFunc.apply(heap.get(curIdx), heap.get(parentIdx))) {
                    swap(curIdx, parentIdx, heap);
                    curIdx = parentIdx;
                    // get next parent of curIdx
                    parentIdx = (curIdx - 1) / 2;
                } else {
                    return;
                }
            }
        }

        int peek() {
            return heap.get(0);
        }

        int remove() {
            swap(0, heap.size() - 1, heap);
            int valueToRemove = heap.get(heap.size() - 1);
            heap.remove(heap.size() - 1);
            length --;
            siftDown(0, heap.size() - 1, heap);
            return valueToRemove;
        }

        private void swap(int i, int j, List<Integer> heap) {
            int temp = heap.get(j);
            heap.set(j, heap.get(i));
            heap.set(i, temp);
        }


        void insert(int value) {
            heap.add(value);
            length ++;
            siftUp(heap.size() - 1, heap);
        }
    }

    private static class MedianFinder3 {
        MedianFinder3() {
            System.out.println();
            System.out.println("starting PriorityQueue method");
        }
        List<Integer> list = new ArrayList<>();
        // max queue is always greater than or equal to min queue
        PriorityQueue<Integer> low = new PriorityQueue<>();
        PriorityQueue<Integer> high = new PriorityQueue<>(1000, Collections.reverseOrder());
        public void addNum(int num) {
            high.offer(num);
            low.offer(high.poll());
            if (high.size() < low.size()) {
                high.offer(low.poll());
            }
            System.out.println("after addNum high = " + high.toString() + " low = " + low.toString());
        }

        public int findMedian() {
            System.out.println("current high = " + high.toString());
            System.out.println("current low = " + low.toString());

            if (high.size() == low.size()) {
                System.out.println("high.size = low.size");
                return (high.peek() + low.peek()) / 2;
            } else {
                System.out.println("high.size != low.size");
                return high.peek();
            }
        }
    }
}
