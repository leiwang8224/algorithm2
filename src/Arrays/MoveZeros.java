package Arrays;

public class MoveZeros {
    public void moveZeros(int[] nums) {
        int lastNonZeroFoundAt = 0;

        // move all nonzeros to the front of the last non zero position
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[lastNonZeroFoundAt++] = nums[i];
            }
        }

        // then just put the zeros at the end
        for (int i = lastNonZeroFoundAt; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}
