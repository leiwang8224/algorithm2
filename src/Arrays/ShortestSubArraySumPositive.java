package Arrays;

//LC-209
public class ShortestSubArraySumPositive {
    public static void main(String[] args) {

    }

    // time O(N^3)
    // space O(1)
    private static int minSubArrayLenGreaterThanTargetNA(int s, int[] nums) {
        int n = nums.length;
        int ans = Integer.MAX_VALUE;
        for (int startIndex = 0; startIndex < n; startIndex++) {
            for (int endIndex = startIndex; endIndex < n; endIndex++) {
                int sum = 0;
                for (int subArrayIndex = startIndex; subArrayIndex <= endIndex; subArrayIndex++) {
                    sum += nums[subArrayIndex];
                }
                if (sum >= s) {
                    ans = Math.min(ans, (endIndex - startIndex + 1));
                    break; //Found the smallest subarray with sum>=s starting with index i, hence move to next index
                }
            }
        }
        return (ans != Integer.MAX_VALUE) ? ans : 0;
    }

    // time O(n^2)
    // space O(n)
    private static int minSubArrayLenGreaterThanTargetBruteForce(int s, int[] nums) {
        int numsLength = nums.length;
        if (numsLength == 0) return 0;
        int ans = Integer.MAX_VALUE;
        int[] cumSum = new int[numsLength];
        cumSum[0] = nums[0];

        for (int startIndex = 1; startIndex < numsLength; startIndex++) {
            cumSum[startIndex] = cumSum[startIndex-1] + nums[startIndex];
        }

        for (int startIndex = 0; startIndex < numsLength; startIndex++) {
            for (int endIndex = startIndex; endIndex < numsLength; endIndex++) {
                int sumSoFar = cumSum[endIndex] - cumSum[startIndex] + nums[startIndex];
                if (sumSoFar >= s) {
                    ans = Math.min(ans, (endIndex - startIndex + 1));
                    break; // found the smallest subarray with sum>=s starting with startIndex
                            // hence move to next index
                }
            }
        }

        return (ans != Integer.MAX_VALUE ? ans : 0);
    }

    // time O(n)
    // space O(1)
    private static int minSubArrayLenGreaterThanTarget(int target, int[] a) {
        if (a == null || a.length == 0) return 0;

        int startIndex = 0, endIndex = 0, cumSum = 0, minWindowLength = Integer.MAX_VALUE;

        while (endIndex < a.length) {
            // add to cumSum first before evaluating minWindowLength
            cumSum += a[endIndex++]; // NOTE the endIndex is incremented before calculating the shortest window

            while (cumSum >= target) {
                // get the min window length first before moving the start pointer
                minWindowLength = Math.min(minWindowLength, endIndex - startIndex);
                // if sum has reached target, increment start index (shrink window)
                // and subtract the number on start index
                cumSum = cumSum - a[startIndex];
                startIndex++;
            }
        }

        return minWindowLength == Integer.MAX_VALUE ? 0 : minWindowLength;
    }
}
