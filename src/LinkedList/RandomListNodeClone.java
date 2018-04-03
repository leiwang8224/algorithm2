package LinkedList;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leiwang on 4/2/18.
 */
public class RandomListNodeClone {
    public static void main(String[] args) {

    }

    private static class RandomListNode{
        int label;
        RandomListNode next, random;
        RandomListNode(int x) {
            this.label = x;
        }
    }

    private static RandomListNode copyRandomList(RandomListNode head)
    {
        if (head == null) return null;

        Map<RandomListNode, RandomListNode> map =
                new HashMap<RandomListNode, RandomListNode>();

        // loop 1. copy all the nodes
        RandomListNode node = head;
        while (node != null) {
            map.put(node, new RandomListNode(node.label));
            node = node.next;
        }

        // loop 2. assign next and random ptrs
        node = head;
        while (node != null) {
            map.get(node).next = map.get(node.next);
            map.get(node).random = map.get(node.random);
            node = node.next;
        }
        return map.get(head);
    }
}
