package Tree;

public class GetNodeDistance {
    public static void main(String[] args) {
        ListNode root = new ListNode(1);
        root.left = new ListNode(2);
        root.right = new ListNode(3);
        root.left.right = new ListNode(4);
        root.right.right = new ListNode(5);
        System.out.println(getNodeDistance(root,2,5));

        System.out.println(findDistanceByLevelAndLCA(root,2,5));
    }

//           1
//          / \
//         2   3
//          \   \
//           4   5
//
//    getNodeDistance(2,5) => 3
//  getNodeDistance(A,B) = pathLen(Root, A) + pathLen(Root, B) - (2 * pathLen(Root, LCA(A,B)))
    private static int getNodeDistance(ListNode root, int n1, int n2) {
        int distN1 = pathLenFromRoot(root, n1) - 1;
        int distN2 = pathLenFromRoot(root, n2) - 1;

        // find least common ancestor for the two nodes
        int lcaData = findLCA(root, n1, n2).getVal();

        // find distance from root node to lca node
        int lcaDistance = pathLenFromRoot(root, lcaData) - 1;

        return (distN1 + distN2) - 2 * lcaDistance;
    }

    private static ListNode findLCAByNode(ListNode root, ListNode node1, ListNode node2) {
        if (root == null || root == node1 || root == node2) return root;
        ListNode left = findLCAByNode(root.left, node1, node2);
        ListNode right = findLCAByNode(root.right, node1, node2);

        if (left != null && right != null) return root;

        if (left != null) return left;
        if (right != null) return right;

        return null;
    }

    private static ListNode findLCA2(ListNode root, int n1, int n2) {
        if (root == null || root.getVal() == n1 || root.getVal() == n2) return root;

        ListNode left = findLCA2(root.left, n1, n2);
        ListNode right = findLCA2(root.right, n1, n2);

        if (left != null && right != null) return root;

        if (left != null) return findLCA2(root.left, n1, n2);
        else return findLCA2(root.right, n1, n2);
    }

    private static int findLevel(ListNode root, int a, int level) {
        if (root == null) return -1;
        if (root.getVal() == a) return level;
        int left = findLevel(root.left, a, level + 1);
        if (left == -1) return findLevel(root.right, a, level + 1);
        return left;
    }

    private static int findDistanceByLevelAndLCA(ListNode root, int a, int b) {
        // find LCA
        ListNode lca = findLCA2(root, a, b);

        // find level using LCA
        int d1 = findLevel(lca, a, 0);
        int d2 = findLevel(lca, b, 0);

        return d1 + d2;
    }

    private static ListNode findLCA(ListNode root, int n1, int n2) {
        if (root == null) {
            return null;
        }

        if (root.getVal() == n1 || root.getVal() == n2) {
            return root;
        }

        // n1 could be on the left or right?
        // find n1 and n2 on left and right and return the
        // left and right node, the parent node is kept as root
        // just return the root.
        ListNode left = findLCA(root.left, n1, n2);
        ListNode right = findLCA(root.right, n1, n2);

        // make sure left and right exists, if they do then
        // parent is root
        if (left != null && right != null) {
            return root;
        }

        // or just return the left or right node if not null
        // (the two values are on either left or right branch)
        if (left != null)
            return left;
        else
            return right;
    }

    private static int pathLenFromRoot(ListNode root, int n1) {
        if (root == null) return 0;

        int outLeft = 0;
        int outRight = 0;

        outLeft = pathLenFromRoot(root.left, n1);
        outRight = pathLenFromRoot(root.right, n1);

//        if ((root.getVal() == n1) ||
//            (out = pathLenFromRoot(root.left, n1)) > 0 ||
//            (out = pathLenFromRoot(root.right, n1)) > 0) {

        // check if n1 is present at root or in left subtree
        // or right subtree
        if (root.getVal() == n1 || outLeft > 0 || outRight > 0) {
            if (outLeft > 0) return outLeft + 1;
            else return outRight + 1;
        }

        return 0;
    }

}
