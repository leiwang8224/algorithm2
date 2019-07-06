package LinkedList;

/**
 * Created by leiwang on 4/2/18.
 */
public class FindCyclesIntersection {
    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);
        ListNode intersectionNode = new ListNode(3);
        head.next.next.next = intersectionNode;
        ListNode intersectionNode2 = new ListNode(5);
        head.next.next.next.next = intersectionNode2;
        ListNode intersectionNode3 = new ListNode(6);
        head.next.next.next.next.next = intersectionNode3;

        ListNode head2 = new ListNode(2);
        head2.next = new ListNode(3);
        head2.next.next = new ListNode(4);
        head2.next.next.next = intersectionNode;
        head2.next.next.next.next = intersectionNode2;
        head2.next.next.next.next.next = intersectionNode3;

        System.out.println("intersection node = " + getIntersectionNode(head, head2).getVal());
    }



    private static int getLength(ListNode list) {
        int len = 0;
        ListNode ptr = list;
        while (ptr != null) {
            len++;
            ptr = ptr.next;
        }
        return len;
    }

    private static ListNode getIntersectionNode(ListNode head1, ListNode head2) {
        int len1 = getLength(head1);
        int len2 = getLength(head2);
        System.out.println("length1 = " + len1 + " length2 = " + len2);

        while (len1 > len2) {
            head1 = head1.next;
            len1--;
        }

        while (len1 < len2) {
            head2 = head2.next;
            len2--;
        }

        // find intersection until end
        while (head1 != head2) {
            head1 = head1.next;
            head2 = head2.next;
        }
        return head1;
    }


}
