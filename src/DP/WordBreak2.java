package DP;

import java.util.*;

/**
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
 add spaces in s to construct a sentence where each word is a valid dictionary word. Return
 all such possible sentences.

        Note:

        The same word in the dictionary may be reused multiple times in the segmentation.
        You may assume the dictionary does not contain duplicate words.

        Example 1:

        Input:
        s = "catsanddog"
        wordDict = ["cat", "cats", "and", "sand", "dog"]
        Output:
        [
        "cats and dog",
        "cat sand dog"
        ]
**/
public class WordBreak2 {
    public static void main(String[] args) {
        String str = "catsanddog";
        Set<String> dict = new HashSet<>();
        dict.add("cat");
        dict.add("cats");
        dict.add("and");
        dict.add("sand");
        dict.add("dog");
        List<String> result = wordBreak(str, dict);
        for (String s : result)
            System.out.println(s);
    }

    private static List<String> wordBreak(String str, Set<String> dict) {
        // create an array of ArrayList<String>
        List<String> dp[] = new ArrayList[str.length()+1];
        dp[0] = new ArrayList<>();

        for (int i = 0; i < str.length(); i ++) {
            if (dp[i] == null)
                continue;

            for (String word : dict) {
                int len = word.length();
                int end = i + len;
                if (end > str.length())
                    continue;

                if (str.substring(i,end).equals(word)) {
                    if (dp[end] == null) {
                        dp[end] = new ArrayList<>();
                    }
                    dp[end].add(word);
                }
            }
        }

        List<String> result = new LinkedList<>();
        if (dp[str.length()] == null) {
            return result;
        }

        ArrayList<String> temp = new ArrayList<>();
        dfs(dp, str.length(), result, temp);

        return result;
    }

    private static void dfs(List<String>[] dp, int length, List<String> result, ArrayList<String> temp) {
        if (length <= 0) {
            String path = temp.get(temp.size()-1);
            for (int i = temp.size() - 2; i >= 0; i--) {
                path += " " + temp.get(i);
            }
            result.add(path);
            return;
        }

        for (String str: dp[length]) {
            temp.add(str);
            dfs(dp, length-str.length(), result, temp);
            temp.remove(temp.size()-1);
        }
    }
}
