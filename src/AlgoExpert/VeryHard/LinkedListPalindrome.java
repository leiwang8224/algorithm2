package AlgoExpert.VeryHard;

import LinkedList.ListNode;

public class LinkedListPalindrome {
    // O(n) time | O(n) space
    public boolean linkedListPalindrome(ListNode head) {
        LinkedListInfo isPalindromeResults = isPalindrome(head, head);
        return isPalindromeResults.prevNodesAreEqual;
    }

    private LinkedListInfo isPalindrome(ListNode leftNode, ListNode rightNode) {
        // base case: no comparison is made yet so just pass true for prev nodes are equal
        if (rightNode == null) return new LinkedListInfo(true, leftNode);

        // move the rightNode ptr to the end of the list
        LinkedListInfo recursiveCallResults = isPalindrome(leftNode, rightNode.next);

        // get the results from the recursion
        ListNode leftNodeToCompare = recursiveCallResults.leftNodeToCompare;
        boolean outerNodesAreEqual = recursiveCallResults.prevNodesAreEqual;

        // need to check if previous nodes checked are equal AND current left and right node are equal
        boolean recursiveIsEqual = outerNodesAreEqual && (leftNodeToCompare.getVal() == rightNode.getVal());
        // move left Node forward
        ListNode nextLeftNodeToCompare = leftNodeToCompare.next;

        // propagate the result (prev nodes are equal and the next left node to compare)
        return new LinkedListInfo(recursiveIsEqual, nextLeftNodeToCompare);
    }

    class LinkedListInfo {
        // prev nodes checked is equal
        boolean prevNodesAreEqual;
        ListNode leftNodeToCompare;
        LinkedListInfo(boolean outerNodesAreEqual, ListNode leftNodeToCompare) {
            this.prevNodesAreEqual = outerNodesAreEqual;
            this.leftNodeToCompare = leftNodeToCompare;
        }
    }

    // O(n) time | O(1) space
    public boolean LinkedListPalinReverse(ListNode head) {
        ListNode slowNode = head;
        ListNode fastNode = head;

        while (fastNode != null && fastNode.next != null) {
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
        }

        ListNode reversedSecondHalfNode = reverseLinkedList(slowNode);
        ListNode firstHalfNode = head;

        // iterate from the reversed list head
        // for odd number of nodes the last node examined will be the same node
        // therefore the center node does not matter(as long as both halves of the linked list are same)
        while (reversedSecondHalfNode != null) {
            if (reversedSecondHalfNode.getVal() != firstHalfNode.getVal()) return false;
            reversedSecondHalfNode = reversedSecondHalfNode.next;
            firstHalfNode = firstHalfNode.next;
        }

        return true;
    }

    private ListNode reverseLinkedList(ListNode head) {
        ListNode prevNode = null;
        ListNode curNode = head;
        while (curNode != null) {
            ListNode nextNode = curNode.next;
            curNode.next = prevNode;
            prevNode = curNode;
            curNode = nextNode;
        }
        return prevNode;
    }
}
