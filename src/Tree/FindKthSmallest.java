package Tree;

public class FindKthSmallest {
    public static void main(String[] args) {
        ListNode root = new ListNode(4);
        root.left = new ListNode(2);
        root.right = new ListNode(8);
        root.right.left = new ListNode(5);
        root.right.right = new ListNode(10);

        System.out.println(findKthSmallest(root,2).getVal());

    }

    private static ListNode findKthSmallest(ListNode root, int k) {
        if (root == null) return null;
        int leftSize = 0;
        if (root.left != null) {
            leftSize = size(root.left);
        }
        if (leftSize+1 == k) {
            return root;
        } else if (k <= leftSize) {
            // recurse on left subtree
            return findKthSmallest(root.left, k);
        } else {
            // find in right subtree
            return findKthSmallest(root.right, k-leftSize-1);
        }
    }

    private static int size(ListNode root) {
        if (root == null) return 0;
        return size(root.left) + size(root.right) + 1;
    }

}
