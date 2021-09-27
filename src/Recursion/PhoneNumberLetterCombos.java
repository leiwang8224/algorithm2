package Recursion;

import javax.print.DocFlavor;
import java.security.DigestInputStream;
import java.util.*;

/**
 * Created by leiwang on 3/28/18.
 */
public class PhoneNumberLetterCombos {
    private static final String[] KEYS =
            {"","", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    public static void main(String args[]) {
//        System.out.println("result 1");
//        List<String> res = letterCombo("123");
//        for (String result : res) {
//            System.out.println(result);
//        }
        System.out.println("result 2");

        List<String> result2 = findCombo("234");
        for (String resultInResult2: result2) {
            System.out.println(resultInResult2);
        }


    }
    
    private static List<String> letterCombo(String digits) {
        List<String> result = new LinkedList<>();
        combo("", digits, 0, result);
        return result;
    }

    private static void combo(String prefix, String digits, int offset, List<String> result) {
        if (offset >= digits.length()) {
            result.add(prefix);
            return;
        }

        String letters = KEYS[(digits.charAt(offset) - '0')];
        for (int i = 0; i < letters.length(); i ++) {
            combo(prefix + letters.charAt(i), digits, offset+1, result);
        }
    }

    /**
     * Use FIFO Queue
     * @param digits
     * @return
     */
    private static List<String> letterCombosBFS(String digits) {
        LinkedList<String> ans = new LinkedList<>();
        if (digits.isEmpty()) return ans;
        String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ans.add("");
        for(int index =0; index<digits.length(); index++){
            int x = Character.getNumericValue(digits.charAt(index));
            while(ans.peekFirst().length()==index){
                String t = ans.removeLast();
                for(char s : mapping[x].toCharArray())
                    ans.add(t+s);
            }
        }
        return ans;
    }


    /**
     * In forming strings we do not use the index as one of the parameters for recursion
     * Just pass in empty string to use as a state indicator of what index we are on
     */
    public static List<String> letterCombinations(String digits) {
        List<String> output = new ArrayList<String>();

        if (digits.length() != 0)
            backtrack("", digits, output);
        return output;
    }

    /**
     * backtrack combo starts with empty string
     * 1. base condition when the digits length becomes 0 add combo to result
     * 2. else
     *      - extract first digit
     *      - get the letters for the first digit
     *      - iterate through the letters by taking substring each for loop
     *      - append to the combo string and recurse with the digits (first digit cut off)
     */
    public static void backtrack(String combination, String nextDigits, List<String> output) {
        Map<String, String> phone = new HashMap<String, String>() {{
            put("2", "abc");
            put("3", "def");
            put("4", "ghi");
            put("5", "jkl");
            put("6", "mno");
            put("7", "pqrs");
            put("8", "tuv");
            put("9", "wxyz");
        }};

        // if there is no more digits to check
        if (nextDigits.length() == 0) {
            // the combination is done
            output.add(combination);
        }
        // if there are still digits to check
        else {
            // iterate over all letters which map
            // the next available digit
            String firstDigit = nextDigits.substring(0, 1);
            String letters = phone.get(firstDigit);
            for (int index = 0; index < letters.length(); index++) {
                String letter = phone.get(firstDigit).substring(index, index + 1);
                // append the current letter to the combination
                // and proceed to the next digits
                // substring(startIndex) returns the substring from startIndex to end
                // essentially crop out from the first letter
                backtrack(combination + letter, nextDigits.substring(1), output);
            }
        }
    }

    /**
     * recursion solution using digit index, note that numbers 0 and 1 does not have any letters
     * so empty string is used for 0 and 1
     */
    public static List<String> findCombo(String digits) {
        // sub empty strings for the numbers 0 and 1
        final String[] KEYS = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
        List<String> result = new LinkedList<>();
        if (digits.length() == 0) return result;
        combination("", digits, 0, result, KEYS);
        return result;

    }
    private static void combination(String prefix,
                                    String digits,
                                    int digitIndex,
                                    List<String> result,
                                    String[] KEYS) {
        if (digitIndex >= digits.length()) {
            result.add(prefix);
            return;
        }
        // get the letters from the key
        String lettersForCurSelectedNumber = KEYS[(digits.charAt(digitIndex) - '0')];
        // iterate through the letters and recurse onto the next digit index
        for (int letterIndex = 0; letterIndex < lettersForCurSelectedNumber.length(); letterIndex++) {
            combination(prefix + lettersForCurSelectedNumber.charAt(letterIndex),
                    digits,
                    digitIndex + 1,
                    result,
                    KEYS);
        }
    }

}
