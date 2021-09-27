package AlgoExpert.Easy;

import java.util.ArrayList;

public class ValidateSubsequence {
    public static void main(String[] args) {
        java.util.List<Integer> array = new ArrayList<>(java.util.Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10));
        java.util.List<Integer> sub = new ArrayList<>(java.util.Arrays.asList(1, 6, -1, 10));

        System.out.println(isValidSubsequence(array, sub));

    }

    // O(n) time | O(1) space where n is length of array

    /**
     * for each element in subsequence, iterate through array to find it, if its found increment
     * subsequence index, arrayIndex increments regardless.
     */
    private static boolean isValidSubsequence(java.util.List<Integer> array,
                                              java.util.List<Integer> subseqence) {
        int arrayIndex = 0;
        int sequenceIndex = 0;

        while (arrayIndex < array.size() && sequenceIndex < subseqence.size()) {
            // note that == operator is checking for reference equality
            // or if they are the same object
            // equals() checks for value equality
            if (array.get(arrayIndex).equals(subseqence.get(sequenceIndex))) {
                sequenceIndex++;
            }
            arrayIndex++;
        }
        return sequenceIndex == subseqence.size();
    }

    // O(n) time | O(1) space where n is length of array
    private static boolean isValidSubsequence2(java.util.List<Integer> array,
                                               java.util.List<Integer> subsequence) {
        int seqIndex = 0;
        for (int value : array) {
            if (seqIndex == subsequence.size()) break;
            if (subsequence.get(seqIndex).equals(value)) seqIndex++;
        }
        return seqIndex == subsequence.size();
    }
}
