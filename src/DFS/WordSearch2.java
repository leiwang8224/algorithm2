package DFS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by leiwang on 3/30/18.
 */
public class WordSearch2 {
    /**
     * Given board and list of words, find all words in the board
     * @param args
     */
    public static void main(String args[]) {
        char[][] board = new char[][] {
                {'o','a','a','n'},
                {'e','t','a','e'},
                {'i','h','k','r'},
                {'i','f','l','v'}
        };
        System.out.println(java.util.Arrays.toString(findWords(board, new String[] {"eat", "oath"}).toArray()));
    }

    static class TrieNode {
        public TrieNode[] children = new TrieNode[26];
        public String item = "";

        // Initialize your data structure here.
        public TrieNode() {
        }
    }

    public static class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        // Inserts a word into the trie.
        public void insert(String word) {
            // save the root to buffer
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                // if the children does not exist, create
                // new TrieNode
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TrieNode();
                }
                node = node.children[c - 'a'];
            }
            node.item = word;
        }

        // Returns if the word is in the trie.
        public boolean search(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                // return false if not in children
                if (node.children[c - 'a'] == null) return false;
                node = node.children[c - 'a'];
            }
            return node.item.equals(word);
        }

        // Returns if there is any word in the trie
        // that starts with the given prefix.
        public boolean startsWith(String prefix) {
            TrieNode node = root;
            for (char c : prefix.toCharArray()) {
                if (node.children[c - 'a'] == null) return false;
                node = node.children[c - 'a'];
            }
            return true;
        }
    }

    static Set<String> res = new HashSet<String>();

    /**
     * Method using trie
     * @param board
     * @param words
     * @return
     */
    private static  List<String> findWords(char[][] board, String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }

        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, visited, "", i, j, trie);
            }
        }

        return new ArrayList<String>(res);
    }

    public static void dfs(char[][] board, boolean[][] visited, String str, int x, int y, Trie trie) {
        // return if x or y is out of range
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) return;
        // if visited return
        if (visited[x][y]) return;

        str += board[x][y];
        System.out.println("str accumulation " + str);
        if (!trie.startsWith(str)) return;

        if (trie.search(str)) {
            res.add(str);
        }

        visited[x][y] = true;
        dfs(board, visited, str, x - 1, y, trie);
        dfs(board, visited, str, x + 1, y, trie);
        dfs(board, visited, str, x, y - 1, trie);
        dfs(board, visited, str, x, y + 1, trie);
        visited[x][y] = false;
    }
}
