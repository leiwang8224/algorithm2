package Tree;

import java.util.LinkedList;
import java.util.Queue;

public class NumberFullNodes {
    public static void main(String[] args) {
        ListNode root = new ListNode(1);
        root.left = new ListNode(2);
        root.right = new ListNode(3);
        root.left.left = new ListNode(4);
        root.left.right = new ListNode(5);
        root.right.left = new ListNode(6);
        root.right.right = new ListNode(7);
        root.left.left.left = new ListNode(8);
        root.left.left.right = new ListNode(9);
//        System.out.println(numberOfFullNodes(root));
        System.out.println(findNumberNodes(root));

    }

    private static int numberOfFullNodes(ListNode root) {
        if (root == null) return 0;
        int numberOfFullNodes = 0;
        Queue<ListNode> q = new LinkedList<>();

        q.offer(root);

        while(!q.isEmpty()) {
            int size = q.size();
            ListNode node = q.poll();

            if (node.left != null && node.right != null) {
                System.out.println("node = " + node.getVal());
                numberOfFullNodes++;
            }

            for (int index = 0; index < size; index++) {
                if (node.left != null)
                    q.offer(node.left);
                if (node.right != null)
                    q.offer(node.right);
            }
        }

        return numberOfFullNodes;
    }

    private static int findNumberNodes(ListNode root) {
        if (root == null) return 0;
        int numberOfNodes = 0;
        Queue<ListNode> q = new LinkedList<>();

        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            ListNode node = q.poll();
            if (node != null) {
                System.out.println(node.getVal());
                numberOfNodes++;
            }

//            for (int index = 0; index < size; index++) {
                if (node.left != null)
                    q.offer(node.left);
                if (node.right != null)
                    q.offer(node.right);
//            }
        }
        return numberOfNodes;
    }
}
