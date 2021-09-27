package AlgoExpert.Easy;

public class CaesarCipherEncryptor {
    public static void main(String[] args) {

    }

    // O(n) time | O(n) space
    private static String encrypt(String str, int key) {
        char[] newLetters = new char[str.length()];
        int newKey = key % 26;
        for (int i = 0; i < str.length(); i++)
            newLetters[i] = getNewLetter(str.charAt(i), newKey);
        return new String(newLetters);
    }

    private static char getNewLetter(char letter, int key) {
        int newLetterCode = letter + key;
        // unicode for z = 122, a = 97
        return newLetterCode <= 'z' ?
                (char) newLetterCode:
                (char) ('a' - 1 + newLetterCode % 'z');
    }

    // O(n) time | O(n) space
    private static String encrypt2(String str, int key) {
        char[] newLetters = new char[str.length()];
        int newKey = key % 26;
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < str.length(); i++) {
            newLetters[i] = getNewLetter2(str.charAt(i), newKey, alphabet);
        }

        return new String(newLetters);
    }

    private static char getNewLetter2(char letter, int key, String alphabet) {
        int newLetterCode = alphabet.indexOf(letter) + key;
        return alphabet.charAt(newLetterCode % 26);
    }

    private static String caesarCypherEncryptor(String str, int key) {
        int shift;
        if (key / 26 == 0) {
            shift = key;
        } else {
            shift = key % 26;
        }

        StringBuilder result = new StringBuilder();
        for (int index= 0;index < str.length(); index++) {
            if (str.charAt(index) + shift > 'z') {
                result.append(((char)('a' - 1 + (str.charAt(index) + shift) % 'z' )));
            } else {
                result.append(((char)(str.charAt(index) + shift)));
            }
        }
        // Write your code here.
        return result.toString();
    }
}
