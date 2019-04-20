package Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class DecompressTree {
    public static void main(String[] args) {

        BST.printBST(decompressTree("1,2,3,*,5,1,4"));
    }

    private static ListNode decompressTree(String str) {
        if (str == null || str == "*" || str.length() == 0) {
            return null;
        }

        ArrayList<Integer> nodes = new ArrayList<>();
        String[] strSplits = str.split(",");
        for (String s : strSplits) {
            // arraylist can have null values and integer values
            Integer num = s.equals("*") ? null : Integer.parseInt(s);
            nodes.add(num);
        }

        Queue<ListNode> q = new LinkedList<>();
        int i = 0, size = nodes.size();

        ListNode head = new ListNode(nodes.get(0));
        q.add(head);

        int index = 0;
        while(index < nodes.size()) {
            ListNode node = q.poll();
            if (node == null) {
                index+=2;
                continue;
            } else {
                Integer leftVal = index+1 < size ? nodes.get(index+1) : null;
                Integer rightVal = index+2 < size ? nodes.get(index+2) : null;
                ListNode leftChild = leftVal != null ? new ListNode(leftVal) :null;
                ListNode rightChild = rightVal != null ? new ListNode(rightVal) :null;
                node.left = leftChild;
                node.right = rightChild;
                q.add(leftChild);
                q.add(rightChild);
                index+=2;

            }
        }

        return head;
    }
}
