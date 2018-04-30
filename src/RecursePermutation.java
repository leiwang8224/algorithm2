import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leiwang on 3/10/18.
 */

/**
 * Given a collection of distinct integers, return all possible permutations.

 Example:

 Input: [1,2,3]
 Output:
 [
 [1,2,3],
 [1,3,2],
 [2,1,3],
 [2,3,1],
 [3,1,2],
 [3,2,1]
 ]


 */
public class RecursePermutation {
    public static void main (String args[]) {
        int[] nums = new int[]{1,2,3,4};
        ArrayList<ArrayList<Integer>> newList = new ArrayList<>();
        createPermutation(newList, new ArrayList<Integer>(), nums);
        for (ArrayList<Integer> list : newList) {
            System.out.println(Arrays.toString(list.toArray()));
        }
    }

    private static void createPermutation(ArrayList<ArrayList<Integer>> newList, ArrayList<Integer> subList, int[] nums) {
        System.out.println("enter createPermutation subList = " + Arrays.toString(subList.toArray()));
        if (subList.size() == nums.length) {
            newList.add(new ArrayList<>(subList));
            System.out.println("added new subList = " + Arrays.toString(subList.toArray()));
        } else {
            for (int i = 0; i < nums.length; i++) {
                System.out.println("start of for loop subList = " + Arrays.toString(subList.toArray()) + " i = " + i);
                if (subList.contains(nums[i])) {
                    System.out.println("subList already contains " + nums[i] + " skipping");
                    continue;
                }
                subList.add(nums[i]);
                createPermutation(newList, subList, nums);
                System.out.println("return from recursion and remove the last element");
                subList.remove(subList.size() - 1);
                System.out.println("end of for loop subList = " + Arrays.toString(subList.toArray()) + " i = " + i );
            }
        }
    }
}
