import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leiwang on 3/10/18.
 */
public class RecurseSubsetsDups {
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,4};
        Arrays.sort(nums);
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        getSubsets(result, new ArrayList<>(), nums, 0);
        for (ArrayList<Integer> list : result) {
            System.out.println(Arrays.toString(list.toArray()));
        }
    }

    private static void getSubsets(ArrayList<ArrayList<Integer>> result, ArrayList<Integer> subList, int[] nums, int start) {
        result.add(new ArrayList<>(subList));
        for (int i = start; i < nums.length; i ++) {
            if (i > 0 && nums[i] == nums[i-1]) continue;
            subList.add(nums[i]);
            getSubsets(result,subList,nums,start + 1);
            subList.remove(subList.size()-1);
        }

    }
}
