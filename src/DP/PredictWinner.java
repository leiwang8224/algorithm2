package DP;

//LC-486
public class PredictWinner {
    public static void main(String[] args) {
        System.out.println(predictWinnerBruteForce(new int[]{1,5,2}));
        System.out.println(predictWinnerBruteForce(new int[]{1,5,233,7}));        ;
        System.out.println(predictWinnerMemo((new int[]{1,5,2})));;
        System.out.println(predictWinnerMemo(new int[]{1,5,233,7}));;
        System.out.println(predictWinnerDP(new int[]{1,5,2}));;
        System.out.println(predictWinnerDP(new int[]{1,5,233,7}));;

    }

    // O(2 ^ n) time
    // O(n) memory
    // turn = 1 is player 1, turn = -1 is player 2
    // at each step we determine the max score possible for current player
    // if it's player 1's turn, we add the current number's score to the total score
    // otherwise we need to subtract the same.
    public static boolean predictWinnerBruteForce(int[] nums) {
        return winner(nums, 0, nums.length -1, 1) >= 0;
    }

    private static int winner(int[] nums, int startIndex, int endIndex, int turn) {
        if (startIndex == endIndex) return turn * nums[startIndex];

        // pick start and recurse, (start +1) or end will be selected by opponent
        // apply negative sign for turn to indicate opponent's turn
        int pickStartIndex = turn * nums[startIndex] + winner(nums, startIndex + 1, endIndex, -turn);
        // pick end and recurse, (end-1) or start will be selected by opponent
        int pickEndIndex = turn * nums[endIndex] + winner(nums, startIndex, endIndex-1, -turn);
        // this represent max(a, b) for player 1 and min(a,b) for player 2
        return turn * Math.max(turn * pickStartIndex, turn * pickEndIndex);
    }

    // O(n ^ 2) time
    // O(n ^ 2) memory
    // concept of memo[start][end] represent the max sum we can get if the array
    // starts from start and ends to end
    private static boolean predictWinnerMemo(int[] nums) {
        Integer[][] memo = new Integer[nums.length][nums.length];
        return winnerMemo(nums, 0, nums.length -1, memo) >= 0;
    }

    // lose the concept of the turn
    private static int winnerMemo(int[] nums, int start, int end, Integer[][] memo) {
        // no longer need to calculate, just return
        if (start == end) return nums[end]; //only one choice left to choose from
        if (memo[start][end] != null) return memo[start][end];

        int playStartIndex = nums[start] - winnerMemo(nums, start + 1, end, memo);
        int playEndIndex = nums[end] - winnerMemo(nums, start, end -1, memo);
        // keep track of the max of the subarray max and min index in memo[start][end]
        memo[start][end] = Math.max(playStartIndex, playEndIndex);
        return memo[start][end];
    }

    // O(n ^ 2) time
    // O(n ^ 2) space
    // dp[row][col] saves how much more scores that the first in action player will get from start to end
    // than the second player.
//    4, After we decide that dp[start][end] saves how much more scores that the first-in-action player will get
//    from start to end than the second player, the next step is how we update the dp table from one state to the
//    next. Going back to the question, each player can pick one number either from the left or the right end
//    of the array. Suppose they are picking up numbers from position start to end in the array and it is player A's turn
//    to pick the number now. If player A picks position start, player A will earn nums[start] score instantly. Then
//    player B will choose from start + 1 to end. Please note that dp[start + 1][end] already saves how much more score that
//    the first-in-action player will get from start + 1 to end than the second player. So it means that player B
//    will eventually earn dp[start + 1][end] more score from start + 1 to end than player A. So if player A picks position
//    start, eventually player A will get nums[start] - dp[start + 1][end] more score than player B after they pick up all numbers.
//    Similarly, if player A picks position end, player A will earn nums[end] - dp[start][end - 1] more score than
//    player B after they pick up all numbers. Since A is smart, A will always choose the max in those two options, so:
//    dp[start][end] = Math.max(nums[start] - dp[start + 1][end], nums[end] - dp[start][end - 1]);
//
//    5, Now we have the recursive formula, the next step is to decide where it all starts. This step is easy
//    because we can easily recognize that we can start from dp[start][start], where dp[start][start] = nums[start]. Then the process
//    becomes a very commonly seen process to update the dp table. I promise that this is a very useful process.
//    Everyone who is preparing for interviews should get comfortable with this process:
//    Using a 5 x 5 dp table as an example, where i is the row number and j is the column number. Each dp[i][j]
//    corresponds to a block at row i, column j on the table. We may start from filling dp[i][i], which are all
//    the diagonal blocks. I marked them as 1. Then we can see that each dp[i][j] depends only on dp[i + 1][j]
//    and dp[i][j - 1]. On the table, it means each block (i, j) only depends on the block to its left (i, j - 1)
//    and to its down (i + 1, j). So after filling all the blocks marked as 1, we can start to calculate those
//    blocks marked as 2. After that, all blocks marked as 3 and so on.
//    ** dp table **
//    1  2  3  4  5 <= final solution, needs to be greater than or equal to 0 to win
//       1  2  3  4
//          1  2  3
//             1  2
//                1
    private static boolean predictWinnerDP(int[] nums) {
        int[][] dp = new int[nums.length][nums.length];
        for (int index = 0; index < nums.length; index++) {
            // populate diagonals with the numbers from the array
            dp[index][index] = nums[index];
        }
        for (int colOffsetFromRow = 1; colOffsetFromRow < nums.length; colOffsetFromRow++) {
            for(int row = 0; row < nums.length - colOffsetFromRow; row++) {
                int col = row + colOffsetFromRow;
                System.out.println("evaluating row = " + row + " col = " + col +
                        " dp[row+1][col] = " + dp[row+1][col] + " dp[row][col-1] = " + dp[row][col-1]);
                dp[row][col] = Math.max(nums[row] - dp[row+1][col],
                                        nums[col] - dp[row][col-1]);
            }
        }
        for (int[] row : dp) {
            System.out.println(java.util.Arrays.toString(row));
        }
        return dp[0][nums.length-1] >= 0;
    }

