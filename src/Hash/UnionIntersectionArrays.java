package Hash;

import java.util.HashSet;

/**
 * Created by leiwang on 4/2/18.
 */
public class UnionIntersectionArrays {
    public static void main(String[] args) {

    }

    // prints union of arr1[0..m-1] and arr2[0..n-1]
    private void printUnion(int arr1[], int arr2[], int m, int n) {
        // before finding union, make sure arr1[0..m-1]
        // is smaller
        if (m > n) {
            int tempp[] = arr1;
            arr1 = arr2;
            arr2 = tempp;

            int temp = m;
            m = n;
            n = temp;
        }

        // Now arr1[] is smaller
        // Sort the first array and print its elements
        java.util.Arrays.sort(arr1);
        for (int i = 0; i < m; i ++) {
            System.out.print(arr1[i] + " ");
        }

        // Search every element of bigger array in smaller
        // array and print the element if not found
        for (int i = 0; i < n; i ++) {
            if (binarySearch(arr1, 0, m - 1, arr2[i]) == -1)
                System.out.print(arr2[i] + " ");
        }
    }

    // prints intersection of arr1[0..m-1] and arr2[0..n-1]
    private void printIntersection(int[] arr1, int[] arr2, int m, int n) {
        // Before finding intersection, make sure arr1[0..m-1]
        // is smaller
        if (m > n) {
            int tempp[] = arr1;
            arr1 = arr2;
            arr2 = tempp;

            int temp = m;
            m = n;
            n = temp;
        }

        // now arr1 is smaller
        // sort smaller array arr1[0..m-1]
        java.util.Arrays.sort(arr1);

        // search every element of bigger array in smaller array
        // and print the element if found
        for (int i = 0; i < n; i++) {
            if (binarySearch(arr1, 0, m -1, arr2[i]) != -1)
                System.out.print(arr2[i] + " ");
        }
    }

    // recursive binary search function, returns location
    // of given array arr[l..r] is present, otherwise -1
    private int binarySearch(int[] arr, int l, int r, int x) {
        if (r >= l) {
            int mid = l + (r - 1) / 2;

            // if the element is present at the middle itself
            if (arr[mid] == x)
                return mid;

            // if element is smaller than mid, then it can only
            // be present in left subarray
            if (arr[mid] > x)
                return binarySearch(arr, l, mid - 1, x);

            // else the element can only be present in right subs
            return binarySearch(arr, mid + 1, r, x);
        }
        return -1;
    }

    //**********Use Hash***********//

    private static void printUnion(int arr1[], int arr2[]) {
        HashSet<Integer> hs = new HashSet<>();

        for (int i = 0; i < arr1.length; i++)
            hs.add(arr1[i]);
        for (int i = 0; i < arr2.length; i++)
            hs.add(arr2[i]);
        System.out.println(hs);
    }

    private static void printIntersection(int arr1[], int arr2[]) {
        HashSet<Integer> hs = new HashSet<>();
        HashSet<Integer> hs1 = new HashSet<>();

        for (int i = 0; i < arr1.length; i ++) {
            hs.add(arr1[i]);
        }

        for (int i = 0; i < arr2.length; i ++) {
            if (hs.contains(arr2[i]))
                System.out.print(arr2[i] + " ");
        }
    }
}
