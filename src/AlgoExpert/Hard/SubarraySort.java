package AlgoExpert.Hard;

public class SubarraySort {
    //O(n) time | O(1) space

    /**
     * find all the unsorted numbers and find the min and max of the
     * unsorted numbers. Then find the index of the min and max
     * unsorted number is found by comparing cur element to element before and
     * after
     */
    private static int[] subarraySort(int[] array) {
        int minNumberOutOfOrder = Integer.MAX_VALUE;
        int maxNumberOutOfOrder = Integer.MIN_VALUE;

        for (int index = 0; index < array.length; index++) {
            int numAtIndex = array[index];
            // check element left and right of the cur element
            if (isOutOfOrder(index, numAtIndex, array)) {
                minNumberOutOfOrder = Math.min(minNumberOutOfOrder, numAtIndex);
                maxNumberOutOfOrder = Math.max(maxNumberOutOfOrder, numAtIndex);
            }
        }

        // return invalid solution (everything in order)
        if (minNumberOutOfOrder == Integer.MAX_VALUE) {
            return new int[] {-1, -1};
        }

        // now find the indices for where the min and max
        // are supposed to be in ordered list
        int subarrayLeftIdx = 0;
        while (minNumberOutOfOrder >= array[subarrayLeftIdx]) {
            subarrayLeftIdx ++ ;
        }

        int subarrayRightIdx = array.length - 1;
        while (maxNumberOutOfOrder <= array[subarrayRightIdx]) {
            subarrayRightIdx --;
        }

        return new int[] {subarrayLeftIdx, subarrayRightIdx};
    }

    private static boolean isOutOfOrder(int index, int num, int[] array) {
        if (index == 0) return num > array[index+1];

        if (index == array.length - 1) return num < array[index-1];

        return num > array[index + 1] || num < array[index - 1];
    }
}
