package AlgoExpert.VeryHard;

import Tree.ListNode;

import java.util.*;
// There's an additional, cleaner and more clever way of solving this question
// with the same time and space time complexities as the optimal solution covered in the video explanation.
//
//         Realize that a given node in the input tree has:
//
//         a depth of 1 with respect to its parent node
//         a depth of 2 with respect to its parent's parent node
//         a depth of 3 with respect to its parent's parent's node
//         ...
//         a depth of d with respect to the root node
//
//         Since these depths are captured in each subtree's nodes' depths,
//         which we sum up to get the final answer, we can deduce that each
//         node in the input tree contributes 1 + 2 + 3 + ... + d - 1 + d to the final answer.
//
//        Thus, we can solve this question with a simple recursive function that
//        takes in the running depthSum and adds the current node's depth to it at
//        every call. See Solution 5 for the implementation of this algorithm.
//
//        We can go one step further by realizing that the sum 1 + 2 + 3 + ... + n - 1 + n
//        can be calculated with the formula (n * (n + 1)) / 2, which eliminates the need
//        to pass the running depthSum to each recursive function call. See Solution 6
//        for this implementation.
//
//        Note that these two extra solutions are very clever and wouldn't be
//        expected of you in an interview (especially Solution 6, which takes
//        advantage of a math formula). That being said, if you were able to come
//        up with either of these two solutions, that certainly wouldn't be bad!
public class AllKindsBinaryTreeDepth {
    // Average case: when the tree is balanced
    // O(nlog(n)) time | O(h) space - where n is the number of nodes in
    // the tree and h is the height of the tree
    int nodeDepths(ListNode root) {
        int sumOfAllDepths = 0;
        // for each node run findNodeDepths to find node depths for each node as root node
        // add to sumOfAllDepths
        List<ListNode> stack = new ArrayList<>();
        stack.add(root);
        while (stack.size() > 0) {
            ListNode node = stack.remove(stack.size()-1);
            if (node == null) continue;
            sumOfAllDepths += findNodeDepths(node, 0);
            stack.add(node.left);
            stack.add(node.right);
        }
        return sumOfAllDepths;
    }

    int findNodeDepths(ListNode node, int depth) {
        if (node == null) return 0;
        return depth + findNodeDepths(node.left, depth + 1) +
                        findNodeDepths(node.right, depth + 1);
    }

    // Average case: when the tree is balanced
    // O(nlog(n)) time | O(h) space - where n is the number of nodes in
    // the tree and h is height of the tree
    int nodeDepthsRecurse(ListNode root) {
        if (root == null) return 0;
        // traverse each node and find node depths for each node as root node
        return nodeDepthsRecurse(root.left) +
                nodeDepthsRecurse(root.right) +
                findNodeDepthRecurse(root, 0);

    }

    // O(n) time | O(n) space where n is the number of nodes in tree
    int findNodeDepthRecurse(ListNode node, int depth) {
        if (node == null) return 0;
        return depth + findNodeDepthRecurse(node.left, depth + 1)  +
                findNodeDepthRecurse(node.right, depth + 1);
    }

    // O(n) time | O(n) space
    int nodeDepthsMap(ListNode root) {
        // for each node cache the node counts and node depths given the node is the root node of subtree
        Map<ListNode, Integer> nodeCountsMap = new HashMap<>();
        Map<ListNode, Integer> nodeDepthsMap = new HashMap<>();
        addNodeCountsForCurNodeToMap(root, nodeCountsMap);
        addNodeDepthsForCurNodeToMap(root, nodeDepthsMap, nodeCountsMap);
        return sumAllNodeDepths(root, nodeDepthsMap);
    }

