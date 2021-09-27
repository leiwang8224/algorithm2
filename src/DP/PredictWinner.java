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
        return getOpponentMaxScore(nums, 0, nums.length -1, 1) >= 0;
    }

    private static int getOpponentMaxScore(int[] nums, int startIndex, int endIndex, int turn) {
        if (startIndex == endIndex) return turn * nums[startIndex];

        // pick start and recurse, (start +1) or end will be selected by opponent
        // apply negative sign for turn to indicate opponent's turn
        int pickStartIndex = turn * nums[startIndex] + getOpponentMaxScore(nums, startIndex + 1, endIndex, -turn);
        // pick end and recurse, (end-1) or start will be selected by opponent
        int pickEndIndex = turn * nums[endIndex] + getOpponentMaxScore(nums, startIndex, endIndex-1, -turn);
        // this represent max(a, b) for player 1 and min(a,b) for player 2
        return turn * Math.max(turn * pickStartIndex, turn * pickEndIndex);
    }

    public static boolean predictWinnerBruteForce3(int[] nums) {
        // find if your play will end up with a score greater than or equal to 0
        return getOpponentMaxScore2(nums, 0, nums.length-1) >= 0;
    }

    private static int getOpponentMaxScore2(int[] nums, int left, int right) {
        if (left == right) return nums[left];
        return Math.max(nums[left] - getOpponentMaxScore2(nums, left + 1, right),
                        nums[right] - getOpponentMaxScore2(nums, left, right-1));
    }

    // O(n ^ 2) time
    // O(n ^ 2) memory
    // concept of memo[start][end] represent the max sum we can get if the array
    // starts from start and ends to end
    private static boolean predictWinnerMemo(int[] nums) {
        Integer[][] memo = new Integer[nums.length][nums.length];
        // if the result is greater than 0 then I have more score than my opponent
        // this essentially returns the difference in score between me and my opponent
        return getOpponentMaxScoreMemo(nums, 0, nums.length -1, memo) >= 0;
    }

    // lose the concept of the turn

    /**
     * 1. if start is equal to end then return one of the elements
     * 2. if memo is not null return that element
     * 3. calculate how much more score I get if I pick Left
     * 4. calculate how much more score I get if I pick right
     * 5. take max of the two and store in memo
     * 6. return memo element
     */
    private static int getOpponentMaxScoreMemo(int[] nums, int start, int end, Integer[][] memo) {
        // no longer need to calculate, just return
        if (start == end) return nums[end]; //only one choice left to choose from
        if (memo[start][end] != null) return memo[start][end];

        int diffScoreIfIPickStart = nums[start] - getOpponentMaxScoreMemo(nums, start + 1, end, memo);
        int diffScoreIfIPickEnd = nums[end] - getOpponentMaxScoreMemo(nums, start, end -1, memo);
        // keep track of the max score the first player can earn by playing the either sides of the array
        memo[start][end] = Math.max(diffScoreIfIPickStart, diffScoreIfIPickEnd);
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
        int[][] dpMaxScore = new int[nums.length][nums.length];
        for (int index = 0; index < nums.length; index++) {
            // populate diagonals with the numbers from the array
            dpMaxScore[index][index] = nums[index];
        }
        for (int windowSize = 1; windowSize < nums.length; windowSize++) {
            for(int startIndex = 0; startIndex < nums.length - windowSize; startIndex++) {
                int endIndex = startIndex + windowSize;
                // take the max of how much more score I needed from start to end than the other player
                // dp[start + 1][end] already saves how much more score that
                // the first-in-action player will get from start + 1 to end than the second player.
                // So if player A picks position start, eventually player A will get
                // nums[start] - dp[start + 1][end] more score than player B after they pick up all numbers.
                // dpMaxScore[startIndex+1][endIndex] and dpMaxScore[startIndex][endIndex-1] are max score for opponent
                dpMaxScore[startIndex][endIndex] = Math.max(nums[startIndex] - dpMaxScore[startIndex+1][endIndex],
                                        nums[endIndex] - dpMaxScore[startIndex][endIndex-1]);
            }
        }
        return dpMaxScore[0][nums.length-1] >= 0;
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
