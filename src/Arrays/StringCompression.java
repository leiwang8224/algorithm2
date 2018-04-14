package Arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leiwang on 4/14/18.
 */
public class StringCompression {
    public static void main(String args[]){
        String str = "abbcifbdbfss";
        System.out.println(compressString(str));
        System.out.println(compressChars(str.toCharArray()));
    }
//
//    Intuition
//
//    We will use separate pointers read and write to mark where we are reading and writing from.
//    Both operations will be done left to right alternately: we will read a contiguous group of characters,
//    then write the compressed version to the array. At the end, the position of the write head will be the
//    length of the answer that was written.
//
//    Algorithm
//
//    Let's maintain anchor, the start position of the contiguous group of characters we are currently reading.
//
//    Now, let's read from left to right. We know that we must be at the end of the block when we are at the
//    last character, or when the next character is different from the current character.
//
//    When we are at the end of a group, we will write the result of that group down using our write head.
//    chars[anchor] will be the correct character, and the length (if greater than 1) will be read - anchor + 1.
//    We will write the digits of that number to the array.
    private int compress(char[] chars) {
        int anchor = 0, write = 0;
        for (int read = 0; read < chars.length; read++) {
            if (read + 1 == chars.length || chars[read + 1] != chars[read]) {
                chars[write++] = chars[anchor];
                if (read > anchor) {
                    for (char c: ("" + (read - anchor + 1)).toCharArray()) {
                        chars[write++] = c;
                    }
                }
                anchor = read + 1;
            }
        }
        return write;
    }

    //TODO Wrong impl
    private static char[] compressChars(char[] chars) {
        int[] charsArray = new int[128];
        for (int i = 0; i < chars.length; i ++) {
            charsArray[chars[i]]++;
        }
        List<Character> charList = new ArrayList<>();
        for (int i = 0; i < charsArray.length; i ++) {
            charList.add(Character.toChars(i)[0]);
            String numStr = Integer.toString(charsArray[i]);
            for (int j = 0; j < numStr.length(); j ++) {
                charList.add(numStr.charAt(j));
            }
        }
        char[] result = new char[charList.size()];
        for (int index = 0; index < charList.size(); index ++) {
            result[index] = charList.get(index);
        }
        return result;
    }

    private static String compressString(String str) {
        int[] chars = new int[128];
        for (int i = 0; i < str.length(); i ++) {
            chars[str.charAt(i)]++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i ++) {
            if (chars[i] > 0) {
                sb.append(Character.toChars(i));
                sb.append(chars[i]);
            }
        }

        return sb.toString();
    }


}
