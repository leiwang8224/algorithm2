package LinkedList;

import java.util.HashMap;

/**
 * Created by leiwang on 3/15/18.
 */
public class BuildLinkedList {
    public static void main(String args[]) {
        printList(reverseList(returnNewList(),null));
        reOrderList(returnNewList());
        ListNode[] res = splitListToParts(returnNewList(),5);
        for (ListNode node : res)
            System.out.println(node.getVal());

        System.out.println("isPalindrome " + isPalindrome(returnNewList()));

        printList(reverseListIterative(returnNewList()));
        printList(removeAllNodeContaining(returnNewList(),5));
        printList(addNode(returnNewList(),6));
        printList(insertNode(returnNewList(),5,4));
        printList(makeOddEvenList(returnNewList()));
        printList(swapPairs(returnNewList()));
        printList(partitionList(returnNewList(),3));
        printList(sortList(returnNewList()));
        printList(removeDupNode(insertNode(returnNewList(),5,4)));
        printList(removeDupNode2(insertNode(returnNewList(),5,4)));
        printList(removeDupNode3(insertNode(returnNewList(),5,4)));
        printList(rotateKPlaces(returnNewList(),2));
    }

    public static ListNode returnNewList() {
        ListNode head = new ListNode(0);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next = new ListNode(5);
        return head;
    }

    public static void printList(ListNode head) {
        while (head != null) {
            System.out.println(head.getVal());
            head = head.next;
        }
    }

    public static void printCircularList(ListNode head) {
        ListNode cur = head;
        while(true) {
            System.out.println(cur.getVal());
            if (cur.next == head) {
                break;
            } else {
                cur = cur.next;
            }
        }
    }

    /**
     * recursive reverse List, newHead is previous head
     *
     * @param head
     * @param newHead
     * @return
     */
    private static ListNode reverseList(ListNode head, ListNode newHead) {

        if (head != null && newHead != null)
            System.out.println("entering reverseList head = " + head.getVal() + " newHead = " + newHead.getVal());
        if (head == null)
            return newHead;
        ListNode next = head.next;
        head.next = newHead;
        if (next != null && head != null)
            System.out.println("returning with next = " + next.getVal() + " head = " + head.getVal());
        return reverseList(next, head);
    }

    private static ListNode reverseListIterative(ListNode head) {
        ListNode previous = null;
        while (head != null) {
            // save the pointer to next node
            ListNode next = head.next;
            // set next node to the new node
            head.next = previous;
            //newHead was pointing to null, now point to head
            //so we can process the next node
            previous = head;
            //now newHead is the prev node (saved)
            head = next;
        }
        // new node will be the new head in the end
        return previous;
    }

    private static boolean isPalindrome(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // slow is at midpoint
        if (fast != null) //odd number of nodes
            slow = slow.next;
        slow = reverseList(head, null);
        fast = head;
        while (slow != null) {
            if (fast.getVal() != slow.getVal()) {
                return false;
            }
            fast = fast.next;
            slow = slow.next;
        }
        return true;
    }

    private static ListNode removeAllNodeContaining(ListNode head, int val) {
        System.out.println("remove all nodes containing " + val);
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;
        ListNode cur = head;
        ListNode prev = dummyNode;
        while (cur != null) {
            if (cur.getVal() == val) {
                prev.next = cur.next;
            } else {
                prev = prev.next;
            }
            cur = cur.next;
        }
        return dummyNode.next;
    }

    private static ListNode addNode(ListNode head, int val) {
        System.out.println("addNode " + val);
        ListNode cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = new ListNode(val);
        return head;
    }

    /**
     * Insert node given the node and position in the linkedlist
     * @param head
     * @param val
     * @param pos
     * @return
     */
    private static ListNode insertNode(ListNode head, int val, int pos) {
        System.out.println("insertNode value " + val + " at pos " + pos);
        ListNode newNode = new ListNode(val);
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;
        ListNode cur = dummyNode;

        for (int i = 0; i < pos; i++) {
            cur = cur.next;
        }
        // link up the next pointer
        newNode.next = cur.next;
        // set link from the prev node
        cur.next = newNode;

        return dummyNode.next;
    }

