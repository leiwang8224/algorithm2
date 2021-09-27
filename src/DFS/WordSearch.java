package DFS;

/**
 * Created by leiwang on 3/30/18.
 */
public class WordSearch {
    public static void main(String args[]) {
        char[][] board = new char[][] {
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}
        };
        System.out.println("word exists = " + wordExists(board, "SEE"));
        System.out.println("word exists2 = " + wordExists2(board, "SEE"));
        wordExists(board, "SEE");
    }

    /**
     * DFS method
     * 1. iterate through the board and recurse from start index of 0
     * 2. recursion base conditions
     *      - array index has reached the end, return true
     *      - if reached outside of board or the letter is not in the word
     *      - set the board position
     *      - recurse on all 4 directions from the cell and set the exist var
     *      - unset the board position
     *      - return the exist var
     *
     */
    private static boolean wordExists(char[][] board, String word) {
        char[] charArray = word.toCharArray();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (exist(board, row, col, charArray, 0)) return true;
            }
        }
        return false;
    }

    private static boolean exist(char[][] board, int row, int col, char[] charArray, int charArrayIndex) {
        if (charArrayIndex == charArray.length) return true;
        if (row<0 || col<0 || row == board.length || col == board[row].length) return false;
        if (board[row][col] != charArray[charArrayIndex]) return false;
        // mask is used to set the element at board[row][col]
        board[row][col] ^= 256; // board[row][col] = '*';
        boolean exist = exist(board, row, col+1, charArray, charArrayIndex+1)
                || exist(board, row, col-1, charArray, charArrayIndex+1)
                || exist(board, row+1, col, charArray, charArrayIndex+1)
                || exist(board, row-1, col, charArray, charArrayIndex+1);
        // mask is used to unset the element at board[row][col]
        board[row][col] ^= 256; // charArray[charArrayIndex]
        return exist;
    }

    public static boolean findWordExist(char[][] board, String wordToLookFor) {
        /*Find word's first letter.  Then call method to check it's surroundings */
        for(int row=0; row<board.length; row++)
            for(int col=0; col<board[0].length; col++)
                if(board[row][col]==wordToLookFor.charAt(0) && help(board,wordToLookFor,0,row,col))
                    return true;

        return false;
    }

    public static boolean help(char[][] b, String word, int start, int row, int col){
        /* once we get past word.length, we are done. */
        if(word.length() <= start)
            return true;

        /* if off bounds, letter is seen, letter is unequal to word.charAt(start) return false */
        if(row<0 ||
                col<0 ||
                row>=b.length ||
                col>=b[0].length ||
                b[row][col]=='0' ||
                b[row][col]!=word.charAt(start))
            return false;

        /* set this board position to seen. (Because we can use this postion) */
        char tmp = b[row][col];
        b[row][col] = '0';

        /* recursion on all 4 sides for next letter, if works: return true */
        if(help(b,word,start+1,row+1,col) ||
                help(b,word,start+1,row-1,col) ||
                help(b,word,start+1,row,col+1) ||
                help(b,word,start+1,row,col-1))
            return true;

        //Set back to unseen
        b[row][col] = tmp;

        return false;
    }

    static boolean[][] visited;

    /**
     * Second method
     * @param board
     * @param word
     * @return
     */
    public static boolean wordExists2(char[][] board, String word) {
        visited = new boolean[board.length][board[0].length];

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if((word.charAt(0) == board[i][j]) && search(board, word, i, j, 0)){
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean search(char[][]board, String word, int i, int j, int index){
        if(index == word.length()){
            return true;
        }

        if(i >= board.length || i < 0 || j >= board[i].length || j < 0 || board[i][j] != word.charAt(index) || visited[i][j]){
            return false;
        }

        visited[i][j] = true;
        if(search(board, word, i-1, j, index+1) ||
                search(board, word, i+1, j, index+1) ||
                search(board, word, i, j-1, index+1) ||
                search(board, word, i, j+1, index+1)){
            return true;
        }

        visited[i][j] = false;
        return false;
    }

}
