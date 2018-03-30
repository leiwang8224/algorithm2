package Recursion;

/**
 * Created by leiwang on 3/27/18.
 */
public class PrintCombinationsCoinChange {
    public static void main(String args[]) {
        int[] coins = new int[] {25,10,5,1};
        java.util.Arrays.sort(coins);
        printCombinations(coins, new int[coins.length], 25, 0);
    }

    /**
     *
     * @param coins sorted descending order, largest in front
     * @param counts record the number of coins at certain location
     * @param target target to sum up to
     * @param index start index for keep track of from which coin
     *              we start processing after choosing
     *              one larger coin amount
     */
    private static void printCombinations(int[] coins, int[] counts, int target, int index) {
        if (index >= coins.length) {
            System.out.print("" + target + "=");
            for (int i = 0; i < coins.length; i++) {
                System.out.print("" + counts[i] + "*" + coins[i] + "+");
            }

            System.out.print("\n");
            return;
        }

        // if the index is the last one, we need to check if target sum is divisible by the last coin
            if (index == coins.length-1) {
                if (target%coins[index] == 0) { // good combination
                    // set the counts of coins at start index
                    counts[index] = target/coins[index];
                    printCombinations(coins,counts,0,index + 1);
                }
            }
         else { // we still have option to choose 0-N larger coins
            for (int i = 0; i <= target/coins[index]; i ++) {
                // for every cycle in a loop, we choose an arbitray number
                // of larger coins and proceed next
                counts[index] = i;
                printCombinations(coins, counts, target - coins[index] * i, index + 1);
            }
        }
    }
}
