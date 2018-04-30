import java.util.ArrayList;
import java.util.Arrays;

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

    }

    private static void generateSumCombo(ArrayList<ArrayList<Integer>> result, ArrayList<Integer> subList, int[] nums, int remain, int start) {
        System.out.println("entering generateSumCombo remain = " + remain  + " subList = " + Arrays.toString(subList.toArray()));
        if (remain < 0) return;
        else if (remain== 0) result.add(new ArrayList<>(subList));
        else {
            for (int i = start; i < nums.length; i ++) {
                subList.add(nums[i]);
                System.out.println("subList after add " + Arrays.toString(subList.toArray()));
                generateSumCombo(result, subList, nums, remain - nums[i], i);
                subList.remove(subList.size()-1);
                System.out.println("subList after remove " + Arrays.toString(subList.toArray()));
            }
        }
    }
}
