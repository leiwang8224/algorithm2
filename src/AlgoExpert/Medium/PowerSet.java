package AlgoExpert.Medium;

import java.util.*;
public class PowerSet {
    public static void main(String[] args) {
        List<List<Integer>> result = powersetIterative(Arrays.asList(new Integer[]{1,2,3}));
        for (List<Integer> ind : result) {
            System.out.println(ind.toString());
        }
    }

    // O(n * 2 ^n) time | (O(n*2^n) space
    private static List<List<Integer>> powersetRecursive(List<Integer> array) {
        return powerset(array, array.size() - 1);
    }

    /**
     * Recursive method to call from last index and recurse back to -1
     * Although the simpler solution is the iterative approach
     */
    private static List<List<Integer>> powerset(List<Integer> array, int index) {
        // return when index is < 0
        if (index < 0) {
            List<List<Integer>> emptySet = new ArrayList<>();
            emptySet.add(new ArrayList<>());
            return emptySet;
        }

        // if index >= 0 then get last element and recurse backwards
        int lastElementFromArray = array.get(index);
        // put call on stack for indices 0 to index-1
        List<List<Integer>> result = powerset(array, index-1);
        // get len of each result returned
        int lenResult = result.size();
        // iterate through the subset from the result and add last element from array
        for (int idx = 0; idx < lenResult; idx++) {
            List<Integer> curSubList = new ArrayList<>(result.get(idx));
            curSubList.add(lastElementFromArray);
            result.add(curSubList);
        }
        return result;
    }

    // O(n * 2 ^n) time | O(n * 2 ^n) space
    // start by adding empty list to result
    // iterate through elements in the array
    // - get length of result (start at 1)
    // - iterate through the result to get sublist
    // - append element from array to sublist
    // - add sub-list back to result
//    []       element, idxResult
//    [1]        1,         0
//    [2]        2,         0
//    [1, 2]     2,         1
//    [3]        3,         0
//    [1, 3]     3,         1
//    [2, 3]     3,         2
//    [1, 2, 3]  3,         3
//    arrayIdx=0
//      sizeIdx=0
//    arrayIdx=1
//      sizeIdx=0
//      sizeIdx=1
//    arrayIdx=2
//      sizeIdx=0
//      sizeIdx=1
//      sizeIdx=2
    // SUMMARIZE: for each element in array iterate from 0 to size of result
    private static List<List<Integer>> powersetIterative(List<Integer> array) {
        List<List<Integer>> result = new ArrayList<>();
        // add empty set
        result.add(new ArrayList<Integer>());
        // two for loops, one to iterate through the given array
        // another to iterate through the result array and append elements from given array
        for (Integer elementFromArray : array) {
            // lenResult starts from 1 since we have added empty set
            int lenAllResults = result.size();
            // for each subset already in the result, add the current element from array
            for (int idxResult = 0; idxResult < lenAllResults; idxResult++) {
                System.out.println("element from array:" + elementFromArray + " idxResult:" + idxResult);

                // create new subset based on the previous subset from result by adding new element
                // get result from last iteration and add on the element
                List<Integer> subListFromResult = new ArrayList<>(result.get(idxResult));
                subListFromResult.add(elementFromArray);
                // add to the result
                result.add(subListFromResult);
            }
        }
        return result;
    }

    private static List<List<Integer>> powersetIterativeMySol(List<Integer> array) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());

        for (int arrayIdx = 0; arrayIdx < array.size(); arrayIdx++) {
            int resultSize = result.size();
            for (int resultIdx = 0; resultIdx < resultSize; resultIdx++) {
                List<Integer> curResult = new ArrayList<>(result.get(resultIdx));
                curResult.add(array.get(arrayIdx));
                result.add(curResult);
            }
        }
        return result;
    }

    // FROM CTCI
    /**
     * Starting with the base case: n = 0
     * There is just one subset of the empty set {}
     * Case: n = 1
     * There are two subsets of the set {a1}: {}{a1}
     * Case: n = 2
     * There are four subsets of the set {a1, a2}: {}, {a1}, {a2}, {a1, a2}
     * Case: n = 3
     * P(2) = {}, {a1}, {a2}, {a1, a2}
     * P(3) = {}, {a1}, {a2}, {a3}, {a1, a2}, {a1, a3}, {a2, a3}, {a1,a2,a3}
     *
     * The difference between the solutions for n = 3 and n = 2 is P(2) is missing
     * all the subsets containing a3:
     * P(3) - P(2) = {a3}, {a1,a3}, {a2,a3}, {a1,a2,a3}
     * How can we use P(2) to create P(3)? We can simply clone the subsets in P(2) to
     * add a3 to them:
     * P(2) = {}, {a1}, {a2}, {a1, a2}
     * P(2) + a3 = {a3}, {a1,a3}, {a2,a3}, {a1,a2,a3}
     * When merged together we make P(3)
     * @param nums
     * @return
     */
    private static ArrayList<ArrayList<Integer>> generateSets(ArrayList<Integer> nums) {
        return generateSets(nums, 0);

    }

    private static ArrayList<ArrayList<Integer>> generateSets(ArrayList<Integer> nums, int index) {
        ArrayList<ArrayList<Integer>> allSubsets;
        if (nums.size() == index) {  // base case - add empty set, when at the last index
            allSubsets = new ArrayList<ArrayList<Integer>>();
            allSubsets.add(new ArrayList<>()); // empty set
        } else {
            allSubsets = generateSets(nums, index + 1);
            int item = nums.get(index);
            ArrayList<ArrayList<Integer>> moreSubssets = new ArrayList<>();
            for (ArrayList<Integer> subset : allSubsets) {
                ArrayList<Integer> newSubset = new ArrayList<>();
                newSubset.addAll(subset);
                newSubset.add(item);
                moreSubssets.add(newSubset);
            }
            allSubsets.addAll(moreSubssets);
        }
        return allSubsets;
    }
}
