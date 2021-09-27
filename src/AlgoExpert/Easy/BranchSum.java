package AlgoExpert.Easy;

import Tree.ListNode;

import java.util.ArrayList;
import java.util.*;

public class BranchSum {
    public static void main(String[] args) {

    }

    // O(n) time | O(n) space where n is the # of nodes in the tree

    /**
     * 1. init list of sums
     * 2. call recursively using sum and list of sums
     * 3. recursive function
     *    - return when root is null
     *    - calculate new sum
     *    - check if we are at the leaf, if so add to sum list and return
     *    - call recursively with new sum for left and right branches
     */
    private static java.util.List<Integer> branchSums(ListNode root) {
        java.util.List<Integer> sums = new ArrayList<>();
        calculateSums(root, 0, sums);
        return sums;
    }

    private static void calculateSums(ListNode root, int sum, java.util.List<Integer> sums) {
        if (root == null) return;

        // use newRunningSum to finalize the last sum, no need to add anymore
        int newRunningSum = sum + root.getVal();
        if (root.left == null && root.right == null) {
            sums.add(newRunningSum);
            return;
        }

        calculateSums(root.left, newRunningSum, sums);
        calculateSums(root.right, newRunningSum, sums);
    }

    public static List<Integer> branchSumsMySol(ListNode root) {
        // Write your code here.
        List<Integer> result = new ArrayList<>();
        branchSum(root, result, 0);
        return result;
    }

    private static void branchSum(ListNode root, List<Integer> result, int sum) {
        if (root.left == null && root.right == null) {
            result.add(sum+root.getVal());
            return;
        }
        if (root.left != null) {
            branchSum(root.left, result, sum + root.getVal());
        }

        if (root.right != null) {
            branchSum(root.right, result, sum + root.getVal());
        }
    }
}
