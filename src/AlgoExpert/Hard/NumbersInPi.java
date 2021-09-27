package AlgoExpert.Hard;

import java.util.*;

public class NumbersInPi {
    // O(n^3 + m) time | O(n + m) space where n = number of digits
    // in Pi and m = number of favorite numbers
    private static int numbersInPi(String pi, String[] numbers) {
        Set<String> favoriteNumbers = new HashSet<>();
        for (String number : numbers) {
            favoriteNumbers.add(number);
        }
        // for each idx what is corresponding minimum spaces
        // store in cache so we don't have to recalculate
        Map<Integer, Integer> cacheIdxToMinSpaces = new HashMap<>(); // <idx, minSpace>
        int minSpaces = getMinSpaces(pi, favoriteNumbers, cacheIdxToMinSpaces, 0);
        return minSpaces == Integer.MAX_VALUE ? -1 : minSpaces;
    }

    // 314156
    // 3, 31, 314, 3145, 31456
    // 1, 14, 141, 1415, 14156
    // 4, 41, 415, 4156,
    // 1, 15, 156,
    // 5, 56,
    // 6
    /**
     * For each pi index form prefix [index:index+1], [index:index+2] ...
     * if we find the prefix in the hash set then recursively call on the rest of the pi string
     */
    private static int getMinSpaces(String pi,
                                    Set<String> numbersToLookFor,
                                    Map<Integer, Integer> cacheIdxToMinSpaces,
                                    int piStartIdxForSubstring) {
        if(piStartIdxForSubstring == pi.length()) return -1; // return -1 to cancel out the last +1
        // optimization: cache the value so when we have solution just return it
        if (cacheIdxToMinSpaces.containsKey(piStartIdxForSubstring))
            return cacheIdxToMinSpaces.get(piStartIdxForSubstring);
        int minSpacesSoFarForCurIdx = Integer.MAX_VALUE;

        // iterate from the current index
        for (int piEndIdxSubstring = piStartIdxForSubstring;
             piEndIdxSubstring < pi.length();
             piEndIdxSubstring++) {
            // slice substring
            String prefixOfPi = pi.substring(piStartIdxForSubstring, piEndIdxSubstring + 1); // third O(n)
            // if set contains the prefix from idx to i,
            // recurse on the rest of the string starting from i + 1 to end
            if (numbersToLookFor.contains(prefixOfPi)) {
                // calculate min spaces for endIdx + 1 (suffix)
                // recurse on the next section of pi(suffix) and try to find favorite number
                int minSpacesInSuffix = getMinSpaces(pi,
                                                    numbersToLookFor,
                                                    cacheIdxToMinSpaces,
                                                piEndIdxSubstring + 1);// inc index and iterate with for loop
                                                                    // end of sliced substring + 1
                // handle int overflow, take the min(globalMin, curMinFromRecursion)
                if (minSpacesInSuffix == Integer.MAX_VALUE) {
                    minSpacesSoFarForCurIdx = Math.min(minSpacesSoFarForCurIdx, minSpacesInSuffix);
                } else { // can't just add 1 to minSpacesInSuffix if it's MAX_VALUE, so have to check before adding
                    minSpacesSoFarForCurIdx = Math.min(minSpacesSoFarForCurIdx, minSpacesInSuffix + 1);
                    // minSpacesInSuffix+1 to add one space, base case to cancel this +1
                }
            }
        }
        cacheIdxToMinSpaces.put(piStartIdxForSubstring, minSpacesSoFarForCurIdx);
        return cacheIdxToMinSpaces.get(piStartIdxForSubstring);
    }

    // O(n ^3 + m) time | O(n + m) space
    private static int numbersInPi2(String pi, String[] numbers) {
        Set<String> numbersToLookFor = new HashSet<>();
        for (String number : numbers) {
            numbersToLookFor.add(number);
        }
        Map<Integer, Integer> cacheIdxToMinSpaces = new HashMap<>();
        // 2, 92, 592, 1592, 41592, 141592, 3141592
        // bottom up approach to start from the end and iterate backwards
        for (int piIdx = pi.length() - 1; piIdx >= 0; piIdx--) {
            getMinSpaces2(pi, numbersToLookFor, cacheIdxToMinSpaces, piIdx);
        }
        return cacheIdxToMinSpaces.get(0) == Integer.MAX_VALUE ? -1 : cacheIdxToMinSpaces.get(0);
    }

    private static int getMinSpaces2(String pi,
                                      Set<String> numbersTable,
                                      Map<Integer, Integer> cacheIdxToMinSpaces,
                                      int piIdx) {
        if (piIdx == pi.length()) return -1;
        if (cacheIdxToMinSpaces.containsKey(piIdx)) return cacheIdxToMinSpaces.get(piIdx);
        int minSpaces = Integer.MAX_VALUE;
        for (int piEndIdx = piIdx; piEndIdx < pi.length(); piEndIdx++) {
            String prefix = pi.substring(piIdx, piEndIdx + 1);
            if (numbersTable.contains(prefix)) {
                int minSpacesInSuffx = getMinSpaces2(pi, numbersTable, cacheIdxToMinSpaces, piEndIdx + 1);

                // handle int overflow
                if (minSpacesInSuffx == Integer.MAX_VALUE) {
                    minSpaces = Math.min(minSpaces, minSpacesInSuffx);
                } else {
                    minSpaces = Math.min(minSpaces, minSpacesInSuffx + 1);
                }
            }
        }
        cacheIdxToMinSpaces.put(piIdx, minSpaces);
        return cacheIdxToMinSpaces.get(piIdx);
    }
}
