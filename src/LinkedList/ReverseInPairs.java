package LinkedList;

public class ReverseInPairs {
    public static void main(String[] args) {
        BuildLinkedList.printList(BuildLinkedList.returnNewList());
        ListNode reversedHead = reverseInPairs(BuildLinkedList.returnNewList());
        BuildLinkedList.printList(reversedHead);
    }

    private static ListNode reverseInPairs(ListNode head) {
        ListNode cur = head;

        // traverse the list only if there are at least two nodes left
        while (cur != null && cur.next != null) {
            // swap data between current and next node
            swap(cur, cur.next);
            cur = cur.next.next;
        }
        return head;
    }

    private static void swap(ListNode cur, ListNode next) {
        int temp = cur.getVal();
        cur.setVal(next.getVal());
        next.setVal(temp);
    }
}
