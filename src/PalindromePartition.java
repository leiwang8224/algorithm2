import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leiwang on 3/11/18.
 */
public class PalindromePartition {
    // return all combinations of palindrome
    public static void main(String[] args) {
        String str = "aabaa";
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        generatePalindrome(result, new ArrayList<String>(), str, 0);
        for (ArrayList<String> subList : result) {
            System.out.println(Arrays.toString(subList.toArray()));
        }

    }

    private static void generatePalindrome(ArrayList<ArrayList<String>> result, ArrayList<String> strings, String str, int startIndex) {
        System.out.println("entering generatePalindrome with strings = " + Arrays.toString(strings.toArray()));
        if (startIndex == str.length()) {
            // finished iterating through the string so return
            result.add(new ArrayList<>(strings));
            System.out.println("entering generatePalindrome with strings = " + Arrays.toString(strings.toArray()));
        } else {
            for (int i = startIndex; i < str.length(); i ++) {
                if (isPalindrome(str,startIndex,i)) {
                    strings.add(str.substring(startIndex,i+1));
                    System.out.println("entering for loop with add strings = " + Arrays.toString(strings.toArray()));
                    generatePalindrome(result,strings,str,i + 1);
                    strings.remove(strings.size()-1);
                    System.out.println("exit for loop with remove strings = " + Arrays.toString(strings.toArray()));
                }
            }
        }
    }

    private static boolean isPalindrome(String str, int low, int high) {
        while (low < high) {
            if (str.charAt(low++) != str.charAt(high--)) return false;
        }
        return true;
    }
}
