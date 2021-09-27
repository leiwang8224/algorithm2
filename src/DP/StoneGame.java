package DP;

public class StoneGame {
    public static void main(String[] args) {
        int[] piles = new int[]{3,9,1,2};
        System.out.println(stoneGame(piles));
        System.out.println(stoneGame1D(piles));
        System.out.println(stoneGame2(piles));
        System.out.println(stoneGameMemo(piles));
    }

//    dp[i][j] means the biggest number of stones you can get more than opponent
//    picking piles in piles[i] ~ piles[j]. You can first pick piles[i] or piles[j].
//
//    If you pick piles[i], your result will be piles[i] - dp[i + 1][j]
//    If you pick piles[j], your result will be piles[j] - dp[i][j - 1]
//
//    So we get:
//    dp[i][j] = max(piles[i] - dp[i + 1][j], piles[j] - dp[i][j - 1])
//    We start from smaller subarray and then we use that to calculate bigger subarray.
//    dp[i][j] actually means maximum(alex stone - lee stone) and maximum(lee stone - alex stone)
//    alternatively, depending on who picks from piles[i]~piles[j] first.
//    If alex picks from piles[i]~piles[j] first, then dp[i][j] means maximum(alex stone - lee stone);
//    If Lee pick from piles[i]~piles[j] first, then dp[i][j] means maximum(lee stone - alex stone) .
    // Ex: given [3,9,1,2]
    //     1     2     3     4
    // 1 (3,0) (9,3) (4,9) (11,4)
    // 2       (9,0) (9,1) (10,2)
    // 3             (1,0) (2,1)
    // 4                   (2,0)
    //______________________________
    //     1     2     3     4
    // 1   3     6    -5     7
    // 2   0     9     8     8
    // 3   0     0     1     1
    // 4   0     0     0     2
    private static boolean stoneGame(int[] piles) {
        int n = piles.length;
        int[][] dp  = new int[n][n];
        for (int i = 0; i < piles.length; i++)
            dp[i][i] = piles[i];
        // two for loops to iterate from the start and end of the array
        // startIndex from left side and endIndex from right side
        for (int offsetFromStart = 1; offsetFromStart < piles.length; offsetFromStart++) {
            for (int startIndex = 0; startIndex < piles.length - offsetFromStart; startIndex++) {
                int endIndex = startIndex + offsetFromStart;
                System.out.println("startIndex = " + startIndex + " endIndex = " + endIndex);
                // take max of picking beginning and end row
                System.out.println("taking max("+ piles[startIndex] + "-" + dp[startIndex+1][endIndex] + ","
                + piles[startIndex+offsetFromStart] + "-" + dp[startIndex][endIndex-1] + ")");

                // take the max of picking left side and picking right side
                // leftSide = pickLeft - otherPlayerSelection
                // rightSide = pickRight - otherPlayerSelection
                dp[startIndex][endIndex] = Math.max(piles[startIndex] - dp[startIndex + 1][endIndex],
                                                    piles[endIndex] - dp[startIndex][endIndex - 1]);
            }
        }
        for (int[] line : dp) {
            System.out.println(java.util.Arrays.toString(line));
        }
        // return if first row last col > 0
        return dp[0][n - 1] > 0;
    }

    //1D array = [7, 8, 1, 2]
    private static boolean stoneGame1D(int[] piles) {
        int[] dp = java.util.Arrays.copyOf(piles, piles.length);;
        for (int offset = 1; offset < piles.length; offset++)
            for (int index = 0; index < piles.length - offset; index++)
                dp[index] = Math.max(piles[index] - dp[index + 1],
                                     piles[index + offset] - dp[index]);
        System.out.println("1D array = " + java.util.Arrays.toString(dp));
        return dp[0] > 0;
    }

