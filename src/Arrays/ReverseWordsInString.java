package Arrays;

/**
 * Created by leiwang on 4/16/18.
 */
//Given an input string, reverse the string word by word.
//
//        For example,
//        Given s = "the sky is blue",
//        return "blue is sky the".
//

public class ReverseWordsInString {
    public static void main(String args[]) {
        String str = "the sky is blue";
        System.out.println("reversed string = " + reverseWordsInPlace(str));
        System.out.println("reversed string = " + reverseWords(str));
    }

    public static String reverseWordsInPlace(String s) {
        if (s.length() < 1) return s; // empty string
        int startIdx = 0;
        char[] str = s.toCharArray();
        // reverse whole string based on chars
        reverseString(str, 0, str.length - 1);
        // [e, u, l, b,  , s, i,  , y, k, s,  , e, h, t]
        System.out.println("reverseWordsInPlace1 = " + java.util.Arrays.toString(str));
        // reverse word one by one
        for (int i = 0; i < str.length; i++) {
            // if letter is valid and not 0 index, replace with space
            if (str[i] != ' ') {
                if (startIdx != 0) str[startIdx++] = ' ';
                int j = i;
                while (j < str.length && str[j] != ' ')
                    str[startIdx++] = str[j++];
                reverseString(str, startIdx - (j - i), startIdx - 1); // startIdx - 1, NOT startIdx;
                // C++ std::reverse : Reverses the order of the elements in the range [first, last)
                i = j;
            }
        }
        // [b, l, u, e,  , i, s,  , s, k, y,  , t, h, e]
        System.out.println("reverseWordsInPlace2 = " + java.util.Arrays.toString(str));

        return new String(str, 0, startIdx);
    }

    private static void reverseString(char[] str, int begin, int end) {
        for (; begin < end; begin++, end--) {
            char tmp = str[begin];
            str[begin] = str[end];
            str[end] = tmp;
        }
    }

    /**
     * create a new string and return
     * @param s
     * @return
     */
    public static String reverseWords(String s) {
        if (s == null) return null;

        char[] a = s.toCharArray();
        int n = a.length;

        // step 1. reverse the whole string
        reverse(a, 0, n - 1);
        // step 2. reverse each word
        reverseWords(a, n);
        // step 3. clean up spaces
        return cleanSpaces(a, n);
    }

    private static void reverseWords(char[] a, int n) {
        int i = 0, j = 0;

        while (i < n) {
            while (i < j || i < n && a[i] == ' ') i++; // skip spaces
            while (j < i || j < n && a[j] != ' ') j++; // skip non spaces
            reverse(a, i, j - 1);                      // reverse the word
        }
    }

    // trim leading, trailing and multiple spaces
    private static String cleanSpaces(char[] a, int n) {
        int i = 0, j = 0;

        while (j < n) {
            while (j < n && a[j] == ' ') j++;             // skip spaces
            while (j < n && a[j] != ' ') a[i++] = a[j++]; // keep non spaces
            while (j < n && a[j] == ' ') j++;             // skip spaces
            if (j < n) a[i++] = ' ';                      // keep only one space
        }

        return new String(a).substring(0, i);
    }

    // reverse a[] from a[i] to a[j]
    private static void reverse(char[] a, int i, int j) {
        while (i < j) {
            char t = a[i];
            a[i++] = a[j];
            a[j--] = t;
        }
    }
}
