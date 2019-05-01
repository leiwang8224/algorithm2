package Arrays;

public class GetMaxRep {
    public static void main(String[] args) {

    }

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
