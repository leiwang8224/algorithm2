package Math;

public class RomanNumeralToInt {
    public static void main(String args[]) {
        String str ="MCMIV";
        System.out.println("result for " + str + " is " + romanToDInt(str));
    }

    private static int getValue(char r) {
        if (r == 'I')
            return 1;
        if (r == 'V')
            return 5;
        if (r == 'X')
            return 10;
        if (r == 'L')
            return 50;
        if (r == 'C')
            return 100;
        if (r == 'D')
            return 500;
        if (r == 'M')
            return 1000;

        return -1;
    }
    private static int romanToDInt(String str) {
        int res = 0;

        for (int index = 0; index < str.length(); index++) {
            // getting value of symbol s[i]
            int s1 = getValue(str.charAt(index));

            if (index + 1 < str.length()) {
                // getting value of symbol s[i+1]
                int s2 = getValue(str.charAt(index + 1));

                // comparing both values
                if (s1 >= s2) {
                    // value of current symbol is greater
                    // or equal to the next symbol
                    res = res + s1;
                } else {
                    res = res + s2 + s1;
                    index++; // value of current symbol is
                    // less than the next symbol
                }
            } else {
                res = res + s1;
                index++;
            }
        }
        return res;
    }
}
