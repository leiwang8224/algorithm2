package AlgoExpert.Medium;

import java.util.List;

public class MinHeightBST {
    public static void main(String[] args) {

    }

    // O(nlog(n)) time | O(n) space
    private static BST minHeightBst(java.util.List<Integer> array) {
        // call recursion from the outside
        return constructMinHeightBst(array,
                null,
                0,
                array.size()-1);
    }

    private static BST constructMinHeightBst(List<Integer> array,
                                             BST head,
                                             int startIdx,
                                             int endIdx) {
        if (endIdx < startIdx) return null;
        // get mid point
        int midIdx = (startIdx + endIdx) / 2;
        // get value to add from mid point
        int valueToAdd = array.get(midIdx);
        // make the root of the tree
        if (head == null) head = new BST(valueToAdd);
        // else just insert the mid value calculated
        else head.insert(valueToAdd);

        // pass the same head down the recursion tree
        constructMinHeightBst(array, head, startIdx, midIdx-1);
        constructMinHeightBst(array, head, midIdx+1, endIdx);

        return head;
    }

    // O(n) time | O(n) space
    /**
     * Does not use the given insert API
     */
    private static BST minHeightBst2(List<Integer> array) {
        return constructMinHeightBst2(array, null, 0, array.size() -1);
    }

    private static BST constructMinHeightBst2(List<Integer> array,
                                              BST nodePassedIn,
                                              int startIdx,
                                              int endIdx) {
        // if endIndex less than start then return null (at end of the leaf)
        if (endIdx < startIdx) return null;
        // get middle idx
        int midIdx = (startIdx + endIdx) / 2;
        // construct new node from mid idx
        BST newBstNode = new BST(array.get(midIdx));
        // if node passed in is null, switch the pointer to point to the new node(head)
        if (nodePassedIn == null) {
            nodePassedIn = newBstNode;
        } else {
            // else build on existing tree
            // if the mid value is less than the node passed in
            // construct left node and traverse to the left
            if (array.get(midIdx) < nodePassedIn.value) {
                nodePassedIn.left = newBstNode;
                nodePassedIn = nodePassedIn.left;
            } else {
                // mid value is greater than the node passed in
                // construct right node and traverse to the right
                nodePassedIn.right = newBstNode;
                nodePassedIn = nodePassedIn.right;
            }
        }
        // recurse, note that the startIdx is the same for left
        // endIdx is the same for right
        constructMinHeightBst2(array, nodePassedIn, startIdx, midIdx - 1);
        constructMinHeightBst2(array, nodePassedIn, startIdx + 1, endIdx);
        // return the head
        return nodePassedIn;
    }

    // O(n) time | O(n) space
    private static BST minHeightBst3(List<Integer> array) {
        return constructMinHeightBst3(array,
                0,
                array.size()-1);
    }

    private static BST constructMinHeightBst3(List<Integer> array, int startIdx, int endIdx) {
        if (endIdx < startIdx) return null;
        int midIdx = (startIdx + endIdx) / 2;
        // start recursion inside the function
        BST head = new BST(array.get(midIdx));
        head.left = constructMinHeightBst3(array, startIdx, midIdx - 1);
        head.right = constructMinHeightBst3(array, midIdx + 1, endIdx);
        return head;
    }

    static class BST{
        public int value;
        public BST left;
        public BST right;

        public BST(int value) {
            this.value = value;
            left = null;
            right = null;
        }

        public void insert(int value) {
            if (value < this.value) {
                if (left == null) {
                    left = new BST(value);
                } else {
                    left.insert(value);
                }
            } else {
                if (right == null) {
                    right = new BST(value);
                } else {
                    right.insert(value);
                }
            }
        }
    }
}
