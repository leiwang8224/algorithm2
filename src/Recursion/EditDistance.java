package Recursion;

/**
 * Created by leiwang on 3/25/18.
 */
public class EditDistance {
    public static void main(String[] args) {
        String str1 = "MART";
        String str2 = "KARMA";
        
        System.out.println("edit min distance = " + editDistance(str1, str2, 0, 0));//        editDistance(str1, str2, 0, 0);
        System.out.println("edit min distance2 = " + editDistance2(str1, str2, str1.length(), str2.length()));
    }

    /**
     * 3 options
     * - delete the char
     * - replace str1 char with str2 char
     * - insert the char from str1 to str2
     * @param str1
     * @param str2
     * @return
     */
    //TODO problem with this method
    private static int editDistance(String str1, String str2, int charPosStr1, int charPosStr2) {
        System.out.println("str1 length = " + str1.length() + " " + charPosStr1 + " str2 length = "
                + str2.length() + " " + charPosStr2);
        if (str1.length() == 0  || charPosStr1 == str1.length() || str1.charAt(charPosStr1) == '\0')
            return str2.length();
        if (str2.length() == 0  || charPosStr2 == str2.length() || str2.charAt(charPosStr2) == '\0')
            return str1.length();

        if (str1.charAt(charPosStr1) == str2.charAt(charPosStr2))
            return editDistance(str1, str2, charPosStr1 + 1, charPosStr2 + 1);

        int delete, replace, insert;
        delete = editDistance(str1, str2, charPosStr1 + 1, charPosStr2);
        replace = editDistance(str1, str2, charPosStr1 + 1, charPosStr2 + 1);
        insert = editDistance(str1, str2, charPosStr1, charPosStr2 + 1);
        return Math.min(delete,Math.min(replace,insert)) + 1;
    }

    /**
     * method from GeekforGeeks
     * https://www.geeksforgeeks.org/dynamic-programming-set-5-edit-distance/
     * @param str1
     * @param str2
     * @param charPosStr1
     * @param charPosStr2
     * @return
     */
    private static int editDistance2( String str1, String str2, int charPosStr1, int charPosStr2) {
        if (charPosStr1 == 0) return charPosStr2;
        if (charPosStr2 == 0) return charPosStr1;

        if (str1.charAt(charPosStr1-1) == str2.charAt(charPosStr2-1))
            return editDistance2(str1, str2, charPosStr1-1, charPosStr2-1);

        return 1 + Math.min(editDistance2(str1, str2, charPosStr1 - 1, charPosStr2), // insert
                Math.min(editDistance2(str1, str2, charPosStr1 - 1, charPosStr2 - 1), // remove
                        editDistance2(str1, str2, charPosStr1, charPosStr2 - 1))); // delete
    }
}
