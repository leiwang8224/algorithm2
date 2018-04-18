package Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by leiwang on 4/17/18.
 */
//Given an integer n, generate all structurally unique BST's
//(binary search trees) that store values 1...n.
//
//        For example,
//        Given n = 3, your program should return all
//        5 unique BST's shown below.
//       1         3     3      2      1
//        \       /     /      / \      \
//         3     2     1      1   3      2
//        /     /       \                 \
//       2     1         2                 3

// I start by noting that 1…n is the in-order traversal for any
// BST with nodes 1 to n. So if I pick i-th node as my root, the
// left subtree will contain elements 1 to (i-1), and the right
// subtree will contain elements (i+1) to n. I use recursive calls
// to get back all possible trees for left and right subtrees and
// combine them in all possible ways with the root.
public class UniqueBST {
    public static void main(String[] args) {

    }

    public List<ListNode> genTrees (int start, int end)
    {

        List<ListNode> list = new ArrayList<ListNode>();

        if(start>end)
        {
            list.add(null);
            return list;
        }

        if(start == end){
            list.add(new ListNode(start));
            return list;
        }

        List<ListNode> left,right;
        for(int i=start;i<=end;i++)
        {

            left = genTrees(start, i-1);
            right = genTrees(i+1,end);

            for(ListNode lnode: left)
            {
                for(ListNode rnode: right)
                {
                    ListNode root = new ListNode(i);
                    root.left = lnode;
                    root.right = rnode;
                    list.add(root);
                }
            }

        }

        return list;
    }

    public static List<ListNode> generateTreesDP(int n) {
        List<ListNode>[] result = new List[n + 1];
        result[0] = new ArrayList<ListNode>();
        if (n == 0) {
            return result[0];
        }

        result[0].add(null);
        for (int len = 1; len <= n; len++) {
            result[len] = new ArrayList<ListNode>();
            for (int j = 0; j < len; j++) {
                for (ListNode nodeL : result[j]) {
                    for (ListNode nodeR : result[len - j - 1]) {
                        ListNode node = new ListNode(j + 1);
                        node.left = nodeL;
                        node.right = clone(nodeR, j + 1);
                        result[len].add(node);
                    }
                }
            }
        }
        return result[n];
    }

    private static ListNode clone(ListNode n, int offset) {
        if (n == null) {
            return null;
        }
        ListNode node = new ListNode(n.getVal() + offset);
        node.left = clone(n.left, offset);
        node.right = clone(n.right, offset);
        return node;
    }

    /**
     * Divide and conquer method
     * @param s
     * @param e
     * @return
     */
    private List<ListNode> generateSubtrees(int s, int e) {
        List<ListNode> res = new LinkedList<ListNode>();
        if (s > e) {
            res.add(null); // empty tree
            return res;
        }

        for (int i = s; i <= e; ++i) {
            List<ListNode> leftSubtrees = generateSubtrees(s, i - 1);
            List<ListNode> rightSubtrees = generateSubtrees(i + 1, e);

            for (ListNode left : leftSubtrees) {
                for (ListNode right : rightSubtrees) {
                    ListNode root = new ListNode(i);
                    root.left = left;
                    root.right = right;
                    res.add(root);
                }
            }
        }
        return res;
    }

    /**
     * Brute force method
     * @param start
     * @param end
     * @return
     */
    public List<ListNode> generateTrees(int start,int end){
        List<ListNode> trees = new ArrayList<ListNode>();
        if(start>end){  trees.add(null); return trees;}

        for(int rootValue=start;rootValue<=end;rootValue++){
            List<ListNode> leftSubTrees=generateTrees(start,rootValue-1);
            List<ListNode> rightSubTrees=generateTrees(rootValue+1,end);

            for(ListNode leftSubTree:leftSubTrees){
                for(ListNode rightSubTree:rightSubTrees){
                    ListNode root=new ListNode(rootValue);
                    root.left=leftSubTree;
                    root.right=rightSubTree;
                    trees.add(root);
                }
            }
        }
        return trees;
    }

    /**
     * Another DP solution
     * @param n
     * @return
     */
    public List<ListNode> generateTrees(int n) {
        List<ListNode> res = new ArrayList<>();
        res.add(null);
        for(; n > 0; n--) {
            List<ListNode> next = new ArrayList<>();
            for(ListNode node: res) {
                //the special case when Node(n) is root of new tree
                ListNode root = new ListNode(n);
                root.right = node;
                next.add(root);
                //while loop inserts new value to every possible position into the left tree side
                while(node != null) {
                    ListNode cRoot = new ListNode(root.right.getVal());
                    //clone left subtree
                    cRoot.left = copyTree(root.right.left);
                    //reusing - point new root.right to the original right subtree
                    cRoot.right = root.right.right;
                    //curr is the cutoff node whose right child will be replaced by the new n 
                    ListNode curr = getValNode(cRoot, node.getVal());
                    //place n as curr's right child, make curr's original right child as the left child of n.
                    ListNode tmp = curr.left;
                    curr.left = new ListNode(n);
                    curr.left.right = tmp;

                    next.add(cRoot);
                    node = node.left;
                }
            }
            res = next;
        }
        return res;
    }
    private ListNode getValNode(ListNode n, int val) { //find the cutoff node in the new tree
        while(n != null) {
            if(n.getVal() == val) break;
            n = n.left;
        }
        return n;
    }

    private ListNode copyTree(ListNode root) { //clone the right subtree
        if(root == null) return null;
        ListNode cRoot = new ListNode(root.getVal());
        cRoot.left = copyTree(root.left);
        cRoot.right = copyTree(root.right);
        return cRoot;
    }

}
