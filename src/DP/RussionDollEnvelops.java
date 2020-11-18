package DP;

import java.util.Comparator;
import java.util.HashMap;

public class RussionDollEnvelops {
    public static void main(String[] args) {
        System.out.println(maxEnvelopes(new int[][] {{5,4}, {6,4}, {6,7}, {2,3}}));
        System.out.println(maxEnvelopes(new int[][] {{4,5}, {4,6}, {6,7}, {2,3}, {1,1}}));
        System.out.println(maxEnvelopBruteForce(new int[][] {{5,4}, {6,4}, {6,7}, {2,3}}));
        System.out.println(maxEnvelopBruteForce(new int[][] {{4,5}, {4,6}, {6,7}, {2,3}, {1,1}}));

    }

    /**
     * Does not meet time requirement but a good way to understand the solution
     * @param envelops
     * @return
     */
    private static int maxEnvelopBruteForce(int[][] envelops) {
        // sort in increasing order by any dimension
        if (envelops.length == 0) return 0;
        java.util.Arrays.sort(envelops, new EnvelopesComparator());

        // we will cache hits
        HashMap<Integer, Integer> cache = new HashMap<>();
        return maxEnvelopsRecurse(envelops, 0, -1, cache);

    }

    private static int maxEnvelopsRecurse(int[][] envelops, int curIndex, int prevIndex, HashMap<Integer, Integer> cache) {
        if (curIndex == envelops.length) return 0;

        int numberIncluded = 0;

        // width greater and height greater OR first recursion iteration
        if (prevIndex == -1 || (envelops[curIndex][0] > envelops[prevIndex][0] &&
                                envelops[curIndex][1] > envelops[prevIndex][1])) {
            if (cache.containsKey(curIndex)) numberIncluded = cache.get(curIndex);
            else {
                // increment index and set index as the prevIndex
                numberIncluded = 1 + maxEnvelopsRecurse(envelops, curIndex+1, curIndex, cache);
                // note that hits are only cached for an index
                // when we know that the condition that prev is
                // less than current is satisfied
                cache.put(curIndex, numberIncluded);
            }
        }

        // increment index and set prevIndex as prevIndex
        int numberSkipped = maxEnvelopsRecurse(envelops, curIndex+1, prevIndex, cache);

        return Math.max(numberSkipped, numberIncluded);
    }

    /**
     * This problem is asking for LIS in two dimensions,
     * width and height. Sorting the width reduces the problem
     * by one dimension. If width is strictly increasing, the
     * problem is equivalent to finding LIS in only the height
     * dimension. However, when there is a tie in width, a
     * strictly increasing sequence in height may not be a correct
     * solution. For example, [[3,3] cannot fit in [3,4]]. Sorting
     * height in descending order when there is a tie prevents such
     * a sequence to be included in the solution.
     *
     * The same idea can be applied to problems of higher dimensions.
     * For example, box fitting is three dimensions, width, height, and length.
     * Sorting width ascending and height descending reduces the problem by
     * one dimension. Finding the LIS by height further reduces the problem
     * by another dimension. When find LIS based on only length, it becomes
     * a standard LIS problem.
     *
     * 1. sort by width then height if width is same descending
     * 2. use LIS algorithm to find the longest increasing subsequence
     * time O(N^2)
     * space O(N)
     */
    public static int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0) {
            return 0;
        }

        System.out.println("before sorting");
        for (int[] envelop : envelopes) {
            System.out.print((java.util.Arrays.toString(envelop) + ","));
        }
        System.out.println();
        // sort by width (envelops[i][0]), then we only need consider height (LIS on height array)
        // if width same then sort height descend
        java.util.Arrays.sort(envelopes, new EnvelopesComparator());

        System.out.println("after sorting");
        for (int[] envelop : envelopes) {
            System.out.print(java.util.Arrays.toString(envelop) + ",");
        }

        // find LIS based on height
        int[] dpHeight = new int[envelopes.length];
        int maxLen = 1;

        for (int curEnvelopIndex = 0; curEnvelopIndex < envelopes.length; curEnvelopIndex++) {
            // the minimum longest increasing subsequence length is 1
            dpHeight[curEnvelopIndex] = 1;
            for (int indexFrom0ToCurEnvelopIndex = 0; indexFrom0ToCurEnvelopIndex < curEnvelopIndex; indexFrom0ToCurEnvelopIndex++) {
                // if height and width of curEnvelop is greater than previous envelope
                if (envelopes[curEnvelopIndex][0] > envelopes[indexFrom0ToCurEnvelopIndex][0] &&
                        envelopes[curEnvelopIndex][1] > envelopes[indexFrom0ToCurEnvelopIndex][1]) {
                    // update dp[curEnvelopIndex] value with the max for the current value in dp and previous value from dp + 1
                    dpHeight[curEnvelopIndex] = Math.max(dpHeight[curEnvelopIndex], dpHeight[indexFrom0ToCurEnvelopIndex] + 1);
                    maxLen = Math.max(maxLen, dpHeight[curEnvelopIndex]);
                }
            }
        }

        return maxLen;
    }

    /**
     * sort based on width and descend on height if width are the same
     */
    public static class EnvelopesComparator implements Comparator<int[]> {
        @Override
        public int compare(int[] a, int[] b) {
            if (a[0] - b[0] != 0) {
                return a[0] - b[0];
            }

            return a[1] - b[1];
        }
    }

    /**
     * Use binary search to perform LIS
     * @param envelopes
     * @return
     */
    // O(NlogN) for time
    public static int maxEnvelopsBinarySearch(int[][] envelopes) {
        if(envelopes == null || envelopes.length == 0
                || envelopes[0] == null || envelopes[0].length != 2)
            return 0;
        // sort by width first then height
        java.util.Arrays.sort(envelopes, new EnvelopesComparator());
        int dpHeights[] = new int[envelopes.length];
        int longestIncSeq = 0;
        // traversing to process the heights
        for(int[] envelope : envelopes){
            // search for envelop[1], or a certain height in dp table
            int indexOrInsertionPtOfMissing = java.util.Arrays.binarySearch(dpHeights,
                                                0,
                                                        longestIncSeq,
                                                        envelope[1]);
            // if not found (-(insertion point) - 1),
            // which is -(index+1) to find insertion point
            if(indexOrInsertionPtOfMissing < 0)
                indexOrInsertionPtOfMissing = -(indexOrInsertionPtOfMissing + 1);
            // insert height into the dp table
            dpHeights[indexOrInsertionPtOfMissing] = envelope[1];
            if(indexOrInsertionPtOfMissing == longestIncSeq)
                longestIncSeq++;
        }
        return longestIncSeq;
    }



}
