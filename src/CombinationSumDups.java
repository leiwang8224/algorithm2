import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leiwang on 3/10/18.
 */
public class CombinationSumDups {
    public static void main(String[] args) {
        int[] nums = new int[] {1,2,3,4,4};
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        generateSumCombo(result, new ArrayList<>(), nums, 5, 0);
        for (ArrayList<Integer> subList : result) {
            System.out.println(Arrays.toString(subList.toArray()));
        }
    }

    private static void generateSumCombo(ArrayList<ArrayList<Integer>> result, ArrayList<Integer> subList, int[] nums, int remain, int start) {
        if (remain < 0) return;
        else if (remain == 0) result.add(new ArrayList<>(subList));
        else {
            for (int i = start; i < nums.length; i ++) {
                if (i > 0 && nums[i] == nums[i-1]) continue;
                subList.add(nums[i]);
                generateSumCombo(result,subList,nums,remain-nums[i],i);
                subList.remove(subList.size()-1);

            }
        }
    }
}
