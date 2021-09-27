package AlgoExpert.Medium;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupAnagrams {
    public static void main(String[] args) {

    }

    // O(w * n * log(n) + n *w * log(w)) time
    // O(wn) space
    // where w is the number of words and n is the length of the longest word
    private static List<List<String>> groupAnagrams(List<String> words) {
        if (words.size() == 0) return new ArrayList<List<String>>();

        List<String> sortedWords = new ArrayList<>();
        // for each word, sort the letters
        for (String word : words) {
            char[] charArray = word.toCharArray();
            Arrays.sort(charArray);
            String sortedWord = new String(charArray);
            sortedWords.add(sortedWord);
        }

        // create indices
        List<Integer> indices = IntStream.range(0, words.size()).boxed().collect(Collectors.toList());
        indices.sort((a, b) -> sortedWords.get(a).compareTo(sortedWords.get(b)));

        List<List<String>> result = new ArrayList<>();
        List<String> currentAnagramGroup = new ArrayList<>();
        String currentAnagram = sortedWords.get(indices.get(0));

        for (Integer index : indices) {
            String word = words.get(index);
            String sortedWord = sortedWords.get(index);

            if (sortedWord.equals(currentAnagram)) {
                currentAnagramGroup.add(word);
                continue;
            }

            result.add(currentAnagramGroup);
            currentAnagramGroup = new ArrayList<String>(Arrays.asList(word));
            currentAnagram = sortedWord;
        }
        result.add(currentAnagramGroup);

        return result;
    }

    // O(w * n * log(n)) time | O(wn) space
    private static List<List<String>> groupAnagramsMap(List<String> words) {
        Map<String, List<String>> anagramMap = new HashMap<>();
        // for each word, sort the letters and store as key in map
        // iterate through words and add to map if any key is found (after sorting the word)
        for (String word : words) {
            char[] charArray = word.toCharArray();
            // sort the char array using the Arrays library
            Arrays.sort(charArray);
            // create string from the char array
            String sortedWord = new String(charArray);

            if (anagramMap.containsKey(sortedWord)) {
                anagramMap.get(sortedWord).add(word);
            } else {
                // does not exist in the list of keys so add to key and the corresponding word
                // create new arraylist with the word as the first element
                anagramMap.put(sortedWord, new ArrayList<String>(Arrays.asList(word)));
            }
        }
        // simply return the map structure as arraylist
        return new ArrayList<>(anagramMap.values());
    }
}
