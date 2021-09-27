package Arrays;

public class MaxConsecutiveOnes {
    public static void main(String[] args) {
        int[] array = new int[]{0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1};
        System.out.println(longestOnes(array, 3));
        System.out.println(longestOnes2(array, 3));
        System.out.println(longestOnes3(array, 3));
    }

//    For each A[endIndex], try to find the longest subarray.
//    If A[startIndex] ~ A[endIndex] has zeros <= K, we continue to increment j.
//    If A[startIndex] ~ A[endIndex] has zeros > K, we increment i (as well as j).
    private static int longestOnes(int[] A, int K) {
        int startIndex = 0;
        int endIndex;

        for (endIndex = 0; endIndex < A.length; endIndex++) {
            if (A[endIndex] == 0) K--;
            if (K < 0 && A[startIndex++] == 0) K++;
        }

        return endIndex - startIndex;
    }

    /**
     * endIndex just keep on increasing, startIndex gets adjusted
     * keep track of the number of contiguous 0's
     * Find the longest subarray with at most K zeros
     */
    private static int longestOnes2(int[] A, int K) {
        int zeroCount = 0;
        int startIndex = 0;
        int result = 0;
        for (int endIndex = 0; endIndex < A.length; endIndex++) {
            if (A[endIndex] == 0)
                zeroCount++;
            // when we reach K we will move the startIndex forward and dec zeroCount if element is 0
            while (zeroCount > K) {
                // subtract zeroCount from beginning and increment startIndex
                if (A[startIndex] == 0)
                    zeroCount--;
                startIndex++;
            }
            result = Math.max(result, endIndex - startIndex + 1);
        }
        return result;
    }

    /**
     * 1. count up the number of endIndex 0 occurrences and keep inc endIndex
     * 2. if occurrences of 0's are above K
     *    - if element at startIndex is 0, dec zeroCount
     *    - inc startIndex
     * 3. take max of result, endIndex-startIndex+1
     */
    private static int longestOnes3(int[] A, int K) {
        int zeroCount = 0;
        int startIndex = 0, result = 0;
        for (int endIndex = 0; endIndex < A.length; endIndex++) {
            if (A[endIndex] == 0)
                zeroCount++;
            if (zeroCount > K) {
                if (A[startIndex] == 0) zeroCount--;
                startIndex++;
            }
            result = Math.max(result, endIndex - startIndex + 1);
        }
        return result;
    }
}
