package AlgoExpert.Medium;

public class NumberOfWaysToMakeChange {
    public static void main(String[] args) {

    }

    // O(nd) time | O(n) space
    /**
     * Build up the ways arrays starting from array index 0 (ways[0] = 1)
     * iterate from 1 to n+1 for amount, check if current denom is <= amount
     * set the ways[amount] = ways[amount] + ways[amount - denom]
     * Basically iterate through denoms and for each denom iterate through amount
     * the amount array gets overwritten for every denomination
     * return the n-th index element as the solution
     * See CoinChange.java for more insights
     */
    // if denom <= amount:
    //      ways[amount] += ways[amount-denom]
    private static int numberWays(int n, int[] denoms) {
        int[] ways = new int[n+1];
        ways[0] = 1;

        for (int denom : denoms) {
            // ways array gets updated for every denomination
            for (int amount = 1; amount < n + 1; amount++) {
                if (denom <= amount) {
                    // ways[amount] is from previous calculation
                    // added to the element at amount-denom
                    ways[amount] += ways[amount - denom];
                }
            }
        }
        return ways[n];
    }
}
