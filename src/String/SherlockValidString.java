package String;

//        Sample Input 0
//
//        aabbcd
//
//        Sample Output 0
//
//        NO
//
//        Sample Input 1
//
//        aabbccddeefghi
//
//        Sample Output 1
//
//        NO
//
//        Sample Input 2
//
//        abcdefghhgfedecba
//
//        Sample Output 2
//
//        YES
//


import java.util.Arrays;
import java.util.HashMap;

public class SherlockValidString {
    public static void main(String[] args) {
        System.out.println(isValid("aaabbbcccd"));
        System.out.println(isValid("aabbccde"));
        System.out.println(isValid("aabbccddd"));

    }

    // valid if:
    // 1. same count for all letters
    // 2. not valid if there are two different type of frequencies of letters
    // 3. valid if:
    // - different between the most common freq and the other freq is 1
    // - uncommon freq appears only once
    // - uncommon freq is 1
    private static String isValid(String s) {
        final String GOOD = "YES";
        final String BAD = "NO";

        if(s.isEmpty()) return BAD;
        if(s.length() <= 3) return GOOD;

        int[] letters = new int[26];
        for(int i = 0; i < s.length(); i++){
            letters[s.charAt(i) - 'a']++;
        }
        java.util.Arrays.sort(letters);
        int numberOfLowestFrequencyLetters=0;

        // count number of zero frequency letters
        while(letters[numberOfLowestFrequencyLetters]==0){
            numberOfLowestFrequencyLetters++;
        }
        //System.out.println(Arrays.toString(letters));
        // lowest frequency
        int min = letters[numberOfLowestFrequencyLetters];   //the smallest frequency of some letter
        // the highest frequency
        int max = letters[25]; // the largest frequency of some letter
        String ret = BAD;
        if(min == max) ret = GOOD; // all freq are the same
        else{
            // remove one letter at higher frequency or the lower frequency
            // max and min differs by 1 and there is one distinct max
            if(((max - min == 1) && (max > letters[24])) || // or min is 1 and lowest frequency beside 0 is located next to the max
                    (min == 1) && (letters[numberOfLowestFrequencyLetters+1] == max))
                System.out.println(Arrays.toString(letters));
                System.out.println(numberOfLowestFrequencyLetters);
                ret = GOOD;
        }
        return ret;
    }


}
