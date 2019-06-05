package Recursion;

public class GroupSum {
    public static void main(String[] args) {
        int[] nums = new int[] {1,2,3,6,5};
        System.out.println(groupSum(nums,10));
    }

//    Given an array of integers and a target number,
// determine if it is possible to choose a group of integers
// from the array, such that the numbers in the group sum to
// the given target.
//
//            Examples:
//    groupSum({1,2,3,6,5},10) ==> true
//
//    groupSum({1,2,3,6,5},18) ==> false
    private static boolean groupSum(int[] arr, int target) {
        return groupSum(0,arr,target);
    }

    // use target to keep track of what is left
    // the recursive algorithm essentially would try two cases:
    // 1. include the first number in the sum and try to see if target is achieved
    // 2. exclude the first number in the sum and try to see if target is achieved
    private static boolean groupSum(int startIndex, int[] arr, int target) {
        String str = " not valid index";
        if (startIndex >= 0 && startIndex < arr.length) {
            str = String.valueOf(arr[startIndex]);
        }
        System.out.println("target = " +
                           target +
                           " index = " +
                           startIndex +
                           " num = " + str);
        // base case: there is no number left
        if (startIndex >= arr.length)
            return target == 0;
        else {
            // recursive case 1: include the 1st number, check the remain
            System.out.println("entering recursive case 1");
            if (groupSum(startIndex + 1, arr, target - arr[startIndex]))
                return true;
            // recursive case 2: does not include the first number, check the remain
            System.out.println("entering recursive case 2");
            if (groupSum(startIndex + 1, arr, target))
                return true;
        }

        return false;
    }
}
