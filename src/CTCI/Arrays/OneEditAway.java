package CTCI.Arrays;

//There are three types of edits: insert a character, remove a character,
//or replace a character. Given two strings, write a function to check
//if they are one edit away.
public class OneEditAway {
    public static void main(String[] args) {
        System.out.println(oneEditAway("abc","abcd"));
        System.out.println(oneEditAway2("abc","abcd"));
    }

    private static boolean oneEditAway2(String first, String second) {
        // length checks
        if (Math.abs(first.length() - second.length()) > 1) return false;

        // get shorter and longer string
        String str1 = first.length() < second.length() ? first : second;
        String str2 = first.length() < second.length() ? second : first;

        int index1 = 0;
        int index2 = 0;

        boolean foundDiff = false;

        while (index2 < str2.length() && index1 < str1.length()) {
            if (str1.charAt(index1) != str2.charAt(index2)) {
                // ensure that this is the first diff found
                if (foundDiff) return false;
                foundDiff = true;
                if (str1.length() == str2.length()) {
                    // on replace, move shorter pointer
                    index1 ++;
                }
            } else {
                index1++; // if matching, move shorter pointer
            }
            index2++;  // always move pointer for longer string
        }
        return true;
    }

    private static boolean oneEditReplace(String s1, String s2) {
        boolean foundDiff = false;

        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                // second char that is different
                // more than one edit so return false
                if (foundDiff) return false;
            }
            // if any character is different
            foundDiff = true;
        }
        return true;
    }

    private static boolean oneEditInsert(String s1, String s2) {
        int index1 = 0;
        int index2 = 0;
        while (index2 < s2.length() && index1 < s1.length()) {
            if (s1.charAt(index1) != s2.charAt(index2)) {
                if (index1 != index2) return false;
                index2++;
            } else {
                index1++;
                index2++;
            }
        }
        return true;
    }

    private static boolean oneEditAway (String first, String second) {
        if (first.length() == second.length()) return oneEditReplace(first,second);
        else if (first.length() + 1 == second.length()) return oneEditInsert(first, second);
        else if (first.length() - 1 == second.length()) return oneEditInsert(second, first);
        return false;
    }
}
