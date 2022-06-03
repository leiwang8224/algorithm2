package Trie;

import java.util.HashMap;

//               T
//             / | \
//            H  H  H
//            |  |  |
//            E  E  E
//            |  |
//            I  R
//            |  |
//            R  E
public class Trie4 {
    public static void main(String args[])
    {
        // Input keys (use only 'a' through 'z' and lower case)
        String keys[] = {"the", "a", "there", "answer", "any",
                         "by", "bye", "their"};

        String output[] = {"Not present in trie", "Present in trie"};


        new Trie();

        // Construct trie
        int i;
        for (i = 0; i < keys.length ; i++)
            Trie.insertWord(keys[i]);

        // Search for different keys
        if(Trie.searchWord("the") == true)
            System.out.println("the --- " + output[1]);
        else System.out.println("the --- " + output[0]);

        if(Trie.searchWord("these") == true)
            System.out.println("these --- " + output[1]);
        else System.out.println("these --- " + output[0]);

        if(Trie.searchWord("their") == true)
            System.out.println("their --- " + output[1]);
        else System.out.println("their --- " + output[0]);

        if(Trie.searchWord("thaw") == true)
            System.out.println("thaw --- " + output[1]);
        else System.out.println("thaw --- " + output[0]);

    }
    static class TrieNode {
        static Character c;
        static Boolean isLeaf = false;
        static HashMap<Character, TrieNode> children = new HashMap<>();

        TrieNode() {

        }

        TrieNode(Character c) {
            this.c = c;
        }
    }

    static class Trie {
        private static TrieNode root;

        Trie() {
            this.root = new TrieNode();
        }

        static void insertWord(String word) {
            if (word == null || word.length() < 1) return;

            // get reference to root
            TrieNode cur = root;
            // get children of root
            HashMap<Character,TrieNode> children = cur.children;

            // iterate through the string input
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                // check if dictionary has key, if not add,
                // else set cur pointer to the node that contains key
                if (children.containsKey(c)) {
                    cur = children.get(c);
                } else {
                    TrieNode node = new TrieNode(c);
                    children.put(c, node);
                    cur = node;
                }

                children = cur.children;

                // if at the end of trie, set leaf to true
                if (i == word.length() - 1) {
                    cur.isLeaf = true;
                }
            }
        }

        static boolean searchWord(String word) {
            TrieNode cur = root;
            HashMap<Character, TrieNode> children = cur.children;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (children.containsKey(c)) {
                    cur = children.get(c);
                    children = cur.children;
                } else {
                    return false;
                }
            }
            return cur.isLeaf;
        }

        static boolean searchPrefix(String word) {
            TrieNode cur = root;
            HashMap<Character, TrieNode> children = cur.children;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (children.containsKey(c)) {
                    cur = children.get(c);
                    children = cur.children;
                } else {
                    return false;
                }
            }
            return true;
        }
    }


}
