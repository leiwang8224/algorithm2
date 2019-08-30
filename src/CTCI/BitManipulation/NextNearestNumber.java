package CTCI.BitManipulation;

/**
 * Given a positive integer, print the next smallest and the next largest number
 * that have the same number of 1 bits in their binary representation.
 * NOTE THIS SEEMS TOO DIFFICULT FOR INTERVIEW
 */
public class NextNearestNumber {
    public static void main(String[] args) {
        int i = 13946;
        int p1 = getPrev(i);
        int n1 = getNext(i);
        
        int p1Arith = getPrevArith(i);
        int n1Arith = getNextArith(i);

        System.out.println("number = " + i + " p1 = " + p1 + " n1 = " + n1);
        System.out.println("number = " + i + " p1Arithm = " + p1Arith + " n1Arithm = " + n1Arith);

    }

    /**
     * Given a number n and two bit locations i and j, suppose we flip bit i from a 1
     * to a 0, and bit j from a 0 to a 1. If i > j, then n will have decreased. If i < j,
     * then n will have increased.
     * 1. if we flip a zero to a one, we must flip one to a zero
     * 2. when we do that, the number will be bigger if and only if the zero-to-one bit
     * was to the left of the one-to-zero bit.
     * 3. we want to make the number bigger, but not unnecessarily bigger. Therefore,
     * we need to flip the rightmost zero which has ones on the right of it.
     *
     * Approach:
     * 1. Flip rightmost non-trailing zero
     * Initial:
     *  1   1   0   1   1   0   1   1   1   1   1   1   0   0
     *  13  12  11  10  9   8   7   6   5   4   3   2   1   0
     *
     * 2. Clear bits to the right of p. From before, c0 = 2. c1 = 5. p = 7
     * Flipped:
     *   1   1   0   1  1   0   1   0   0   0   0   0   0   0
     *  13  12  11  10  9   8   7   6   5   4   3   2   1   0
     *
     * 3. Add in c1 - 1 ones
     *   1   1   0   1  1   0   1   0   0   0   1   1   1   1
     *  13  12  11  10  9   8   7   6   5   4   3   2   1   0
     *
     * @param number
     * @return
     */
    static int getNext (int number) {
        int c = number;
        int c0 = 0;
        int c1 = 0;

        // count the number of rightmost 0's and 1's
        while (((c & 1) == 0) && (c != 0)) {
            c0 ++;
            c = c >> 1;
        }

        while ((c & 1) == 1) {
            c1 ++;
            c = c >> 1;
        }

        // if c is 0, then n is a sequence of 1s followed by a seq of 0s
        // this is already the biggest number with c1 ones, return error.
        // Error: if number == 11..1100...00, then there is no bigger number
        // with the same number of 1s.
        if (c0 + c1 == 31 || c0 + c1 == 0) return -1;

        // position of rightmost non-trailing zero (where the right most bit is bit 0)
        int posRightMostNonTrailingZero = c0 + c1;

        // flip (toggle) the rightmost non-trailing zero (which will be at position pos)
        number = number | (1 << posRightMostNonTrailingZero);  // flip right most non-trailing zero

        /**
         * Clear all bits to the right of pos.
         * Example with pos = 5
         * 1. Shift 1 over by 5 to create 0..0100000        [mask = 1 << pos]
         * 2. subtract 1 to get 0..0011111                  [mask = mask - 1]
         * 3. flip all the bits by using '~' to get 1..1100000 [mask = ~mask]
         * 4. AND with number
         */
        // clear all bits to the right of pos
        number = number & ((~1 << posRightMostNonTrailingZero) - 1);

        /**
         * put (ones - 1) 1s on the right by doing the following:
         * 1. shift 1 over by (ones - 1) spots. If ones = 3, this gets you 0..0100
         * 2. subtract one from that to get 0..0011
         * 3. OR With number
         */
        // insert (c1-1) ones on the right
        number = number | ( 1 << (c1 - 1)) - 1;

        return number;
    }

