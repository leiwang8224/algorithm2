package CTCI.LinkedList;

public class RemoveDups {
    public static void main(String[] args) {
        ListNodeUtils.printListNodes(removeDups(ListNodeUtils.generateList()));
    }

    private static ListNode removeDups(ListNode head) {
        ListNode temp = new ListNode(0);
        temp.next = head;
        ListNode cur = temp;
        while (cur.next != null) {
            if (cur.val != cur.next.val) {
                cur = cur.next;
            } else {
                cur.next = cur.next.next;
                cur = cur.next;
            }
        }
        return temp.next;
    }

    private static ListNode removeDupsRunner(ListNode head) {
        if (head == null) return null;
        ListNode pre = head;
        ListNode cur = pre.next;

        while (cur != null) {
            // look backwards for dups
            ListNode runner = head;
            while (runner != cur) {
                if (runner.val == cur.val) {
                    ListNode temp = cur.next;
                    pre.next = temp;
                    cur = temp;
                    // we know we can't have more than one dup preceding
                    // the element since it would have been removed earlier
                    break;
                }
                runner = runner.next;
            }

            /* If runner == current, then we didn't find any duplicate
             * elements in the previous for loop.  We then need to
             * increment current.
             * If runner != current, then we must have hit the �break�
             * condition, in which case we found a dup and current has
             * already been incremented.*/
            if (runner == cur) {
                pre = cur;
                cur = cur.next;
            }
            
        }
        return head;
    }
}
