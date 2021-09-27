package AlgoExpert.VeryHard;
import java.util.*;

public class LongestIncSubsequence {
    // O(n^2) time | O(n) space
    public static List<Integer> longestIncSubseq(int[] array) {
        int[] sequences = new int[array.length];
        Arrays.fill(sequences, Integer.MIN_VALUE);
        int[] lengths = new int[array.length];

        Arrays.fill(lengths, 1);
        int maxLenIdx = 0;
        for (int i = 0; i < array.length; i++) {
            int curNum = array[i];
            for (int j = 0; j < i; j++) {
                int otherNum = array[j];
                if (otherNum < curNum && lengths[j] + 1 >= lengths[i]) {
                    lengths[i] = lengths[j] + 1;
                    sequences[i] = j;
                }
            }
            if (lengths[i] >= lengths[maxLenIdx]) {
                maxLenIdx = i;
            }
        }
        return buildSequence(array, sequences, maxLenIdx);
    }

    private static List<Integer> buildSequence(int[] array, int[] sequences, int maxLenIdx) {
        List<Integer> sequence = new ArrayList<>();
        while (maxLenIdx != Integer.MIN_VALUE) {
            sequence.add(0, array[maxLenIdx]);
            maxLenIdx = sequences[maxLenIdx];
        }
        return sequence;
    }

    // O(nlogn) time | O(n) space
    public static List<Integer> longestIncSeqBinarySearch(int[] array) {
        int[] sequences = new int[array.length];
        // array.length + 1 because 0th index is MIN_VALUE
        int[] smallestValueIndexForLongestIncSeq = new int[array.length + 1];
        Arrays.fill(smallestValueIndexForLongestIncSeq, Integer.MIN_VALUE);
        int longestLen = 0;
        for (int i = 0; i < array.length; i++) {
            int num = array[i];
            // start binary search at 1 because 0th index is MIN_VALUE
            int newLen = binarySearch(1, longestLen, smallestValueIndexForLongestIncSeq, array, num);
            sequences[i] = smallestValueIndexForLongestIncSeq[newLen - 1];
            smallestValueIndexForLongestIncSeq[newLen] = i;
            longestLen = Math.max(longestLen, newLen);
        }
        return buildSequence(array, sequences, smallestValueIndexForLongestIncSeq[longestLen]);
    }

    private static int binarySearch(int startIdx, int endIdx, int[] indices, int[] array, int num) {
        if (startIdx > endIdx) return startIdx;

        int middleIdx = (startIdx + endIdx) / 2;
        if (array[indices[middleIdx]] < num) {
            startIdx = middleIdx + 1; // ignore everything to the left
        } else {
            endIdx = middleIdx - 1; //ignore everything to the right
        }
        return binarySearch(startIdx, endIdx, indices, array, num);
    }

    public static List<Integer> longestIncreasingSubsequenceMySol(int[] array) {
        int[] longestLen = new int[array.length];
        int[] sequence = new int[array.length];
        int longestIdx = 0;
        Arrays.fill(longestLen, 1);
        Arrays.fill(sequence, Integer.MIN_VALUE);
        for (int idx = 0; idx < array.length; idx++) {
            int curNum = array[idx];
            for (int prevIdx = 0; prevIdx < idx; prevIdx++) {
                int prevNum = array[prevIdx];
                // NOTE only update longestLen when longestLen[prevIdx] + 1 > longestLen[idx]
                if (curNum > prevNum && longestLen[prevIdx] + 1 > longestLen[idx]) {
                    longestLen[idx] = longestLen[prevIdx] + 1;
                    sequence[idx] = prevIdx;
                }
            }
            if (longestLen[idx] > longestLen[longestIdx]) {
                longestIdx = idx;
            }
        }
        // Write your code here.
        return buildSolution(array, sequence, longestIdx);
    }


    private static List<Integer> buildSolution(int[] array, int[] sequence, int longestIdx) {
        List<Integer> result = new ArrayList<>();

        while (longestIdx != Integer.MIN_VALUE) {
            result.add(0, array[longestIdx]);
            longestIdx = sequence[longestIdx];
        }
        return result;
    }
}
