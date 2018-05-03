package EPI_BOOK;

/**
 * Created by leiwang on 5/2/18.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Problem statement
 * Multiply two arrays of integers with sign
 * ex. [1,2,3,4,5] * [-1,2,3,4,5] = ?
 */
public class MultiplyArrays {
    public static void main(String[] args) {
        System.out.println(multiplyArrays(new int[] {-1,2,3,4},new int[] {1,2,3,4}));
        System.out.println(-1234*1234);
    }

    private static List<Integer> multiplyArrays(int[] integer1, int[] integer2) {
        // take care of the sign
        // multiply digit by digit starting from the end of array (2 for loops)
        int sign = (integer1[0] < 0 ? -1 : 1) * (integer2[0] < 0 ? -1 : 1);

        integer1[0] = Math.abs(integer1[0]);
        integer2[0] = Math.abs(integer2[0]);

        List<Integer> result = new ArrayList<>();
        for (int index = 0; index < integer1.length + integer2.length; index ++) {
            result.add(0);
        }
        for (int int1Index = integer1.length-1; int1Index >= 0; int1Index--) {
            for (int int2Index = integer2.length-1; int2Index >= 0; int2Index--) {
                // note the plus 1 for the index since 1 was subtracted from original 2 numbers
                result.set(int1Index + int2Index + 1,
                        result.get(int1Index + int2Index + 1) + // result from prev addition (accumulator)
                                integer1[int1Index] * integer2[int2Index]); // actual multiplication
                result.set(int1Index + int2Index, // take account of carry to the next digit
                        result.get(int1Index + int2Index) + result.get(int1Index + int2Index + 1) / 10);
                // update the current digit with the carry (if any from previous multiply)
                result.set(int1Index + int2Index + 1, result.get(int1Index + int2Index + 1) % 10);
                System.out.print(result.get(int1Index + int2Index + 1));
            }
            System.out.println();
        }

        System.out.println(java.util.Arrays.toString(result.toArray()));
        // remove the ending 0s
        int firstNotZero = 0;
        while (firstNotZero < result.size() && result.get(firstNotZero) == 0) {
            firstNotZero ++;
        }

        // first not zero position found, so truncate the list
        result = result.subList(firstNotZero, result.size());

        // if result is empty then return array of 0
        if (result.isEmpty()) {
            return java.util.Arrays.asList(0);
        }

        // take account of the sign
        result.set(0, result.get(0) * sign);
        return result;

    }
}
