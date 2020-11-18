package Arrays;

import java.util.ArrayList;
import java.util.List;

//LC-1018
//Given an array A of 0s and 1s, consider N_i: the i-th subarray from A[0] to A[i] interpreted as a binary number (from most-significant-bit to least-significant-bit.)
//
//        Return a list of booleans answer, where answer[i] is true if and only if N_i is divisible by 5.
//
//        Example 1:
//
//        Input: [0,1,1]
//        Output: [true,false,false]
//        Explanation:
//        The input numbers in binary are 0, 01, 011; which are 0, 1, and 3 in base-10.  Only the first number is divisible by 5, so answer[0] is true.
//
//        Example 2:
//
//        Input: [1,1,1]
//        Output: [false,false,false]
//
//        Example 3:
//
//        Input: [0,1,1,1,1,1]
//        Output: [true,false,false,false,true,false]
//
//        Example 4:
//
//        Input: [1,1,1,0,1]
//        Output: [false,false,false,false,false]

public class BinaryPreDivBy5 {
    public static void main(String[] args) {
        List<Boolean> result = new ArrayList<>();
        result = prefixesDvBy5(new int[]{1,1,0,0,0,1,0,0,1});
        List<Boolean> result2 = new ArrayList<>();
        result2 = prefixesDivBy5NoWork(new int[]{1,1,0,0,0,1,0,0,1});


        // 0, 01, 011, 0111, 01111, 011111
        for (Boolean i : result) {
            System.out.print(i + ",");
        }
        System.out.println();
        for (Boolean i : result2) {
            System.out.print(i + ",");
        }
        System.out.println();
    }

    private static String intToBiniaryString(int num) {
        StringBuilder str = new StringBuilder();
        while (num != 0) {
            int mod = num % 2;
            num /= 2;
            str.append(mod);
        }
        return str.toString();
    }
    public static List<Boolean> prefixesDvBy5(int[] arrayOf1And0) {
        List<Boolean> answer = new ArrayList<>();
        int value = 0;

        for (int num : arrayOf1And0) {
            // the shift basically OR with the bit from num
            // notice after shift the value is set back (shift in place)
            // left shift is equvalent to *2, OR with num extracts the bit
            value = (value << 1) | num; // OR with num or add num is the SAME THING
            System.out.println("value = " + (value) + " num = " + num + " bin for value = " + intToBiniaryString(value));
            // note that value is reset here by mod by 5 to prevent overflow (32bits+)
            value = value % 5;
            System.out.println("value = " + value + " after mod 5" + " binary value = " + intToBiniaryString(value));
            answer.add(value == 0);
        }

        return answer;
    }

    public static List<Boolean> prefixesDivBy5(int[] A) {
        List<Boolean> list = new ArrayList<>();
        int val = 0;

        for (int i = 0; i < A.length; i++) {
            val = val * 2 + A[i];
            System.out.println("val = " + val + " after adding " + A[i]);

            // note that value is reset here by mod by 5 so only the least significant digit is kept!!!!
            if ((val %= 5) == 0)
                list.add(true);
            else
                list.add(false);
        }
        return list;
    }

    public static List<Boolean> prefixesDivBy5DP(int[] A) {
        for (int i = 1; i < A.length; i++) {
            // estimate mod 5 of the number is being interpreted up to A[i]
            A[i] += A[i-1]%5 * 2;
            A[i] %= 5;
        }
        List<Boolean> res = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            res.add(A[i] == 0 ? true : false);
        }
        return res;
    }

    // does not work because it's least to most significant
    // question is asking for most to least significant
    public static List<Boolean> prefixesDivBy5NoWork(int[] A) {
        List<Boolean> result = new ArrayList<>();
        double sum = 0;
        for (int index = 0; index < A.length; index++) {
            sum = sum + Math.pow(2, index) * A[index];
            if (sum % 5 == 0) {
                result.add(true);
            } else {
                result.add(false);
            }
        }
        return result;
    }

    public static List<Boolean> prefixesDivBy5_(int[] A) {
        List<Boolean> result = new ArrayList<>();
        int num = A[0];
        boolean good = num % 5 == 0 ? true:false;
        result.add(good);

        for (int index = 1; index < A.length; index++) {
            if (A[index] == 1) num = num * 2 + 1;
            else num = num * 2;
            if (num %5 == 0) result.add(true);
            else result.add(false);
            // prevent overflow
            num = num % 5;
        }
        return result;
    }
}


