package LinkedList;

public class MergeList {
    public static void main(String[] args) {

    }

    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;  // if l1 is at the end of the list, return node2
        if (l2 == null) return l1;  // if l2 is at the end of the list, return node1

        if (l1.getVal() < l2.getVal()) {  // if l1 < l2, merge l1.next
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {                         // else l1 >= l2 merge l2.next
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }
}
