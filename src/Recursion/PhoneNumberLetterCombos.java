package Recursion;

import javax.print.DocFlavor;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by leiwang on 3/28/18.
 */
public class PhoneNumberLetterCombos {
    private static final String[] KEYS =
            {"","", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    public static void main(String args[]) {
        List<String> res = letterCombo("123");
        for (String result : res) {
            System.out.println(result);
        }
    }
    
    private static List<String> letterCombo(String digits) {
        List<String> ret = new LinkedList<>();
        combo("", digits, 0, ret);
        return ret;
    }

    private static void combo(String prefix, String digits, int offset, List<String> ret) {
        if (offset >= digits.length()) {
            ret.add(prefix);
            return;
        }

        String letters = KEYS[(digits.charAt(offset) - '0')];
        for (int i = 0; i < letters.length(); i ++) {
            combo(prefix + letters.charAt(i), digits, offset+1, ret);
        }
    }

    /**
     * Use FIFO Queue
     * @param digits
     * @return
     */
    private List<String> letterCombos2(String digits) {
        LinkedList<String> ans = new LinkedList<>();
        if (digits.isEmpty()) return ans;
        String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ans.add("");
        for(int i =0; i<digits.length();i++){
            int x = Character.getNumericValue(digits.charAt(i));
            while(ans.peek().length()==i){
                String t = ans.remove();
                for(char s : mapping[x].toCharArray())
                    ans.add(t+s);
            }
        }
        return ans;
    }
}
