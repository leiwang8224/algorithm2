package String;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.HashMap;

public class SherlockAnagrams {
    public static void main(String[] args) {
        System.out.println(sherlockAnagrams("kkkk"));
    }

    static int sherlockAnagrams(String s) {
        HashMap<String, Integer> map = new HashMap<>();
        int startIndex, endIndex, substringIndex = 0;
        int length = s.length();
        int numSubstring = length * (length + 1)/2;
        String[] substrings = new String[numSubstring];

        // find all substrings
        for (startIndex = 0; startIndex < length; startIndex++) {
            for (endIndex = startIndex+1; endIndex <= length; endIndex++) {
                substrings[substringIndex++] = s.substring(startIndex, endIndex);
            }
        }

        System.out.println(Arrays.toString(substrings));

        int[] mapOfLetterFrequencyforSubstr= new int[26];

        int result = 0;

        // for each substring, there is a signature map of all the letters in the alphabet
        // provide signature for each substring
        for (int indexForEachSubstring = 0;
             indexForEachSubstring < numSubstring;
             indexForEachSubstring++) {
            // create hash to store the letters and frequency
            for (int letterIndex = 0; letterIndex < 26; letterIndex++) {
                mapOfLetterFrequencyforSubstr[letterIndex] = 0;
            }
            char[] charArrayForSubstr = substrings[indexForEachSubstring].toCharArray();
            for (char ch : charArrayForSubstr) {
                mapOfLetterFrequencyforSubstr[ch - 'a']++;
            }

            String signatureMapArray = Arrays.toString(mapOfLetterFrequencyforSubstr);
            System.out.println(signatureMapArray);

            // populate the signature map array and add results
            // add signature in map to find if there are duplicate signatures in substrings
            // if there are duplicates then add one to the result
            if (map.get(signatureMapArray) != null) {
                System.out.println("found signature " + signatureMapArray + " with value " + map.get(signatureMapArray) + " adding 1");
                result = result + map.get(signatureMapArray);
                map.put(signatureMapArray, map.get(signatureMapArray)+1);
            } else {
                System.out.println("putting signature " + signatureMapArray);
                map.put(signatureMapArray, 1);
            }


        }

        for (String key : map.keySet()) {
            System.out.println(key + "," + map.get(key));
        }
        return result;
    }
}
