package AlgoExpert.Medium;
import java.util.*;
public class SuffixTrie {
    static class TrieNode {
        // trie node class contains the char and the corresponding trie node
        Map<Character, TrieNode> children = new HashMap<>();
    }

    /**
     * Imagine a trie node:
     * map of trie node (no values) and corresponding char
     * to traverse, get children
     * DEF: Trie has a branch for each substring of the string
     *          * example: abcde
     *          * abcde
     *          * bcde
     *          * cde
     *          * de
     *          * d
     */
    static class SuffixTrieImpl {
        TrieNode root = new TrieNode();
        static char endSymbol = '*';

        public SuffixTrieImpl(String str) {
            populateSuffixTrieFrom(str);

        }

        // O(n ^ 2) time | O(n ^ 2) space
        private void populateSuffixTrieFrom(String str) {
            // for each letter start a branch
            for (int startIdxSubstring = 0; startIdxSubstring < str.length(); startIdxSubstring++) {
                insertSubstringStartingAt(startIdxSubstring, str);
            }
        }

        /**
         * startIndex iterates from beginning to end
         * example: abcde
         * abcde
         * bcde
         * cde
         * de
         * d
         *
         *               o
         *             / | \
         *            b  a  c
         *           /|  |   \
         *          c a  b    *
         *         /  |  |
         *        *   b  c
         *            |  |
         *            c  *
         *            |
         *            *
         */
        private void insertSubstringStartingAt(int insertIndex, String str) {
            // get the reference to the root for every insertIndex
            TrieNode node = root;
            // iterate starting from the insert index to the end of string
            for (int startIdx = insertIndex; startIdx < str.length(); startIdx++) {
                char letter = str.charAt(startIdx);
                // if children does not have the key, put key into map along
                // with corresponding node
                // if children does have the key, we still want to traverse the rest of the tree
                // and populate, just continue on that branch
                if (!node.children.containsKey(letter)) {
                    node.children.put(letter, new TrieNode());
                }
                // traverse down the trie
                node = node.children.get(letter);
            }
            // put the terminating char in the end with corresponding null node
            node.children.put(endSymbol, null);
        }

        // O(m) time | O(1) space
        private boolean contains(String str) {
            TrieNode node = root;
            // traverse down the branch to verify all chars are there
            for (int i = 0; i < str.length(); i++) {
                char letter = str.charAt(i);
                if (!node.children.containsKey(letter))
                    return false;
                // traverse down the trie
                node = node.children.get(letter);
            }
            return node.children.containsKey(endSymbol);
        }
    }
}
