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

        // find size of left subtree
        int leftSize = 0;
        if (root.left != null) {
            leftSize = size(root.left);
        }

        // if leftsize == k-1 then we found our kth smallest node
        if (leftSize+1 == k) {
            return root;
        } else if (k <= leftSize) { // k is less than or equal to leftSize
            // recurse on left subtree if k is smaller than the leftsize
            // remember the kth smallest
            return findKthSmallest(root.left, k);
        } else {
            // recurse on right subtree if k is bigger than the leftsize
            // take note to subtract the leftsize as we are trying to find the kth smallest
            return findKthSmallest(root.right, k-leftSize-1);    // subtract 1 for head node
        }
    }

    private static ListNode findKthLargest(ListNode root, int k) {
        if (root == null) return null;
        int leftSize = 0;

        if (root.left != null) {
            leftSize = size(root.left);
        }

        if (leftSize == k-1) {
            return root;
        } else if (leftSize < k) {
            return findKthLargest(root.left, k);
        } else {
            return findKthLargest(root.right, k - leftSize -1);
        }
    }

    private static int size(ListNode root) {
        if (root == null) return 0;
        return size(root.left) + size(root.right) + 1;
    }

}
