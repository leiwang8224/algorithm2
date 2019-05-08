package Trie;

import java.util.HashMap;

public class Trie4 {
    class TrieNode {
        Character c;
        Boolean isLeaf = false;
        HashMap<Character, TrieNode> children = new HashMap<>();

        TrieNode() {

        }

        TrieNode(Character c) {
            this.c = c;
        }
    }

    class Trie {
        private TrieNode root;

        Trie() {
            this.root = new TrieNode();
        }

        void insertWord(String word) {
            if (word == null || word.length() < 1) return;
            TrieNode cur = root;
            HashMap<Character,TrieNode> children = cur.children;

            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (children.containsKey(c)) {
                    cur = children.get(c);
                } else {
                    TrieNode node = new TrieNode(c);
                    children.put(c, node);
                    cur = node;
                }

                children = cur.children;

                if (i == word.length() - 1) {
                    cur.isLeaf = true;
                }
            }
        }

        boolean searchWord(String word) {
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

        boolean searchPrefix(String word) {
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
