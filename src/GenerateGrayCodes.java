import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by leiwang on 3/15/18.
 */
public class GenerateGrayCodes {
    public static void main(String[] args) {
        List<Integer> result = grayCode(3);
        for (Integer res : result) {
            System.out.println(res);
        }
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
            int candidateNextCode = previousCode ^ (1 << i);

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
}
