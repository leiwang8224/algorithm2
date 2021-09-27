package Hash;

import java.util.HashMap;

public class LongestArithSubsequence {
    public static void main(String[] args) {

    }

    /**
     * Explanation
     *
     * dp[index][diff] equals to the length of
     * arithmetic sequence at index with difference diff.
     *
     * Complexity
     *
     * Time O(N^2)
     * Space O(N^2)
     */
    private static int longestArithSub (int[] A) {
        int longest = 2;
        // array of hashmaps with length A.length
        // hashmap of <diff, longestLen>
        HashMap<Integer, Integer>[] dp = new HashMap[A.length];
        for (int index = 0; index < A.length; index++) {
            dp[index] = new HashMap<>();
            // iterate through the values to the left of index
            for (int indexUpTo = 0; indexUpTo < index; indexUpTo++) {
                int diff = A[index] - A[indexUpTo];
                dp[index].put(diff, dp[indexUpTo].getOrDefault(diff, 1) + 1);
                longest = Math.max(longest, dp[index].get(diff));
            }
        }
        return longest;
    }

//    The main idea is to maintain a map of differences seen at each index.
//
//    We iteratively build the map for a new index i, by considering all elements
//    to the left one-by-one.
//    For each pair of indices (i,j) and difference d = A[i]-A[j] considered,
//    we check if there was an existing chain at the index j with difference d already.
//
//    If yes, we can then extend the existing chain length by 1.
//    Else, if not, then we can start a new chain of length 2 with this
//    new difference d and (A[j], A[i]) as its elements.
//
//    At the end, we can then return the maximum chain length that we have seen so far.
//
//    Time Complexity: O(n^2)
//    Space Complexity: O(n^2)

    private static int longestArithSeqLength(int[] A) {
        if (A.length <= 1) return A.length;

        int longest = 0;

        // Declare a dp array that is an array of hashmaps.
        // The map for each index maintains an element of the form-
        //   (difference, length of max chain ending at that index with that difference).
        HashMap<Integer, Integer>[] dp = new HashMap[A.length];

        for (int index = 0; index < A.length; ++index) {
            // Initialize the map.
            dp[index] = new HashMap<>();
        }

        for (int index = 1; index < A.length; ++index) {
            int curElement = A[index];
            // Iterate over values to the left of i.
            for (int indexUpTo = 0; indexUpTo < index; ++indexUpTo) {
                int prevElement = A[indexUpTo];
                int diff = curElement - prevElement;

                // We at least have a minimum chain length of 2 now,
                // given that (A[j], A[i]) with the difference d,
                // by default forms a chain of length 2.
                int len = 2;

                if (dp[indexUpTo].containsKey(diff)) {
                    // At index j, if we had already seen a difference d,
                    // then potentially, we can add A[i] to the same chain
                    // and extend it by length 1.
                    len = dp[indexUpTo].get(diff) + 1;
                }

                // Obtain the maximum chain length already seen so far at index i
                // for the given differene d;
                int curr = dp[index].getOrDefault(diff, 0);

                // Update the max chain length for difference d at index i.
                dp[index].put(diff, Math.max(curr, len));

                // Update the global max.
                longest = Math.max(longest, dp[index].get(diff));
            }
        }

        return longest;
    }
}
