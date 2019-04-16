package LinkedList;

public class DeleteTailNodeCircularLinkedList {
    public static void main(String[] args) {

    }

    private static ListNode deleteAtTail(ListNode head) {
        ListNode currentNode = head;
        ListNode prevNode = head;

        while (currentNode.next != head) {
            prevNode = currentNode;
            currentNode = currentNode.next;
        }

        prevNode.next = head;
        currentNode.next = null;
        return head;
    }
}
