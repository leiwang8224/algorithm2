package BinarySearch;

/**
 * Created by leiwang on 3/22/18.
 */

/**
 * Binary search method in finding the first and last index
 */
public class FindFirstAndLast {
    public static void main(String args[]) {
        int[] nums = new int[]{1, 3, 5, 5, 5, 5 ,67, 123, 125};

        int n = nums.length;
        int value = 5;

        int first = firstOcc(nums, 0, nums.length, 5, n);
        int last = lastOcc(nums, 0, nums.length, 5, n);

        System.out.println("first occurrance " + first + " last occurance = " + last);

    }

    /**
     * if value exists in array then return index of value
     * or return -1
     * @param intArray
     * @param low
     * @param high
     * @param value
     * @param n
     * @return
     */
    public static int firstOcc(int intArray[], int low, int high, int value, int n) {
        if (high >= low) {
            int mid = low + (high - low) / 2;
            if ((mid == 0 || value > intArray[mid-1]) && intArray[mid] == value) {
                return mid;
            } else if (value > intArray[mid]) {
                return firstOcc(intArray, (mid + 1), high, value, n);
            } else {
                return firstOcc(intArray, low, (mid-1), value, n);
            }
        }
        return -1;
    }

    public static int lastOcc(int intArray[], int low, int high, int value, int n) {
        if (high >= low) {
            int mid = low + (high - low) / 2;
            if ((mid == n - 1 || value < intArray[mid + 1]) && intArray[mid] == value) {
                return mid;
            } else if (value < intArray[mid]) {
                return lastOcc(intArray, low, mid-1, value, n);
            } else {
                return lastOcc(intArray, mid + 1, high, value, n);
            }
        }
        return -1;
    }
}
