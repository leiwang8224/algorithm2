package AlgoExpert.Hard;


import LinkedList.ListNode;

public class ReverseLinkedList {
    public static void main(String[] args) {

    }

    // O(n) time | O(1) space
    // trick to this problem is to use 3 pointers
    // start out first ptr at null, second ptr at head, third ptr at second.next
    // while middle ptr not null, iterate
    private static ListNode reverseLinkedList(ListNode head) {
        ListNode prevNode = null;
        ListNode curNode = head;
        while (curNode != null) {
            // save ptr to next node
            ListNode nextNode = curNode.next;  // p3 = p2.next
            // reverse cur node and prev node
            curNode.next = prevNode;           // p2.next = p1
            // move prev node to point to cur node
            prevNode = curNode;                 // p1 = p2
            // move cur node up to next node
            curNode = nextNode;                 // p2 = p3
        }
        // the first node will land on the head of the new linked list
        return  prevNode;
    }

    private static ListNode reverseLinkedListWithP(ListNode head) {
        ListNode p1 = null;
        ListNode p2 = head;
        while (p2 != null) {
            // save ptr to next node
            ListNode p3 = p2.next;  // p3 = p2.next
            // reverse cur node and prev node
            p2.next = p1;           // p2.next = p1
            // move prev node to point to cur node
            p1 = p2;                 // p1 = p2
            // move cur node up to p3, notice it's not the next node since next node is p1 (p2.next = p1)
            p2 = p3;                 // p2 = p3
        }
        // the first node will land on the head of the new linked list
        return  p1;
    }
}
