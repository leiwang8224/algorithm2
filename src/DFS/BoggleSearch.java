package DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

// given a dictionary and board of letters, find all the words in the dictionary
// in the board
public class BoggleSearch {
    public static void main(String[] args) {
        char[][] board = {{'a','b','c','d'},
                        {'a','a','c','d'},
                        {'a','s','e','f'},
                        {'a','e','e','f'},
                        {'a','e','e','f'}};

        char[][] board2 = {{'a','b','c','d'},
                          {'a','a','c','d'},
                          {'a','s','e','f'},
                          {'a','e','e','f'},
                          {'a','e','e','f'}};

        char[][] board3 = {{'a','b','c','d'},
                           {'a','a','c','d'},
                           {'a','s','e','f'},
                           {'a','e','e','f'},
                           {'a','e','e','f'}};
        System.out.println("boggle search = " + boggleSearch(board, "acdffee"));

        TrieNode root = new TrieNode();
        insert(root,"acdffee");
        System.out.println("word exist = " + wordExist(board2,"acdffee"));

        ArrayList<String> dict = new ArrayList<>();
        dict.add("acdffee");
        ArrayList<String> result = boggleSearchWithDict(board3, dict);
        System.out.println("boggle search with dict");
        for (String re : result) {
            System.out.println(re);
        }


        //        findWords(board,root);
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
                out = search(row, col, board, word, "",0);
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
                                  int depth) {
        int rows = board.length;
        int cols = board[0].length;
        System.out.println(getDepthString(depth) + "beginning of method predecessor " + " row = " + row + " col = " + col);
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
            System.out.println(getDepthString(depth) + "word = " + word + " pre = " + predecessor + " row = " + row + " col = " + col + " returning false");
            return false;
        }

        // append current ch to the predecessor
        char ch = board[row][col];
        String stringFromBoard = predecessor + ch;

