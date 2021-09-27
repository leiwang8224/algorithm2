package AlgoExpert.Hard;

public class IndexEqualsValue {
    public static int indexEqualsValue(int[] array) {
        return indexEqualsValue(array, 0, array.length - 1);
    }

    // O(log(n)) time | O(log(n)) space
    private static int indexEqualsValue(int[] array, int leftIdx, int rightIdx) {
        if (leftIdx > rightIdx) return -1;

        int middleIdx = leftIdx + (rightIdx - leftIdx) / 2;
        // middleIdx = (leftIdx + rightIdx) / 2;
        int middleVal = array[middleIdx];

        // compare val to index, if val < index then for every index < middleIdx
        // index < array[index] so search the right side
        if (middleVal < middleIdx) {
            return indexEqualsValue(array, middleIdx + 1, rightIdx);
            // if the value == idx and the idx == 0, nothing is smaller
        } else if ((middleVal == middleIdx) && middleIdx == 0) {
            return middleIdx;
            // idx == val and value to the left < its index
            // then we have found the smallest idx = val
        } else if ((middleVal == middleIdx) && array[middleIdx - 1] < middleIdx - 1) {
            return middleIdx;
        } else { // middleVal > middleIdx search left side
            return indexEqualsValue(array, leftIdx, middleIdx - 1);
        }
    }

    // O(log(n)) time | O(1) space
    private static int indexEqualsValueIterative(int[] array) {
        int leftIdx = 0;
        int rightIdx = array.length - 1;

        while (leftIdx <= rightIdx) {
            int middleIdx = rightIdx + (leftIdx - rightIdx) / 2;
            // middleIdx = (leftIdx + rightIdx) / 2;
            int middleVal = array[middleIdx];

            if (middleVal < middleIdx) {
                leftIdx = middleIdx + 1;
            } else if ((middleVal == middleIdx) && middleIdx == 0) {
                return middleIdx;
            } else if (middleVal == middleIdx && array[middleIdx - 1] < (middleIdx - 1)) {
                return middleIdx;
            } else {
                rightIdx = middleIdx - 1;
            }
        }

        return -1;
    }
}
