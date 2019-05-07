package Tree;

import java.util.*;

/**
 * Created by leiwang on 3/13/18.
 */
public class BuildTree {
    public static Integer depth = 0;

    public static void main(String[] args) {
        dfsTravel(generateTree());
        bfsTravel(generateTree());

        System.out.println("depth = " + getDepth(generateTree()));

        System.out.println("inverse Tree = ");
        bfsTravel(inverseTree(generateTree()));
        System.out.println("sum of left leaves = " + sumOfLeftLeaves(generateTree()));

        isBalanced(generateStrTree());
        calculateTreeTilt(generateTree(),new int[1]);
        getDiamater(generateStrTree());
        ArrayList<String> result = getTreePaths(generateStrTree());
        System.out.println("second getTreePaths");
        ArrayList<String> result2 = new ArrayList<>();
        getTreePaths(generateTree(), result2, "");

        System.out.println("get tree paths method 1");
        for (String str : result2) {
            System.out.println(str);
        }

        System.out.println("get tree paths method 2");
        for (String re : result) {
            System.out.println(re);
        }

        System.out.println("sum of all paths = " + getSumTreePaths(generateTree(),0));

        System.out.println("printing boundaries");
        printBoundariesTree(generateTree());

        ListNode head = generateTree();
        System.out.println("is Tree Symmetric " + isSymmetric(head.left,head.right));

        System.out.println("has path sum 12 " + hasPathSum(head,12));

        System.out.println("right side view");
        List<Integer> list = rightSideView(head);

        for (Integer integer : list)
            System.out.println(integer);

        System.out.println("is binary tree complete " + isBinaryTreeComplete(head));

        System.out.println("print range from 1 to 3");
        printRange(head,1,3);
        System.out.println();
        System.out.println("right side view is " + rightSideView2(generateTree()));

        ListNode anotherHead = generateTree();

        System.out.println("lowest common ancestor is " + findLowestCommonAncestor(anotherHead,anotherHead.left.left,anotherHead.right.right).getVal());

        System.out.println("max path sum " + maxPathSum(generateTree()));
        System.out.println("max path sum2 " + maxSumPath(generateTree()));
        List<List<Integer>> zigZagResult = zigzagTraversal(generateTree());
        System.out.println("zig zag traversal result ");
        for (List<Integer> lst : zigZagResult) {
            System.out.println(java.util.Arrays.toString(lst.toArray()));
        }
    }

    private static ListNode generateTree() {
        ListNode head = new ListNode(1);
        head.left = new ListNode(2);
        head.right = new ListNode(3);
        head.left.left = new ListNode(4);
        head.left.right = new ListNode(5);
        head.right.left = new ListNode(6);
        head.right.right = new ListNode(7);
        return  head;
    }

    private static ListNodeString generateStrTree() {
        ListNodeString headStr = new ListNodeString("head");
        headStr.left = new ListNodeString("l");
        headStr.left.left = new ListNodeString("ll");
        headStr.left.right = new ListNodeString("lr");
        headStr.right = new ListNodeString("r");
        headStr.right.left = new ListNodeString("rl");
        headStr.right.right = new ListNodeString("rr");
        return headStr;
    }

    /**
     * BFS traversal to print out the tree
     * @param head
     */
    private static void bfsTravel(ListNode head) {
        System.out.println("start BFS");
        Queue<ListNode> q = new LinkedList<>();
        q.add(head);

        while (!q.isEmpty()) {
            ListNode cur = q.remove();
            System.out.println(cur.getVal());
            if (cur.left != null) {
                q.add(cur.left);
            }
            if (cur.right != null) {
                q.add(cur.right);
            }
        }

    }

    /**
     * DFS traversal to print out the tree
     * @param head
     */
    private static void dfsTravel(ListNode head) {
        if (head == null)
            return;
        System.out.println(head.getVal());

        dfsTravel(head.left);
        dfsTravel(head.right);
    }

    /**
     * find if binary tree is balanced
     * @param head
     * @return
     */
    private static boolean isBalanced(ListNodeString head) {
        return getDfsHeight(head) != -1;
    }

