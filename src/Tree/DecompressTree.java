package Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// Given a String that represents a Binary Tree, write a method - decompressTree that
// decompresses that tree (reconstructs the tree) and returns the root TreeNode. The
// compression algorithm included traversing the tree level by level, from the left
// to the right. The TreeNode's data values were appended to the String, delimited by
// commas. Also, null TreeNodes were denoted by appending an asterisk - *.
//
// The input String denotes the structure of a Full Binary Tree - i.e. a tree that is
// structurally balanced. However, the reconstructed tree may not be a full tree as
// the String included * characters, which represent null TreeNodes.
//
//        Note:
//        You can assume that if a Binary Tree contains k levels,
//        the compressed String will contain 2k-1 elements - either numbers or *.
public class DecompressTree {
    public static void main(String[] args) {
         BST.printBST(decompressTree2("1,2,3,4,*,6,*"));
    }

    private static ListNode decompressTree(String str) {
        if (str == null || str.equals("") || str.equals("*")) {
            return null;
        }
        ArrayList<Integer> nodeVals = new ArrayList<>();
        String[] splits = str.split(",");
        for (String s : splits) {
            if (s.equals("*")) {
                nodeVals.add(null);
            } else {
                nodeVals.add(Integer.valueOf(s));
            }
        }

        Queue<ListNode> q = new LinkedList<>();
        int index = 0;

        ListNode root = new ListNode(nodeVals.get(0));
        q.add(root);

        while (index < nodeVals.size()) {
            ListNode node = q.poll();

            if (node != null) {
                // not equal to null so create nodes
                // first extract the values for the nodes with the correct index
                // left +1, right +2
                Integer leftVal = null;
                Integer rightVal = null;
                if (index + 1 < nodeVals.size()) {
                    leftVal = nodeVals.get(index + 1);
                } // else leftVal is null

                if (index + 2 < nodeVals.size()) {
                    rightVal = nodeVals.get(index + 2);
                } // else rightVal is null

                ListNode leftChild = null;
                ListNode rightChild = null;

                // create the nodes with left and right values
                if (leftVal == null) {
                    leftChild = null;
                } else {
                    leftChild = new ListNode(leftVal);
                }

                if (rightVal == null) {
                    rightChild = null;
                } else {
                    rightChild = new ListNode(rightVal);
                }

                node.left = leftChild;
                node.right = rightChild;
                q.add(leftChild);
                q.add(rightChild);
                index += 2;
            } else {
                // just increment the index
                index += 2;
//                continue;
            }
        }
        return root;
    }

    private static ListNode decompressTree2(String str) {
        String[] split = str.split(",");

        ArrayList<Integer> list = new ArrayList<>();

        for (String str1 : split) {
            list.add(str1.equals("*") ? null : Integer.valueOf(str1));
        }

        int size = list.size();

        Queue<ListNode> q = new LinkedList<>();
        q.offer(list.get(0) == null ? null : new ListNode(list.get(0)));

        ListNode head = q.peek();

        int index = 0;
        while (index < size) {
            ListNode node = q.poll();

            if (index + 1 < size) {
                if (list.get(index+1) == null) {
                    node.left = null;
                } else {
                    node.left = new ListNode(list.get(index + 1));
                }
            }

            if (index + 2 < size) {
                if (list.get(index+2) == null) {
                    node.right = null;
                } else {
                    node.right = new ListNode(list.get(index + 2));
                }
            }

            q.offer(node.left);
            q.offer(node.right);
            index = index+2;
        }

        return head;
    }
}
