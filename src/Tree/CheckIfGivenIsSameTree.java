package Tree;

//Input : Inorder -> 4 2 5 1 3
//        Preorder -> 1 2 4 5 3
//        Postorder -> 4 5 2 3 1
//        Output : Yes
//        Exaplanation : All of the above three traversals are of
//        the same tree
//          1
//        /   \
//       2     3
//      /       \
//     4         5
//
//        Input : Inorder -> 4 2 5 1 3
//        Preorder -> 1 5 4 2 3
//        Postorder -> 4 1 2 3 5
//        Output : No
public class CheckIfGivenIsSameTree {
    static int preIndex =0;
    public static void main(String[] args) {
        int inOrder[] = {4, 2, 5, 1, 3};
        int preOrder[] = {1, 2, 4, 5, 3};
        int postOrder[] = {4, 5, 2, 3, 1};

        int len = inOrder.length;

        ListNode root = buildTree(inOrder, preOrder, 0, len -1);

        int index = checkPostOrder(root, postOrder, 0);

        if (index == -1) {
            System.out.println("Wrong tree");
        } else {
            System.out.println("Correct tree");
        }
    }

    private static ListNode newNode(int data) {
        ListNode temp = new ListNode(data);
        temp.left = null;
        temp.right = null;
        return temp;
    }

    static int search (int arr[], int start, int end, int value) {
        for (int index = start; index <=end; index++) {
            if (arr[index] == value)
                return index;
        }

        return -1;
    }

    // Recursive function to construct binary tree of size len from
    // Inorder traversal in[] and Preorder traversal pre[]. Initial values
    // of start and end should be 0 and len-1.
    // The function doesn't do any error checking for cases where inorder
    // and preorder do not form tree
    private static ListNode buildTree(int in[], int pre[], int start, int end) {
        if (start > end)
            return null;

        // pick current node from preorder traversal using preIndex and increment preIndex
        ListNode tNode = newNode(pre[preIndex++]);

        // if this node has no children then return
        if (start == end) {
            return tNode;
        }

        // else find the index of this node in inorder traversal
        int inIndex = search(in, start, end, tNode.getVal());

        // using index in inorder traversal, construct left and right subtrees
        tNode.left = buildTree(in, pre, start, inIndex-1);
        tNode.right = buildTree(in, pre, inIndex+1, end);

        return tNode;
    }


    // function to compare post order traversal on constructed tree and given postorder
    static int checkPostOrder(ListNode node, int postOrder[], int index) {
        if (node == null) return index;

        index = checkPostOrder(node.left, postOrder, index);
        index = checkPostOrder(node.right,postOrder,index);

        // compare if data at current index in both postorder traversals are the same
        if (node.getVal() == postOrder[index])
            index++;
        else
            return -1;
        return index;
    }
    
}
