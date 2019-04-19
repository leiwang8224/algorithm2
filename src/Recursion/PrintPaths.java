package Recursion;

import java.util.ArrayList;

//You're given a 2D board which contains an m x n matrix of chars
// - char[][] board. Write a method - printPaths that prints all
// possible paths from the top left cell to the bottom right cell.
// Your method should return an ArrayList of Strings, where each
// String represents a path with characters appended in the order
// of movement. You're only allowed to move down and right on the
// board. The order of String insertion in the ArrayList does not matter.
//        A
//        AF
//        AFK
//        AFKP
//        AFKPU
//        AFKPU
//        AFKPUV
//        AFKPUV
//        AFKPUVW
//        AFKPUVW
//        AFKPUVWX
//        AFKPUVWX
//        AFKP
//        AFKPQ
//        AFKPQV
//        AFKPQV
//        AFKPQVW
//        AFKPQVW
//        AFKPQVWX
//        AFKPQVWX
//        AFKPQ
//        AFKPQR
//        AFKPQRW
//        AFKPQRW
//        AFKPQRWX
//        AFKPQRWX
//        AFKPQR
//        AFKPQRS
//        AFKPQRSX
//        AFKPQRSX
//        AFKPQRS
//        AFKPQRST
//        AFKPQRST
//        AFK
//        AFKL
//        AFKLQ
//        AFKLQV
//        AFKLQV
//        AFKLQVW
//        AFKLQVW
//        AFKLQVWX
//        AFKLQVWX
//        AFKLQ
//        AFKLQR
//        AFKLQRW
//        AFKLQRW
//        AFKLQRWX
//        AFKLQRWX
//        AFKLQR
//        AFKLQRS
//        AFKLQRSX
//        AFKLQRSX
//        AFKLQRS
//        AFKLQRST
//        AFKLQRST
//        AFKL
//        AFKLM
//        AFKLMR
//        AFKLMRW
//        AFKLMRW
//        AFKLMRWX
//        AFKLMRWX
//        AFKLMR
//        AFKLMRS
//        AFKLMRSX
//        AFKLMRSX
//        AFKLMRS
//        AFKLMRST
//        AFKLMRST
//        AFKLM
//        AFKLMN
//        AFKLMNS
//        AFKLMNSX
//        AFKLMNSX
//        AFKLMNS
//        AFKLMNST
//        AFKLMNST
//        AFKLMN
//        AFKLMNO
//        AFKLMNOT
//        AFKLMNOT
//        AFKLMNO
//        AF
//        AFG
//        AFGL
//        AFGLQ
//        AFGLQV
//        AFGLQV
//        AFGLQVW
//        AFGLQVW
//        AFGLQVWX
//        AFGLQVWX
//        AFGLQ
//        AFGLQR
//        AFGLQRW
//        AFGLQRW
//        AFGLQRWX
//        AFGLQRWX
//        AFGLQR
//        AFGLQRS
//        AFGLQRSX
//        AFGLQRSX
//        AFGLQRS
//        AFGLQRST
//        AFGLQRST
//        AFGL
//        AFGLM
//        AFGLMR
//        AFGLMRW
//        AFGLMRW
//        AFGLMRWX
//        AFGLMRWX
//        AFGLMR
//        AFGLMRS
//        AFGLMRSX
//        AFGLMRSX
//        AFGLMRS
//        AFGLMRST
//        AFGLMRST
//        AFGLM
//        AFGLMN
//        AFGLMNS
//        AFGLMNSX
//        AFGLMNSX
//        AFGLMNS
//        AFGLMNST
//        AFGLMNST
//        AFGLMN
//        AFGLMNO
//        AFGLMNOT
//        AFGLMNOT
//        AFGLMNO
//        AFG
//        AFGH
//        AFGHM
//        AFGHMR
//        AFGHMRW
//        AFGHMRW
//        AFGHMRWX
//        AFGHMRWX
//        AFGHMR
//        AFGHMRS
//        AFGHMRSX
//        AFGHMRSX
//        AFGHMRS
//        AFGHMRST
//        AFGHMRST
//        AFGHM
//        AFGHMN
//        AFGHMNS
//        AFGHMNSX
//        AFGHMNSX
//        AFGHMNS
//        AFGHMNST
//        AFGHMNST
//        AFGHMN
//        AFGHMNO
//        AFGHMNOT
//        AFGHMNOT
//        AFGHMNO
//        AFGH
//        AFGHI
//        AFGHIN
//        AFGHINS
//        AFGHINSX
//        AFGHINSX
//        AFGHINS
//        AFGHINST
//        AFGHINST
//        AFGHIN
//        AFGHINO
//        AFGHINOT
//        AFGHINOT
//        AFGHINO
//        AFGHI
//        AFGHIJ
//        AFGHIJO
//        AFGHIJOT
//        AFGHIJOT
//        AFGHIJO
//        AFGHIJ
//        A
//        AB
//        ABG
//        ABGL
//        ABGLQ
//        ABGLQV
//        ABGLQV
//        ABGLQVW
//        ABGLQVW
//        ABGLQVWX
//        ABGLQVWX
//        ABGLQ
//        ABGLQR
//        ABGLQRW
//        ABGLQRW
//        ABGLQRWX
//        ABGLQRWX
//        ABGLQR
//        ABGLQRS
//        ABGLQRSX
//        ABGLQRSX
//        ABGLQRS
//        ABGLQRST
//        ABGLQRST
//        ABGL
//        ABGLM
//        ABGLMR
//        ABGLMRW
//        ABGLMRW
//        ABGLMRWX
//        ABGLMRWX
//        ABGLMR
//        ABGLMRS
//        ABGLMRSX
//        ABGLMRSX
//        ABGLMRS
//        ABGLMRST
//        ABGLMRST
//        ABGLM
//        ABGLMN
//        ABGLMNS
//        ABGLMNSX
//        ABGLMNSX
//        ABGLMNS
//        ABGLMNST
//        ABGLMNST
//        ABGLMN
//        ABGLMNO
//        ABGLMNOT
//        ABGLMNOT
//        ABGLMNO
//        ABG
//        ABGH
//        ABGHM
//        ABGHMR
//        ABGHMRW
//        ABGHMRW
//        ABGHMRWX
//        ABGHMRWX
//        ABGHMR
//        ABGHMRS
//        ABGHMRSX
//        ABGHMRSX
//        ABGHMRS
//        ABGHMRST
//        ABGHMRST
//        ABGHM
//        ABGHMN
//        ABGHMNS
//        ABGHMNSX
//        ABGHMNSX
//        ABGHMNS
//        ABGHMNST
//        ABGHMNST
//        ABGHMN
//        ABGHMNO
//        ABGHMNOT
//        ABGHMNOT
//        ABGHMNO
//        ABGH
//        ABGHI
//        ABGHIN
//        ABGHINS
//        ABGHINSX
//        ABGHINSX
//        ABGHINS
//        ABGHINST
//        ABGHINST
//        ABGHIN
//        ABGHINO
//        ABGHINOT
//        ABGHINOT
//        ABGHINO
//        ABGHI
//        ABGHIJ
//        ABGHIJO
//        ABGHIJOT
//        ABGHIJOT
//        ABGHIJO
//        ABGHIJ
//        AB
//        ABC
//        ABCH
//        ABCHM
//        ABCHMR
//        ABCHMRW
//        ABCHMRW
//        ABCHMRWX
//        ABCHMRWX
//        ABCHMR
//        ABCHMRS
//        ABCHMRSX
//        ABCHMRSX
//        ABCHMRS
//        ABCHMRST
//        ABCHMRST
//        ABCHM
//        ABCHMN
//        ABCHMNS
//        ABCHMNSX
//        ABCHMNSX
//        ABCHMNS
//        ABCHMNST
//        ABCHMNST
//        ABCHMN
//        ABCHMNO
//        ABCHMNOT
//        ABCHMNOT
//        ABCHMNO
//        ABCH
//        ABCHI
//        ABCHIN
//        ABCHINS
//        ABCHINSX
//        ABCHINSX
//        ABCHINS
//        ABCHINST
//        ABCHINST
//        ABCHIN
//        ABCHINO
//        ABCHINOT
//        ABCHINOT
//        ABCHINO
//        ABCHI
//        ABCHIJ
//        ABCHIJO
//        ABCHIJOT
//        ABCHIJOT
//        ABCHIJO
//        ABCHIJ
//        ABC
//        ABCD
//        ABCDI
//        ABCDIN
//        ABCDINS
//        ABCDINSX
//        ABCDINSX
//        ABCDINS
//        ABCDINST
//        ABCDINST
//        ABCDIN
//        ABCDINO
//        ABCDINOT
//        ABCDINOT
//        ABCDINO
//        ABCDI
//        ABCDIJ
//        ABCDIJO
//        ABCDIJOT
//        ABCDIJOT
//        ABCDIJO
//        ABCDIJ
//        ABCD
//        ABCDE
//        ABCDEJ
//        ABCDEJO
//        ABCDEJOT
//        ABCDEJOT
//        ABCDEJO
//        ABCDEJ
//        ABCDE
//        AFKPUVWXY
//        AFKPQVWXY
//        AFKPQRWXY
//        AFKPQRSXY
//        AFKPQRSTY
//        AFKLQVWXY
//        AFKLQRWXY
//        AFKLQRSXY
//        AFKLQRSTY
//        AFKLMRWXY
//        AFKLMRSXY
//        AFKLMRSTY
//        AFKLMNSXY
//        AFKLMNSTY
//        AFKLMNOTY
//        AFGLQVWXY
//        AFGLQRWXY
//        AFGLQRSXY
//        AFGLQRSTY
//        AFGLMRWXY
//        AFGLMRSXY
//        AFGLMRSTY
//        AFGLMNSXY
//        AFGLMNSTY
//        AFGLMNOTY
//        AFGHMRWXY
//        AFGHMRSXY
//        AFGHMRSTY
//        AFGHMNSXY
//        AFGHMNSTY
//        AFGHMNOTY
//        AFGHINSXY
//        AFGHINSTY
//        AFGHINOTY
//        AFGHIJOTY
//        ABGLQVWXY
//        ABGLQRWXY
//        ABGLQRSXY
//        ABGLQRSTY
//        ABGLMRWXY
//        ABGLMRSXY
//        ABGLMRSTY
//        ABGLMNSXY
//        ABGLMNSTY
//        ABGLMNOTY
//        ABGHMRWXY
//        ABGHMRSXY
//        ABGHMRSTY
//        ABGHMNSXY
//        ABGHMNSTY
//        ABGHMNOTY
//        ABGHINSXY
//        ABGHINSTY
//        ABGHINOTY
//        ABGHIJOTY
//        ABCHMRWXY
//        ABCHMRSXY
//        ABCHMRSTY
//        ABCHMNSXY
//        ABCHMNSTY
//        ABCHMNOTY
//        ABCHINSXY
//        ABCHINSTY
//        ABCHINOTY
//        ABCHIJOTY
//        ABCDINSXY
//        ABCDINSTY
//        ABCDINOTY
//        ABCDIJOTY
//        ABCDEJOTY
//
//        Process finished with exit code 0

