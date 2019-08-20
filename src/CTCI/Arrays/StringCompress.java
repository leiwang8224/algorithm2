package CTCI.Arrays;

// Implement a method to perform basic string compression using the counts of repeated characters.
// For exmaple the string aabbcc = a2b2c2
public class StringCompress {
    public static void main(String[] args) {
        String str = "aaaaabbbbaaaabbddc";
        System.out.println(str);
        System.out.println(compressStringWrong(str));
    }

    // note does not handle the end of the string
    private static String compressStringWrong(String str) {
        StringBuilder strResult = new StringBuilder();
        int index = 0;
        int charRepeats = 1;
        while(index < str.length()-1) {
            if (str.charAt(index)!= str.charAt(index+1)) {
                strResult.append(str.charAt(index));
                if (charRepeats > 1) strResult.append(charRepeats);
            } else {
                if (charRepeats == 1) strResult.append(str.charAt(index));
                charRepeats++;
            }
            index++;

        }
        return strResult.toString();
    }

    private static String compressBad(String str) {
        String compressedStr = "";
        int countForCompression = 0;

        for (int index = 0; index < str.length(); index++) {
            countForCompression ++;

            // if next char is diff than current or end of string, append this char to result
            if (index + 1 >= str.length() ||  // handle the end of string case
                str.charAt(index) != str.charAt(index+1)) {
                compressedStr += "" + str.charAt(index) + countForCompression;
                countForCompression = 0;
            }
        }

        // only return if the compressed string is smaller
        return compressedStr.length() < str.length() ? compressedStr : str;
    }

    private static String compress(String str) {
        StringBuilder compressed = new StringBuilder();

        int countForCompression = 0;

        for (int index = 0; index < str.length(); index++) {
            countForCompression++;
            if (index + 1 >= str.length() ||  // handle the end of string case
                str.charAt(index) != str.charAt(index + 1)) {
                compressed.append(str.charAt(index));
                compressed.append(countForCompression);
                countForCompression = 0;
            }
        }
        return compressed.length() < str.length() ? compressed.toString() : str;
    }
}
