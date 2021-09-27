package AlgoExpert.Medium;

public class BuildDoublyLinkedList {
    public static void main(String[] args) {

    }

    static class DoublyLinkedList {
        Node head;
        Node tail;

        // O(1) time | O(1) space
        void setHead(Node node) {
            // check if head exists
            if (head == null) {
                head = node;
                tail = node;
                return;
            }
            insertBefore(head, node);
        }

        //O(1) time | O(1) space
        void setTail(Node node) {
            // check if tail exists
            if (tail == null) {
                setHead(node);
                return;
            }
            insertAfter(tail, node);
        }

        //O(1) time | O(1) space
        /**
         * Need to consider the following:
         *  - tail case
         *  - whether there is only one node and it's already in the list
         *
         */
        private void insertAfter(Node insertAfterThisNode, Node nodeToInsert) {
            // in case the node to insert is head and tail (only one node)
            if (nodeToInsert == head && nodeToInsert == tail) return;
            remove(nodeToInsert);
            // insertAfterThisNode<=nodeToInsert=>insertAfterThisNode.next
            nodeToInsert.prev = insertAfterThisNode;
            nodeToInsert.next = insertAfterThisNode.next;
            if (insertAfterThisNode.next == null) { // add to tail if next ptr doesnt exist
                tail = nodeToInsert;
            } else {
                // insertAfterThisNode<-nodeToInsert<=insertAfterThisNode.next
                insertAfterThisNode.next.prev = nodeToInsert;
            }
            // insertAfterThisNode=>nodeToInsert<-insertAfterThisNode.next
            insertAfterThisNode.next = nodeToInsert;

        }

        //O(p) time | O(1) space
        private void insertAtPosition(int position, Node nodeToInsert) {
            // consider the head
            if (position == 1) {
                setHead(nodeToInsert);
                return;
            }
            Node ptr = head;
            // traverse to the i'th node
            int curPos = 1;
            while (ptr != null && curPos++ != position)
                ptr = ptr.next;
            // we are at the node of insertion point (position)
            if (ptr != null) {
                insertBefore(ptr, nodeToInsert);
            } else {
                // or we have reached the end of the list but hasn't reached position
                setTail(nodeToInsert);
            }
        }

        //O(n) time | O(1) space
        void removeNodesWithValue(int value) {
            Node ptr = head;
            while (ptr != null) {
                Node nodeToRemove = ptr;
                ptr = ptr.next;
                if (nodeToRemove.value == value) remove(nodeToRemove);
            }
        }

        // O(1) time | O(1) space
        /**
         * Need to consider the head node
         */
        private void insertBefore(Node insertBeforeThisNode, Node nodeToInsert) {
            // always check if the node to insert is the only node in the list
            if (nodeToInsert == head && nodeToInsert == tail) return;
            // if node is part of the list, remove it from list
            remove(nodeToInsert);
            // insert into the position specified
            nodeToInsert.prev = insertBeforeThisNode.prev;
            nodeToInsert.next =insertBeforeThisNode;
            if (insertBeforeThisNode.prev == null) { // if we are inserting into head node
                head = nodeToInsert;
            } else { // else we are inserting into the middle
                insertBeforeThisNode.prev.next = nodeToInsert;
            }
            insertBeforeThisNode.prev = nodeToInsert;

        }

        //O(1) time | O(1) space
        // second most primitive method
        // need to consider head and tails
        private void remove(Node node) {
            if (node == head) head = head.next;
            if (node == tail) tail = tail.prev;
            removeNodeBindings(node);
        }

        private void removeNodeBindings(Node node) {
            if(node.prev != null) node.prev.next = node.next;
            if(node.next != null) node.next.prev = node.prev;
            node.prev = null;
            node.next = null;
        }

        // O(n) time | O(1) space
        // most primitive method
        private boolean containsNodeWithValue(int value) {
            Node node = head;
            while (node != null && node.value != value) node = node.next;
            return node != null;
        }

        static class Node {
            int value;
            Node prev;
            Node next;
            Node(int value) {
                this.value = value;
            }
        }
    }
}