    private static int getDfsHeight(ListNodeString head) {
        if (head != null)
            System.out.println("Entering getDfsHeight " + head.getVal());
        if (head == null)
            return 0;

        System.out.println("recurse on left tree");
        int leftHeight = getDfsHeight(head.left);
        if (leftHeight == -1) return -1;
        System.out.println("recurse on right tree");
        int rightHeight = getDfsHeight(head.right);
        if (rightHeight == -1) return -1;

        if (Math.abs(leftHeight - rightHeight) > 1) return -1;
        return Math.max(leftHeight,rightHeight) + 1;
    }

    /**
     * getDepth
     * @param head
     * @return
     */
    private static int getDepth(ListNode head) {
        if (head == null) return 0;
        if (head.left == null && head.right == null) return 1;

        if (head.left == null) {
            return getDepth(head.right);
        }

        if (head.right == null) {
            return getDepth(head.left);
        }

        return Math.min(getDepth(head.left),getDepth(head.right))+1;
    }

    /**
     * Invert binary tree
     * @param head
     * @return
     */
    private static ListNode inverseTree(ListNode head) {
        if (head == null)
            return null;
        ListNode left=null, right=null;
        if (head.left != null) {
            left = head.left;
        }

        if (head.right != null) {
            right = head.right;
        }

        head.left = inverseTree(right);
        head.right = inverseTree(left);
        return head;
    }

    //TODO figure this out
    private static int sumOfLeftLeaves(ListNode head) {
        System.out.println("enter sumOfLeftLeaves");
        if (head == null)
            return 0;
        int ans = 0;
        if (head.left != null) {
            System.out.println("head.left is not null ans = " + ans);
            // if no more left nodes
            if (head.left.left == null && head.right.left == null) {
                System.out.println("add left node " + head.left.getVal());
                ans += head.left.getVal();
            } else {
                // traverse left
                System.out.println("add to ans call recurse on left");
                ans += sumOfLeftLeaves(head.left);
            }
        }
        System.out.println("add to ans call recurse on right");
        ans +=sumOfLeftLeaves(head.right);
        return ans;
    }

    /**
     * Tree tilt is the sum of left tree nodes - sum of right tree nodes
     * use array so the values can be modified within the function
     * @param head
     * @return
     */
    private static int calculateTreeTilt(ListNode head, int[] ans) {
        System.out.println("Enter calculateTreeTilt " + ans[0]);
        if (head == null)
            return 0;

        System.out.println("recurse on left " + ans[0]);
        int left = calculateTreeTilt(head.left, ans);
        System.out.println("recurse on right " + ans[0]);
        int right = calculateTreeTilt(head.right, ans);

        ans[0] += Math.abs(left - right);
        System.out.println("Enter returning value " + ans[0]);

        return left + right + head.getVal();

    }

    /**
     * get diameter of the tree using max depth
     * @param head
     * @return
     */
    private static int getDiamater(ListNodeString head) {
        int[] result = new int[1];
        getMaxDepthTree(head, result);
        return result[0];
    }

    private static int getMaxDepthTree(ListNodeString head, int[] ans) {
        // use of array for passing parameters so it's not static (passing by value rather than reference)
        // changes every iteration
        if (head != null)
            System.out.println("entering getMaxDepthTree " + ans[0] + " " + head.getVal());
        if (head == null) return 0;

        System.out.println("recurse on left " + ans[0]);
        int left = getMaxDepthTree(head.left, ans);
        System.out.println("recurse on right" + ans[0]);
        int right = getMaxDepthTree(head.right, ans);

        ans[0] = Math.max(ans[0], left + right);
        System.out.println("returning from recurse " + ans[0]);

        return Math.max(left, right) + 1;
    }

    /**
     * Get all different tree paths to all the leaves and print it out
     * @param head
     * @return
     */
    private static ArrayList<String> getTreePaths(ListNodeString head) {
        //post traversal
        ArrayList<String> result = new ArrayList<>();

        getTreePathsHelper(head,result,"");
        return result;
    }

