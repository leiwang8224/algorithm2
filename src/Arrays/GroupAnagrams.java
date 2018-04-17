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

    }

//    Intuition
//
//    Two strings are anagrams if and only if their sorted strings are equal.
//
//    Algorithm
//    Maintain a map ans : {String -> List} where each key K\text{K}K is a sorted string,
//    and each value is the list of strings from the initial input that when sorted, are equal to K\text{K}K.
//    In Java, we will store the key as a string, eg. code. In Python,
//    we will store the key as a hashable tuple, eg. ('c', 'o', 'd', 'e').
    // Time Complexity O(NKlog(K)), where N is the length of strs, K is the max length of a string in strs
    // The outer loop has complexity O(N) as we iterate through each string.
    // Then we sort each string in O(KlogK) time
    // Space Complexity O(N*K)
    private List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0) return new ArrayList<>();
        Map<String, List> ans = new HashMap<>();
        for (String s : strs) {
            char[] ca = s.toCharArray();
            java.util.Arrays.sort(ca);
            String key = String.valueOf(ca);
            if (!ans.containsKey(key)) ans.put(key, new ArrayList());
            ans.get(key).add(s);
        }
        return new ArrayList(ans.values());
    }

//    Intuition
//
//    Two strings are anagrams if and only if their character counts
//    (respective number of occurrences of each character) are the same.
    // Time Complexity O(N*K)
    // Space Complexity O(N*K)
    private List<List<String>> groupAnagrams2(String[] strs) {
        if (strs.length == 0) return new ArrayList<>();
        Map<String, List> ans = new HashMap<>();
        int[] count = new int[26];

        for (String s : strs) {
            Arrays.fill(count, 0);
            for (char c : s.toCharArray()) count[c-'a']++;

            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < 26; i ++) {
                sb.append('#');
                sb.append(count[i]);
            }
            String key = sb.toString();
            if (!ans.containsKey(key)) ans.put(key, new ArrayList());
            ans.get(key).add(s);
        }
        return new ArrayList<>(ans.values());
    }


}
