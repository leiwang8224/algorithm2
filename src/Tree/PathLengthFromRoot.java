package Tree;

public class PathLengthFromRoot {
    public static void main(String[] args) {
        ListNode root = new ListNode(1);
        root.left = new ListNode(2);
        root.right = new ListNode(3);
        root.left.left = new ListNode(4);
        root.left.right = new ListNode(5);
        root.right.left = new ListNode(6);
        root.right.right = new ListNode(7);
        System.out.println(pathLengthFromRoot(root,7));
        System.out.println(pathLengthFromRoot2(root,7));

    }

    private static int pathLengthFromRoot(ListNode root, int n1) {
        if (root == null) return 0;
        else {
            int out = 0;
            // if root val is == n1 OR path length on left > 0 OR path length on right > 0
            // assign pathLength on left and right to out
            if ((root.getVal() == n1) ||
                (out = pathLengthFromRoot(root.left, n1)) > 0 ||
                (out = pathLengthFromRoot(root.right,n1)) > 0) {
                return out + 1;
            }
            return 0;
        }
    }

    private static int pathLengthFromRoot2(ListNode root, int n1) {
        if (root == null) return 0;
        else {
            int outLeft = pathLengthFromRoot2(root.left, n1);
            int outRight =  pathLengthFromRoot2(root.right, n1);
            // outLeft is the number of nodes on the left
            // outRight is the number of nodes on the right
            // note that without the greater than 0 condition this recursion
            // would end prematurely
            if (root.getVal() == n1 || outLeft > 0 || outRight > 0) {
                return outLeft + outRight + 1;
            }
            return 0;
        }
    }
}
