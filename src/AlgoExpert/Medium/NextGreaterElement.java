package AlgoExpert.Medium;
import java.util.*;
public class NextGreaterElement {
    // O(n) time | O(n) space
    int[] nextGreaterElement(int[] array) {
        int[] result = new int[array.length];
        Arrays.fill(result, -1);

        Stack<Integer> stack = new Stack<>();

        for (int idx = 0; idx < 2 * array.length; idx++) {
            int circularIdx = idx % array.length;

            while (stack.size() > 0 && array[stack.peek()] < array[circularIdx]) {
                int top = stack.pop();
                result[top] = array[circularIdx];
            }
            stack.push(circularIdx);
        }

        return result;
    }

    // O(n) time | O(n) space
    int[] nextGreaterElement2(int[] array) {
        int[] result = new int[array.length];
        Arrays.fill(result, -1);

        Stack<Integer> stack = new Stack<>();

        for(int idx = 2 *array.length-1; idx >= 0; idx--) {
            int circularIdx = idx % array.length;

            while (stack.size() > 0) {
                if (stack.peek() <= array[circularIdx]) {
                    stack.pop();
                } else {
                    result[circularIdx] = stack.peek();
                    break;
                }
            }
            stack.push(array[circularIdx]);
        }
        return result;
    }
}
