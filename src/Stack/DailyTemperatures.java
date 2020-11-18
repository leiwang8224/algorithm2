package Stack;

import java.util.Arrays;
import java.util.Stack;

// LC-739
public class DailyTemperatures {
    public static void main(String[] args) {
        int[] array = {73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println(Arrays.toString(dailyTemperatures(array)));

    }

    //remember that stack keeps track of the indices, not the element itself
    //one loop to keep track of indices traversal
    //one loop to keep track of popping from previous indices
    //once you pop to a certain point, you need to extract the end index
    //process repeats, don't forget to push the index last
    //also remember the result array is initialized to 0, no need to fill when result is 0 days
    private static int[] dailyTemperatures(int[] T) {
        if (T == null || T.length == 0) return new int[0];

        int[] numberDays = new int[T.length];

        Stack<Integer> stack = new Stack<>();

        // index backwards in the array
        for (int startIndex = T.length - 1; startIndex >= 0; startIndex--) {
            // T(startIndex) >= T(index)
            // while (hotter) stack.pop()
            while (!stack.isEmpty() && T[startIndex] >= T[stack.peek()]) {
                // moving forward in index to find the end Index
                // which is T(startIndex) < T(index)
                stack.pop();
            }

            // at this point we have found the next greater element
            if (!stack.isEmpty()) {
                int endIndex = stack.peek();  // note that this is not pop, IT is peek
                // we want to keep the endIndex in the stack for further processing
                numberDays[startIndex] = endIndex - startIndex;
            }
            stack.push(startIndex);// make sure to push EVERY index into the stack for processing
        }
        return numberDays;
    }
}