    private void addNodeDepthsForCurNodeToMap(ListNode root,
                                              Map<ListNode, Integer> nodeDepthsMap,
                                              Map<ListNode, Integer> nodeCountsMap) {
        nodeDepthsMap.put(root, 0); // root node has depth of 0 at root
        if (root.left != null) {
            addNodeDepthsForCurNodeToMap(root.left, nodeDepthsMap, nodeCountsMap);
            nodeDepthsMap.put(root, nodeDepthsMap.get(root) + nodeDepthsMap.get(root.left) +
                    nodeCountsMap.get(root.left));
        }

        if (root.right != null) {
            addNodeDepthsForCurNodeToMap(root.right, nodeDepthsMap, nodeCountsMap);
            nodeDepthsMap.put(root, nodeDepthsMap.get(root) + nodeDepthsMap.get(root.right) +
                    nodeCountsMap.get(root.right));
        }
    }

    private void addNodeCountsForCurNodeToMap(ListNode root, Map<ListNode, Integer> nodeCounts) {
        nodeCounts.put(root, 1); // root node has count of 1 since root itself is a node
        if (root.left != null) {
            addNodeCountsForCurNodeToMap(root.left, nodeCounts);
            nodeCounts.put(root, nodeCounts.get(root) + nodeCounts.get(root.left));
        }
        if (root.right != null) {
            addNodeCountsForCurNodeToMap(root.right, nodeCounts);
            nodeCounts.put(root, nodeCounts.get(root) + nodeCounts.get(root.right));
        }
    }

    private int sumAllNodeDepths(ListNode root, Map<ListNode, Integer> nodeDepthsMap) {
        if (root == null) return 0;
        return sumAllNodeDepths(root.left, nodeDepthsMap) +
                sumAllNodeDepths((root.right, nodeDepthsMap) + nodeDepthsMap.get(root);
    }

    // O(n) time | O(h) space
    int nodeDepthsUsingNumNodes(ListNode root) {
        return getTreeInfo(root).sumOfAllDepths;
    }

    private TreeInfo getTreeInfo(ListNode root) {
        if (root == null) return new TreeInfo(0,0,0);

        TreeInfo leftTreeInfo = getTreeInfo(root.left);
        TreeInfo rightTreeInfo = getTreeInfo(root.right);

        int sumOfLeftDepths = leftTreeInfo.sumOfDepths + leftTreeInfo.numNodesInTree;
        int sumOfRightDepths = rightTreeInfo.sumOfDepths + rightTreeInfo.numNodesInTree;

        int numNodesInTree = 1 + leftTreeInfo.numNodesInTree + rightTreeInfo.numNodesInTree;
        int sumOfDepths = sumOfLeftDepths + sumOfRightDepths;
        int sumOfAllDepths = sumOfDepths + leftTreeInfo.sumOfAllDepths + rightTreeInfo.sumOfAllDepths;

        return new TreeInfo(numNodesInTree, sumOfDepths, sumOfAllDepths);
    }

    // used to store the num of nodes, sum of depths and sum of all depths
    class TreeInfo {
        int numNodesInTree;
        int sumOfDepths;
        int sumOfAllDepths;

        TreeInfo(int numNodesInTree, int sumOfDepths, int sumOfAllDepths) {
            this.numNodesInTree = numNodesInTree;
            this.sumOfAllDepths = sumOfAllDepths;
            this.sumOfDepths = sumOfDepths;
        }
    }

    //O(n) time O(h) space
    int nodeDepthsRecurseWithNumNodes(ListNode root) {
        return nodeDepthHelper(root, 0, 0);
    }

    int nodeDepthHelper(ListNode root, int depthSum, int depth) {
        if (root == null) return 0;

        depthSum += depth;
        return depthSum + nodeDepthHelper(root.left, depthSum, depth + 1)
                + nodeDepthHelper(root.right, depthSum, depth + 1);
    }

    //O(n) time | O(h) space
    int nodeDepthsMath(ListNode root) {
        return nodeDepthsMathHelper(root, 0);
    }

    private int nodeDepthsMathHelper(ListNode root, int depth) {
        if (root == null) return 0;

        // formula to calculate 1 + 2 + 3 + ... + depth - 1 + depth
        int depthSum = (depth * (depth + 1)) / 2;
        return depthSum + nodeDepthsMathHelper(root.left, depth + 1)
                + nodeDepthsMathHelper(root.right, depth + 1);
    }


}
