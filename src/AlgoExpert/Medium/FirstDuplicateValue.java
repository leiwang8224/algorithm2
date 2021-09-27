package AlgoExpert.Medium;

import java.util.HashSet;

public class FirstDuplicateValue {
    public static void main(String[] args) {

    }

    // O(n^2) time | O(1) space
    private int firstDupValue(int[] array) {
        int minSecondIndex = array.length;

        for (int index = 0; index < array.length; index++) {
            int value = array[index];
            for (int secIndex = index + 1; secIndex < array.length; secIndex++) {
                int valueToCompare = array[secIndex];
                if (value == valueToCompare) {
                    minSecondIndex = Math.min(minSecondIndex, secIndex);
                }
            }
        }

        if (minSecondIndex == array.length) return -1;

        return array[minSecondIndex];
    }

    // O(n) time | O(n) space
    private int firstDupValue2(int[] array) {
        HashSet<Integer> set = new HashSet<>();
        for (int index = 0; index < array.length; index++) {
            if (set.contains(array[index])) {
                return array[index];
            }
            set.add(array[index]);
        }

        return -1;
    }

    // O(n) time | O(1) space
    // 1. translate value to index and convert value to negative
    // 2. if the next value translated to index is negative then we have visited it before
    // mark each element seen the first time negative
    // if the element is seen again check if negative (element repeated)
    private int firstDupValue3(int[] array) {
        for (int value : array) {
            // translate value to index
            int absValue = Math.abs(value);
            int index = absValue - 1;
            if (array[index] < 0) return absValue; // if negative then it's already marked
            array[index] *= -1; // mark the seen value as negative
        }

        return -1;
    }


}
