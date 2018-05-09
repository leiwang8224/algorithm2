package DP;

/**
 * Created by leiwang on 3/25/18.
 */
public class Fibonacci {
    public static void main(String args[]) {
        // input number plus 1
        int memo[] = new int[5];
        System.out.println(fib(4,memo));
    }

    private static int fib(int n, int memo[]) {
        // if fib[n] already calculated do not calculate again
        if (memo[n] != 0)
            return memo[n];

        // compute fib[n] and store in memo[n]
        if (n == 1 || n == 2) {
            memo[n] = 1;
        } else {
            memo[n] = fib(n-1, memo) + fib(n-2, memo);
        }
        return memo[n];
    }

    private static int fibRecurse(int n) {
        int[] fibNums = new int[n+1];
        fibNums[0] = 0;
        fibNums[1] = 1;

        for (int i = 2; i < n+1; i ++)
            fibNums[i] = fibNums[i-1] + fibNums[i-2];

        return fibNums[n];
    }

    private static int fibSimplify(int n) {
        int fibFirst = 0;
        int fibSecond = 0;

        for (int i = 2; i < n+1; i++) {
            fibSecond = fibFirst + fibSecond;
            fibFirst = fibSecond - fibFirst;
        }
        return fibSecond;
    }
}
