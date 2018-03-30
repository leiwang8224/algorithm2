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
        addChildSum(head);
    }

    private static void addChildSum(ListNode head) {
        if (head == null) return;
        addChildSum(head.left);
        addChildSum(head.right);

        int finalSum = head.getVal();
        System.out.println(finalSum);
        if (head.left != null) {
            finalSum += head.left.getVal();
        }
        if (head.right != null) {
            finalSum += head.right.getVal();
        }
        head.setVal(finalSum);
        System.out.println("headVal = " + head.getVal() + "finalSum = " + finalSum);
    }

}
