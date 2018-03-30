import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leiwang on 3/12/18.
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