    //TODO check to make sure this is right
    private static void getTreePathsHelper(ListNodeString head, ArrayList<String> result, String path) {
        System.out.println("entering getTreePathsHelper " + path);
        if (head == null) {
            if (!result.contains(path))
                result.add(path);
            return;
        }

        // do your thing
        path = path + head.getVal() + "->";
        getTreePathsHelper(head.left, result,path);
        getTreePathsHelper(head.right, result,path);
        System.out.println("exiting " + path);
    }

    /**
     * Get all tree paths and print it out
     * @param head
     * @param result
     * @param path
     */
    private static void getTreePaths (ListNode head, ArrayList<String> result, String path) {
        if (head.left == null && head.right == null) result.add(path + head.getVal());
        if (head.left != null) getTreePaths(head.left, result, path + head.getVal() + "->");
        if (head.right != null) getTreePaths(head.right, result, path + head.getVal() + "->");
    }

    /**
     * Returns the sum of all of the tree paths
     * @param head
     * @param partialPathSum
     * @return
     */
    private static int getSumTreePaths(ListNode head, int partialPathSum) {
        if (head == null) return 0;

        partialPathSum = partialPathSum + head.getVal();

        //leaf found
        if(head.left == null && head.right == null) {
            return partialPathSum;
        }
        return getSumTreePaths(head.left, partialPathSum) + getSumTreePaths(head.right, partialPathSum);
    }

    static int ans;
    /**
     * Given a non-empty binary tree, find the maximum path sum.

     For this problem, a path is defined as any sequence of nodes from some starting node to
     any node in the tree along the parent-child connections. The path must contain at least
     one node and does not need to go through the root.
     * Time O(N)
     * Space O(h)
     * @param head
     * @return
     */
    private static int maxPathSum(ListNode head) {
        if (head == null) return 0;
        ans = head.getVal();
        getMax(head);
        return ans;
    }

    /**
     * Keep track of 2 states
     * max of path with current node as root
     * max of head.val, head.val + left, head.val + right
     * @param head
     * @return
     */
    private static int getMax(ListNode head) {
        if (head == null) return 0;

        int left = getMax(head.left);
        int right = getMax(head.right);

        int maxPathThruCurrent; // max path with current node as root
        int toReturn; // max between head.val, head.val + left, head.val + right

        if (left <= 0 && right <= 0) {
            maxPathThruCurrent = head.getVal();
            toReturn = head.getVal();
            System.out.println("left <= 0 && right <= 0: left = " + left + " right = " + right + " maxPathThruCurrent " + maxPathThruCurrent + " toReturn " + toReturn);
        } else if (left <= 0) {
            maxPathThruCurrent = head.getVal() + right;
            toReturn = head.getVal() + right;
            System.out.println("left <= 0: " + left + " right = " + right + " maxPathThruCurrent " + maxPathThruCurrent + " toReturn " + toReturn);
        } else if (right <= 0) {
            maxPathThruCurrent = head.getVal() + left;
            toReturn = head.getVal() + left;
            System.out.println("right <= 0: " + left + " right = " + right + " maxPathThruCurrent " + maxPathThruCurrent + " toReturn " + toReturn);
        } else {
            // left > 0 and right > 0
            maxPathThruCurrent = head.getVal() + left + right;
            toReturn = head.getVal() + Math.max(left, right);
            System.out.println("else: left = " + left + " right = " + right + " maxPathThruCurrent " + maxPathThruCurrent + " toReturn " + toReturn);
        }

        ans = Math.max(ans, maxPathThruCurrent);
        return toReturn;
    }

    private static int maxSumPath(ListNode root) {
        int[] max = new int[1];
        maxSumPath(root, max);
        return max[0];
    }

    private static int maxSumPath(ListNode root, int[] max) {
        if (root == null) return 0;

        // calculate the left node and right node sum using
        // the return value, including the current root node
        int leftSum = maxSumPath(root.left, max);
        int rightSum = maxSumPath(root.right, max);

        // get the max path sum up to this node, including this node's value
        // return to the function (leftSum and rightSum)
        int nodeCumVal = Math.max(root.getVal() + leftSum, root.getVal() + rightSum);

        // check and update the max holder by using the results
        // leftSum and rightSum calculated from recursion
        max[0] = Math.max(max[0], leftSum + root.getVal() + rightSum);
        return nodeCumVal;
    }

