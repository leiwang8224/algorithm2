package CTCI.RecursionAndDP;

/**
 * Implement the paint fill function that one might see on many image editing programs.
 * That is given a screen (represented by two dimensional array of colors), a point, and a
 * new color, fill in the surrounding area until the color changes from the original color.
 */
public class PaintFill {
    public static void main(String[] args) {
        int N = 10;
        Color[][] screen = new Color[N][N];

        for (int row = 0; row < N;row ++) {
            for (int col = 0; col < N; col++) {
                screen[row][col] = Color.Black;
            }
        }

        // put some random green color
        for (int index = 0; index < 100; index ++) {
            screen[randomInt(N)][randomInt(N)] = Color.Green;
        }

        printScreen(screen);
        paintFill(screen, 2, 2, Color.White);
        System.out.println();
        printScreen(screen);

    }

    enum Color {
        Black, White, Red, Yellow, Green
    }

    static String printColor(Color c) {
        switch (c) {
            case Black:
                return "B";
            case White:
                return "W";
            case Red:
                return "R";
            case Yellow:
                return "Y";
            case Green:
                return "G";
        }
        return "X";
    }

    private static void printScreen(Color[][] screen) {
        for (int row = 0; row < screen.length; row++) {
            for (int col = 0; col < screen[0].length; col++) {
                System.out.print(printColor(screen[row][col]));
            }
            System.out.println();
        }
    }

    private static int randomInt(int n) {
        return (int) (Math.random() * n);
    }

    private static boolean paintFill(Color[][] screen,
                                     int row,
                                     int col,
                                     Color oldColor,
                                     Color newColor) {
        if (row < 0 || row >= screen.length || col < 0 || col >= screen[0].length)
            return false;

        // only paint over if the old color is found in the grid
        if (screen[row][col] == oldColor) {
            screen[row][col] = newColor;
            paintFill(screen, row - 1, col, oldColor, newColor); // up
            paintFill(screen, row + 1, col, oldColor, newColor); // down
            paintFill(screen, row, col - 1, oldColor, newColor);    // left
            paintFill(screen, row, col + 1, oldColor, newColor);  // right
        }
        return true;
    }

    private static boolean paintFill(Color[][] screen, int row, int col, Color newColor) {
        if (screen[row][col] == newColor) return false;
        return paintFill(screen, row, col, screen[row][col], newColor);
    }
}
