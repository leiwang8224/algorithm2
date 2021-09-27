package AlgoExpert.Hard;
import java.util.*;
public class FourNumberSum {
    /**
     * build a hashmap of <sum, indices>
     *     build the map from the indices before the current index
     *     add to result based on the indices after the current index
     */
    // O(n^2) time | O(n^2) space
    // hashmap to cache the numbers before the current index
    // another index after the current index to get the current sum and calculate the leftover
    // for current index
    private static List<Integer[]> fourNumberSum(int[] array, int targetSum) {
        Map<Integer, List<Integer[]>> allPairSums = new HashMap<>();
        List<Integer[]> result = new ArrayList<>();

        // 3 for loops, note the third for loop is to preventing double counting
        for (int mainIdx = 1; mainIdx < array.length - 1; mainIdx++) {
            for (int indexAfter = mainIdx + 1; indexAfter < array.length; indexAfter++) {
                // get sum for indices after i
                int curSum = array[mainIdx] + array[indexAfter];
                int leftover = targetSum - curSum;
                // look for the diff in the map
                if (allPairSums.containsKey(leftover)) {
                    // if diff is in map, iterate through pairs and add to quad
                    // you want all of the pairs that add up to leftover
                    for (Integer[] pair : allPairSums.get(leftover)) {
                        Integer[] newQuadruplet = {pair[0],
                                                    pair[1],
                                                    array[mainIdx],
                                                    array[indexAfter]};
                        result.add(newQuadruplet);
                    }
                }
            }
            // this for loop is to prevent us double counting the numbers in the array
            // iterate from beginning to the index and put into map if it doesn't exist
            for (int indexBefore = 0; indexBefore < mainIdx; indexBefore++) {
                int curSum = array[mainIdx] + array[indexBefore];
                // create the pair of indices after index i
                Integer[] pair = {array[indexBefore], array[mainIdx]};
                // look for the curSum in the map, if not exist add to map
                if (!allPairSums.containsKey(curSum)) {
                    List<Integer[]> pairGroup = new ArrayList<>();
                    pairGroup.add(pair);
                    allPairSums.put(curSum, pairGroup);
                } else { // if exist append to existing key
                    allPairSums.get(curSum).add(pair);
                }
            }
        }
        return result;
    }
}
