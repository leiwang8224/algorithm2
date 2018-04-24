package DFS;

/**
 * Created by leiwang on 3/25/18.
 */

import Tree.ListNode;

/**
 * Given binary tree, for each node add sum of all the nodes in its
 * hierarchy to its value
 * Algorithm is top-down, flow of data is always bottom up
 */
public class AddBinaryTreeNodes {
    public static void main (String args[]) {
        ListNode head = new ListNode(0);
        head.left = new ListNode(1);
        head.right = new ListNode(2);
        head.left.left = new ListNode(3);
        head.left.right = new ListNode(4);
        head.right.left = new ListNode(5);
        head.right.right = new ListNode(6);
        System.out.println("before processing ");
        printTreeNodes(head);
        addChildSum(head);
        System.out.println("result:");
        printTreeNodes(head);
    }

    private static void addChildSum(ListNode head) {
        if (head == null) return;
        addChildSum(head.left);
        addChildSum(head.right);

        int finalSum = head.getVal();
        if (head.left != null) {
            finalSum += head.left.getVal();
        }
        if (head.right != null) {
            finalSum += head.right.getVal();
        }
        // calculate finalSum then set it to the current node
        head.setVal(finalSum);
    }

    private static void printTreeNodes(ListNode head) {
        if (head == null)
            return;
        if (head.left== null && head.right == null)
            System.out.println("leaf node " + head.getVal());
        else
            System.out.println("node " + head.getVal());
        printTreeNodes(head.left);
        printTreeNodes(head.right);
    }

}
