package CTCI.Arrays;

//Implement algorithm to determine a string has all unique chars.
public class IsUnique {
    public static void main(String[] args) {
        System.out.println(isUnique("abcdefa"));
    }

    /**
     * Use array of ASCII chars to keep track of what is already in the string
     * @param str
     * @return
     */
    private static boolean isUnique(String str) {
        boolean[] chars = new boolean[26];

        for (Character ch : str.toCharArray()) {
            if (chars[ch - 'a']) return false;
            chars[ch - 'a'] = true;
        }

        return true;
    }
}
