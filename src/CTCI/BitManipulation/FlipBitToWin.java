package CTCI.BitManipulation;

import java.util.ArrayList;

/**
 * You have an integer and you can flip exactly one bit from a 0 to 1. Write
 * code to find the length of the longest sequence of 1s you could create.
 * Input: 1775 (or 11011101111
 * Output: 8
 */
public class FlipBitToWin {
    public static void main(String[] args) {
        System.out.println(findLongestSeqByFlipping(1775));
        System.out.println(flipBitOptimal(1775));
        System.out.println(longestSequence2(1775));
    }

    /**
     * Brute force solution
     * @param number
     * @return
     */
    static int findLongestSeqByFlipping(int number) {
        // if all ones, just return 32
        if (number == -1) return Integer.BYTES * 8;
        ArrayList<Integer> sequences = getAlternatingSeq(number);
        return findLongestSequence(sequences);
    }

    /**
     * Walk through the array and at each 0s sequence we consider merging the adjacent
     * 1s sequence if the 0s sequence has length 1.
     * @param sequences
     * @return
     */
    private static int findLongestSequence(ArrayList<Integer> sequences) {
        int maxSeq = 1;

        for (int index = 0; index < sequences.size(); index += 2) {
            // extract the 0s sequence
            int zerosSeq = sequences.get(index);

            // extract the 1s sequence previous to 0s seq
            int onesSeqPrev = index - 1 >= 0 ? sequences.get(index-1) : 0;

            // extract the 1s sequence next to 0s seq
            int onesSeqNext = index + 1 < sequences.size() ? sequences.get(index+1) : 0;

            int thisSeq = 0;

            if (zerosSeq == 1) {
                // can merge
                thisSeq = onesSeqNext + 1 + onesSeqPrev;
            } else if (zerosSeq > 1) {
                // add one to either side
                thisSeq = 1 + Math.max(onesSeqPrev, onesSeqNext);
            } else if (zerosSeq == 0) {
                // no zero, but take either side
                thisSeq = Math.max(onesSeqPrev, onesSeqNext);
            }
            maxSeq = Math.max(thisSeq,maxSeq);
        }

        return maxSeq;
    }

    /**
     * Return a list of the sizes of the sequences. The sequence starts
     * off with the number of 0s (which might be 0) and then alternates
     * with the counts of each value
     * ex: 11011101111 -> 0,4,1,3,1,2,21,
     * starting from the rightmost side:
     * 0 0s
     * 4 1s
     * 1 1s
     * 3 1s
     * 1 1s
     * 2 1s
     * 21 1s
     * @param number
     * @return
     */
    private static ArrayList<Integer> getAlternatingSeq(int number) {
         ArrayList<Integer> sequences = new ArrayList<>();

         int searchFor = 0;
         int counter = 0;

         for (int index = 0; index < Integer.BYTES * 8; index++) {
             // check the first bit
             if ((number & 1) != searchFor) {
                 sequences.add(counter);
                 searchFor = number & 1; // flip 1 to 0 or 0 to 1
                 counter = 0;
             }
             counter++;
             number = number >>> 1;
         }
         sequences.add(counter);
         return sequences;
    }

    /**
     * To reduce the space usage, we don't need to hang on to the length of each seq the
     * entire time. We only need it long enough to compare each 1s seq to the immediately preceding 1s
     * seq. Therefore, we can just walk through the integer doing this, tracking the current 1s seq
     * length and the previous 1s seq length. When we see a zero, update previous length:
     * - if the next bit is a 1, previous length should be set to currentLength
     * - if the next bit is a 0, then we can't merge these seq together, so set previousLength to 0.
     * @param number
     * @return
     */
    static int flipBitOptimal(int number) {
        if (~number == 0) return Integer.BYTES * 8;

        int curLength = 0;
        int prevLength = 0;
        int maxLength = 1; // we can always have a seq of at least one 1

        while (number != 0) {
            if ((number & 1) == 1) {
                curLength++;
                // else if there are two consecutive 0's then reset prevLength to 0
                // curLength is set to 0 when we see a 0
            } else if ((number & 1) == 0) {
                // update to 0 (if next bit is 0) or curLength (if next bit is 1)
                prevLength = (number & 2) == 0 ? 0 : curLength;
                curLength = 0;
            }

            // max length is the max of prevLength + curLength + 1 and maxLength
            maxLength = Math.max(prevLength + curLength + 1, maxLength);

            // right shift 1 bit
            number = number >>> 1;
        }

        return maxLength;
    }

    /**
     * Considering the max number of 0's we can have is 1 (flipping one bit)
     * just iterate through each of the 32 bits and mask out 1 bit and check
     * how many consecutive 1's we can get
     * @param number
     * @return
     */
    static int longestSequence2(int number) {
        int maxSeq = 0;

        for (int index = 0; index < 32; index++)
            // note using two max here
            // for each bit we are going to ignore and see how many 1s we get
            maxSeq = Math.max(maxSeq, longestSeqOf1s(number, index));
        return maxSeq;
    }

    private static int longestSeqOf1s(int number, int indexToIgnore) {
        int max = 0;
        int counter = 0;
        for (int index = 0; index < 32; index++) {
            // if we are on the index to ignore or the bit is a 1, increment counter
            if (index == indexToIgnore || (BitUtils.getBit(number, index))) {
                counter ++;
                max = Math.max(counter, max);
            } else { // else we are on a 0 bit, reset the counter
                counter = 0;
            }
        }
        return  max;
    }
}
