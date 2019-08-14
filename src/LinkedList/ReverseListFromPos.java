package LinkedList;

public class ReverseListFromPos {
    public static void main(String[] args) {
        BuildLinkedList.printList(reverseBetween(BuildLinkedList.returnNewList(),2,4));
        BuildLinkedList.printList(revereBetween2(BuildLinkedList.returnNewList(),2,4));
    }

    private static ListNode revereBetween2(ListNode head, int listStartReverse, int listEndReverse) {
        if (head == null) return null;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        for (int i = 0; i < listStartReverse-1; i++) {
            pre = pre.next;
        }

        // pointer to the beginning of a sub-list that will be reversed
        ListNode start = pre.next;

        // pointer to a node that will be reversed
        ListNode then = start.next;

        // 1 - 2 -3 - 4 - 5 ; m=2; n =4 ---> pre = 1, start = 2, then = 3
        // dummy-> 1 -> 2 -> 3 -> 4 -> 5
        for (int i = 0; i < listEndReverse - listStartReverse; i++) {
            start.next = then.next;
            then.next = pre.next;
            pre.next = then;
            then = start.next;
        }

        // first reversing : dummy->1 - 3 - 2 - 4 - 5; pre = 1, start = 2, then = 4
        // second reversing: dummy->1 - 4 - 3 - 2 - 5; pre = 1, start = 2, then = 5 (finish)
        return dummy.next;
    }
    private static ListNode reverseBetween(ListNode head, int start, int end) {

        // Empty list
        if (head == null) {
            return null;
        }

        // Move the two pointers until they reach the proper starting point
        // in the list.
        ListNode cur = head, prev = null;
        while (start > 1) {
            prev = cur;
            cur = cur.next;
            start--;
            end--;
        }

        // The two pointers that will fix the final connections.
        ListNode con = prev, tail = cur;

        // Iteratively reverse the nodes until n becomes 0.
        ListNode third = null;
        while (end > 0) {
            third = cur.next;
            cur.next = prev;
            prev = cur;
            cur = third;
            end--;
        }

        // Adjust the final connections as explained in the algorithm
        if (con != null) {
            con.next = prev;
        } else {
            head = prev;
        }

        tail.next = cur;
        return head;
    }
}
