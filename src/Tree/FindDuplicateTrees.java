package Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindDuplicateTrees {
    public static void main(String[] args) {
        System.out.println(findDuplicateSubtrees(generateTree()));
    }

//    Time Complexity: O(N^2)O(N2),
// where NN is the number of nodes in the tree.
// We visit each node once, but each creation of serial may take O(N)O(N) work.
//
//    Space Complexity: O(N^2)O(N2), the size of count.
    static Map<String, Integer> count;
    static List<ListNode> ans;

    private static List<ListNode> findDuplicateSubtrees(ListNode root) {
        count = new HashMap<>();
        ans = new ArrayList<>();
        System.out.println(collectPreOrder(root));
        System.out.println(collectInOrder(root));
        System.out.println(collectPostOrder(root));
        collect(root);
        return ans;
    }

    //    1
    //   / \
    //   2 3
    //  / \ \
    //  4 2 4
    //    /
    //   4
//            4,#,#
//            2,4,#,#,#
//            4,#,#
//            2,4,#,#,#
//            4,#,#
//            3,2,4,#,#,#,4,#,#
//            1,2,4,#,#,#,3,2,4,#,#,#,4,#,#
    private static String collect(ListNode root) {
        // output # if at leaf node
        if (root == null) return "#";
//        System.out.println("entering");
        // current node val, left node val, right node val
        // goes all the way to the left, get leaf node
        // move up one node and get leaf nodes
        String serial = root.getVal() + "," + collect(root.left) + "," + collect(root.right);

        System.out.println(serial);

        // put into hashmap, string and count ++
        count.put(serial, count.getOrDefault(serial, 0) + 1);
        // key = value
//        System.out.println("map=" + count.toString());

        // if there is duplicate subtree (more than 2), add that node
        if (count.get(serial) == 2)
            ans.add(root);
//        System.out.println("exiting");

        return serial;
    }

    //    1
    //   / \
    //  2   3
    // / \   \
    //4   2   4
    //   /
    //  4
//    1,2,4,#,#,#,3,2,4,#,#,#,4,#,#
    private static String collectPreOrder(ListNode root) {
        if (root == null) return "#";

        return root.getVal() + "," + collectPreOrder(root.left) + "," + collectPreOrder(root.right);
    }

//    #,4,#,2,#,1,#,4,#,2,#,3,#,4,#
    private static String collectInOrder(ListNode root) {
        if (root == null) return "#";

        return collectInOrder(root.left) + "," + root.getVal() + "," + collectInOrder(root.right);
    }

//    #,#,4,#,2,#,#,4,#,2,#,#,4,3,1
    private static String collectPostOrder(ListNode root) {
        if (root == null) return "#";

        return collectPostOrder(root.left) + "," + collectPostOrder(root.right) + "," + root.getVal();
    }
//
//    Time Complexity: O(N)O(N), where NN is the number of nodes in the tree. We visit each node once.
//
//    Space Complexity: O(N)O(N). Every structure we use is using O(1)O(1) storage per node.
    static int t;
    static Map<String, Integer> trees;
    static Map<Integer, Integer> countMap;
    List<ListNode> result;

    private List<ListNode> findDuplicateSubTrees2(ListNode root) {
        t = 1;
        trees = new HashMap<>();
        countMap = new HashMap<>();
        result = new ArrayList<>();
        lookup(root);
        return result;
    }

    private int lookup(ListNode root) {
        if (root == null) return 0;
        String serial = root.getVal() + "," + lookup(root.left) + "," + lookup(root.right);

        // put value t++ in key serial, output value in uid
        int uid = trees.computeIfAbsent(serial, x-> t++);
        countMap.put(uid, countMap.getOrDefault(uid, 0) + 1);
        if (count.get(uid) == 2)
            ans.add(root);
        return uid;
    }

    private static ListNode generateTree() {
        ListNode head = new ListNode(1);
        head.left = new ListNode(2);
        head.right = new ListNode(3);
        head.left.left = new ListNode(4);
        head.right.left = new ListNode(2);
        head.right.right = new ListNode(4);
        head.right.left.left = new ListNode(4);

        return  head;
    }
}
