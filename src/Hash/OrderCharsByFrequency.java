package Hash;

//Given a string, sort it in decreasing order based on the frequency of characters.
//
//        Example 1:
//
//        Input:
//        "tree"
//
//        Output:
//        "eert"
//
//        Explanation:
//        'e' appears twice while 'r' and 't' both appear once.
//        So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
//
//        Example 2:
//
//        Input:
//        "cccaaa"
//
//        Output:
//        "cccaaa"
//
//        Explanation:
//        Both 'c' and 'a' appear three times, so "aaaccc" is also a valid answer.
//        Note that "cacaca" is incorrect, as the same characters must be together.
//
//        Example 3:
//
//        Input:
//        "Aabb"
//
//        Output:
//        "bbAa"
//
//        Explanation:
//        "bbaA" is also a valid answer, but "Aabb" is incorrect.
//        Note that 'A' and 'a' are treated as two different characters.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderCharsByFrequency {

    public static void main(String[] args) {

    }

    private static String frequencySort(String str) {
        HashMap<Character, Integer> map = new HashMap<>();

        // chars and freq
        for (char c : str.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        // array of lists
        List<Character>[] bucket = new List[str.length() + 1];

        for (char key : map.keySet()) {
            int freq = map.get(key);

            // if list at location freq is null, create new array at index freq
            // the position of the last bucket index is the greatest frequency
            if (bucket[freq] == null) bucket[freq] = new ArrayList<>();

            // for this freq, add corresponding char
            bucket[freq].add(key);
        }

        StringBuilder sb = new StringBuilder();
        // going backwards because the position of the last bucket index is the
        // greatest frequency
        for (int pos = bucket.length - 1; pos >= 0; pos --)
            if (bucket[pos] != null)
                for (char c : bucket[pos])  //put it back in result
                    for (int i = 0; i < map.get(c); i ++)
                        sb.append(c);
        return sb.toString();
    }
}
