package String;

//LC-567
public class PermutationsInString {
    public static void main(String[] args) {
        String s1 = "ab";
        String s2 = "eidbaooo";

        System.out.println(checkInclusion(s1, s2));
        System.out.println(checkInclusionOptimize(s1, s2));
        System.out.println(checkInclusionSlidingWindow(s1, s2));
    }

    //s2 contains s1?
//    Time complexity : O(l1+26∗l1∗(l2−l1))
//    hashmap contains atmost 26 keys. where l1 is the length of
//    string l1 and l2 is the length of string l2
//
//    Space complexity : O(1). hashmap contains atmost 26 key-value pairs.


    private static boolean checkInclusion(String shorterString, String longerString) {
        if (shorterString.length() > longerString.length()) return false;
        int[] shorterStringMap = new int[26];
        for (int i = 0; i < shorterString.length(); i++)
            shorterStringMap[shorterString.charAt(i) - 'a']++;
        for (int diffIndex = 0; diffIndex <= longerString.length() - shorterString.length(); diffIndex++) {
            int[] longerStringMap = new int[26];
            for (int shorterStringIndex = 0; shorterStringIndex < shorterString.length(); shorterStringIndex++) {
                longerStringMap[longerString.charAt(diffIndex + shorterStringIndex) - 'a']++;
            }
            if (matches(shorterStringMap, longerStringMap))
                return true;
        }
        return false;
    }

    private static boolean matches(int[] s1map, int[] s2map) {
        for (int i = 0; i < 26; i++) {
            if (s1map[i] != s2map[i])
                return false;
        }
        return true;
    }



//    Time complexity : O(l1+26∗(l2−l1)),
//    where l1 is the length of string l1 and l2 is the length
//    of string l2.
//
//    Space complexity : O(1). Constant space is used.
//    How do we know string s2 contains a permutation of s1?
//    We just need to create a sliding window with length of s1,
//    move from beginning to the end of s2. When a character
//    moves in from right of the window, we subtract 1 to that
//    character count from the map. When a character moves out
//    from left of the window, we add 1 to that character count.
//    So once we see all zeros in the map, meaning equal numbers
//    of every characters between s1 and the substring in the
//    sliding window, we know the answer is true.
    //1. form hash for both strings
    //2. iterate through the longer string and adjust window size based on:
        //- character moves in from the right side, subtract 1 from hash for that char
        //- character moves out from the left side, add 1 to the hash for that char
    private static boolean checkInclusionSlidingWindow(String shorterString, String longerString) {
        if (shorterString.length() > longerString.length())
            return false;
        int[] shorterStrHash = new int[26];
        int[] longerStrHash = new int[26];
        // setup two hashes with only the first n chars of both strings
        // where n = shorterString.length
        for (int i = 0; i < shorterString.length(); i++) {
            shorterStrHash[shorterString.charAt(i) - 'a']++;
            longerStrHash[longerString.charAt(i) - 'a']++;
        }
        System.out.println(java.util.Arrays.toString(shorterStrHash));
        // continue traversing through rest of the longer string
        // iterate till we find the map matches
        for (int index = shorterString.length();
             index < longerString.length();
             index++) {
            if (matches(shorterStrHash, longerStrHash))
                return true;
            // index at beginning window subtract, update map
            longerStrHash[longerString.charAt(index - shorterString.length()) - 'a']--;
            // index at end window add, update map
            longerStrHash[longerString.charAt(index) - 'a']++;
            System.out.println(java.util.Arrays.toString(longerStrHash));
        }

        // check for matches at the end of the loop (after last char added to map)
        return matches(shorterStrHash, longerStrHash);
    }



//    Time complexity : O(l1+(l2−l1)).
//    where l1 is the length of string l1 and l2 is the length
//    of string l2.
//
//    Space complexity : O(1). Constant space is used.
    // TODO does not work

    private static boolean checkInclusionOptimize(String s1, String s2) {
        if (s1.length() > s2.length())
            return false;
        int[] s1map = new int[26];
        int[] s2map = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            s1map[s1.charAt(i) - 'a']++;
            s2map[s2.charAt(i) - 'a']++;
        }
        int count = 0;
        for (int i = 0; i < 26; i++)
            if (s1map[i] == s2map[i])
                count++;
        for (int i = 0; i < s2.length() - s1.length(); i++) {
            int r = s2.charAt(i + s1.length()) - 'a', l = s2.charAt(i) - 'a';
            if (count == 26)
                return true;
            s2map[r]++;
            if (s2map[r] == s1map[r])
                count++;
            else if (s2map[r] == s1map[r] + 1)
                count--;
            s2map[l]--;
            if (s2map[l] == s1map[l])
                count++;
            else if (s2map[l] == s1map[l] - 1)
                count--;
        }
        return count == 26;
    }

}
