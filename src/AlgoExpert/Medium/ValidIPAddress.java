package AlgoExpert.Medium;

import java.util.ArrayList;

public class ValidIPAddress {
    public static void main(String[] args) {

    }

    /**
     * 3 nested for loops to go through all combinations of section division.
     * key points:
     * - use substring to extract the ip subsections, iterate through all possible end indices
     * - take note of the end index due to substring api
     * - always take the min(string.len - last section endindex, 4)
     * - 3 nested for loops
     *   - for each indices in first subsection (start from index 1 as end index
     *     as substring extraction starts from 0)
     *     - for each indices in second subsection
     *       - for each indices in third subsection
     *         - take care of both third and fourth subsections
     */
    private ArrayList<String> validIPAddress(String string) {
        ArrayList<String> validIpFound = new ArrayList<>();

        // first section extraction from index 0 to min(len, 4)
        for (int firstDigitEndIndex = 1;
             firstDigitEndIndex < Math.min(string.length(), 4);
             firstDigitEndIndex++) {
            // array of length 4 to store each section and join with period at end
            String[] curIPAddr = new String[] {"", "", "", ""};

            // take the first digit as first section
            curIPAddr[0] = string.substring(0, firstDigitEndIndex);
            // check if the first section is valid, exit for loop if not valid
            if (!isValidPartForIP(curIPAddr[0])) {
                continue;
            }

            // take the second digit as second section
            // second digit from first digit + 1 to min(first digit + 4, length-first digit)
            for (int secondDigitEndIndex = firstDigitEndIndex + 1;
                 // since the string length might be shorter than 4, take the min
                 secondDigitEndIndex < firstDigitEndIndex + Math.min(string.length() - firstDigitEndIndex, 4);
                 secondDigitEndIndex++) {
                // take the second digit
                curIPAddr[1] = string.substring(firstDigitEndIndex, secondDigitEndIndex);
                // check if the second section is valid, exit for loop if not valid
                if (!isValidPartForIP(curIPAddr[1])) {
                    continue;
                }
                // take the third digit as third section and fourth digit as fourth section
                for (int thirdDigitEndIndex = secondDigitEndIndex + 1;
                     thirdDigitEndIndex < secondDigitEndIndex + Math.min(string.length() - secondDigitEndIndex, 4);
                     thirdDigitEndIndex++) {
                    // take the third digit for the third section
                    curIPAddr[2] = string.substring(secondDigitEndIndex, thirdDigitEndIndex);
                    // take the rest of the digits for the fourth section
                    curIPAddr[3] = string.substring(thirdDigitEndIndex);

                    // make sure third and fourth section are valid,
                    // if so insert period and add to results
                    if (isValidPartForIP(curIPAddr[2]) && isValidPartForIP(curIPAddr[3])) {
                        validIpFound.add(joinWithPeriod(curIPAddr));
                    }
                }

            }

        }
        return validIpFound;
    }

    private String joinWithPeriod(String[] strings) {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < strings.length; index++) {
            // append a digit followed by a period
            sb.append(strings[index]);
            if (index < strings.length -1) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    /**
     * valid section is defined as less than 255 and with no leading 0's
     */
    private boolean isValidPartForIP(String str) {
        int stringAsInt = Integer.parseInt(str);
        if (stringAsInt > 255) return false;

        // check for leading 0
        return str.length() == Integer.toString(stringAsInt).length();
    }

    /**
     * NOTE does not work for input  255255255255 and 255255255256
     */
    public ArrayList<String> validIPAddressesMySol(String string) {
        ArrayList<String> result = new ArrayList<>();
        int len = string.length();
        for (int endIdx = 1; endIdx < Math.min(4, len); endIdx++) {
            String[] ipAddr = new String[4];
            ipAddr[0] = string.substring(0, endIdx);
            if (notValidIP(ipAddr[0])) continue;
            for (int endIdx2 = endIdx + 1; endIdx2 < endIdx2 + Math.min(4, len - endIdx2); endIdx2++) {
                ipAddr[1] = string.substring(endIdx, endIdx2);

                if (notValidIP(ipAddr[1])) continue;
                for (int endIdx3 = endIdx2 + 1; endIdx3 < endIdx3 + Math.min(4, len - endIdx3); endIdx3++) {
                    ipAddr[2] = string.substring(endIdx2, endIdx3);
                    ipAddr[3] = string.substring(endIdx3);

                    if (notValidIP(ipAddr[2]) || notValidIP(ipAddr[3])) {
                        continue;
                    }
                    result.add(String.join(".", ipAddr));
                }
            }
        }
        // Write your code here.
        return result;
    }

    private boolean notValidIP(String subSection) {
        int num = Integer.parseInt(subSection); // cant parse large nums?
        if (num > 255) return true;
        return Integer.toString(num).length() == subSection.length() ? false : true;
    }
}
