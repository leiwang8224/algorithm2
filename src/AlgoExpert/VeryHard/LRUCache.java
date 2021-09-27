package AlgoExpert.VeryHard;

import java.util.*;
public class LRUCache {
    static class LRUCacheImpl {
        Map<String, DoublyLinkedListNode> cacheStrToNode = new HashMap<>();
        int maxSize;
        int curSize = 0;
        DoublyLinkedList listOfMostRecent = new DoublyLinkedList();

        LRUCacheImpl(int maxSize) {
            this.maxSize= maxSize > 1 ? maxSize : 1;
        }

        // O(1) time | O(1) space
        public void insertKeyValuePair(String key, int value) {
            if (!cacheStrToNode.containsKey(key)) {
                if (curSize == maxSize) {
                    evictLeastRecentAtTail();
                } else {
                    curSize++;
                }
                // put new node in map
                cacheStrToNode.put(key, new DoublyLinkedListNode(key, value));
            } else { // already exist in map, just update the value in the map
                updateKeyValue(key, value);
            }
            // update most recently used, always add to the head of list
            listOfMostRecent.setHeadTo(cacheStrToNode.get(key));
        }

        // O(1) time | O(1) space
        LRUResult getValueFromKey(String key) {
            // if not in map just return not found and -1 value
            if (!cacheStrToNode.containsKey(key)) {
                return new LRUResult(false, -1);
            }
            // update most recently used
            listOfMostRecent.setHeadTo(cacheStrToNode.get(key));
            return new LRUResult(true, cacheStrToNode.get(key).value);
        }

        // O(1) time | O(1) space
        String getMostRecentKey() {
            if (listOfMostRecent.head == null) return "";
            return listOfMostRecent.head.key;
        }

        private void updateKeyValue(String key, int value) {
            if (!this.cacheStrToNode.containsKey(key)) return;
            cacheStrToNode.get(key).value = value;
        }

        // set the node to be head (most recently used)
        private void updateMostRecent(DoublyLinkedListNode node) {
            listOfMostRecent.setHeadTo(node);
        }

        private void evictLeastRecentAtTail() {
            // least recently used is at the tail of the list
            String keyToRemove = listOfMostRecent.tail.key;
            listOfMostRecent.removeTail();
            cacheStrToNode.remove(keyToRemove);
        }
    }

    static class DoublyLinkedList {
        DoublyLinkedListNode head = null;
        DoublyLinkedListNode tail = null;

        void setHeadTo(DoublyLinkedListNode nodeToSet) {
            if (head == nodeToSet) {
                return;
            } else if (head == null) { // empty node list
                head = nodeToSet;
                tail = nodeToSet;
            } else if (head == tail) { // one node so insert before head/tail
                tail.prev = nodeToSet;
                head = nodeToSet;
                head.next = tail;
            } else { // multiple nodes in list already
                if (tail == nodeToSet) { // if the node to set head is tail, remove it from tail
                    removeTail();
                }
                nodeToSet.removeBindings();
                head.prev = nodeToSet;  // and move to front of the list
                nodeToSet.next = head;
                head = nodeToSet;
            }
        }

        void removeTail() {
            if (tail == null) return;
            if (tail == head) {
                head = null;
                tail = null;
                return;
            }
            tail = tail.prev;
            tail.next = null;
        }
    }

    static class DoublyLinkedListNode {
        String key;
        int value;
        DoublyLinkedListNode prev = null;
        DoublyLinkedListNode next = null;

        public DoublyLinkedListNode(String key, int value) {
            this.key = key;
            this.value = value;
        }

        void removeBindings() {
            if (prev != null) prev.next = next;
            if (next != null) next.prev = prev;
            prev = null;
            next = null;
        }
    }

    static class LRUResult {
        boolean found;
        int value;
        public LRUResult(boolean found, int value) {
            this.found = found;
            this.value = value;
        }
    }

}
