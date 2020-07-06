package CTCI.Systems;

import java.util.HashMap;

/**
 * Imagine a webserver for a simplified search engine. This system has 100 machines to respond
 * to search queries, which may then call out using processSearch(string query) to another cluster
 * of machines to actually get the result. The machine which responds to a given
 * query is chosen at random, so you cannot guarantee that the same machine will always respond to
 * the same request. The method processSearch is very expensive. Design a caching mechanism
 * to cache the results of the most recent queries.
 */
public class CachingLatestSearchResults {
    public static void main(String[] args) {
        Cache cache = new Cache();
        for (int index = 0; index < 20; index++) {
            String query = "query" + index;
            cache.insertResults(query, generateResults(index));

            if (index == 9 || index == 16 || index == 19) {
                cache.getResults("query" + 2);
                cache.getResults("query" + 6);
                cache.getResults("query" + 9);
            }
        }
        
        for (int index = 0; index < 30; index++) {
            String query = "query" + index;
            String[] results = cache.getResults(query);
            System.out.print(query + ": ");
            if (results == null) System.out.print("null");
            else {
                for (String s : results) {
                    System.out.print(s + ", ");
                }
            }
            System.out.println();
        }


    }

    static String[] generateResults(int index) {
        String[] results = {"resultA" + index, "resultB" + index, "resultC" + index};
        return results;
    }

    static class DoublyLinkedListNode {
        DoublyLinkedListNode prev;
        DoublyLinkedListNode next;
        String[] results;
        String query;

        DoublyLinkedListNode(String q, String[] res) {
            results = res;
            query = q;
        }
    }

    /**
     * A good way to approach this problem is to start by designing it for a single machine.
     * How would you create a data structure that enables you to easily purge old data and also
     * efficiently look up a value based on a key?
     * - Linked list would allow easy purging of old data, by moving fresh items to the front.
     * We could implement it to remove the last element of the linked list when the list exceeds
     * a certain size.
     * - Hash table allows efficient lookups of data, but it wouldn't ordinarily allow easy data
     * purging.
     *
     * How do we get the best of both worlds? By combining the two data struct.
     *
     * We create a linked list where a node is moved to the front every time it's accessed.
     * This way, the end of the linked list will always contain the stalest information.
     *
     * In addition, we have a hash table that maps from a query to the corresponding node in the
     * linked list. This allows us to not only efficiently return the cached results, but also to move
     * the appropriate node to the front of the list, thereby updating its 'freshness'
     *
     */
    static class Cache {
        static int MAX_SIZE = 10;
        DoublyLinkedListNode head;
        DoublyLinkedListNode tail;
        HashMap<String, DoublyLinkedListNode> map;
        int size = 0;

        Cache() {
            map = new HashMap<>();
        }

        void moveToFront(DoublyLinkedListNode node) {
            if (node == head) return;

            removeFromLinkedList(node);
            node.next = head;

            if (head != null) head.prev = node;
            head = node;
            size ++;

            if (tail == null) tail = node;
        }

        void moveToFront(String query) {
            DoublyLinkedListNode node = map.get(query);
            moveToFront(node);
        }

        void removeFromLinkedList(DoublyLinkedListNode node) {
            if (node == null) return;

            if (node.next != null || node.prev != null) size --;

            DoublyLinkedListNode prev = node.prev;
            if (prev != null) prev.next = node.next;

            DoublyLinkedListNode next = node.next;
            if (next != null) next.prev = prev;

            if (node == head) head = next;

            if (node == tail) tail = prev;

            node.next = null;
            node.prev = null;
        }

        String[] getResults(String query) {
            if (map.containsKey(query)) {
                DoublyLinkedListNode node = map.get(query);
                moveToFront(node);
                return node.results;
            }
            return null;
        }

        void insertResults(String query, String[] results) {
            if (map.containsKey(query))  {
                DoublyLinkedListNode node = map.get(query);
                node.results = results;
                moveToFront(node);
                return;
            }

            DoublyLinkedListNode node = new DoublyLinkedListNode(query, results);
            moveToFront(node);
            map.put(query, node);

            if (size > MAX_SIZE) {
                map.remove(tail.query);
                removeFromLinkedList(tail);
            }
        }
        

    }

    
}
