package LinkedList;

public class DeleteMiddleNodeLinkedList {
    public static void main(String[] args) {

    }

    private static ListNode deleteAtMiddle(ListNode head, int position) {
        if (position == 1)
            return head == null ? head : head.next;

        // start both pointers at head, cur and prev
        ListNode cur = head;
        ListNode prev = cur;

        int count = 0;
        while (cur != null) {
            // increment counter first
            count ++;
            if (count == position) {
                // at the position, jump over the next node to delete
                prev.next = cur.next;
                // disconnect the cur node
                cur.next = null;
            } else {
                // traverse the list
                prev = cur;
                cur = cur.next;
            }
        }
        return head;
    }


}
