package CTCI.BitManipulation;

/**
 * A monochrome screen is stored as a single array of bytes, allow eight consecutive pixels
 * to be stored in one byte. The screen has width w, where w is divisible by 8 (that is, no byte
 * will be split across rows). The height of the screen, of course, can be derived from the length
 * of the array and the width. Implement a function that draws a horizontal line from (x1, y) to (x2, y).
 */
public class DrawLine {
    public static void main(String[] args) {

        int width = 8;
        int height = 1;
        for (int row = 0; row < height; row++) {
            for (int byteNum = 0; byteNum < width; byteNum++) {
                for (int bitNum = byteNum; bitNum < width; bitNum++) {
                    byte[] screen = new byte[width * height / 8];

                    System.out.println("row: " + row + " byte " + byteNum + " bit -> " + bitNum);
                    drawLine(screen, width, byteNum, bitNum, row);
                    printScreen(screen, width);
                    System.out.println();
                    System.out.println();
                }
            }
        }
    }

    /**
     * The key is to recognize that if x1 and x2 are far away from each other, several full
     * bytes will be contained between them. these full bytes can be set one at a time by doing
     * screen[bytePos] = 0xFF. The residual start and end of the line can be set using
     * masks.
     * @param screen
     * @param width
     * @param lineStartX
     * @param lineEndX
     * @param y
     */
    static void drawLine(byte[] screen, int width, int lineStartX, int lineEndX, int y) {
         // offset = leftover bits if not whole bytes
         int startOffset = lineStartX % 8;
         int firstFullByte = lineStartX / 8;
         // add one byte if the leftover bits is greater than 0
         if (startOffset != 0) firstFullByte++;

         int endOffset = lineEndX % 8;
         int lastFullByte = lineEndX / 8;
         // subtract one byte if the leftover bits is not 7
         if (endOffset != 7) lastFullByte--;

         // set full bytes
        for (int b = firstFullByte; b <= lastFullByte; b++)
            // set the elements in the array using byte chunks
            screen[(width / 8) * y + b] = (byte) 0xFF;

        // create masks for start and end of the line
        // take a byte and shift to the right to get the bits
        byte startMask = (byte) (0xFF >> startOffset);
        byte endMask = (byte) ~(0xFF >> (endOffset+1));
        System.out.print("startMask with startOffset " + startOffset + " : ");
        printByte(startMask);
        System.out.print("\n endMask with endOffset " + endOffset + " : ");
        printByte(endMask);

        // set start and end of the line
        if ((lineStartX / 8) == (lineEndX / 8)) { // x1 and x2 are in the same byte
            byte mask = (byte) (startMask & endMask);
            screen[(width / 8) * y + (lineStartX / 8)] |= mask;
        } else {
            if (startOffset != 0) {
                // number of bytes * number of rows + firstFullByte - 1
                int byteNumber = (width / 8) * y + firstFullByte - 1;
                screen[byteNumber] |= startMask;
            }
            if (endOffset != 7) {
                int byteNumber = (width / 8) * y + lastFullByte + 1;
                screen[byteNumber] |= endMask;
            }
        }
    }

    private static void printByte(byte b) {
        for (int i = 7; i >= 0; i--) {
            char c = ((b >> i) & 1) == 1 ? '1' : '_';
            System.out.print(c);
        }
    }

    private static void printScreen(byte[] screen, int width) {
        int height = screen.length * 8 / width;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col+=8) {
                byte b = screen[computeByteNum(width, col, row)];
                printByte(b);
            }
            System.out.println();
        }
    }

    private static int computeByteNum(int width, int col, int row) {
        return (width * row + col) / 8;
    }
}
