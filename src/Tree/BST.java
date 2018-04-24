package Tree;

import java.util.Stack;

/**
 * Created by leiwang on 4/4/18.
 */
public class BST {
    public static void main(String args[]) {
        ListNode head = new ListNode(8);
        head.left = new ListNode(3);
        head.left.left = new ListNode(1);
        head.left.right = new ListNode(6);
        head.left.right.left = new ListNode(4);
        head.left.right.right = new ListNode(7);
        head.right = new ListNode(10);
        head.right.right = new ListNode(14);
        head.right.right.left = new ListNode(13);

        printBST(head);

        insert(head,5);

        System.out.println("afterInsert");
        printBST(head);

        System.out.println("afterDeletion");
        printBST(delete(head, 3));
        printBST(head);

        BSTIterator bstIterator = new BSTIterator(head);
        System.out.println("using BST Iterator");
        while (bstIterator.hasNext())
            System.out.println(bstIterator.next());
    }

    private static void printBST(ListNode head) {
        if (head == null)
            return;
        System.out.println(head.getVal());
        printBST(head.left);
        printBST(head.right);
    }
    private static ListNode search(ListNode head, int key) {
        if (head == null || head.getVal() == key)
            return head;

        if (head.getVal() > key) {
            return search(head.left, key);
        }

        return search(head.right, key);
    }

    private static ListNode insert(ListNode head, int key) {
        if (head == null) {
            head = new ListNode(key);
            return head;
        }

        if (key < head.getVal()) {
            head.left = insert(head.left, key);
        } else if (key > head.getVal()) {
            head.right = insert(head.right, key);
        }

        return head;
    }

    private static ListNode delete(ListNode head, int key) {
        if (head == null) return head;

        if (key < head.getVal()) {
            head.left = delete(head.left, key);
        } else if (key > head.getVal()) {
            head.right = delete(head.right, key);
        } else {
            // if key is same as head's key
            // then this is node to be deleted

            // node with only one child or no child
            if (head.left == null) {
                return head.right;
            } else if (head.right == null) {
                return head.left;
            }

            // node with two children
            // get the inorder successor
            // smallest in the right subtree
            head.setVal(minVal(head.right));

            // delete the inorder successor
            head.right = delete(head.right, head.getVal());
        }
        return head;
    }

    private static int minVal(ListNode head) {
        int minv = head.getVal();
        while (head.left != null) {
            minv = head.left.getVal();
            head = head.left;
        }
        return minv;
    }

    /**
     * BSTIterator initializes with the root node of a BST
     * Calling next() will return the next smallest number in the
     * BST. Note next() and hasNext() runs in O(1) and uses O(h)
     * memory, where h is the height of the tree.
     */
    private static class BSTIterator {
        Stack<ListNode> stack;

        public BSTIterator(ListNode root) {
            stack = new Stack<ListNode>();
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public int next() {
            ListNode node = stack.pop();
            int result = node.getVal();
            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
            return result;
        }
    }

}
