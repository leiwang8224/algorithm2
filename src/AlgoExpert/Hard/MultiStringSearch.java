package AlgoExpert.Hard;
import java.util.*;
public class MultiStringSearch {
    //O(bns)time | O(n) space
    public static List<Boolean> multiStringSearch(String bigString,
                                                  String[] smallStrings) {
        List<Boolean> solution = new ArrayList<>();
        for (String smallString : smallStrings) {
            solution.add(isInBigString(bigString, smallString));
        }
        return solution;
    }

    private static Boolean isInBigString (String bigString, String smallString) {
        for (int i = 0; i < bigString.length(); i++) {
            if (i + smallString.length() > bigString.length()) {
                break;
            }
            if (isInBigString(bigString, smallString, i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInBigString(String bigString, String smallString, int startIdx) {
        int leftBigIdx = startIdx;
        int rightBigIdx = startIdx + smallString.length() - 1;
        int leftSmallIdx = 0;
        int rightSmallIdx = smallString.length() - 1;

        while (leftBigIdx <= rightBigIdx) {
            if (bigString.charAt(leftBigIdx) != smallString.charAt(leftSmallIdx) ||
            bigString.charAt(rightBigIdx) != smallString.charAt(rightSmallIdx)) {
                return false;
            }

            leftBigIdx ++;
            rightBigIdx ++;
            leftSmallIdx ++;
            rightSmallIdx --;
        }
        return true;
    }

    //O(b^2 + ns) time | O(b^2 + n) space
    List<Boolean> multiStringSearch2(String bigString, String[] smallStrings) {
        ModifiedSuffixTrie modifiedSuffixTrie = new ModifiedSuffixTrie(bigString);
        List<Boolean> solution = new ArrayList<>();

        for (String smallString : smallStrings) {
            solution.add(modifiedSuffixTrie.contains(smallString));
        }

        return solution;
    }

    class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
    }

    class ModifiedSuffixTrie{
        TrieNode root = new TrieNode();

        ModifiedSuffixTrie(String str) {
            populateModifiedSuffixTrieFrom(str);
        }

        void populateModifiedSuffixTrieFrom(String str) {
            for (int i = 0; i < str.length(); i++) {
                insertSubstringStartingAt(i, str);
            }
        }

        void insertSubstringStartingAt(int i, String str) {
            TrieNode node = root;
            for (int j = i; j < str.length(); j++) {
                char letter = str.charAt(j);
                if (!node.children.containsKey(letter)) {
                    TrieNode newNode = new TrieNode();
                    node.children.put(letter, newNode);
                }
                node = node.children.get(letter);
            }
        }

        boolean contains(String str) {
            TrieNode node = root;
            for (int i = 0; i < str.length(); i++) {
                char letter = str.charAt(i);
                if (!node.children.containsKey(letter)) return false;
                node = node.children.get(letter);
            }
            return true;
        }
    }

    // O(ns + bs) time | O(ns) space
    List<Boolean> multiStringSearchTrie2(String bigString, String[] smallStrings) {
        NewTrie trie = new NewTrie();
        for (String smallString : smallStrings) {
            trie.insert(smallString);
        }

        Set<String> containedStrings = new HashSet<>();
        for (int i = 0; i < bigString.length(); i++) {
            findSmallStringIn(bigString, i, trie, containedStrings);
        }

        List<Boolean> solution = new ArrayList<>();
        for (String str : smallStrings) {
            solution.add(containedStrings.contains(str));
        }
        return solution;
    }

    private void findSmallStringIn(String string, int startIdx, NewTrie trie, Set<String> containedStrings) {
        NewTrieNode curNode = trie.root;
        for (int i = startIdx; i < string.length(); i++) {
            char curChar = string.charAt(i);
            if (!curNode.children.containsKey(curChar)) break;
            curNode = curNode.children.get(curChar);
            if (curNode.children.containsKey(trie.endSymbol)) {
                containedStrings.add(curNode.word);
            }
        }
    }

    class NewTrieNode {
        Map<Character, NewTrieNode> children = new HashMap<>();
        String word;
    }

    class NewTrie {
        NewTrieNode root = new NewTrieNode();
        char endSymbol = '*';

        void insert(String str) {
            NewTrieNode node = root;
            for (int i = 0; i < str.length(); i++) {
                char letter = str.charAt(i);
                if (!node.children.containsKey(letter)) {
                    NewTrieNode newNode = new NewTrieNode();
                    node.children.put(letter, newNode);
                }
                node = node.children.get(letter);
            }
            node.children.put(endSymbol, null);
            node.word = str;
        }
    }

}
