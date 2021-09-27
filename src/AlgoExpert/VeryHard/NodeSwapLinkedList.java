package AlgoExpert.VeryHard;

import LinkedList.ListNode;

public class NodeSwapLinkedList {
    // O(n) time (n/2) swaps reduce to O(n) | O(n) space - where n is the number of nodes in list
    // recurse down to the end of the list and perform swap from the last pair
    // return the swapped last pair to the prev node to the pair
    ListNode nodeSwap(ListNode head) {
        if (head == null || head.next == null) return head;

        // save nextNode ptr
        ListNode nextNode = head.next;
        // head.next = nodeSwap(rest of the list)
        // perform swap on the next pair nodes
        head.next = nodeSwap(head.next.next);
        // set the nextNode.next to the prevNode (head)
        nextNode.next = head;
        // return the next node to head
        return nextNode;
    }

    // O(n) time | O(1) space - where n is the number of nodes in the list
    ListNode nodeSwapIterative(ListNode head) {
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;

        // save the prevNode ptr
        ListNode prevNode = dummyNode;
        // need to check the next 2 nodes exists to perform swap
        while (prevNode.next != null && prevNode.next.next != null) {
            ListNode firstNode = prevNode.next;
            ListNode secondNode = prevNode.next.next;
            // prevNode -> firstNode -> secondNode -> x

            firstNode.next = secondNode.next;
            secondNode.next = firstNode;
            prevNode.next = secondNode; // fix the pointer that has prevNode -> firstNode
            // prevNode -> secondNode -> firstNode -> x

            // move prevNode forward
            prevNode = firstNode;
        }
        // tempNode.next retains the head
        return dummyNode.next;
    }
}
