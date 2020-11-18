package String;

//LC-880
public class DecodeString {
    public static void main(String[] args) {
        String leet3code2 = "a2345678999999999999999"; //leetleetcodeleetleetcodeleetleetcode

        System.out.println(leet3code2);
        System.out.println(decodeAtIndex(leet3code2, 1));
        System.out.println(decodeAtIndex2(leet3code2, 1));

    }
    //Time complexity O(N), space compleixty O(1)
    // 1. decompress the string and find size of the string
    // 2. use K mod decompressed string length till result becomes 0 while compress the string (reverse operation from 1)
    public static String decodeAtIndex(String S, int K) {
        long decompressedSize = 0;
        int compressedLength = S.length();

        // count the size of the decompressed string
        for (int compressedIndex = 0; compressedIndex < compressedLength; compressedIndex++) {
            char currentChar = S.charAt(compressedIndex);
            if (Character.isDigit(currentChar)) {
                decompressedSize *= currentChar - '0';
            } else {
                decompressedSize++;
            }
        }

        System.out.println("total decomp size = " + decompressedSize);
        // every iteration size is decreasing, K is only changed when size <= K
        // count backwards from end of compressed string
        // note that the loop iterate through compressed string while the K mod uses decompressed index
        for (int i = compressedLength-1; i>= 0; --i) {
            char c = S.charAt(i);
            // keep on K mod decompressedSize till K == 0 (K is divisible by decomp size) then return letter
            K %= decompressedSize;
            System.out.println("beginning of loop K = " + K + " c = " + c + " indexCompressed = " + i);

            if (K==0 && Character.isLetter(c))
                return Character.toString(c);

            // doing exactly opposite of the previous for loop
            if (Character.isDigit(c)) {
                // this is to get back the compressed version
                decompressedSize /= c-'0';
                System.out.println("char is number = " + c + " decomp size = " + decompressedSize + " K = " + K);
            } else {
                decompressedSize--;
                System.out.println("char is letter, digit = " + c + " decomp size = " + decompressedSize + " K = " + K);

            }
            System.out.println("end of loop");
        }
        throw null;
    }

    public static String decodeAtIndex2(String S, int K) {
        int i = 0;
        long decodedLength = 0;
        char c;

        // loop through to either find the char at K or the decodedLength just past K
        for (i = 0; i < S.length(); i++) {
            c = S.charAt(i);
            if (Character.isDigit(c)) {
                decodedLength *= c - '0';
            } else {
                // K index is found before full expansion
                if (K - 1 == decodedLength)
                    return String.valueOf(c);
                decodedLength++;
            }
            // if length goes beyond K break
            if (decodedLength >= K) break;
        }

        // we have gone past K, so need to go backwards to find the real char at K
        for (; i>= 0; i--) {
            c = S.charAt(i);
            K %= decodedLength;
            if (K == 0 && !Character.isDigit(c))
                return String.valueOf(c);

            if (Character.isDigit(c)) {
                decodedLength = decodedLength / (c-'0');
            } else {
                decodedLength--;
            }
        }
        return null;
    }
}
