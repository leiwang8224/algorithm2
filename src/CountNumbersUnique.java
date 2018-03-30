/**
 * Created by leiwang on 3/19/18.
 */

/**
 * count how many numbers have unique digits
 * For n = 2 return 91, excluding 11,22,33,44,55...
 */
public class CountNumbersUnique {

    public static void main() {
        generateNumbersUnique(2);
    }

    private static int generateNumbersUnique(int digits) {
        if (digits == 0) return 1;

        int res = 10;
        int uniqueDigits = 9;
        int availableNumber = 9;
        while (digits-- > 1 && availableNumber > 0) {
            uniqueDigits = uniqueDigits * availableNumber;
            res += uniqueDigits;
            availableNumber--;
        }
        return res;
    }
}
