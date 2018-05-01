public class TargetSum {
//    You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now you
    // have 2 symbols + and -. For each integer, you should choose one from + and - as its new symbol.
//
//    Find out how many ways to assign symbols to make sum of integers equal to target S.
//
//            Example 1:
//
//    Input: nums is [1, 1, 1, 1, 1], S is 3.
//    Output: 5
//    Explanation:
//
//            -1+1+1+1+1 = 3
//            +1-1+1+1+1 = 3
//            +1+1-1+1+1 = 3
//            +1+1+1-1+1 = 3
//            +1+1+1+1-1 = 3
//
//    There are 5 ways to assign symbols to make the sum of nums be target 3.

//    Note:
//
//    The length of the given array is positive and will not exceed 20.
//    The sum of elements in the given array will not exceed 1000.
//    Your output answer is guaranteed to be fitted in a 32-bit integer.


    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,43,2,32,1,2,4};
        System.out.println(findTargetSumWaysBruteForce(nums, 10));//findTargetSumWaysBruteForce(nums, 10);
        System.out.println(findTargetSumWaysRecurse(nums, 10));
        System.out.println(findTargetSumWaysDP2D(nums, 10));
        System.out.println(findTargetSumWaysDP1D(nums, 10));
    }

    static int countBruteForce = 0;

    /**
     * The brute force method is based on recursion. The + and - symbols are
     * placed at every location in the given nums array to find out the assignments
     * which lead to the result. The function appends a + and - sign both to the
     * element at the current index and calls itself with the updated sum as:
     * sum + nums[i] and sum - nums[i] respectively and update current index with i + 1.
     * Whenever the end of the array is reached, we compare the sum obtained with target.
     * If equal we increment the counter.
     * Time: O(2^N)
     * Space: O(N)
     * @param nums
     * @param target
     * @return
     */
    private static int findTargetSumWaysBruteForce(int[] nums, int target) {
        calculate(nums, 0, 0, target);
        return countBruteForce;
    }

    /**
     * Time O(2^N)
     * Space O(N)
     * @param nums
     * @param index
     * @param sum
     * @param target
     */
    private static void calculate(int[] nums, int index, int sum, int target) {
        if (index == nums.length) {
            if (sum == target)
                countBruteForce++;
        } else {
            calculate(nums, index+1, sum + nums[index],target);
            calculate(nums, index+1, sum-nums[index], target);
        }
    }

    static int countBruteForceRecurse = 0;

    /**
     * Recursion with memoization
     * Time: O(l*n) where l is the range of the sum, and n refers to the size of nums array
     * Space O(n) the depth of the recursion tree is n
     * Since the same value of i and same value of sum is being repeated throughout, we can
     * optimize by using memoization. Thus we store the result obtained from calculate(nums,i,sum,target)
     * in memo[i][sum+1000]. The factor of 1000 has been added as an offset to the sum
     * value to map all the sums possible to positive integer range.
     *
     * @param nums
     * @param target
     * @return
     */
    private static int findTargetSumWaysRecurse(int[] nums, int target) {
        int[][] memo = new int[nums.length][2001];
        for (int[] row : memo)
            java.util.Arrays.fill(row,Integer.MIN_VALUE);
        return calculateRecurse(nums, 0,0,target,memo);
    }

    private static int calculateRecurse(int[] nums, int index, int sum, int target, int[][] memo) {
        if (index == nums.length) {
            if (sum == target)
                return 1;
            else
                return 0;
        } else {
            if (memo[index][sum+1000] != Integer.MIN_VALUE)
                return  memo[index][sum + 1000];
            int add = calculateRecurse(nums, index + 1, sum + nums[index], target, memo);
            int subtract = calculateRecurse(nums, index + 1, sum - nums[index], target, memo);
            memo[index][sum+1000] = add + subtract;
            return memo[index][sum+1000];
        }

    }

    /**
     * Time O(l*n)
     * Space O(l*n)
     * To determine the number of assignments which can lead to a sum
     * of sum+nums[i] up to the (i+1)th index, we can use
     * dp[i][sum+nums[i]] = dp[i][sum+nums[i]] + dp[i-1][sum]
     * dp[i][sum-nums[i]] = dp[i][sum+nums[i]] + dp[i-1][sum]
     * Since sum can range from -1000 to +1000, we need to add an offset
     * of 1000 to the sum indices to map all the sums obtained to
     * positive range only.
     * At the end, the value of dp[n-1](target+1000] gives the required
     * number of assignments.
     * @param nums
     * @param target
     * @return
     */
    private static int findTargetSumWaysDP2D(int[] nums, int target) {
        int[][] dp = new int[nums.length][2001];
        dp[0][nums[0]+1000] = 1;
        dp[0][-nums[0]+1000] += 1;
        for (int i = 1; i < nums.length; i ++) {
            for (int sum = -1000; sum <= 1000; sum++) {
                if (dp[i-1][sum+1000] > 0) {
                    dp[i][sum+nums[i]+1000] += dp[i-1][sum+1000];
                    dp[i][sum-nums[i]+1000] += dp[i-1][sum+1000];
                }
            }
        }
        return target > 1000 ? 0 : dp[nums.length-1][target + 1000];
    }

    /**
     * Based on observations we can see that for the evaluation of the current
     * row of dp, only the values of previous row of dp are needed. Thus we can
     * save space by using 1D DP instead of 2D DP.
     * Time: O(l*n) where the entire nums array is traversed l times, n is the size
     * Space: O(n)
     * @param nums
     * @param target
     * @return
     */
    private static int findTargetSumWaysDP1D(int[] nums, int target) {
        int[] dp = new int[2001];
        dp[nums[0] + 1000] = 1;
        dp[-nums[0] + 1000] += 1;
        for (int i = 1; i < nums.length; i++) {
            int[] next = new int[2001];
            for (int sum = -1000; sum <= 1000; sum++) {
                if (dp[sum + 1000] > 0) {
                    next[sum + nums[i] + 1000] += dp[sum + 1000];
                    next[sum - nums[i] + 1000] += dp[sum + 1000];
                 }
            }
            dp = next;
        }
        return target > 1000 ? 0 : dp[target+ 1000];
    }
}
