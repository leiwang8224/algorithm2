package AlgoExpert.Hard;

import LinkedList.ListNode;

// O(n) time | O(1) space

/**
 * 0->1->2->4->5->6->7->8->9
 *          |_           __|
 *          loop from 9 to 4
 * let D = 0 to 4
 * let P = 4 to 7
 * let R = 7 to 4
 * slow ptr = X => D + P
 * fast ptr = 2X => 2D + 2P
 * T = 2D + P
 * R = T - P - D
 * R = 2D + P - P - D = D
 * slow ptr needs to move D distance to find the head of the loop
 *
 */
public class FindLoopLinkedList {
    private static ListNode findLoop(ListNode head) {
        ListNode slowPtr = head.next;
        ListNode fastPtr = head.next.next;

        while (slowPtr != fastPtr) {
            slowPtr = slowPtr.next;
            fastPtr = fastPtr.next.next;
        }
        slowPtr = head;
        while (slowPtr != fastPtr) {
            slowPtr = slowPtr.next;
            fastPtr = fastPtr.next;
        }
        return slowPtr;
    }
}
