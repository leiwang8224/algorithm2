package Arrays;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import LinkedList.ListNode;

/**
 * Merge k linked lists
 */
public class MergeKSortedLists {
    public static void main(String[] args) {
        ListNode head1 = new ListNode(0);
        head1.next = new ListNode(23);
        head1.next.next = new ListNode(42);
        head1.next.next.next = new ListNode(1);
        head1.next.next.next.next = new ListNode(4);

        ListNode head2 = new ListNode(23);
        head2.next = new ListNode(234);
        head2.next.next = new ListNode(421);
        head2.next.next.next = new ListNode(13);
        head2.next.next.next.next = new ListNode(43);

        ListNode head3 = new ListNode(223);
        head3.next = new ListNode(3);
        head3.next.next = new ListNode(42);
        head3.next.next.next = new ListNode(123);
        head3.next.next.next.next = new ListNode(433);

        List<ListNode> listOfLinkedList = new ArrayList<>();
        listOfLinkedList.add(head1);
        listOfLinkedList.add(head2);
        listOfLinkedList.add(head3);
        ListNode result = mergeKLists(listOfLinkedList);
        System.out.println("first result");
        printLinkedList(result);

        ListNode[] arrayLinkedList = new ListNode[listOfLinkedList.size()];
        int i = 0;
        for (ListNode node : listOfLinkedList) {
            arrayLinkedList[i] = node;
            i++;
        }
        ListNode result2 = mergeKLists2(arrayLinkedList);
        System.out.println("second result");
        printLinkedList(result2);

        ListNode result3 = mergeKLists3(arrayLinkedList);
        System.out.println("third result");
        printLinkedList(result3);
    }
    private static void printLinkedList(ListNode head) {
        while (head != null) {
            System.out.println(head.getVal() + "->");
            head = head.next;
        }
    }
    public static ListNode mergeKLists(List<ListNode> lists) {
        if (lists==null||lists.size()==0) return null;

        PriorityQueue<ListNode> queue= new PriorityQueue<ListNode>(lists.size(), new Comparator<ListNode>(){
            @Override
            public int compare(ListNode o1,ListNode o2){
                if (o1.getVal()<o2.getVal())
                    return -1;
                else if (o1.getVal()==o2.getVal())
                    return 0;
                else
                    return 1;
            }
        });

        ListNode dummy = new ListNode(0);
        ListNode tail=dummy;

        // fill priority queue with the top list nodes of each sorted list
        for (ListNode node:lists)
            if (node!=null)
                queue.add(node);

        // while the queue is not empty, poll the min element
        while (!queue.isEmpty()){
            tail.next=queue.poll();
            tail=tail.next;

            if (tail.next!=null)
                queue.add(tail.next);
        }
        return dummy.next;
    }

//    Heaps are great for these types of problems - where you have k lists or
//    arrays that need to be sorted. Why? Visualize a heap as a pipe that takes
//    in stuff from multiple sources - in this case multiple lists - rearranges
//    that stuff to ensure that the smallest element is always at the top of the
//    heap (Min Heap) - and spits out this element to an output list. Now if we
//    use a heap as a sorting pipe, we simply need to keep this pipe filled and
//    collect whatever comes out of the other end in a master list.
//    In Java, heaps are PriorityQueue s.
    private static ListNode mergeKLists4(ArrayList<ListNode> lists) {
        if (lists.size() == 0) return null;
        PriorityQueue<ListNode> queue = new PriorityQueue<>(lists.size(),
                                                            new Comparator<ListNode>() {
                                                                @Override
                                                                public int compare(ListNode node1,
                                                                                   ListNode node2) {
                                                                    if (node1.getVal() > node2.getVal()) return 1;
                                                                    else if (node1.getVal() == node2.getVal()) return 0;
                                                                    else return -1;
                                                                }
                                                            });
        for (ListNode node : lists) {
            if (node != null)
                queue.add(node);
        }

        ListNode head = new ListNode(0), curr = head;

        // while queue is not empty, get the smallest element
        // and add to the next pointer in the new linkedlist
        while (queue.size() > 0) {
            ListNode temp = queue.poll();
            curr.next = temp;
            if (temp.next != null)
                queue.add(temp.next);
            curr = curr.next;
        }

        return head.next;
    }

    public static ListNode mergeKLists2(ListNode[] lists){
        return partion2(lists,0,lists.length-1);
    }

    public static ListNode partion2(ListNode[] lists,int s,int e){
        if(s==e)  return lists[s];
        if(s<e){
            int q=(s+e)/2;
            ListNode l1=partion2(lists,s,q);
            ListNode l2=partion2(lists,q+1,e);
            return merge2(l1,l2);
        }else
            return null;
    }

    //This function is from Merge Two Sorted Lists.
    public static ListNode merge2(ListNode l1,ListNode l2){
        if(l1==null) return l2;
        if(l2==null) return l1;
        if(l1.getVal()<l2.getVal()){
            l1.next=merge2(l1.next,l2);
            return l1;
        }else{
            l2.next=merge2(l1,l2.next);
            return l2;
        }
    }

    public static ListNode mergeKLists3(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        return sort3(lists, 0, lists.length - 1);
    }

    private static ListNode sort3(ListNode[] lists, int lo, int hi) {
        if (lo >= hi) return lists[lo];
        int mid = lo + (hi - lo) / 2;
        ListNode l1 = sort3(lists, lo, mid);
        ListNode l2 = sort3(lists, mid + 1, hi);
        return merge3(l1, l2);
    }

    private static ListNode merge3(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.getVal() < l2.getVal()) {
            l1.next = merge3(l1.next, l2);
            return l1;
        }
        l2.next = merge3(l1, l2.next);
        return l2;
    }
}
