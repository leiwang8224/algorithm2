import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leiwang on 3/11/18.
 */

/**
 * Given a string s, partition s such that every substring of the partition is a palindrome.

 Return the minimum cuts needed for a palindrome partitioning of s.

 Example:

 Input: "aab"
 Output: 1
 Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.


 */
public class PalindromePartition2 {
    public static void main(String[] args) {
        String str = "aabaa";
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        generatePalindrome(result, new ArrayList<String>(), str);
        for (ArrayList<String> subList : result) {
            System.out.println(Arrays.toString(subList.toArray()));
        }
    }

    private static void generatePalindrome(ArrayList<ArrayList<String>> result, ArrayList<String> strings, String str) {
        System.out.println("entering generatePalindrome strings = " + Arrays.toString(strings.toArray()));
        if (str.length() == 0) {
            result.add(new ArrayList<>(strings));
            System.out.println("adding to result strings = " + Arrays.toString(strings.toArray()));
            return;
        }
        for (int i = 0; i < str.length(); i ++) {
            if (isPalindrome(str.substring(0,i+1))) {
                strings.add(str.substring(0,i+1));
                System.out.println("begin for loop add to strings = " + Arrays.toString(strings.toArray()));
                generatePalindrome(result,strings,str.substring(i + 1));
                strings.remove(strings.size()-1);
                System.out.println("end for loop remove from strings = " + Arrays.toString(strings.toArray()));
            }
        }
    }

    private static boolean isPalindrome(String str) {
        for (int i = 0; i < str.length() / 2; i ++) {
            if (str.charAt(i) != str.charAt(str.length()-1-i))
                return false;
        }
        return true;
    }
}
