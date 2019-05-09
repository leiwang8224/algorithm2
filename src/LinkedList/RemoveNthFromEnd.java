package LinkedList;

public class RemoveNthFromEnd {
    public static void main(String[] args) {
        BuildLinkedList.printList((BuildLinkedList.returnNewList()));
        BuildLinkedList.printList(removeNthFromEnd(BuildLinkedList.returnNewList(),3));
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
        nth = length-n;

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
}
