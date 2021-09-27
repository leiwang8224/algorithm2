package AlgoExpert.VeryHard;
import java.util.*;
public class SmallestSubstringContaining {
    // O(b + s) time | O(b + s) space where b is the length of the big
    // input string and s is the length of the small input string
    public String smallestSubstringContaining(String bigString, String smallString) {
        Map<Character, Integer> targetCharCounts = getCharCountsForSmallString(smallString);
        List<Integer> substringBounds = getSubstringBoundsBySlidingWindow(bigString, targetCharCounts);
        return getStringFromBounds(bigString, substringBounds);
    }

    private String getStringFromBounds(String bigString, List<Integer> bounds) {
        int start = bounds.get(0);
        int end = bounds.get(1);
        if (end == Integer.MAX_VALUE) return "";
        return bigString.substring(start, end + 1);
    }

    // sliding window
    private List<Integer> getSubstringBoundsBySlidingWindow(String bigString, Map<Character, Integer> smallStringCharCounts) {
        List<Integer> substringBounds = new ArrayList<>(Arrays.asList(0, Integer.MAX_VALUE));
        Map<Character, Integer> charsFoundWithCounts = new HashMap<>();
        int numUniqueCharsForSmallString = smallStringCharCounts.size();
        int numUniqueCharsFound = 0;
        int leftIdx = 0;
        int rightIdx = 0;

        // Move the rightIdx to the right in the string until you have counted
        // all of the target chars enough times
        while (rightIdx < bigString.length()) {
            char rightChar = bigString.charAt(rightIdx);
            // if the rightChar is not part of the small string, keep incrementing rightIdx
            if (!smallStringCharCounts.containsKey(rightChar)) {
                rightIdx++;
                continue;
            }
            // if the rightChar is part of the small string, increment the charCount Map
            increaseCharCount(rightChar, charsFoundWithCounts);

            // if the count of the chars is equal to the count of chars in the small string then inc numUniqueCharsFound
            if (charsFoundWithCounts.get(rightChar).equals(smallStringCharCounts.get(rightChar))) {
                // only update this when all instances of the respective char is found in the substring
                numUniqueCharsFound++;
            }

            // start moving the leftIdx once we find all the unique chars and respective counts
            // Move the leftIdx to the right in the string until you no longer
            // have enough of the target chars in between the leftIdx and rightIdx
            // update the substringBounds accordingly
            while (numUniqueCharsFound == numUniqueCharsForSmallString && leftIdx <= rightIdx) {
                substringBounds =
                        getShortestBounds(leftIdx, rightIdx, substringBounds.get(0), substringBounds.get(1));
                char leftChar = bigString.charAt(leftIdx);

                // if the leftChar is not part of the small string, keep incrementing leftIdx
                if (!smallStringCharCounts.containsKey(leftChar)) {
                    leftIdx++;
                    continue;
                }

                // if the count of the chars is equal to the count of chars in the small string then dec numUniqueCharsFound
                if (charsFoundWithCounts.get(leftChar).equals(smallStringCharCounts.get(leftChar))) {
                    // only update this when all instances of the respective char is found in the substring
                    numUniqueCharsFound--;
                }

                // if the leftChar is part of the small string, decrement the charCount Map
                decreaseCharCount(leftChar, charsFoundWithCounts);

                leftIdx++;
            }
            rightIdx++;
        }
        return substringBounds;
    }

    private void decreaseCharCount(char c, Map<Character, Integer> charCounts) {
        charCounts.put(c, charCounts.get(c) - 1);
    }

    private void increaseCharCount(char c, Map<Character, Integer> charCounts) {
        if (!charCounts.containsKey(c)) {
            charCounts.put(c, 1);
        } else {
            charCounts.put(c, charCounts.get(c) + 1);
        }
    }

    private List<Integer> getShortestBounds(int idx1, int idx2, Integer idx3, Integer idx4) {
        return idx2 - idx1 < idx4 - idx3 ?
                new ArrayList<>(Arrays.asList(idx1, idx2)) :
                new ArrayList<>(Arrays.asList(idx3, idx4));
    }


    private Map<Character, Integer> getCharCountsForSmallString(String smallString) {
        Map<Character, Integer> charCounts = new HashMap<>();
        for (int i = 0; i < smallString.length(); i++) {
            increaseCharCount(smallString.charAt(i), charCounts);
        }
        return charCounts;
     }
}
