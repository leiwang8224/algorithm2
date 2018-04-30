package Stack;

import java.util.Stack;

/**
 * Created by leiwang on 3/31/18.
 */
public class SortByStack {

    public static void main(String[] args) {

        // prints out the result here
        sortByStack(generateStack());
        System.out.println("sort ascending");
        sortByStackAscending(generateStack());
    }

    private static Stack<Integer> generateStack() {
        int[] arr = new int[] { 2,3,5,2,4,2,23,2,1,3,2,3,5};
        Stack<Integer> s1 = new Stack<>();
        for (int num : arr) {
            s1.push(num);
        }
        return s1;
    }

    /**
     * Descending order
     * @param s1
     */
    private static void sortByStack(Stack<Integer> s1) {
        Stack<Integer> result = new Stack<>();
        while (!s1.isEmpty()) {
            // get one element and compare the rest of element
            int temp = s1.pop();
            while (!result.isEmpty() && result.peek() > temp) {
                s1.push(result.pop());
            }
            // move from s1 stack to b stack
            result.push(temp);
        }
        while (!result.isEmpty()) {
            System.out.println(result.pop());
        }
    }

    private static void sortByStackAscending(Stack<Integer> s1) {
        Stack<Integer> orderedStack = new Stack<>();
        while (!s1.isEmpty()) {
            int temp = s1.pop();
            while (!orderedStack.isEmpty() && orderedStack.peek() < temp) {
                s1.push(orderedStack.pop());
            }
            // move from s1 stack to orderedStack
            orderedStack.push(temp);
        }
        while (!orderedStack.isEmpty())
            System.out.println(orderedStack.pop());
    }
}
