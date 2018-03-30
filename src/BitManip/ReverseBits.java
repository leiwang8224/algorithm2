package BitManip;

import java.util.HashMap;
import java.util.Map;

public class ReverseBits {
    private static final Map<Byte, Integer> cache = new HashMap<Byte,Integer>();
    public static void main(String args[]) {
        reverseBits(10);
    }

    private static int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result += n & 1;
            n >>>= 1;   // CATCH: must do logical shift instead of arithmetic shift
            if (i < 31) // CATCH: for last digit, do not shift
                result <<= 1;
        }
        return result;
    }

    /**
     * Split into bytes revert then get the integer back
     * @param n
     * @return
     */
    private static int reverseBits2(int n) {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i ++) // convert int into 4 bytes
            bytes[i] = (byte)((n >>> 8*i) & 0xFF);
        int result = 0;
        for (int i =0; i < 4; i ++) {
            result += reverseByte(bytes[i]);  // reverse per byte
            if (i < 3)
                result <<= 8;
        }
        return result;
    }

    private static int reverseByte(byte aByte) {
        Integer value = cache.get(aByte);  // first look up in cache
        if (value != null)
            return value;
        value = 0;

        // reverse by bit
        for (int i = 0; i < 8; i++) {
            value += ((aByte >>> i) & 1);
            if (i < 7)
                value <<= 1;
        }
        cache.put(aByte, value);
        return value;
    }
}
