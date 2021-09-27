package AlgoExpert.Hard;
import java.util.*;
public class BoggleBoard {
    public static void main(String[] args) {
        char[][] board = new char[][]{{'t', 'h', 'i', 's', 'i', 's', 'a'}, 
                                      {'s', 'i', 'm', 'm', 'p', 'l', 'e'},
                                      {'b', 'x', 'x', 'x', 'x', 'e', 'b'},
                                      {'x', 'o', 'g', 'g', 'l', 'x', 'o'},
                                      {'x', 'x', 'x', 'D', 'T', 'r', 'a'},
                                      {'R', 'E', 'P', 'E', 'A', 'd', 'x'},
                                      {'x', 'x', 'x', 'x', 'x', 'x', 'x'},
                                      {'N', 'O', 'T', 'R', 'E', '-', 'P'},
                                      {'x', 'x', 'D', 'E', 'T', 'A', 'E'}};
        String[] words = new String[] {"this", "is", "not", "a", "simple", "boggle", "board"};
       List<String> result = boggleBoard(board, words);

                                      
        
    }
    // w = # words
    // s = # longest chars
    // n and m are the width and height of matrix
    // O(nm * 8^s + ws) time | O(nm + ws) space
    /**
     * Build Trie to store all words to search for
     * for each cell in the boggle board, explore paths that match the word.
     * We do this using dfs on the graph with 8 different choices for path
     */
    static List<String> boggleBoard(char[][] board, String[] words) {
        // populate trie
        Trie trie = new Trie();
        for (String word : words) {
            trie.addWord(word);
        }

        // initialize words found in array as a set
        Set<String> result = new HashSet<>();
        boolean[][] visited = new boolean[board.length][board[0].length];

        // for each element in array traverse and backtrack
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                exploreBacktrack(row, col, board, trie.root, visited, result);
            }
        }
        // convert hashset into arraylist
        List<String> finalWordsArray = new ArrayList<>();
        finalWordsArray.addAll(result);
        return finalWordsArray;
    }

    private static void exploreBacktrack(int row, // cur position
                                         int col,
                                         char[][] board, // board
                                         TrieNode root,  // current position in trie
                                         boolean[][] visited, // visited array
                                         Set<String> result) { // populate result
        // return immediately if visited
        if (visited[row][col]) return;

        char curLetter = board[row][col];
        // if letter is not in trie node return
        if (!root.children.containsKey(curLetter)) return;

        // if letter is trie node, mark as visited
        visited[row][col] = true;
        // traverse down to the letter and validate each char
        root = root.children.get(curLetter);
        // if we have reached the terminating symbol then we
        // have a valid word, add to result
        if (root.children.containsKey('*')) {
            result.add(root.word);
        }
        // get neighbors for current row, col (up, down, left, right)
        List<Integer[]> neighbors = getNeighbors(row, col, board);

        // recursively search
        for (Integer[] neighbor : neighbors) {
            exploreBacktrack(neighbor[0], neighbor[1], board, root, visited, result);
        }
        // backtracking magic, once we reach this point we have reached a letter that is not in the word
        // go back to where we started and repeat with another neighbor
        visited[row][col] = false;
    }

    private static List<Integer[]> getNeighbors(int row, int col, char[][] board) {
        List<Integer[]> neighbors = new ArrayList<>();
        // get all 8 elements around the current element,
        // verify the elements are within bounds and add to neiglhbors

        // four corners
        // left upper corner
        if (row > 0 && col > 0) {
            neighbors.add(new Integer[]{row - 1, col - 1});
        }

        // right upper corner
        if (row > 0 && col < board[0].length - 1) {
            neighbors.add(new Integer[]{row - 1, col + 1});
        }

        // right bottom corner
        if (row < board.length - 1 && col < board[0].length - 1) {
            neighbors.add(new Integer[]{row + 1, col + 1});
        }

        // left bottom corner
        if (row < board.length -1 && col > 0) {
            neighbors.add(new Integer[]{row + 1, col - 1});
        }

        // up, down, left, right
        if (row > 0) {
            // up
            neighbors.add(new Integer[] { row - 1, col });
        }

        if (row < board.length - 1) {
            // down
            neighbors.add(new Integer[] { row + 1, col});
        }

        if ( col < 0) {
            // left
            neighbors.add(new Integer[] { row, col - 1});
        }

        if (col < board[0].length - 1) {
            // right
            neighbors.add(new Integer[]{row, col + 1});
        }
        return neighbors;
    }

    /**
     * TrieNode contains a map of chars to nodes,
     * and the word that contains all the chars
     * - Map of children < char, TrieNode>
     * - word (each Node is linked with a certain word)
     */
    static class TrieNode {
        // for each char map it to a trie node
        Map<Character, TrieNode> children = new HashMap<>();
        // easily accessed the word
        String word = "";
    }

    /**
     * Trie is made up of multiple TrieNodes
     * contains the root and the endSymbol (terminating char)
     */
    static class Trie {
        TrieNode root;
        char endSymbol;

        public Trie() {
            this.root = new TrieNode();
            this.endSymbol = '*';
        }

        /**
         * For any specific "str" create trie nodes that composes of the "str"
         * "str" is composed of multiple chars and corresponding nodes, and
         * the string it represents.
         */
        public void addWord(String str) {
            TrieNode node = this.root;
            for (int charIndex = 0; charIndex < str.length(); charIndex++) {
                char letter = str.charAt(charIndex);
                // put letter into children if children does not contain the key
                if (!node.children.containsKey(letter)) {
                    node.children.put(letter, new TrieNode());
                }
                // traverse down to the node below
                node = node.children.get(letter);
            }
            // put terminating char with the word in the last node
            node.children.put(this.endSymbol, null);
            node.word = str;
        }
    }
}
