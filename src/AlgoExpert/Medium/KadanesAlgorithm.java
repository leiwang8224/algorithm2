package AlgoExpert.Medium;

public class KadanesAlgorithm {
    public static void main(String[] args) {

    }

    /**
     * Use two vars to keep track of the local max and global max
     *
     */
    private static int kadanes(int[] array) {
        // init both vars to the first element
        int localMaxSum = array[0];
        int globalMaxSum = array[0];
        for (int index = 1; index < array.length; index++) {
            int num = array[index];
            // take the max between the current element and sum + current element
            localMaxSum = Math.max(num, localMaxSum + num);
            globalMaxSum = Math.max(globalMaxSum, localMaxSum);
        }
        return globalMaxSum;
    }
}
