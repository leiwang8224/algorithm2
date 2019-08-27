package CTCI.TreesAndGraphs;

import CTCI.TreesAndGraphs.TreeUtils.TreeNode;

public class ValidateBST {
    public static void main(String[] args) {
        System.out.println(isBST(TreeUtils.generateTree(), Integer.MIN_VALUE, Integer.MAX_VALUE));
        System.out.println(isBST(TreeUtils.generateBST(), Integer.MIN_VALUE, Integer.MAX_VALUE));

    }

    private static boolean isBST(TreeNode root, int min, int max) {
        if (root == null) return true;

        if (root.val < min || root.val > max) return false;

        return isBST(root.left, min, root.val) && isBST(root.right, root.val, max);


    }
}
