package Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeRightSideView {
    public static void main(String[] args) {

    }

    private static List<Integer> rightSideViewBFS (ListNode root) {
        List<Integer> lst = new ArrayList<>();
        Queue<ListNode> q = new LinkedList<>();

        if (root == null) {
            return lst;
        }

        q.offer(root);

        while(!q.isEmpty()) {
            int size = q.size();
            lst.add(q.peek().getVal()); // get the head, since we are adding from right
                                        // to left. So at anytime the head will be rightmost node

            for (int i = 0; i < size; i ++) {
                ListNode node = q.poll();
                if (node.right != null) {
                    q.offer(node.right);
                }
                if (node.left != null) {
                    q.offer(node.left);
                }
            }
        }

        return lst;
    }

//    The core idea of this algorithm:
//
//    1.Each depth of the tree only select one node.
//    2. View depth is current size of result list.
    private static List<Integer> rightSideViewDepth(ListNode root) {
        List<Integer> result = new ArrayList<>();
        rightView(root, result, 0);
        return result;
    }

    private static void rightView(ListNode root, List<Integer> result, int currDepth) {
        if (root == null)
            return;

        if (currDepth == result.size()) {
            result.add(root.getVal());
        }

        rightView(root.right, result, currDepth + 1);  // THIS is important, do it first before left
        rightView(root.left, result, currDepth + 1);
    }

    ArrayList<Integer> result = new ArrayList<>();
    private List<Integer> rightSideView (ListNode root) {
        helper (root, 0);
        return result;
    }

    private void helper(ListNode root, int layer) {
        if (root != null) {
            if (result.size() <= layer) {
                result.add(root.getVal());
            }
        }

        helper(root.right, layer + 1);
        helper(root.left, layer + 1);
    }
}
