package AlgoExpert.Medium;

public class MaxSubsetSumNoAdjacent {
    public static void main(String[] args) {

    }

    /**
     * Main idea here is to accumulate the MAX Sum so far so that
     * you only need the last two elements in the dp array to calculate
     * the current element in the dp array
     * Build up the solution from the beginning, consider what is the max sum with
     * array[0]? array[0..1]? array[0..2]? array[0..3]? etc
     */
    // Assuming all elements in array is positive
    // maxSums[i] = max(maxSums[i-1], maxSums[i-2] + array[i])
    // O(n) time | O(n) space
    private static int maxSubsetSumNoAdjacent(int[] array) {
        if (array.length == 0) {
            return 0;
        } else if (array.length == 1) {
            return array[0];
        }
        int[] maxSums = new int[array.length];
        maxSums[0] = array[0];
        maxSums[1] = Math.max(array[0], array[1]);
        for (int index = 2; index < array.length; index++) {
            maxSums[index] = Math.max(maxSums[index-1],
                    maxSums[index-2] + array[index]);
        }
        return maxSums[array.length-1];
    }

    // O(n) time | O(1) space
    private static int maxSubsetSumNoAdjacent2(int[] array) {
        if (array.length == 0) {
            return 0;
        } else if (array.length == 1) {
            return array[0];
        }

        int twoPrev = array[0];
        int onePrev = Math.max(array[0], array[1]);
        for (int index = 2; index < array.length; index++) {
            int current = Math.max(onePrev, twoPrev + array[index]);
            twoPrev = onePrev;
            onePrev = current;
        }
        return onePrev;
    }
}
