package CTCI.BitManipulation;

import java.util.Arrays;
import java.util.Stack;

/**
 * Logical shift ->  >>>  <<<  does not retain sign bit
 * Arithmetic shift ->  >>   <<   retains sign bit
 */
public class BitUtils {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(intToBoolArray(8,8)));
        System.out.println(boolArrayToInt(intToBoolArray(5,8)));
    }

    /**
     * Shifts 1 over by digits, creating a value that looks like 00010000.
     * By performing an AND with num, we clear all bits other than the bit at
     * bit digit. Finally we compare that to 0, if that new value is not zero,
     * then bit digit must have a 1, otherwise bit digit is a 0.
     * @param num
     * @param digit
     * @return
     */
    static boolean getBit(int num, int digit) {
        // (1 << digit) is a mask
        return ((num & (1 << digit)) != 0);
    }

    /**
     * Shifts 1 over by digit bits, creating a value like 00010000. By
     * performing an OR with num, only the value at bit digit will change.
     * all other bits of the mask are zero and will not affect num.
     * @param num
     * @param digit
     * @return
     */
    static int setBit(int num, int digit) {
        // (1 << digit) is a mask
        return num | (1 << digit);
    }


    /**
     * This method operates in almost the reverse of setBit. First
     * we create a number like 11101111 by creating the reverse of it
     * (00010000) and negating it. Then we perform an AND with num.
     * this will clear the digit bit and leave the remainder unchanged.
     * @param num
     * @param digit
     * @return
     */
    static int clearBit(int num, int digit) {
        int mask = ~(1 << digit);
        return num & mask;
    }


    /**
     * To clear all bits from the MSB through i inclusive, we create a mask with
     * a 1 at the ith bit (1 << i). Then we subtract 1 from it, giving us a sequence
     * of 0s followed by i 1s. We then AND our number with this mask to leave just the last i
     * bits
     * @param num
     * @param i
     * @return
     */
    int clearBitsMSBthroughI(int num, int i) {
        // ex: if i = 4: 0001000 - 1 => 0000111, clears the first 4 bits
        int mask = (1 << i) - 1;
        return num & mask;
    }


    /**
     * To clear all bits from i through 0 inclusive, we take a sequence of all 1s
     * (which is -1) and shift it left by i + 1 bits. this gives a sequence of 1s
     * (in the MSB) followed by i 0 bits.
     * @param num
     * @param i
     * @return
     */
    int clearBitsIthrough0(int num, int i) {
        // Note it is i through (0), so add 1 for 0 index
        // ex: if i = 4: 1111111 << 5 = 1110000, clears the last 4 bits plus 0 bit
        int mask = (-1 << (i + 1));
        return num & mask;
    }

    /**
     * To set the ith bit to a value v, we first clear the bit at position i by
     * using a mask that looks like 11101111. Then, we shift the intended value, v,
     * left by i bits. This will create a number with bit i equal to v and all other
     * bits equal to 0. Finally we OR these two numbers, update the ith bit
     * if v is 1 and leaving it as 0 otherwise.
     * @param num
     * @param i
     * @param bitIs1
     * @return
     */
    int updateBit(int num, int i, boolean bitIs1) {
        // convert bool to int
        int value = bitIs1 ? 1 : 0;
        // clear the bit at position i by using a mask
        int mask = ~(1 << i);
        // update the value in the mask position
        return (num & mask) | (value << i);
    }

    //TODO broke
    static int[] intToBoolArray(int num, int numBitsToDisplay) {
        Stack<Integer> stack = new Stack<>();
        if (num > 0) {
            while (num > 0) {
                stack.push(num % 2);
                num = num / 2;
            }
        } else {
            while (num < 0) {
                stack.push(Math.abs(num % 2));
                num = num / 2;
            }
        }

        int[] result = new int[numBitsToDisplay];
        for (int index = numBitsToDisplay-1; index > numBitsToDisplay - stack.size()-3; index--) {
            result[index] = stack.pop();
        }
        return result;
    }

    //TODO broke
    static double boolArrayToInt(int[] boolArray) {
        double result = 0;
        for (int index = boolArray.length-1; index >= 0; index--) {
            result = result + Math.pow(2, index);

        }
        return result;
    }

    /**
     * Shift the digits to the right and mask the lsb
     * append to the string
     * @param num
     * @return
     */
    static String toFullBinString(int num) {
        String s = "";
        for (int index = 0; index < 32; index++) {
            Integer lsb = new Integer(num & 1);
            s = lsb.toString() + s;
            num = num >> 1;
        }

        return s;
    }


}
