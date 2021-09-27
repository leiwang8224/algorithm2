package Tree;

import java.util.LinkedList;
import java.util.Queue;

public class PopulatingNextRightPtr {
    public static void main(String[] args) {
        ListNodeThisProblem root = new ListNodeThisProblem(1);
        root.left = new ListNodeThisProblem(2);
        root.right = new ListNodeThisProblem(3);
        root.left.left = new ListNodeThisProblem(4);
        root.left.right = new ListNodeThisProblem(5);
        root.right.left = new ListNodeThisProblem(6);
        root.right.right = new ListNodeThisProblem(7);

        connect(root);

        printThisTree(root);
    }

    static void printThisTree(ListNodeThisProblem root) {
        if (root == null) {
//            System.out.println("#");
            return;
        }

        printThisTree(root.left);
        System.out.println(root == null ? "#" : root.val);
        printThisTree(root.right);
    }

    static class ListNodeThisProblem {
        int val;
        ListNodeThisProblem left;
        ListNodeThisProblem right;
        ListNodeThisProblem next;
        ListNodeThisProblem (int val) {
            this.val = val;
        }

    }
    private static void connect(ListNodeThisProblem head) {
        if (head == null) return;

        Queue<ListNodeThisProblem> queue = new LinkedList<>();

        // trick is to push null to maintain depth info
        queue.offer(head);
        queue.offer(null);

        while (!queue.isEmpty()) {
            ListNodeThisProblem curr = queue.poll();
            queue.poll();

            if (curr == null) {
                if (!queue.isEmpty()) queue.offer(null);
            } else {
                curr.next = queue.poll();
                if (curr.left != null ) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }

        }
    }

    private void connect2(ListNodeThisProblem root) {
        if (root == null) return;
        helper (root.left, root.right);
    }

    private void helper(ListNodeThisProblem left, ListNodeThisProblem right) {
        if (left == null) return;
        left.next = right;
        helper(left.left, left.right);
        helper(right.left, right.right);
        helper(left.right, right.left);
    }

    private ListNodeThisProblem connect3(ListNodeThisProblem root) {
        if (root == null || root.left == null) return root;

        root.left.next = root.right;
        if (root.next != null) root.right.next = root.next.left;

        connect3(root.left);
        connect3(root.right);
        return root;
    }


}
