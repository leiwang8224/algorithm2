package Arrays;

// LC-152
public class MaxProductSubarray {
    public static void main(String[] args) {

    }

    private static int maxProduct(int[] A) {
        if (A.length == 0) {
            return 0;
        }

        int localMaxBefore = A[0];
        int localMinBefore = A[0];
        int globalMax = A[0];
        int localMax, localMin;

        // maintain localMax and localMin and take the max of the two
        // for global max. The reason to maintain the localMin is in case
        // we have a negative number which could possibly become greater than
        // the localMax (ex: negative multiply by negative yields positive)
        for (int index = 1; index < A.length; index++) {
            // take max of:
            // - localMaxBefore * A[index] is always positive
            // - localMinBefore * A[index] may turn from negative to positive
            localMax = Math.max(Math.max(localMaxBefore * A[index],
                    localMinBefore * A[index]),
                    A[index]);
            // take the local min since it may become the local max
            // take min of:
            // - localMaxBefore * A[index]
            // - localMinBefore * A[index]
            localMin = Math.min(Math.min(localMaxBefore * A[index],
                    localMinBefore * A[index]),
                    A[index]);
            // update globalMax
            globalMax = Math.max(localMax, globalMax);
            // update localMaxBefore and localMinBefore for next iteration
            localMaxBefore = localMax;
            localMinBefore = localMin;
        }
        return globalMax;
    }

    private static int maxProduct2(int[] A) {
        int result = A[0], left = 0, right = 0;
        for (int index = 0; index < A.length; index++) {
            left = (left == 0 ? 1: left) * A[index];
            right = (right == 0 ? 1: right) * A[A.length - 1 - index];
            result = Math.max(result, Math.max(left, right));
        }

        return result;
    }

    private static int maxProductDP(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        int[] maxProductEndingInIndex = new int[A.length];
        int[] minProductEndingInIndex = new int[A.length];
        maxProductEndingInIndex[0] = A[0];
        minProductEndingInIndex[0] = A[0];
        int result = A[0];
        for (int index = 1; index < A.length; index++) {
            maxProductEndingInIndex[index] = Math.max(Math.max(maxProductEndingInIndex[index - 1] * A[index],
                                                            minProductEndingInIndex[index - 1] * A[index]),
                                                            A[index]);
            minProductEndingInIndex[index] = Math.min(Math.min(maxProductEndingInIndex[index - 1] * A[index],
                                                            minProductEndingInIndex[index - 1] * A[index]),
                                                            A[index]);
            result = Math.max(result, maxProductEndingInIndex[index]);
        }
        return result;
    }
}
