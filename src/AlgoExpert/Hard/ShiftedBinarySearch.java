package AlgoExpert.Hard;

public class ShiftedBinarySearch {
    // O(log(n)) time | O(log(n)) space
    private static int shiftedBinarySearch(int[] array, int target) {
        return findTarget(array, target, 0, array.length-1);
    }

    /**
     * More checks needed than a regular binary search
     * 1. if left number <= middleNumber  -> SORTED CASE
     *       if target < middleNumber && target >= left number
     *          return findTarget(array, target, left -> middle)
     *       else
     *          return findTarget(array, target, middle -> right)
     *    left number > middleNumber      -> UNSORTED CASE
     *       if target > middleNumber && target <= right number
     *          return findTarget(array, target, middle -> right)
     *       else
     *          return findTarget(array, target, left -> middle)
     *  - Need to check if sorted, then check bounds then recurse
     */
    private static int findTarget(int[] array, int target, int left, int right) {
        if (left > right) return -1;

        int middleIndex = (left + right) / 2;
        int potentialMatch = array[middleIndex];

        if (target == potentialMatch) return middleIndex;
        else if (array[left] <= potentialMatch) { // check if left half is sorted
            // NOTE instead of checking against the midPoint(potentialMatch) we compare to the leftmost point to midPoint
            // check if target is within bound of left half, if so recurse on left half
            if (target < potentialMatch && target >= array[left]) { // note the >= is important (include the first indice)
                return findTarget(array, target, left, middleIndex -1);
            } else { // else recurse on right half
                return findTarget(array, target, middleIndex + 1, right);
            }
        } else { // left half is not sorted, perform opposite search
            // check if target is within the bounds of the right half, if so recurse on right half
            if (target > potentialMatch && target <= array[right]) { // note the <= is important (include the last indice)
                return findTarget(array, target, middleIndex + 1, right);
            } else { // else recurse on left half
                return findTarget(array, target, left, middleIndex - 1);
            }
        }
    }

    // O(log(n)) time | O(1) space
    private static int shiftBinarySearchIterative(int[] array, int target) {
        return findTargetIterative(array, target, 0, array.length - 1);
    }

    private static int findTargetIterative(int[] array, int target, int left, int right) {
        while (left <= right) {
            int middle = (left + right) / 2;
            int potentialMatch = array[middle];
            int leftNum = array[left];
            int rightNum = array[right];
            if (target == potentialMatch) {
                return middle;
            } else if (leftNum <= potentialMatch) {
                if (target < potentialMatch && target >= leftNum) { // note the >= is important
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            } else {
                if (target > potentialMatch && target <= rightNum) { // note the <= is important
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            }
        }
        return -1;
    }
}
