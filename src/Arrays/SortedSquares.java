package Arrays;

public class SortedSquares {
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int left = 0;
        int right = n - 1;

        for (int i = n - 1; i >= 0; i--) {
            int square;
            if (Math.abs(nums[left]) < Math.abs(nums[right])) {
                square = nums[right]; // put right value into the rightmost position
                right--;
            } else {
                square = nums[left]; // put left value into rightmost position
                left++;
            }
            result[i] = square * square;
        }
        return result;
    }
}
