package CTCI.LinkedList;

public class FindLoopBeginning {
    public static void main(String[] args) {
         ListNode loop = ListNodeUtils.generateList();
         ListNode cur = loop;
         while (cur.next != null) {
             cur = cur.next;
         }
         cur.next = loop;

        System.out.println(findBeginning(loop).val);

    }

    // set fast and slow pointers to move until they both meet(coincide).
    // if fast or fast.next is null return null (there is no loop)
    // RESET: move slow pointer to head and move till they meet again
    // return on the node they met
    private static ListNode findBeginning(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        // find meeting point
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;
        }

        // error check, there is no meeting point, and therefore no loop
        if (fast == null || fast.next == null) return null;

        // move slow to head, keep fast at meeting point.
        // each are k steps from the loop start.
        // if they move at the same pace they must meet at loop start
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        // both now point to the start of the loop
        return fast;
    }
}