    // O(n ^ 2) time
    // O(n) space
    private static boolean predictWinnerDP1D(int[] nums) {
        if (nums == null) return true;
        int numsLen = nums.length;
        if ((numsLen & 1) == 0) return true;
        int[] dp = new int[numsLen];

        for (int i = numsLen - 1; i >= 0; i--) {
            for (int j = i; j < numsLen; j++) {
                if (i == j) dp[i] = nums[i];
                else dp[j] = Math.max(nums[i] - dp[j], nums[j] - dp[j - 1]);
            }
        }

        return dp[numsLen - 1] >= 0;
    }

//    currently 1st with choosable start, end,
//        1.if 1st picks nums[start], 2nd can pick either ends of nums[start + 1, end]
//    a.if 2nd picks nums[start + 1], 1st can pick either ends of nums[start + 2, end]
//    b.if 2nd picks nums[end], 1st can pick either ends of nums[start + 1, end - 1]
//    since 2nd plays to maximize his score, 1st can get nums[start] + min(1.a, 1.b)
//
//        2.if 1st picks nums[end], 2nd can pick either ends of nums[start, end - 1]
//    a.if 2nd picks nums[start], 1st can pick either ends of nums[start + 1, end - 1];
//    b.if 2nd picks nums[end - 1], 1st can pick either ends of nums[start, end - 2];
//    since 2nd plays to maximize his score, 1st can get nums[end] + min(2.a, 2.b)
//
//    since the 1st plays to maximize his score, 1st can get max(nums[i] + min(1.a, 1.b), nums[j] + min(2.a, 2.b));
    private static boolean predictWinnerBruteForce2(int[] nums) {
        int scoreFirst = predictTheWinnerFrom(nums, 0, nums.length -1);
        int scoreTotal = getTotalScores(nums);

        return scoreFirst >= scoreTotal - scoreFirst;
    }

    private static int predictTheWinnerFrom(int[] nums, int start, int end) {
        if (start > end) return 0;            // game already ended, player gets 0 for this round
        if (start == end) return nums[start]; // only choice for the player is start

        int curScore = Math.max(nums[start] + Math.min(predictTheWinnerFrom(nums, start + 2, end),
                                                        predictTheWinnerFrom(nums, start + 1, end - 1)),
                                    nums[end] + Math.min(predictTheWinnerFrom(nums, start, end - 2),
                                                        predictTheWinnerFrom(nums, start + 1, end - 1)));
        return curScore;
    }

    private static int getTotalScores(int[] nums) {
        int scoreTotal = 0;
        for (int num : nums) {
            scoreTotal += num;
        }
        return scoreTotal;
    }

    private static boolean predictWinnerMemo2(int[] nums) {
        int[][] memo = buildMemo(nums.length);
        int scoreFirst = predictTheWinnerFromMemo(nums, 0, nums.length - 1, memo);
        int scoreTotal = getTotalScoresMemo(nums);
        return scoreFirst >= scoreTotal - scoreFirst;
    }

    private static int getTotalScoresMemo(int[] nums) {
        int scoreTotal = 0;
        for (int num : nums) scoreTotal += num;

        return scoreTotal;
    }

    private static int predictTheWinnerFromMemo(int[] nums, int start, int end, int[][] memo) {
        if (start > end) return 0;
        if (start == end) return nums[start];

        if (memo[start][end] != -1) return memo[start][end];

        int curScore = Math.max(nums[start] + Math.min(predictTheWinnerFromMemo(nums, start + 2, end, memo),
                                                        predictTheWinnerFromMemo(nums, start + 1, end -1, memo)),
                                nums[end] + Math.min(predictTheWinnerFromMemo(nums, start, end-2, memo),
                                                        predictTheWinnerFromMemo(nums, start + 1, end - 1, memo)));
        return memo[start][end] = curScore;
    }

    private static int[][] buildMemo(int length) {
        int[][] memo = new int[length][length];

        for (int[] memoRow : memo) java.util.Arrays.fill(memoRow, -1);
        return memo;
    }
}
