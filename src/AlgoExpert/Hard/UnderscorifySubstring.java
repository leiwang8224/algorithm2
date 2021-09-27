package AlgoExpert.Hard;
import java.util.*;
public class UnderscorifySubstring {
    // N / M * 2M or average O(n + m) time | O(n) space
    // where N is length of string and M is length of substring
    // we call the indexOf method N/M times and 2M is the length
    // of chars it takes to find the substring
    // note that indexOf() takes O(n + m)
    // example abcabcabctest find substring test O(n + m)
    // example aaaaatestaaaaatest find substring test O(2(n + m))
    // example testtesttesttesttest find substring test O(N / M * 2M) = O(N)
    // indexOf call takes O(n + m)
    private static String underscore(String str, String substring) {
        List<Integer[]> locations = collapse(getLocations(str,substring));
        return underScorify(str, locations);
    }

    /**
     * Get locations of the substrings in the string
     * Iterate through the string and once a substring location is found, set
     * startIdx to the next substring start index + 1
     */
    private static List<Integer[]> getLocations(String str, String substring) {
        List<Integer[]> locations = new ArrayList<>();
        int startIdx = 0;
        while (startIdx < str.length()) {
            int idxMatchSubstring = str.indexOf(substring, startIdx);
            if (idxMatchSubstring != -1) {
                // note the end index goes one more out
                locations.add(new Integer[] {idxMatchSubstring, idxMatchSubstring + substring.length()});
                startIdx = idxMatchSubstring + 1;
            } else {
                // no more left so stop
                break;
            }
        }
        return locations;
    }

    /**
     * Create new arraylist to store the collapsed locations
     */
    private static List<Integer[]> collapse(List<Integer[]> locations) {
        if (locations.size() == 0) {
            return locations;
        }
        List<Integer[]> collapsedLocations = new ArrayList<>();
        collapsedLocations.add(locations.get(0));
        Integer[] prevLocation = collapsedLocations.get(0);

        for (int locationsIdx = 1; locationsIdx < locations.size(); locationsIdx++) {
            Integer[] curLocation = locations.get(locationsIdx);
            // if current endIndex <= previous endIndex
            // only check overlaps (prev end >= cur start)
            if (curLocation[0] <= prevLocation[1]) {
                // update prev end index = cur end index
                // in the next iteration the cur array would be overwritten
                // only need to update the previous end index!
                prevLocation[1] = curLocation[1];
            } else {
                // just add the current when no overlap
                collapsedLocations.add(curLocation);
                prevLocation = curLocation;
            }
        }
        return collapsedLocations;
    }

    /**
     * Use startOrEndIdxLocation to toggle between start and end index in locations
     * Use inBetweenUnderscores to indicate if we are finished with the current string, if so inc locationsIdx
     * Remember to pick up the left over locations in the end, or append the leftover string
     */
    private static String underScorify(String str, List<Integer[]> locations) {
        // traverse str and locations
        int locationsIdx = 0;
        int stringIdx = 0;
        boolean inBetweenUnderscores = false;

        // use String.join to join arraylist of strings, COULD also use StringBuilder here?
        List<String> finalChars = new ArrayList<>();
        int startOrEndIdxLocation = 0;
        while (stringIdx < str.length() && locationsIdx < locations.size()) {
            // if we are at the beginning of the insertion
            if (stringIdx == locations.get(locationsIdx)[startOrEndIdxLocation]) { //[[0,4],[8,12],[14,15]]
                // insert underscore
                finalChars.add("_");
                // we are in between underscores now
                inBetweenUnderscores = !inBetweenUnderscores;
                if (!inBetweenUnderscores) { // only increment locationIdx at beginning of word
                                  // insertion at the end of the word does not need this
                    locationsIdx ++;
                }
                // toggle locationIdx (start or end)
                startOrEndIdxLocation = startOrEndIdxLocation == 1 ? 0 : 1;
            }
            // add chars to finalChars
            finalChars.add(String.valueOf(str.charAt(stringIdx)));
            stringIdx += 1;
        }

        // is there is location pairs left in the locations array or string left, append to the end
        if (locationsIdx < locations.size()) {
            finalChars.add("_");
        } else if (stringIdx < str.length()) {
            finalChars.add(str.substring(stringIdx));
        }
        return String.join("", finalChars);
    }
}
