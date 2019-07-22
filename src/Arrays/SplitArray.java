package Arrays;

public class SplitArray {
    public static void main(String[] args) {
        int[] input = new int[]{1,2,3,4};
        System.out.println(splitArray(input));




    }

    private static boolean splitArray(int[] arr) {
        if (arr.length == 0) return false;

        // sum up the array
        int arrSum = 0;
        for (int item : arr)
            arrSum += item;

        // if sum is an odd number, it is impossible to split the array of elements evenly.
        if (arrSum % 2 != 0) return false;

        // if sum is an even number, check if the summation to half is possible
        return groupSum(0, arr, arrSum / 2);
    }

    // recursive cases where one could include the number in the next array index
    // or not include
    private static boolean groupSum(int startIndex, int[] arr, int target) {
        // base case: there is no number left
        if (startIndex >= arr.length) {
            return (target == 0);
        } else {
            // recursive case 1: include the 1 st number, check the remain
            if (groupSum(startIndex+1, arr, target-arr[startIndex])) return true;

            // recursive case 2: does not include the 1 st number, check the remain
            if (groupSum(startIndex+1, arr, target)) return true;
        }

        return false;
    }
}
