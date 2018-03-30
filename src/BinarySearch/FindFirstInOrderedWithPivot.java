package BinarySearch;

/**
 * Created by leiwang on 3/24/18.
 */

//Input arr[] = {3, 4, 5, 1, 2}
//        Element to Search = 1
//        1) Find out pivot point and divide the array in two
//        sub-arrays. (pivot = 2) /*Index of 5*/
//        2) Now call binary search for one of the two sub-arrays.
//        (a) If element is greater than 0th element then
//        search in left array
//        (b) Else Search in right array
//        (1 will go in else as 1 < 0th element(3))
//        3) If element is found in selected sub-array then return index
//        Else return -1.

/**
 * finds the index of the first occurance of the key when the array is sorted
 * but rotated
 */
public class FindFirstInOrderedWithPivot {
    public static void main(String[] args) {
        int[] nums = new int[]{4, 5, 6, 6, 7, 8, 1, 2, 3};
        int n = nums.length;
        int valueToSearch = 3;

        pivotedBinarySearch(nums, n, valueToSearch);
    }

    private static int pivotedBinarySearch(int[] nums, int length, int key) {
        int pivot = findPivot (nums, 0, nums.length-1);

        // if no pivot there is not rotation in the array
        if (pivot == -1)
            return binarySearch(nums, 0, length - 1, key);

        if (nums[pivot] == key) {
            return pivot;
        }
        if (nums[0] <= key) {
            return binarySearch(nums,0,pivot-1,key);
        }
        return binarySearch(nums,pivot+1,length-1,key);
    }

    private static int findPivot(int[] nums, int low, int high) {
        // base case
        if (low > high) {
            return -1;
        }
        if (low == high) {
            return low;
        }

        int mid = low + (low + high) / 2;
        if (mid < high && nums[mid] > nums[mid+1]) {
            return mid;
        }
        if (mid > low && (nums[mid] < nums[mid-1])) {
            return mid-1;
        }
        if (nums[low] >= nums[mid]) {
            return findPivot(nums, low, mid - 1);
        }
        return findPivot(nums, mid + 1, high);
    }

    private static int binarySearch(int[] nums, int low, int high, int key) {
        if (high < low)
            return -1;

        int mid = (low + high) / 2;
        if (key == nums[mid])
            return mid;
        if (key > nums[mid])
            return binarySearch(nums, mid + 1, high, key);
        return binarySearch(nums, low, (mid -1), key);
    }

}
