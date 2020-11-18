package Tree;

//LC1325
public class RemoveLeafNodes {
    public static void main(String[] args) {
        ListNode head = composeTestTree();
        ListNode result1 = removeLeafNodes(head, 2);
        ListNode head2 = composeTestTree();
        ListNode result2 = removeLeafNodes2(head2, 2);
        ListNode head3 = composeTestTree();
        ListNode result3 = removeLeafNodes3(head3, 2);

        System.out.println("first");
        printTree(result1);
        System.out.println();
        System.out.println("second");
        printTree(result2);
        System.out.println();
        System.out.println("third");
        printTree(result3);
        System.out.println();

    }

    private static void printTree(ListNode head) {
        if (head == null) {
            System.out.print("null,");
            return;
        }
        System.out.print(head.getVal() + ",");
        printTree(head.left);
        printTree(head.right);
    }

    private static ListNode composeTestTree() {
        ListNode root = new ListNode(1);
        root.left = new ListNode(2);
        root.right = new ListNode(3);
        root.left.left = new ListNode(2);
        root.right.left = new ListNode(2);
        root.right.right = new ListNode(4);
        return root;
    }
    public static Tree.ListNode removeLeafNodes(Tree.ListNode root, int target) {
        if (root == null) return null;

        // leaf node with no children
        if (root.left == null && root.right == null && root.getVal() == target) {
            return null;
        }

        // recurse to reach the child node from the left child node,
        // then right child node
        Tree.ListNode left = removeLeafNodes(root.left, target);
        Tree.ListNode right = removeLeafNodes(root.right, target);

        if (left == null && right == null && root.getVal() == target) return null;

        root.left = left;
        root.right = right;

        return root;
    }

    // Time O(n)
    // Space O(height)
    // note deletion occurs at the end of the recursion because you want to
    // delete children and parent if they are both equal to target
    // if the deletion occurs in the beginning then ONLY the leaf nodes would be deleted
    // any subsequent parents will not be deleted
    public static Tree.ListNode removeLeafNodes2(Tree.ListNode root, int target) {
        if (root == null) return null;

        root.left = removeLeafNodes2(root.left, target);
        root.right = removeLeafNodes2(root.right, target);

        //bottom leaf and value found, so return null (delete current node)
        if (root.left == null && root.right == null && root.getVal() == target)
            return null;
        //else return root
        return root;
    }

//    Recursively call removeLeafNodes on the left and right.
//    If root.left == root.right == null and root.val == target,
//    the root node is now a leaf with value = target, we return null.
//    Otherwise return root node itself.
    public static Tree.ListNode removeLeafNodes3(Tree.ListNode root, int target) {
        if (root.left != null) root.left = removeLeafNodes3(root.left, target);
        if (root.right != null) root.right = removeLeafNodes3(root.right, target);
        return root.left == root.right && root.getVal() == target ? null:root;
    }
}
