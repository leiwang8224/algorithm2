package DFS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
//
//Given a String that represents the digits pressed on a classic cell phone keypad -
// return all possible letter combinations that the numbers could represent in an
// ArrayList of Strings. Check out the keypad and mapping below for reference.
//Mapping:
//        2 -> "abc"
//        3 -> "def"
//        4 -> "ghi"
//        5 -> "jkl"
//        6 -> "mno"
//        7 -> "pqrs"
//        8 -> "tuv"
//        9 -> "wxyz"
//
//        Example:
//
//        Input  : "34"
//        Output : [dg, dh, di, eg, eh, ei, fg, fh, fi]
public class GetStringsFromPhoneNums {
    public static void main(String[] args) {
        printArrayList(getStringsFromNums("34"));
    }

    private static void printArrayList(ArrayList<String> result) {
        for (String r : result) {
            System.out.println(r);
        }
    }

    private static ArrayList<String> getStringsFromNums(String digits) {
        HashMap<Character,String> mapping = new HashMap<>();

        mapping.put('2',"abc");
        mapping.put('3',"def");
        mapping.put('4',"ghi");
        mapping.put('5',"jkl");
        mapping.put('6',"mno");
        mapping.put('7',"pqrs");
        mapping.put('8',"tuv");
        mapping.put('9',"wxyz");

        class PhoneNode {
            String word;
            int digitCount;
            PhoneNode(String word, int count) {
                this.word = word;
                this.digitCount = count;
            }
        }

        // create the stack
        ArrayList<String> result = new ArrayList<>();
        Stack<PhoneNode> stack = new Stack<>();
        int len = digits.length();

        // push the first node into the stack
        for (Character ch : mapping.get(digits.charAt(0)).toCharArray()) {
            stack.push(new PhoneNode(String.valueOf(ch), 1));
        }

        // DFS
        while (!stack.isEmpty()) {
            PhoneNode node = stack.pop();
            // all digits are there (num digits = length), add result
            if (node.digitCount == len) result.add(node.word);
            else {
                for (Character ch : mapping.get(digits.charAt(node.digitCount)).toCharArray()) {
                    stack.push(new PhoneNode(node.word + ch, node.digitCount+1));
                }
            }
        }
        return result;
    }
}
