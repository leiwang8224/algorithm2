import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leiwang on 3/12/18.
 */

/**
 * Given a string S, we can transform every letter individually to be lowercase or uppercase
 * to create another string.  Return a list of all possible strings we could create.

 Examples:
 Input: S = "a1b2"
 Output: ["a1b2", "a1B2", "A1b2", "A1B2"]

 Input: S = "3z4"
 Output: ["3z4", "3Z4"]

 Input: S = "12345"
 Output: ["12345"]

 Note:

 S will be a string with length at most 12.
 S will consist only of letters or digits.

 */
public class LetterCasePermutation {
    public static void main(String[] args) {
        String str = "a1b2";
        ArrayList<String> result = new ArrayList<>();
        createCasePermutation(result, str, 0);
        for (String subString : result) {
            System.out.println(subString);
        }
    }

    private static void createCasePermutation(ArrayList<String> strings, String str, int pos) {
        System.out.println("Entering createCasePermutation strings = " + Arrays.toString(strings.toArray()));
        if (pos == str.length()) {
            strings.add(str);
            System.out.println("Adding to strings = " + Arrays.toString(strings.toArray()));
            return;
        }
        if (str.charAt(pos) >= '0' && str.charAt(pos) <= '9') {
            createCasePermutation(strings, str, pos+1);
            return;
        }

        char[] chars = str.toCharArray();
        chars[pos] = Character.toLowerCase(chars[pos]);
        System.out.println("Calling first createCasePermutation chars = " + Arrays.toString(chars));
        createCasePermutation(strings, chars.toString(), pos + 1);

        chars[pos] = Character.toUpperCase(chars[pos]);
        System.out.println("Calling second createCasePermutation chars = " + Arrays.toString(chars));
        createCasePermutation(strings, chars.toString(), pos + 1);
    }
}
