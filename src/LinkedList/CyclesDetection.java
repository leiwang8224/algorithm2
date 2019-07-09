package LinkedList;

/**
 * Created by leiwang on 4/2/18.
 */
public class CyclesDetection {
    public static void main(String[] args) {

        System.out.println("list with cycle");
//        BuildLinkedList.printList(generateCycleList()); this will go forever
        System.out.println(isCyclic(BuildLinkedList.returnNewList()));
        System.out.println(isCyclic(generateCycleList()));
        System.out.println("detectAndRemoveLoop without cycle" +detectAndRemoveLoop(BuildLinkedList.returnNewList()));
        System.out.println("detectAndRemoveLoop with cycle");
        BuildLinkedList.printList(detectAndRemoveLoopOptimized(generateCycleList()));
        System.out.println("detectAndRemoveLoop with cycle Floyd");
        BuildLinkedList.printList(removeLoopFloyd(generateCycleList(),null));

    }

    private static boolean isCyclic(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        // slow or fast could land on null node
        while (slow != null && fast != null) {
            // slow move 1 node
            slow = slow.next;
            // fast move 2 nodes if fast.next is not null
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                break;
            }

            // OR:
            // if (fast.next == null) break;
            // else fast = fast.next.next;

            // if slow and fast meet, there is a loop
            if (slow == fast)
                return true;

        }
        return false;
    }

    private static ListNode generateCycleList() {
        ListNode head = new ListNode(0);
        ListNode loopNode = new ListNode(0);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = loopNode;
        head.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next.next = loopNode;
        return head;
    }

    private static int detectAndRemoveLoop(ListNode node) {
        ListNode slow = node, fast = node;
        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            // if slow and fast meet at same point then loop is
            // present
            if (slow == fast) {
                removeLoop(slow, node);
                return 1;
            }
        }
        return 0;
    }

    private static void removeLoop(ListNode loop, ListNode curr) {
        ListNode ptr1 = null, ptr2 = null;

        // set ptr to the beginning of the Linked List and
        // move it one by one to find the first node which
        // is part of the linked list
        ptr1 = curr;
        while (true) {
            // start a pointer from loop_node and check
            // if it reaches ptr2
            ptr2 = loop;
            while (ptr2.next != loop && ptr2.next != ptr1) {
                ptr2 = ptr2.next;
            }

            // if ptr2 reached ptr1 then there is a loop. So
            // break loop
            if (ptr2.next == ptr1) {
                break;
            }

            // if ptr2 didn't reach ptr1 then try the next node
            // after ptr1
            ptr1 = ptr1.next;
        }

        // After the end of loop ptr2 is the last node of the loop
        // make next of ptr2 as NULL
        ptr2.next = null;
    }

    /************** Floyd's cycle detection algorithm *************/
    private static ListNode removeLoopFloyd(ListNode loop, ListNode head) {
        ListNode ptr1 = loop;
        ListNode ptr2 = loop;

        //Count the number of nodes in loop
        int k = 1, i;
        while (ptr1.next != ptr2) {
            ptr1 = ptr1.next;
            k++;
        }

        // fix one pointer to head
        ptr1 = head;

        // and the other pointer to k nodes after head
        ptr2 = head;
        for (i = 0; i < k; i ++) {
            ptr2 = ptr2.next;
        }

        // move both ptrs at the same pace,
        // they will meet at loop starting node
        while (ptr2 != ptr1) {
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }

        // get pointer to the last node
        ptr2 = ptr2.next;
        while (ptr2.next != ptr1) {
            ptr2 = ptr2.next;
        }

        // set the next node of the loop ending node
        // to fix the loop
        ptr2.next = null;

        return head.next;
    }

    private static ListNode detectAndRemoveLoopOptimized(ListNode node) {
        // if list is empty or has only one node
        // without loop
        if (node == null || node.next == null) {
            return null;
        }
        ListNode head = node;
        ListNode slow = node, fast = node;

        // Move slow and fast 1 and 2 steps
        // ahead respectively
        slow = slow.next;
        fast = fast.next.next;

        // search for loop using slow and fast ptrs
        while (fast != null && fast.next != null) {
            if (slow == fast)
                break;

            slow = slow.next;
            fast = fast.next.next;
        }

        // if loop exists
        if (slow == fast) {
            slow = node;
            while (slow.next != fast.next) {
                slow = slow.next;
                fast = fast.next;
            }

            // since fast.next is the looping point
            fast.next = null;  // remove loop
        }

        return head;
    }
}
