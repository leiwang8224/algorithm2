package BinarySearch;

public class SearchInsert {
    public int searchInsert(int[] nums, int target) {
        int midPoint = 0;
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            midPoint = left + (right - left)/2;
            if (nums[midPoint] == target) return midPoint;
            if (target < nums[midPoint])
                right = midPoint - 1;
            else
                left = midPoint + 1;
        }
        return left;
    }


    public int simpleBinarySearch(int[] nums, int target) {
        int pivot, left = 0, right = nums.length - 1;
        while (left <= right) {
            pivot = left + (right - left) / 2;
            if (nums[pivot] == target) return pivot;
            if (target < nums[pivot])
                right = pivot - 1;
            else
                left = pivot + 1;
        }
        return -1;
    }
}
