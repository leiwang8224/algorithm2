package CTCI.LinkedList;

// delete the middle node, not necessary the exact middle but definitely not the first node or the last.
public class DeleteMiddleNode {
    public static void main(String[] args) {
        ListNodeUtils.printListNodes(ListNodeUtils.generateList());
        System.out.println();
        ListNode head = ListNodeUtils.generateList();
        ListNode cur = head;
        cur = cur.next;
        cur = cur.next;
        deleteMiddleNode(cur);
        ListNodeUtils.printListNodes(head);
    }

    private static void deleteMiddleNode(ListNode middleNode) {
        middleNode.val = middleNode.next.val;
        middleNode.next = middleNode.next.next;
    }
}
