import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

import static Math.NextPermutation.swap;

/**
 * Created by leiwang on 3/10/18.
 */

/**
 * Given a set of candidate numbers (candidates) (without duplicates) and a target
 * number (target), find all unique combinations in candidates where the candidate
 * numbers sums to target.

 The same repeated number may be chosen from candidates unlimited number of times.

 Note:

 All numbers (including target) will be positive integers.
 The solution set must not contain duplicate combinations.

 Example 1:

 Input: candidates = [2,3,6,7], target = 7,
 A solution set is:
 [
 [7],
 [2,2,3]
 ]

 */
public class CombinationSum {
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5};
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        generateSumCombo(result, new ArrayList<>(), nums, 5, 0);
        for (ArrayList<Integer> subList : result) {
            System.out.println(Arrays.toString(subList.toArray()));
        }

        ArrayList<ArrayList<Integer>> resultBit = findSumCombos(nums, 5);
        for (ArrayList<Integer> subList : resultBit) {
            System.out.println(Arrays.toString(subList.toArray()));
        }

        List<List<Integer>> resultDP = findSumCombosDP(nums, 5);
        for (List<Integer> subList : resultDP) {
            System.out.println(Arrays.toString(subList.toArray()));
        }
    }

    /**
     * Time complexity is O(N^target) where N is a length of candidates array.
     * Space complexity is O(target).
     * for each element in the array, you make 1 decision, to take the number
     * or not take the number, form binary tree.
     * @param result
     * @param subList
     * @param nums
     * @param remain
     * @param indexToStart
     */
    private static void generateSumCombo(ArrayList<ArrayList<Integer>> result, ArrayList<Integer> subList, int[] nums, int remain, int indexToStart) {
        System.out.println("entering generateSumCombo remain = " + remain  + " subList = " + Arrays.toString(subList.toArray()));
        // has reached below zero, simply return
        if (remain < 0) return;
        // reached the sum target, so add to list of solutions
        else if (remain== 0) result.add(new ArrayList<>(subList));
        else {
            // iterate through the array and add to sublist
            // call recursively to populate the result
            for (int indexForNums = indexToStart; indexForNums < nums.length; indexForNums ++) {
                subList.add(nums[indexForNums]);
                System.out.println("subList after add " + Arrays.toString(subList.toArray()));
                generateSumCombo(result, subList, nums, remain - nums[indexForNums], indexForNums); // do not increment i since we can reuse num
                subList.remove(subList.size()-1);
                System.out.println("subList after remove " + Arrays.toString(subList.toArray()));
            }
        }
    }

    private static List<List<Integer>> findSumCombosDP(int[] cands, int totalToAdd) {
        Arrays.sort(cands); // sort candidates to try them in asc order
        List<List<List<Integer>>> dp = new ArrayList<>();
        for (int index = 1; index <= totalToAdd; index++) { // run through all targets from 1 to t
            List<List<Integer>> newList = new ArrayList(); // combs for curr i
            // run through all candidates <= i
            for (int indexUpTo = 0; indexUpTo < cands.length && cands[indexUpTo] <= index; indexUpTo++) {
                // special case when curr target is equal to curr candidate
                if (index == cands[indexUpTo]) newList.add(Arrays.asList(cands[indexUpTo]));
                    // if current candidate is less than the target use prev results
                else for (List<Integer> l : dp.get(index-cands[indexUpTo]-1)) {
                    if (cands[indexUpTo] <= l.get(0)) {
                        List cl = new ArrayList<>();
                        cl.add(cands[indexUpTo]);
                        cl.addAll(l);
                        newList.add(cl);
                    }
                }
            }
            dp.add(newList);
        }
        return dp.get(totalToAdd-1);
    }

    private static ArrayList<ArrayList<Integer>> findSumCombos(int[] nums, int target) {
        int i, j;
        int numsLength = nums.length;
        ArrayList<Integer> subList = new ArrayList<>();
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        // rotate left n bits
        for (i = 0; i < (1<<numsLength); i ++) {
            int sum = 0;
            for (j = 0; j < numsLength; j++) {
                if ((i&(1<<j)) != 0) {
                    sum += nums[j];
                    subList.add(nums[j]);
                }
            }
            if (sum == target) {
                result.add(subList);
            }
        }

        return result;
    }

    //TODO come up with the brute force method
    private static ArrayList<ArrayList<Integer>> generateSumComboBruteForce(int[] nums, int remain) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        permute(result, new ArrayList<>(), nums, 0, remain);
        return result;
    }

    private static void permute(ArrayList<ArrayList<Integer>> result,
                                ArrayList<Object> subList,
                                int[] nums,
                                int pos,
                                int remain) {
        if (pos == nums.length - 1) {
            int i;
            for (i = 1; i <= nums.length; i ++) {
                if (remain == 0)
                    break;
            }
//            if (i == nums.length + 1)
        }

        for (int i = pos; i < nums.length; i++) {
            swap(nums, i, pos);
            permute(result, subList, nums, pos, remain);
            swap(nums, i, pos);
        }
    }
}
