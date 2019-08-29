package CTCI.BitManipulation;

/**
 * Given two 32 bits numbers, N and M, and two bit positions i and j.
 * Write a method to insert M into N such that M starts at bit j and ends at bit i.
 * Assume that the bits j through i have enough space to fit all of M.
 * ex: input N = 10000000000, M = 10011, i = 2, j = 6
 * output N = 10001001100
 */
public class InsertBits {
    public static void main(String[] args) {
        int a = ~23423;
        int b = 5;
        int c = insertBits(a, b, 29, 31);
        System.out.println("a:" + BitUtils.toFullBinString(a));
        System.out.println("b:" + BitUtils.toFullBinString(b));
        System.out.println("c:" + BitUtils.toFullBinString(c));

    }

    private static int insertBits(int numN, int numM, int positionI, int positionJ) {
        // validate the numbers
        if (positionI > positionJ || positionI < 0 || positionJ >= 32) return 0;

        // create a mask to clear bits i through j in n
        // example: i = 2, j = 4, result should be 11100011
        // using 8 bits for this example. This is obviously not actually 8 bits
        int allOnes = ~0; // all Ones 11111111

        // when making a mask note the following (push zeros in) for zero indexing:
        // starting from the right, make sure to add 1 to position
        // starting from the left, make sure to subtract 1 in the end

        // 1s until position j, then 0s. left = 11100000
        int left = positionJ < 31 ? (allOnes << (positionJ + 1)) : 0;
        // 1s after position i. right = 00000011
        int right = ((1 << positionI) - 1);

        // all 1s except for 0s between i and j. mask = 11100011
        int mask = left | right;

        // clear i through j, then put m in there
        int nCleared = numN & mask; // clear bits j through i
        int mShifted = numM << positionI; // move m into correct position

        return nCleared | mShifted;
    }
}
