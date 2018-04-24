package Heap;

/**
 * Created by leiwang on 4/2/18.
 */
public class HeapifySort {
    public static void main(String args[]) {

        int[] nums = new int[] {1,2,3,4,5,6,7,8,9,10};
        heapify(nums,nums.length,1);
        System.out.println(java.util.Arrays.toString(nums));
        //        [1, 5, 3, 4, 10, 6, 7, 8, 9, 2] ?

        // Min heap: value of a node must be less
        // than or equal to the values of its children

        // Max heap: value of a node must be greater
        // than or equal to the values of its children
    }

    // to heapify a subtree rooted with node i which
    // is an index in arr[]. n is size of heap
    private static void heapify(int arr[], int n, int i) {
        int largest = i; // init largest as root
        int l = 2*i + 1; // left
        int r = 2*i + 2; // right

        // if left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // if right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // if largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }

    }

    private static void sort(int arr[]) {
        int n = arr.length;

        // build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i --) {
            heapify(arr,n,i);
        }

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i --) {
            // move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }
}