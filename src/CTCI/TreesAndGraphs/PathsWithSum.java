package CTCI.TreesAndGraphs;

import java.util.HashMap;

import CTCI.TreesAndGraphs.TreeUtils.TreeNode;

/**
 * Given a binary tree in which each node contains an integer value. Design
 * an algorithm to count the number of paths that sum to a given value.
 * The path does not need to start or end at the root or a leaf, but
 * it must go downwards.
 */
public class PathsWithSum {
    public static void main(String[] args) {

    }

    /**
     * Brute force method in trying all different paths
     * @param root
     * @param targetSum
     * @return
     */
    private static int getNumberPathsTree(TreeNode root, int targetSum) {
        if (root == null) return 0;

        // count paths with sum starting from the root using separate method
        // note that the root could be the root.left, root.right, root.right.right...
        // recusion on recursion
        int pathsFromRoot = countPathsWithSumFromRoot(root, targetSum, 0);

        // try the nodes on the left and right
        int pathsOnLeft = getNumberPathsTree(root.left, targetSum);
        int pathsOnRight = getNumberPathsTree(root.right, targetSum);

        return pathsFromRoot + pathsOnLeft + pathsOnRight;
    }

    /**
     * Counts the number of paths from root
     * @param root
     * @param targetSum
     * @param currSum
     * @return number of paths from the root
     */
    private static int countPathsWithSumFromRoot(TreeNode root, int targetSum, int currSum) {
        if (root == null) return 0;

        currSum = currSum + root.val;

        int totalPaths = 0;
        if (currSum == targetSum) totalPaths++;

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
        return countPathsWithSum(root, targetSum, 0, new HashMap<Integer, Integer>());
    }

    private static int countPathsWithSum(TreeNode root,
                                         int targetSum,
                                         int runningSum,
                                         HashMap<Integer, Integer> pathCount) {
        if (root == null) return 0;

        runningSum = runningSum + root.val;

        // count paths with sum ending at the current node
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
}
