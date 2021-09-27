package AlgoExpert.Hard;
import java.util.*;

// O(n^2) time | O(n) space
public class MaxSumIncSubsequence {
    public static void main(String[] args) {
        int[] array = new int[]{10,70,20,30,50,11,30};
        List<List<Integer>> result = maxSumIncSubsequence(array);

    }
    // curIdx iterate through the array, indexUpToCurIdx gets the elements less than the curIdx element
    // store cumsum into array and the subsequence indices that makes up the maxsum
    // Two Arrays: numbers and cumSum
    // Another Array for the prev indices that makes up the cumSum,
    // and a var to keep track of the last idx with GLOBAL max sum
    private static List<List<Integer>> maxSumIncSubsequence(int[] array) {
        int[] prevIdxWithMaxSumSoFar = new int[array.length];
        // create subsequenceIndices as NA values, need to build this in separate function
        Arrays.fill(prevIdxWithMaxSumSoFar, Integer.MIN_VALUE);
        int[] cumSum = array.clone();
        // initialize the max sum index = 0, keeps track of where the GLOBAL max sum
        // is located at all times
        int globalMaxSumIdxPrev = 0;

        // update cumSum array in inner loop and maxSumIdxSoFar in outer loop
        for (int curIdx = 0; curIdx < array.length; curIdx++) {
            int curNum = array[curIdx];
            for (int prevIdx = 0; prevIdx < curIdx; prevIdx++) {
                int prevNum = array[prevIdx];
                // the number before current number is < current number &&
                // sums up to the number before current number + current number >= sums up to the cur number
                if (prevNum < curNum && cumSum[prevIdx] + curNum >= cumSum[curIdx]) {
                    // update the sums current index with the larger sums at index up to index + current number
                    cumSum[curIdx] = cumSum[prevIdx] + curNum;
                    // update the subsequence indices to record the index that has the greater number
                    // subseqenceIndices is a combination of indices and NA values
                    prevIdxWithMaxSumSoFar[curIdx] = prevIdx;
                }
            }
            // at any point keep track of where the GLOBAL max sum is located in INDEX
            if (cumSum[curIdx] >= cumSum[globalMaxSumIdxPrev]) {
                globalMaxSumIdxPrev = curIdx;
            }
        }
        System.out.println(globalMaxSumIdxPrev + "," + Arrays.toString(prevIdxWithMaxSumSoFar) + "," + Arrays.toString(cumSum));
        // sums[maxSumIdx] contains the max sum while maxSumIdx contains the last index for max sum
        return buildSequenceReverse(array,
                                    prevIdxWithMaxSumSoFar,
                                    globalMaxSumIdxPrev,
                                    cumSum[globalMaxSumIdxPrev]);
    }

    /**
     * Add to the indices list in reverse order
     * extract the indices from the subsequenceIndices array and put in list in reverse order
     * note that subsequenceIndices has Integer.MIN_VALUE(NaN) values in it.
     */
    private static List<List<Integer>> buildSequenceReverse(int[] array,
                                                            int[] prevIdxWithMaxSum,
                                                            int globalMaxSumIdx,
                                                            int maxSum) {
        List<List<Integer>> result = new ArrayList<>();
        // add the max sum
        result.add(new ArrayList<>());
        result.get(0).add(maxSum);

        // add the values that add up to the sum
        result.add(new ArrayList<>());

        // while we are not at the end of the sequences
        while (globalMaxSumIdx != Integer.MIN_VALUE) {
            // insert array[curIdx] into the 0th index in the list
            // which effectively reverses the array[]
            result.get(1).add(0, array[globalMaxSumIdx]);
            // update the index to be the index of the previous number
            // essentially iterating backwards in the subsequenceIndices array
            System.out.println("lastIdxSequence before " + globalMaxSumIdx);

            globalMaxSumIdx = prevIdxWithMaxSum[globalMaxSumIdx];
            System.out.println("lastIdxSequence after " + globalMaxSumIdx);
        }
        return result;
    }

    /**
     * My solution
     */
    public static List<List<Integer>> maxSumIncreasingSubsequenceMySol(int[] array) {
        int[] maxSum = new int[array.length];
        for (int idx = 0; idx < array.length; idx++) {
            maxSum[idx] = array[idx];
        }
        int[] lastIdx = new int[array.length];
        Arrays.fill(lastIdx, Integer.MIN_VALUE);
        int globalMaxIdx = 0;
        for (int idx = 0; idx < array.length; idx++) {
            int curNum = array[idx];
            for (int prevIdx = 0; prevIdx < idx; prevIdx++) {
                int prevNum = array[prevIdx];
                if (curNum > prevNum && maxSum[prevIdx] + array[idx] > maxSum[idx]) {
                    maxSum[idx] = maxSum[prevIdx] + array[idx];
                    lastIdx[idx] = prevIdx;
                }
            }
            if (maxSum[idx] > maxSum[globalMaxIdx]) {
                globalMaxIdx = idx;
            }
        }

        // Write your code here.
        return buildSolution(lastIdx, array, globalMaxIdx, maxSum[globalMaxIdx]);
    }

    private static List<List<Integer>> buildSolution(int[] lastIdx,
                                                     int[] array,
                                                     int globalMaxIdx,
                                                     int maxSum) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        result.get(0).add(maxSum);
        result.add(new ArrayList<>());

        while (globalMaxIdx != Integer.MIN_VALUE) {
            result.get(1).add(0, array[globalMaxIdx]);
            globalMaxIdx = lastIdx[globalMaxIdx];
        }

        return result;
    }
}
