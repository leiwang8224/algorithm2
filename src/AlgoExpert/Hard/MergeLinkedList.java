package AlgoExpert.Hard;

import LinkedList.ListNode;

public class  MergeLinkedList {
    //O(n+m) time | O(1) space
    // where n is the number nodes in first linkedlist
    // m is the number nodes in second linkedlist

    /**
     * Allocate three pointers, 2 for first list and 1 for second list
     * always keep the list1 to be smaller than list2
     * push smaller values from list2 into list1
     */
    private static ListNode mergeLinkedList(ListNode head1, ListNode head2) {
        ListNode p1 = head1;
        ListNode p1Prev = null;
        ListNode p2 = head2;

        while (p1 != null && p2 != null) {
            // keep moving forward the p1 ptrs if p1 < p2
            // so that we reach a point where p1 > p2
            if (p1.getVal() < p2.getVal()) {
                p1Prev = p1;
                p1 = p1.next;
            } else { // p1 > p2, so insert p2 to before p1 and after p1Prev
                // p1Prev -> p2 -> p1
                if (p1Prev != null) p1Prev.next = p2; // we are not at the head, just insert
                p1Prev = p2; // grab reference to p2 before we replace it
                // NOTE the order in the next 2 lines ARE IMPORTANT!!!
                p2 = p2.next; // replace p2 ptr with the next ptr
                p1Prev.next = p1; // new ptr next node connects to p1
            }
        }
        // if we are at the end of list 1, connect list 2 to end of list 1
        if(p1 == null) p1Prev.next = p2;

        // find the head to return (smallest of two)
        return head1.getVal() < head2.getVal() ? head1 : head2;
    }

    //O(n + m) time | O(n + m) space where n is the number nodes in first list
    // m is the number nodes in second list

    /**
     * essentially the same as the iterative solution, just need to keep track of the 3 ptrs
     */
    private static ListNode mergeLinkedListRecurse(ListNode head1, ListNode head2) {
        recursiveMerge(head1, head2, null);
        return head1.getVal() < head2.getVal() ? head1 : head2;
    }

    private static void recursiveMerge(ListNode p1, ListNode p2, ListNode p1Prev) {
        // reached the end of p1 but there is still p2,
        // append p2 to the end of p1
        if (p1 == null) {
            p1Prev.next = p2;
            return;
        }
        if (p2 == null) return;

        if (p1.getVal() < p2.getVal()) { // if p1 < p2, keep moving p1 ptrs forward
            recursiveMerge(p1.next, p2, p1);
        } else { // p1 > p2 so need to insert p2 into before p1 and after p1Prev
            if (p1Prev != null) p1Prev.next = p2;
            ListNode newP2 = p2.next; // save reference to p2.next
            p2.next = p1; // connect end of p2 to p1
            recursiveMerge(p1, newP2, p2);
        }
    }
}
