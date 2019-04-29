package LinkedList;

//Given a circular linked list, implement a method to delete its head node. Return the list's new head node.
//        *x = indicates head node
//        1->2->3->4->*1 ==> 2->3->4->*2
public class DeleteHeadNodeCircularLinkedList {
    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next.next = head;

        BuildLinkedList.printCircularList(deleteAtHead(head));
    }

    private static ListNode deleteAtHead(ListNode head) {
        if (head == null)
            return null;

        ListNode curr = head;

        // traverse to tail
        while (curr.next != head) {
            curr = curr.next;
        }

        // there are 2 frames of references, the curr is a pointer pointing to the node before head
        // set the curr.next pointer to head.next
        curr.next = head.next;  // save head.next in curr.next
        head.next = null;       // delete head.next ptr
        head = curr.next;       // connect head to curr.next (skip over head ptr)
        return head;
    }
}
