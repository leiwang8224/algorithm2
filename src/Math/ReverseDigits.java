package Math;

/**
 * Created by leiwang on 4/5/18.
 */
public class ReverseDigits {
    public static void main(String[] args) {
        int num = 123;
        System.out.println("reversed number = " + reverseDigits(num));
    }

    private static int reverseDigits(int num) {
        int result = 0;

        while (num != 0) {
            int tail = num % 10;
            int newResult = result * 10 + tail;
            // overflow detected
            if ((newResult - tail) / 10 != result)
                return 0;
            result = newResult;
            num = num / 10;
        }
        return result;
    }
}
