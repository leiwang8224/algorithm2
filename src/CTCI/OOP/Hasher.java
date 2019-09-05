package CTCI.OOP;

import java.util.ArrayList;

public class Hasher {
    public static void main(String[] args) {

    }
    static class HasherImpl<K, V> {
        static class LinkedListNode<K, V> {
            LinkedListNode<K, V> next;
            LinkedListNode<K, V> prev;

            K key;
            V value;

            LinkedListNode(K k, V v) {
                key = k;
                value = v;
            }

            String printForward() {
                String data = "(" + key + "," + value + ")";
                if (next != null)
                    return data + "->" + next.printForward();
                else
                    return data;
            }
        }

        private ArrayList<LinkedListNode<K,V>> arr;

        HasherImpl(int capacity) {
            // create list of linked lists
            arr = new ArrayList<>();
            arr.ensureCapacity(capacity);
            for (int index = 0; index < capacity; index++) arr.add(null);
        }

        V put(K key, V value) {
            LinkedListNode<K, V> node = getNodeForKey(key);
            if (node != null) {
                V oldValue = node.value;
                node.value = value; // update the value
                return oldValue;
            }

            node = new LinkedListNode<>(key, value);

            int index = getIndexForKey(key);

            if (arr.get(index) != null) {
                node.next = arr.get(index);
                node.next.prev = node;
            }

            arr.set(index, node);
            return null;
        }

        V remove(K key) {
            LinkedListNode<K, V> node = getNodeForKey(key);
            if (node == null) return null;
            if(node.prev != null) node.prev.next = node.next;
            else {
                // removing head - update
                int hashKey = getIndexForKey(key);
                arr.set(hashKey, node.next);
            }

            if (node.next != null) node.next.prev = node.prev;

            return node.value;
        }

        V get(K key) {
            if (key == null) return null;
            LinkedListNode<K, V> node = getNodeForKey(key);
            return node == null ? null : node.value;
        }

        LinkedListNode<K, V> getNodeForKey(K key) {
            int index = getIndexForKey(key);
            LinkedListNode<K, V> current = arr.get(index);
            while (current != null) {
                if (current.key == key) return current;
                current = current.next;
            }
            return null;
        }

        private int getIndexForKey(K key) {
            return Math.abs(key.hashCode() % arr.size());
        }
        
        void printTable() {
            for (int index = 0; index < arr.size(); index++) {
                String s = arr.get(index) == null ? "" : arr.get(index).printForward();
                System.out.println(index + ": " + s);
            }
        }

    }

}
