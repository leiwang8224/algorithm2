package CTCI.TreesAndGraphs;

import CTCI.TreesAndGraphs.TreeUtils.TreeNode;

// Implement algorithm to chekc if binary tree is balanced. A balanced
// tree is defined to be a tree such that the heights of the two subtrees
// of any node never differ more than one level.
public class CheckIfTreeBalanced {
    public static void main(String[] args) {

    }

    private static boolean isTreeBalanced(TreeNode root) {
        // find number of levels in the left tree and
        // number of levels on the right tree and compare
        // if levels differs more than one then it is not
        // balanced, else it is balanced tree.
        if (root == null) return true;

        int heightDiff = getHeight(root.left) - getHeight(root.right);
        if (Math.abs(heightDiff) > 1) return false;
        else return isTreeBalanced(root.left) && isTreeBalanced(root.right);
    }

    
    private static int getHeight(TreeNode root) {
        if (root == null) return -1;
        return Math.max(getHeight(root.left), getHeight(root.right));
    }

    private static boolean isBalancedImproved(TreeNode root) {
        return checkHeightImproved(root) != Integer.MIN_VALUE;
    }

    private static int checkHeightImproved(TreeNode root) {
        if (root == null) return -1;

        // not tail recursion
        int leftHeight = checkHeightImproved(root.left);
        if (leftHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;

        int rightHeight = checkHeightImproved(root.right);
        if (rightHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;

        int heightDiff = leftHeight - rightHeight;
        if (Math.abs(heightDiff) > 1) return Integer.MIN_VALUE;
        // increment the max of the left and right height
        else return Math.max(leftHeight, rightHeight) + 1;
    }
}
