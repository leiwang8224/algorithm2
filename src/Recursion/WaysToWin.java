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
        System.out.println(waysToWin(13)); //returns 5
        System.out.println(waysToWinDP(13)); //returns 2
        // 10 + 3
        // 3 + 5 + 5
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

    private static int waysToWinDP(int n) {
        int table[] = new int[n+1], i;

        // init all table values to be 0
        java.util.Arrays.fill(table, 0);

        // base case(if given value is 0)
        table[0] = 1; // only 1 way to win if final score is 0

        // one by one consider given 3
        // moves and update the table[]
        // values after the index greater
        // than or equal to the value of
        // the picked move
        for (i = 3; i <= n; i ++)
            table[i] += table[i-3];
        for (i = 5; i <= n; i ++)
            table[i] += table[i-5];
        for (i = 10; i <= n; i ++)
            table[i] += table[i-10];
        System.out.println(java.util.Arrays.toString(table));
        //[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,12,13]
//        [1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 2, 1, 1, 2]
        return table[n];
    }

    // 3  | 0
    // 5  | 0
    // 10 | 0
}