        // if the predecessor is equal to word, return true
        if (stringFromBoard.equals(word)) return true;
        else {
            // 1. mark the grid position by setting '@' to indicate grid is visited
            // 2. backtrack
            // 3. set the grid position by setting ch (original board[row][col]
            // not equals to word so set position as searched
            board[row][col] = '@';
            System.out.println(getDepthString(depth) + "begin backtracking row = " + row + " col = " + col + " pred = " + predecessor);
            // search top, bottom, left and right (backtracking)
            if (search(row-1, col, board, word, stringFromBoard, depth+1)
                    || search (row+1, col, board, word, stringFromBoard, depth+1)
                    || search (row,col-1, board, word, stringFromBoard, depth+1)
                    || search (row, col+1, board, word, stringFromBoard, depth+1)) return true;
            System.out.println(getDepthString(depth) + "end backtracking row = " + row + " col = " + col + " pred = " + predecessor);
            // set char to the board position
            board[row][col] = ch;
        }
        return false;
    }

    private static String getDepthString(int depth) {
        StringBuilder str = new StringBuilder();
        for (int index = 0; index < depth; index++) {
            str.append("    ");
        }
        return str.toString();
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
                    // call recursion function to exhaustively search the grid
                    if (existHelper(board, word, row, col, 0, 0)) return true;
                }
            }
            // would have returned true if the word is found
            // at this point there is no hope
            return false;
        }

    private static boolean existHelper(char[][] board,
                                String word,
                                int row,
                                int col,
                                int index,
                                int depth) {
        if (row < 0 || row >= board.length || col <0 || col >= board[0].length|| board[row][col] == '@') return false;
        System.out.println(getDepthString(depth) + "checking row = " + row + " col = " + col + " index = " + index);
        if (board[row][col] == word.charAt(index)) {
            // if at the end of the word and there is no return false
            // and current board position is equal to the current char in word
            // then we are definitely returning true
            if (index == word.length()-1) return true;

            // get the char from the current board position and set
            // the current position to visisted '@'
            char ch = board[row][col];
            board[row][col] = '@';

            System.out.println(getDepthString(depth) + " begin recursion row = " + row + " col = " + col + " index = " + index + " word = " + word);
            // exhaustively search the nearby positions
            if (existHelper(board, word, row-1, col, index+1, depth+1) ||
                existHelper(board, word, row, col-1, index+1, depth+1) ||
                existHelper(board, word, row+1, col, index+1, depth+1) ||
                existHelper(board, word, row, col+1, index+1, depth+1)) return true;
//            for (int[] m : move) {
//                if (existHelper(board, word, row+m[0], col+m[1], index+1, move)) return true;
//            }

            System.out.println(getDepthString(depth) + " return from recursion row = " + row + " col = " + col + " index = " + index + " word = " + word);

            // set the current position back to ch (after row and col have been updated by recursion)
            board[row][col] = ch;
        }

        // return false in the end since all other cases would have returned true
        return false;
    }

    static class TrieNodeChars {
        Character c;
        Boolean isLeaf = false;
        HashMap<Character, TrieNodeChars> children = new HashMap<>();
        public TrieNodeChars() {}
        public TrieNodeChars(Character c) {
            this.c = c;
        }
    }

    static class TrieImpl {
        private TrieNodeChars root;

        public TrieImpl() {
            this.root = new TrieNodeChars();
        }

        void insertWord(String word) {
            TrieNodeChars cur = root;
            HashMap<Character, TrieNodeChars> children = cur.children;

            for (int index = 0; index < word.length(); index++) {
                char ch = word.charAt(index);
                if (children.containsKey(ch)) {
                    cur = children.get(ch);
                } else {
                    TrieNodeChars n = new TrieNodeChars(ch);
                    children.put(ch,n);
                    cur = n;
                }
                children = cur.children;

                if (index == word.length()-1) cur.isLeaf = true;
            }
        }

        boolean searchPrefix(String word) {
            TrieNodeChars cur = root;
            HashMap<Character, TrieNodeChars> children = cur.children;

            for (int index = 0; index < word.length(); index++) {
                char ch = word.charAt(index);
                if (children.containsKey(ch)) {
                    cur = children.get(ch);
                    children = cur.children;
                } else return false;
            }
            return true;
        }

        boolean searchWord(String word) {
            TrieNodeChars cur = root;
            HashMap<Character, TrieNodeChars> children = cur.children;
            for (int index = 0; index < word.length(); index++) {
                char ch = word.charAt(index);
                if (children.containsKey(ch)) {
                    cur = children.get(ch);
                    children = cur.children;
                } else return false;
            }
            return cur.isLeaf;
        }
    }

    //    You're given a 2D Boggle Board which contains an m x n matrix of chars - char[][] board,
    //    and a rudimentary, paper Dictionary in the form of an ArrayList of close to 10,000
    //    words. Write a method - boggleByot that searches the Boggle Board for words in the dictionary.
    //    Your method should return an alphabetically sorted ArrayList of words that are present on
    //    the board as well as in the dictionary. Words on the board can be constructed with
    //    sequentially adjacent letters, where adjacent letters are horizontal or vertical neighbors
    //    (not diagonal). Also, each letter on the Boggle Board must be used only once.

    private static ArrayList<String> boggleSearchWithDict(char[][] board, ArrayList<String> dictionary) {
        // TreeSet to make sure the output is in alphabetical order
        TrieImpl prefixTree = new TrieImpl();

        for (String word : dictionary) {
            prefixTree.insertWord(word);
        }

        TreeSet<String> outputHolder = new TreeSet<>();

        int numRows = board.length;
        int numCols = board[0].length;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                searchBoard(row, col, board, prefixTree, "", outputHolder);
            }
        }

        return new ArrayList<>(outputHolder);
    }

    private static void searchBoard(int row,
                             int col,
                             char[][] board,
                             TrieImpl dictionary,
                             String prefix,
                             TreeSet<String> outputHolder) {
        int rows = board.length;
        int cols = board[0].length;

        if (row > rows - 1 ||
            row < 0 ||
            col > cols - 1 ||
            col < 0 ||
            !dictionary.searchPrefix(prefix) ||
            board[row][col] == '@') {
            return;
        }              

        char ch = board[row][col];
        String str = prefix + ch;
        if (dictionary.searchWord(str)) {
            outputHolder.add(str);      // add to the treeSet
        }

        // label as visited
        board[row][col] = '@';          // mark the board node as visited

        searchBoard(row-1, col, board, dictionary, str, outputHolder);
        searchBoard(row+1,col,board,dictionary,str,outputHolder);
        searchBoard(row,col-1,board,dictionary,str,outputHolder);
        searchBoard(row,col+1,board,dictionary,str,outputHolder);

        // put the char back in the board position
        board[row][col] = ch;
    }
}
