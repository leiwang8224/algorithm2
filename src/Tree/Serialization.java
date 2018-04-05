package Tree;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by leiwang on 4/4/18.
 */
public class Serialization {
    public static void main() {
        ListNode head = new ListNode(8);
        head.left = new ListNode(3);
        head.left.left = new ListNode(1);
        head.left.right = new ListNode(6);
        head.left.right.left = new ListNode(4);
        head.left.right.right = new ListNode(7);
        head.right = new ListNode(10);
        head.right.right = new ListNode(14);
        head.right.right.left = new ListNode(13);

    }

    public String serialize(ListNode head) {
        StringBuilder sb = new StringBuilder();
        buildString(head, sb);
        return sb.toString();

    }

    private void buildString(ListNode head, StringBuilder sb) {
        if (head == null) {
            sb.append("X").append(",");
        } else {
            sb.append(head.getVal()).append(",");
            buildString(head.left, sb);
            buildString(head.right, sb);
        }
    }

    private ListNode deserialize(String data) {
        Deque<String> nodes = new LinkedList<>();
        nodes.addAll(Arrays.asList(data.split(",")));
        return buildTree(nodes);
    }

    private ListNode buildTree(Deque<String> nodes) {
        String val = nodes.remove();
        if (val.equals("X")) return null;
        else {
            ListNode node = new ListNode(Integer.valueOf(val));
            node.left = buildTree(nodes);
            node.right = buildTree(nodes);
            return node;
        }
    }

}
