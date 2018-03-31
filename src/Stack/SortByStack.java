package Stack;

import java.util.Stack;

/**
 * Created by leiwang on 3/31/18.
 */
public class SortByStack {

    public static void main(String[] args) {
        int[] arr = new int[] { 2,3,5,2,4,2,23,2,1,3,2,3,5};
        Stack<Integer> s1 = new Stack<>();
        for (int num : arr) {
            s1.push(num);
        }
        sortByStack(s1);
    }

    /**
     * Descending order
     * @param s1
     */
    private static void sortByStack(Stack<Integer> s1) {
        Stack<Integer> b = new Stack<>();
        while (!s1.isEmpty()) {
            int temp = s1.pop();
            while (!b.isEmpty() && b.peek() > temp) {
                s1.push(b.pop());
            }
            b.push(temp);
        }
        while (!b.isEmpty()) {
            System.out.println(b.pop());
        }
    }
}
