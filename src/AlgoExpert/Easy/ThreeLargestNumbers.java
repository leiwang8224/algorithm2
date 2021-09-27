package AlgoExpert.Easy;
import java.util.*;
public class ThreeLargestNumbers {
    public static void main(String[] args) {

    }

    // O(n) time | O(1) space
    private static int[] findThreeLargest(int[] array) {
        int[] threeLargest = new int[3];
        java.util.Arrays.fill(threeLargest, Integer.MIN_VALUE);
        for (int num : array)
            updateLargest(threeLargest, num);
        return threeLargest;
    }

    private static void updateLargest(int[] threeLargest, int num) {
        // starting from the right and check if current number is larger than
        // array element, if it is larger shift left and place it in the index
        if (num > threeLargest[2]) {
            shiftAndUpdate(threeLargest, num, 2);
        } else if (num > threeLargest[1]) {
            shiftAndUpdate(threeLargest, num, 1);
        } else if (num > threeLargest[0]) {
            shiftAndUpdate(threeLargest, num, 0);
        }
    }

    // left shift the leftmost digits and place num in threeLargest[index]
    private static void shiftAndUpdate(int[] threeLargest, int num, int updateIdx) {
        for (int leftmostIndex = 0; leftmostIndex <= updateIdx; leftmostIndex++) {
            if (leftmostIndex == updateIdx) threeLargest[leftmostIndex] = num;
            else threeLargest[leftmostIndex] = threeLargest[leftmostIndex+1];
        }
    }

    public static int[] findThreeLargestNumbersMySol(int[] array) {
        int[] threeLargest = new int[3];
        Arrays.fill(threeLargest, Integer.MIN_VALUE);

        for (int idx = 0; idx < array.length; idx++) {
            if (array[idx] > threeLargest[2]) {
                shiftLeft(threeLargest, 2, array[idx]);
            } else if (array[idx] > threeLargest[1]) {
                shiftLeft(threeLargest, 1, array[idx]);
            } else if (array[idx] > threeLargest[0]) {
                shiftLeft(threeLargest, 0, array[idx]);
            }
        }

        // Write your code here.
        return threeLargest;
    }

    private static void shiftLeft(int[] threeLargest, int idx, int num) {
        if (idx == 2) {
            threeLargest[0] = threeLargest[1];
            threeLargest[1] = threeLargest[2];
            threeLargest[2] = num;
        } else if (idx == 1) {
            threeLargest[0] = threeLargest[1];
            threeLargest[1] = num;
        } else {
            threeLargest[0] = num;
        }
    }
}
