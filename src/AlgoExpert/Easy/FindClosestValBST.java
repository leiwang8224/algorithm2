package AlgoExpert.Easy;

import Tree.ListNode;

import javax.swing.tree.TreeNode;

public class FindClosestValBST {
    public static void main(String[] args) {

    }

    // Average: O(log(n)) time | O(log(n)) space
    // Worst: O(n) time | O(n) space
    /**
     * 1. setup the target and initialize the root as the closest to the target
     * 2. call recursive function
     * 3. recursive function
     *    - update closest var
     *    - if target < root value and root.left is not null, recurse to the left with new closest
     *    - if target > root value and root.right is not null, recurse to the right with new closest
     *    - else just return the closest since the target is equal to root.value
     */
    private static int findClosest(ListNode tree, int target) {
        return findClosestVal(tree, target, tree.getVal());

    }

    private static int findClosestVal(ListNode tree, int target, int closest) {
        if (Math.abs(target - closest) > Math.abs(target-tree.getVal()))
            closest = tree.getVal();

        if (target < tree.getVal() && tree.left != null)
            return findClosestVal(tree.left, target, closest);
        else if (target > tree.getVal() && tree.right != null)
            return findClosestVal(tree.right, target, closest);
        else
            return closest;
    }

    // Average: O(log(n)) time | O(1) time
    // Worst: O(n) time | O(1) space
    private static int findClosestIterative(ListNode tree, int target) {
        return findClosestVal2(tree, target, tree.getVal());
    }

    private static int findClosestVal2(ListNode tree, int target, int closest) {
        ListNode curNode = tree;
        while (curNode != null) {
            if (Math.abs(target - closest) > Math.abs(target - curNode.getVal()))
                closest = curNode.getVal();
            if (target < curNode.getVal())
                curNode = curNode.left;
            else if (target > curNode.getVal())
                curNode = curNode.right;
            else break;
        }
        return closest;
    }

    private static int findClosestValAnother(ListNode tree, int target, int closest) {
        if (tree == null) return closest;
        if (Math.abs(target - closest) > Math.abs(target - tree.getVal()))
            closest = tree.getVal();
        if (target < tree.getVal())
            return findClosestValAnother(tree.left, target, closest);
        else if (target > tree.getVal())
            return findClosestValAnother(tree.right, target, closest);
        else return closest;
    }
}
