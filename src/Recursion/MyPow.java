package Recursion;

/**********************************************************************************
 *
 * Implement pow(x, n).
 *
 * Example 1:
 *
 *  Input: 2.00000, 10
 *  Output: 1024.00000
 *
 * Example 2:
 *
 *  Input: 2.10000, 3
 *  Output: 9.26100
 *
 **********************************************************************************/

public class MyPow {
    public static void main(String[] args) {

    }

    private double recursion (double x, long n) {
        if (n == 1) return x;

        // unsigned right shift
        double half = recursion(x, n >>> 1);

        if ((n & 1) == 0) {
            return half * half;
        } else {
            return half * half * x;
        }
    }

    private double myPow1(double x, int n) {
        if (n == 0 || x == 1) {
            return 1;
        }

        // avoid being out of bounds, we should cast int to long
        long m = n;
        double result = recursion(x, Math.abs(m));

        if (n > 0)
            return result;
        else
            return 1 / result;
    }

    private double myPow2(double x, int n) {
        if (n == 0 || x == 1) {
            return 1;
        }

        // avoid being out of bounds, we should cast int to long
        long m = n;
        double result = bitFunction(x, Math.abs(m));

        if (n > 0) {
            return result;
        } else {
            return 1 / result;
        }

    }

    /**
     * Solution with bit-manipulation
     * For example:
     * 9=1001
     * 3^9=(3^1)^1*(3^2)^0*(3^4)^0*(3^8)^1
     * Space is O(1), Time is O(logN)
     */
    private double bitFunction(double x, long abs) {
        double multy = 1;
        double base = x;
        for (long k = abs; k >= 1; k >>>= 1) {
            if ((k & 1) > 0) {
                multy = multy * base;
            }
            base *= base;
        }
        return multy;
    }

}