    /**
     * Find if binary tree is symmetric (left and right nodes are not null)
     * @param headLeftNode
     * @param headRightNode
     * @return
     */
    private static boolean isSymmetric(ListNode headLeftNode, ListNode headRightNode) {
        if (headLeftNode == null && headRightNode == null) return true;
        else if (headLeftNode != null && headRightNode != null) {
            return headLeftNode.getVal() == headRightNode.getVal() &&
                    isSymmetric(headLeftNode.left, headRightNode.right) &&
                    isSymmetric(headLeftNode.right, headLeftNode.left);
        }
        //one subtree is empty and the other is not
        return false;
    }

    /**
     * Find if any path in the tree adds to the given sum
     * @param head
     * @param remainingWeight
     * @return
     */
    private static boolean hasPathSum(ListNode head, int remainingWeight) {
        if (head == null) return false;
        else if (head.left == null && head.right == null) {
            return remainingWeight == head.getVal();
        }
        // non leaf to do here
        return hasPathSum(head.left, remainingWeight - head.getVal()) ||
                hasPathSum(head.right, remainingWeight - head.getVal());
    }

    /**
     * Print boundaries of binary tree
     * @param head
     */
    private static void printBoundariesTree(ListNode head) {
        System.out.println("head = " + head.getVal());
        System.out.println("printing left");
        printLeftBoundaryTree(head.left);

        System.out.println("printing leaves");
        printLeaves(head.left);
        printLeaves(head.right);

        System.out.println("printing right");
        printRightBoundaryTree(head.right);

    }

    private static void printLeftBoundaryTree(ListNode head) {
        // print first to allow for top to bottom printing
        if (head != null) {
            if (head.left != null) {
                System.out.println(head.getVal() + " ");
                printLeftBoundaryTree(head.left);
            } else if (head.right != null) {
                System.out.println(head.getVal() + " ");
                printLeftBoundaryTree(head.right);
            }
        }
    }

    private static void printRightBoundaryTree(ListNode head) {
        // first recurse to allow for bottom to top printing
        if (head != null) {
            if (head.right != null) {
                printRightBoundaryTree(head.right);
                System.out.println(head.getVal());
            } else if (head.left != null) {
                System.out.println(head.getVal() + " ");
                printRightBoundaryTree(head.left);
            }
        }
    }

    /**
     * Only print the leaves of a tree
     * @param head
     */
    private static void printLeaves(ListNode head) {
        if (head != null) {
            printLeaves(head.left);
            if (head.left == null && head.right == null) {
                System.out.println(head.getVal());
            }
            printLeaves(head.right);
        }
    }

    /**
     * get right side view of binary tree
     * @param head
     * @return
     */
    public static List<Integer> rightSideView(ListNode head) {
        ArrayList<Integer> list = new ArrayList<>();
        getRightSideView(head, list, 0);
        return list;
    }

    private static void getRightSideView(ListNode head, ArrayList<Integer> list, int curDepth) {
        if (head == null) return;

        // makes sure the first element of the depth is added to the list first
        if (list.size() == curDepth) {
            list.add(head.getVal());
        }

        // note it's not the standard in order traversal, reverse and it will
        // print left side view
        getRightSideView(head.right, list, curDepth + 1);
        getRightSideView(head.left, list, curDepth + 1);
    }

