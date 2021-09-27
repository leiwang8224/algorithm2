package Hash;

import java.util.HashMap;
import java.util.HashSet;

//Given a String input, find the length of the longest substring that is made up
// of non-repeating characters. For ex, the longest substrings without repeated
// characters in “BCEFGHBCFG” are “CEFGHB” and “EFGHBC”, with length = 6.
// In the case of "FFFFF", the longest substring is "F" with a length of 1.
// Note: keep in mind that the non-repeating chars sequences could overlap
public class LongestNonRepeatingSubstring {
    public static void main(String[] args) {
        String testString = "BCEFGHBCFG";
        System.out.println(longestNRSubstringLen(testString));
        System.out.println(lengthOfLongestSubstring(testString));
        System.out.println(lengthOfLongestSubStringSet(testString));
        System.out.println(longestUniqueSubstr(testString));
    }

    /**
     * Sliding window that leverages the hashmap
     * 1. iterate through the string and put in hashmap if not exist <element, index>
     * 2. if element already exist in the hashmap
     *    - take the max of length so far (hashmap size)
     *    - get the index of the previous duplicate and set it to the for loop index
     *    - clear the map to start over again
     * 3. return the max of the size of hashmap and the max of length so far
     * @param input
     * @return
     */
    private static int longestNRSubstringLen(String input) {
        if (input == null)
            return 0;
        char[] charArray = input.toCharArray();
        int maxDistinctNums = 0;

        HashMap<Character, Integer> characterMap = new HashMap<>();

        //Index is jumping around and not stable
        //Not really a good implementation???
        for (int index = 0; index < charArray.length; index++) {
            if (!characterMap.containsKey(charArray[index])) {
                characterMap.put(charArray[index], index);
            } else { // duplicate found!
                // if the hashmap already has the key
                // keep track of the max map size (max distinct elements in array)
                maxDistinctNums = Math.max(maxDistinctNums, characterMap.size());

                // given there is a duplicate element,
                // reposition the cursor index to the other repeating char
                // in the next iteration of the for loop the index will increment 1,
                // which will put the cursor at the next non-duplicate element
                // for example ABACD: cursor would move from the first A to B and loop continues
                index = characterMap.get(charArray[index]);     // get the index of the duplicate char and clear map (start over)
                characterMap.clear();
            }
        }

        return Math.max(maxDistinctNums, characterMap.size());
    }

    /**
     * Using array of chars to keep track of the letters
     * @param str
     * @return
     */
    private static int lengthOfLongestSubstring(String str) {
        if (str == null) return 0;
        boolean[] flag = new boolean[256];

        int result = 0;
        int start = 0;
        char[] array = str.toCharArray();

        for (int index = 0; index < array.length; index++) {
            char current = array[index];
            if (flag[current]) {
                result = Math.max(result, index - start);
                // the loop update the new start point and
                // reset flag array
                // for example, abccab, when it comes to 2nd c,
                // it update start from 0 to 3, reset flag for a, b
                for (int k = start; k < index; k++) {
                    if (array[k] == current) {
                        start = k + 1;
                        break;
                    }
                    flag[array[k]] = false;
                }
            } else {
                flag[current] = true;
            }
        }
        result = Math.max(array.length - start, result);

        return result;
    }
//
//    Let us talk about the linear time solution now. This solution uses extra space
//    to store the last indexes of already visited characters. The idea is to scan
//    the string from left to right, keep track of the maximum length Non-Repeating
//    Character Substring (NRCS) seen so far. Let the maximum length be maxLen.
//    When we traverse the string, we also keep track of length of the current NRCS
//    using index variable. For every new character, we look for it in already processed
//    part of the string (the hashset is used for this purpose).
//    If it is not present, then we increase the maxLen by 1. If present, then there are two cases:
//
//    a) The previous instance of character is not part of current NRCS
//    (The NRCS which is under process). In this case, we need to simply increase maxLen by 1.
//    b) If the previous instance is part of the current NRCS, then our current NRCS changes.
//    It becomes the substring staring from the next character of previous instance to currently
//    scanned character. We also need to compare index and maxLen, before changing
//    current NRCS (or changing curLen).
    private static int longestUniqueSubstr(String str) {
        int n = str.length();
        int curLen = 1;        // length of current substring
        int maxLen = 1;        // result
        int prevIndex;         // previous index
        int index;
        int visited[] = new int[256];

        /* Initialize the visited array as -1, -1 is
         used to indicate that character has not been
         visited yet. */
        for (index = 0; index < visited.length; index++) {
            visited[index] = -1;
        }

        /* Mark first character as visited by storing the
             index of first   character in visited array. */
        visited[str.charAt(0)] = 0;

        /* Start from the second character. First character is
           already processed (cur_len and max_len are initialized
         as 1, and visited[str[0]] is set */
        for (index = 1; index < n; index++) {
            prevIndex = visited[str.charAt(index)];

            /* If the current character is not present in
           the already processed substring or it is not
              part of the current NRCS, then do cur_len++ */
            if (prevIndex == -1 || index - curLen > prevIndex)
                curLen++;
            else {
                /* Also, when we are changing the NRCS, we
                   should also check whether length of the
                   previous NRCS was greater than max_len or
                   not.*/
                if (curLen > maxLen)
                    maxLen = curLen;
                curLen = index - prevIndex;
            }

            // update the index of current character 
            visited[str.charAt(index)] = index;
        }

        // Compare the length of last NRCS with max_len and
        // update max_len if needed
        if (curLen > maxLen)
            maxLen = curLen;

        return maxLen;
    }
    private static int lengthOfLongestSubStringSet(String str) {
        if (str == null || str.length() == 0) return 0;

        HashSet<Character> set = new HashSet<>();
        int maxDistinctNums = 1;
        int index = 0;
        for (int iterate = 0; iterate < str.length(); iterate++) {
            System.out.println("on char = " + str.charAt(iterate));
            char c = str.charAt(iterate);
            if (!set.contains(c)) {
                set.add(c);
                maxDistinctNums = Math.max(maxDistinctNums, set.size());
            } else {
                System.out.println("index = " + index + " iteration = " + iterate + " char = " + c);
                while (index < iterate) {
                    // found duplicate so break
                    if (str.charAt(index) == c) {
                        index++;
                        System.out.println("break and index = " + index);
                        break;
                    }
                    // remove the char from the set that is a duplicate and increment index
                    System.out.println("removing element at index = " + index + " " + str.charAt(index));
                    set.remove(str.charAt(index));
                    index++;
                }
            }
        }
        return maxDistinctNums;
    }
}
