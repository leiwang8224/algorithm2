package AlgoExpert.Medium;

import java.util.ArrayList;

public class ThreeNumberSum {
    public static void main(String[] args) {

    }

    // O(n^2) time | O(n) space

    /**
     * two loops, one loop for the first number, second loop for left and right ptr movement
     */
    private static java.util.List<Integer[]> threeNumberSum(int[] array, int targetSum) {
        java.util.Arrays.sort(array);
        java.util.List<Integer[]> triplets = new ArrayList<>();

        // the first pivot point, goes up to length-2 due to the left and right ptrs
        // array[index] + array[left] + array[right] = target
        for (int index = 0; index < array.length-2; index++) {
            int left = index + 1;
            int right = array.length - 1;
            // left and right ptr moves towards each other
            while (left < right) {
                int curSum = array[index] + array[left] + array[right];
                if (curSum == targetSum) {
                    Integer[] newTriplet = {array[index], array[left], array[right]};
                    triplets.add(newTriplet);
                    left++;
                    right--;
                } else if (curSum < targetSum) {
                    left++;
                } else if (curSum > targetSum) {
                    right--;
                }
            }
        }
        return triplets;
    }
}
