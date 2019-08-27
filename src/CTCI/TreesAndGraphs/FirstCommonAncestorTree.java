package CTCI.TreesAndGraphs;

import CTCI.TreesAndGraphs.TreeUtils.TreeNode;

// design an algorithm and write code to find the first common
// ancestor (lowest common ancestor) of two nodes in a binary tree.
// Avoid storing additional nodes in a data structure.
// note this may not necessarily be a BST
public class FirstCommonAncestorTree {
    public static void main(String[] args) {
        TreeNode root = TreeUtils.generateTree();
        System.out.println(commonAncestor(root, root.right.right, root.left.left).val);
        System.out.println(commonAncestor2(root, root.right.right, root.left.left).val);
        System.out.println(commonAncestor3(root, root.right.right, root.left.left).val);
        System.out.println(commonAncestor3Bad(root, root.right.right, root.left.left).val);
//        System.out.println(commonAncestorWithParentDoesNotWork(root, root.right.right, root.left.left).val);
    }

    private static TreeNode commonAncestor2(TreeNode root, TreeNode node1, TreeNode node2) {
        // if node1 or node2 does not exist in the tree return null
        if (!ifNodeExist(root, node1) || !ifNodeExist(root, node2)) {
            // error check - one node is not in tree
            return null;
        }

        return findAncestorHelper(root, node1, node2);
    }

    private static TreeNode findAncestorHelper(TreeNode root, TreeNode node1, TreeNode node2) {
        // base condition returns null if at end, or return node if found
        if (root == null || root == node1 || root == node2) return root;

        boolean node1IsOnLeft = ifNodeExist(root.left, node1);
        boolean node2IsOnLeft = ifNodeExist(root.left, node2);

        // nodes are on different side therefore root is the ancestor
        if (node1IsOnLeft != node2IsOnLeft) return root;

        // if node1 is in the left tree, recurse on the left tree
        TreeNode childSide = node1IsOnLeft ? root.left : root.right;
        return findAncestorHelper(childSide, node1, node2);
    }

    private static boolean ifNodeExist(TreeNode root, TreeNode node) {
        if (root == null) return false;
        if (root == node) return true;
        // find node on the left tree and right tree
        return ifNodeExist(root.left, node) || ifNodeExist(root.right, node);
    }

    /**
     * More complex method of find common ancestor
     */
    static int TWO_NODES_FOUND = 2;
    static int ONE_NODE_FOUND = 1;
    static int NO_NODES_FOUND = 0;

    // checks for how many 'special' nodes are located under this root
    private static int covers(TreeNode root,
                              TreeNode node1,
                              TreeNode node2) {
        int ret = NO_NODES_FOUND;
        if (root == null) {
            return ret;
        }

        // if either node1 or node2 is found, increment counter
        if (root == node1 || root == node2) {
            ret = ret + 1;
        }

        // recurse on the left tree
        ret = ret + covers(root.left, node1, node2);

        // if two nodes are found on the left tree, return the counter
        if (ret == TWO_NODES_FOUND) {
            return ret; //found node1 and node2 in the left tree
        }

        // recurse on the right tree and add to the left tree totals
        return ret + covers(root.right, node1, node2);
    }

    /**
     * TODO quite complex way of finding ancestor
     * @param root
     * @param node1
     * @param node2
     * @return
     */
    private static TreeNode commonAncestor(TreeNode root,
                                           TreeNode node1,
                                           TreeNode node2) {
        if (node1 == node2 && (root.left == node2 || root.right == node2)) return root;
        // number of nodes on the left
        int nodesFromLeft = covers(root.left, node1, node2);   // check left side
        if (nodesFromLeft == TWO_NODES_FOUND) {
            if (root.left == node1 || root.left == node2) return root.left;
            else return commonAncestor(root.left,node1,node2);
        } else if (nodesFromLeft == ONE_NODE_FOUND) {
            if (root == node1) return node1;
            else if (root == node2) return node2;
        }

        int nodesFromRight = covers(root.right, node1, node2);  // check right side
        if (nodesFromRight == TWO_NODES_FOUND) {
            if (root.right == node1 || root.right == node2) return root.right;
            else return commonAncestor(root.right, node1, node2);
        } else if (nodesFromRight == ONE_NODE_FOUND) {
            if (root == node1) return node1;
            else if (root == node2) return node2;
        }

        if (nodesFromLeft == ONE_NODE_FOUND &&
            nodesFromRight == ONE_NODE_FOUND) return root;
        else return null;
    }

    /**
     * class to encapsulate the node information and whether it
     * is an ancestor
     */
    static class Result {
        TreeNode node;
        boolean isAncestor;
        Result(TreeNode node, boolean isAncestor) {
            this.node = node;
            this.isAncestor = isAncestor;
        }
    }

    private static Result commonAncestorHelper(TreeNode root, TreeNode node1, TreeNode node2) {
        if (root == null) {
            return new Result(null, false);
        }

        // in case if two nodes are the same node then the ancestor is itself
        if (root == node1 && root == node2) {
            return new Result(root, true);
        }

        // recurse on the left tree(put the calls on stack)
        Result resultLeft = commonAncestorHelper(root.left, node1, node2);

        if (resultLeft.isAncestor) {
            return resultLeft; // found common ancestor
        }

        // recurse on the right tree(put the calls on stack)
        Result resultRight = commonAncestorHelper(root.right, node1, node2);

        if (resultRight.isAncestor) {
            return resultRight; // found common ancestor
        }

        // if node is found on the left and right tree
        if (resultRight.node != null && resultLeft.node != null) {
            // root is common ancestor
            return new Result(root, true); // this is common ancestor
        } else if (root == node1 || root == node2) {
            // if we are currently at node1 or node2, and we also found one of those
            // nodes in a subtree, then this is truly an ancestor and the flag
            // should be true.
            boolean isAncestor = resultRight.node != null || resultLeft.node != null;
            return new Result(root, isAncestor);
        } else {
            // else return the left or right node (non ancestor), whichever is not null
            return new Result(resultRight.node != null ?
                                      resultRight.node : resultLeft.node,
                                false);
        }
    }

    static TreeNode commonAncestor3(TreeNode root, TreeNode node1, TreeNode node2) {
        Result result = commonAncestorHelper(root, node1, node2);
        if (result.isAncestor) return result.node;
        return null;
    }

    static TreeNode commonAncestor3Bad(TreeNode root, TreeNode node1, TreeNode node2) {
        if (root == null) return null;

        if (root == node1 && root == node2) return root;

        // recurse on left
        TreeNode leftNode = commonAncestor3Bad(root.left, node1, node2);
        // if node not found, keep on recursing
        if (leftNode != null && leftNode != node1 && leftNode != node2) return leftNode;

        // recurse on right
        TreeNode rightNode = commonAncestor3Bad(root.right, node1, node2);
        // if node not found, keep on recursing
        if (rightNode != null && rightNode != node1 && rightNode != node2) return rightNode;

        if (leftNode != null && rightNode != null) return root; // this is common ancestor
        else if (root == node1 || root == node2) return root;
        else return leftNode == null ? rightNode : leftNode;
    }

    static TreeNode commonAncestorWithParentDoesNotWork(TreeNode root, TreeNode node1, TreeNode node2) {
        if (node1 == null || node2 == null) return null;

        TreeNode node1Parent = node1.parent;
        while (node1Parent != null) {
            TreeNode node2Parent = node2.parent;
            while (node2Parent != null) {
                if (node1Parent == node2Parent) return node2Parent;
                node2Parent = node2Parent.parent;
            }
            node1Parent = node1Parent.parent;
        }
        return null;
    }



}
