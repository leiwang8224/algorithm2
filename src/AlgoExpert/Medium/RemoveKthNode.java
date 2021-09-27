package AlgoExpert.Medium;


import LinkedList.ListNode;

public class RemoveKthNode {
    public static void main(String[] args) {

    }

    // O(n) time | O(1) space
    // - move fast ptr to the kth position
    // - if fast ptr lands at end of list then we need to remove the head
    // - else traverse to the end of the list to remove kth node
    private static void removeKthNode(ListNode head, int k) {
        int counter = 1;
        ListNode slowPtr = head;
        ListNode fastPtr = head;

        // move fast ptr k nodes, including k
        while (counter <= k) {
            fastPtr = fastPtr.next;
            counter++;
        }

        // corner case is when k = list length
        // if fast ptr is null, delete original head (k = list length)
        if (fastPtr == null) {
            // change the head value first to the next value
            // then change reference to next next node
            head.setVal(head.next.getVal());
            head.next = head.next.next;
            return;
        }

        while (fastPtr.next != null) {
            fastPtr = fastPtr.next;
            slowPtr = slowPtr.next;
        }

        slowPtr.next = slowPtr.next.next;
    }


}
