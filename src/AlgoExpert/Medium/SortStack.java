package AlgoExpert.Medium;
import java.util.*;
//O(n^2) time | O(n) space
public class SortStack {
    //O(n^2) time | O(n) space
    ArrayList<Integer> sortStack(ArrayList<Integer> stack) {
        if (stack.size() == 0) {
            return stack;
        }

        int top = stack.remove(stack.size() - 1);
        sortStack(stack);
        insertInSortedOrder(stack, top);

        return stack;
    }

    private void insertInSortedOrder(ArrayList<Integer> stack, int value) {
        if (stack.size() == 0 || (stack.get(stack.size()-1) <= value)) {
            stack.add(value);
            return;
        }

        int top = stack.remove(stack.size() - 1);
        insertInSortedOrder(stack, value);

        stack.add(top);
    }

}
