package AlgoExpert.Hard;

import java.util.*;
public class SameBst {
    // O(n^2) time | O(n^2) space
    // base case: if the first element or the size of the array is not the same then it's not same BST
    // Gather all elements greater than arrayOne[0] and all elements less than arrayOne[0]
    // Gather all elements greater than arrayTwo[0] and all elements less than arrayTwo[0]
    // recursively call on the two greater than and two less than arrays
    private static boolean sameBst(List<Integer> arrayOne, List<Integer> arrayTwo) {
        if (arrayOne.size() != arrayTwo.size()) return false;

        if (arrayOne.size() == 0 && arrayTwo.size() == 0) return true;

        if (arrayOne.get(0) != arrayTwo.get(0)) return false;

        List<Integer> leftOne = getSmaller(arrayOne);
        List<Integer> leftTwo = getSmaller(arrayTwo);
        List<Integer> rightOne = getBiggerOrEqual(arrayOne);
        List<Integer> rightTwo = getBiggerOrEqual(arrayTwo);

        return sameBst(leftOne, leftTwo) && sameBst(rightOne, rightTwo);
    }

    private static List<Integer> getBiggerOrEqual(List<Integer> array) {
        List<Integer> biggerOrEqual = new ArrayList<>();
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) >= array.get(0)) {
                biggerOrEqual.add(array.get(i));
            }
        }
        return biggerOrEqual;
    }

    private static List<Integer> getSmaller(List<Integer> array) {
        List<Integer> smaller = new ArrayList<>();
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) < array.get(0)) {
                smaller.add(array.get(i));
            }
        }
        return smaller;
    }

    // O(n^2) time | O(d) space - where n is the number of nodes
    // in each array and d is the depth of the BST that they represent
    // this method has improved space complexity because we don't have to store
    // the values from the greater than and less than (leveraging the recursion call stack)
    private static boolean sameBst2(List<Integer> arrayOne, List<Integer> arrayTwo) {
        // recurse based on the indices of arrayOne and arrayTwo, min and max for current value
        // starts off with min and max since we don't know what the min max are
        return areSameBst(arrayOne,
                            arrayTwo,
                    0,
                    0,
                            Integer.MIN_VALUE,
                            Integer.MAX_VALUE);
    }

    private static boolean areSameBst(List<Integer> arrayOne,
                                      List<Integer> arrayTwo,
                                      int rootIdxOne,
                                      int rootIdxTwo,
                                      int minValue,
                                      int maxValue) {
        // base case when index reaches below 0, verify index are the same
        // this is the same as checking the size of the arrays
        if (rootIdxOne == -1 || rootIdxTwo == -1) return rootIdxOne == rootIdxTwo;

        // check if element in the index are the same
        // this is the same as checking the first element like the first method
        if (arrayOne.get(rootIdxOne) != arrayTwo.get(rootIdxTwo)) return false;

        // get index of the first smaller element after rootIdxOne/rootIdxTwo
        // but greater than the minValue specified
        int leftRootIdxArrayOne = getIdxOfFirstSmaller(arrayOne, rootIdxOne, minValue);
        int leftRootIdxArrayTwo = getIdxOfFirstSmaller(arrayTwo, rootIdxTwo, minValue);
        // get index of the first bigger element after rootIdxOne/rootIdxTwo
        // but less than the maxValue specified
        int rightRootIdxArrayOne = getIdxOfFirstBiggerOrEqual(arrayOne, rootIdxOne, maxValue);
        int rightRootIdxArrayTwo = getIdxOfFirstBiggerOrEqual(arrayTwo, rootIdxTwo, maxValue);

        // it shouldn't matter which value you pick either from array one or array two
        int curValueArrayOne = arrayOne.get(rootIdxOne);
        boolean leftAreSame =
                areSameBst(arrayOne, arrayTwo, leftRootIdxArrayOne, leftRootIdxArrayTwo, minValue, curValueArrayOne);
        boolean rightAreSame =
                areSameBst(arrayOne, arrayTwo, rightRootIdxArrayOne, rightRootIdxArrayTwo, curValueArrayOne, maxValue);
        return leftAreSame && rightAreSame;
    }

    private static int getIdxOfFirstBiggerOrEqual(List<Integer> array,
                                                  int startingIdx,
                                                  int maxValue) {
        // find the index of the first bigger/equal value after the startingIdx
        // make sure that this value is smaller than maxVal, which is the value
        // of the previous parent node in the BST. If it isn't, then that
        // value is located in the right subtree of the previous parent node.
        for (int i = startingIdx + 1; i < array.size(); i++) {
            if (array.get(i) >= array.get(startingIdx) && array.get(i) < maxValue) return i;
        }
        return -1;
    }

    private static int getIdxOfFirstSmaller(List<Integer> array,
                                            int startingIdx,
                                            int minValue) {
        // find the index of the first smaller value after the startingIdx
        // make sure that this value is greater than or equal to the minval
        // which is the value of the previous parent node in the BST. If it
        // isn't, then that value is located in the left subtree of the previous
        // parent node
        for (int i = startingIdx + 1; i < array.size(); i++) {
            if (array.get(i) < array.get(startingIdx) && array.get(i) >= minValue) return i;
        }
        return -1;
    }
}
