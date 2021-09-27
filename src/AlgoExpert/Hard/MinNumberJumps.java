package AlgoExpert.Hard;
import java.util.*;
public class MinNumberJumps {
    // O(n ^ 2) time | O(n) space
    private static int minNumberJumps (int[] array) {
        int[] minJumps = new int[array.length];
        // initialize jumps with infinte values
        Arrays.fill(minJumps, Integer.MAX_VALUE);
        // it takes 0 jumps to get to 0th index
        minJumps[0] = 0;

        for (int arrayIdx = 1; arrayIdx < array.length;arrayIdx++) {
            for (int prevIdx = 0; prevIdx < arrayIdx; prevIdx++) {
                // check if the jump value is greater than the amount needed to
                // reach index, take the min(previous jump + 1, current jump value)
                if (array[prevIdx] + prevIdx >= arrayIdx) {
                    minJumps[arrayIdx] = Math.min(minJumps[prevIdx] + 1, minJumps[arrayIdx]);
                }
            }
        }
        return minJumps[minJumps.length - 1];
    }

    /**
     * My Solution
     */
    public static int minNumberOfJumpsMySol(int[] array) {
        int[] minJumps = new int[array.length];
        Arrays.fill(minJumps, Integer.MAX_VALUE);
        minJumps[0] = 0;

        for (int idx = 1; idx < array.length; idx++) {
            for (int prevIdx = 0; prevIdx < idx; prevIdx++) {
                if (prevIdx + array[prevIdx] >= idx) {
                    minJumps[idx] = Math.min(minJumps[prevIdx] + 1, minJumps[idx]);
                }
            }
        }
        // Write your code here.
        return minJumps[minJumps.length-1];
    }

    // O(n) time | O (1) space
    // initialize stepsInJump to the first element, iterate from 1 to -1 and
    // update the stepsInJump with the maxReach-1
    private static int minNumberJumps2(int[] array) {
        if (array.length == 1) return 0;
        // number of jumps it takes, increment when steps reach 0
        int jumps = 0;
        // the max reach at any one index
        int prevMaxReach = array[0];
        // steps to take
        int stepsInJump = array[0];

        // iterate from index 1 to -1, get max reach, dec steps
        for (int index = 1; index < array.length - 1; index++) {
            // update maxReach with the new index value, find the farthest reaching value
            prevMaxReach = Math.max(prevMaxReach, index + array[index]);
            stepsInJump--;
            // when steps reach 0 inc jumps and update steps
            if (stepsInJump == 0) {
                // need to jump since we reached the end of the steps in jump
                jumps ++;
                // update the stepsInJump for the new jump
                stepsInJump = prevMaxReach - 1;
            }
        }
        // add 1 since we still have 1 jump not consumed
        return jumps + 1;
    }
}
