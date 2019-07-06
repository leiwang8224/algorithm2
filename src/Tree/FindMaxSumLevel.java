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

        ListNode root2 = new ListNode(1);
        root2.left = new ListNode(2);
        root2.right = new ListNode(3);
        root2.left.left = new ListNode(4);
        root2.left.right = new ListNode(5);
        root2.right.left = new ListNode(6);
        root2.right.right = new ListNode(7);
        root2.left.left.left = new ListNode(8);

        ListNode rootNotBalanced = new ListNode(9);
        rootNotBalanced.left = new ListNode(8);
        rootNotBalanced.left.left = new ListNode(4);
        rootNotBalanced.left.left.right = new ListNode(6);
        System.out.println("max is " + findMaxSumLevel(rootNotBalanced));
        System.out.println("max is " + findMaxSumLevel2(rootNotBalanced));

    }

    //NOTE THIS IMPLEMENTATION IS WRONG TO SHOW THAT NOT ALL LEVEL ORDER TRAVERSAL WORKS
    //ONLY WORKS FOR BALANCED TREES!
    private static int findMaxSumLevel2(ListNode root) {
        if (root == null) return -1;
        int currSum = 0, maxSum = 0;
        int currLevel = 0, maxLevel = 0;

        Queue<ListNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            for (int index = 0; index < size; index++) {
                ListNode node = q.poll();

                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
                // NOTE this is currSum from last level,
                // so need to subtract one level in result
                currSum = currSum + node.getVal();
            }
            if (currSum > maxSum) {
                maxSum = currSum;
                maxLevel = currLevel-1;
            }
            currLevel++;
            printQueue(q,currLevel,currSum);
        }
        return maxLevel;
    }

    private static int findMaxSumLevel(ListNode root) {
        if (root == null) return -1;
        int currSum = 0, maxSum = 0;
        int currLevel = 0, maxLevel = 0;

        ListNode cur = null;
        Queue<ListNode> q = new LinkedList<>();

        q.add(root);
        // push null for level delimiter
        q.add(null); // end of first level indicator

        while (!q.isEmpty()) {
            cur = q.poll();
            // if at the end of the current level, compare sum and process result
            // if null is found we are at the end of the level
            if (cur == null) {
                if (currSum > maxSum) {
                    maxSum = currSum;
                    maxLevel = currLevel;
                }

                currSum = 0; // reset
                // if q is not empty, that means there is more nodes (next level)
                if (!q.isEmpty()) {
                    q.add(null); // add end of level indicator
                }
                currLevel++; // begin next level
            } else {
                currSum += cur.getVal();
                if (cur.left != null)
                    q.add(cur.left);
                if (cur.right != null)
                    q.add(cur.right);
            }
            printQueue(q,currLevel,currSum);
        }
        System.out.println();
        return maxLevel;
    }

    private static void printQueue(Queue<ListNode> q, int level, int currSum) {
        StringBuilder str = new StringBuilder();
        System.out.print("level = " + level + " sum = " + currSum + " - ");
        for (ListNode node : q) {
            if (node == null) str.append("null,");
            else str.append(node.getVal() + ",");
        }
        System.out.println(str.toString());
    }
}
