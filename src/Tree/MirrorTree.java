package Tree;

public class MirrorTree {
    public static void main(String[] args) {

    }

    private ListNode mirror (ListNode root) {
        if (root == null) return null;
        ListNode curr = null;
        if (root != null) {
            mirror(root.left);
            mirror(root.right);

            // swap node pointers between left and right nodes
            curr = root.left;
            root.left = root.right;
            root.right = curr;
        }
        return root;
    }

    private ListNode mirror2(ListNode root) {
        if (root == null) return root;
        ListNode left = mirror2(root.left);
        ListNode right = mirror2(root.right);

        root.left = right;
        root.right = left;

        return root;
    }
}
