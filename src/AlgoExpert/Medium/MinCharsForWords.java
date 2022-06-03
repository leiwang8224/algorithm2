package AlgoExpert.Medium;
import java.util.*;

public class MinCharsForWords {
    // O(n * l) time | O(c) space - where n is the number of words,
    // l is the length of the longest word, and c is the number of
    // unique chars across all words
    //
    char[] minCharsForWords (String[] words) {
        HashMap<Character, Integer> maxCharsFreq = new HashMap<>();

        for (String word : words) {
            HashMap<Character, Integer> charFreq = countCharFreq(word);
            updateMaxFrequencies(charFreq, maxCharsFreq);
        }

        return makeArrayFromCharFreq(maxCharsFreq);
    }

    private char[] makeArrayFromCharFreq(HashMap<Character, Integer> charsFreq) {
        ArrayList<Character> characters = new ArrayList<>();

        for (Map.Entry<Character, Integer> freq : charsFreq.entrySet()) {
            char ch = freq.getKey();
            int charFreq = freq.getValue();

            for (int idx = 0; idx < charFreq; idx++) {
                characters.add(ch);
            }
        }

        char[] charArray = new char[characters.size()];

        for (int idx = 0; idx < characters.size(); idx++) {
            charArray[idx] = characters.get(idx);
        }

        return charArray;
    }

    private void updateMaxFrequencies(HashMap<Character, Integer> charFreq, HashMap<Character, Integer> maxCharsFreq) {
        for (Map.Entry<Character, Integer> freq : charFreq.entrySet()) {
            char ch = freq.getKey();
            int characterFreq = freq.getValue();

            if (maxCharsFreq.containsKey(ch)) {
                maxCharsFreq.put(ch, Math.max(characterFreq, maxCharsFreq.get(ch)));
            } else {
                maxCharsFreq.put(ch, characterFreq);
            }
        }
    }

    private HashMap<Character, Integer> countCharFreq(String string) {
        HashMap<Character, Integer> charFreq = new HashMap<>();

        for (char ch : string.toCharArray()) {
            charFreq.put(ch, charFreq.getOrDefault(ch, 0) + 1);
        }

        return charFreq;
    }

}
