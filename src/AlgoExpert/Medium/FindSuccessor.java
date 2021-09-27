package AlgoExpert.Medium;

import java.util.ArrayList;

public class FindSuccessor {
    public static void main(String[] args) {

    }

    static class BinaryTree {
        public int value;
        public BinaryTree left = null;
        public BinaryTree right = null;
        public BinaryTree parent = null;

        public BinaryTree(int value) {
            this.value = value;
        }
    }

    /**
     * perform in order traversal and add nodes to array list
     * iterate through the list to find the successor to the node of interest
     * O(n) time | O(n) space
     * successor is the left most node in the right tree of the node of interest
     */
    private BinaryTree findSuccessor(BinaryTree root, BinaryTree node) {
        ArrayList<BinaryTree> inOrderList = new ArrayList<>();
        getInOrder(root, inOrderList);

        for (int index = 0; index < inOrderList.size(); index++) {
            BinaryTree curNode = inOrderList.get(index);

            if (curNode != node) continue;

            // there is no successor if the node is a leaf to the rightmost of the tree
            if (index == inOrderList.size() - 1) {
                return null;
            }
            // just return the next element when we find the node of interest
            return inOrderList.get(index+1);
        }
        return null;
    }

    private void getInOrder(BinaryTree root, ArrayList<BinaryTree> inOrderList) {
        if (root == null) return;

        getInOrder(root.left, inOrderList);
        inOrderList.add(root);
        getInOrder(root.right, inOrderList);
    }

    /**
    //O(h) time | O(1) space
    // uses the fact that there is parent node reference so we can
    // traverse upwards
     when node.right exists, traverse to the right and find the leftmost node
     else node.right does not exist, go up the tree to find the rightmost parent
     */
    private BinaryTree findSuccessor2(BinaryTree root, BinaryTree node) {
        // when right child is not null, get leftmost child from the right subtree
        if (node.right != null) return getLeftMostChild(node.right);
        // when right child is null, get rightmost child of current node (parent)
        return getRightMostParent(node);
    }

    // when right node is null, go up to parent to find successor
    private BinaryTree getRightMostParent(BinaryTree node) {
        BinaryTree curNode = node;
        // note that if we are at a right node, the parent cannot be a successor(already visited)
        // have to travel up the tree to find successor, go up until we are at the left
        // child of the node (not yet visited)
        while (curNode.parent != null && curNode.parent.right == curNode) {
            curNode = curNode.parent;
        }
        return curNode.parent;
    }

    private BinaryTree getLeftMostChild(BinaryTree node) {
        BinaryTree curNode = node;
        while (curNode.left != null) {
            curNode = curNode.left;
        }
        return curNode;
    }





}
