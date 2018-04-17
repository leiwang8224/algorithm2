package Tree;
//Populate each next pointer to point to its next right node.
//If there is no next right node, the next pointer should be set to NULL.
//
//        Initially, all next pointers are set to NULL.
//
//        Note:
//
//        You may only use constant extra space.
//        You may assume that it is a perfect binary tree
// (ie, all leaves are at the same level, and every parent has two children).
//          1
//         /  \
//        2    3
//       / \  / \
//      4  5  6  7
public class NextRightPointer {
    public static void main(String[] args) {
        ListNode root = new ListNode(0);
        root.left = new ListNode(1);
        root.right = new ListNode(2);
        root.left.left = new ListNode(3);
        root.left.right = new ListNode(4);
        root.right.left = new ListNode(5);
        root.right.right = new ListNode(6);

        connect(root);
        printTree(root);
    }

    private static void printTree(ListNode root) {
        if (root == null)
            return;
        System.out.println(root.val);
        if (root.next != null)
            System.out.println("next " + root.next.val);
        printTree(root.left);
        printTree(root.right);
    }

    private static class ListNode {
        public ListNode left;
        public ListNode right;
        public ListNode next;
        private int val;
        ListNode(int x) {
            val = x;
        }
    }

    private static void connect(ListNode root) {
        if (root == null) return;
        ListNode pre = root;
        ListNode cur = null;

        while (pre.left != null) {
            cur = pre;
            while (cur!=null) {
                cur.left.next = cur.right;
                if (cur.next != null) cur.right.next = cur.next.left;
                cur = cur.next;
            }
            pre = pre.left;
        }
    }

    private static void connect2(ListNode root) {
        ListNode levelStart = root;
        while (levelStart!= null) {
            ListNode cur = levelStart;
            while(cur!= null) {
                if(cur.left!=null) cur.left.next=cur.right;
                if(cur.right!=null && cur.next!=null) cur.right.next=cur.next.left;

                cur=cur.next;
            }
            levelStart = levelStart.left;
        }
    }

    private static void connectRecurse(ListNode root) {
        if (root == null)
            return;

        if (root.left != null) {
            root.left.next = root.right;
            if (root.next != null)
                root.right.next = root.next.left;
        }

        connect(root.left);
        connect(root.right);
    }
}
