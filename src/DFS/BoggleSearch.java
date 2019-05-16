package DFS;

import java.util.Arrays;

public class BoggleSearch {
    public static void main(String[] args) {
        char[][] board = {{'a','b','c','d','e'},
                        {'a','a','c','d','e'},
                        {'a','s','e','f','e'},
                        {'a','e','e','f','a'},
                        {'a','e','e','f','a'}};
        System.out.println(boggleSearch(board, "acdffee"));


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

        if (row > rows-1 ||
            row < 0 ||
            col > cols-1 ||
            col < 0 ||
            !word.contains(predecessor) ||
            board[row][col] == '@') {
            return false;
        }

        char ch = board[row][col];
        String s = predecessor + ch;
        boolean out = false;
        if (s.equals(word)) return true;
        else {
            // not equals to word so set position as searched
            board[row][col] = '@';
            // search top, bottom, left and right (backtracking)
            out = search(row-1, col, board, word, s)
                    || search (row+1, col, board, word, s)
                    || search (row,col-1, board, word, s)
                    || search (row, col+1, board, word, s);
            // set char to the board position
            board[row][col] = ch;
        }
        return out;
    }
}
