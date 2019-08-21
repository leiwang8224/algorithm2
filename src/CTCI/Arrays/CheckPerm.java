package CTCI.Arrays;

// Given two strings, write a method to decide if one is a permutation of the other
public class CheckPerm {
    public static void main(String[] args) {
        System.out.println(checkIfPerm("abc","cba"));

    }

    private static boolean checkIfPerm(String str1, String str2) {
        int[] charMap = new int[26];

        for (Character ch : str1.toCharArray()) {
            charMap[ch - 'a']++;
        }

        for (Character ch : str2.toCharArray()) {
            charMap[ch - 'a']--;
        }

        for (Integer num : charMap) {
            if (num < 0) return false;
        }

        return true;
    }
}
