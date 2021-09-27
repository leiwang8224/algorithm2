package AlgoExpert.Medium;
import java.util.*;
public class MinNumberCoinsForChange {
    public static void main(String[] args) {

    }

    // O(nd) time | O(n) space
    /**
     * Use dp to keep track of the number of coins needed for each denom from denoms[]
     * build up the solution from the smallest denom
     */
    public static int minNumberCoinsForChange(int n, int[] denoms) {
        // setup the dp to be length n + 1
        int[] numCoinsDP = new int[n+1];
        // fill dp array with highest values since we will need to compare against
        java.util.Arrays.fill(numCoinsDP, Integer.MAX_VALUE);
        // there are 0 coins need to make 0 dollars
        numCoinsDP[0] = 0;
        // leftOver is used to keep track the left over amount that is needed
        // to add up to the total amount
        int numCoinsNeeded = 0;
        for (int curDenom : denoms) {
            for (int amount = 0; amount < numCoinsDP.length; amount++) {
                if (curDenom <= amount) {
                    // calculate the previous amount of coins needed
                    if (numCoinsDP[amount - curDenom] == Integer.MAX_VALUE) {
                        // if the last minCoin was invalid, then set the current minCoin to be invalid
                        numCoinsNeeded = numCoinsDP[amount - curDenom];
                    } else { // add one coin if and only if the pervDenom was valid
                        // there is valid number of coins located at
                        // numCoins[amount - denom] so add one coin to that
                        // the one coin amounts to denom
                        numCoinsNeeded = numCoinsDP[amount - curDenom] + 1;
                    }
                    // take the min of the current number of coins needed
                    // and the calculated number coins needed
                    numCoinsDP[amount] = Math.min(numCoinsDP[amount], numCoinsNeeded);
                }
            }
        }

        // return the last element in the dp array or -1 if it's not possible
        return numCoinsDP[n] != Integer.MAX_VALUE ? numCoinsDP[n] : -1;
    }

    public static int minNumberOfCoinsForChange(int n, int[] denoms) {
        int[][] dp = new int[denoms.length+1][n+1];
        for (int col = 0; col < dp[0].length; col++) {
            dp[0][col] = Integer.MAX_VALUE;
        }

        for (int denomIdx = 1; denomIdx < dp.length; denomIdx++) {
            int curDenom = denoms[denomIdx-1];
            for (int amount = 1; amount < dp[0].length; amount++) {
                // only set if coin amount <= total amount AND the previous amount is not MAX_VALUE
                if (curDenom <= amount && dp[denomIdx][amount-curDenom] != Integer.MAX_VALUE) {
                    // take min(take prev coin, take cur coin)
                    // where prev num coin = dp[denomIdx-1][amount]
                    // cur num coin = dp[denomIdx][amount-curDenom]+1
                    dp[denomIdx][amount] = Math.min(dp[denomIdx][amount - curDenom]+1, dp[denomIdx-1][amount]);
                } else {
                    dp[denomIdx][amount] = dp[denomIdx-1][amount];
                }
            }
        }
        // Write your code here.
        return dp[denoms.length][n] == Integer.MAX_VALUE ? -1 : dp[denoms.length][n];
    }

    public static int minNumberOfCoinsForChangeMySol(int n, int[] denoms) {
        int[][] dp = new int[denoms.length+1][n+1];

        for (int i = 1; i < dp[0].length; i++) {
            dp[0][i] = Integer.MAX_VALUE;
        }

        for (int denomIdx = 1; denomIdx < dp.length; denomIdx++) {
            int curDenom = denoms[denomIdx-1];
            for (int amount = 1; amount < dp[0].length; amount++) {
                if (curDenom <= amount) {
                    if (dp[denomIdx][amount - curDenom] != Integer.MAX_VALUE) // compare current num coins with prev number coins
                        dp[denomIdx][amount] = Math.min(dp[denomIdx][amount - curDenom] + 1, dp[denomIdx-1][amount]);
                    else
                        dp[denomIdx][amount] = dp[denomIdx-1][amount];
                } else {
                    dp[denomIdx][amount] = dp[denomIdx-1][amount];
                }
            }
        }
        // Write your code here.
        return dp[denoms.length][n] == Integer.MAX_VALUE ? -1 : dp[denoms.length][n];
    }
}


