package AlgoExpert.VeryHard;
import java.util.*;

public class CountInversions {
    // O(nlogn) time | O(n) space
    int countInversions(int[] array) {
        return countSubArrayInversionsRecurse(array, 0, array.length);// note its [0,array.length-1]
    }

    private int countSubArrayInversionsRecurse(int[] array, int start, int end) {
        if (end - start <= 1) return 0; // no inversions if the # element is <= 1 (base case)

        int middle = start + (end - start) / 2;
        int leftInversions = countSubArrayInversionsRecurse(array, start, middle);
        int rightInversions = countSubArrayInversionsRecurse(array, middle, end);
        // left and right array sorted at this point so we can call mergesort
        int mergedArrayInversions = mergeSortAndCountInversions(array, start, middle, end);
        return leftInversions + rightInversions + mergedArrayInversions;
    }

    private int mergeSortAndCountInversions(int[] array, int start, int middle, int end) {
        List<Integer> sortedArray = new ArrayList<>();
        int leftPosition = start;
        int rightPosition = middle;
        int inversions = 0;

        while (leftPosition < middle && rightPosition < end) {
            if (array[leftPosition] <= array[rightPosition]) {
                // insert into sortedArray
                sortedArray.add(array[leftPosition]);
                leftPosition += 1;
            } else { // left array element is greater than right array element, insertion needed
                inversions += middle - leftPosition;  //# of inversion is equal to the # of elements left in the left array
                sortedArray.add(array[rightPosition]);
                rightPosition += 1;
            }
        }

        // add remaining elements in left Array, if any
        for (int leftIdx = leftPosition; leftIdx < middle; leftIdx++) {
            sortedArray.add(array[leftIdx]);
        }

        // add remaining elements in right array, if any
        for (int rightIdx = rightPosition; rightIdx < end; rightIdx++) {
            sortedArray.add(array[rightIdx]);
        }

        for (int sortedArrayIdx = 0; sortedArrayIdx < sortedArray.size(); sortedArrayIdx++) {
            int num = sortedArray.get(sortedArrayIdx);
            array[start + sortedArrayIdx] = num;
        }

        return inversions;
    }
}
