package CTCI.BitManipulation;

/**
 * Write a program to swap odd and even bits in an integer with as few instructions
 * as possible (ex. bit 0 and bit 1 are swapped, bit2 and bit3 are swapped and so forth)
 */
public class PairwiseSwapBits {
    public static void main(String[] args) {

    }

    /**
     * The trick is to divide the bits into even and odd ones. We can operate
     * on the odd bits first then the even ones. Let's move the odd bits over by 1.
     * We can mask all odd bits with 10101010 in binary (which is 0xAA),
     * then shift them right by 1 to put them in the even spots. For the even bits
     * we do an equivalent operation. Finally we merge the two results.
     * @param num
     * @return
     */
    static int pairWiseSwap(int num) {
        // total of 5 instructions
        // note we use logical right shift instead of arithmetic right shift. This is
        // because we want the sign bit to be filled with a 0.
        return (((num & 0xaaaaaaaa) >>> 1) | ((num & 0x55555555) << 1));
    }
}
