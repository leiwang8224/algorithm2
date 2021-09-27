package AlgoExpert.Medium;
import java.util.*;
public class ReverseWordsInString {
    public static void main(String[] args) {

    }

    //O(n) time | O(n) space
    private static String reverseWordsString(String str) {
        List<String> result = new ArrayList<>();
        int startOfWord = 0;

        // iterate idx from 0 to end
        for (int idxEndOfWord = 0; idxEndOfWord < str.length(); idxEndOfWord++) {
            char ch = str.charAt(idxEndOfWord);

            // whenever a space is encountered we add the substring
            // and set startOfWord to the index of the space
            if (ch == ' ') {
                result.add(str.substring(startOfWord, idxEndOfWord));
                // set startOfWord to the index of space so that we can
                // process it in the next iteration
                startOfWord = idxEndOfWord;
                // when we hit the space at the startOfWord, we add space
                // then update startOfWord to next index
            } else if (str.charAt(startOfWord) == ' ') {
                result.add(" ");
                startOfWord = idxEndOfWord;
            }
        }

        // finally add from the startOfWord to the end of string
        result.add(str.substring(startOfWord));

        // reverse the result for return
        Collections.reverse(result);
        // join the list with no delimiter (empty string)
        return String.join("", result);
    }
    
    // O(n) time | O(n) space
    // implementation based on char array
    // 1. reverse the whole string with spaces in char array format
    // 2. for each space observed, reverse the char array (Word)
    // 3. return the String by building the string from char array
    private static String reverseWordsString2(String str) {
        char[] charsArray = str.toCharArray();
        // reverse the char array (the whole string with spaces)
        reverseListRange(charsArray, 0, charsArray.length - 1);

        int startOfWord = 0;
        while(startOfWord < charsArray.length) {
            // for each char iterate to the end of the word as long as there is no space
            int endOfWord = startOfWord;
            // find the end of word
            while (endOfWord < charsArray.length && charsArray[endOfWord] != ' ') {
                endOfWord += 1;
            }

            // when space is seen, reverse the char array because we saw a word
            reverseListRange(charsArray, startOfWord, endOfWord - 1);

            // inc startOfWord by set the start of word to the index of space
            startOfWord = endOfWord + 1;
        }

        return new String(charsArray);
    }

    private static char[] reverseListRange(char[] charList, int start, int end) {
        while (start < end) {
            char temp = charList[start];
            charList[start] = charList[end];
            charList[end] = temp;
            start ++;
            end --;
        }
        return charList;
    }
}
