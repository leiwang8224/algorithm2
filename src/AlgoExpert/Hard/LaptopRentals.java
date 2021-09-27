package AlgoExpert.Hard;

import java.util.ArrayList;
import java.util.Collections;

public class LaptopRentals {
    // O(nlog(n)) time | O(n) space
    int laptopRentalsHeap(ArrayList<ArrayList<Integer>> times) {
        if (times.size() == 0) return 0;

        Collections.sort(times, (a,b)->Integer.compare(a.get(0), b.get(0)));
        ArrayList<ArrayList<Integer>> timesWhenLaptopIsused = new ArrayList<>();
        timesWhenLaptopIsused.add(times.get(0));
        MinHeap heap = new MinHeap(timesWhenLaptopIsused);

        for (int idx = 1; idx < times.size(); idx++) {
            ArrayList<Integer> curInterval = times.get(idx);
            if(heap.peek().get(1) <= curInterval.get(0)) {
                heap.remove();
            }
            heap.insert(curInterval);
        }
        return timesWhenLaptopIsused.size();
    }

    static class MinHeap {
        ArrayList<ArrayList<Integer>> heap = new ArrayList<>();

        MinHeap(ArrayList<ArrayList<Integer>> array) {
            heap = buildHeap(array);
        }

        ArrayList<ArrayList<Integer>> buildHeap(ArrayList<ArrayList<Integer>> array) {
            int firstParentIDx = (array.size() - 2) / 2;
            for (int curIdx = firstParentIDx; curIdx >= 0; curIdx--) {
                siftDown(curIdx, array.size() - 1, array);
            }
            return array;
        }

        void siftDown(int curIdx, int endIdx, ArrayList<ArrayList<Integer>> heap) {
            int newCurIdx = curIdx;
            int childOneIdx = curIdx * 2 + 1;
            while (childOneIdx <= endIdx) {
                int childTwoIdx = (newCurIdx * 2 + 2 <= endIdx) ? newCurIdx * 2 + 2 : -1;
                int idxToSwap;
                if (childTwoIdx != -1 &&
                        heap.get(childTwoIdx).get(1) < heap.get(childOneIdx).get(1)) {
                    idxToSwap = childTwoIdx;
                } else {
                    idxToSwap = childOneIdx;
                }

                if (heap.get(idxToSwap).get(1) < heap.get(curIdx).get(1)) {
                    swap(newCurIdx, idxToSwap, heap);
                    newCurIdx = idxToSwap;
                    childOneIdx = newCurIdx * 2 + 1;
                } else {
                    return;
                }
            }
        }

        void siftUp(int curIdx, ArrayList<ArrayList<Integer>> heap) {
            int newCurIdx = curIdx;
            int parentIdx = (curIdx -1) / 2;
            while (newCurIdx > 0 && heap.get(newCurIdx).get(1) < heap.get(parentIdx).get(1)) {
                swap(newCurIdx, parentIdx, heap);
                newCurIdx = parentIdx;
                parentIdx = (newCurIdx - 1) / 2;
            }
        }

        ArrayList<Integer> peek() {
            return heap.get(0);
        }

        ArrayList<Integer> remove() {
            swap(0, heap.size() - 1, heap);
            ArrayList<Integer> valueToRemove = heap.get(heap.size() - 1);
            heap.remove(heap.size() - 1);
            siftDown(0, heap.size() - 1, heap);
            return valueToRemove;
        }

        void insert(ArrayList<Integer> value) {
            heap.add(value);
            siftUp(heap.size() - 1, heap);
        }

        void swap(int i, int j, ArrayList<ArrayList<Integer>> heap) {
            ArrayList<Integer> temp = heap.get(j);
            heap.set(j, heap.get(i));
            heap.set(i, temp);
        }
    }

    // O(nlog(n)) time | O(n) space
    int laptopRentals(ArrayList<ArrayList<Integer>> times) {
        if (times.size() == 0) return 0;

        int usedLaptops = 0;
        ArrayList<Integer>  startTimes = new ArrayList<>();
        ArrayList<Integer>  endTimes = new ArrayList<>();

        for (ArrayList<Integer> interval : times) {
            startTimes.add(interval.get(0));
            endTimes.add(interval.get(1));
        }

        Collections.sort(startTimes);
        Collections.sort(endTimes);

        int startIterator = 0;
        int endIterator = 0;

        while (startIterator < times.size()) {
            if (startTimes.get(startIterator) >= endTimes.get(endIterator)) {
                usedLaptops -= 1;
                endIterator += 1;
            }
            usedLaptops += 1;
            startIterator+= 1;
        }

        return usedLaptops;
    }
}
