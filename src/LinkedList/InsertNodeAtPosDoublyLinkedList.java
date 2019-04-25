package LinkedList;

public class InsertNodeAtPosDoublyLinkedList {
    public static void main(String[] args) {

    }

    private DoublyLinkedList insertAtPos(DoublyLinkedList head, int data, int pos) {
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
}
