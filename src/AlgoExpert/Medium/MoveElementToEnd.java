package AlgoExpert.Medium;

import java.util.List;

public class MoveElementToEnd {
    public static void main(String[] args) {

    }

    // O(n) time | O(1) space

    /**
     * 1. iterate with two pointers start and end
     * 2. move end pointer only when the element to move is pointed by the end pointer
     * 3. if start index lands on a to move element, perform swap with end index
     * 4. increment the start index every iteration
     */
    private static java.util.List<Integer> moveElementToEnd(java.util.List<Integer> array,
                                                                int toMove) {
        int startIndex = 0;
        int endIndex = array.size() - 1;

        while (startIndex < endIndex) {
            // while toMove element is in the right, move left endIdx
            // if toMove element is in the left, swap
            // move to the right startIdx
            while (startIndex < endIndex &&
                    array.get(endIndex) == toMove) {
                endIndex--;
            }
            if (array.get(startIndex) == toMove)
                swap(startIndex, endIndex, array);
            startIndex++;
        }

        return array;
    }

    public static List<Integer> moveElementToEndMySol(List<Integer> array, int toMove) {
        int startIdx = 0;
        int endIdx = array.size()-1;
        while (startIdx <= endIdx) {
            while (array.get(startIdx) != toMove && startIdx < endIdx) {
                startIdx++;
            }
            if (startIdx == endIdx) return array;
            if (array.get(startIdx) == toMove && array.get(endIdx) != toMove) {
                swap(startIdx, endIdx, array);
            } else if (array.get(endIdx) == toMove) {
                endIdx--;
            }
        }
        // Write your code here.
        return array;
    }

    public static List<Integer> moveElementToEndMySol2(List<Integer> array, int toMove) {
        int startIdx = 0;
        int endIdx = array.size()-1;

        while (startIdx < endIdx) {
            while (array.get(startIdx) != toMove && startIdx < endIdx) {
                startIdx++;
            }

            while (array.get(endIdx) == toMove && startIdx < endIdx) {
                endIdx--;
            }
            swap(startIdx, endIdx, array);

            if (startIdx == endIdx) return array;

            startIdx++;
            endIdx--;
        }
        // Write your code here.
        return array;
    }

    private static void swap(int startIndex, int endIndex, List<Integer> array) {
        int temp = array.get(endIndex);
        array.set(endIndex, array.get(startIndex));
        array.set(startIndex, temp);
    }
}
