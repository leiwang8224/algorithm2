package Arrays;

//Given an array of integers, write a method - maxGain -
// that returns the maximum gain. Maximum Gain is defined
// as the maximum difference between 2 elements in a list
// such that the larger element appears after the smaller
// element. If no gain is possible, return 0.
public class MaxGain {
    public static void main(String[] args) {

    }

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
