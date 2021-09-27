package AlgoExpert.VeryHard;
import java.util.*;

public class RightSmallerThan {
    // O(n^2) time | O(n) space - where n is the length of the array
    public static List<Integer> rightSmallerThan(List<Integer> array) {
        List<Integer> rightSmallerCounts = new ArrayList<>();
        for (int idx = 0; idx < array.size(); idx++) {
            int rightSmallerCount = 0;
            for (int idxAfter = idx + 1; idxAfter < array.size(); idxAfter++) {
                if (array.get(idxAfter) < array.get(idx)) {
                    rightSmallerCount++;
                }
            }
            rightSmallerCounts.add(rightSmallerCount);
        }
        return rightSmallerCounts;
    }
    // average case: when created BST is balanced
    // O(nlog(n)) time | O(n) space
    // worst case: when the created BST is like a linked list
    // O(n^2) time | O(n) space
    public static List<Integer> rightSmallerThanBST(List<Integer> array) {
        if (array.size() == 0) return new ArrayList<>();

        int lastIdx = array.size() - 1;
        // create the root of the tree with the last element
        SpecialBST specialBSTRoot = new SpecialBST(array.get(lastIdx),
                                        lastIdx,
                     0);
        // start from second from last element and start inserting into bst
        for (int i = array.size() - 2; i >= 0; i--) {
            specialBSTRoot.insert(array.get(i), i);
        }

        List<Integer> rightSmallerCounts = new ArrayList<>(array);
        // construct the solution list
        constructRightSmallerCountsFromBST(specialBSTRoot, rightSmallerCounts);
        return rightSmallerCounts;

    }

    // preorder traversal
    private static void constructRightSmallerCountsFromBST(SpecialBST bst,
                                                           List<Integer> rightSmallerCounts) {
        if (bst == null) return;
        rightSmallerCounts.set(bst.idx, bst.numSmallerToRightAtInsertTime);
        constructRightSmallerCountsFromBST(bst.left, rightSmallerCounts);
        constructRightSmallerCountsFromBST(bst.right, rightSmallerCounts);
    }

    static class SpecialBST {
        int value;
        int idx;
        int numSmallerToRightAtInsertTime; // keep track of the number of elements smaller at insert time
        int leftSubtreeSize;        // keep track of the left subtree size
        SpecialBST left;
        SpecialBST right;

        SpecialBST(int value, int idx, int numSmallerAtInsertTime) {
            this.value = value;
            this.idx = idx;
            this.numSmallerToRightAtInsertTime = numSmallerAtInsertTime;
            leftSubtreeSize = 0;
            left = null;
            right = null;
        }

        /**
         * When inserting, need to keep track of numSmallerAtInsertTime, idx and value
         */
        void insert(int value, int idx) {
            insertHelper(value, idx, 0);
        }

        private void insertHelper(int value, int idx, int numSmallerAtInsertTime) {
            if (value < this.value) {
                // insert to the left branch
                leftSubtreeSize++; // increase leftSubtree size
                if (left == null) {
                    left = new SpecialBST(value, idx, numSmallerAtInsertTime);
                } else {
                    left.insertHelper(value, idx, numSmallerAtInsertTime);
                }
            } else {
                // insert to the right branch will need to update numSmallerAtInsertTime
                numSmallerAtInsertTime += leftSubtreeSize;
                if (value > this.value) numSmallerAtInsertTime++;
                if (right == null) {
                    right = new SpecialBST(value, idx, numSmallerAtInsertTime);
                } else {
                    right.insertHelper(value, idx, numSmallerAtInsertTime);
                }
            }
        }
    }


    // average case: when created BST is balanced
    // O(nlog(n)) time | O(n) space
    // worst case: when the created BST is like a linked list
    // O(n^2) time | O(n) space
    public static List<Integer> rightSmallerThanBST2(List<Integer> array) {
        if (array.size() == 0) return new ArrayList<>();
        List<Integer> rightSmallerCounts = new ArrayList<>(array);

        int lastIdx = array.size() - 1;
        SpecialBST2 bst = new SpecialBST2(array.get(lastIdx));
        rightSmallerCounts.set(lastIdx, 0);

        // start from second from last number
        for (int i = array.size() - 2; i >= 0; i--) {
            // insert into tree and update the rightSmallerCounts list
            bst.insert(array.get(i), i, rightSmallerCounts);
        }

        return rightSmallerCounts;

    }

    static class SpecialBST2 {
        int value;
        int leftSubtreeSize;
        SpecialBST2 left;
        SpecialBST2 right;

        SpecialBST2(int value) {
            this.value = value;
            leftSubtreeSize = 0;
            left = null;
            right = null;
        }

        void insert(int value, int idx, List<Integer> rightSmallerCounts) {
            insertHelper(value, idx, rightSmallerCounts, 0);
        }

        private void insertHelper(int value,
                                  int idx,
                                  List<Integer> rightSmallerCounts,
                                  int numSmallerAtInsertTime) {
            if (value < this.value) {
                // insert to the left branch
                leftSubtreeSize++; // increase leftSubtree size
                if (left == null) {
                    left = new SpecialBST2(value);
                    rightSmallerCounts.set(idx, numSmallerAtInsertTime);
                } else {
                    left.insertHelper(value,
                                     idx,
                                     rightSmallerCounts,
                                     numSmallerAtInsertTime);
                }
            } else {
                // insert to the right branch
                numSmallerAtInsertTime += leftSubtreeSize;
                if (value > this.value) numSmallerAtInsertTime++;
                if (right == null) {
                    right = new SpecialBST2(value);
                    rightSmallerCounts.set(idx, numSmallerAtInsertTime);
                } else {
                    right.insertHelper(value,
                                       idx,
                                       rightSmallerCounts,
                                       numSmallerAtInsertTime);
                }
            }
        }
    }

}
