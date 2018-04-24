package Arrays;

//Given a non-empty array of digits representing a non-negative integer, plus one to the integer.
//
//The digits are stored such that the most significant digit is at the head of the list,
//and each element in the array contain a single digit.
//
//You may assume the integer does not contain any leading zero, except the number 0 itself.
public class PlusOne {
    public static void main(String[] args) {
        int[] digits = new int[]{1,2,3,4,5};
        System.out.println(java.util.Arrays.toString(plusOne(digits)));
        System.out.println(java.util.Arrays.toString(plusOne2(digits)));

    }

    private static int[] plusOne(int[] digits) {
        int n = digits.length;
        // looping start from the right and work towards the left
        for(int i=n-1; i>=0; i--) {
            // if less than 9 just add one and return
            if(digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            // else it's equal to 9 set to 0
            digits[i] = 0;
        }

        // set the first digit to be 1 and rest to 0
        int[] newNumber = new int [n+1];
        newNumber[0] = 1;

        return newNumber;
    }

    private static int[] plusOne2(int[] digits) {
        for (int i = digits.length - 1; i >=0; i--) {
            if (digits[i] != 9) {
                digits[i]++;
                break;
            } else {
                digits[i] = 0;
            }
        }
        if (digits[0] == 0) {
            int[] res = new int[digits.length+1];
            res[0] = 1;
            return res;
        }
        return digits;
    }
}
