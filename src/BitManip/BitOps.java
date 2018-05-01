package BitManip;

import java.util.ArrayList;
import java.util.Arrays;

public class BitOps {
    public static void main(String[] args) {
        int num = 4, pos = 1;
        printBinaryArray(num);
        printBinaryArray(set(num, pos));
        printBinaryArray(unset(num, 1));
        printBinaryArray(toggle(num,1));
        printBinaryArray(updateBit(num,1,false));
        printBinaryArray(flipEveryBit(num));
        printBinaryArray(getLowestSetBit(num));
        printBinaryArray(stripLastBit(num));
        printBinaryArray(twosComplement(num));
    }

    private static boolean get(int num, int pos) {
        return ((num & (1 << pos)) != 0);
    }

    private static int set(int num, int pos) {
        // First step is shift '1', second step is bitwise OR
        return num |= (1 << pos);
    }

    private static int unset(int num, int pos) {
        // First step is to get a number that has all 1's except for the given position
        // Second step is to bitwise AND this number with given number
        return num &= (~(1 << pos));
    }

    private static int toggle(int num, int pos) {
        // First step is to shift 1
        // Second step is to XOR with given number
        return num ^= (1 << pos);
    }

    private static int updateBit(int num, int pos, boolean bitIs1) {
        int value = bitIs1 ? 1 : 0;
        int mask = ~(1 << pos);
        return (num & mask) | (value << pos);
    }

    private static boolean checkIfSet(int num, int pos) {
        return (num & (1<<pos)) == 1;
    }

    private static int flipEveryBit(int num) {
        // 1's complement
        return ~ num;
    }

    private static int twosComplement(int num) {
        return ~num + 1;  // -num or 1's complement + 1
    }

    private static int stripLastBit(int num) {
        // Given X = 1100
        // X-1 inverts all the bits till it encounters lowest set 1 and it also invert that lowest set 1 => 1011
        // X-1 becomes 1011, after ANDing X with X-1 we get lowest set bit stripped (1100 && 1011 = 1000)
        return num & (num - 1);
    }

    private static int getLowestSetBit (int num) {
        return num & -num;
    }

    public static void printBinaryArray(int num) {
        ArrayList<Integer> list = new ArrayList<>();
        while (num != 0) {
            list.add(num%2);
            num = num / 2;
        }
        int[] result = new int[list.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = list.get(list.size()-i-1);
        System.out.println(Arrays.toString(result));

    }
}
