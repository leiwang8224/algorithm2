package CTCI.BitManipulation;

/**
 * Given a real number between 0 and 1 (ex. 0.72) that is passed in as a double
 * print the binary representation. If the number cannot be represented accurately
 * in binary with at most 32 chars, print "ERROR".
 * Input: n = 0.47, k = 5
 * Output: 0.01111
 *
 * Input: n = 0.986 k = 8
 * Output: 0.11111100
 *
 * ex: b0.101 = 1 * 1 / 2^1 + 0 * 1 / 2^2 + 1 * 1 / 2^3
 */
public class BinaryToString {
    public static void main(String[] args) {
        System.out.println(printBinaryLessThan1(0.7));
        System.out.println(printDecimal(0.671).toString());
        System.out.println(decimalToBinary(0.671,32));
        System.out.println(printBinary2(0.671));

    }

    /**
     * To print the decimal part, we can multiply by 2 and check if 2n
     * is greater than or equal to 1. This is essentially shifting
     * the fractional sum.
     * TODO does not work
     * @param num
     * @return
     */
    private static String printBinaryLessThan1(double num) {
        if (num >=1 || num <= 0) return "ERROR";

        StringBuilder binary = new StringBuilder();

        binary.append(".");
        while (num > 0) {
            // setting a limit on length: 32 chars
            if (binary.length() > 32) return "ERROR";
            double result = num * 2;
            if (result >= 1) {
                binary.append(1);
                num = result - 1;
            } else {
                binary.append(0);
                num = result;
            }
        }

        return binary.toString();
    }

    /**
     * //TODO not working
     * What if the number is greater than 1?
     * @param decimalNum
     * @param k_prec
     * @return
     */
    private static String decimalToBinary(double decimalNum, int k_prec) {
        StringBuilder binary = new StringBuilder();

        // get the integral part of the decimal number
        int wholeNumber = (int)decimalNum;

        // get the fractional part of decimal number
        double faction = decimalNum - wholeNumber;

        // convert integral part
        while (wholeNumber > 0) {
            int rem = wholeNumber % 2;

            // append 0 in binary
            binary.append ((char)(rem + '0'));

            wholeNumber = wholeNumber / 2;
        }

        // reverse string to get original binary equivalent
        binary = reverse(binary.toString());

        // append point before the conversion of fraction
        binary.append(".");

        // conversion of fraction to binary
        while(k_prec > 0) {
            // find next bit in fraction
            faction = faction * 2;
            int fractBit = (int) faction;

            if (fractBit == 1) {
                faction = faction - fractBit;
                binary.append((char)(1 + '0'));
            } else {
                binary.append((char)(0 + '0'));
            }
            k_prec--;
        }
        return binary.toString();

    }

    private static StringBuilder reverse(String string) {
        char[] tempArray = string.toCharArray();
        int left, right = 0;
        right = string.length()-1;

        for (left = 0; left < right; left++, right--) {
            // swap values of left and right
            char temp = tempArray[left];
            tempArray[left] = tempArray[right];
            tempArray[right] = temp;
        }

        StringBuilder result = new StringBuilder();

        for (char ch : tempArray) {
            result.append(ch);
        }
        return result;
    }

    /**
     * Extract integer and decimal part
     * - convert the integer part to binary
     * - convert decimal part to binary
     * @param originalNumber
     * @return
     */
    private static StringBuilder printDecimal(double originalNumber) {
        StringBuilder str = new StringBuilder();
        int integerPart = (int) originalNumber;
        double decimalPart = originalNumber -integerPart;

        while (integerPart > 0) {
            // get the modulus and continue division
            str.append(integerPart % 2);
            integerPart = integerPart / 2;
        }

        // need to reverse the string
        str = str.reverse();
        int binaryDigitCount = 0;
        str.append(".");

        // take the decimal part and multiply by 2 until the decimal
        // part is 0.5 (1/2). This is essentially shifting the the digits
        while (true) {
            // terminating condition is when the decimal
            // is small enough = 0.5 ( 2 ^ -1 = 1 / 2)
            if (decimalPart * 2 == 1) {
                str.append(1);
                break;
            }

            // take the integer part and * 2
            str.append((int) (decimalPart*2));
            binaryDigitCount++;
            // number of digits precision binary
            if (binaryDigitCount == 32) break;

            // if greater than 1, subtract 1 to extract the decimal part
            if (decimalPart * 2 > 1) decimalPart = decimalPart * 2 - 1;
            // else keep the decimal part if it is smaller than 1 and multiply by 2
            else if (decimalPart * 2 < 1) decimalPart = decimalPart * 2;
        }
        return str;

    }

    static String printBinary2(double num) {
        if (num >= 1 || num <= 0) return "ERROR";

        StringBuilder binary = new StringBuilder();
        double frac = 0.5;
        binary.append(".");

        while (num > 0) {
            // set a limit on length: 32 chars
            if (binary.length() > 32) return "ERROR";

            if (num >= frac) {
                binary.append(1);
                num = num - frac;
            } else {
                binary.append(0);
            }
            // frac gets smaller and smaller
            frac = frac / 2;  // 0.25, 0.125...
        }

        return binary.toString();
    }
}
