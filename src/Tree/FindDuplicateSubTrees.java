package Tree;

import java.util.*;
public class FindDuplicateSubTrees {
    public static void main(String[] args) {
        ListNode root = new ListNode(0);
        root.left = new ListNode(1);
        root.left.left = new ListNode(2);
        root.right = new ListNode(0);
        root.right.left = new ListNode(1);
        root.right.left.left = new ListNode(2);

        //  0
        // 1 0
        //2 1
        // 2

        // hashmap of left subtree, right subtree
//        {2,#,#,=1}
//        {2,#,#,=1, 1,2,#,#,#,=1}
//        {2,#,#,=2, 1,2,#,#,#,=1}
//        {2,#,#,=2, 1,2,#,#,#,=2}
//        {2,#,#,=2, 0,1,2,#,#,#,#,=1, 1,2,#,#,#,=2}
//        {2,#,#,=2, 0,1,2,#,#,#,#,=1, 0,1,2,#,#,#,0,1,2,#,#,#,#,=1, 1,2,#,#,#,=2}
        List<ListNode> result = findDuplicateSubTrees(root);
        for (ListNode node : result) {
            System.out.print(node.getVal() + ",");
        }
    }

    static List<ListNode> findDuplicateSubTrees(ListNode root) {
        List<ListNode> duplicateResults = new LinkedList<>();
        postorder(root, new HashMap<>(), duplicateResults);
        return duplicateResults;
    }

    private static String postorder(ListNode root,
                           HashMap<String, Integer> map,
                           List<ListNode> duplicateResults) {
        if (root == null) return "#,";
        StringBuilder serializedTree = new StringBuilder();
        serializedTree.append(root.getVal() + ",");
        serializedTree.append(postorder(root.left, map, duplicateResults)); // traverse left
        serializedTree.append(postorder(root.right, map, duplicateResults)); // traverse right

        // put in map
        map.put(serializedTree.toString(), map.getOrDefault(serializedTree.toString(), 0) + 1);
        if (map.get(serializedTree.toString()) == 2) duplicateResults.add(root);
        System.out.println(map.toString());
        return serializedTree.toString();

    }
}
