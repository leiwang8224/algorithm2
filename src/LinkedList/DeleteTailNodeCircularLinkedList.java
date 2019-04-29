package LinkedList;

public class DeleteTailNodeCircularLinkedList {
    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next.next = head;

        BuildLinkedList.printCircularList(deleteAtTail(head));
    }

    private static ListNode deleteAtTail(ListNode head) {
        if (head == null || head.next == head) return null;
        ListNode currentNode = head.next;
        ListNode prevNode = head;

        while (currentNode.next != head) {
            prevNode = prevNode.next;
            currentNode = currentNode.next;
        }

        prevNode.next = head;
        currentNode.next = null;
        return head;
    }
}
