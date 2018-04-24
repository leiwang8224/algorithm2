package DFS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by leiwang on 4/16/18.
 */

//Given two words (beginWord and endWord), and a dictionary's word list,
//find the length of shortest transformation sequence from beginWord to
//endWord, such that:
//
//Only one letter can be changed at a time.
//Each transformed word must exist in the word list. Note that beginWord
// is not a transformed word.
// For example,
//
// Given:
// beginWord = "hit"
// endWord = "cog"
// wordList = ["hot","dot","dog","lot","log","cog"]
// As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
// return its length 5.
//
// Note:
// Return 0 if there is no such transformation sequence.
// All words have the same length.
// All words contain only lowercase alphabetic characters.
// You may assume no duplicates in the word list.
// You may assume beginWord and endWord are non-empty and are not the same.

public class WordLadder {
    public static void main(String args[]) {
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = new ArrayList<>();
        wordList.add("hot");
        wordList.add("dot");
        wordList.add("dog");
        wordList.add("lot");
        wordList.add("log");
        wordList.add("cog");
        System.out.println("ladderLength = " + ladderLength(beginWord,endWord,wordList));

    }
//
//    Basically I keep two sets of words, one set reached that represents
//    the borders that have been reached with “distance” steps; another set
//    wordDict that has not been reached. In the while loop, for each word in
//    the reached set, I give all variations and check if it matches anything
//    from wordDict, if it has a match, I add that word into toAdd set, which
//    will be my “reached” set in the next loop, and remove the word from wordDict
//    because I already reached it in this step. And at the end of while loop,
//    I check the size of toAdd, which means that if I can’t reach any new String
//    from wordDict, I won’t be able to reach the endWord, then just return 0.
//    Finally if the endWord is in reached set, I return the current steps
//    “distance”.
//
//    The idea is that reached always contain only the ones we just reached in the
//    last step, and wordDict always contain the ones that haven’t been reached.
//    This is pretty much what Dijkstra’s algorithm does, or you can see this as
//    some variation of BFS.
    private static int ladderLength(String beginWord,
                                    String endWord,
                                    List<String> wordDict) {
        Set<String> reached = new HashSet<String>();
        reached.add(beginWord);
        wordDict.add(endWord);
        int distance = 1;
        while (!reached.contains(endWord)) {
            Set<String> toAdd = new HashSet<String>();
            for (String each : reached) {
                // for each letter inside the string, replace with 'a'-'z'
                // and check whether it's in the dictionary
                for (int i = 0; i < each.length(); i++) {
                    System.out.println("each = " + each);
                    char[] chars = each.toCharArray();
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        chars[i] = ch;
                        System.out.println(java.util.Arrays.toString(chars));
                        String word = new String(chars);
                        // if it's in the dict, add to the toAdd set and remove from dict
                        if (wordDict.contains(word)) {
                            toAdd.add(word);
                            wordDict.remove(word);
                        }
                    }
                }
            }
            distance++;
            if (toAdd.size() == 0) return 0;
            reached = toAdd;
        }
        return distance;

    }
}
