package DP;

import java.util.*;

/**
 * Created by leiwang on 3/26/18.
 */

//1) Optimal Substructure
//        To count total number solutions, we can divide all set solutions in two sets.
//        1) Solutions that do not contain mth coin (or Sm).
//        2) Solutions that contain at least one Sm.
//        Let count(S[], m, n) be the function to count the number of solutions, then
//        it can be written as sum of count(S[], m-1, n) and count(S[], m, n-Sm).
//
//        Therefore, the problem has optimal substructure property as the problem
//        can be solved using solutions to subproblems.
public class CoinChange {
    public static void main(String args[]) {
        int[] coins = new int[]{1,5,10,25};
        System.out.println(findCoinChange(coins, 16));                // returns 3
        System.out.println(findCoinChange(coins, coins.length, 16));  // returns 6
        System.out.println(findCoinChangeDP(coins, coins.length, 16));// returns 6

        // formatting the string outputs
        Map<Integer,String > names = new HashMap<>();
        names.put(1,"pennies");
        names.put(5,"nickels");
        names.put(10,"dimes");
        names.put(25,"quarters");

        // keep track of the current amounts of each type of coin
        Map<String, Integer> curr = new HashMap<>();
        curr.put(names.get(1),0);
        curr.put(names.get(5),0);
        curr.put(names.get(10),0);
        curr.put(names.get(25),0);
        findCoinsPrintAll(16,0,coins,names,curr);
        reverseOrderCoins(coins);
        printCombination(coins,new int[coins.length],0,16);
    }

    private static int findCoinChange(int[] coins, int length, int target) {
        // if length is 0 then there is 1 solution
        // do not include any coin
        if (target == 0)
            return 1;

        // if length is less than 0 then no
        // solution exists
        if (target < 0)
            return 0;

        // if there are no coins and length
        // is greater than 0, then no solution exists
        if (length <= 0 && target >= 1)
            return 0;

        // count is sum of solutions (i)
        // including S[m-1] excluding S[m-1]
        return findCoinChange(coins, length -1, target) +
               findCoinChange(coins, length, target - coins[length-1]);
    }

    private static int findCoinChangeDP(int[] coins, int length, int target) {
        int[] dp = new int[target + 1];
        java.util.Arrays.fill(dp, 0);

        // base case (if given value is 0)
        dp[0] = 1;

        // pick all coins one by one and update the table[]
        // values after the index greater than or equal to
        // the value of the picked coin
        for (int i=0; i<length; i++) {
            for (int j = coins[i]; j <= target; j++) {
                dp[j] += dp[j-coins[i]];
            }
        }
        System.out.println("findCoinChangeDP " + java.util.Arrays.toString(dp));
        return dp[target];
    }

    /**
     * Not using repeated coins
     * @param coins
     * @param target
     * @return
     */
    private static int findCoinChange(int[] coins, int target) {
        if (target < 1) return 0;
        return coinChange(coins, target, new int[target]);

    }

    private static int coinChange(int[] coins, int target, int[] dp) {
        if (target < 0) return -1;
        if (target == 0) return 0;
        if (dp[target-1] != 0) return dp[target-1];

        int min = Integer.MAX_VALUE;

        for (int coin : coins) {
            // recurse call to get what's left (target-coin)
            int res = coinChange(coins, target - coin, dp);
            if (res >= 0 && res < min) {
                min = 1 + res;
            }
        }
        dp[target - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
        System.out.println("findCoinChange " +java.util.Arrays.toString(dp));
        return dp[target -1];
    }
    
    private static void findCoinsPrintAll(int remain, int index, int[] coins, Map<Integer,String> names, Map<String,Integer> curr) {
        if (index < coins.length -1) {
            // if we have not reached our goal yet
            if (remain > 0) {
                int coinAmount = coins[index];
                if (coinAmount <= remain) {
                    // try all possible numbers of current coin given the 
                    // amount that is left
                    for (int i = 0; i <= remain / coinAmount; i ++) {
                        curr.put(names.get(coinAmount),i);
                        findCoinsPrintAll(remain - coinAmount * i, index + 1, coins, names, curr);
                    }
                    // reset the current coin amount to zero before recursing
                    curr.put(names.get(coinAmount),0);
                }
                // case when there is a coin whose value is greater than the goal
                else {
                    findCoinsPrintAll(remain, index + 1, coins, names, curr);
                }
            }
            // we have reached our goal, print out the current coin amounts
            else {
                printCurr(curr);
            }
        }else { // last type of coin
            // if we have not reached our goal value yet
            if (remain > 0) {
                int coinAmount = coins[index];
                if(coinAmount <= remain) {
                    // if the remainder of our goal is evenly divisible by our last
                    // coin value, we can make the goal amount
                    if (remain % coinAmount == 0) {
                        // add last coin amount and print current values out
                        curr.put(names.get(coinAmount), remain / coinAmount);
                        printCurr(curr);
                        // reset this coin amount to zero before recursing
                        curr.put(names.get(coinAmount),0);
                    }
                }
            } else {
                printCurr(curr);
            }
        }
    }

    private static void printCurr(Map<String, Integer> curr) {
        Iterator<String> iter = curr.keySet().iterator();
        while (iter.hasNext()) {
            String denom = iter.next();
            System.out.println(curr.get(denom) + " " + denom + " ");
        }
        System.out.println();
    }

    private static void reverseOrderCoins(int[] coins) {
        int begin = 0;
        int end = coins.length -1;
        while (begin < end) {
            int copy = coins[begin];
            coins[begin] = coins[end];
            coins[end] = copy;
            begin++;
            end--;
        }
    }
    private static void printCombination(int[] coins, int[] counts, int startIndex, int totalAmount) {
        if (startIndex >= coins.length) {
            // format the print out as amount = ?*25 + ?*10...
            for (int i = 0; i < coins.length; i++) {
                System.out.println("" + counts[i] + " * " + coins[i] + " + ");
            }
            System.out.println();
            return;
        }
        // if startIndex is the last one, we need to check if it can be divisible
        // by the smallest coin, if so, this is a good combo
        if (startIndex == coins.length - 1) {
            if (totalAmount % coins[startIndex] == 0) {
                // set the counts of coins at start index
                counts[startIndex] = totalAmount / coins[startIndex];
                // recurse
                printCombination(coins, counts, startIndex + 1, 0);
            }
        } else {
            // still have option to choose 0-N larger coins
            for (int i = 0; i <= totalAmount / coins[startIndex]; i ++) {
                // for every cycle in the loop, choose arbitrary number
                // of larger coins and proceed next
                counts[startIndex] = i;
                // update the remaining amount
                printCombination(coins, counts, startIndex + 1, totalAmount - coins[startIndex] * i);
            }
        }
    }
}
