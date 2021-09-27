package AlgoExpert.Easy;

import java.awt.*;
import java.util.*;

public class TwoSumNumber {

    public static void main(String[] args) {

    }

    // O(nlog(n)) | O(1) space
    public static int[] twoNumberSum(int[] array, int targetSum) {
        int startIdx = 0;
        int endIdx = array.length-1;
        Arrays.sort(array);
        while (startIdx < endIdx) {
            if (array[startIdx] + array[endIdx] < targetSum) {
                startIdx++;
            } else if (array[startIdx] + array[endIdx] > targetSum) {
                endIdx--;
            } else {
                return new int[]{array[startIdx], array[endIdx]};
            }
        }
        // Write your code here.
        return new int[0];
    }


}
