package AlgoExpert.Medium;

public class SingleCycleCheck {
    public static void main(String[] args) {

    }

    //O(n) time | O(1) space
    private static boolean hasSingleCycle(int[] array) {
        int numElementsVisited = 0;
        int curIdx = 0;
        // keep track of num elements visited to make sure not
        // one element is visited more than once
        while (numElementsVisited < array.length) {
            // the first element is visited twice so return false
            // THIS IS the way to tell whether all indices are visited once
            if (numElementsVisited > 0 && curIdx == 0) return false;
            numElementsVisited ++;
            // get next index after the jump
            curIdx = getNextIdx(curIdx, array);
        }
        // visited all elements, now check if we are back to
        // starting index
        return curIdx == 0;
    }

    private static int getNextIdx(int curIdx, int[] array) {
        // get the amount to jump
        int jump = array[curIdx];
        // calculate the next index to jump, mod array length for rollover
        int nextIdx = (curIdx + jump) % array.length;
        // in case if nextIdx < 0, add the array length to make positive
        return nextIdx >= 0 ? nextIdx : nextIdx + array.length;
    }
}
