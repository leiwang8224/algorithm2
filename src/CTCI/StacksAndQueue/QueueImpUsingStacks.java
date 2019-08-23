package CTCI.StacksAndQueue;

import java.util.Stack;

// Implement a Queue using two stacks
public class QueueImpUsingStacks {
    public static void main(String[] args) {
        QueueImpl q = new QueueImpl();
        q.add(1);
        q.add(2);
        q.remove();
        q.add(3);
        while (!q.isEmpty()) {
            System.out.println(q.remove());
        }

    }

    static class QueueImpl {
        Stack<Integer> pushStack;
        Stack<Integer> popStack;

        QueueImpl() {
            pushStack = new Stack<>();
            popStack = new Stack<>();
        }

        int size() {
            return pushStack.size() + popStack.size();
        }

        void add(int val) {
            shiftFromPopToPushStack();
            pushStack.push(val);
        }

        void shiftFromPushToPopStack() {
            if (popStack.isEmpty()) {
                while(!pushStack.isEmpty()) {
                    popStack.push(pushStack.pop());
                }
            }
        }

        void shiftFromPopToPushStack() {
            while (!popStack.isEmpty()) {
                pushStack.push(popStack.pop());
            }
        }

        int peek() {
            shiftFromPushToPopStack();
            return popStack.peek();    // oldest item
        }

        int remove() {
            shiftFromPushToPopStack();
            return popStack.pop();     // oldest item
        }

        boolean isEmpty() {
            shiftFromPushToPopStack();
            return popStack.isEmpty();
        }


    }


}
