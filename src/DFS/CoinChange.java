package DFS;

/**
 * Created by leiwang on 3/26/18.
 */

public class CoinChange {
    public static void main(String[] args) {
        int[] coins = new int[]{1,5,10,25};
        System.out.println("findCoins return " + findCoinChange(0, coins, 10));
        System.out.println("findCoins change " + findCoinChange(coins, 10));
        int[] coins2 = new int[]{25,10,5,1};
        System.out.println("make Change finally returns " + makeChange(coins2,10));
    }

    /**
     *
     * @param n total value
     * @param d coin value
     * @return
     */
    public static int makeChange(int n, int d) {
        if (n < 0)
            return 0;
        else if (n == 0)
            return 1;
        else {
            int sum = 0;
            switch (d) {
                case 25: sum += makeChange(n-25,25);
                    break;
                case 10: sum += makeChange(n-10,10);
                    break;
                case 5: sum += makeChange(n-5,5);
                    break;
                case 1: sum ++;
                    break;
            }
            return sum;
        }
    }
    /**
     * Top Down Approach
     * @param coinidx
     * @param coins
     * @param target
     * @return
     */
    private static int findCoinChange(int coinidx, int[] coins, int target) {
        if (target == 0)
            return 0;
        if (coinidx < coins.length && target > 0) {
            // for this specific target, how many coins do I need for coinidx?
            int maxVal = target / coins[coinidx];
            int minCost = Integer.MAX_VALUE;

            // for each coin
            for (int x = 0; x <= maxVal; x++) {
                // if the coin is less or equal to target
                if (target >= x * coins[coinidx]) {
                    int res = findCoinChange(coinidx + 1, coins, target - x * coins[coinidx]);
                    if (res != -1)
                        minCost = Math.min(minCost, res + x);

                }
            }
            return (minCost == Integer.MAX_VALUE)? -1: minCost;

        }
        return -1;
    }

    private static int makeChange(int[] coins, int amount) {
        if (coins != null && coins.length > 0 && amount >= 0) {
            return makeChange(coins, amount, 0,0);
        }
        return 0;
    }

    /**
     * Note that the assumption is the coins are sorted from largest to smallest
     * also assume there are infinite number of coins for 1,5,10,25
     * @param coins
     * @param amount
     * @param currentIndex
     * @return
     */
    private static int makeChange(int[] coins, int amount, int currentIndex,int depth) {
        int nextCoinIndex;
        System.out.println(getDepthString(depth) + "remaining amount is " + amount);
        if (currentIndex < coins.length-1) {
            nextCoinIndex = currentIndex + 1;
            System.out.println(getDepthString(depth) + "increment coin index to " + nextCoinIndex);
        } else {
            System.out.println(getDepthString(depth) + "returning current coin " + coins[currentIndex] + " current Index = " + currentIndex);
            return coins[currentIndex];
        }

        int res = 0;
        for (int numberOfCoinsForCurrentIndex = 0;
             numberOfCoinsForCurrentIndex * coins[currentIndex] <= amount;
             numberOfCoinsForCurrentIndex++) {
            System.out.println(getDepthString(depth) + "making changes with " + coins[currentIndex] +
                               " with the number of coins = " + numberOfCoinsForCurrentIndex);
            res += makeChange(coins,
                              amount - numberOfCoinsForCurrentIndex * coins[currentIndex],
                              nextCoinIndex,
                              depth + 1);
            System.out.println(getDepthString(depth) + "return from recursion making changes with " + coins[currentIndex] +
                    " with the number of coins = " + numberOfCoinsForCurrentIndex + " res = " + res);
        }
        return res;
    }

    private static String getDepthString(int depth) {
        StringBuilder str = new StringBuilder();
        for (int index = 0; index < depth; index++) {
            str.append("    ");
        }
        return str.toString();
    }

    /**
     * Bottom up approach
     * @param coins
     * @param target
     * @return
     */
    private static int findCoinChange (int[] coins, int target) {
        int max = target + 1;
        int[] dp = new int[target+1];
        for (int i = 0; i < dp.length; i ++) {
            dp[i] = max;
        }
        dp[0] = 0;

        // for each number below target, gather coins to add up
        for (int i = 1; i <= target; i++) {
            // for each coin check to see if we can add up to the value
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[target] > target ? -1 : dp[target];
    }

}
