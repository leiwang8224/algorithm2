package Recursion;

public class InsertPairStar {
    public static void main(String[] args) {
        System.out.println(insertPairStar("CCCC"));
    }

    private static String insertPairStar(String s) {
        if (s == null) {
            return null;
        } else if (s.length() == 1) {
            // base case
            return s;
        // recursive case for matching, if first char equal to second char
            // Chop off the first char and recurse on the rest of the string
        } else if (s.substring(0,1).equals(s.substring(1,2))) {
            return s.substring(0,1) + "*" + insertPairStar(s.substring(1,s.length()));
        }
        // recursive case for non-matching
        return s.substring(0,1) + insertPairStar(s.substring(1,s.length()));
    }
}
