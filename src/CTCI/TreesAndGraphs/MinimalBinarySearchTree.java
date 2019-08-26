package CTCI.TreesAndGraphs;

import apple.laf.JRSUIUtils;

// Given sorted array of unique elements. Build binary search tree
// with minimal height
public class MinimalBinarySearchTree {
    public static void main(String[] args) {
        TreeUtils.printTree(buildBST(new int[] {0,1,2,3,4,5,6,7,8}));
    }

    /**
     * build BST based on ordered array from smallest to largest
     * @param array
     * @return
     */
    private static TreeUtils.TreeNode buildBST(int[] array) {
        //DFS in order traversal
        //Create the root node by using the middle indexed value
        //Add left node and right node from the root by recursively
        //call create BST using start index and end index
        return createBST(array, 0, array.length-1);
    }

    /**
     * Create BST use recursive method
     * @param array
     * @param startIndex
     * @param endIndex
     * @return
     */
    private static TreeUtils.TreeNode createBST(int[] array, int startIndex, int endIndex) {
        // base condition where end Index is greater than start Index
        if (endIndex < startIndex) return null;

        // middle index is the average of the start and end
        int middleIndex = (startIndex + endIndex) / 2;

        // create middle node to recurse on
        TreeUtils.TreeNode middleNode = new TreeUtils.TreeNode(array[middleIndex]);

        // use middle node as the root node and recurse on its children
        // left node end index is going to be the middle index
        // right node end index is going to be the middle index
        middleNode.left = createBST(array, startIndex, middleIndex - 1);
        middleNode.right = createBST(array, middleIndex+1, endIndex);
        return middleNode;
    }
}
