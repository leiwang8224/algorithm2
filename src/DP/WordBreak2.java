package DP;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by leiwang on 3/28/18.
 */
public class WordBreak2 {
    public static void main(String[] args) {

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
