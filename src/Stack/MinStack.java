package Stack;

import Tree.ListNode;

import java.util.Stack;

/**
 * Created by leiwang on 4/16/18.
 */
public class MinStack {
    public static void main(String args[]) {
        MinStackImpl impl = new MinStackImpl();
        impl.push(2);
        impl.push(1);
        impl.push(4);
        impl.push(25);
        impl.push(24);
        impl.push(12);
        impl.push(32);

        System.out.println("first result");
        System.out.println("top value for impl = " + impl.top());
        System.out.println("min value for impl = " + impl.getMin());

        MinStackImpl2 impl2 = new MinStackImpl2();
        impl2.push(2);
        impl2.push(1);
        impl2.push(4);
        impl2.push(25);
        impl2.push(24);
        impl2.push(12);
        impl2.push(32);

        System.out.println("second result");
        System.out.println("top value for impl2 = " + impl2.top());
        System.out.println("min value for impl2 = " + impl2.getMin());

        MinStackImpl3 impl3 = new MinStackImpl3();
        impl3.push(2);
        impl3.push(1);
        impl3.push(4);
        impl3.push(25);
        impl3.push(24);
        impl3.push(12);
        impl3.push(32);

        System.out.println("third result");
        System.out.println("top value for impl3 = " + impl3.top());
        System.out.println("min value for impl3 = " + impl3.getMin());

    }

    private static class MinStackImpl {
        Stack<Integer> stack;
        int min;

        public MinStackImpl() {
           stack = new Stack<>();
        }

        public void push(int num) {
            if (stack.isEmpty()) {
                stack.push(0);
                min = num;
            } else {
                stack.push(num - min); // could be negative if min value changes
                if (num < min) min = num;
            }
        }

        public void pop() {
            if (stack.isEmpty()) return;

            int pop = stack.pop();

            if (pop < 0) min = min - pop;
        }

        public int getMin() {
            return min;
        }

        public int top() {
            int top = stack.peek();
            if (top > 0) {
                return top + min;
            } else {
                return min;
            }
        }
    }

    static class MinStackImpl2 {
        int min = Integer.MAX_VALUE;
        Stack<Integer> stack = new Stack<Integer>();

        public void push(int x) {
            // only push the old minimum value when the current
            // minimum value changes after pushing the new value x
            if(x <= min){
                // this is to ensure the next smallest value
                // is always underneath the smallest value
                stack.push(min);
                min=x;
            }
            stack.push(x);
        }

        public void pop() {
            // if pop operation could result in the changing of the current minimum value,
            // pop twice and change the current minimum value to the last minimum value.
            if(stack.pop() == min) min=stack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min;
        }
    }

    static class MinStackImpl3 {
        class ListNode {
            int value;
            int min;
            ListNode next;

            ListNode(int x, int min) {
                this.value = x;
                this.min = min;
                next = null;
            }
        }

        ListNode head;
        public void push(int x) {
            if (head == null) {
                head = new ListNode(x,x);
            } else {
                ListNode n = new ListNode(x, Math.min(x,head.min));
                n.next = head;
                head = n;
            }
        }

        public void pop() {
            if (head != null)
                head = head.next;
        }

        public int top() {
            if (head != null)
                return head.value;
            return -1;
        }

        public int getMin() {
            if (head != null)
                return head.min;
            return -1;
        }
    }
}
