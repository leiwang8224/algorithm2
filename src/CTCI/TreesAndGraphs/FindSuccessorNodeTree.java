package CTCI.TreesAndGraphs;

import CTCI.TreesAndGraphs.TreeUtils.TreeNode;

// Write an algorithm to find the 'next' node of a given node in
// a binary search tree. You may assume that each node has a link
// to its parent. (In order traversal 'next' node), root, left, right.
// Also defined as the node with the smallest key greater than
// the key of input node.
public class FindSuccessorNodeTree {
    public static void main(String[] args) {
        TreeUtils.printTree(TreeUtils.generateBST());
        System.out.println();
        System.out.println(TreeUtils.generateBST().right.val);
        System.out.println();
        System.out.println(inorderSucc(TreeUtils.generateBST().right).val);

    }

    private static TreeNode inorderSucc(TreeNode n) {
        if (n == null) return null;

        // found right children -> return left most node of right subtree
        if (n.parent == null || n.right != null) {
            // find smallest value greater than current node
            return leftMostChild(n.right);
        } else { // no right children
            TreeNode nodeOfInterest = n;
            TreeNode parent = nodeOfInterest.parent;
            // go up until we are on left instead of right
            while (parent != null && parent.left != nodeOfInterest) {
                nodeOfInterest = parent;
                parent = parent.parent;
            }

            return parent;
        }
    }

    private static TreeNode leftMostChild(TreeNode node) {
        if (node == null) return null;
        while (node.left != null) node = node.left;

        return node;
    }
}
