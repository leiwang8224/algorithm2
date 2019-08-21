package CTCI.LinkedList;
//
//Given a linked list and a value x, partition it such that
// all nodes less than x come first, then all nodes with value
// equal to x and finally nodes with value greater than or
// equal to x. The original relative order of the nodes in
// each of the three partitions should be preserved.
// The partition must work in-place.
public class PartitionListNode {
    public static void main(String[] args) {
        //insert 3 in beginnning
        ListNode head = new ListNode(3);
        head.next = ListNodeUtils.generateList();
        //partition based on 3
        ListNodeUtils.printListNodes(partition(head,3));
    }

    private static ListNode partition(ListNode node, int x) {
        ListNode lessThanStart = null;
        ListNode lessThanEnd = null;
        ListNode greaterThanStart = null;
        ListNode greaterThanEnd = null;

        while (node != null) {
            // cache save the next node
            ListNode next = node.next;
            // disconnect the current node from the list
            node.next = null;

            // partition the nodes based on values
            if (node.val < x) {
                if (lessThanStart == null) {
                    // first element in the lessThan list
                    lessThanStart = node;
                    lessThanEnd = lessThanStart;
                } else {
                    // lessThan list has already started
                    // connect the next and set the end to be this node
                    lessThanEnd.next = node;
                    lessThanEnd = node;
                }
            } else { // node.val >= x
                if (greaterThanStart == null) {
                    // first element in the greaterThan list
                    greaterThanStart = node;
                    greaterThanEnd = greaterThanStart;
                } else {
                    // greaterThan list has already started
                    // connect the next and set the end to be this node
                    greaterThanEnd.next = node;
                    greaterThanEnd = node;
                }
            }
            // iterate to the next node
            node = next;
        }

        // merge the less than and greater than list
        if (lessThanStart == null) return greaterThanStart;

        lessThanEnd.next = greaterThanStart;
        return lessThanStart;
    }
}
