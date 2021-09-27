package AlgoExpert.Hard;


import LinkedList.ListNode;

/**
 * Need 4 pointers:
 * - old Head
 * - old Tail
 * - new Head
 * - new Tail
 * new Tail position = len - offset for k > 0, offset for k < 0
 * newHead = newTail.next
 */
public class ShiftLinkedLIst {
    //O(n) time | O(1) space
    public static ListNode shiftListNodeSol(ListNode head, int k) {
        // IMPORTANT to count the first node in counting the length of list
        int listLen = 1;
        ListNode oldTail = head;
        // find length of list
        while (oldTail.next != null) {
            oldTail = oldTail.next;
            listLen++;
        }

        // calculate offset using modulus
        int offset = Math.abs(k) % listLen;
        if (offset == 0) return head;
        // tail position calculated if k > 0 (count offset from end of list)
        // else count from beginning of list
        int newTailPosition = k > 0 ? listLen - offset : offset;

        // traverse to new tail position (len-k position away if k>0 or k position away if k<0)
        ListNode newTail = head;
        // NOTE traversal for linkedlist position starts at 1 (taking account of first node)
        for (int i = 1; i < newTailPosition; i++) {
            newTail = newTail.next;
        }

        // newTail.next = newHead (CRUCIAL LINE)
        ListNode newHead = newTail.next;
        newTail.next = null;
        oldTail.next = head;
        return newHead;
    }

    public static ListNode shiftListNode(ListNode head, int k) {
        // ListNode newHeadNode = null;
        ListNode ptrFindLen = head;
        int len = 0;
        while (ptrFindLen != null) {
            ptrFindLen = ptrFindLen.next;
            len++;
        }
        k = k % len;
        ListNode slowPtr = head;
        ListNode fastPtr = head;
        ListNode newHeadNode = null;
        if (k > 0) {
            for (int index = 0; index < k; index++) {
                fastPtr = fastPtr.next;
            }
            if (fastPtr == null) return head;

            while (fastPtr.next != null) {
                slowPtr = slowPtr.next;
                fastPtr = fastPtr.next;
            }
            // save new head node before we lose it
            newHeadNode = slowPtr.next;

            // disconnect the slow ptr
            slowPtr.next = null;
            // traverse to end of the list and connect with old head
            fastPtr = newHeadNode;
            while (fastPtr.next != null) {
                fastPtr = fastPtr.next;
            }
            fastPtr.next = head;
            return newHeadNode;
        } else if (k < 0) {
            k = -k;
            // why k - 1? because we want to be at the node before the new head node
            for (int index = 0; index < k - 1; index++) {
                slowPtr = slowPtr.next;
            }
            newHeadNode = slowPtr.next;
            // put fast ptr at the end of list and connect with old head
            while (fastPtr.next != null) {
                fastPtr = fastPtr.next;
            }
            fastPtr.next = head;
            slowPtr.next = null;

            return newHeadNode;
        } else {
            return head;
        }
    }

    public static ListNode shiftLinkedListMySol(ListNode head, int k) {
        ListNode oldEnd = head;
        int len = 1;
        while (oldEnd.next != null) {
            oldEnd = oldEnd.next;
            len++;
        }

        int offset = 0;
        if (k > 0) {
            offset = len - (k % len);
        } else {
            offset = Math.abs(k) % len;
        }

        if (offset == 0 || offset == len) return head;

        ListNode newEnd = head;
        for (int i = 1; i < offset; i++) {
            newEnd = newEnd.next;
        }

        ListNode newStart = newEnd.next;
        oldEnd.next = head;
        newEnd.next = null;


        // Write your code here.
        return newStart;
    }
}
