package CTCI.RecursionAndDP;

/**
 * A magic index in an array A[1..n-1] is defined to be an index such that A[i] = i.
 * Given a sorted array of distinct integers, write a method to find a magic index,
 * if one exists, in array A.
 */
public class MagicIndex {
    public static void main(String[] args) {
        int[] array = new int[]{1,2,3,4,5,6,6};
        System.out.println(magicSlow(array));

    }

    static int magicSlow(int[] array) {
        for (int index = 0; index < array.length; index++) {
            if (array[index] == index) return index;
        }
        return -1;
    }

    static int magicBinarySearch(int[] array) {
        return magicBinarySearch(array, 0, array.length-1);
    }

    private static int magicBinarySearch(int[] array, int start, int end) {
        if (end < start) return -1;
        int mid = (start + end) / 2;
        if (array[mid] == mid) return mid;
        // remember to -1 to mid for 0 indexing
        else if (array[mid] > mid) return magicBinarySearch(array, start, mid-1);
        // remember to +1 to mid
        else return magicBinarySearch(array, mid+1, end);
    }

    /**
     * What is the elements are not unique?
     * When we see that A[mid] < mid, we cannot conclude which side the magic index is on.
     * It could be on the right side, as before, or it could be on the left side.
     * Could it be anywhere on the left side? Not exactly. Since A[5] = 3, we know that A[4] couldn't
     * be a magic index. A[4] would need to be 4 to be the magic index, but A[4] must be less than
     * or equal to A[5].
     * In fact, when we see that A[5] = 3, we will need to recursively search the right side as before.
     * But to search the left side, we can skip a bunch of elements and only recursively search
     * elementsA[0] through A[3]. A[3] is the first element that could be a magic index.
     * The general pattern is that we compare midIndex and midValue for equality first.
     * Then, if they are not equal, we recursively search the left and right sides as follows:
     * - Left side: search indices start through Math.min(midIndex - 1, midValue)
     * - Right side: search indices Math.max(midIndex + 1, midValue) through end.
     * @param array
     * @return
     */
    private static int magicBinarySearchNotUnique(int[] array) {
        return magicBinarySearchNotUnique(array, 0 , array.length-1);

    }

    private static int magicBinarySearchNotUnique(int[] array, int start, int end)  {
        if (end < start) return -1;

        int midIndex = (start + end) / 2;
        int midValue = array[midIndex];

        if (midValue == midIndex) return midIndex;

        // search left
        // take minimum of middle index and the middle value
        int leftIndex = Math.min(midIndex - 1, midValue);
        int left = magicBinarySearchNotUnique(array, start, leftIndex);

        if (left >= 0) return left;

        // search right
        // take maximum of middle index and middle value
        int rightIndex = Math.max(midIndex + 1, midValue);
        int right = magicBinarySearchNotUnique(array, rightIndex, end);

        return right;
    }
}
