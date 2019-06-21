package DFS;

import java.util.Arrays;

// given a dictionary and board of letters, find all the words in the dictionary
// in the board
public class BoggleSearch {
    public static void main(String[] args) {
        char[][] board = {{'a','b','c','d'},
                        {'a','a','c','d'},
                        {'a','s','e','f'},
                        {'a','e','e','f'},
                        {'a','e','e','f'}};
        System.out.println(boggleSearch(board, "acdffee"));

        TrieNode root = new TrieNode();
        insert(root,"acdffee");
        findWords(board,root);
        System.out.println(wordExist(board,"acdffee"));


//        findWords(board,new String[]{"acdffee"});


    }

    /**
     * Build up the word using the predecessor. Predecessor is a string
     * picked up from the board. This is using the DFS approach.
     * @param board
     * @param word
     * @return
     */
    private static boolean boggleSearch(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;

        boolean out = false;
        // for each char in the board, search exhaustively
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                out = search(row, col, board, word, "","mainCall");
                // once match is found, return immediately
                if (out) return true;
            }
        }
        return out;
    }

    private static boolean search(int row,
                                  int col,
                                  char[][] board,
                                  String word,
                                  String predecessor,
                                  String callingFrom) {
        int rows = board.length;
        int cols = board[0].length;
        System.out.println("beginning of method predecessor " + " row = " + row + " col = " + col + " calling from " + callingFrom);
        for (char[] line : board) {
            System.out.println(Arrays.toString(line));
        }

        // if we are outside of the board, predecessor went out of the word,
        // or board position is @, return false
        if (row > rows-1 ||
            row < 0 ||
            col > cols-1 ||
            col < 0 ||
            !word.contains(predecessor) ||
            board[row][col] == '@') {
            System.out.println("word = " + word + " pre = " + predecessor + " row = " + row + " col = " + col + " returning false");
            return false;
        }

        // append current ch to the predecessor
        char ch = board[row][col];
        String stringFromBoard = predecessor + ch;
        boolean out = false;

        // if the predecessor is equal to word, return true
        if (stringFromBoard.equals(word)) return true;
        else {
            // 1. mark the grid position by setting '@' to indicate grid is visited
            // 2. backtrack
            // 3. set the grid position by setting ch (original board[row][col]
            // not equals to word so set position as searched
            board[row][col] = '@';
            System.out.println("begin backtracking row = " + row + " col = " + col + " pred = " + predecessor);
            // search top, bottom, left and right (backtracking)
            out = search(row-1, col, board, word, stringFromBoard, "row below")
                    || search (row+1, col, board, word, stringFromBoard, "row above")
                    || search (row,col-1, board, word, stringFromBoard, "col to left")
                    || search (row, col+1, board, word, stringFromBoard, "col to right");
            System.out.println("end backtracking row = " + row + " col = " + col + " pred = " + predecessor);
            // set char to the board position
            board[row][col] = ch;
        }
        return out;
    }

    ////////////////////////////////////////////////////////////////////
    // TODO something wrong with the implementation below
    private static void findWordsUtil(char boggle[][],
                                      boolean visited[][],
                                      int row,
                                      int col,
                                      String str,
                                      String[] dict) {
        System.out.println("row = " + row + " col = " + col + " str = " + str);
        // Mark current cell as visited and append current char to str
        visited[row][col] = true;
        str = str + boggle[row][col];

        // if str is present in dict, then print it
        if (isWord(str, dict)) {
            System.out.println(str);
        }

        // traverse 8 adjacent cells of boggle[row][col]
        for (int rows = row - 1; rows <= row + 1 && rows < boggle.length; rows++) {
            for (int cols = col - 1; cols <= col + 1 && cols < boggle[0].length; cols++) {
                if (rows >= 0 && cols >= 0 && !visited[rows][cols])
                    findWordsUtil(boggle, visited, rows, cols, str, dict);
            }
        }

        // erase current char from string and mark visited
        // of current cell as false
        str = "" + str.charAt((str.length() - 1));
        visited[row][col] = false;
    }

    private static boolean isWord(String str, String[] dictionary) {
        // linearly search all words
        for (int index = 0; index < dictionary.length; index++) {
            if (str.equals(dictionary[index]))
                return true;
        }
        return false;
    }

    private static void findWords(char[][] boggle, String[] dict) {
        boolean visited[][] = new boolean[boggle.length][boggle[0].length];

        String str = "";

        for (int row = 0; row < boggle.length; row++) {
            for (int col = 0; col < boggle[0].length; col++) {
                findWordsUtil(boggle,visited,row,col,str,dict);
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////
    static class TrieNode {
        TrieNode[] Child = new TrieNode[26];

        // ifLeaf is true if the node represents end of word
        boolean leaf;

        // constructor
        public TrieNode() {
            leaf = false;
            for (int i = 0; i < 26; i++) {
                Child[i] = null;
            }
        }
    }

    // if not present, inserts a key into the trie
    // if the key is a prefix of trie node, just
    // marks leaf node
    static void insert(TrieNode root, String key) {
        int n = key.length();
        TrieNode pChild = root;

        for (int i = 0; i < n; i++) {
            int index = key.charAt(i) - 'a';

            if (pChild.Child[index] == null)
                pChild.Child[index] = new TrieNode();

            pChild = pChild.Child[index];
        }

        // make last node as leaf node
        pChild.leaf = true;
    }



    // function to check that current location
        // (i and j) is in matrix range
        static boolean isSafe(int row, int col, boolean[][] visited) {
            return (row >= 0 && row < visited.length && col >= 0 && col < visited[0].length && !visited[row][col]);
        }

        // recursive function to print all words represent on boggle
        static void searchWord(TrieNode root, char board[][], int row, int col, boolean visisted[][], String str) {
            // if we found word in trie /dict
            if (root.leaf == true) {
                System.out.println(str);
            }

            // if both row and col in range and we visited
            // that element of matrix first time
            if (isSafe(row, col, visisted)) {
                // make it visited
                visisted[row][col] = true;

                // traverse all child of current node
                for (int index = 0; index < 26; index++) {
                    if (root.Child[index] != null) {
                        // current char
                        char ch = (char)(index + 'a');

                        // recursively search remaining char of word
                        // in trie for all 8 adjacent cells of boggle[row][col]
                        if (isSafe(row+1, col+1, visisted) && board[row+1][col+1] == ch)
                            searchWord(root.Child[index],board,row+1,col+1,visisted,str+ch);

                        if (isSafe(row, col+1, visisted) && board[row][col+1] == ch)
                            searchWord(root.Child[index],board,row,col+1,visisted,str+ch);

                        if (isSafe(row-1,col+1,visisted) && board[row-1][col+1] == ch)
                            searchWord(root.Child[index],board,row+1,col,visisted,str+ch);

                        if (isSafe(row+1,col-1,visisted) && board[row+1][col-1] == ch)
                            searchWord(root.Child[index],board,row+1,col-1,visisted,str+ch);

                        if (isSafe(row,col-1,visisted) && board[row][col-1] == ch)
                            searchWord(root.Child[index],board,row,col-1,visisted,str+ch);

                        if(isSafe(row-1,col-1,visisted) && board[row-1][col-1] == ch)
                            searchWord(root.Child[index],board,row-1,col-1,visisted,str+ch);

                        if(isSafe(row-1,col,visisted) && board[row-1][col] == ch)
                            searchWord(root.Child[index],board,row-1,col,visisted,str+ch);
                    }
                }

                visisted[row][col] = false;
            }
        }

        static void findWords(char[][] board, TrieNode root) {
            // mark all chars as not visited
            boolean[][] visited = new boolean[board.length][board[0].length];
            TrieNode pChild = root;

            String str = "";

            // traverse all matrix elements
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[0].length; col++) {
                    // we start searching for word in dict
                    // if we found a char which is child of
                    // Trie root
                    if (pChild.Child[(board[row][col]) - 'a'] != null) {
                        str = str+ board[row][col];
                        searchWord(pChild.Child[(board[row][col]) - 'a'],board,row,col,visited,str);
                        str = "";
                    }
                }
            }
        }

        private static boolean wordExist(char[][] board, String word) {
            if (board == null || board[0].length == 0) return false;

            int numRows = board.length;
            int numCols = board[0].length;

            // direction of top, right, down, left in (x,y) coordinates
            int[][] move = {{-1,0}, {0,1}, {1,0}, {0,-1}};
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    if (existHelper(board, word, row, col, 0, move)) return true;
                }
            }

            return false;
        }

    private static boolean existHelper(char[][] board,
                                String word,
                                int row,
                                int col,
                                int index,
                                int[][] move) {
        if (row < 0 || row >= board.length || col <0 || col >= board[0].length|| board[row][col] == '@') return false;
        if (board[row][col] == word.charAt(index)) {
            if (index == word.length()-1) return true;
            char ch = board[row][col];
            board[row][col] = '@';
            if (existHelper(board, word, row-1, col, index+1, move) ||
                existHelper(board, word, row, col-1, index+1, move) ||
                existHelper(board, word, row+1, col, index+1, move) ||
                existHelper(board, word, row, col+1, index+1, move)) return true;
//            for (int[] m : move) {
//                if (existHelper(board, word, row+m[0], col+m[1], index+1, move)) return true;
//            }
            board[row][col] = ch;
        }
        return false;
    }
}
