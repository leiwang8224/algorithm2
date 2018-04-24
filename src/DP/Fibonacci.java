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
}
