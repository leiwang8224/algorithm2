package LinkedList;


import Tree.*;

/**
 * Created by leiwang on 4/3/18.
 */
public class TreeToLinkedList {
    private static Tree.ListNode prev = null;
    public static void main(String[] args) {

        printTree(generateTree());
        Tree.ListNode head = generateTree();
        System.out.println("flattenTree");
        flattenTree(head);
        printTree(head);

        System.out.println("flattenTreeIterative");
        Tree.ListNode head1 = generateTree();
        flattenTreeIterative(head1);
        printTree(head1);



    }

    private static Tree.ListNode generateTree() {
        Tree.ListNode head = new Tree.ListNode(0);
        head.left = new Tree.ListNode(1);
        head.right = new Tree.ListNode(2);
        head.left.left = new Tree.ListNode(3);
        head.left.right = new Tree.ListNode(4);
        head.right.left = new Tree.ListNode(5);
        head.right.right = new Tree.ListNode(6);
        return head;

    }

    private static void printTree(Tree.ListNode root) {
        if (root == null)
            return;
        System.out.println(root.getVal());
        printTree(root.left);
        printTree(root.right);
    }

    private static void flattenTree(Tree.ListNode root) {
        if (root == null)
            return;

        flattenTree(root.right);
        flattenTree(root.left);
        root.right = prev;
        root.left = null;
        prev = root;
    }

    private static void flattenTreeIterative(Tree.ListNode root) {
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
