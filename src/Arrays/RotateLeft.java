package Arrays;

import java.util.Arrays;

public class RotateLeft {
    public static void main(String[] args) {
        int[] array = new int[]{1,2,3,4,5,6,7,8};
        rotateLeft(array, 4);
        System.out.println(Arrays.toString(array));

    }

    private static int[] rotateLeft(int[] arr, int k) {
        if (arr == null) return null;
        int actualShifts = k % arr.length;
        // reverse whole array
        reverse(arr, 0, arr.length-1);
        // reverse starting from 0 and end at length - actualShifts
        reverse(arr, 0, arr.length - actualShifts - 1);
        // reverse starting from middle and end at end
        reverse(arr, arr.length - actualShifts, arr.length-1);
        return arr;
    }

    // reverse the numbers in an array given left bound and right bound
    private static void reverse(int[] arr, int left, int right) {
        if (arr == null || arr.length == 1) return;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
}
