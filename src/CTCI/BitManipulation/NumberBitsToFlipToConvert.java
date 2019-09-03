package CTCI.BitManipulation;

/**
 * Write a function to determine the number of bits you would need to flip to
 * convert integer A to integer B
 * ex:
 * Input: 29 (11101), 15(01111)
 * Output: 2
 */
public class NumberBitsToFlipToConvert {
    public static void main(String[] args) {
        System.out.println(bitSwapRequired(29,15));
        System.out.println(bitSwapRequired2(29,15));

    }

    /**
     * Each 1 in the XOR represents a bit that is different between A
     * and B. Therefore, to check the number of bits that are different
     * between A and B, we simply need to count the number of bits in A XOR B
     * that are 1.
     * @param a
     * @param b
     * @return
     */
    static int bitSwapRequired(int a, int b) {
        int count = 0;
        // right shift the bit in c and count the number of 1's
        // right shift c until it is 0
        for (int c = a ^ b; c != 0; c = c >> 1) {
            // c & 1 takes the lsb
            count = count + c & 1;
        }
        return count;
    }

    /**
     * Rather than simply shifting c repeatedly while checking the
     * LSB, we can continuously flip the LSB and count how long it takes c
     * to reach 0. The operation c = c & (c-1) will clear the LSB in c.
     * @param a
     * @param b
     * @return
     */
    static int bitSwapRequired2(int a, int b) {
        int count = 0;
        // how many times it takes for c to become 0 when we flip the lsb
        // ex: a = 11101, b = 01111, c = a ^ b
        //     c = 10010, c & (c-1) = 10010 & 10001 = 10000     count = 1
        //     c = 10000, c & (c-1) = 10000 & 01111 = 0         count = 2
        for (int c = a ^ b; c != 0; c = c & (c-1)) {
            count++;
        }

        return count;
    }
}
