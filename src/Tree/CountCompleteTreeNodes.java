package Tree;

//LC-222
public class CountCompleteTreeNodes {
    public static void main(String[] args) {
        ListNode root = new ListNode(1);
        root.left = new ListNode(2);
        root.right = new ListNode(3);
        root.left.left = new ListNode(4);
        root.left.right = new ListNode(5);
        root.right.left = new ListNode(6);
        System.out.println(countNodesIterative(root));
        System.out.println(countNodes2(root));
        System.out.println(countNodes3(root));
        System.out.println(countNodes4(root));
        System.out.println(countNodes5(root));
    }

//    The height of a tree can be found by just going left.
//    Let a single node tree have height 0. Find the height
//    h of the whole tree. If the whole tree is empty, i.e.,
//    has height -1, there are 0 nodes.
    private static int height(ListNode root) {
        return root == null ? -1 :1 + height(root.left);
    }

//    Otherwise check whether the height of the right subtree is
//    just one less than that of the whole tree, meaning left and
//    right subtree have the same height.
//
//    If yes, then the last node on the last tree row is in the
//    right subtree and the left subtree is a full tree of height
//    h-1. So we take the 2^h-1 nodes of the left subtree plus
//    the 1 root node plus recursively the number of nodes in the right subtree.
//    If no, then the last node on the last tree row is in the left
//    subtree and the right subtree is a full tree of height h-2.
//    So we take the 2^(h-1)-1 nodes of the right subtree plus the 1
//    root node plus recursively the number of nodes in the left subtree.
//
//    Since I halve the tree in every recursive step, I have O(log(n))
//    steps. Finding a height costs O(log(n)). So overall O(log(n)^2).

    private static int countNodesIterative(ListNode root) {
        int nodes = 0, curHeight = height(root);
        while (root != null) {
            // if right node height is equal to overall height - 1 (left height - 1)
            if (height(root.right) == curHeight - 1) {
                nodes += 1 << curHeight; // nodes = nodes + 2^(h)
                // traverse to the right and get height
                root = root.right;
            } else { // right node height is not equal to overall height - 1
                nodes += 1 << curHeight-1;// 2^(h)-1
                root = root.left;
            }
            curHeight--;
        }
        return nodes;
    }

    /**
     * @param root
     * @return
     */
    private static int countNodesRecurse(ListNode root) {
        int h = height(root);
        return h < 0 ? 0 :
                height(root.right) == h - 1? (1 << h) + countNodesRecurse(root.right) :
                                             (1 << h-1) + countNodesRecurse(root.left);
    }

    /**
     * Complete tree has (2 ^ h) -1 nodes, if left and right height is equal
     * we can use this formula, otherwise recursively search left and right
     * time O(n) worst case since we have to traverse to each node
     * @param root
     * @return
     */
    private static int countNodes2(ListNode root) {
        if (root == null) return 0;
        int leftHeight = getHeightLeft(root);
        int rightHeight = getHeightRight(root);

        //If left and right height are equal then by
        //definition there are 2^(h)-1 nodes.
        if(leftHeight == rightHeight) return (2<<(leftHeight)) -1;

        //else recursively calculate the number of nodes in left
            // and right and add 1 for root.
        else return countNodes2(root.left)+ countNodes2(root.right)+1; // O(n)
    }

    private static int getHeightRight(ListNode root) {
        int count=0;
        while(root.right!=null){
            count++;
            root = root.right;
        }
        return count;
    }

    private static int getHeightLeft(ListNode root) {
        int count=0;
        while(root.left!=null){
            count++;
            root = root.left;
        }
        return count;
    }

    /**
     *
     * @param root
     * @return
     */
    public static int countNodes3(ListNode root) {
        if (root == null) {
            return 0;
        }

        int totalDepth = getDepth(root);
        int rightDepth = getDepth(root.right);

        if (rightDepth + 1 == totalDepth) {
            return (1 << totalDepth - 1) + countNodes3(root.right);
        }
        return (1 << totalDepth - 2) + countNodes3(root.left);
    }

    /**
     * TODO How does this work?
     * @param root
     * @return
     */
    private static int getDepth(ListNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + getDepth(root.left);
    }

    /**
     *
     * @param root
     * @return
     */
    private static int countNodes4(ListNode root) {
        if(root == null)
            return 0;
        ListNode left = root.right, right = root.right;
        int height = 0;
        while(right!= null){
            left = left.left;
            right = right.right;
            height++;
        }
        if(left == null && right == null)
            return 1+(1 << height)-1 +countNodes4(root.left);
        else
            return 1+(1 << height+1)-1 + countNodes4(root.right);
    }

    // it's a problem of finding height of the tree
    // if left height == right height then # nodes = (2 ^ h)-1
    private static int countNodes5(ListNode root) {
        if (root == null)
            return 0;
        ListNode left = root, right = root;
        int height = 0;
        while (right != null) {
            left = left.left;
            right = right.right;
            height++;
        }
        if (left == null) // this means left height = right height
            return (1 << height) - 1;
        // else recurse to find the number of nodes
        return 1 + countNodes5(root.left) + countNodes5(root.right);
    }
}
