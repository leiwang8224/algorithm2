package CTCI.TreesAndGraphs;

import java.util.HashMap;

import CTCI.TreesAndGraphs.TreeUtils.TreeNode;

//TODO understand this.
/**
 * Given a binary tree in which each node contains an integer value. Design
 * an algorithm to count the number of paths that sum to a given value.
 * The path does not need to start or end at the root or a leaf, but
 * it must go downwards.
 */
public class PathsWithSum {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(3);
        root.right = new TreeNode(2);
        root.right.right = new TreeNode(3);
        root.right.left = new TreeNode(3);

        System.out.println(getNumberPathsTree(root,6));
        System.out.println(countPathsWithSumHashMap(root, 6));
        System.out.println(getNumberPathsTree2(root, 6));

    }

    /**
     * Brute force method in trying all different paths
     * DFS traversal to touch each node
     * then use DFS again to try different paths
     * @param root
     * @param targetSum
     * @return
     */
    private static int getNumberPathsTree(TreeNode root, int targetSum) {
        if (root == null) return 0;

        // count paths with sum starting from the root using separate method
        // note that the root could be the root.left, root.right, root.right.right...
        // recusion on recursion

        // take care of the root node by calling another recursive method
        int pathsFromRoot = countPathsWithSumFromRoot(root, targetSum, 0);

        // try the nodes on the left and right
        // note this will also call countPathsWithSumFromRoot(), but on left and right tree

        // DFS traversal by calling self
        int pathsOnLeft = getNumberPathsTree(root.left, targetSum);
        int pathsOnRight = getNumberPathsTree(root.right, targetSum);

        return pathsFromRoot + pathsOnLeft + pathsOnRight;
    }

    /**
     * Counts the number of paths from root
     * Use DFS to try different paths
     * @param root
     * @param targetSum
     * @param currSum
     * @return number of paths from the root
     */
    private static int countPathsWithSumFromRoot(TreeNode root, int targetSum, int currSum) {
        if (root == null) return 0;

        // take care of the root node
        currSum = currSum + root.val;

        int totalPaths = 0;
        if (currSum == targetSum) totalPaths++;

        // DFS traversal
        totalPaths = totalPaths + countPathsWithSumFromRoot(root.left, targetSum, currSum);
        totalPaths = totalPaths + countPathsWithSumFromRoot(root.right, targetSum, currSum);

        return totalPaths;
    }

    /**
     * Keeping a dictionary lookup of the paths that have traversed before so
     * we don't have to traverse again.
     * @param root
     * @param targetSum
     * @return
     */
    private static int countPathsWithSumHashMap(TreeNode root, int targetSum) {
        // hashmap used to keep track of sum and number of paths
        return countPathsWithSum(root,
                                 targetSum,
                                 0,
                                 new HashMap<Integer, Integer>());
    }

    private static int countPathsWithSum(TreeNode root,
                                         int targetSum,
                                         int runningSum,
                                         HashMap<Integer, Integer> pathCount) {
        // base condition for recursion
        if (root == null) return 0;

        // add the current node
        runningSum = runningSum + root.val;

        // count paths with sum ending at the current node
        // try to look up the remainder sum (if we have already
        // traversed this part of the tree we don't need to do it
        // again)
        int sum = runningSum - targetSum;
        int totalPaths = pathCount.getOrDefault(sum, 0);

        // if runningSum equals targetSum, then one additonal path starts at root.
        // add in this path
        if (runningSum == targetSum) totalPaths++;

        // add runningSum to pathCounts
        addEntryToHashTable(pathCount, runningSum, 1);

        // count paths with sum on the left and right
        totalPaths = totalPaths + countPathsWithSum(root.left, targetSum, runningSum, pathCount);
        totalPaths = totalPaths + countPathsWithSum(root.right, targetSum, runningSum, pathCount);

        addEntryToHashTable(pathCount, runningSum, -1);  // remove runningSum
        return totalPaths;
    }

    private static void addEntryToHashTable(HashMap<Integer, Integer> pathCount, int key, int delta) {
        int newCount = pathCount.getOrDefault(key, 0)+delta;
        if(newCount == 0) pathCount.remove(key);
        else pathCount.put(key,newCount);
    }

    /**
     * Simplified getNumberPathsTree but still the same complexity
     * There are two DFS's nested
     * getNumberPathsTree2 traverses each node using DFS
     * @param root
     * @param sum
     * @return
     */
    private static int getNumberPathsTree2(TreeNode root, int sum) {
        if (root == null) return 0;

        // make new method for the root, otherwise recurse on the left and right
        return pathSumFrom(root, sum) +
               getNumberPathsTree2(root.left, sum) +
               getNumberPathsTree2(root.right, sum);
    }

    /**
     * At each node we recursively try all paths downwards, tracking each
     * sum as we go. Once again using DFS,
     * @param root
     * @param sum
     * @return
     */
    private static int pathSumFrom(TreeNode root, int sum) {
        if (root == null) return 0;
        return (root.val == sum ? 1 : 0)    // can I get to sum with just the current node?
                + pathSumFrom(root.left, sum - root.val)   // how many nodes on left tree?
                + pathSumFrom(root.right, sum -root.val);  // how many nodes on right tree?
    }
}