    /**
     * Similar approach for getPrev()
     * 1. Compute c0 and c1. Note that c1 is the number of trailing ones, and c0 is
     * the size of the block of zeros immediately to the left of the trailing ones.
     * Initial: p = 7, c1 = 2, c0 = 5
     *  1   0   0   1   1   1   1   0   0   0   0   0   1   1
     *  13  12  11  10  9   8   7   6   5   4   3   2   1   0
     * 2. Flip the rightmost non-trailing one to a zero. This will be at position p = c1 + c0.
     * 3. Clear all bits to the right of bit p.
     *    1   0   0   1  1   1   0   0   0   0   0   0   0   0
     *   13  12  11  10  9   8   7   6   5   4   3   2   1   0
     * 4. Insert c1 + 1 ones immediately to the right of position p.
     *    1   0   0   1  1   1   0   1   1   1   0   0   0   0
     *   13  12  11  10  9   8   7   6   5   4   3   2   1   0
     * Note that step 2 sets bit p to a zero and step 3 sets bits 0 through p-1 to zero
     * @param number
     * @return
     */
    static int getPrev(int number) {
        int temp = number;
        int c0 = 0;
        int c1 = 0;
        while ((temp & 1) == 1) {
            c1++;
            temp = temp >> 1;
        }

        /**
         * if temp is 0, then the number is a sequence of 0s followed by a sequence of 1s.
         * This is already the smallest number with c1 ones. Return -1 for error.
         */
        if (temp == 0) return -1;

        while (((temp & 1) == 0) && (temp != 0)) {
            c0++;
            temp = temp >> 1;
        }

        // position of rightmost non-trailing one(where the rightmost bit is bit 0)
        int p = c0 + c1;

        /**
         * flip rightmost non-trailing one
         * Example: n = 00011100011
         * c1 = 2
         * c0 = 3
         * pos = 5
         *
         * Build up a mask as follows:
         * 1. ~0 will be a sequence of 1s
         * 2. shifting left by p+1 will give you 11.111000000 (six 0s)
         * 3. ANDing with number will clear the last 6 bits
         * number is now 00011000000
         */
        number = number & ((~0) << (p + 1));     // clears from bit p onwards (to the right)

        /**
         * Create a sequence of (c1+1) 1s as follows
         * 1. Shift 1 to the left(c1+1) times. If c1 is 2, this will give you 0..001000
         * 2. Subtract one from that. This will give you 0..00111
         */
        int mask = (1 << (c1+1)) - 1;       // sequence of (c1+1) ones

        /**
         * Move th eones to be right up next to bit p
         * since this is a sequence of (c1+1) ones, and p = c1+c0, we
         * just need to shift this over by (c0-1) spots.
         * if c0 = 3 and c1 = 2, then this will look like 00...0011100
         *
         * then finally we OR this with number
         */
        number = number | (mask << (c0 - 1));

        return number;
    }

    /**
     * If c0 is the number of trailing zeros, c1 is the size of the one block immediately
     * following, and p = c0 + c1.
     * 1. Set the pth bit to 1.
     * 2. Set all bits following p to 0.
     * 3. Set bits 0 through c1 - 2 to 1. This will be c1 -1 total bits
     * @param number
     * @return
     */
    static int getNextArith(int number) {
        int c = number;
        int c0 = 0;
        int c1 = 0;
        while (((c & 1) == 0) && (c != 0)) {
            c0++;
            c = c >> 1;
        }

        while ((c & 1) == 1) {
            c1++;
            c = c >> 1;
        }

        // if c is 0, then n is a sequence of 1s followed by a sequence of 9s.
        // this is already the biggest number with c1 ones. Return error.
        if (c0 + c1 == 31 || c0 + c1 == 0) return -1;

        /**
         * Arithmetically:
         * 2^c0 = 1 << c0
         * 2^(c1-1) = 1 << (c0-1)
         * next = n + 2^c0 + 2 ^(c1-1) - 1;
         */

        return number + (1 << c0) + (1 << (c1 - 1)) - 1;
    }

    /**
     * If c1 is the number of trailing ones, c0 is the size of the zero block immediately
     * following, and p = c0 + c1.
     * 1. Set the pth bit to 0
     * 2. Set all bits following p to 1
     * 3. Set bits 0 through c0 - 1 to 0
     * @param number
     * @return
     */
    static int getPrevArith(int number) {
        int temp = number;
        int c0 = 0;
        int c1 = 0;
        while (((temp & 1) == 1) && (temp != 0)) {
            c1++;
            temp = temp >> 1;
        }

        /**
         * if temp is 0, then the number is sequence of 0s followed by a sequence
         * of 1s. This is already the smallest number with c1 ones. Return -1 for an error
         */
        if (temp == 0) return -1;

        while ((temp & 1) == 0 && (temp != 0)) {
            c0++;
            temp = temp >> 1;
        }

        /**
         * Arithmetic:
         * 2^c1 = 1 << c1
         * 2^(c0 -1) = 1 << (c0 - 1)
         */
        return number - (1 << c1) - (1 << (c0 - 1)) + 1;
    }
}
