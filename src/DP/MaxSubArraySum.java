package DP;

/**
 * Created by leiwang on 3/25/18.
 */
public class MaxSubArraySum {
    public static void main(String args[]) {
        int[] array = new int[] {-2,3,5,3,2,4,45,2,3,45,6,64,2,4};
       System.out.println("brute force method = " + maxSubArraySumForce(array, array.length));
        System.out.println("kadane's method = " + maxSubArraySumKadaneAlgorithm(array,array.length));
    }

    //Recursive method = M(n) = max(M(n-1) + A[n], A[n])

    private static int maxSubArraySumForce(int[] array, int length) {
        int maxSum = 0;
        int tempSum = 0;
        for (int i = 0; i < length; i ++) {
            tempSum = array[i];
            for (int j = i + 1; j < length; j ++) {
                tempSum += array[j];
                if (tempSum > maxSum) {
                    maxSum = tempSum;
                }
            }
        }
        return maxSum;
    }

    /**
     * using Kadane's algorithm
     * @param array
     * @param length
     * @return
     */
    private static int maxSubArraySumKadaneAlgorithm(int[] array, int length) {
        int maxSumSoFar = 0;
        int maxSumEndingHere = 0;
        for (int i = 0; i < length; i++) {
            maxSumEndingHere = maxSumEndingHere + array[i];
            if (maxSumEndingHere < 0)
                maxSumEndingHere = 0;
            if (maxSumSoFar < maxSumEndingHere)
                maxSumSoFar = maxSumEndingHere;
        }
        return maxSumSoFar;
    }
}
