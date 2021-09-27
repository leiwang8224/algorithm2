package AlgoExpert.Hard;

public class InterweavingStrings {
    // O(2^(n + m)) time | O(n + m) space where n is the length
    // of the first string and m is the length of second string
    public static boolean interweaving(String one, String two, String three) {
        if (three.length() != one.length() + two.length()) return false;
        return areInterwoven(one, two, three, 0, 0);
    }

    /**
     * - Check if one and three matches, return from recursion only when true (we want to stop immediately)
     * - Check if two and three matches, return from recursion regardless true or false
     */
    private static boolean areInterwoven(String one,
                                         String two,
                                         String three,
                                         int strOneIdx,
                                         int strTwoIdx) {
        int strThreeIdx = strOneIdx + strTwoIdx;
        // reached the end of string and did not find un-matched char
        if (strThreeIdx == three.length()) return true;

        if (strOneIdx < one.length() &&
                one.charAt(strOneIdx) == three.charAt(strThreeIdx)) {
            // need this if statement because we still want to explore the strTwoIdx below,
            // don't return yet, still need to explore string two to string three comparison
            if (areInterwoven(one, two, three, strOneIdx + 1, strTwoIdx))
                return true; // only return when true, else keep recurse
        }

        if (strTwoIdx < two.length() &&
                two.charAt(strTwoIdx) == three.charAt(strThreeIdx)) {
            return areInterwoven(one, two, three, strOneIdx, strTwoIdx + 1); // return regardless or true or false
        }

        // at this point ptr1 != ptr3 and ptr2 != ptr3 so return false
        return false;
    }

    // O(nm) time | O(nm) space where n is length of the first string
    // m is the length of the second string
    public static boolean interweavingMemo(String one,
                                           String two,
                                           String three) {
        if (three.length() != one.length() + two.length()) return false;

        // need memo size +1 since we will be iterating to the last index n
        Boolean[][] memo = new Boolean[one.length() + 1][two.length() + 1];
        return areInterwoven(one, two, three, 0, 0, memo);
    }

    private static boolean areInterwoven(String one,
                                         String two,
                                         String three,
                                         int idxOne,
                                         int idxTwo,
                                         Boolean[][] cache) {
        // if already calculated just return
        if (cache[idxOne][idxTwo] != null) return cache[idxOne][idxTwo];

        // by the time idxThree == three.length() we are finished return true
        int idxThree = idxOne + idxTwo;
        if (idxThree == three.length()) return true;

        // verify idxOne and idxTwo are within limits before checking against idxThree values
        if (idxOne < one.length() && one.charAt(idxOne) == three.charAt(idxThree)) {
            cache[idxOne][idxTwo] = areInterwoven(one, two, three, idxOne + 1, idxTwo, cache);
            // do not simply return since we are not finished recursion yet
            // only return if it's true, if false check idxTwo == idxThree below
            if (cache[idxOne][idxTwo]) return true;
        }

        if (idxTwo < two.length() && two.charAt(idxTwo) == three.charAt(idxThree)) {
            cache[idxOne][idxTwo] = areInterwoven(one, two, three, idxOne, idxTwo + 1, cache);
            // have to return now regardless of true or false
            return cache[idxOne][idxTwo];
        }

        // tried both one and two, oneIdx != threeIdx && twoIdx != threeIdx so must be false
        cache[idxOne][idxTwo] = false;
        return false;
    }
}
