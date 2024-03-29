package DFS;

/**
 * Created by leiwang on 3/26/18.
 */

public class CoinChange {
    public static void main(String[] args) {
        int[] coins = new int[]{1,5,10,25};
        System.out.println("findCoins return " + findCoinChange(0, coins, 10, 0));
        System.out.println("findCoins change " + findCoinChange(coins, 10));
        int[] coins2 = new int[]{25,10,5,1};
        System.out.println("make Change finally returns " + makeChange(coins2,10));
        System.out.println("make Change2 finally returns " + makeChange2(coins2, 10));
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
    private static int findCoinChange(int coinidx, int[] coins, int target, int depth) {
        if (target == 0)
            return 0;
        if (coinidx < coins.length && target > 0) {
            // for this specific target, how many coins do I need for coinidx?
            int maxNumOfCoins = target / coins[coinidx];
            int minCost = Integer.MAX_VALUE;

            // for each coin
            for (int numberOfCoins = 0; numberOfCoins <= maxNumOfCoins; numberOfCoins++) {
                // if the coin is less or equal to target
                if (numberOfCoins * coins[coinidx] <= target) {
                    System.out.println(getDepthString(depth) + "calling recursion coin = " + coins[coinidx] + " target = " + target);
                    int res = findCoinChange(coinidx + 1, coins, target - numberOfCoins * coins[coinidx], depth+1);
                    System.out.println(getDepthString(depth) + "returning recursion coin = " + coins[coinidx] + " target = " + target);
                    if (res != -1)
                        minCost = Math.min(minCost, res + numberOfCoins);

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
     * @param curCoinIdx
     * @return
     */
    private static int makeChange(int[] coins, int amount, int curCoinIdx,int depth) {
        int nextCoinIndex;
        System.out.println(getDepthString(depth) + "remaining amount is " + amount);
        // if current index is not at the end increment one
        if (curCoinIdx < coins.length-1) {
            nextCoinIndex = curCoinIdx + 1;
            System.out.println(getDepthString(depth) + "increment coin index to " + nextCoinIndex);
        } else { // else we are at the end, return 1 for one cent
            // at the last coin which is 1 cent, return that 1 cent
            System.out.println(getDepthString(depth) + "returning current coin amount " + coins[curCoinIdx] + " current Index = " + curCoinIdx);
            return 1;
//            return coins[currentIndex]; // if the last coin is not one cent use this statement
        }

        int res = 0;
        for (int numberOfCoinsForCurrentIndex = 0;
             numberOfCoinsForCurrentIndex * coins[curCoinIdx] <= amount;
             numberOfCoinsForCurrentIndex++) {
            System.out.println(getDepthString(depth) + "call recursion making changes with " + coins[curCoinIdx] +
                               " with the number of coins = " + numberOfCoinsForCurrentIndex + " res = " + res +
                               " currentIdx = " + curCoinIdx + " nextIdx = " + nextCoinIndex);
            res += makeChange(coins,
                              amount - numberOfCoinsForCurrentIndex * coins[curCoinIdx],
                              nextCoinIndex,
                              depth + 1);
            System.out.println(getDepthString(depth) + "return from recursion making changes with " + coins[curCoinIdx] +
                    " with the number of coins = " + numberOfCoinsForCurrentIndex + " res = " + res + " currentIdx = " + curCoinIdx
                               + " nextIdx = " + nextCoinIndex);
        }
        return res;
    }

    private static int makeChange2(int[] coins, int amount) {
        if (coins.length == 0 || amount <= 0) {
            return 0;
        }

        // separate the first coin and the rest
        int firstCoin = coins[0];

        // rest of the coins
        int[] otherCoins = java.util.Arrays.copyOfRange(coins, 1, coins.length);

        // add one way to make change if the first coin is already the amount
        if (firstCoin == amount) {
            return 1 + makeChange2(otherCoins, amount);
            // else if the first coin is > amount, recurse on the rest of the coins
        } else if (firstCoin > amount) {
            return makeChange2(otherCoins, amount);
        } else {
            // else the first coin is < amount, recurse on the rest of the amount +
            // the number of ways to use rest of the coins to make the change
            return makeChange2(coins, amount - firstCoin) + makeChange2(otherCoins, amount);
        }
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

        // populate initial array with values target+1
        // first element is 0 because there is 0 ways to come up with target 0
        for (int i = 0; i < dp.length; i ++) {
            dp[i] = max;
        }
        dp[0] = 0;

        // for each number below target, gather coins to add up
        for (int eachNumBelowTarget = 1; eachNumBelowTarget <= target; eachNumBelowTarget++) {
            // for each coin check to see if we can add up to the value
            for (int coinIdx = 0; coinIdx < coins.length; coinIdx++) {
                if (coins[coinIdx] <= eachNumBelowTarget) {
                    dp[eachNumBelowTarget] = Math.min(dp[eachNumBelowTarget], dp[eachNumBelowTarget - coins[coinIdx]] + 1);
                }
            }
        }

        if (dp[target] > target) return -1;
        else return dp[target];
    }
}
