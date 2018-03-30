package LruCache;

public class DLinkedNode<T,U> {
    T key;
    U value;
    DLinkedNode<T,U> pre;
    DLinkedNode<T,U> post;

    public DLinkedNode(DLinkedNode<T,U> previous, DLinkedNode<T,U> next, T key, U value) {
        this.pre = previous;
        this.post = next;
        this.key = key;
        this.value = value;
    }
}
