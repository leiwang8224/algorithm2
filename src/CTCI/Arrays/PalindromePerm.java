package CTCI.Arrays;

//Given a string, write a function to check if it is a permutation of a palindrome.
//Palindrome is spelled the same forward and backward.
public class PalindromePerm {
    public static void main(String[] args) {
        String[] strings = {"Rats live on no evil star",
                            "A man, a plan, a canal, panama",
                            "Lleve",
                            "Tacotac",
                            "asda"};
        for (String s : strings) {
            boolean a = isPermOfPalindrome(s);
            System.out.println(s);
            if (a) {
                System.out.println("Agree: " + a);
            } else {
                System.out.println("Disagree: " + a);
            }
            System.out.println();
        }
    }

    // Returns the equivalent ASCII code for the character
    private static int getCharASCII(Character c) {
        int a = Character.getNumericValue('a');
        int z = Character.getNumericValue('z');

        int val = Character.getNumericValue(c);

        if (a <= val && val <= z) {
            return val - a;
        }
        
        return -1;
    }

    // Build ASCII table with frequency of the chars
    private static int[] buildCharFreqTable(String phrase) {
        int[] table = new int[Character.getNumericValue('z') -
                              Character.getNumericValue('a') + 1];
        for (char c : phrase.toCharArray()) {
            int x = getCharASCII(c);
            if (x != -1) {
                // increment counter on this char
                table[x] ++;
            }
        }
        return table;
    }

    private static boolean checkMaxOneOdd(int[] table) {
        boolean foundOdd = false;
        for (int count : table) {
            // if there is odd number of a certain char, return false
            if (count % 2 == 1) {
                if (foundOdd) {
                    return false;
                }
                foundOdd = true;
            }
        }
        // return true when there is even number of all the chars
        return true;
    }

    private static boolean isPermOfPalindrome(String phrase) {
        int[] table = buildCharFreqTable(phrase);
        return checkMaxOneOdd(table);
    }
}
