package AlgoExpert.Hard;

import Tree.ListNode;

import java.util.*;

public class MaxPathSum {
    //O(n) time | O(log(n)) space because we are going to have at
    //most log(n) space on the call stack
    //Need to keep track of both the triangle max sum and the branch max sum
    //and branch max sum with/without root
    private static int maxPathSum (ListNode tree) {
        // maxSumList contains max sum as a branch and max running path sum
        List<Integer> maxSumList = findMaxSum(tree);
        return maxSumList.get(1);
    }

    /**
     * Need to calculate both the branch max sum and the triangle max sum
     *      O               O
     *    /  \               \
     *   O    O               O
     *                         \
     *                          O
     * Need to store both the max sum as a branch and max sum as a triangle.
     * To store both values use an arraylist.
     *
     * max of 4 values, for example:
     *   1
     *  / \
     * 2   3
     * option 1: 1
     * option 2: 1
     *          /
     *         2
     * option 3: 1
     *            \
     *             3
     * option 4: 1
     *          / \
     *         2   3
     *
     * MPS(T):
     *   LS = MPS(L)
     *   RS = MPS(R)
     *   temp = max(LS + V, V)  // max(LS + V, V, temp + RS)
     *   temp2 = max(temp + RS, temp)
     *
     * MPS(T):
     * LSB: left sum branch
     * RSB: right sum branch
     * LS: left max sum (regardless of branch or triangle), essentially left tree sum
     * RS: right max sum (regardless of branch or triangle), essentially right tree sum
     * MCSB: max child sum as a branch
     * MSB: max current sum as a branch
     * MST: max sum triangle with root node
     * RMPS: running max path sum
     *   - branch calc
     *   LSB, LS = MPS(L)
     *   RSB, RS = MPS(R)
     *   MCSB = max(LSB, RSB)
     *   MSB = max(MCSB + V, V)
     *   - triangle calc
     *   MST = max(MSB, LSB + V + RSB)
     *   RMPS = max(LS, RS, MST)
     *   return (MSB, RMPS)
     */
    private static List<Integer> findMaxSum(ListNode tree) {
        // note cannot return 0 for base case since there is a possibility all nodes
        // in the tree are negative values, in which case we need to return very negative
        // value
        if (tree == null) {
            // returns the max sum in the branch and the running max sum
            // initialize the solution to be MIN_VALUE
            return new ArrayList<>(Arrays.asList(0, Integer.MIN_VALUE));
        }

        List<Integer> leftMaxSumList = findMaxSum(tree.left);
        Integer leftMaxSumAsBranch = leftMaxSumList.get(0);
        Integer cumLeftMaxPathSum = leftMaxSumList.get(1);

        List<Integer> rightMaxSumList = findMaxSum(tree.right);
        Integer rightMaxSumAsBranch = rightMaxSumList.get(0);
        Integer cumRightMaxPathSum = rightMaxSumList.get(1);

        // at the current node, calculate the max child from left and right branch
        Integer maxBranchSum = Math.max(leftMaxSumAsBranch, rightMaxSumAsBranch);
        // now add the current node (parent) value and determine if current node value is greater
        Integer maxSumAsBranch = Math.max(maxBranchSum + tree.getVal(),
                                            tree.getVal());
        // calculate the max sum for triangle and compare to branch
        Integer maxSumWithRootNode = Math.max(leftMaxSumAsBranch + tree.getVal() + rightMaxSumAsBranch,
                                            maxSumAsBranch);
        // finally take the max of left path and right path sum and the triangle max sum
        // max(cumLeft, cumRight, max(triangle+branch))
        int maxPathSum = Math.max(cumLeftMaxPathSum,
                                    Math.max(cumRightMaxPathSum, maxSumWithRootNode));

        return new ArrayList<>(Arrays.asList(maxSumAsBranch, maxPathSum));
    }

    /**
     * Second method
     */
    static int maxValGlobal;  // global var

    public static int maxPathSum2(ListNode root) {
        maxValGlobal = Integer.MIN_VALUE;
        getMaxBranchSum(root); // note that we don't care about the return value
        return maxValGlobal;
    }

    /**
     * Returns the branch max sum but also updates the global max sum variable
     */
    private static int getMaxBranchSum(ListNode node) {
        if (node == null) return 0;
        // goes from bottom of the tree to the top, post-order traversal
        int leftBranchMaxSum = Math.max(0, getMaxBranchSum(node.left));  // compare to 0 to compensate for negative values
        int rightBranchMaxSum = Math.max(0, getMaxBranchSum(node.right));
        // compare global var to the max v tree
        maxValGlobal = Math.max(maxValGlobal,
                            leftBranchMaxSum + rightBranchMaxSum + node.getVal());
        return Math.max(leftBranchMaxSum, rightBranchMaxSum) + node.getVal();
    }

    /**
     * Solution 3
     */
    private static int cumMaxSum;

    public int maxPathSum3(ListNode root) {
        cumMaxSum = Integer.MIN_VALUE;
        maxSumHelper(root);
        return cumMaxSum; // as maxSum will always store the result
    }

    public int maxSumHelper(ListNode root) {

        // base case
        if (root == null) return 0;

        // recursing through left and right subtree
        int leftTreeMax = maxSumHelper(root.left);
        int rightTreeMax = maxSumHelper(root.right);

        // finding all the four paths and the maximum between all of them
        int maxLeftRightTree = Math.max(leftTreeMax, rightTreeMax); // max of left or right tree
        int maxSumAsBranchToReturn = Math.max(root.getVal(), root.getVal() + maxLeftRightTree);
        int maxSumAsBranchAndV = Math.max(maxSumAsBranchToReturn, leftTreeMax + rightTreeMax + root.getVal());

        // Storing the result in the maxSum holder
        cumMaxSum = Math.max(cumMaxSum, maxSumAsBranchAndV);

        // returning the value if root was part of the answer
        return maxSumAsBranchToReturn;

    }


}
