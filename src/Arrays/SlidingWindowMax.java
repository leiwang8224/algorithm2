package Arrays;

import java.util.Deque;
import java.util.LinkedList;

public class SlidingWindowMax {
    public static void main(String[] args) {

    }

    /**
     * We scan the array from 0 to n-1, keep "promising" elements in the deque.
     * The algorithm is amortized O(n) as each element is put and polled once.
     *
     * At each i, we keep "promising" elements, which are potentially max number
     * in window [i-(k-1),i] or any subsequent window. This means if an element
     * in the deque and it is out of i-(k-1), we discard them. We just need to
     * poll from the head, as we are using a deque and elements are ordered as
     * the sequence in the array
     *
     * Now only those elements within [i-(k-1),i] are in the deque. We then
     * discard elements smaller than a[i] from the tail. This is because
     * if a[x] <a[i] and x<i, then a[x] has no chance to be the "max" in [i-(k-1),i],
     * or any other subsequent window: a[i] would always be a better candidate.
     *
     * As a result elements in the deque are ordered in both sequence in array
     * and their value. At each step the head of the deque is the max element in [i-(k-1),i]
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return new int[0];
        }
        int[] result = new int[nums.length-k+1];
        int resultIndex = 0;
        // store index
        Deque<Integer> queue = new LinkedList<>();
        for (int index = 0; index < nums.length; index++) {
            // remove numbers out of range k
            while (!queue.isEmpty() && queue.peek() < index - k + 1) {
                queue.poll();
            }
            // remove smaller numbers in k range as they are useless
            while (!queue.isEmpty() && nums[queue.peekLast()] < nums[index]) {
                queue.pollLast();
            }
            // q contains index... r contains content
            queue.offer(index);
            if (index >= k - 1) {
                result[resultIndex++] = nums[queue.peek()];
            }
        }
        return result;
    }

//    For Example: A = [2,1,3,4,6,3,8,9,10,12,56], w=4
//
//    partition the array in blocks of size w=4. The last block may have less then w.
//            2, 1, 3, 4 | 6, 3, 8, 9 | 10, 12, 56|
//
//    Traverse the list from start to end and calculate max_so_far.
//    Reset max after each block boundary (of w elements).
//    left_max[] = 2, 2, 3, 4 | 6, 6, 8, 9 | 10, 12, 56
//
//    Similarly calculate max in future by traversing from end to start.
//            right_max[] = 4, 4, 4, 4 | 9, 9, 9, 9 | 56, 56, 56
//
//    now, sliding max at each position i in current window,
//    sliding-max(i) = max{right_max(i), left_max(i+w-1)}
//    sliding_max = 4, 6, 6, 8, 9, 10, 12, 56
    public static int[] slidingWindowMax(final int[] nums, final int k) {
        final int[] max_left = new int[nums.length];
        final int[] max_right = new int[nums.length];

        max_left[0] = nums[0];
        max_right[nums.length - 1] = nums[nums.length - 1];

        for (int leftIndex = 1; leftIndex < nums.length; leftIndex++) {
            max_left[leftIndex] = (leftIndex % k == 0) ?
                                    nums[leftIndex] :
                                    Math.max(max_left[leftIndex - 1], nums[leftIndex]);

            final int rightIndex = nums.length - leftIndex - 1;
            max_right[rightIndex] = (rightIndex % k == 0) ?
                                    nums[rightIndex] :
                                    Math.max(max_right[rightIndex + 1], nums[rightIndex]);
        }

        final int[] slidingMax = new int[nums.length - k + 1];
        for (int i = 0, j = 0; i + k <= nums.length; i++) {
            slidingMax[j++] = Math.max(max_right[i], max_left[i + k - 1]);
        }

        return slidingMax;
    }
}