    private static boolean stoneGame2 (int[] piles) {
        int N = piles.length;

        // dp[i+1][j+1] = the value of the game [piles[i], ..., piles[j]].
        int[][] dp = new int[piles.length+2][piles.length+2];
        for (int size = 1; size <= piles.length; ++size)
            for (int startIndex = 0; startIndex + size <= piles.length; ++startIndex) {
                int endIndex = startIndex + size - 1;
                int parity = (endIndex + startIndex + N) % 2;  // j - i - N; but +x = -x (mod 2)
                if (parity == 1)
                    dp[startIndex+1][endIndex+1] = Math.max(piles[startIndex] + dp[startIndex+2][endIndex+1],
                                                            piles[endIndex] + dp[startIndex+1][endIndex]);
                else
                    dp[startIndex+1][endIndex+1] = Math.min(-piles[startIndex] + dp[startIndex+2][endIndex+1],
                                                            -piles[endIndex] + dp[startIndex+1][endIndex]);
            }

        return dp[1][N] > 0;
    }

//    This is a Minimax problem. Each player plays optimally to win, but you can't greedily choose
//    the optimal strategy so you have to try all strategies, this is DP now.
//
//    What does it mean for Alex to win? Alex will win if score(Alex) >= score(Lee), but this also
//    means score(Alex) - score(Lee) >= 0, so here you have a common parameter which is a score variable.
//    The score parameter really means score = score(Alex) - score(Lee).
//
//    Now, if each player is suppoed to play optimally, how do you decide this with one variable?
//
//    Well since Alex is playing optimally, he wants to maximize the score variable because remember,
//    Alex only wins if score = score(Alex) - score(Lee) >= 0 Alex should add to the score because he
//    wants to maximize it.
//    Since Lee is also playing optimally, he wants to minimize the score variable, since if the score
//    variable becomes negative, Lee has more individual score than Alex. But since we have only one
//    variable, Lee should damage the score (or in other words, subtract from the score).
//
//    Now, let's think of the brute force solution. You are at the current state, say this is Alex's turn.
//    Alex can choose either left or right, but he can't greedily pick so you try both of them,
//    this is O(2^n) for runtime.
//
//    But realize the state you are in. You can easily memoize the this, the varying parameters are
//    l, r, ID, where ID is the player ID (1 for Alex, 0 for Lee), hence you can make a DP/Cache
//    table and return answer if you have discovered the state.
//
//    Finally, in your main function you call this helper function and check if you were able to get a score >= 0.
    public static boolean stoneGameMemo(int[] piles) {
        // needs piles.length+1 since we are starting from the 0 index
        Integer[][][] memo = new Integer[piles.length + 1][piles.length + 1][2];

        return helper(0, piles.length - 1, piles, 1, memo) >= 0;
    }

    public static int helper(int leftIndex, int rightIndex, int [] piles, int ID, Integer[][][] memo){
        if(rightIndex < leftIndex)
            return 0;
        if(memo[leftIndex][rightIndex][ID] != null)
            return memo[leftIndex][rightIndex][ID];

        int nextPlayerID = Math.abs(ID - 1);
        if(ID == 1) // take max of picking left and picking right
            memo[leftIndex][rightIndex][ID] = Math.max(piles[leftIndex] + helper(leftIndex + 1, rightIndex, piles, nextPlayerID, memo),
                                    piles[rightIndex] + helper(leftIndex, rightIndex - 1, piles, nextPlayerID, memo));
        // the other player (ID=0) will try to subtract for your score so use negative sign for the pick
        else
            memo[leftIndex][rightIndex][ID] = Math.min(-piles[leftIndex] + helper(leftIndex + 1, rightIndex, piles, nextPlayerID, memo),
                                    -piles[rightIndex] + helper(leftIndex, rightIndex - 1, piles, nextPlayerID, memo));

        for (Integer[][] line : memo) {
            for (Integer[] innerLine : line) {
                System.out.println(java.util.Arrays.toString(innerLine));
            }
        }
        return memo[leftIndex][rightIndex][ID];
    }
}
