package CTCI.RecursionAndDP;

/**
 * Write a recursive function to multiply two positive integers without using
 * the * operator (or / operator). You can use addition, subtraction, and bit shifting,
 * but you should minimize the number of those operations.
 */
public class RecursiveMultiply {

    public static void main(String[] args) {
        System.out.println(minProduct(2,3));
        System.out.println(minProductCaching(2,3));
        System.out.println(minProductOptimized(2,3));
    }

    /**
     * One way of thinking about multiplying is adding: 8 x 7 = 8+8+8+8+8+8+8
     * ex:
     * minProduct(17, 23)
     *      minProduct(8, 23)
     *              minProduct(4, 23) * 2
     *                  ...
     *      + minProduct(9, 23)
     *              minProduct(4, 23)
     *                  ...
     *            + minProduct(5, 23)
     *                  ...
     * @param a
     * @param b
     * @return
     */
    static int minProduct(int a, int b) {
       int biggerNumber = a < b ? b:a;
       int smallerNumber = a < b ? a:b;
       return minProductHelper(smallerNumber, biggerNumber);
    }

    private static int minProductHelper(int smallerNumber, int biggerNumber) {
        // base condition
        if (smallerNumber == 0) return 0;                 // 0 x bigger = 0
        else if (smallerNumber == 1) return biggerNumber; // 1 x bigger = bigger

        // compute half. If not even, compute other half.
        // If even, double it
        int smallerNumberDivideBy2 = smallerNumber >> 1; // divide by 2
        // recursively divide by 2
        int partOneOfTwo = minProductHelper(smallerNumberDivideBy2, biggerNumber);
        int partTwoOfTwo = partOneOfTwo;

        // if the smallerNumber is odd
        // when it's not even, we need to do the counting/ summing from scratch
        if (smallerNumber % 2 == 1) partTwoOfTwo = minProductHelper(smallerNumber - smallerNumberDivideBy2, biggerNumber);
        return  partOneOfTwo + partTwoOfTwo;
    }

    /**
     * Cache values so we don't have to recalculate
     * @param a
     * @param b
     * @return
     */
    static int minProductCaching(int a, int b) {
        int bigger = a < b ? b : a;
        int smaller = a < b ? a : b;

        int memo[] = new int[smaller + 1];
        return minProductCaching(smaller, bigger, memo);
    }

    private static int minProductCaching(int smaller, int bigger, int[] memo) {
        if (smaller == 0) return 0;
        else if (smaller == 1) return bigger;
        else if (memo[smaller] > 0) return memo[smaller];

        // compute half. IF not even, compute other half. If even, double it.
        int smallerDivideBy2 = smaller >> 1; // divide by 2
        int side1 = minProductCaching(smallerDivideBy2, bigger, memo); // compute half
        int side2 = side1;

        // smaller number is odd, compute other half
        if (smaller % 2 == 1) side2 = minProductCaching(smaller - smallerDivideBy2, bigger, memo);

        // sum and cache
        memo[smaller] = side1 + side2;
        return memo[smaller];
    }

    static int minProductOptimized(int a, int b) {
        int bigger = a < b ? b : a;
        int smaller = a < b ? a : b;
        return minProductOptimizedHelper(smaller, bigger);
    }

    private static int minProductOptimizedHelper(int smaller, int bigger) {
        if (smaller == 0) return 0;
        else if (smaller == 1) return bigger;

        int s = smaller >> 1; // divide by 2 until smaller reaches 1
        int halfProd = minProductOptimizedHelper(s, bigger);

        // if smaller is even just return twice the half Product
        if (smaller % 2 == 0) return halfProd + halfProd;
        else return  halfProd + halfProd + bigger;
    }

    /* This is an algorithm called the peasant algorithm.
     * https://en.wikipedia.org/wiki/Multiplication_algorithm#Peasant_or_binary_multiplication
     * Example: 11 x 3
     * Decimal:     Binary:
     * 11   3       1011    11
     * 5    6       101    110
     * 2    12      10    1100     <--- scratched out
     * 1    24      1    11000
     *      __           _____
     *      33          100001
     * Steps:
     * * 11 and 3 are written at the top
     * * 11 is halved (5.5) and 3 is doubled (6). The fractional portion is discarded (5.5 becomes 5)
     * * 5 is halved (2.5) and 6 is doubled (12). The fractional portion is discarded (2.5 becomes 2).
     * * * The figure in the left column (2) is even, so the figure in the right column (12) is discarded.
     * * 2 is halved (1) and 12 is doubled (24)
     * * All not scratched out values are summed: 3+ 6 + 24 = 33.
     * The method works because multiplication is distributive so:
     * 3 x 11 = 3 x (1 x 2^0 + 1 x 2^1 + 0 x 2^2 + 1 x 2^3)
     *        = 3 x ( 1 + 2 + 8)
     *        = 3 + 6 + 24 = 33
     */
    static int counter = 0;

    static int minProductPeasantAlgorithm(int a, int b) {
        if (a < b) return minProductPeasantAlgorithm(b, a);
        int value = 0;

        while (a > 0) {
            counter ++;
            if ((a%10) % 2 == 1) value += b;
            a >>= 1;
            b <<= 1;
        }
        return value;
    }

}


