package Tree;

public class PathLengthFromRoot {
    public static void main(String[] args) {

    }

    private static int pathLengthFromRoot(ListNode root, int n1) {
        if (root == null) return 0;
        else {
            int out = 0;
            if ((root.getVal() == n1) || (out = pathLengthFromRoot(root.left, n1)) > 0 ||
                (out = pathLengthFromRoot(root.right,n1)) > 0) {
                return out + 1;
            }
            return 0;
        }
    }
}
