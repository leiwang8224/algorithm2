package CTCI.TreesAndGraphs;

import CTCI.TreesAndGraphs.TreeUtils.TreeNode;

/**
 * T1 and T2 are two very large binary trees, with T1 much bigger than T2.
 * create an algorithm to determine if T2 is a subtree of T1.
 * A tree T2 is a subtree of T1 if there exists a node n in T1 such that the
 * subtree of n is identical to T2.
 * That is if you cut off the tree at node n, the two trees would be identical.
 */
public class CheckSubTree {
    public static void main(String[] args) {
        TreeNode root = TreeUtils.generateTree();
        System.out.println(isT2PartOfT1(root, root.right.right));
        System.out.println(isT2PartOfT1Parsing(root, root.right.right));

    }

    private static boolean isT2PartOfT1(TreeNode T1, TreeNode T2) {
        // traverse T1 DFS to find T2
        if (T2 == null) return true;
        return subTree(T1,T2);
    }

    private static boolean subTree(TreeNode t1, TreeNode t2) {
        // big tree empty so small tree will not exist
        if (t1 == null) return false;
        else if (t1.val == t2.val && matchTree(t1,t2)) return true;
        return subTree(t1.left, t2) || subTree(t1.right, t2);
    }

    private static boolean matchTree(TreeNode t1, TreeNode t2) {
        //nothing left in the tree
        if (t1 == null && t2 == null) return true;
        // excatly one tree is empty, therefore trees don't match
        else if (t1 == null || t2 == null) return false;
        //data don't match;
        else if (t1.val != t2.val) return false;
        // traverse two trees simultaneously
        else return matchTree(t1.left, t2.left) && matchTree(t1.right,t2.right);
    }

    private static boolean isT2PartOfT1Parsing(TreeNode t1, TreeNode t2) {
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();

        getOrderString(t1, str1);
        getOrderString(t2, str2);

        // the key is to find the substring str2 inside of str1
        return str1.indexOf(str2.toString()) != -1;
    }

    private static void getOrderString(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("X");              // null indicated by 'X'
            return;
        }

        sb.append(node.val);             // add root
        getOrderString(node.left, sb);   // add left
        getOrderString(node.right, sb);  // add right
    }


}
