package DP;

import java.util.Arrays;
import java.util.List;

/**
 * Created by leiwang on 4/8/18.
 */
public class SubsetSum {
    public static void main(String args[]) {
        Integer[] array = new Integer[]
                {3, 2, 7, 1};

        int[] arrayInt = new int[]{3,2,7,1};

        int[] arrayDP = new int[] {
                3,2,7,1
        };

//        isSubsetSum(array, array.length, 6);
        System.out.println(isSubsetSum(arrayInt,array.length,6,0));
        System.out.println(isSubsetSumDP(arrayDP,arrayDP.length,6));
    }

    // TODO problem with this
    private static boolean isSubsetSum(int[] nums, int length, int target, int index) {
        if (target == 0)
            return true;

        if (length == 0)
            return false;

        if (nums[0] > target)
            return isSubsetSum(nums, length-1, target, index + 1);

        return isSubsetSum(nums, length-1, target, index + 1) ||
                isSubsetSum(nums, length-1, target-nums[0], index + 1);
        // if first element is > target, ignore it
//        if (list.get(0) >  target) // ignore first element if greater
//            return isSubsetSum(list.get(index+1), length - 1, target);
//
//        return isSubsetSum(list.subList(1,list.size()-1), length-1, target) ||
//                isSubsetSum(list.subList(1,list.size()-1), length-1, target-list.get(0));

    }

    private static boolean isSubsetSumDP(int[] arr, int length, int target) {
        boolean dp[][] = new boolean[target+1][length+1];

        for (int col = 0; col <= length; col ++) {
            dp[0][col] = true;  // if x is 0, true
        }
        for (int row = 1; row <= target; row ++) {
            dp[row][0] = false;  // if x is not 0 and arr is empty, false
        }

        for (int row = 1; row <= target; row ++) {
            for (int col = 1; col <= length; col ++) {
                dp[row][col] = dp[row][col-1];
                if (row >= arr[col-1])
                    dp[row][col] = dp[row][col] || dp[row-arr[col-1]][col-1];
            }
        }
        return dp[target][length];
    }

}
