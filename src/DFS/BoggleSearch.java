package DFS;

import java.util.Arrays;

// given a dictionary and board of letters, find all the words in the dictionary
// in the board
public class BoggleSearch {
    public static void main(String[] args) {
        char[][] board = {{'a','b','c','d','e'},
                        {'a','a','c','d','e'},
                        {'a','s','e','f','e'},
                        {'a','e','e','f','a'},
                        {'a','e','e','f','a'}};
        System.out.println(boggleSearch(board, "acdffee"));
//        findWords(board,new String[]{"acdffee"});


    }

    private static boolean boggleSearch(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;

        boolean out = false;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                out = search(row, col, board, word, "");
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
                                  String predecessor) {
        int rows = board.length;
        int cols = board[0].length;
        System.out.println(predecessor + " row = " + row + " col = " + col);
        for (char[] line : board) {
            System.out.println(Arrays.toString(line));
        }
        System.out.println("end");

        // if we are outside of the board, predecessor went out of the word,
        // or board position is @, return false
        if (row > rows-1 ||
            row < 0 ||
            col > cols-1 ||
            col < 0 ||
            !word.contains(predecessor) ||
            board[row][col] == '@') {
            return false;
        }

        // append current ch to the predecessor
        char ch = board[row][col];
        String stringFromBoard = predecessor + ch;
        boolean out = false;

        // if the predecessor is equal to word, return true
        if (stringFromBoard.equals(word)) return true;
        else {
            // 1. clear the grid position
            // 2. backtrack
            // 3. set the grid position
            // not equals to word so set position as searched
            board[row][col] = '@';
            System.out.println("begin backtracking");
            // search top, bottom, left and right (backtracking)
            out = search(row-1, col, board, word, stringFromBoard)
                    || search (row+1, col, board, word, stringFromBoard)
                    || search (row,col-1, board, word, stringFromBoard)
                    || search (row, col+1, board, word, stringFromBoard);
            System.out.println("end backtracking");
            // set char to the board position
            board[row][col] = ch;
        }
        return out;
    }

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
}
