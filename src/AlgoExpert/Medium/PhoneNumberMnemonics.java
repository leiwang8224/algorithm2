package AlgoExpert.Medium;
import java.util.*;

/**
 * Also PhoneNumberLetterCombos.java'
 * Recursive is the most straightforward solution it seems
 */
public class PhoneNumberMnemonics {
    public static Map<Character, String[]> DIGIT_LETTERS = new HashMap<>();

    static {
        DIGIT_LETTERS.put('0', new String[] {"0"});
        DIGIT_LETTERS.put('1', new String[] {"1"});
        DIGIT_LETTERS.put('2', new String[] {"a", "b", "c"});
        DIGIT_LETTERS.put('3', new String[] {"d", "e", "f"});
        DIGIT_LETTERS.put('4', new String[] {"g", "h", "i"});
        DIGIT_LETTERS.put('5', new String[] {"j", "k", "l"});
        DIGIT_LETTERS.put('6', new String[] {"m", "n", "o"});
        DIGIT_LETTERS.put('7', new String[] {"p", "q", "r", "s"});
        DIGIT_LETTERS.put('8', new String[] {"t", "u", "v"});
        DIGIT_LETTERS.put('8', new String[] {"w", "x", "y", "z"});
    }

    /**
     * Use array of string to store results so that it could be joined when
     * there is enough characters, String.join(array).
     */
    private ArrayList<String> phoneNumber(String phoneNumber) {
        // resulting mnemonic should have the same length as the phone number
        String[] curMnemonic = new String[phoneNumber.length()];
        // initialize current result as all 0's, will be an array of Strings with len 1 (char)
        Arrays.fill(curMnemonic, "0");

        ArrayList<String> result = new ArrayList<>();
        phoneNumberMnemonicsHelper(0, phoneNumber, curMnemonic, result);
        return result;
    }

    // O(4^n) time | O(4^n) space
    private void phoneNumberMnemonicsHelper(int phoneNumberIdx,
                                            String phoneNumber,
                                            String[] curMnemonic,
                                            ArrayList<String> result) {
        if (phoneNumberIdx == phoneNumber.length()) {
            // build the mnemonic to string from array
            String mnemonic = String.join("", curMnemonic);
            result.add(mnemonic);
        } else {
            // get the number at the specified index
            char digit = phoneNumber.charAt(phoneNumberIdx);
            // get the letters from the map based on the digit
            String[] letters = DIGIT_LETTERS.get(digit);
            // iterate through the letters and put in mnemonics array
            for (String letter : letters) {
                // update the current result with the letter from map
                curMnemonic[phoneNumberIdx] = letter;
                // recurse by incrementing the index with the current result
                phoneNumberMnemonicsHelper(phoneNumberIdx + 1,
                                                        phoneNumber,
                                                        curMnemonic,
                                                        result);
            }
        }
    }

    // other method
    private static final String[] KEYS =
            {"0","1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    private static List<String> letterCombo(String phoneNumber) {
        List<String> result = new LinkedList<>();
        combo("", phoneNumber, 0, result);
        return result;
    }

    private static void combo(String prefix,
                              String phoneNumber,
                              int phoneNumberOffset,
                              List<String> result) {
        if (phoneNumberOffset >= phoneNumber.length()) {
            result.add(prefix);
            return;
        }

        // translate phone digit into indices for KEYS
        String letters = KEYS[(phoneNumber.charAt(phoneNumberOffset) - '0')];
        // iterate through the letters and call recursive
        for (int i = 0; i < letters.length(); i ++) {
            combo(prefix + letters.charAt(i), phoneNumber, phoneNumberOffset+1, result);
        }
    }

    /**
     * My Solution
     */
    public ArrayList<String> phoneNumberMnemonicsMySol(String phoneNumber) {
        String[] MAP = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ArrayList<String> result = new ArrayList<>();

        genMnemonics(phoneNumber, 0, MAP, result, "");
        // Write your code here.
        return result;
    }

    private void genMnemonics(String phoneNumber, int idx, String[] MAP, ArrayList<String> result, String curResult) {
        if (curResult.length() == phoneNumber.length()) {
            result.add(curResult);
            return;
        }

        String strAtChar = MAP[phoneNumber.charAt(idx) - '0'];
        for (int index = 0; index < strAtChar.length(); index++) {
            genMnemonics(phoneNumber, idx + 1, MAP, result, curResult+strAtChar.charAt(index));
        }
    }
}
