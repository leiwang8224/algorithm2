package AlgoExpert.VeryHard;

import LinkedList.ListNode;

public class ZipLinkedList {
    // O(n) time | O(1) space
    // split up the linked list into 2 parts
    // reverse the second list
    // put the two list together interweaving the two
    ListNode zipLinkedList(ListNode linkedList) {
        if (linkedList.next == null || linkedList.next.next == null) return linkedList;

        ListNode firstHalfHead = linkedList;
        ListNode secondHalfHead = splitLinkedList(linkedList);

        ListNode reverseSecondHalfHead = reverseLinkedList(secondHalfHead);

        return interweaveLinkedList(firstHalfHead, reverseSecondHalfHead);
    }

    private ListNode splitLinkedList(ListNode linkedList) {
        ListNode slowIterator = linkedList;
        ListNode fastIterator = linkedList;

        while (fastIterator != null && fastIterator.next != null) {
            slowIterator = slowIterator.next;
            fastIterator = fastIterator.next.next;
        }

        // set the second half head to be the slow ptr next node
        ListNode secondHalfHead = slowIterator.next;
        // disconnect the first half of the linkedlist from second
        slowIterator.next = null;
        return secondHalfHead;
    }

    private ListNode reverseLinkedList(ListNode linkedList) {
        ListNode prevNode = null;
        ListNode curNode = linkedList;

        while (curNode != null) {
            ListNode nextNode = curNode.next;
            curNode.next = prevNode;
            prevNode = curNode;
            curNode = nextNode;
        }
        return prevNode;
    }

    private ListNode interweaveLinkedList(ListNode linkedList1, ListNode linkedList2) {
        ListNode linkedList1Iterator = linkedList1;
        ListNode linkedList2Iterator = linkedList2;

        while (linkedList1Iterator != null && linkedList2Iterator != null) {
            // save the next ptr
            ListNode firstHalfIteratorNext = linkedList1Iterator.next;
            ListNode secondHalfIteratorNext = linkedList2Iterator.next;

            // set the next ptr to the respective other list
            // first list.next = second list
            // second list.next = first list
            linkedList1Iterator.next = linkedList2Iterator;
            linkedList2Iterator.next = firstHalfIteratorNext;

            // move ptr forward
            linkedList1Iterator = firstHalfIteratorNext;
            linkedList2Iterator = secondHalfIteratorNext;
        }
        return linkedList1;
    }


}
