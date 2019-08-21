package CTCI.LinkedList;

import java.util.Stack;

public class IsListPalindrome {
    public static void main(String[] args) {
        System.out.println(isListPalindromeWrong(ListNodeUtils.generatePalindrome()));

    }

    // this is wrong because needs a stack for the second half
    private static boolean isListPalindromeWrong(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        System.out.println(slow.val);
        ListNode cur = head;

        while (slow != null) {
            if (slow.val != cur.val)
                return false;
            slow = slow.next;
            cur = cur.next;
        }

        return true;
    }

    private static boolean isPalindromeStack(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        Stack<Integer> stack = new Stack<>();

        while (fast != null && fast.next != null) {
            stack.push(slow.val);
            slow = slow.next;
            fast = fast.next.next;
        }

        // if fast lands on a non-null node then there is odd number of elements
        // has odd number of elements, so skip the middle
        if (fast != null) {
            slow = slow.next;
        }

        while (slow != null) {
            int top = stack.pop().intValue();
            if (top != slow.val) return false;
            slow = slow.next;
        }

        return true;
    }

    // reverse linked list and compare to original
    private static ListNode reverseAndClone(ListNode node) {
        ListNode head = null;
        while (node != null) {
            ListNode n = new ListNode(node.val);
            n.next = head;
            head = n;
            node = node.next;
        }
        return head;
    }

    private static boolean isEqual(ListNode one, ListNode two) {
        while (one != null && two != null) {
            if (one.val != two.val) {
                return false;
            }

            one = one.next;
            two = two.next;
        }

        return one == null && two == null;
    }
}
