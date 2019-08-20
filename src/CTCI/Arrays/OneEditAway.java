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
        String shorterString = first.length() < second.length() ? first : second;
        String longerString = first.length() < second.length() ? second : first;

        int shorterStringIndex = 0;
        int longerStringIndex = 0;

        boolean foundDiff = false;

        while (longerStringIndex < longerString.length() &&
               shorterStringIndex < shorterString.length()) {
            if (shorterString.charAt(shorterStringIndex) !=
                longerString.charAt(longerStringIndex)) {
                // ensure that this is the first diff found
                if (foundDiff) return false;
                foundDiff = true;
                if (shorterString.length() == longerString.length()) {
                    // on replace, move shorter pointer
                    shorterStringIndex ++;
                }
            } else {
                shorterStringIndex++; // if matching, move shorter pointer
            }
            longerStringIndex++;  // always move pointer for longer string
        }
        return true;
    }

    /**
     * Find out if there is only one letter different, if more than
     * one letter return false.
     * @param s1
     * @param s2
     * @return
     */
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
            // if char is different and two indices are different return false
            if (s1.charAt(index1) != s2.charAt(index2)) {
                // if indices and chars are different, return false
                // there is more than 1 insert needed to match
                if (index1 != index2) return false;
                // if indices are the same and chars are different, increment index 2
                index2++;
            } else {
                // increment both indices if chars are the same
                index1++;
                index2++;
            }
        }
        return true;
    }

    private static boolean oneEditAway (String first, String second) {
        // if lengths are equal, only check replacing one char
        if (first.length() == second.length())
            return oneEditReplace(first,second);
        // if length a is greater than length b, try inserting into b
        else if (first.length() + 1 == second.length())
            return oneEditInsert(first, second);
        // if length a is less than length b, try inserting into a
        else if (first.length() - 1 == second.length())
            return oneEditInsert(second, first);
        return false;
    }
}
