package Math;

public class IsHappyNumber {
    public static void main(String[] args) {
        System.out.println("45 is " + isHappyNumber(45));
        System.out.println("19 is " + isHappyNumber(19));
    }

    private static boolean isHappyNumber(int n) {
        int[] digitArray = getInputDigits(n);
        int sum;

        do {
            sum = 0;
            for (int i = 0; i < digitArray.length; i++) {
                sum = sum + digitArray[i] * digitArray[i];
            }
            digitArray = getInputDigits(sum);
        } while (sum > 9);

        if (sum == 1)
            return true;

        return false;
    }

    private static int[] getInputDigits(int n) {
        String s = String.valueOf(n);
        int[] digits = new int[s.length()];
        int i = 0;

        while (n > 0) {
            int m = n % 10;
            digits[i++] =m;
            n = n/10;
        }
        return digits;
    }
}
