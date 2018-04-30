package Tree;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by leiwang on 4/4/18.
 */
public class Serialization {
    public static void main(String[] args) {
        ListNode head = new ListNode(8);
        head.left = new ListNode(3);
        head.left.left = new ListNode(1);
        head.left.right = new ListNode(6);
        head.left.right.left = new ListNode(4);
        head.left.right.right = new ListNode(7);
        head.right = new ListNode(10);
        head.right.right = new ListNode(14);
        head.right.right.left = new ListNode(13);
        String serializedResult = serialize(head);
        System.out.println(serializedResult);
        BST.printBST(deserialize(serializedResult));
    }

    public static String serialize(ListNode head) {
        StringBuilder sb = new StringBuilder();
        buildString(head, sb);
        return sb.toString();

    }

    private static void buildString(ListNode head, StringBuilder sb) {
        if (head == null) {
            // end of the branch in the tree, terminating char
            sb.append("X").append(",");
        } else {
            // pre order traversal
            sb.append(head.getVal()).append(",");
            buildString(head.left, sb);
            buildString(head.right, sb);
        }
    }

    private static ListNode deserialize(String data) {
        Deque<String> nodes = new LinkedList<>();
        nodes.addAll(Arrays.asList(data.split(",")));
        return buildTree(nodes);
    }

    private static ListNode buildTree(Deque<String> nodes) {
        String val = nodes.remove();
        if (val.equals("X")) return null;  //terminating character
        else {
            ListNode node = new ListNode(Integer.valueOf(val));
            node.left = buildTree(nodes);
            node.right = buildTree(nodes);
            return node;
        }
    }

}
