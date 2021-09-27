package AlgoExpert.VeryHard;

import Tree.ListNode;

public class RightSiblingTree {

    // O(n) time | O(d) space for recursive calls - where n is the number of nodes
    // in the binary tree and d is the depth(height) of binary tree
    // for every "left child" node point its right child pointer to its parent's right child
    // for every "right child" node connect the right child pointer to the right parent's left child
    ListNode rightSiblingTree(ListNode root) {
        mutateDFS(root, null, false);
        return root;
    }

    void mutateDFS(ListNode node, ListNode parent, boolean isLeftChild) {
        if (node == null) return;

        // save left and right ptrs before recursion
        ListNode left = node.left;
        ListNode right = node.right;

        // calling on left subtree
        mutateDFS(left, node, true);
        // all the processing
        if (parent == null) { // root node
            node.right = null;
        } else if (isLeftChild) { // point right child ptr to its parent's right child
            node.right = parent.right;
        } else { // (right child) point the right child ptr to the right parent's left child
            if (parent.right == null) {
                node.right = null;
            } else {
                node.right = parent.right.left; // parent.right is already connected to the right subtree
                                                // so just use the right ptr's left ptr
            }
        }
        // calling on right subtree
        mutateDFS(right, node, false);
    }
}
