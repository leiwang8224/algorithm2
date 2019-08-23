package CTCI.StacksAndQueue;

import java.util.Stack;

// sort a stack such that the smallest items are on top.
// You may use additional stack space but no other data
// storage
public class StackSort {
    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);

        Stack<Integer> result = mergeSortStack(s);
        System.out.println("first one");
        while (!result.isEmpty()) {
            System.out.print(result.pop());
        }
        System.out.println();
        System.out.println("second one");

        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);
        sort(s);
        while (!s.isEmpty()) {
            System.out.print(s.pop());
        }

    }

    // split up the stack into even and odd stack and perform merge sort.
    private static Stack<Integer> mergeSortStack(Stack<Integer> stack) {
        if (stack.size() <= 1) return stack;

        // split the stack into odd and even based on index
        Stack<Integer> evenStack = new Stack<>();
        Stack<Integer> oddStack = new Stack<>();

        int itemCount = 0;

        while (stack.size() != 0) {
            itemCount ++;
            if (itemCount % 2 == 0) evenStack.push(stack.pop());
            else oddStack.push(stack.pop());
        }

        evenStack = mergeSortStack(evenStack);
        oddStack = mergeSortStack(oddStack);

        while (!evenStack.isEmpty() || !oddStack.isEmpty()) {
            // if even stack is empty, push odd stack to stack
            // if odd stack is empty, push even stack to stack
            if (evenStack.isEmpty()) stack.push(oddStack.pop());
            else if (oddStack.isEmpty()) stack.push(evenStack.pop());
            // if they are both not empty, compare
            // if oddStack value < evenStack value, push evenStack
            else if (oddStack.peek().compareTo(evenStack.peek()) <= 0)
                stack.push(evenStack.pop());
            // else oddStack value > evenStack value, push oddStack
            else stack.push(oddStack.pop());
        }

        // now all of the largest items are at the bottom
        Stack<Integer> reverseStack = new Stack<>();

        // reverse the order so the smallest item is at the bottom
        while (!stack.isEmpty()) reverseStack.push(stack.pop());

        return reverseStack;
        
    }

    // use a buffer stack and a temp var to keep track of the adjacent
    // elements are compare
    private static void sort(Stack<Integer> inStack) {
        Stack<Integer> bufferStack= new Stack<>();
        while (!inStack.isEmpty()) {
            //insert each element in s in sorted order into r
            int tmp = inStack.pop();
            // buffer stack is ordered from the biggest values on the bottom and
            // smallest values on top
            while(!bufferStack.isEmpty() && bufferStack.peek() < tmp)
                // push bufferStack item to inStack if it's smaller
                inStack.push(bufferStack.pop());
            // push the element before to buffer regardless
            bufferStack.push(tmp);
        }

        // copy the elements back
        // reverse order so the biggest values are on the top and
        // smallest items on the bottom
        while (!bufferStack.isEmpty()) inStack.push(bufferStack.pop());
    }
}
