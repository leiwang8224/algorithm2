package CTCI.RecursionAndDP;

import java.util.Arrays;

/**
 * A child is running up a staircase with n steps and can hop either
 * 1 step, 2 step or 3 steps at a time. Implement a method to count
 * how many possible ways the child can run up the stairs.
 */
public class TripleStep {
    public static void main(String[] args) {
        System.out.println(howManyStepsRecurse(3));
        System.out.println(countWays(3));

    }

    /**
     * The one tricky bit is defining the base case. If we have 0 steps to go
     * (we are currently standing on the step), are there zero paths to that
     * step or one path?
     * We could define it either way, there is no right answer.
     * However, it's easier to define it as 1. If defined as 0, then there would
     * need to be additional base cases.
     * @param n
     * @return
     */
    static int howManyStepsRecurse(int n) {
        if (n < 0) return 0;
        else if (n == 0) return 1;
        else return howManyStepsRecurse(n - 1) + howManyStepsRecurse(n - 2) + howManyStepsRecurse(n - 3);
    }

    static int countWays(int n) {
        int[] memo = new int[n+1];
        Arrays.fill(memo, -1);
        return countWays(n, memo);
    }

    /**
     * Essentially if we have seen this value n before, return the cached value.
     * Each time we compute a fresh value, add it to the cache.
     * Typically we use a HashMap<Integer, Integer> for a cache. In this case,
     * the keys will be exactly 1 through n. It's more compact to use an integer array.
     *
     * Note that the number of ways will quickly overflow the bounds of an integer.
     * By the time you hit n = 37, the result has already overflowed. Using a long
     * will help but not completely resolve the issue. BigInteger class is another alternative.
     * @param n
     * @param memo
     * @return
     */
    static int countWays(int n, int[] memo) {
        if (n < 0) return 0;
        // one way for reaching 0 steps
        else if (n == 0) return 1;
        else if (memo[n] > -1) return memo[n];
        else {
            memo[n] = countWays(n - 1, memo) +
                      countWays(n - 2, memo) +
                      countWays(n - 3, memo);
            return memo[n];
        }
    }



}
