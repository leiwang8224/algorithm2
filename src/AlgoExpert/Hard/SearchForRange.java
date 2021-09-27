package AlgoExpert.Hard;


public class SearchForRange {
    // O(log(n)) time | O(log(n)) space for recursive stack
    /**
     * binary search but when the target is found, check the left or right index
     * to see if we are at beginning or end, if target is not found, keep recursively
     * search.
     * Kind of like performing binary search twice:
     * - first one to find the target
     * - second one to find the start and end index of the range
     */
    private static int[] searchForRange(int[] array, int target) {
        int[] result = {-1, -1};
        // goLeft to find the start of sequence
        search(array, target, 0, array.length - 1, result, true);
        // find the end of the sequence
        search(array, target, 0, array.length - 1, result, false);
        return result;
    }

    private static void search(int[] array,
                               int target,
                               int startIndex,
                               int endIndex,
                               int[] result,
                               boolean goLeft) {
        if (startIndex > endIndex) {
            return;
        }

        int midPoint = (startIndex + endIndex) / 2;
        if (array[midPoint] < target) {
            // search on the right side of midpoint
            search(array, target, midPoint + 1, endIndex, result, goLeft);
        } else if (array[midPoint] > target) {
            // search on the left side of midpoint
            search(array, target, startIndex, midPoint - 1, result, goLeft);
        } else { // midpoint = target
            if (goLeft) {
                // corner case of starting index at 0, and check the point left of the
                // mid point is beginning of sequence
                if (midPoint == 0 || array[midPoint - 1] != target) {
                    result[0] = midPoint;
                } else {
                    // search left side of mid point
                    search(array, target, startIndex, midPoint - 1, result, goLeft);
                }
            } else {
                // check the point right of the mid point is the end of the sequence
                if (midPoint == array.length - 1 || array[midPoint + 1] != target) {
                    result[1] = midPoint;
                } else {
                    // search right side of mid point
                    search(array, target, midPoint + 1, endIndex, result, goLeft);
                }
            }
        }
    }

    // O(log(n)) time | O(1) space
    private static int[] searchForRangeIter(int[] array, int target) {
        int[] result = new int[]{-1, -1};
        searchIterative(array, target, 0, array.length - 1, result, true);
        searchIterative(array, target, 0, array.length - 1, result, false);
        return result;
    }

    private static void searchIterative(int[] array,
                                        int target,
                                        int startIndex,
                                        int endIndex,
                                        int[] result,
                                        boolean goLeft) {
        while (startIndex <= endIndex) {
            int midPoint = (startIndex + endIndex) / 2;
            if (array[midPoint] < target) {
                startIndex = midPoint + 1;
            } else if (array[midPoint] > target) {
                endIndex = midPoint - 1;
            } else {
                if (goLeft) {
                    if (midPoint == 0 || array[midPoint - 1] != target) {
                        result[0] = midPoint;
                        return;
                    } else {
                        endIndex = midPoint - 1;
                    }
                } else {
                    if (midPoint== array.length - 1 || array[midPoint + 1] != target) {
                        result[1] = midPoint;
                        return;
                    } else {
                        startIndex = midPoint + 1;
                    }
                }
            }
        }
    }
}
