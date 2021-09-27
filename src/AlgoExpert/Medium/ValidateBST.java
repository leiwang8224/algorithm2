package AlgoExpert.Medium;

import CTCI.TreesAndGraphs.TreeUtils;
import Tree.ListNode;

public class ValidateBST {
    public static void main(String[] args) {

    }

    // O(n) time | O(d) space
    private static boolean validateBst(ListNode tree) {
        return validateBst(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    // list all conditions that would invalidate the BST
    // finally return true
    private static boolean validateBst(ListNode tree, int minValue, int maxValue) {
        if (tree.getVal() < minValue || tree.getVal() >= maxValue)
            return false;
        if (tree.left != null && !validateBst(tree.left, minValue, tree.getVal()))
            return false;
        if (tree.right != null && !validateBst(tree.right, tree.getVal(), maxValue))
            return false;
        return true;
    }

    private static boolean isBST(ListNode root, int min, int max) {
        // if we have reached the leaves and haven't returned false, then it must be true
        if (root == null) return true;

        // return false immediately if the node does not meet BST criteria
        if (root.getVal() < min || root.getVal() > max) return false;

        // recurse on the BST
        return isBST(root.left, min, root.getVal()) &&
                isBST(root.right, root.getVal(), max);


    }
}
