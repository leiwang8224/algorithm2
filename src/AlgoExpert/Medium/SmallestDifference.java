package AlgoExpert.Medium;

public class SmallestDifference {
    public static void main(String[] args) {

    }

    // O(nlog(n) + mlog(m)) time | O(1) space
    private static int[] smallestDiff(int[] arrayOne, int[] arrayTwo) {
        java.util.Arrays.sort(arrayOne);
        java.util.Arrays.sort(arrayTwo);

        int arrayOneIndex = 0;
        int arrayTwoIndex = 0;
        int smallest = Integer.MAX_VALUE;
        int currentDiff = Integer.MAX_VALUE;
        int[] smallestPair = new int[2];

        while (arrayOneIndex < arrayOne.length &&
                arrayTwoIndex < arrayTwo.length) {
            int arrayOneNumber = arrayOne[arrayOneIndex];
            int arrayTwoNumber = arrayTwo[arrayTwoIndex];
            if (arrayOneNumber < arrayTwoNumber) {
                currentDiff = arrayTwoNumber - arrayOneNumber;
                arrayOneIndex++;
            } else if (arrayTwoNumber < arrayOneNumber) {
                currentDiff = arrayOneNumber - arrayTwoNumber;
                arrayTwoIndex++;
            } else {
                return new int[] {arrayOneNumber, arrayTwoNumber};
            }
            if (smallest > currentDiff) {
                smallest = currentDiff;
                smallestPair = new int[] {arrayOneNumber, arrayTwoNumber};
            }
        }
        return smallestPair;
    }
}
