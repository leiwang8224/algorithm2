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
         BST.printBST(decompressTree("1,2,3,4,*,6,*"));
    }

    private static ListNode decompressTree(String str) {
        if (str == null || str.equals("") || str.equals("*")) {
            return null;
        }
        ArrayList<Integer> nodeVals = new ArrayList<>();
        String[] splits = str.split(",");

        Integer num = null;
        for (String s : splits) {
            if (!s.equals("*")) {
                num = Integer.valueOf(s);
            } else {
                num = null;
            }
            nodeVals.add(num);
        }

        Queue<ListNode> q = new LinkedList<>();
        int index = 0;
        int size = nodeVals.size();

        ListNode root = new ListNode(nodeVals.get(0));
        q.add(root);

        while (index < nodeVals.size()) {
            ListNode node = q.poll();

            if (node == null) {
                // just increment the index
                index += 2;
                continue;
            } else {
                // not equal to null so create nodes
                Integer leftVal = null;
                Integer rightVal = null;
                if (index + 1 < size) {
                    leftVal = nodeVals.get(index + 1);
                }

                if (index + 2 < size) {
                    rightVal = nodeVals.get(index + 2);
                }

                ListNode leftChild = null;
                ListNode rightChild = null;

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
            }
        }
        return root;
    }
}
