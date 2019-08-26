package CTCI.LinkedList;

public class SumList {
    public static void main(String[] args) {
        ListNodeUtils.printListNodes(ListNodeUtils.generateList());
        System.out.println();
        ListNodeUtils.printListNodes(sumList(ListNodeUtils.generateList(),ListNodeUtils.generateList()));
    }

    private static ListNode sumList(ListNode node1, ListNode node2) {
        if (node1 == null && node2 == null) return null;
        if (node1 == null) return node2;
        if (node2 == null) return node1;

        ListNode temp = new ListNode(0);
        ListNode cur = temp;
        int carry = 0;
        while (node1 != null && node2 != null) {
            int sum = node1.val + node2.val;
            int digit = sum % 10;
            cur.next = new ListNode( digit + carry);
            carry = sum / 10;
            cur = cur.next;
            node1 = node1.next;
            node2 = node2.next;
        }

        while (node1 != null) {
            cur.next = new ListNode(node1.val);
            node1 = node1.next;
            cur = cur.next;
        }

        while (node2 != null) {
            cur.next = new ListNode(node2.val);
            node2 = node2.next;
            cur = cur.next;
        }

        if (carry > 0) cur.next = new ListNode(carry);

        return temp.next;
    }
}
