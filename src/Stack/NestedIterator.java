package Stack;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by leiwang on 4/5/18.
 */

/**
 * Given a nested list of integers, implment an iterator
 * to flatten it.
 * Each element is either an integer or a list, whose
 * elements may also be integers or other lists.
 * Given the list [1,1],2,[1,1]],
 * calling next() should return 1,1,2,1,1,
 */
public class NestedIterator {
    public static class NestedIteratorImpl implements Iterator<Integer> {

        private interface NestedIntegerImpl {
            public boolean isInteger();

            public Integer getInteger();

            public List<NestedIntegerImpl> getList();
        }

        Stack<NestedIntegerImpl> stack = new Stack<>();

        public NestedIteratorImpl(List<NestedIntegerImpl> nestedList) {
            for (int i = nestedList.size() - 1; i >= 0; i--) {
                stack.push(nestedList.get(i));
            }
        }

        @Override public boolean hasNext() {
            while (!stack.isEmpty()) {
                NestedIntegerImpl curr = stack.peek();
                if (curr.isInteger()) {
                    return true;
                }
                stack.pop();

                for (int i = curr.getList().size() - 1; i >= 0; i--) {
                    stack.push(curr.getList().get(i));
                }

            }
            return false;
        }

        @Override public Integer next() {
            return stack.pop().getInteger();
        }
    }
}
