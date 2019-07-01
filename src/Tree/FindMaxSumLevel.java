package Tree;

import java.util.LinkedList;
import java.util.Queue;

public class FindMaxSumLevel {
    public static void main(String[] args) {
        ListNode root = new ListNode(1);
        root.left = new ListNode(2);
        root.right = new ListNode(3);
        root.left.left = new ListNode(4);
        root.left.right = new ListNode(5);
        root.right.left = new ListNode(6);
        root.right.right = new ListNode(7);
        root.left.left.left = new ListNode(8);

        System.out.println("max is " + findMaxSumLevel(root));

    }

    private static int findMaxSumLevel(ListNode root) {
        if (root == null) return -1;
        int currSum = 0, maxSum = 0;
        int currLevel = 0, maxLevel = 0;

        ListNode cur = null;
        Queue<ListNode> q = new LinkedList<>();

        q.add(root);
        q.add(null); // end of first level indicator

        while (!q.isEmpty()) {
            cur = q.poll();
            // if at the end of the current level, compare sum and process result
            if (cur == null) {
                if (currSum > maxSum) {
                    maxSum = currSum;
                    maxLevel = currLevel;
                }

                currSum = 0; // reset
                if (!q.isEmpty()) {
                    q.add(null); // end of level indicator
                }
                currLevel++; // begin next level
            } else {
                currSum += cur.getVal();
                if (cur.left != null)
                    q.add(cur.left);
                if (cur.right != null)
                    q.add(cur.right);
            }
        }
        return maxLevel;
    }
}
