package LinkedList;

public class RemoveNthFromEnd {
    public static void main(String[] args) {
        BuildLinkedList.printList((BuildLinkedList.returnNewList()));
        BuildLinkedList.printList(removeNthFromEnd(BuildLinkedList.returnNewList(),3));
        BuildLinkedList.printList(removeNthFromEnd2(BuildLinkedList.returnNewList(),3));
    }

    private static ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || head.next == null) return null;

        int length = 0, nth = 0, pos = 0;
        ListNode cur = head, prev = null, newHead = head;

        //get list length
        while (cur != null) {
            length++;
            cur = cur.next;
        }

        //get nth node position in the list
        nth = length-n+1;

        //remove nth node
        cur = head; // reset head to beginning
        while (cur!= null) {
            pos++;
            //nth node found
            if (pos == nth) {
                //remove nth node
                if (pos == 1) {
                    //case head
                    head = cur.next;
                    cur.next = null;
                    newHead = head;
                } else if (pos == length) {
                    //case tail
                    prev.next = null;
                } else {
                    //case middle
                    prev.next = cur.next;
                    cur.next = null;
                }
                break;
            }
            prev = cur;
            cur = cur.next;
        }
        return newHead;
    }

    private static ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode temp = new ListNode(0);
        temp.next = head;
        ListNode cur = head;
        ListNode prev = temp;
        ListNode refCur = head;

        int count = 0;
        if (head != null) {
            while (count < n) {
                if (refCur == null) {
                    // n is greater than no of nodes
                    return null;
                }
                refCur = refCur.next;
                count++;
            }

            while (refCur != null) {
                prev = prev.next;
                cur = cur.next;
                refCur = refCur.next;
            }
            //cur is at n
            prev.next = prev.next.next;
            cur.next = null;
        }
        return head;
    }

    private static ListNode removeNthFromEnd3(ListNode head, int n) {
        if (head == null || n < 1) return head;

        ListNode prev = head;
        ListNode cur = head;

        while (cur.next != null) {
            if (--n < 0)
                prev = prev.next;
            cur = cur.next;
        }

        if (n<0)
            prev.next = prev.next.next;
        return (n == 1 && cur.next == null) ? head.next : head;
    }
}
