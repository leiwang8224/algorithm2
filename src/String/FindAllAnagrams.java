package String;

import java.util.ArrayList;
import java.util.List;

//LC-438
public class FindAllAnagrams {
    public static void main(String[] args) {
//        System.out.println(findAnagrams3("cbaebabacd","abc"));
//        System.out.println(findAnagrams2("cbaebabacd","abc"));
        System.out.println(findAnagramsTest("cbaebabacd","abc"));


    }

//
//    Steps -
//
//    Build map from P with frequency of characters, P = 'abc' map = ['a' = 1, 'b' = 1,'c' = 1]
//    Initially, window Start = 0, window End = 0
//    Traverse over string S,
//
//    if character at windowEnd exists in map decrement that character in map and increment the windowEnd. ( increase the window for valid anagram)
//    if character does not exist in map, add the character at window start back in map and increment windowStart (reduce the window)
//    if windowStart and windowEnd are at same index, increment both since size of window is 0.

    // notice that we need to focus on matching the size of the window length rather than looking at the hash
    // if size of window length is equal to p length then we found the anagram
    public static List<Integer> findAnagrams3(String s, String p) {
        int[] map = new int[26];
        List<Integer> result = new ArrayList<>();

        for(int i=0;i<p.length();i++){
            map[p.charAt(i) - 'a']++;
        }

        int windowStart = 0;
        int windowEnd = 0;
        while(windowEnd<s.length()){
            // valid anagram
            if(map[s.charAt(windowEnd) - 'a'] > 0){ // if there is a char in map, decrement
                // increment window size and decrement the count in hash
                map[s.charAt(windowEnd) - 'a']--;
                windowEnd++;
                // window size equal to size of P
                if(windowEnd-windowStart ==  p.length()){ // found anagram if the window length is equal to p length
                    result.add(windowStart);
                }
            }
            // window is of size 0
            else if(windowStart == windowEnd){
                windowStart ++;
                windowEnd ++;
            }
            // decrease window size if the char doesnt exist in map and add to map
            else{
                map[s.charAt(windowStart) - 'a']++;
                windowStart++;
            }
        }
        return result;
    }

    public static List<Integer> findAnagramsTest(String s, String p) {
        // form hash for p
        // iterate through s and inc and dec based on the hash
        // if length of the window == p.length then return startIndex
        // adjustable window

        int[] hash = new int[26];

        for (int index = 0; index < p.length(); index++) {
            hash[p.charAt(index) - 'a']++;
        }

        int startIndex = 0;
        int endIndex = 0;
        List<Integer> result = new ArrayList<>();

        while (endIndex < s.length()) {
            // NOTE that you check for char at end Index and add at end Index
            if (hash[s.charAt(endIndex) - 'a'] > 0) {
                hash[s.charAt(endIndex) - 'a']--;
                endIndex++;
                if (endIndex - startIndex == p.length()) {
                    System.out.println("adding");
                    result.add(startIndex);
                }
            }

            else if (startIndex == endIndex) {
                startIndex++;
                endIndex++;
            }

            else {
                //NOTE that you add the char at startIndex
                hash[s.charAt(startIndex) - 'a']++;
                startIndex++;
            }
        }

        return result;

    }

    public static List<Integer> findAnagrams2(String s, String p) {
        int count = p.length();
        int[] freq = new int[26];

        for (int index = 0; index < p.length(); index++)
            freq[p.charAt(index) - 'a']++;

        List<Integer> result = new ArrayList<>();
        int start = 0, end = 0, len = p.length();

        while (end < s.length()) {
            // reduce count if char found
            if (--freq[s.charAt(end++) - 'a'] >= 0) count--;

            // if pLength is 0, we found an anagram
            if (count == 0) {
                System.out.println("found anagram");
                result.add(start);
            }

            //A positive count later on means that the character is still "needed" in the anagram
            //A negative count means that either the character was found more times than necessary
            //Or that it isn't needed at all
            // note that when the expression is evaluated the var is incremented accordingly
            if (end - start == len && ++freq[s.charAt(start++) - 'a'] > 0) count++;
            System.out.println(java.util.Arrays.toString(freq));
        }
        return result;
    }

    public static List<Integer> findAnagrams(String s, String p) {
        ///We will use sliding window template

        ArrayList<Integer> soln = new ArrayList<Integer>();

        //Check for bad input
        if (s.length() == 0 || p.length() == 0 || s.length() < p.length()){
            return new ArrayList<Integer>();
        }

        //Set up character hash
        //Keep track of how many times each character appears
        int[] chars = new int[26];
        for (Character c : p.toCharArray()){
            //Increment to setup hash of all characters currently in the window
            //Later on, these get DECREMENTED when a character is found
            //A positive count later on means that the character is still "needed" in the anagram
            //A negative count means that either the character was found more times than necessary
            //Or that it isn't needed at all
            chars[c-'a']++;
        }

        //Start = start poniter, end = end pointer,
        //len = length of anagram to find
        //diff = length of currently found anagram. If it equals
        //the length of anagram to find, it must have been found
        int start = 0, end = 0, len = p.length(), numberCharsDiff = len;

        char temp;
        //Before we begin this, the "window" has a length of 0, start and
        //end pointers both at 0
        for (end = 0; end < len; end++){
            //Process current char
            temp = s.charAt(end);

            //As discussed earlier, decrement it
            chars[temp-'a']--;

            //If it's still >= 0, the anagram still "needed" it so we count it towards the anagram by
            //decrementing diff
            if (chars[temp-'a'] >= 0){
                numberCharsDiff--;
            }
        }

        //This would mean that s began with an anagram of p
        if (numberCharsDiff == 0){
            soln.add(0);
        }

        //At this point, start remains at 0, end has moved so that the window is the length of the anagram
        //from this point on we are going to be moving start AND end on each iteration, to shift the window
        //along the string
        while (end < s.length()){

            //Temp represents the current first character of the window. The character that is
            //going to be "left behind" as the window moves.
            temp = s.charAt(start);

            //If it's not negative, this means that the character WAS part of the anagram. That means we
            //are one step "farther away" from completing an anagram. So we must increment diff.
            if (chars[temp-'a'] >= 0){
                numberCharsDiff++;
            }

            //Increment the hash value for this character, because it is no longer contained in the window
            chars[temp-'a']++;

            //Increment start to start shifting the window over by 1
            start++;

            //Temp represents the last character of the window, the "new" character from the window shift.
            //This character "replaces" the one we removed before so the window stays the same length (p.length())
            temp = s.charAt(end);

            //Decrement hash value for this character, because it is now a part of the window
            chars[temp-'a']--;

            //Again, if it's not negative it is part of the anagram. So decrement diff
            if (chars[temp-'a'] >= 0){
                numberCharsDiff--;
            }

            //If diff has reached zero, that means for the last p.length() iterations, diff was decremented and
            //NOT decremented, which means every one of those characters was in the anagram, so it must be an anagram

            //Note: If many windows in a row find anagrams, then each iteration will have diff incremented then decremented again
            if (numberCharsDiff == 0){
                soln.add(start);
            }

            //Increment for next iteration
            end++;

        }

        return soln;
    }
}
