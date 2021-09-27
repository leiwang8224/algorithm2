package AlgoExpert.Easy;


import Tree.ListNode;

import java.util.Stack;

public class NodeDepths {
    public static void main(String[] args) {

    }

    // Average case is when the tree is balanced
    // O(n) time | O(h) space - where n is the number of nodes
    // in the binary tree and h is the height of the tree
    private static int nodeDepths (ListNode root) {
        int sumOfDepths = 0;
        Stack<TreeInfo> stack = new Stack<>();
        stack.push(new TreeInfo(root, 0));

        while (stack.size() > 0) {
            TreeInfo curLevel = stack.pop();
            ListNode curNode = curLevel.root;
            int curDepth = curLevel.depth;
            // once we are at the end of the branch, exit
            // no need to tell what the children depths are
            if (curNode == null) continue;
            sumOfDepths += curDepth;
            // parent node tells children nodes what their depth are
            stack.push(new TreeInfo(curNode.left, curDepth+1));
            stack.push(new TreeInfo(curNode.right, curDepth +1));
        }

        return sumOfDepths;
    }

    static class TreeInfo {
        public ListNode root;
        int depth;

        public TreeInfo(ListNode root, int depth) {
            this.root = root;
            this.depth = depth;
        }
    }

    // Average case when the tree is balanced
    // O(n) time | O(h) space - where n is the number of nodes in
    // the binary tree and h is the height of the tree
    private static int nodeDepths2 (ListNode root) {
        return nodeDepthsHelper(root, 0);
    }

    private static int nodeDepthsHelper(ListNode root, int depth) {
        if (root == null) return 0;
        return depth + nodeDepthsHelper(root.left, depth+1) +
                        nodeDepthsHelper(root.right, depth+1);
    }

    private static int findDepthSumDFSMySol(ListNode root) {
        Stack<TreeInfo> stack = new Stack<>();
        int sum = 0;
        stack.push(new TreeInfo(root, 0));

        while (!stack.isEmpty()) {
            TreeInfo treeInfo = stack.pop();
            sum = sum + treeInfo.depth;
            if (treeInfo.root.left != null) {
                stack.push(new TreeInfo(treeInfo.root.left, treeInfo.depth+1));
            }
            if (treeInfo.root.right != null) {
                stack.push(new TreeInfo(treeInfo.root.right, treeInfo.depth+1));
            }
        }

        return sum;
    }

}
