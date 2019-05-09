package LinkedList;

public class InsertNodeAtPosDoublyLinkedList {
    public static void main(String[] args) {

        DoublyLinkedList head = new DoublyLinkedList(2);
        head.prev = null;
        head.next = new DoublyLinkedList(3);
        head.next.next = new DoublyLinkedList(4);
        head.next.next.next = new DoublyLinkedList(5);
        head.next.next.next.next = new DoublyLinkedList(6);
        printDoublyLL(deleteAtPos(insertAtPos(head, 5, 2),2));;


    }

    private static void printDoublyLL(DoublyLinkedList head) {
        DoublyLinkedList cur = head;
        while (cur != null) {
            System.out.print(cur.data + "->");
            cur = cur.next;
        }
    }
    private static DoublyLinkedList insertAtPos(DoublyLinkedList head, int data, int pos) {
        DoublyLinkedList newNode = new DoublyLinkedList(data);

        if (head == null && pos == 1) head = newNode;
        else {
            DoublyLinkedList currentNode = head;
            DoublyLinkedList prevNode = head;
            int count = 1;

            while (currentNode != null) {
                if (count == pos) {
                    break;
                } else {
                    // insert in between prevNode and currentNode
                    prevNode = currentNode;
                    currentNode = currentNode.next;
                    count++;
                }
            }

            // do nothing if position not available
            if (count < pos) return head;

            // insert at heading position
            else if (count == 1) {
                newNode.next = currentNode;
                currentNode.prev = newNode;
                head = newNode;
            }

            // insert at middle
            else if (currentNode != null) {
                newNode.next = currentNode;
                newNode.prev = prevNode;
                prevNode.next = newNode;
                currentNode.prev = newNode;
            }
            // insert at end
            else {
                prevNode.next = newNode;
                newNode.prev = prevNode;
            }
        }
        return head;
    }

    private static DoublyLinkedList deleteAtPos(DoublyLinkedList head, int pos) {
        if (head == null)
            return null;
        else if (head.next == null)
            return (pos == 1) ? null : head;
        else {
            int count = 1;
            DoublyLinkedList curr = head;
            DoublyLinkedList prev = null;
            while (curr != null) {
                if (count == pos)
                    break;
                count++;
                prev = curr;
                curr = curr.next;
            }

            //invalid position
            if (count < pos)
                return head;
            //tail position
            else if (curr.next == null) {
                prev.next = null;
                curr.prev = null;
                return head;
            }
            //head position
            else if (count == 1) {
                DoublyLinkedList temp = curr;
                curr.next.prev = null;
                curr = curr.next;
                temp.next = null;
                return curr;
            }
            //middle position
            else {
                prev.next = curr.next;
                curr.prev = null;
                curr.next.prev = prev;
                curr.next = null;
                return head;
            }
        }
    }
}
