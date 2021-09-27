package AlgoExpert.VeryHard;

import Tree.ListNode;
import java.util.*;

public class FlattenBinaryTree {
    //O(n) time | O(n) space
    public static ListNode flattenBinaryTree(ListNode root) {
        List<ListNode> inOrderNodes = getNodesInOrder(root, new ArrayList<ListNode>());
        for (int i = 0; i < inOrderNodes.size() - 1; i++) {
            // get left and right nodes and connect them
            ListNode leftNode = inOrderNodes.get(i);
            ListNode rightNode = inOrderNodes.get(i + 1);
            leftNode.right = rightNode;
            rightNode.left = leftNode;
        }
        return inOrderNodes.get(0);
    }

    private static List<ListNode> getNodesInOrder(ListNode root,
                                                  ArrayList<ListNode> array) {
        if (root != null) {
            getNodesInOrder(root.left, array);
            array.add(root);
            getNodesInOrder(root.right, array);
        }
        return array;
    }

    // At any given node in the in order traversal order the node immediately to its
    // left is the rightmost node of its left subtree, and the node immediately to its
    // right is the leftmost node of its right subtree
    // O(n) time | O(d) space
    public static ListNode flattenBinaryTreeOptimized(ListNode root) {
        ListNode leftMost = flattenTree(root)[0];
        return leftMost;
    }

    // list node array contains the left most node in left tree and right most node in right tree
    private static ListNode[] flattenTree(ListNode root) {
        ListNode leftMostLeftTree;
        ListNode rightMostRightTree;

        if (root.left == null) {
            leftMostLeftTree = root;
        } else { // left is not null so recurse on left node and connect rightmost node on the left tree to root
            // {leftSubtreeLeftMost, leftSubtreeRightMost}
            ListNode[] leftAndRightMostNodes = flattenTree(root.left);
            // leftSubtreeRightMost, root
            connectNodes(leftAndRightMostNodes[1], root);
            // leftMost = leftSubtreeLeftMost
            leftMostLeftTree = leftAndRightMostNodes[0];
        }

        if (root.right == null) {
            rightMostRightTree = root;
        } else {
            // {rightSubtreeLeftMost, rightSubtreeRightMost}
            ListNode[] leftAndRightMostNodes = flattenTree(root.right);
            // root, rightSubtreeLeftMost
            connectNodes(root, leftAndRightMostNodes[0]);
            // rightMost = rightSubtreeRightMost
            rightMostRightTree = leftAndRightMostNodes[1];
        }

        // the list starts with leftMostLeftTree and ends with rightMostRightTree
        return new ListNode[]{leftMostLeftTree, rightMostRightTree};
    }

    private static void connectNodes(ListNode left, ListNode right) {
        left.right = right;
        right.left = left;
    }
}
