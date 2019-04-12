package LinkedList;

//Given a circular linked list, implement a method to delete its head node. Return the list's new head node.
//        *x = indicates head node
//        1->2->3->4->*1 ==> 2->3->4->*2
public class DeleteHeadNodeCircularLinkedList {
    public static void main(String[] args) {

    }

    private ListNode deleteAtHead(ListNode head) {
        if (head == null)
            return null;

        ListNode curr = head;

        // traverse to tail
        while (curr.next != head) {
            curr = curr.next;
        }

        curr.next = head.next;  // save head.next in curr.next
        head.next = null;       // delete head.next ptr
        head = curr.next;       // connect head to curr.next (skip over head ptr)
        return head;
    }
}
