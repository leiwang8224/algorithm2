import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by leiwang on 3/15/18.
 */

/**
 * The gray code is a binary numeral system where two successive values differ in only one bit.

 Given a non-negative integer n representing the total number of bits in the code, print the
 sequence of gray code. A gray code sequence must begin with 0.

 For example, given n = 2, return [0,1,3,2]. Its gray code sequence is:

 00 - 0
 01 - 1
 11 - 3
 10 - 2

 Note:
 For a given n, a gray code sequence is not uniquely defined.

 For example, [0,2,3,1] is also a valid gray code sequence according to the above definition.

 For now, the judge is able to judge based on one instance of gray code sequence. Sorry about that.

 */
public class GenerateGrayCodes {
    public static void main(String[] args) {
        List<Integer> result = grayCode(3);
        for (Integer res : result) {
            System.out.println(res);
        }

        System.out.println(java.util.Arrays.toString(printBinaryArray(4))); // ;
    }

    private static List<Integer> grayCode(int sizeOfCode) {
        ArrayList<Integer> result = new ArrayList<>(Arrays.asList(0));
        directedGrayCode(sizeOfCode, new HashSet<Integer>(Arrays.asList(0)),result);
        return result;
    }

    private static boolean directedGrayCode(int sizeOfCode, HashSet<Integer> history, ArrayList<Integer> result) {
        if (result.size() == (Math.pow(2,sizeOfCode))) {
            return differsByOneBit(result.get(0), result.get(result.size() - 1));
        }

        for (int i = 0; i < sizeOfCode; ++i) {
            int previousCode = result.get(result.size() - 1);
            // XOR previousCode with 1
            System.out.println("previousCode = " + java.util.Arrays.toString(printBinaryArray(previousCode)) + " i = " + i);
            int candidateNextCode = previousCode ^ (1 << i);
            System.out.println("candidateNextCode = " + java.util.Arrays.toString(printBinaryArray(candidateNextCode)) + " i = " + i);
            if (!history.contains(candidateNextCode)) {
                history.add(candidateNextCode);
                result.add(candidateNextCode);
                if (directedGrayCode(sizeOfCode, history, result)) {
                    return true;
                }
                history.remove(candidateNextCode);
                result.remove(result.size()-1);
            }
        }
        return false;

    }

    private static boolean differsByOneBit(Integer x, Integer y) {
        // XOR here gives the bit thats diff
        int bitDiff = x ^ y;
        // x and y differs by the least sig bit
        return bitDiff != 0 && (bitDiff & (bitDiff - 1)) == 0;
    }

    private static int[] printBinaryArray(int num) {
        ArrayList<Integer> list = new ArrayList<>();
        while (num != 0) {
            list.add(num%2);
            num = num / 2;
        }
        int[] result = new int[list.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = list.get(list.size()-i-1);
        return result;
    }
}
