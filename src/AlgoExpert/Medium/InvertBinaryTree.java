package AlgoExpert.Medium;

import Tree.ListNode;

import java.util.LinkedList;
import java.util.Queue;

public class InvertBinaryTree {
    public static void main(String[] args) {

    }

    // O(n) time | O(n) space
    private static void invertBinaryTree(ListNode root) {
        // BFS traversal
        Queue<ListNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            ListNode curNode = queue.poll();
            // invert the tree
            ListNode left = curNode.left;
            curNode.left = curNode.right;
            curNode.right = left;
            // push the children onto the queue for further processing
            if (curNode.left != null) queue.offer(curNode.left);
            if (curNode.right != null) queue.offer(curNode.right);
        }
    }

    //O(n) time | O(d) space
    private static void invertBinaryTree2(ListNode root) {
        if (root == null) return;

        // invert the subtree
        ListNode left = root.left;
        root.left = root.right;
        root.right = left;

        // traverse left and traverse right
        invertBinaryTree2(root.left);
        invertBinaryTree2(root.right);
    }
}
