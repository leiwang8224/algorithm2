package AlgoExpert.VeryHard;

import LinkedList.ListNode;

public class rearrangeLinkedList {
    // O(n) time | O(1) space where n is the number of nodes in the linked list
    public ListNode rearrangeLinkedList(ListNode head, int k) {
        ListNode smallerListHead = null;
        ListNode smallerListTail = null;
        ListNode equalListHead = null;
        ListNode equalListTail = null;
        ListNode greaterListHead = null;
        ListNode greaterListTail = null;

        ListNode ptr = head;
        while (ptr != null) {
            if (ptr.getVal() < k) {
                LinkedListPair smallerList = growLinkedListWithNewNode(smallerListHead,
                                                            smallerListTail,
                                                            ptr);
                smallerListHead = smallerList.head;
                smallerListTail = smallerList.tail;
            } else if (ptr.getVal() > k) {
                LinkedListPair greaterList = growLinkedListWithNewNode(greaterListHead,
                                                            greaterListTail,
                                                            ptr);
            } else {
                LinkedListPair equalList = growLinkedListWithNewNode(equalListHead,
                                                                    equalListTail,
                                                                    ptr);
                equalListHead = equalList.head;
                equalListTail = equalList.tail;
            }
            ListNode prevNode = ptr;
            ptr = ptr.next;
            prevNode.next = null; // overwrite the next node
        }

        LinkedListPair firstPair =
                connectLinkedLists(smallerListHead,
                                    smallerListTail,
                                    equalListHead,
                                    equalListTail);
        LinkedListPair finalPair =
                connectLinkedLists(firstPair.head,
                                    firstPair.tail,
                                    greaterListHead,
                                    greaterListTail);
        return finalPair.head;
    }

    class LinkedListPair {
        ListNode head;
        ListNode tail;
        LinkedListPair(ListNode head, ListNode tail) {
            this.head = head;
            this.tail = tail;
        }
    }

    LinkedListPair connectLinkedLists(ListNode headOne,
                                      ListNode tailOne,
                                      ListNode headTwo,
                                      ListNode tailTwo) {
        ListNode newHead = headOne == null ? headTwo : headOne;
        ListNode newTail = tailTwo == null ? tailOne : tailTwo;

        if (tailOne != null) tailOne.next = headTwo;

        return new LinkedListPair(newHead, newTail);
    }

    LinkedListPair growLinkedListWithNewNode(ListNode head,
                                             ListNode tail,
                                             ListNode newNode) {
        ListNode newHead = head;
        ListNode newTail = newNode;

        if (newHead == null) newHead = newNode;
        if (tail != null) tail.next = newNode;

        return new LinkedListPair(newHead, newTail);
    }
}
