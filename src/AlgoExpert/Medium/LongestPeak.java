package AlgoExpert.Medium;

public class LongestPeak {
    public static void main(String[] args) {

    }

    // O(n) time | O(1) space
    private static int longestPeak(int[] array) {
        int longestPeakLen = 0;
        int index = 1;
        while (index < array.length - 1) {
            boolean isPeak = array[index-1] < array[index] &&
                    array[index] > array[index+1];
            // keep iterating if no peak is found
            if (!isPeak) {
                index++;
                continue;
            }

            // peak is found so check if the left to left index is less than the cur number, if it is
            // keep searching for the bottom of the peak
            int leftIdx = index - 2;
            while (leftIdx >= 0 && array[leftIdx] < array[leftIdx + 1]) {
                leftIdx--;
            }

            // peak is found so check if the right to the right index is greater than the cur number,
            // if it is keep searching for the bottom of the peak
            int rightIdx = index + 2;
            while (rightIdx < array.length && array[rightIdx] < array[rightIdx - 1]) {
                rightIdx++;
            }

            // calculate the peak len
            int curPeakLen = rightIdx - leftIdx - 1;
            if (curPeakLen > longestPeakLen) longestPeakLen = curPeakLen;
            // set index to be the end of the peak so we don't have to reprocess
            index = rightIdx;
         }

        return longestPeakLen;
    }
}
