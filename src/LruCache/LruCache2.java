package LruCache;

import java.util.HashMap;

/**
 * LinkedListHashMap implementation
 * Order matters with fast look up
 * @param <K>
 * @param <V>
 */
public class LruCache2<K,V> {
    private HashMap<K, DLinkedNode<K,V>> cache;
    private DLinkedNode<K,V> leastRecentlyUsed;
    private DLinkedNode<K,V> mostRecentlyUsed;
    private int maxSize;
    private int currentSize;

    public LruCache2(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;
        leastRecentlyUsed = new DLinkedNode<>(null, null, null, null);
        mostRecentlyUsed = leastRecentlyUsed;
        cache = new HashMap<K,DLinkedNode<K,V>>();
    }

    public V get(K key) {
        DLinkedNode<K,V> tempNode = cache.get(key);
        if (tempNode == null)
            return null;
        else if (tempNode.key == mostRecentlyUsed.key)  // if MRU then leave the list as it is
            return mostRecentlyUsed.value;

        // get next and prev nodes
        DLinkedNode<K,V> nextNode = tempNode.post;
        DLinkedNode<K,V> prevNode = tempNode.pre;

        // if at the left-most we update LRU
        if (tempNode.key == leastRecentlyUsed.key) {
            nextNode.pre = null;
            leastRecentlyUsed = nextNode;
        }

        // if we are in the middle, we need to update the items before and after our item
        else if (tempNode.key != mostRecentlyUsed.key) {
            prevNode.post = nextNode;
            nextNode.pre = prevNode;
        }

        // finally move our item to the MRU
        tempNode.pre = mostRecentlyUsed;
        mostRecentlyUsed.post = tempNode;
        mostRecentlyUsed = tempNode;
        mostRecentlyUsed.post = null;

        return tempNode.value;
    }

    public void put(K key, V value) {
        if (cache.containsKey(key))
            return;

        // put the new node at the right most end of the linked list
        DLinkedNode<K,V> myNode = new DLinkedNode<K,V>(mostRecentlyUsed, null, key, value);
        mostRecentlyUsed.post = myNode;
        cache.put(key,myNode);
        mostRecentlyUsed = myNode;

        // Delete the left most entry and update the LRU pointer
        if (currentSize == maxSize) {
            cache.remove(leastRecentlyUsed.key);
            leastRecentlyUsed = leastRecentlyUsed.post;
            leastRecentlyUsed.pre = null;
        }

        // Update cache size, for the first added entry update the LRU pointer
        else if (currentSize < maxSize) {
            if (currentSize == 0)
                leastRecentlyUsed = myNode;

            currentSize++;
        }
    }

}
