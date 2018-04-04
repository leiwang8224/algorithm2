package LinkedList;


import Tree.*;

/**
 * Created by leiwang on 4/3/18.
 */
public class TreeToLinkedList {
    private Tree.ListNode prev = null;
    public static void main(String[] args) {

    }

    private void flattenTree(Tree.ListNode root) {
        if (root == null)
            return;

        flattenTree(root.right);
        flattenTree(root.left);
        root.right = prev;
        root.left = null;
        prev = root;
    }

    private void flattenTreeIterative(Tree.ListNode root) {
        Tree.ListNode cur = root;
        while (cur!= null) {
            if (cur.left != null) {
                // find current node's prenode that links
                // to current node's right subtree
                Tree.ListNode pre = cur.left;
                while (pre.right != null) {
                    pre = pre.right;
                }
                pre.right = cur.right;
                // use current node's left subtree to replace
                // its right subtree
                // original right subtree is already linked
                // by current node's prenode
                cur.right = cur.left;
                cur.left = null;
            }
            cur = cur.right;
        }
    }
}
