package Tree;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class ConstructTreePrePost {
    public static void main(String[] args) {

        int[] pre = new int[]{1,2,4,5,3,6,7};
        int[] post = new int[]{4,5,2,6,7,3,1};
        ListNode soln1 = constructFromPrePost(pre,post);
        ListNode soln2 = constructFromPrePostSoln1(pre,post);
        ListNode soln3 = constructFromPrePost2(pre,post);

        BST.printBST(soln1);
        System.out.println();
        BST.printBST(soln2);
        System.out.println();
        BST.printBST(soln3);


    }

    private static ListNode constructFromPrePost(int[] pre, int[] post) {
        return constructFromPrePost(pre, 0, pre.length - 1, post, 0, post.length - 1);
    }

    private static ListNode constructFromPrePost(int[] pre, int preL, int preR, int[] post, int postL, int postR) {
        if (preL > preR || postL > postR) return null;

        ListNode root = new ListNode(pre[preL]);
        if (preL == preR) return root;  // indication we are at the end of the array

        // in the post, look for the same node as pre
        // set index to the post index where the same node located
        int index = -1;
        for (int i = postL; i < postR; i++) {
            if (pre[preL + 1] == post[i]) {
                index = i;
                break;
            }
        }

        // if that index is not found, return root
        if (index == -1) return root;

        // recurse on left and right to build tree
        root.left = constructFromPrePost(pre, preL + 1, preL + 1+(index-postL), post, postL, index);
        root.right = constructFromPrePost(pre, preL+1+(index-postL)+1, preR, post, index + 1, postR);
        return root;
    }



//    Time Complexity: O(N2)O(N^2)O(N2), where NNN is the number of nodes.
//
//    Space Complexity: O(N)O(N)O(N), the space used by the answer.
    private static ListNode constructFromPrePost2(int[] pre, int[] post) {
        // Deque is a double sided queue
        Deque<ListNode> s = new ArrayDeque<>();

        // insert into tail of queue
        s.offer(new ListNode(pre[0]));

        for (int preIndex = 1, postIndex = 0; preIndex < pre.length; preIndex++) {
            ListNode node = new ListNode(pre[preIndex]);

            // retrieves, but does not remove the last element
            while (s.getLast().getVal() == post[postIndex]) {
                // retrieves and removes the last element
                s.pollLast();
                postIndex++;
            }

            // retrieves, but does not remove the last element
            if (s.getLast().left == null)
                s.getLast().left = node;
            else s.getLast().right = node;
            s.offer(node);
        }
        return s.getFirst();
    }

//
//    Approach 1: Recursion
//
//            Intuition
//
//    A preorder traversal is:
//
//            (root node) (preorder of left branch) (preorder of right branch)
//
//    While a postorder traversal is:
//
//            (postorder of left branch) (postorder of right branch) (root node)
//
//    For example, if the final binary tree is [1, 2, 3, 4, 5, 6, 7] (serialized), then the preorder traversal
    //is [1] + [2, 4, 5] + [3, 6, 7], while the postorder traversal is [4, 5, 2] + [6, 7, 3] + [1].
//
//    If we knew how many nodes the left branch had, we could partition these arrays as such,
    //    and use recursion to generate each branch of the tree.
//
//            Algorithm
//
//    Let's say the left branch has LLL nodes. We know the head node of that left branch is pre[1], but it also occurs last in the
    //    postorder representation of the left branch. So pre[1] = post[L-1] (because of uniqueness of the node values.) Hence, L = post.indexOf(pre[1]) + 1.
//
//    Now in our recursion step, the left branch is represnted by pre[1 : L+1] and post[0 : L],
    //    while the right branch is represented by pre[L+1 : N] and post[L : N-1].

//    Time Complexity: O(N2)O(N^2)O(N2), where NNN is the number of nodes.
//
//    Space Complexity: O(N2)O(N^2)O(N2).
    private static ListNode constructFromPrePostSoln1 (int[] pre, int[] post) {
        int N = pre.length;
        if (N == 0) return null;
        ListNode root = new ListNode(pre[0]);
        if (N == 1) return root;

        int L = 0;
        for (int i = 0; i < N; i++) {
            if (post[i] == pre[1])
                L = i + 1;
        }

        root.left = constructFromPrePostSoln1(Arrays.copyOfRange(pre, 1, L + 1), Arrays.copyOfRange(post, 0, L));
        root.right = constructFromPrePostSoln1(Arrays.copyOfRange(pre, L + 1, N), Arrays.copyOfRange(post, L, N - 1));
        return root;
    }

//    static int[] pre, post;
//    private static ListNode constructFromPrePostSoln2 (int[] pre, int[] post) {
//        this.pre = pre;
//        this.post = post;
//        return make (0,0, pre.length);
//    }
//
//    private static ListNode make(int i0, int i1, int N) {
//        if (N == 0) return null;
//        ListNode root = new ListNode(pre[i0]);
//        if (N == 1) return root;
//
//        int L = 1;
//        for (; L < N; L++)
//            if (post[i1 + L-1] == pre[i0 + 1])
//                break;
//
//
//        root.left = make(i0+1,i1,L);
//        root.right = make(i0+L+1,i1+L,N-1-L);
//        return root;
//    }
}
