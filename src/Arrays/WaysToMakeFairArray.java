package Arrays;

public class WaysToMakeFairArray {
    public static void main(String[] args) {
        int[] nums = new int[] {2,1,6,4};

        System.out.println((waysToMakeFair(nums)));
        System.out.println(waysToMakeFair2(nums));
        System.out.println(waysToMakeFair3(nums));
    }

    /**
     * We will split the array into two parts, left and right.
     * Firstly we count the sum to an array right,
     * where right[0] = A[0] + A[2] +...
     * and right[1] = A[1] + A[3] +...
     *
     * Now we iterates the whole array A, and try to split at each A[i].
     * When move one element from right to left,
     * we reduce the sum in right,
     * check the if it's fair,
     * then increse the sum in left.
     *
     * Complexity
     *
     * Time O(N)
     * Space O(1)
     */
    private static int waysToMakeFair(int[] A) {
        int result = 0;
        int left[] = new int[2];
        int right[] = new int[2];

        // even sum at 0th index, odd sum at 1st index
        for (int i = 0; i < A.length; i++)
            right[i%2] += A[i];

        // reduce sum in right and build up the sum in left
        for (int i = 0; i < A.length; i++) {
            right[i%2] -= A[i];
            if (left[0] + right[1] == left[1] + right[0])
                result++;
            left[i%2] += A[i];
        }
        return result;
    }

    private static int waysToMakeFair2(int[] nums) {
        int sumOdd = 0;
        int sumEven = 0;

        for (int index = 0; index < nums.length; index++) {
            if (index % 2 == 0) sumEven += nums[index];
            else sumOdd += nums[index];
        }

        int curEvenSum = 0;
        int curOddSum = 0;
        int result = 0;

        for (int index = 0; index < nums.length; index++) {
            if (index % 2 == 0) {
                if (curEvenSum - curOddSum  + sumOdd == curOddSum - curEvenSum + sumEven - nums[index])
                    result++;
                curEvenSum += nums[index];
            } else {
                if (curEvenSum + sumOdd - curOddSum - nums[index] == curOddSum + sumEven - curEvenSum)
                    result++;
                curOddSum += nums[index];
            }
        }
        return result;
    }

    /**
     * 1. make two different sums for odd and even indices
     * 2. add up the sum for the first sum for even and odd
     * 3. iterate through the nums array
     *    - subtract each num from first sum
     *    - compare the first and second sum (odd to even and even to odd)
     *    - add to each sum for second sum
     * Basic idea is to take out elements one by one and compare until the
     * even indices sum == odd indices sum
     */
    private static int waysToMakeFair3(int[] nums) {
        int res = 0;
        int firstEvenSum = 0, firstOddSum = 0;

        // build up the first odd sum and even sum
        for(int i=0; i<nums.length; ++i) {
            if(i%2 == 0) firstEvenSum += nums[i];
            else firstOddSum += nums[i];
        }

        // build up the second even sum and odd sum
        int secondEvenSum = 0, secondOddSum = 0;

        // subtract rightEvenSum and rightOddSum
        // build up leftEvenSum and leftOddSum until they intersect in sum
        for(int i=0; i<nums.length; ++i) {

            // subtract from firstSum
            if(i%2 == 0) {
                firstEvenSum -= nums[i];
            } else {
                firstOddSum -= nums[i];
            }

            // compare second to the first sum and add to result if equal
            if(secondEvenSum + firstOddSum == secondOddSum + firstEvenSum)
                ++res;

            // add to the secondSum
            if(i%2== 0) {
                secondEvenSum += nums[i];
            } else {
                secondOddSum += nums[i];
            }
        }

        return res;
    }
}
