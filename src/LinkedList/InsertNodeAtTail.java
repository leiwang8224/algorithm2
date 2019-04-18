package LinkedList;

public class InsertNodeAtTail {
    public static void main(String[] args) {

    }

    private ListNode insertAtTail(ListNode head, int data) {
        ListNode newNode = new ListNode(data);
        ListNode cur = head;
        newNode.next = newNode; // point to self after creation

        if (head == null) {
            head = newNode;
        } else {
            while (cur.next != head) {
                cur = cur.next;
            }
            newNode.next = head;
            cur.next = newNode;
        }
        return head;
    }
}
