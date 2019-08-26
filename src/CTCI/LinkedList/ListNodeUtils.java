package CTCI.LinkedList;

public class ListNodeUtils {
    static ListNode generateList() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next = new ListNode(5);
        return head;
    }

    static void printListNodes(ListNode head) {
        while (head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }
    }

    static ListNode generatePalindrome() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(2);
        head.next.next.next.next = new ListNode(1);
        return head;
    }

    static ListNode convertArrayToListNodes(int[] array) {
        ListNode temp = new ListNode(0);
        ListNode cur = temp;

        for (int num : array) {
            cur.next = new ListNode(num);
            cur = cur.next;
        }

        return temp.next;
    }
}
