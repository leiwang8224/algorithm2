package CTCI.StacksAndQueue;

import java.util.Stack;

public class MinStack {
    public static void main(String[] args) {

    }

    /**
     * Use a second stack on top of the super class stack
     * to keep track of the minimum element. Ensure the min element
     * is always on the top of the stack by comparing the new element
     * to the element on the top of the stack.
     */
    private class StackMin extends Stack<Integer> {
        Stack<Integer> s2;

        StackMin() {
            s2 = new Stack<Integer>();
        }

        void pushValue(int value) {
            if (value <= min()) {
                // always push the smallest element on top of stack
                s2.push(value);
            }
            super.push(value);
        }

        Integer popValue() {
            int value = super.pop();
            if (value == min()) s2.pop();
            return value;
        }

        private int min() {
            if (s2.isEmpty()) return Integer.MAX_VALUE;
            else return s2.peek();
        }
    }

    private class NodeWithMin {
        int value;
        int min;
        NodeWithMin(int v, int min) {
            value = v;
            this.min = min;
        }
    }

    /**
     * Use data structure to capture the min value in the stack
     */
    private class StackMin2 extends Stack<NodeWithMin> {
        void push(int value) {
            int newMin = Math.min(value, min());
            super.push(new NodeWithMin(value, newMin));
        }

        private int min() {
            if (this.isEmpty()) return Integer.MAX_VALUE;
            else return peek().min;
        }
    }


    
}
