package CTCI.StacksAndQueue;

import java.util.ArrayList;

// Imagine a stack of plates. If the stack gets too high it will topple.
// therefore when the stack gets to a certain size (exceeds some threshold)
// create a new stack to use.
public class SetOfStacks {
    public static void main(String[] args) {

    }

    /*
    Implementation of a doubly linked list
     */
    class Node {
        Node above;
        Node below;
        int value;
        Node(int value) {
            this.value = value;
        }
    }

    class StackImpl{
        private int capacity;
        // use pointers for the top and bottom of the stack
        Node top;
        Node bottom;
        int size = 0;

        // capacity is the max number of elements before the stack falls apart
        StackImpl(int capacity) {
            this.capacity = capacity;
        }

        boolean isFull() {
            return capacity == size;
        }

        // joins two stacks together
        void join (Node above, Node below) {
            if (below != null) below.above = above;
            if (above != null) above.below = below;
        }

        boolean push (int v) {
            // do not allow for push when stack at largest capacity
            if (size >= capacity) return false;
            // increment size
            size++;
            // make new node based on the value
            Node n = new Node(v);
            // if there is only one element in stack
            // put the node in the bottom
            if (size == 1) bottom = n;
            // else put the node on the top of the 'top' node
            join(n, top);
            // point top to the new node
            top = n;
            return true;
        }

        int pop() {
            // get the top node
            Node t = top;
            // change the top pointer to the element below
            top = top.below;
            size--;
            return t.value;
        }

        boolean isEmpty() {
            return size == 0;
        }

        int removeBottom() {
            // get the bottom node and point bottom pointer
            // to the next element above
            Node b = bottom;
            bottom = bottom.above;
            // if the new bottom is valid, set bottom below to null
            if (bottom != null) bottom.below = null;
            size--;
            return b.value;
        }

    }

    class StackSet {
        ArrayList<StackImpl> stacks = new ArrayList<>();
        int capacity;

        StackSet(int capacity) {
            this.capacity = capacity;
        }

        StackImpl getLastStack() {
            if (stacks.size() == 0) return null;
            return stacks.get(stacks.size() - 1);
        }

        void push (int v) {
            StackImpl last = getLastStack();
            if (last != null && !last.isFull()) last.push(v);
            else {
                StackImpl stack = new StackImpl(capacity);
                stack.push(v);
                stacks.add(stack);
            }
        }

        int pop() {
            StackImpl last = getLastStack();
            int v = last.pop();
            if (last.size == 0) stacks.remove(stacks.size()-1);
            return v;
        }

        int popAt(int index) {
            return leftShift(index, true);
        }

        private int leftShift(int index, boolean removeTop) {
            StackImpl stack = stacks.get(index);
            int removedItem;
            if (removeTop) removedItem = stack.pop();
            else removedItem = stack.removeBottom();

            if (stack.isEmpty()) stacks.remove(index);
            else if (stacks.size() > index + 1) {
                int v = leftShift(index + 1, false);
                stack.push(v);
            }
            return removedItem;
        }

        boolean isEmpty() {
            StackImpl last = getLastStack();
            return last == null || last.isEmpty();
        }


    }
}
