package Math;

//Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
//If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
//The replacement must be in-place and use only constant extra memory.
//Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
//
//        1,2,3 → 1,3,2
//        3,2,1 → 1,2,3
//        1,1,5 → 1,5,1

public class NextPermutation {
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3};
        nextPermutation(nums);
        System.out.println(java.util.Arrays.toString(nums));

        nextPermutation2(nums);
        System.out.println(java.util.Arrays.toString(nums));

        nextPermutation3(nums);
        System.out.println(java.util.Arrays.toString(nums));
    }


    // Start from its last element, traverse backward to find the first one with index i that satisfy num[i-1] < num[i].
    // So, elements from num[i] to num[n-1] is reversely sorted.
    // To find the next permutation, we have to swap some numbers at different positions, to minimize the increased amount,
    // we have to make the highest changed position as high as possible. Notice that index larger than or equal to i is not
    // possible as num[i,n-1] is reversely sorted. So, we want to increase the number at index i-1, clearly, swap it with
    // the smallest number between num[i,n-1] that is larger than num[i-1]. For example, original number is 121543321,
    // we want to swap the ‘1’ at position 2 with ‘2’ at position 7.
    // The last step is to make the remaining higher position part as small as possible, we just have to reversely
    // sort the num[i,n-1]
    private static void nextPermutation(int[] num) {
        int n=num.length;
        if(n<2)
            return;
        int index=n-1;
        while(index>0){
            if(num[index-1]<num[index])
                break;
            index--;
        }
        if(index==0){
            reverseSort(num,0,n-1);
            return;
        }
        else{
            int val=num[index-1];
            int j=n-1;
            while(j>=index){
                if(num[j]>val)
                    break;
                j--;
            }
            swap(num,j,index-1);
            reverseSort(num,index,n-1);
            return;
        }
    }

    public static void swap(int[] num, int i, int j){
        int temp=0;
        temp=num[i];
        num[i]=num[j];
        num[j]=temp;
    }

    public static void reverseSort(int[] num, int start, int end){
        if(start>end)
            return;
        for(int i=start;i<=(end+start)/2;i++)
            swap(num,i,start+end-i);
    }

    /**
     *
     * @param nums
     */
    public static void nextPermutation2(int[] nums) {
        if(nums.length<=1){
            return;
        }

        int i= nums.length-1;

        for(;i>=1;i--){

            if(nums[i]>nums[i-1]){ //find first number which is smaller than it's after number
                break;
            }
        }

        if(i!=0){
            swap(nums,i-1); //if the number exist,which means that the nums not like{5,4,3,2,1}
        }

        reverse(nums,i);
    }

    private static void swap(int[] a,int i){
        for(int j = a.length-1;j>i;j--){
            if(a[j]>a[i]){
                int t = a[j];
                a[j] = a[i];
                a[i] = t;
                break;
            }
        }
    }

    private static void reverse(int[] a,int i){//reverse the number after the number we have found
        int first = i;
        int last = a.length-1;
        while(first<last){
            int t = a[first];
            a[first] = a[last];
            a[last] = t;
            first ++;
            last --;
        }
    }

    /**
     *
     * @param A
     */
    public static void nextPermutation3(int[] A) {
        if(A == null || A.length <= 1) return;
        int i = A.length - 2;
        while(i >= 0 && A[i] >= A[i + 1]) i--; // Find 1st id i that breaks descending order
        if(i >= 0) {                           // If not entirely descending
            int j = A.length - 1;              // Start from the end
            while(A[j] <= A[i]) j--;           // Find rightmost first larger id j
            swap3(A, i, j);                     // Switch i and j
        }
        reverse3(A, i + 1, A.length - 1);       // Reverse the descending sequence
    }

    public static void swap3(int[] A, int i, int j) {
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    public static void reverse3(int[] A, int i, int j) {
        while(i < j) swap(A, i++, j--);
    }
}
