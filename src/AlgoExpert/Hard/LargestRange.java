package AlgoExpert.Hard;
import java.util.*;
public class LargestRange {
    //O(n) time | O(n) space
    private static int[] largestRange(int[] array) {
        int[] bestRange = new int[2];
        // NEED to keep track of longestLen and left and right indices
        int longestLen = 0;
        // integer is the value, boolean is whether the value is valid
        // if we have already visited the value, label it false
        Map<Integer, Boolean> numsMap = new HashMap<>();
        for (int num : array) {
            numsMap.put(num, true);
        }

        for (int num : array) {
            // if not in map
            if (!numsMap.get(num)) {
                continue;
            }
            // if in map, update to false
            numsMap.put(num, false);
            // cur element is in map, so len is 1
            // curLen is used to keep track of the longest len
            int curLen = 1;
            // expand from cur element on both sides
            int left = num - 1;
            int right = num + 1;
            // if the num-1 exist in map. update the map value to false
            // inc len and dec left
            while (numsMap.get(left) != null) {
                numsMap.put(left, false);
                curLen ++;
                left --;  // left + 1
            }
            // if the num+1 exist in map. update the map value to false
            // dec len and inc right
            while (numsMap.get(right) != null) {
                numsMap.put(right, false);
                curLen ++;
                right ++;  // right - 1
            }
            if (curLen > longestLen) {
                longestLen = curLen;
                // remember to add 1 to left and sub 1 to right
                // due to while loop
                bestRange = new int[] {left + 1, right -1};
            }
        }
        return bestRange;
    }
}
