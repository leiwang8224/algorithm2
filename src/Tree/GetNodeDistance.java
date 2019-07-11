package Tree;

public class GetNodeDistance {
    public static void main(String[] args) {
        ListNode root = new ListNode(1);
        root.left = new ListNode(2);
        root.right = new ListNode(3);
        root.left.right = new ListNode(4);
        root.right.right = new ListNode(5);
        System.out.println(getNodeDistance(root,2,5));

    }

//           1
//          / \
//         2   3
//          \   \
//           4   5
//
//    getNodeDistance(2,5) => 3
    private static int getNodeDistance(ListNode root, int n1, int n2) {
        int distN1 = pathLenFromRoot(root, n1) - 1;
        int distN2 = pathLenFromRoot(root, n2) - 1;
        int lcaData = findLCA(root, n1, n2).getVal();
        int lcaDistance = pathLenFromRoot(root, lcaData) - 1;
        return (distN1 + distN2) - 2 * lcaDistance;
    }

    private static ListNode findLCA(ListNode root, int n1, int n2) {
        if (root == null) {
            return null;
        }

        if (root.getVal() == n1 || root.getVal() == n2) {
            return root;
        }

        ListNode left = findLCA(root.left, n1, n2);
        ListNode right = findLCA(root.right, n1, n2);

        if (left != null && right != null) {
            return root;
        }

        return left != null ? left : right;

    }

    private static int pathLenFromRoot(ListNode root, int n1) {
        if (root == null) return 0;

        int out = 0;
        if ((root.getVal() == n1) ||
            (out = pathLenFromRoot(root.left, n1)) > 0 ||
            (out = pathLenFromRoot(root.right, n1)) > 0) {
            return out + 1;
        }

        return 0;
    }

}
