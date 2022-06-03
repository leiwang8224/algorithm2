package Arrays;

public class RotateRight {
    public void rotateBruteForce(int[] nums, int k) {
        // speed up the rotation
        k %= nums.length;
        int temp, previous;
        for (int i = 0; i < k; i++) { // for each k element
            previous = nums[nums.length - 1];
            for (int j = 0; j < nums.length; j++) { // perform nums.length rotation
                temp = nums[j];
                nums[j] = previous;
                previous = temp;
            }
        }
    }

    public void rotateExtraArray(int[] nums, int k) {
        int[] bufferArray = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            bufferArray[(i + k) % nums.length] = nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = bufferArray[i];
        }
    }

    public void rotateCyclicReplace(int[] nums, int k) {
        k = k % nums.length;
        int count = 0;
        for (int start = 0; count < nums.length; start++) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % nums.length;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }

//    Original List                   : 1 2 3 4 5 6 7
//    After reversing all numbers     : 7 6 5 4 3 2 1
//    After reversing first k numbers : 5 6 7 4 3 2 1
//    After revering last n-k numbers : 5 6 7 1 2 3 4 --> Result
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }
    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}
