package CTCI.LinkedList;

public class ReturnKthToLast {
    public static void main(String[] args) {
        ListNodeUtils.printListNodes(ListNodeUtils.generateList());
        System.out.println();
        System.out.println(getKthToLastWrong(ListNodeUtils.generateList(),2).val);
        System.out.println();
        printKthToLast(ListNodeUtils.generateList(),2);
        System.out.println();
        System.out.println(kthToLast(ListNodeUtils.generateList(),2).val);
        System.out.println();
        System.out.println(nthToLastTwoPointerMethod(ListNodeUtils.generateList(),2).val);
    }

    // returns Kth element from start, not last
    private static ListNode getKthToLastWrong(ListNode head, int k) {
        // forward recursion
        if (head == null || k == 0) return head;

        return getKthToLastWrong(head.next, k-1);

    }

    private static int printKthToLast(ListNode head, int k) {
        // backward recursion
        if (head == null) return 0;

        int index = printKthToLast(head.next, k) + 1;

        if (index == k) {
            System.out.println("kth element is " + head.val );
        }
        return  index;
    }

    static class Index {
        public int value = 0;
    }

    private static ListNode kthToLast(ListNode head, int k) {
        Index idx = new Index();
        return kthToLast(head, k, idx);
    }

    private static ListNode kthToLast(ListNode head, int k, Index idx) {
        if (head == null) return null;

        ListNode node = kthToLast(head.next, k, idx);

        idx.value++;

        // at kth node
        if (idx.value == k) return head;

        // passing the next node to the parameter
        return node;
    }

    private static ListNode nthToLastTwoPointerMethod(ListNode head, int k) {
        ListNode p1 = head;
        ListNode p2 = head;

        // move p1 k nodes into the list
        for (int i = 0; i < k; i++) {
            if (p1 == null) return null; // out of bounds
            p1 = p1.next;
        }

        // move them at the same pace, when p1 hits the end
        // p2 will be at the right element
        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p2;
    }


}
