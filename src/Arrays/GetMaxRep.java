package Arrays;



//Given an Array of integers, write a method that will return
// the integer with the maximum number of repetitions. Your code
// is expected to run with O(n) time complexity and O(1) space
// complexity. The elements in the array are between 0 to
// size(array) - 1 and the array will not be empty.
//        f({3,1,2,2,3,4,4,4}) --> 4
public class GetMaxRep {
    public static void main(String[] args) {
        int[] array = new int[] {1,2,3,4,3,4,3,2,3,2,1,2,3,4};
        System.out.println(getMaxRepetition(array));

    }

//    Since we use arr[i]%k as index and add value k at the index
//    arr[i]%k, the index which is equal to maximum repeating element
//    will have the maximum value in the end. Note that k is added
//    maximum number of times at the index equal to maximum repeating
//    element and all array elements are smaller than k.
    private static int getMaxRepetition(int[] a) {
        int max = a.length;

        // iterate through input array, for every element a[i],
        // increment a[a[i]%k] by k
        for (int index = 0; index < a.length; index++) {
            a[a[index]%max] += max;
        }

        // find index of the max repeating element
        int maxr = a[0], result = 0;
        for (int index = 1; index < a.length; index++) {
            if (a[index] > maxr) {
                maxr = a[index];
                result = index;
            }
        }

        return result;
    }
}
