package AlgoExpert.VeryHard;

import Tree.ListNode;

import java.util.*;

public class CompareLeafNodes {
    // O(n + m) time | O(h1 + h2) space - where n is the number of nodes in the first
    // binary tree, m is the number in the second, h1 is the height of the first binary
    // tree, and h2 is the height of the second
    public boolean compareLeafTraversal(ListNode tree1, ListNode tree2) {
        Stack<ListNode> tree1TraversalStack = new Stack<>();
        tree1TraversalStack.push(tree1);
        Stack<ListNode> tree2TraversalStack = new Stack<>();
        tree2TraversalStack.push(tree2);

        while(tree1TraversalStack.size() > 0 && tree2TraversalStack.size() > 0) {
            ListNode tree1Leaf = getNextLeafNode(tree1TraversalStack);
            ListNode tree2Leaf = getNextLeafNode(tree2TraversalStack);

            if (tree1Leaf.getVal() != tree2Leaf.getVal()) {
                return false;
            }
        }

        // number of leaf nodes the same?
        return (tree1TraversalStack.size() == 0) && (tree2TraversalStack.size() == 0);
    }

    private ListNode getNextLeafNode(Stack<ListNode> traversalStack) {
        ListNode curNode = traversalStack.pop();

        while (!isLeafNode(curNode)) {
            if (curNode.right != null) {
                traversalStack.push(curNode.right);
            }

            // we purposely add the left node to the stack after the
            // right node so that it gets popped off the stack first
            if (curNode.left != null) {
                traversalStack.push(curNode.left);
            }

            curNode = traversalStack.pop();
        }
        return curNode;
    }

    private boolean isLeafNode(ListNode node) {
        return (node.left == null) && (node.right == null);
    }

    // O(n + m) time | O(max(h1 + h2)) space - where n is the number of nodes in the first
    // binary tree, m is the number in the second, h1 is the height of the first binary
    // tree, and h2 is the height of the second
    public boolean compareLeafTraversal2(ListNode tree1, ListNode tree2) {
        ListNode tree1LeafNodesLinkedList = connectLeafNodes(tree1, null, null)[0];
        ListNode tree2LeafNodesLinkedList = connectLeafNodes(tree2, null, null)[0];

        ListNode list1CurNode = tree1LeafNodesLinkedList;
        ListNode list2CurNode = tree2LeafNodesLinkedList;

        // check to see if the leaf linked list values are the same
        while (list1CurNode != null && list2CurNode != null) {
            if (list1CurNode.getVal() != list2CurNode.getVal()) return false;

            list1CurNode = list1CurNode.right;
            list2CurNode = list2CurNode.right;
        }

        return list1CurNode == null && list2CurNode == null;
    }

    // connect the leaf nodes into linkedlist
    private ListNode[] connectLeafNodes(ListNode curNode, ListNode headLinkedList, ListNode prevNode) {
        if (curNode== null) return new ListNode[] {headLinkedList, prevNode};

        if (isLeafNode(curNode)) {
            if (prevNode == null) { // prevNode is null so no head yet, set the curNode to be head
                headLinkedList = curNode;
            } else { // prevNode is not null so connect the prevNode to the curNode(prevNode->curNode)
                prevNode.right = curNode;
            }
            prevNode = curNode; // move forward the prevNode
        }

        // recurse on the left tree
        ListNode[] nodes = connectLeafNodes(curNode.left, headLinkedList, prevNode);
        ListNode leftHead = nodes[0]; // start of the linkedlist
        ListNode leftPrevNode = nodes[1]; // end of the linkedlist

        // recurse on the right tree
        return connectLeafNodes(curNode.right, leftHead, leftPrevNode);
    }
}
