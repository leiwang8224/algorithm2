package LinkedList;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leiwang on 4/2/18.
 */

/**
 * Given a linked list having two pointers in each node. The first one points to the
 * next node of the list, however the other pointer is random and can point to any
 * node of the list. Write a program that clones the given list in O(1) space, i.e.,
 * without any extra space.
 */
public class RandomListNodeClone {
    public static void main(String[] args) {
        RandomListNode head = new RandomListNode(0);
        head.next = new RandomListNode(1);
        head.next.next = new RandomListNode(2);
        head.next.next.next = new RandomListNode(3);
        head.next.next.next.next = new RandomListNode(4);
        head.next.next.next.next.next = new RandomListNode(5);

        head.random = head.next.next;
        head.next.random = head.next;
        head.next.next.random = head.next.next.next;
        head.next.next.next.random = head.next.next.next.next;
        head.next.next.next.next.random = head.next.next.next.next.next;

        printLinkedList(head);
        RandomListNode newHead = copyRandomList(head);
        printLinkedList(newHead);

    }

    private static void printLinkedList(RandomListNode head) {
        while (head != null) {
            System.out.println("value " + head.label);
            if(head.random != null)
                System.out.println("random " + head.random.label);
            head = head.next;
        }
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
        System.out.println("copyRandomList");
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
