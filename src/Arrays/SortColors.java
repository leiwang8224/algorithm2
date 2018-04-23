package Arrays;

import java.util.Arrays;

//Given an array with n objects colored red, white or blue,
// sort them in-place so that objects of the same color are adjacent,
// with the colors in the order red, white and blue.
//
// Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
//
// Note: You are not suppose to use the library's sort function for this problem.
//
// Example:
//
// Input: [2,0,2,1,1,0]
// Output: [0,0,1,1,2,2]
public class SortColors {
    public static void main(String[] args) {
        int[] nums = new int[]{2,0,2,1,1,0};
        sortColors(nums);
        int[] nums2 = new int[]{2,0,2,1,1,0};

        sortColors2(nums2);
        int[] nums3 = new int[]{2,0,2,1,1,0};
        int[] nums4 = new int[]{2,0,2,1,1,0};

        sortColorsCountingSort(nums3);
        sortColorsImproved(nums4);
        System.out.println("sorted array = " + Arrays.toString(nums));
        System.out.println("sorted array = " + Arrays.toString(nums2));
        System.out.println("sorted array = " + Arrays.toString(nums3));
        System.out.println("sorted array = " + Arrays.toString(nums4));

    }

    /**
     * One pass solution
     * @param nums
     */
    private static void sortColors(int[] nums) {
        // 2 pointers, one in begin, one in end
        int p1 = 0, p2 = nums.length - 1, index = 0;
        while (index <= p2) {
            if (nums[index] == 0) {
                nums[index] = nums[p1];
                nums[p1] = 0;
                p1++;
            }
            if (nums[index] == 2) {
                nums[index] = nums[p2];
                nums[p2] = 2;
                p2--;
                index--;
            }
            index ++;
        }
    }

    /**
     * Two pass solution
     */
    private static void sortColors2(int[] nums) {
        int count0 = 0, count1 = 0, count2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {count0++;}
            if (nums[i] == 1) {count1++;}
            if (nums[i] == 2) {count2++;}
        }
        for(int i = 0; i < nums.length; i++) {
            if (i < count0) {nums[i] = 0;}
            else if (i < count0 + count1) {nums[i] = 1;}
            else {nums[i] = 2;}
        }
    }

    /**
     * Using counting sort
     * Algorithm:
     * Initialize counting array to all zeros.
     Count the number of times each value occurs in the input.
     Modify the counting array to give the number of values smaller than index
     Transfer numbers from input to output array at locations provided by counting array
     * @param nums
     */
    public static void sortColorsCountingSort(int[] nums) {
        if(nums==null||nums.length<2){
            return;
        }

        int[] countArray = new int[3];
        for(int i=0; i<nums.length; i++){
            countArray[nums[i]]++;
        }

        for(int i=1; i<=2; i++){
            countArray[i]=countArray[i-1]+countArray[i];
        }

        int[] sorted = new int[nums.length];
        for(int i=0;i<nums.length; i++){
            int index = countArray[nums[i]]-1;
            countArray[nums[i]] = countArray[nums[i]]-1;
            sorted[index]=nums[i];
        }

        System.arraycopy(sorted, 0, nums, 0, nums.length);
    }

//    In solution 1, two arrays are created. One is for counting, and the other is for
//    storing the sorted array (space is O(n)). We can improve the solution so that it
//    only uses constant space. Since we already get the count of each element, we can
//    directly project them to the original array, instead of creating a new one.
    public static void sortColorsImproved(int[] nums) {
        if(nums==null||nums.length<2){
            return;
        }

        int[] countArray = new int[3];
        for(int i=0; i<nums.length; i++){
            countArray[nums[i]]++;
        }

        int j = 0;
        int k = 0;
        while(j<=2){
            if(countArray[j]!=0){
                nums[k++]=j;
                countArray[j] = countArray[j]-1;
            }else{
                j++;
            }
        }
    }
}
