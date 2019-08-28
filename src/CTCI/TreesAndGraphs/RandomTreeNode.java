package CTCI.TreesAndGraphs;

import java.util.Random;

/**
 * Design a binary search tree class from scratch, which in addition to insert,
 * find, and delete, has a method getRandomNode() which returns a random node
 * from the tree. All nodes should be equally likely to be chosen. Design and
 * implement algorithm for getRandomNode and explain how you would implement
 * the rest of the methods.
 */
public class RandomTreeNode {
    public static void main(String[] args) {
        RandomNode root = new RandomNode(10);
        root.insertInOrder(1);
        root.insertInOrder(2);
        root.insertInOrder(3);
        root.insertInOrder(4);
        root.insertInOrder(6);
        root.insertInOrder(7);

        System.out.println(root.getRandomNode().val);
        System.out.println(root.getRandomNode().val);
        System.out.println(root.getRandomNode().val);
        System.out.println(root.getRandomNode().val);

    }

    static class RandomNode{
        int val;
        RandomNode left;
        RandomNode right;
        // additional var for size so we can use random number
        // generator and spread the probabilities evenly among the numbers
        int size = 0;
        RandomNode(int val) {
            this.val = val;
            size = 1;
        }

        void insertInOrder(int data) {
            if (data <= val) {
                if (left == null) left = new RandomNode(data);
                else left.insertInOrder(data);
            } else {
                if (right == null) right = new RandomNode(data);
                else right.insertInOrder(data);
            }
            size++;
        }

        int size() {
            return size;
        }

        RandomNode find(int data) {
            if (val == data) return this;
            // recursive call to find for left and right
            else if (data <= val) return left != null ? left.find(data) : null;
            else if (data > val) return right != null ? right.find(data) :null;
            return null;
        }

        RandomNode getRandomNode() {
            int leftSize = left == null ? 0 : left.size;
            Random random = new Random();
            int index = random.nextInt(size);
            // if random index is less than size of left tree
            // recurse on calling random node on left tree
            if (index < leftSize) return left.getRandomNode();

            // if index == leftSize then we return the node
            else if (index == leftSize) return this;
            // else recurse on the right tree for random node
            else return right.getRandomNode();
        }

        RandomNode getIthNode(int i) {
            int leftSize = left == null ? 0 : left.size;
            // if i less than leftSize then it's in left Tree
            if (i < leftSize) return left.getIthNode(i);
            else if (i == leftSize) return this;
            // else i is greater than leftSize then it's in right Tree
            else return right.getIthNode(i - (leftSize + 1));
        }
    }
}
