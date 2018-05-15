import java.util.Arrays;

/**
 * Created by leiwang on 3/11/18.
 */

/**
 *  Suppose you have N integers from 1 to N. We define a beautiful
 *  arrangement as an array that is constructed by these N numbers
 *  successfully if one of the following is true for the ith
 *  position (1 <= i <= N) in this array:

 The number at the ith position is divisible by i.
 i is divisible by the number at the ith position.

 Now given N, how many beautiful arrangements can you construct?

 Example 1:

 Input: 2
 Output: 2
 Explanation:

 The first beautiful arrangement is [1, 2]:

 Number at the 1st position (i=1) is 1, and 1 is divisible by i (i=1).

 Number at the 2nd position (i=2) is 2, and 2 is divisible by i (i=2).

 The second beautiful arrangement is [2, 1]:

 Number at the 1st position (i=1) is 2, and 2 is divisible by i (i=1).

 Number at the 2nd position (i=2) is 1, and i (i=2) is divisible by 1.

 */
public class BeautifulArrangements {
    static int count = 0;

    public static void main(String[] args) {
        System.out.println("result = " + countArrangement(3));
        System.out.println("result brute force = " + countArrangementBruteForce(3));
        System.out.println("result brute force better = " + countArrangementBetterBruteForce(3));
    }

    static int countBruteForce = 0;

    /**
     * Using brute force method
     * Time O(N!) total of N! permutations will be generated for array of length N
     * Space O(N) the depth of the recursion tree can go up to N
     * @param N
     * @return
     */
    private static int countArrangementBruteForce(int N) {
        System.out.println("starting brute force method");
        int[] nums = new int[N];
        // populate the nums array with its index
        for (int i = 1; i <= N; i ++) {
            nums[i-1] = i;
        }
        System.out.println("nums = " + Arrays.toString(nums));
        permute(nums,0);
        return countBruteForce;
    }

    private static void permute(int[] nums, int pos) {
        if (pos == nums.length - 1) {
            int i;
            for (i = 1; i <= nums.length; i ++) {
                // convert i to index, get the nums and find remainder
                // if not beautiful arrangement, return
                if (nums[i - 1] % i != 0 && i % nums[i-1] != 0)
                    break;
            }
            if (i == nums.length + 1)
                countBruteForce++;
        }
        for (int i = pos; i < nums.length; i ++) {
            // i iterate from pos to N
            System.out.println("entering swap with nums = " + java.util.Arrays.toString(nums)
                               + " i = " + i + " pos = " + pos);
            swap(nums, i, pos);
            System.out.println("entering permute with nums = " + java.util.Arrays.toString(nums)
                               + " i = " + i + " pos = " + (pos + 1));
            permute(nums, pos + 1);
            System.out.println("entering swap with nums = " + java.util.Arrays.toString(nums)
                               + " i = " + i + " pos = " + pos);
            swap(nums, i, pos);
        }
    }

    private static void swap(int[] nums, int x, int y) {
        int temp = nums[x];
        nums[x] = nums[y];
        nums[y] = temp;
    }

    private static int countBetterBruteForce = 0;

    /**
     * Instead of creating the full array for every permutation then check the array,
     * we can keep checking the elements while being added to the permutation array at every
     * step for the divisibility condition and stop creating it when we find out the
     * element just added violates the divisibility condition.
     * Time O(k) where k refers to the number of valid permutations
     * Space O(N) the depth of recursion tree can go up to N
     * @param N
     * @return
     */
    private static int countArrangementBetterBruteForce(int N) {
        int[] nums = new int[N];
        for (int i = 1; i <= N; i++)
            nums[i-1] = i;
        permute2(nums, 0);
        return countBetterBruteForce;
    }

    private static void permute2(int[] nums, int pos) {
        if (pos == nums.length)
            countBetterBruteForce++;
        for (int i = pos; i < nums.length; i ++) {
            swap(nums, i, pos);
            if (nums[pos] % (pos + 1) == 0 || (pos + 1) % nums[pos] == 0)
                permute2(nums, pos + 1);
            swap(nums, i, pos);
        }
    }

    /**
     * Count how many different arrangements given N using Backtracking method
     * Create all permutations of the numbers from 1 to N. Need to keep track
     * of which numbers are used
     * @param N
     * @return
     */
    public static int countArrangement(int N) {
        if (N == 0) return 0;
        helper(N, 1, new int[N + 1]);
        return count;
    }

    private static void helper(int N, int pos, int[] used) {
        System.out.println("Entering helper with used = " + Arrays.toString(used) + " N = " + N + " pos " + pos);
        // one arrangement has made so increment count by 1
        if (pos > N) {
            count++;
            return;
        }

        for (int i = 1; i <= N; i++) {
            // value at index i is divisible by index i and index i is divisible by value at index i
            // check to see if current value is used
            if (used[i] == 0 && (i % pos == 0 || pos % i == 0)) {
                // select then call recursive
                used[i] = 1;
                System.out.println("before calling helper method used = " + Arrays.toString(used));
                // note the selection is based on incrementing the pos index
                helper(N, pos + 1, used);
                // un-select to reset the array to all zeros
                used[i] = 0;
                System.out.println("after calling helper method used = " + Arrays.toString(used));
            }
        }
    }
}