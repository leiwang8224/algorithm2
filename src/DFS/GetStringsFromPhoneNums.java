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
        System.out.println();
        printArrayList(getStringsFromNumsRecurse("34"));
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

//        PhoneNode that stores the possible String up to that point in the stack, along with the number of digits remaining.
        class PhoneNode {
            String word;
            int digitsUsed;
            PhoneNode(String word, int count) {
                this.word = word;
                this.digitsUsed = count;
            }
        }

        // create the stack
        ArrayList<String> result = new ArrayList<>();
        Stack<PhoneNode> stack = new Stack<>();
        int len = digits.length();

        // push the first node into the stack
        // the first node is the first char from the digits string, with char being used = 1
        for (Character ch : mapping.get(digits.charAt(0)).toCharArray()) {
            stack.push(new PhoneNode(String.valueOf(ch), 1));
        }

        // DFS
        while (!stack.isEmpty()) {
            PhoneNode node = stack.pop();
            // all digits are there (num digits = length), add result
            if (node.digitsUsed == len) result.add(node.word);
            else {
                for (Character ch : mapping.get(digits.charAt(node.digitsUsed)).toCharArray()) {
                    // increment the digitsUsed counter and add the char
                    stack.push(new PhoneNode(node.word + ch, node.digitsUsed + 1));
                }
            }
        }
        return result;
    }

    private static ArrayList<String> getStringsFromNumsRecurse(String digits) {
        HashMap<Character,String> mapping = new HashMap<>();

        mapping.put('2',"abc");
        mapping.put('3',"def");
        mapping.put('4',"ghi");
        mapping.put('5',"jkl");
        mapping.put('6',"mno");
        mapping.put('7',"pqrs");
        mapping.put('8',"tuv");
        mapping.put('9',"wxyz");
        ArrayList<String> result = new ArrayList<>();

        getStringFromNums(digits,mapping,result,0,"");

        return result;
    }

    private static void getStringFromNums(String digits, HashMap<Character, String> map, ArrayList<String> result, int indexOfDigits, String word) {
        if (indexOfDigits == digits.length()) {
            result.add(word);
            return;
        } else {
            Character digitAtPos = digits.charAt(indexOfDigits);
            String digitMappedToStr = map.get(digitAtPos);
            // get all chars in the String and add to the word
            // recurse on each char from digits mapping
            for (Character ch : digitMappedToStr.toCharArray()) {
                getStringFromNums(digits,map,result,indexOfDigits+1,word+ch);
            }
        }
    }
}
