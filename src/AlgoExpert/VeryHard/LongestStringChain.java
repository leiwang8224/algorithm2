package AlgoExpert.VeryHard;

import java.util.*;

public class LongestStringChain {
    class StringChain {
        String nextString;
        Integer maxChainLength;

        StringChain(String nextString, Integer maxChainLength) {
            this.nextString = nextString;
            this.maxChainLength = maxChainLength;
        }
    }

    // O(n * m^2 + nlog(n)) time | O(nm) space where n is the number of strings
    // and m is the length of the longest string
    // ae, abc, abd, ade, abde, abcde, 1abde, abcdef
    // ""  ""   ""   ae   ade    abde   abde   abcde
    // 1    1    1    2    3      4      4       5
    List<String> longestStringChain(List<String> strings) {
        // for every string, imagine the longest string chain that starts with it.
        // setup every string to point to the next string in its respective longest
        // string chain. Also keep track of the lengths of these longest string chains
        Map<String, StringChain> stringChains = new HashMap<>(); // O(nm)
        for (String string : strings) { // O(n)
            // init stringChains with maxChainLength = 1
            // maxChainLength is the max chain len starting with the current string
            stringChains.put(string, new StringChain("", 1));
        }

        // sort the strings based on their length so that whenever we visit
        // a string (as we iterate through them from left to right), we can
        // already have computed the longest string chains of any smaller strings
        List<String> sortedStrings = new ArrayList<>(strings);
        sortedStrings.sort((a, b) -> a.length() - b.length()); // nlog(n)

        for (String string: sortedStrings) { // O(m^2)
            findLongestStringChainByRemovingLetters(string, stringChains);
        }
        return buildLongestStringChain(strings, stringChains);
    }

    List<String> buildLongestStringChain(List<String> strings, Map<String, StringChain> stringChains) {
        // find the string that starts the longest string chain
        int maxChainLen = 0;
        String chainStartingString = "";
        // find max chain length and the starting string by iterating
        for (String string : strings) {
            if (stringChains.get(string).maxChainLength > maxChainLen) {
                maxChainLen = stringChains.get(string).maxChainLength;
                chainStartingString = string;
            }
        }

        // starting at the string found above, build the longest string chain
        List<String> ourLongestStringChain = new ArrayList<>();
        String curString = chainStartingString;
        while (curString != "") { // terminate by empty string
            ourLongestStringChain.add(curString);
            curString = stringChains.get(curString).nextString;
        }

        return ourLongestStringChain.size() == 1 ? new ArrayList<>() : ourLongestStringChain;
    }

    private void findLongestStringChainByRemovingLetters(String string,
                                                         Map<String, StringChain> stringChains) {
        // try removing every letter of the current string to see if
        // the remaining strings form a string chain.
        for (int i = 0; i < string.length(); i++) {
            String smallerString = removeCharAtIndex(string, i); // smaller string is ith letter removed
            if (!stringChains.containsKey(smallerString)) continue;
            tryUpdateLongestStringChain(string, smallerString, stringChains);
        }
    }

    // if smaller string chain length > cur string chain length then
    // update string chain with the smaller string
    private void tryUpdateLongestStringChain(String curString,
                                             String smallerString,
                                             Map<String, StringChain> stringChains) {
        int smallerStringChainLength = stringChains.get(smallerString).maxChainLength;
        int currentStringChainLength = stringChains.get(curString).maxChainLength; // calculated before
        // update the string chain of the current string only if the smaller string
        // leads to a longer string chain
        if (smallerStringChainLength + 1 > currentStringChainLength) {
            stringChains.get(curString).maxChainLength = smallerStringChainLength + 1;
            stringChains.get(curString).nextString = smallerString; // point next string to smaller string
        }
    }

    private String removeCharAtIndex(String string, int index) {
        return string.substring(0, index) + string.substring(index+1);
    }
}
