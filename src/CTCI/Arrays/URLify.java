package CTCI.Arrays;

// write a method to replace all spaces in a string with '%20'. Assume the string has sufficient spaces
// at the end to hold the additional chars, and you are given the true length of the string.
public class URLify {
    public static void main(String[] args) {
        String str = "Mr John Smith    ";
        char[] arr = str.toCharArray();

        // trueLength is the index of the last non-space index + 1
        // this is basically the length of the string
        int trueLength = findLastChar(arr) + 1;
        System.out.println("trueLength = " + trueLength + " strLength = " + str.length());
        replaceSpaces(arr, trueLength);
        System.out.println("\"" + charArrayToString(arr)+ "\"");
    }

    private static String charArrayToString(char[] charArray) {
        StringBuilder str = new StringBuilder();
        for (char ch : charArray) {
            str.append(ch);
        }

        return str.toString();
    }
    private static void replaceSpaces(char[] str, int trueLength) {
        int spaceCount = 0, indexOfNewString, indexOfOldString = 0;

        for (indexOfOldString = 0; indexOfOldString < trueLength; indexOfOldString++) {
            if (str[indexOfOldString] == ' ') {
                spaceCount++;
            }
        }

        indexOfNewString = trueLength + spaceCount * 2;
        System.out.println("str length = "  + str.length + " index = " + indexOfNewString);

        //null terminated string, add null in the end
        if (trueLength < str.length) str[trueLength] = '\0';

        for (indexOfOldString = trueLength - 1; indexOfOldString >= 0; indexOfOldString--) {
            if (str[indexOfOldString] == ' ') {
                str[indexOfNewString - 1] = '0';
                str[indexOfNewString - 2] = '2';
                str[indexOfNewString - 3] = '%';
                indexOfNewString = indexOfNewString - 3;
            } else {
                str[indexOfNewString - 1] = str[indexOfOldString];
                indexOfNewString --;
            }
        }
    }

    // find last char that is not a space (Given string has spaces in the end)
    private static int findLastChar(char[] str) {
        for (int i = str.length - 1; i >= 0; i--) {
            if (str[i] != ' ') {
                return i;
            }
        }
        return -1;
    }
}