public class PrintPaths {
    public static void main(String[] args) {
        char[][] board = {{'A','B','C','D','E'},
                          {'F','G','H','I','J'},
                          {'K','L','M','N','O'}, 
                          {'P','Q','R','S','T'},
                          {'U','V','W','X','Y'}};

        ArrayList<String> result = printPaths(board);
        for (String res : result) {
            System.out.println(res);
        }

    }

    private static ArrayList<String> printPaths(char[][] board) {
        ArrayList<String> out = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        search (0,0,board,sb,out);
        return out;
    }

    private static void search(int row, int col, char[][] board, StringBuilder sb, ArrayList<String> out) {
        System.out.println(sb.toString());
        int rows = board.length;
        int cols = board[0].length;
        if (row > rows-1 || col > cols-1) return;  // return when row or col is out of range

        sb.append(board[row][col]); // mark to keep track of visited places
        if (row == rows - 1 && col == cols - 1) {
            // at the end of the board so add to result arraylist
            out.add(sb.toString());
            // make sure to take out last char and return from method
            sb.deleteCharAt(sb.length()-1); // unmark last char as it does not fall on the correct paths
            return;
        }

        // going down first
        search(row+1, col, board, sb, out);
        // then going horizontal to right
        search(row, col+1, board, sb, out);
        sb.deleteCharAt(sb.length()-1); //delete last char and return from method
        return;
    }

    private static void printPaths2(int mat[][], int numRows, int numCols, int row, int col, int path[], int idx) {
        path[idx] = mat[row][col];

        if (row == numRows-1) {
            for (int k = col+1; k < numCols; k++) {
                // going backward
                path[idx + k - col] = mat[row][k];
            }
            for (int l = 0; l < idx + numCols - col; l++) {
                System.out.println(path[l] + " ");
            }
            System.out.println();
            return;
        }

        if (col == numCols-1) {
            for (int k = row + 1; k < numRows; k++) {
                // going backward
                path[idx + k - row] = mat[k][col];
            }
            for (int l = 0; l < idx + numRows - row; l++) {
                System.out.println(path[l] + " ");
            }
            System.out.println();
            return;
        }

        printPaths2(mat, numRows, numCols, row + 1, col, path, idx+1);
        printPaths2(mat, numRows, numCols, row, col+1, path, idx+1);
    }
}
