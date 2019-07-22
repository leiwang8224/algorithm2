package Arrays;

//Given an array of integers, write a method - maxGain -
// that returns the maximum gain. Maximum Gain is defined
// as the maximum difference between 2 elements in a list
// such that the larger element appears after the smaller
// element. If no gain is possible, return 0.
public class MaxGain {
    public static void main(String[] args) {
        int[] array = new int[]{1,2,3,4,5,6,7,8,9,10};
        System.out.println(maxGain(array));
    }
//
//    Since the larger element must always appear
//    after the smaller element, this problem can
//    be solved in a single pass. Keep a record of
//    the maximum gain found so far, and the minimum
//    element. When finding the maximum gain, use
//    the difference between the current element and
//    the minimum element found so far.
    private static int maxGain(int arr[]) {
        int max = arr[1]- arr[0];
        int min = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - min > max)
                max = arr[i] - min;
            if (arr[i] < min)
                min = arr[i];
        }

        return max < 0 ? 0 : max;
    }
}
