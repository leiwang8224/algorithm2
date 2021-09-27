package AlgoExpert.VeryHard;

import javax.swing.text.Position;
import java.util.function.Function;

// O(n) time | O(1) space Iterative algorithm gets rid of the stack (recursion)
public class IterativeInOrderTraversal {
    // for each node in the in order traversal make a call on the callback for that node
    public static void iterativeInOrderTraversal (BinaryTree root,
                                                  Function<BinaryTree, Void> callback) {
        // have a ptr to the prev Node to indicate we are coming
        // from the top or from the bottom of the tree
        BinaryTree prevNode = null;
        BinaryTree curNode = root;
        // 3 scenarios to update the nextNode ptr then push the prevNode and curNode forward:
        // 1: coming down from top of the tree (prevNode = parentNode || prevNode = null)
        // 2: coming up from the left tree (prevNode = curNode.left)
        // 3: coming up from the right tree (prevNode = curNode.right)
        while (curNode != null) { // algorithm ends when curNode is null
            BinaryTree nextNode;
            // coming from the top, explore left
            if (prevNode == null || prevNode == curNode.parent) {
                if (curNode.left != null) {
                    nextNode = curNode.left;
                } else { // we are at leftmost part of the tree
                    callback.apply(curNode);
                    // go back up if right node is null, or traverse to the right node
                    nextNode = curNode.right != null ? curNode.right : curNode.parent;
                }
            } else if (prevNode == curNode.left) { // coming back up from the left
                callback.apply(curNode);
                nextNode = curNode.right != null ? curNode.right : curNode.parent; // explored all of the leftmost tree, go right or back up
            } else { // coming back up from the right
                nextNode = curNode.parent;
            }
            prevNode = curNode; // move prevNode forward
            curNode = nextNode; // move curNode forward
        }
    }

    static class BinaryTree {
        public int value;
        public BinaryTree left;
        public BinaryTree right;
        public BinaryTree parent;

        public BinaryTree(int value) {
            this.value = value;
        }

        public BinaryTree(int value, BinaryTree parent) {
            this.value = value;
            this.parent = parent;
        }
    }
}
