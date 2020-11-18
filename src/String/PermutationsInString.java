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


    private static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) return false;
        int[] s1map = new int[26];
        for (int i = 0; i < s1.length(); i++)
            s1map[s1.charAt(i) - 'a']++;
        for (int diffIndex = 0; diffIndex <= s2.length() - s1.length(); diffIndex++) {
            int[] s2map = new int[26];
            for (int s1Index = 0; s1Index < s1.length(); s1Index++) {
                s2map[s2.charAt(diffIndex + s1Index) - 'a']++;
            }
            if (matches(s1map, s2map))
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
    private static boolean checkInclusionSlidingWindow(String shorterString, String longerString) {
        if (shorterString.length() > longerString.length())
            return false;
        int[] shorterStrHash = new int[26];
        int[] longerStrHash = new int[26];
        for (int i = 0; i < shorterString.length(); i++) {
            shorterStrHash[shorterString.charAt(i) - 'a']++;
            longerStrHash[longerString.charAt(i) - 'a']++;
        }
        for (int index = shorterString.length(); index < longerString.length(); index++) {
            if (matches(shorterStrHash, longerStrHash))
                return true;
            // index at beginning window add
            longerStrHash[longerString.charAt(index - shorterString.length()) - 'a']++;
            // index at end window subtract
            longerStrHash[longerString.charAt(index) - 'a']--;
        }
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
