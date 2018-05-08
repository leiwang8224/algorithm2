package CTCI;

/**
 * Created by leiwang on 5/5/18.
 */

/**
 * Write recursive function to multiply two numbers without using the * operator
 * or / operator. You can use addition, subtract and bit manipulation. You should
 * minimize the operations.
 */
public class RecursiveMulitply {
    public static void main(String[] args) {
        System.out.println(multiply(2,4));
        System.out.println(multiply2(2,4));
        System.out.println(multiply3(2,4));

    }

    /**
     * Image using a grid to calculate
     * ex: 8 * 7 = 8 + 8 + 8+8 +8 +8 +8
     * @param num1
     * @param num2
     * @return
     */
    private static int multiply (int num1, int num2) {
        int bigger = num1 < num2 ? num2 : num1;
        int smaller = num1 < num2 ? num1 : num2;
        return minProductHelper(smaller, bigger);
    }

    private static int minProductHelper(int smaller, int bigger) {
        if (smaller == 0) { // 0x bigger = 0
            return 0;
        } else if (smaller == 1) // 1  x bigger = bigger
            return bigger;
        // compute half. If uneven, compute other half. If even, double it
        int s = smaller >> 1; // divide by 2
        int side1 = multiply(s, bigger);
        int side2 = side1;
        if (smaller % 2 == 1)
            side2 = minProductHelper(smaller - s, bigger);

        return  side1 + side2;
    }

    /**
     * Try to cache the results first so we don't have to recalculate
     * @param num1
     * @param num2
     * @return
     */
    private static int multiply2 (int num1, int num2) {
        int bigger = num1 < num2 ? num2 : num1;
        int smaller = num1 < num2 ? num1 : num2;

        int memo[] = new int[smaller + 1];
        return minProduct(smaller, bigger, memo);
    }

    private static int minProduct(int smaller, int bigger, int[] memo) {
        if (smaller == 0)
            return 0;
        else if (smaller == 1)
            return bigger;
        else if (memo[smaller] > 0)
            return memo[smaller];

        // compute half, if uneven, compute other half. If even double it
        int s = smaller >> 1; // divide by 2
        int side1 = minProduct(s, bigger, memo); // compute half
        int side2 = side1;
        if (smaller % 2 == 1)
            side2 = minProduct(smaller - s, bigger, memo);

        // sum and cache
        memo[smaller] = side1 + side2;
        return memo[smaller];
    }

    /**
     * One observation is a call to minProduct on an even number is much
     * faster than one on an odd number. If we call minProduct(30,35), then we will just
     * do minProduct(15,35) and double the result. However, if we do minProduct(31,35), then
     * we will need to call minProduct(15,35) and minProduct(16,35)
     * This is unnecessary, instead we can do:
     * minProduct(31, 35) = 2 * minProduct(15,35) + 35
     * Since 31 = 2* 15+1 then 31 * 35 = 2*15*35+35
     * @param num1
     * @param num2
     * @return
     */
    private static int multiply3 (int num1, int num2) {
        int bigger = num1 < num2 ? num2 : num1;
        int smaller = num1 < num2 ? num1 : num2;
        return minProductHelper2(smaller, bigger);
    }

    private static int minProductHelper2(int smaller, int bigger) {
        if (smaller == 0) return 0;
        else if (smaller == 1) return bigger;

        int s = smaller >> 1; // divide by 2
        int halfProd = minProductHelper2(s, bigger);

        if (smaller % 2 == 0) {
            return halfProd + halfProd;
        } else {
            return halfProd + halfProd + bigger;
        }
    }
}
