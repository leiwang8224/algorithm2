package Arrays;

import Stack.NestedIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by leiwang on 4/14/18.
 */
// TODO don't understand this one
public class MiniParser {
//    Given a nested list of integers represented as a string,
//    implement a parser to deserialize it.
//
//    Each element is either an integer, or a list -- whose elements
//    may also be integers or other lists.
//
//            Note: You may assume that the string is well-formed:
//
//    String is non-empty.
//    String does not contain white spaces.
//    String contains only digits 0-9, [, - ,, ].

    public static void main(String args[]) {
        List<NestedInteger> list1 = new ArrayList<>();
        List<NestedInteger> list2 = new ArrayList<>();
        List<NestedInteger> list3 = new ArrayList<>();
        list1.add(new NestedInteger(1));
        list1.add(new NestedInteger(2));
        list1.add(new NestedInteger(3));

        list2.add(new NestedInteger(4));
        list2.add(new NestedInteger(5));
        list2.add(new NestedInteger(6));

        list3.add(new NestedInteger(7));
        list3.add(new NestedInteger(8));
        list3.add(new NestedInteger(9));
    }

    private static class NestedInteger {
        private List<NestedInteger> list;
        private Integer integer;

        public NestedInteger(List<NestedInteger> list){
            this.list = list;
        }

        public void add(NestedInteger nestedInteger) {
            if(this.list != null){
                this.list.add(nestedInteger);
            } else {
                this.list = new ArrayList();
                this.list.add(nestedInteger);
            }
        }

        public void setInteger(int num) {
            this.integer = num;
        }

        public NestedInteger(Integer integer){
            this.integer = integer;
        }

        public NestedInteger() {
            this.list = new ArrayList();
        }

        public boolean isInteger() {
            return integer != null;
        }

        public Integer getInteger() {
            return integer;
        }

        public List<NestedInteger> getList() {
            return list;
        }

        public String printNi(NestedInteger thisNi, StringBuilder sb){
            if(thisNi.isInteger()) {
                sb.append(thisNi.integer);
                sb.append(",");
            }
            sb.append("[");
            for(NestedInteger ni : thisNi.list){
                if(ni.isInteger()) {
                    sb.append(ni.integer);
                    sb.append(",");
                }
                else {
                    printNi(ni, sb);
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }//    public interface NestedInteger {
//        // Constructor initializes an empty nested list.
//        public NestedInteger();
//
//                *     // Constructor initializes a single integer.
//                *     public NestedInteger(int value);
//        *
//                *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
//                *     public boolean isInteger();
//        *
//                *     // @return the single integer that this NestedInteger holds, if it holds a single integer
//                *     // Return null if this NestedInteger holds a nested list
//                *     public Integer getInteger();
//        *
//                *     // Set this NestedInteger to hold a single integer.
//                *     public void setInteger(int value);
//        *
//                *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
//                *     public void add(NestedInteger ni);
//        *
//                *     // @return the nested list that this NestedInteger holds, if it holds a nested list
//                *     // Return null if this NestedInteger holds a single integer
//                *     public List<NestedInteger> getList();
//        * }

    public NestedInteger deserialize(String s) {
        if (s.isEmpty())
            return null;
        if (s.charAt(0) != '[') // ERROR: special case
            return new NestedInteger(Integer.valueOf(s));

        Stack<NestedInteger> stack = new Stack<>();
        NestedInteger curr = null;
        int l = 0; // l shall point to the start of a number substring;
        // r shall point to the end+1 of a number substring
        for (int r = 0; r < s.length(); r++) {
            char ch = s.charAt(r);
            if (ch == '[') {
                if (curr != null) {
                    stack.push(curr);
                }
                curr = new NestedInteger();
                l = r+1;
            } else if (ch == ']') {
                String num = s.substring(l, r);
                if (!num.isEmpty())
                    curr.add(new NestedInteger(Integer.valueOf(num)));
                if (!stack.isEmpty()) {
                    NestedInteger pop = stack.pop();
                    pop.add(curr);
                    curr = pop;
                }
                l = r+1;
            } else if (ch == ',') {
                if (s.charAt(r-1) != ']') {
                    String num = s.substring(l, r);
                    curr.add(new NestedInteger(Integer.valueOf(num)));
                }
                l = r+1;
            }
        }

        return curr;
    }
}
