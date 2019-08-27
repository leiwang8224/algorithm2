package CTCI.TreesAndGraphs;

public class TreeUtils {
    static class TreeNode {
        TreeNode left;
        TreeNode right;
        TreeNode parent;
        int val;

        TreeNode(int val) {
            this.val = val;
        }

        void setLeftChild(TreeNode left) {
            this.left = left;
            if (left != null) left.parent = this;
        }

        void setRightChild(TreeNode right) {
            this.right = right;
            if (right != null) right.parent = this;
        }
    }

    static TreeNode generateTree() {
        TreeNode root = new TreeNode(0);
        root.left = new TreeNode(1);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(6);

        return root;
    }

    static TreeNode generateBST() {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(7);

        return root;
    }

    static void printTree(TreeNode root) {
        if (root == null) return;
        printTree(root.left);
        System.out.print(root.val);
        printTree(root.right);
    }
}
