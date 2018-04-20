package Arrays;

import java.util.*;

//Given an array of strings, group anagrams together.
//
//        Example:
//
//        Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
//        Output:
//        [
//        ["ate","eat","tea"],
//        ["nat","tan"],
//        ["bat"]
//        ]
public class GroupAnagrams {
    public static void main(String[] args) {
        String[] str = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result1 = groupAnagrams(str);
        System.out.println("first result");
        for (List<String> list : result1) {
            System.out.println(Arrays.toString(list.toArray()));
        }

        System.out.println("second result");
        List<List<String>> result2 = groupAnagrams2(str);
        for (List<String> list : result2) {
            System.out.println(Arrays.toString(list.toArray()));
        }

    }

//    Intuition
//
//    Two strings are anagrams if and only if their sorted strings are equal.
//
//    Algorithm
//    Maintain a map ans : {String -> List} where each key K\text{K}K is a sorted string,
//    and each value is the list of strings from the initial input that when sorted, are equal to K\text{K}K.
//    In Java, we will store the key as a string, eg. code.
    // Time Complexity O(NKlog(K)), where N is the length of strs, K is the max length of a string in strs
    // The outer loop has complexity O(N) as we iterate through each string.
    // Then we sort each string in O(KlogK) time
    // Space Complexity O(N*K)
    private static List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0) return new ArrayList<>();
        Map<String, List> ans = new HashMap<>();
        for (String s : strs) {
            // sort chars based on alphabetic order
            char[] ca = s.toCharArray();
            java.util.Arrays.sort(ca);
            // key is the sorted char array
            String key = String.valueOf(ca);
            // value is the original string
            if (!ans.containsKey(key)) ans.put(key, new ArrayList());
            ans.get(key).add(s);
        }

        for (Map.Entry<String, List> entry : ans.entrySet()) {
            System.out.println(entry.getKey() + " " + Arrays.toString(entry.getValue().toArray()));
        }
        //map output
//        aet [eat, tea, ate]
//        abt [bat]
//        ant [tan, nat]
        return new ArrayList(ans.values());
    }

//    Intuition
//
//    Two strings are anagrams if and only if their character counts
//    (respective number of occurrences of each character) are the same.
    // Time Complexity O(N*K)
    // Space Complexity O(N*K)
    private static List<List<String>> groupAnagrams2(String[] strs) {
        if (strs.length == 0) return new ArrayList<>();
        Map<String, List> ans = new HashMap<>();
        int[] count = new int[26];  // 26 letters in alphabet

        for (String s : strs) {
            Arrays.fill(count, 0);
            for (char c : s.toCharArray()) count[c-'a']++;

            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < 26; i ++) {
                sb.append('#');
                sb.append(count[i]);
            }
            // map key is an array with all 26 alphabet chars and freq
            // map value is the original array char values
            String key = sb.toString();
            if (!ans.containsKey(key)) ans.put(key, new ArrayList());
            ans.get(key).add(s);
        }

        for (Map.Entry<String, List> entry : ans.entrySet()) {
            System.out.println(entry.getKey() + " " + Arrays.toString(entry.getValue().toArray()));
        }
        //map output
//        #1#1#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#1#0#0#0#0#0#0 [bat]
//        #1#0#0#0#0#0#0#0#0#0#0#0#0#1#0#0#0#0#0#1#0#0#0#0#0#0 [tan, nat]
//        #1#0#0#0#1#0#0#0#0#0#0#0#0#0#0#0#0#0#0#1#0#0#0#0#0#0 [eat, tea, ate]
        return new ArrayList(ans.values());
    }


}