    /**
     * Split list to parts
     *
     * @param root
     * @param k
     * @return
     */
    public static ListNode[] splitListToParts(ListNode root, int k) {
        System.out.println("entering splitListToParts");
        ListNode[] parts = new ListNode[k];
        int len = 0;

        // 1. find length of the linked list
        for (ListNode node = root; node != null; node = node.next)
            len++;
        // 2. divide into parts
        int n = len / k, r = len % k;
        // n : minimum guaranteed part size;
        // r : extra nodes spread to the first r parts;
        ListNode node = root, prev = null;
        // for each group
        for (int i = 0; node != null && i < k; i++, r--) {
            parts[i] = node;
            // n + remainder, inc j
            // for each one in the group do the following
            for (int j = 0; j < n + (r > 0 ? 1 : 0); j++) {
                prev = node;
                node = node.next;
            }
            prev.next = null;
        }
        return parts;
    }

    public static ListNode makeOddEvenList(ListNode head) {
        System.out.println("entering makeOddEvenList");
        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;
        ListNode dummyNode = new ListNode(0);

        while (even != null && even.next != null) {
            odd.next = odd.next.next;
            even.next = even.next.next;
            odd = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return odd;
    }

    /**
     * Use dummy pointer
     * 1. pointer for first element, pointer for second element
     * 2. replace second with third element
     * 3. move second element to first position
     * 4. move first element is put into second pos
     * 5. move cur pointer
     *
     * @param head
     * @return
     */
    public static ListNode swapPairs(ListNode head) {
        System.out.println("entering swapPairs");
        ListNode dummy = new ListNode(0);
        // use dummy node to avoid checking conditions
        dummy.next = head;
        ListNode current = dummy;
        while (current.next != null && current.next.next != null) {
            // first element
            ListNode first = current.next;
            // second element
            ListNode second = current.next.next;
            // replace second with third element
            first.next = second.next;
            // move second element to first position
            current.next = second;
            // since current is at dummy, the first element put into second pos
            current.next.next = first;
            // move current pointer
            current = current.next.next;
        }
        return dummy.next;
    }

    public static ListNode partitionList(ListNode head, int val) {
        System.out.println("entering partitionList");
        ListNode dummy1 = new ListNode(0);
        ListNode dummy2 = new ListNode(0);

        dummy1.next = head;
        ListNode cur1 = dummy1;
        ListNode cur2 = dummy2;

        while (head != null) {
            if (head.getVal() < val) {
                cur1.next = head;
                cur1 = head;
            } else {
                cur2.next = head;
                cur2 = head;
            }
            head = head.next;
        }

        cur2.next = null;
        cur1.next = dummy2.next;
        return dummy1.next;
    }

    /**
     * Merge Sort to sort the linked list
     *
     * @param head
     * @return
     */
    public static ListNode sortList(ListNode head) {
        System.out.println("entering sortList");
        if (head == null || head.next == null)
            return head;

        // cut list to two halves
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            // keep a copy of the prev pointer in slow iterator
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // cut off the first list from the second list
        prev.next = null;

        // sort each half
        ListNode l1 = sortList(head);
        // slow is at the starting pointer of second list
        ListNode l2 = sortList(slow);

        return merge(l1, l2);

    }

    private static ListNode merge(ListNode l1, ListNode l2) {
        ListNode l = new ListNode(0);
        // need an iterator to travel through list
        ListNode p = l;

        while (l1 != null && l2 != null) {
            if (l1.getVal() < l2.getVal()) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }

        // add the rest of the list1 and list2
        if (l1 != null)
            p.next = l1;

        if (l2 != null)
            p.next = l2;

        return l.next;
    }

    /**
     * Remove all nodes with duplicate values in given linkedlist
     * @param head
     * @return
     */
    public static ListNode removeDupNode(ListNode head) {
        System.out.println("removeDupNode");
        if (head == null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        // maintain a pre for prev pointer
        ListNode pre = dummy;
        ListNode cur = head;
        while (cur != null) {
            // this while loop skips all of the duplicates and puts
            // the pointer pointing to the next distinct node
            while (cur.next != null && cur.getVal() == cur.next.getVal()) {
                cur = cur.next;
            }
            // cur moves to distinct node
            if (pre.next == cur) {
                // just advance pre if pre is before cur
                pre = pre.next;
            } else {
                // deleting of node occurs when the duplicates are
                // traversed, landing on the distinct node
                pre.next = cur.next;
            }
            cur = cur.next;
        }
        return dummy.next;
    }

    // This method creates a new linkedlist without the duplicates
    public static ListNode removeDupNode2(ListNode head) {
        System.out.println("removeDupNode2");
        if (head == null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prev = dummy;
        ListNode cur = head;

        // create new linkedlist for result
        ListNode first = dummy; // first node of the result (distinct list)

        while (cur != null && cur.next != null) {
            if (cur.getVal() != prev.getVal() && cur.getVal() != cur.next.getVal()) {
                // found distinct node, connect to tail of dummy
                first.next = cur;
                first = first.next;
            }
            prev = cur;
            cur = cur.next;
        }

        // last node is dealt independently
        if (prev.getVal() != cur.getVal()) {
            first.next = cur;
            first = first.next;
        }

        first.next = null;// the rest of the list are dups

        return dummy.next;
    }

//    Given a singly-linked list, remove duplicates in the list and
//    return head of the list. Target a worst case space complexity of O(n).
    private static ListNode removeDupNode3(ListNode head) {
        System.out.println("removeDupNode3");

        HashMap<Integer, ListNode> nodeTable = new HashMap<>();
        if (head == null || head.next == null) return head;

        ListNode curr = head;
        ListNode prev = curr;

        while (curr != null) {
            if (nodeTable.containsKey(curr.getVal())) {
                prev.next = curr.next;
                curr.next = null;
                curr = prev.next;
                if (curr == null) break; // end of list
                nodeTable.put(curr.getVal(),curr);
            } else {
                nodeTable.put(curr.getVal(),curr);
            }
            prev = curr;
            curr = curr.next;
        }
        return head;
    }

//    Given a singly linked list, rotate the linked list counter-clockwise
//    by k nodes. Where k is a given positive integer. For example, if the
//    given linked list is 10->20->30->40->50->60 and k is 4, the list
//    should be modified to 50->60->10->20->30->40. Assume that k is smaller
//    than the count of nodes in linked list.
    static ListNode rotateKPlaces(ListNode head, int k) {
        System.out.println("rotate " + k + " places");
        // note that k may be bigger than the length of list
        if (head == null || head.next == null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = dummy, slow = dummy;

        int i;
        for (i = 0; fast.next != null; i++) { // get total length
            fast = fast.next;
        }

        for (int j = i - k % i; j > 0; j--) { // goto i-k%ith node
            slow = slow.next;
        }

        fast.next = dummy.next; // do the rotation
        dummy.next = slow.next;
        slow.next = null;
        return dummy.next;
    }

    /**
     * Reorder based on L0 -> Ln -> L1 -> Ln-1 ...
     * 1. Travel to middle of the list
     * 2. Reverse second half of the list
     * 3. Reorder the whole list
     *
     * @param head
     * @return
     */
    public static void reOrderList(ListNode head) {
        System.out.println("entering reOrderList");
        ListNode p1 = head;
        ListNode p2 = head;

        // move pointer to middle of list
        while (p2.next != null && p2.next.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
        }

        // now p1 is at the middle node of the list
        // 1->2->3->4->5->6 to 1->2->3->6->5->4
        ListNode preMiddle = p1;
        ListNode preCurrent = p1.next;
        while (preCurrent.next != null) {
            ListNode current = preCurrent.next;
            preCurrent.next = current.next;
            current.next = preMiddle.next;
            preMiddle.next = current;
        }

        // start reorder 1 by 1
        // 1->2->3->4->5->6 to 1->6->2->5->3->4
        p1 = head;
        p2 = preMiddle.next;
        while (p1 != preMiddle) {
            preMiddle.next = p2.next;
            p2.next = p1.next;
            p1.next = p2;
            p1 = p2.next;
            p2 = preMiddle.next;
        }
    }


}