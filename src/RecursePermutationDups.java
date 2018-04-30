import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leiwang on 3/10/18.
 */

/**
 * Given a collection of numbers that might contain duplicates, return all possible unique permutations.

 Example:

 Input: [1,1,2]
 Output:
 [
 [1,1,2],
 [1,2,1],
 [2,1,1]
 ]


 */
public class RecursePermutationDups {
    public static void main(String args[]) {
        int[] nums = new int[]{1, 2, 3, 3};
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        createPermutations(result, new ArrayList<Integer>(), nums, new boolean[nums.length]);
        for (ArrayList<Integer> subList : result) {
            System.out.println(Arrays.toString(subList.toArray()));
        }
    }

    private static void createPermutations(ArrayList<ArrayList<Integer>> result, ArrayList<Integer> subList, int[] origArray, boolean[] used) {
        System.out.println("enter createPermutation subList = " + Arrays.toString(subList.toArray()));
        if (subList.size() == origArray.length) {
            result.add(new ArrayList<>(subList));
            System.out.println("added new subList = " + Arrays.toString(subList.toArray()));

        } else {
            for (int i = 0; i<origArray.length; i ++) {
                System.out.println("start of for loop subList = " + Arrays.toString(subList.toArray()) + " i = " + i + " used = " + Arrays.toString(used));
                // i > 0 && origArray[i] == origArray[i-1] && !used[i-1]
                // i needs to be greater than 0 so we can check the element before (i-1)
                // the element before needs to be same as the current element AND last element is false
                if (used[i] || i > 0 && origArray[i] == origArray[i-1] && !used[i - 1]) continue;
                used[i] = true;
                System.out.println("set i to true " + i + " to used = " + Arrays.toString(used));
                subList.add(origArray[i]);
                createPermutations(result, subList, origArray, used);
                used[i] = false;
                System.out.println("set i to false " + i + " to not used = " + Arrays.toString(used));
                subList.remove(subList.size()-1);
                System.out.println("end of for loop subList = " + Arrays.toString(subList.toArray()) + " i = " + i + " used = " + Arrays.toString(used));

            }
        }
    }

}


