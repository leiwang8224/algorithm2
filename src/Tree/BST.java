package Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by leiwang on 4/4/18.
 */
public class BST {
    public static void main(String args[]) {
        printBST(generateBST());

        insert(generateBST(),5);

        System.out.println("afterInsert");
        printBST(generateBST());

        System.out.println("afterDeletion");
        printBST(delete(generateBST(), 3));
        printBST(generateBST());

        BSTIterator bstIterator = new BSTIterator(generateBST());
        System.out.println("using BST Iterator");
        while (bstIterator.hasNext())
            System.out.println(bstIterator.next());

        System.out.println("isBSTRecurse " + isBSTRecurse(generateBST()));
        System.out.println("isBSTIterative " + isBSTIterative(generateBST()));

        int[] nums = new int[]{1,2,3,4,5,6,7,8,9,10};
        ListNode transformedList = sortedArrayToBST(nums);
        printBST(transformedList);

        List<Integer> result = treeToListByLevel(generateBST());
        System.out.println(java.util.Arrays.toString(result.toArray()));

        System.out.println(search(generateBST(),3).getVal());
    }

    private static ListNode generateBST() {
        ListNode head = new ListNode(8);
        head.left = new ListNode(3);
        head.left.left = new ListNode(1);
        head.left.right = new ListNode(6);
        head.left.right.left = new ListNode(4);
        head.left.right.right = new ListNode(7);
        head.right = new ListNode(10);
        head.right.right = new ListNode(14);
        head.right.right.left = new ListNode(13);
        return head;
    }

    /**
     * print out tree nodes using BST traversal
     * @param head
     */
    public static void printBST(ListNode head) {
        if (head == null)
            return;
        printBST(head.left);
        System.out.println(head.getVal());
        printBST(head.right);
    }

    /**
     * binary search for BST
     * @param head
     * @param key
     * @return
     */
    private static ListNode search(ListNode head, int key) {
        if (head == null || head.getVal() == key)
            return head;

        if (head.getVal() > key) {
            return search(head.left, key);
        }

        return search(head.right, key);
    }

    /**
     * insert node into the appropriate place in BST
     * @param head
     * @param key
     * @return
     */
    private static ListNode insert(ListNode head, int key) {
        if (head == null) {
            head = new ListNode(key);
            return head;
        }

        if (key < head.getVal()) {
            head.left = insert(head.left, key);
        } else if (key > head.getVal()) {
            head.right = insert(head.right, key);
        }

        return head;
    }

    /**
     * delete node from BST
     * @param head
     * @param key
     * @return
     */
    private static ListNode delete(ListNode head, int key) {
        if (head == null) return head;

        if (key < head.getVal()) {
            head.left = delete(head.left, key);
        } else if (key > head.getVal()) {
            head.right = delete(head.right, key);
        } else {
            // if key is same as head's key
            // then this is node to be deleted

            // node with only one child or no child
            if (head.left == null) {
                return head.right;
            } else if (head.right == null) {
                return head.left;
            }

            // node with two children
            // get the inorder successor
            // smallest in the right subtree
            head.setVal(minVal(head.right));

            // delete the inorder successor
            head.right = delete(head.right, head.getVal());
        }
        return head;
    }

    /**
     * Get min value from BST
     * @param head
     * @return
     */
    private static int minVal(ListNode head) {
        int minv = head.getVal();
        while (head.left != null) {
            minv = head.left.getVal();
            head = head.left;
        }
        return minv;
    }

    /**
     * BSTIterator initializes with the root node of a BST
     * Calling next() will return the next smallest number in the
     * BST. Note next() and hasNext() runs in O(1) and uses O(h)
     * memory, where h is the height of the tree.
     */
    private static class BSTIterator {
        Stack<ListNode> stack;

        public BSTIterator(ListNode root) {
            stack = new Stack<ListNode>();
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public int next() {
            ListNode node = stack.pop();
            int result = node.getVal();
            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
            return result;
        }
    }

    private static class BSTIterator2 {
        List<Integer> sortedArr;
        int current;

        public BSTIterator2(ListNode root) {
            sortedArr = new ArrayList<>();
            current = -1;
            if (root != null)
                recurse(root);
        }

        private void recurse(ListNode root) {
            if (root.left == null && root.right == null) {
                sortedArr.add(root.getVal());
            } else {
                if (root.left != null)
                    recurse(root.left);
                sortedArr.add(root.getVal());

                if (root.right != null)
                    recurse(root.right);
            }
        }

        public int next() {
            current ++;
            return sortedArr.get(current);
        }

        public boolean hasNext() {
            if (current+1 < sortedArr.size()) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Check to see if tree is BST using recursive method
     * @param root
     * @return
     */
    private static boolean isBSTRecurse(ListNode root) {
        if (root == null)
            return true;
        else {
            return (root.left == null || root.getVal() >= root.left.getVal() &&
                    root.right == null || root.getVal() <= root.right.getVal() &&
                    isBSTRecurse(root.left) &&
                    isBSTRecurse(root.right));
        }
    }

    /**
     * check to see if tree is BST using iterative method
     * @param root
     * @return
     */
    private static boolean isBSTIterative(ListNode root) {
        if (root == null)
            return false;
        BuildTree.getTreeNodesInorder(root);
        List<ListNode> list = BuildTree.list;
        for (ListNode node : list)
            System.out.println("list in order " + node.getVal());
        if (list.size() == 1)
            return true;
        for (int i = 0; i < list.size()-1; i ++) {
            if (list.get(i).getVal() > list.get(i+1).getVal()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Recursive method with binary search
     * time: O(N)
     * space: O(N)
     * @param nums
     * @return
     */
    private static ListNode sortedArrayToBST(int[] nums) {
        if (nums != null && nums.length != 0) {
            return transformToBST(nums, 0, nums.length -1);
        }
        return null;
    }

    /**
     * Transform array to BST
     * @param nums
     * @param bottom
     * @param top
     * @return
     */
    private static ListNode transformToBST(int[] nums, int bottom, int top) {
        int center = (top + bottom) / 2;
        if (nums.length == 1) {
            return new ListNode(nums[0]);
        } else if (bottom > top) {
            return null;
        } else {
            // splits the array into two and put first half in left, second half in right
            ListNode node = new ListNode(nums[center]);
            node.left = transformToBST(nums, bottom, center - 1);
            node.right = transformToBST(nums, center+1, top);
            return node;
        }
    }

    /**
     * Transform BST to list level by level
     * @param root
     * @return
     */
    private static List<Integer> treeToListByLevel(ListNode root) {
        List<Integer> nodesByLevel = new LinkedList<>();
        Queue<ListNode> stack = new LinkedList<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            ListNode node = stack.remove();
            nodesByLevel.add(node.getVal());
            if (node.left != null) {
                stack.add(node.left);
            }
            if (node.right != null) {
                stack.add(node.right);
            }
        }
        return nodesByLevel;
    }

}
