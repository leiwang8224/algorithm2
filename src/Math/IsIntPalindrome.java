package Math;
//          Write a method that checks if a given integer is a palindrome - without allocating additional heap space Examples:
//                  -1 ==> false
//                  0 ==> true
//                  1221 ==> true
//                  123 ==> false
public class IsIntPalindrome {
    public static void main(String[] args) {

    }

    private static boolean isIntPalindrome(int x) {
        int num = x;

        // reversing number
        int reverse = 0;
        while (num > 0) {
            // build up the reverse, starting from the most significant bit?
            reverse = reverse * 10 + num % 10; //important step!!!

            num = num / 10;
        }

        return (reverse == x);
    }
}
