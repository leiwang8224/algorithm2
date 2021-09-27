package AlgoExpert.Hard;
import java.util.*;
public class MaximizeExpression {
    // O(n^4) time | O(1) space where n is length of array
    int maximizeExpression(int[] array) {
        if (array.length < 4) return 0;

        int maxValueFound = Integer.MIN_VALUE;

        for (int a = 0; a< array.length; a++) {
            int aValue = array[a];
            for (int b = a+1; b < array.length; b++) {
                int bValue = array[b];
                for (int c = b + 1; c < array.length; c++) {
                    int cValue = array[c];
                    for (int d = c+1; d < array.length; d++) {
                        int dValue = array[d];
                        int expressionValue = evaluateExpression(aValue, bValue, cValue, dValue);
                        maxValueFound = Math.max(expressionValue, maxValueFound);
                    }
                }
            }
        }
        return maxValueFound;
    }

    private int evaluateExpression(int aValue, int bValue, int cValue, int dValue) {
        return aValue - bValue + cValue - dValue;
    }

    // O(n) time  | O(n) space
    int maxmizeExpression2(int[] array) {
        if (array.length < 4) return 0;

        ArrayList<Integer> maxOfA =
                new ArrayList<>(Arrays.asList(array[0]));
        ArrayList<Integer> maxOfAMinusB =
                new ArrayList<>(Arrays.asList(Integer.MIN_VALUE));
        ArrayList<Integer> maxOfAMinusBPlusC =
                new ArrayList<>(Arrays.asList(Integer.MIN_VALUE,
                        Integer.MIN_VALUE));
        ArrayList<Integer> maxOfAMinusBPlusCMinusD=
                new ArrayList<>(Arrays.asList(Integer.MIN_VALUE,
                        Integer.MIN_VALUE,
                        Integer.MIN_VALUE));

        for (int idx= 1; idx< array.length; idx++) {
            int curMax = Math.max(maxOfA.get(idx - 1),array[idx]);
            maxOfA.add(curMax);
        }

        for (int idx = 1; idx < array.length; idx++) {
            int curMax = Math.max(maxOfAMinusB.get(idx-1), maxOfA.get(idx-1) - array[idx]);
            maxOfAMinusB.add(curMax);
        }

        for (int idx = 2; idx < array.length; idx++) {
            int curMax =
                    Math.max(maxOfAMinusBPlusC.get(idx-1),
                            maxOfAMinusB.get(idx-1) + array[idx]);
            maxOfAMinusBPlusC.add(curMax);
        }

        for (int idx = 3; idx < array.length; idx++) {
            int curMax = Math.max(
                    maxOfAMinusBPlusCMinusD.get(idx-1),
                    maxOfAMinusBPlusC.get(idx-1) - array[idx]);
            maxOfAMinusBPlusCMinusD.add(curMax);
        }

        return maxOfAMinusBPlusCMinusD.get(maxOfAMinusBPlusCMinusD.size() - 1);
    }
}
