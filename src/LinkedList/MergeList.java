package LinkedList;

public class MergeList {
    public static void main(String[] args) {
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        ListNode head2 = new ListNode(5);
        head2.next = new ListNode(6);
        head2.next.next = new ListNode(7);
        head2.next.next.next = new ListNode(8);
        BuildLinkedList.printList(mergeTwoLists(head1,head2));

        System.out.println();

        ListNode head3 = new ListNode(1);
        head3.next = new ListNode(2);
        head3.next.next = new ListNode(3);
        head3.next.next.next = new ListNode(4);
        ListNode head4 = new ListNode(5);
        head4.next = new ListNode(6);
        head4.next.next = new ListNode(7);
        head4.next.next.next = new ListNode(8);
        BuildLinkedList.printList(mergeTwoLists2(head3,head4));
    }

    /**
     * Build up the resulting list without caching anything
     * @param l1
     * @param l2
     * @return
     */
    private static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;  // if l1 is at the end of the list, return node2
        if (l2 == null) return l1;  // if l2 is at the end of the list, return node1

        // take the lower of the two nodes and iterate on that node's next
        if (l1.getVal() < l2.getVal()) {  // if l1 < l2, merge l1.next
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {                         // else l1 >= l2 merge l2.next
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    private static ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        ListNode newHead;
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        // take the lower of the two nodes and add the node to the newHead next
        if (l1.getVal() <= l2.getVal()) {
            newHead = l1;
            newHead.next = mergeTwoLists2(l1.next,l2);
        } else {
            newHead = l2;
            newHead.next = mergeTwoLists2(l1,l2.next);
        }

        return newHead;
    }
}
