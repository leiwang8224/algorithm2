package Recursion;

/**
 * Created by leiwang on 3/25/18.
 */

/**
 * Consider a game where the player can score 3, 5 or 10
 * points in one move. Given total score N, find number of ways
 * to reach N.
 */
public class WaysToWin {
    public static void main(String[] args) {
        waysToWin(13);
    }

    /**
     * Complexity = O(n3)
     * @param n
     * @return
     */
    private static int waysToWin(int n) {
        if (n < 0) return 0;
        if (n == 0) return 1;

        return waysToWin(n - 10) +
                waysToWin(n - 5) +
                waysToWin(n - 3);
    }
}
