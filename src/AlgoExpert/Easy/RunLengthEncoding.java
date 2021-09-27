package AlgoExpert.Easy;

public class RunLengthEncoding {
    public static void main(String[] args) {

    }

    // O(n) time | O(n) space
    /**
     * Use two pointers to keep track of the difference between letters
     * only record length when the current char differs from prev char OR cur length == 9
     */
    private static String runLengthEncoding(String string) {
        StringBuilder encodedString = new StringBuilder();
        int curLen = 1;
        for (int i = 1; i < string.length(); i++) {
            char curChar = string.charAt(i);
            char prevChar = string.charAt(i-1);

            // if subsequent char changes or len is 9 add to result and reset len
            if ((curChar != prevChar) || curLen == 9) {
                encodedString.append(Integer.toString(curLen));
                encodedString.append(prevChar);
                curLen = 0;
            }
            curLen += 1;
        }

        //handle last run, simply append the result obtained from the for loop
        encodedString.append(Integer.toString(curLen));
        encodedString.append(string.charAt(string.length() - 1));

        return encodedString.toString();
    }
}