    /**
     * Another way of getting right side view
     * @param head
     * @return
     */
    public static List<Integer> rightSideView2(ListNode head) {
        List<Integer> list = new ArrayList<>();
        Queue<ListNode> q = new LinkedList<>();
        if (head == null) return list;

        q.offer(head);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i ++) {
                ListNode cur = q.poll();
                // the first element is the rightmost element in tree
                if (i == 0) list.add(cur.getVal());
                if (cur.right != null) q.add(cur.right);
                if (cur.left != null) q.add(cur.left);
            }
        }
        return list;
    }

    /**
     * Calculate the number of nodes (count) in the binary tree.
     Start recursion of the binary tree from the root node of the binary tree
     with index (i) being set as 0 and the number of nodes in the binary (count).
     If the current node under examination is NULL, then the tree is a complete
     binary tree. Return true.
     If index (i) of the current node is greater than or equal to the number of
     nodes in the binary tree (count) i.e. (i>= count), then the tree is not a
     complete binary. Return false.
     Recursively check the left and right sub-trees of the binary tree for same
     condition. For the left sub-tree use the index as (2*i + 1) while for the
     right sub-tree use the index as (2*i + 2).
     * @param head
     * @return
     */
    private static boolean isBinaryTreeComplete(ListNode head) {
        return isBinaryTreeComplete(head, countNodesInTree(head), 0);

    }

    private static boolean isBinaryTreeComplete(ListNode head, int numberOfNodes, int nodeIndex) {
        if (head == null)
            return true;

        if (nodeIndex >= numberOfNodes)
            return false;

        return (isBinaryTreeComplete(head.left, numberOfNodes, 2 * nodeIndex + 1) &&
                isBinaryTreeComplete(head.right, numberOfNodes, 2 * nodeIndex + 2));
    }

    private static int countNodesInTree(ListNode head) {
        if (head == null)
            return 0;
        return 1 + countNodesInTree(head.left) + countNodesInTree(head.right);
    }

    private static void printRange(ListNode head, int leftRange, int rightRange) {
        if (head == null)
            return;

        if (leftRange < head.getVal()) {
            printRange(head.left, leftRange, rightRange);
        }

        if (leftRange <= head.getVal() && rightRange >= head.getVal()) {
            System.out.print(head.getVal() + " ");
        }

        if (rightRange > head.getVal()) {
            printRange(head.right, leftRange, rightRange);
        }
    }

    /**
     * Find lowest common ancestor for node1 and node2
     * @param head
     * @param node1
     * @param node2
     * @return
     */
    private static ListNode findLowestCommonAncestor(ListNode head, ListNode node1, ListNode node2) {
        if(head == null || head == node1 || head == node2)  return head;
        ListNode left = findLowestCommonAncestor(head.left, node1, node2);
        ListNode right = findLowestCommonAncestor(head.right, node1, node2);
        if(left != null && right != null)   return head;
        return left != null ? left : right;
    }

    /**
     * Traverses left to right then right to left and repeat
     * @param head
     * @return
     */
    private static List<List<Integer>> zigzagTraversal(ListNode head) {
        List<List<Integer>> sol = new ArrayList<>();
        travel(head, sol, 0);
        return sol;
    }

    private static void travel(ListNode head, List<List<Integer>> sol, int level) {
        if (head == null) return;

        if (sol.size() <= level) {
            List<Integer> newLevel = new LinkedList<>();
            sol.add(newLevel);
        }

        List<Integer> collection = sol.get(level);
        if (level % 2 == 0) collection.add(head.getVal());
        else collection.add(0, head.getVal());

        travel(head.left, sol, level + 1);
        travel(head.right, sol, level + 1);
    }

    public static List<ListNode> list = new ArrayList<>();

    /**
     * Get the tree nodes in order:
     * left -> head -> right
     * @param head
     */
    public static void getTreeNodesInorder(ListNode head) {
        if (head == null)
            return;
        getTreeNodesInorder(head.left);
        list.add(head);
        getTreeNodesInorder(head.right);
    }

    // print in order traversal of tree
    public static List<List<Integer>> printInOrderTraversal(ListNode head) {
        List<List<Integer>> result = new ArrayList<>();
        if (head == null) return result;

        List<ListNode> currentLevelNodes = new LinkedList<>();

        currentLevelNodes.add(head);
        while (!currentLevelNodes.isEmpty()) {
            List<ListNode> nextLevelNodes = new LinkedList<>();
            List<Integer> temp = new LinkedList<>();

            for (ListNode node : currentLevelNodes) {
                temp.add(node.getVal());

                if (node.left != null) {
                    nextLevelNodes.add(node.left);
                }

                if (node.right != null) {
                    nextLevelNodes.add(node.right);
                }
            }

            result.add(temp);
            currentLevelNodes = nextLevelNodes;
        }

        return result;
    }


}
