package Arrays;

import javax.print.DocFlavor;

//LC-14
public class LongestCommonPrefix {
    public static void main(String[] args) {
        String[] array = new String[]{"flower", "flow", "flight"};
        System.out.println(longestCommonPrefix(array));
        System.out.println(longestCommonPrefix2(array));
        System.out.println(longestCommonPrefix3(array));
        System.out.println(longestCommonPrefixBinarySearch(array));
    }

    // time O(s)
    // space O(1)
    // shorten the first element in the array until there is a match
    // in string prefix, if no match keep shrinking the string from the
    // right till the string becomes empty
    private static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        String strToFind = strs[0];
        // iterate through the array of strings to find index of first string
        for (int arrayIndex = 1; arrayIndex < strs.length; arrayIndex++) {
            // keep searching till we find the string
            // in the case we reach empty string we simply return empty string
            // indexOf should return 0 if strToFind is in the prefix
            while (strs[arrayIndex].indexOf(strToFind) != 0) {
                // NOTE the substring Java API includes 0th
                // beginning index and ends with endIndex-1 (strToFind.length() - 2)
                strToFind = strToFind.substring(0, strToFind.length() - 1);
                // if there are no prefix matches then return empty string
                if (strToFind.isEmpty()) return "";
            }
        }
        return strToFind;
    }

    // time O(s)
    // space O(1)
    private static String longestCommonPrefix2(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        for (int charIndex = 0; charIndex < strs[0].length() ; charIndex++){
            // for each char in the first string
            char c = strs[0].charAt(charIndex);
            // for each string find when the first mismatch happens
            for (int arrayIndex = 1; arrayIndex < strs.length; arrayIndex ++) {
                // if we reached the end of string OR mismatch occurred
                if (charIndex == strs[arrayIndex].length() ||
                        strs[arrayIndex].charAt(charIndex) != c)
                    // return substring 0 to charIndex
                    return strs[0].substring(0, charIndex);
            }
        }
        return strs[0];
    }

    // time O(s)
    // space O(m*logn)
//    To accomplish this we compare one by one the characters of lcpLeft
//    and lcpRight till there is no character match. The found common
//    prefix of lcpLeft and lcpRight is the solution of the LCP(Si…Sj).
    public static String longestCommonPrefix3(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        return longestCommonPrefix(strs, 0 , strs.length - 1);
    }

    // divide and conquer
    private static String longestCommonPrefix(String[] strs, int left, int right) {
        if (left == right) {
            return strs[left];
        } else {
            int mid = (left + right)/2;
            String lcpLeft =   longestCommonPrefix(strs, left , mid);
            String lcpRight =  longestCommonPrefix(strs, mid + 1,right);
            return commonPrefix(lcpLeft, lcpRight);
        }
    }

    static String commonPrefix(String left,String right) {
        int minLeftRightStr = Math.min(left.length(), right.length());
        // increment indexCharMatches till they don't equal, then return the string
        for (int indexCharMatches = 0; indexCharMatches < minLeftRightStr; indexCharMatches++) {
            if ( left.charAt(indexCharMatches) != right.charAt(indexCharMatches) )
                return left.substring(0, indexCharMatches);
        }
        // else everything matches so return everything
        return left.substring(0, minLeftRightStr);
    }

    // time O(S*log(m))
    // space O(1)
    public static String longestCommonPrefixBinarySearch(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        int minLen = Integer.MAX_VALUE;
        for (String str : strs)
            minLen = Math.min(minLen, str.length());
        int low = 1;
        int high = minLen;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (isCommonPrefix(strs, middle))
                low = middle + 1;
            else
                high = middle - 1;
        }
        return strs[0].substring(0, (low + high) / 2);
    }

    private static boolean isCommonPrefix(String[] strs, int len){
        String firstStrSubstring = strs[0].substring(0,len);
        for (int i = 1; i < strs.length; i++)
            if (!strs[i].startsWith(firstStrSubstring))
                return false;
        return true;
    }
}
