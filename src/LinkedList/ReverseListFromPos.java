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
        ListNode beforeStart = dummy;

        // traverse to the position right before the start position
        for (int i = 0; i < listStartReverse-1; i++) {
            beforeStart = beforeStart.next;
        }

        // pointer to the beginning of a sub-list that will be reversed
        ListNode start = beforeStart.next;

        // pointer to a node that will be reversed
        ListNode nextOfStart = start.next;

        // calculate the length for reversal
        int lengthForReverse = listEndReverse - listStartReverse;

        // 1 - 2 - 3 - 4 - 5 ; start=2; end=4 ---> pre = 1, start = 2, then = 3
        // dummy -> 1 -> 2 -> 3 -> 4 -> 5
        for (int i = 0; i < lengthForReverse; i++) {
            start.next = nextOfStart.next;
            nextOfStart.next = beforeStart.next;
            beforeStart.next = nextOfStart;
            nextOfStart = start.next;
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
