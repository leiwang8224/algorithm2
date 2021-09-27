package AlgoExpert.Easy;

public class PalindromeCheck {
    public static void main(String[] args) {

    }

    // O(n) time | O(1) space
    // iterate from both sides of string
    private boolean isPalindrome(String str) {
        int startIndex = 0;
        int endIndex = str.length()-1;

        while (startIndex < endIndex) {
            if (str.charAt(startIndex) != str.charAt(endIndex))
                return false;
            startIndex++;
            endIndex--;
        }

        return true;
    }

    // O(n) time | O(n) space
    // recursive to iterate from both sides of string
    private static boolean isPalindrome2(String str) {
        return isPalindrome2(str, 0);
    }

    private static boolean isPalindrome2(String str, int startIndex) {
        int endIndex = str.length() - 1 - startIndex;
        return startIndex >= endIndex ||
                str.charAt(startIndex) == str.charAt(endIndex) &&
                isPalindrome2(str, startIndex + 1);
    }

    // O(n) time | O(n) space
    // form reversed string and check if equal to original
    private static boolean isPalindrome3(String str) {
        StringBuilder reverseStr = new StringBuilder();
        for (int index = str.length() - 1; index >= 0; index--)
            reverseStr.append(str.charAt(index));
        return str.equals(reverseStr.toString());
    }

    // O(n^2) time | O(n) space
    private static boolean isPalindrome4(String str) {
        String reversedStr = "";
        for (int index = str.length() -1; index >= 0; index--)
            reversedStr += str.charAt(index);
        return str.equals(reversedStr);
    }

}
