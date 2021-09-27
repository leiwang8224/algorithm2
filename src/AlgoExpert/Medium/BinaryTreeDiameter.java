package AlgoExpert.Medium;

import Tree.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class BinaryTreeDiameter {
    public static void main(String[] args) {

    }

    private static int binaryTreeDiameter(ListNode root) {
        return getTreeInfo(root).diameter;
    }

    // dfs traversal to travel to the end of the nodes and keep track of both
    // diameter and height
    // diameter = max(longestPathThruRoot, maxDiameterSoFar)
    // note that longestPathThruRoot may not be the longest path through the tree,
    // hence we take the max between longestPathThruRoot and maxDiameterSoFar
    // O(n) time | O(n) space
    private static TreeInfo getTreeInfo(ListNode root) {
        if (root == null) {
            // null node so diameter and height = 0
            return new TreeInfo(0, 0);
        }

        // note the traversal order, left, right then root (post-order traversal)
        // ensures the availability of the node until left and right subtrees are traversed
        TreeInfo leftTreeInfo = getTreeInfo(root.left);
        TreeInfo rightTreeInfo = getTreeInfo(root.right);

        // longestPath = left tree height + right tree height
        int totalHeight = leftTreeInfo.height + rightTreeInfo.height;
        // max diameter = max(left tree diameter, right tree diameter)
        int maxDiameterSoFar = Math.max(leftTreeInfo.diameter, rightTreeInfo.diameter);
        // current diameter = max(longestPath, max diameter)
        // longestPaththruRoot may not represent the longest path
        // current diameter also = max(leftTreeHeight + rightTreeHeight, leftTreeDiameter, rightTreeDiameter)
        int curDiameter = Math.max(totalHeight, maxDiameterSoFar);
        // current height = 1 + max(left tree height, right tree height)
        int curHeight = 1 + Math.max(leftTreeInfo.height, rightTreeInfo.height);

        // push the diameter and height into next recursion
        return new TreeInfo(curDiameter, curHeight);
    }

    private static class TreeInfo{
        public int diameter;
        public int height;
        public TreeInfo(int diameter, int height) {
            this.diameter = diameter;
            this.height = height;
        }
    }

    public int diameterOfBinaryTree(ListNode root)
    {
        // node to height mapping
        Map<ListNode, Integer> map = new HashMap<>();
        Deque<ListNode> stack = new ArrayDeque<>();
        int diameter = 0;

        if(root != null)
            stack.push(root);

        while(!stack.isEmpty())
        {
            ListNode node = stack.peek();

            if(node.left != null && !map.containsKey(node.left)) {
                stack.push(node.left);
            } else if(node.right != null && !map.containsKey(node.right)) {
                stack.push(node.right);
            } else {
                stack.pop();
                int leftHeight = map.getOrDefault(node.left, 0);
                int rightHeight = map.getOrDefault(node.right, 0);
                map.put(node, 1 + Math.max(leftHeight, rightHeight));
                diameter = Math.max(diameter, leftHeight + rightHeight);
            }
        }
        return diameter;
    }

    /**
     * My solution
     */
    public int binaryTreeDiameterMySol(ListNode tree) {
        // Write your code here.
        return findDiameter(tree).diameter;
    }

    private TreeInfo findDiameter(ListNode root) {
        if (root == null) return new TreeInfo(0, 0);

        TreeInfo leftTree = findDiameter(root.left);
        TreeInfo rightTree = findDiameter(root.right);

        int longestThroughRoot = leftTree.height + rightTree.height;
        int diameter = Math.max(longestThroughRoot, Math.max(leftTree.diameter, rightTree.diameter));

        return new TreeInfo(Math.max(leftTree.height, rightTree.height) + 1, diameter);
    }
}
