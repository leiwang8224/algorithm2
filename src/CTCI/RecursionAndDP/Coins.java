package CTCI.RecursionAndDP;

/**
 * Given an infinite number of quarters, dimes, nickels (25, 10, 5) and pennies (1)
 * write code to calculate the number of ways of representing n cents.
 */
public class Coins {
    public static void main(String[] args) {

    }

    /**
     * Let's say n = 100. We want to compute the number of ways of making changes
     * for 100 cents. What is the relationship between this problem and its
     * subproblems?
     * We know that making change for 100 cents will involve either 0, 1, 2, 3 or 4 quarters.
     * So:
     *  makeChange(100) = makeChange(100 using 0 quarters) +
     *                    makeChange(100 using 1 quarters) +
     *                    makeChange(100 using 2 quarters) +
     *                    makeChange(100 using 3 quarters) +
     *                    makeChange(100 using 4 quarters)
     * Inspecting this further, we can see that some of these problems reduce.
     * For example, makeChange(100 using 1 quarter) will equal makeChange(75 using 0 quarters)
     * This is because, if we must use exactly one quarter to make change for 100 cents, then
     * our only remaining choices involve making change for the remaining 75 cents.
     *
     * We can apply the same logic to makeChange(100 using 2 quarters), makeChange(100 using
     * 3 quarters) and makeChange(100 using 4 quarters). We have thus reduced the above statement
     * to the following:
     * makeChange(100) = makeChange(100 using 0 quarters) +
     *                   makeChange(75 using 0 quarters) +
     *                   makeChange(50 using 0 quarters) +
     *                   makeChange(25 using 0 quarters) + 1
     * Note the final statement from above, makeChange(100 using 4 quarters), equals 1.
     * We call this 'fully reduced'
     *
     * Now that we have used up all our quarters, so now we can start applying our next biggest
     * denomination: dimes.
     * Our approache for quarters applies to dimes as well, but we apply this for each
     * of the four of five parts of the above statement.
     * So, for the first part, we get the following:
     * makeChange(100 using 0 quarters) = makeChange(100 using 0 quarters, 0 dimes) +
     *                                    makeChange(100 using 0 quarters, 1 dimes) +
     *                                    makeChange(100 using 0 quarters, 2 dimes) +
     *                                    ...
     *                                    makeChange(100 using 0 quarters, 10 dimes)
     *
     * makeChange(75 using 0 quarters) = makeChange(75 using 0 quarters, 0 dimes) +
     *      *                             makeChange(75 using 0 quarters, 1 dimes) +
     *      *                             makeChange(75 using 0 quarters, 2 dimes) +
     *      *                             ...
     *      *                             makeChange(75 using 0 quarters, 7 dimes)
     *
     * makeChange(50 using 0 quarters) = makeChange(50 using 0 quarters, 0 dimes) +
     *      *                            makeChange(50 using 0 quarters, 1 dimes) +
     *      *                            makeChange(50 using 0 quarters, 2 dimes) +
     *      *                            ...
     *      *                            makeChange(50 using 0 quarters, 5 dimes)
     *      *
     * makeChange(25 using 0 quarters) = makeChange(25 using 0 quarters, 0 dimes) +
     *      *                             makeChange(25 using 0 quarters, 1 dimes) +
     *      *                             makeChange(25 using 0 quarters, 2 dimes)
     *
     * Each one of these, in turn, expands out once we start applying nickels. We end up
     * with a tree like recursive structure where each call expands out to four or more calls.
     *
     * The base case of our recursion is the fully reduced statement. For example, makeChange(50
     * using 0 quarters, 5 dimes) is fully reduced to 1, since 5 dimes equals 50 cents.
     *
     */
    /**
     *
     * @param nCents: total amount we are trying to achieve
     * @param denoms: each different denominations (5, 10, 25, 1)
     * @param index: index of the coins
     * @return
     */
    private static int numWays(int nCents, int[] denoms, int index) {
        // if we reach the index of last denom, return 1 (the num of coin)
        if (index >= denoms.length - 1) return 1;  // last denom


        int amountFromCurrentIndex = denoms[index];
        int ways = 0;
        // recursion takes care of for each coin amount...
        // for loop takes care of how many of each coin
        for (int numOfCurrentCoin = 0;
             numOfCurrentCoin * amountFromCurrentIndex <= nCents;
             numOfCurrentCoin++) {
            int amountRemain = nCents - numOfCurrentCoin * amountFromCurrentIndex;
            ways += numWays(amountRemain, denoms, index+1);
        }
        return ways;
    }

    /**
     * More efficient is to store a mapping from each pair (amount, index)
     * to precomputed result. Or storing the previously computed results.
     * @param n
     * @return
     */
    private static int makeChangeMoreEfficient(int n) {
        int[] denoms = {25, 10, 5, 1};
        int[][] map = new int[n+1][denoms.length]; // precomputed vals
        return makeChange(n, denoms, 0, map);
    }

    private static int makeChange(int amount, int[] denoms, int index, int[][] map) {
        if (map[amount][index] > 0) {
            // retrieve value
            return map[amount][index];
        }

        if (index >= denoms.length - 1) return 1; // one denom remaining
        int denomAmount = denoms[index];
        int ways = 0;

        for (int i = 0; i * denomAmount <= amount; i++) {
            // go to next denom, assuming i coins of denomAmount
            int amountRemain = amount - i * denomAmount;
            ways += makeChange(amountRemain, denoms, index+1, map);
        }
        map[amount][index] = ways;
        return ways;
    }


}
