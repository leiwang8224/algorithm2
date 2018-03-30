import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leiwang on 3/12/18.
 */
public class LetterCasePermutationSoln2 {
    public static void main (String args[]) {
        String str = "a1b2";
        ArrayList<String> result = new ArrayList<>();
        int len = str.length();
        result.add(str);
        // BackTrack solution
        backtrack(str, result, new String(), 0);

        for (String subString : result) {
            System.out.println(subString);
        }
    }

    private static void backtrack(String str, ArrayList<String> result, String curr, int index) {
        System.out.println("Entering backtrack result = " + Arrays.toString(result.toArray()));
        if(runCheck(result,curr,str.length())){
            return;
        }
        // StringBuffer to manipulate immutable Strings
        StringBuffer strBuilder = new StringBuffer();
        strBuilder.append(curr);
        for(int i = index; i<str.length(); i++){
            int num = (int) str.charAt(i);
            boolean check = (str.charAt(i) < '0') || (str.charAt(i) > '9');
            // If the character is an upper/lower case letter
            if(check){
                // Add change-cased letter
                // strBuilder is like an arraylist within the arraylist result
                strBuilder.append(changeCase(num));
                System.out.println("1 calling backtrack appending letter strBuilder = " + strBuilder.toString() + " result = " + Arrays.toString(result.toArray()));
                backtrack(str, result, strBuilder.toString(), i + 1);
                // Delete new letter and revert (so both permutations included)
                strBuilder.delete(strBuilder.length()-1,strBuilder.length());
                strBuilder.append(str.charAt(i));
                // If end conditions are satisfied, add to list
                if(runCheck(result,strBuilder.toString(),str.length())){
                    return;
                }
            }
            // ELSE accounts for non-letter characters 0-9
            else {
                // Appends the number
                System.out.println("2 calling backtrack appending number strBuilder = " + strBuilder.toString() + " result = " + Arrays.toString(result.toArray()));

                strBuilder.append(str.charAt(i));
                // If end conditions are satisfied, add to list
                if(runCheck(result,strBuilder.toString(),str.length())){
                    return;
                }
            }
        }
    }

    private static char changeCase(int num) {
        char c;
        // 'A'-'a' = 32
        if(64<num && num<91){
            num+=32;
        }
        else{
            num-=32;
        }
        c = (char) num;
        return c;
    }

    private static boolean runCheck(ArrayList<String> result, String s, int length) {
        if(!result.contains(s) && s.length() == length) {
            result.add(s);
            return true;
        }
        return false;
    }
}
