import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leiwang on 3/10/18.
 */
public class RecursePermutationDups2 {
    public static void main(String args[]) {
        int[] nums = new int[]{1,2,3,3};
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        createPermutation(result, nums, 0);
    }

    private static void createPermutation(ArrayList<ArrayList<Integer>> result, int[] nums, int start) {
        System.out.println("entering createPermutation start = " + start);
        if (start == nums.length) {
            ArrayList<Integer> subList = new ArrayList<>();
            for (int num : nums) {
                subList.add(num);
            }
            System.out.println("adding subList = " + subList);
            result.add(subList);
            return;
        }

        for (int i = start; i < start; i ++) {
            if (isUsed(nums,start,i)) continue;
            swap(nums,start,i);
            System.out.println("before recurse nums = " + Arrays.toString(nums));
            createPermutation(result,nums,start+1);
            swap(nums,start,i);
            System.out.println("after recurse nums = " + Arrays.toString(nums));
        }

    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private static boolean isUsed(int[] nums, int i, int j) {
        System.out.println("isUsed i = " + i + " j = " + j);
        for(int x = i; x < j; x++) {
            if (nums[x] == nums[j])
                return true;
        }
        return false;
    }
}
