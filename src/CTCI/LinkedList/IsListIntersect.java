package CTCI.LinkedList;

//Given two linked lists, determine if the two lists intersect.
//Return the ntersecting node. Note the intersection is based on
//reference not on value.
public class IsListIntersect {
    public static void main(String[] args) {
        int[] vals = {-1, -2, 0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] vals2 = {12, 14, 15};

        ListNode list1 = ListNodeUtils.convertArrayToListNodes(vals);
        ListNode list2 = ListNodeUtils.convertArrayToListNodes(vals2);

        list2.next.next = list1.next.next.next.next;

        ListNodeUtils.printListNodes(list1);
        System.out.println();
        ListNodeUtils.printListNodes(list2);

        ListNode intersection = findIntersection(list1,list2);
        System.out.println();

        ListNodeUtils.printListNodes(intersection);
    }

    static class Result {
        ListNode tail;
        int size;
        Result(ListNode tail, int size) {
            this.tail = tail;
            this.size = size;
        }
    }

    private static Result getTailAndSize(ListNode list) {
        if (list == null) return null;

        int size = 1;
        ListNode current = list;

        while (current.next != null) {
            size++;
            current = current.next;
        }

        // returns the tail node and the size of the list
        return new Result(current, size);
    }

    private static ListNode getKthNode(ListNode head, int k) {
        ListNode cur = head;
        while (k > 0 && cur != null) {
            cur = cur.next;
            k--;
        }
        return cur;
    }

    /**
     * Advance pointer on the longer list to the difference between the longer list and
     * shorter list. Then advance the pointers on both list until the nodes are the same (collision)
     * @param list1
     * @param list2
     * @return
     */
    private static ListNode findIntersection(ListNode list1, ListNode list2) {
        if (list1 == null || list2 == null) return null;

        // get tail and sizes
        Result tailAndSizeList1 = getTailAndSize(list1);
        Result tailAndSizeList2 = getTailAndSize(list2);

        // if different tail nodes, then there is no intersection
        if (tailAndSizeList1.tail != tailAndSizeList2.tail) return null;

        // set pointers to the start of each linked list
        ListNode shorterList = tailAndSizeList1.size < tailAndSizeList2.size ? list1 : list2;
        ListNode longerList = tailAndSizeList1.size < tailAndSizeList2.size ? list2 : list1;

        // advance the pointer for the longer linked list by diff in lengths
        ListNode kthNodeLongerList = getKthNode(longerList, Math.abs(tailAndSizeList1.size - tailAndSizeList2.size));

        // move both pointers until you have a collision
        while (shorterList != kthNodeLongerList) {
            shorterList = shorterList.next;
            kthNodeLongerList = kthNodeLongerList.next;
        }

        return kthNodeLongerList;
    }

    /**
     * Multiple traversals through the linked list until the two list nodes are the same
     * @param headA
     * @param headB
     * @return
     */
    private static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        //boundary check
        if(headA == null || headB == null) return null;

        ListNode a = headA;
        ListNode b = headB;

        //if a & b have different len, then we will stop the loop after second iteration
        while( a != b){
            //for the end of first iteration, we just reset the pointer to the head of another linkedlist
            a = a == null? headB : a.next;
            b = b == null? headA : b.next;
        }

        return a;
    }
}
