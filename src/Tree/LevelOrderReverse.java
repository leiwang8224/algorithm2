package Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class LevelOrderReverse {
    public static void main(String[] args) {
        ArrayList<Integer> result = levelOrderRev(BuildTree.generateTree());
        System.out.println(result);

    }

    private static ArrayList<Integer> levelOrderRev(ListNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Queue<ListNode> q = new LinkedList<>();
        Stack<ListNode> stack = new Stack<>();
        q.offer(root);

        while (!q.isEmpty()) {
            ListNode node = q.poll();
            if (node.right != null) { // IMPORTANT to get right node first!!!
                q.offer(node.right);
            }
            if (node.left != null) {
                q.offer(node.left);
            }
            stack.push(node);
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop().getVal());
        }

        return result;
    }
}
