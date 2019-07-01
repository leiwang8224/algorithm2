package BitManip;

import java.util.Stack;

public class SwapOddEvenBits {
    public static void main(String[] args) {
        System.out.println(swapOddEvenBits(42));
        System.out.println(swapOddEvenBitsIntuitive(42));
        System.out.println(swapOddEvenBits2(42));
    }

    private static int swapOddEvenBits(int x) {


//        1. First mask (clear) all the odd bits with 10101010 (0xAA) and shift them to the right by 1.
//
//        2. Mask all the even bits with 01010101 (0x55) and shift them to the left by 1.
//
//        3. Bitswise OR | the outputs of the first and second steps to reach the final result.
        System.out.println(Integer.toBinaryString(x));
        // mask all odd bits
        System.out.println(Integer.toBinaryString((x & 0xAAAAAAAA) >> 1)); // even
        // mask all even bits
        System.out.println(Integer.toBinaryString((x & 0x55555555) << 1)); // odd
        // print final results
        System.out.println(Integer.toBinaryString(((x & 0xAAAAAAAA) >> 1 | ((x & 0x55555555) << 1))));
        return ((x & 0xAAAAAAAA) >> 1 | ((x & 0x55555555) << 1));
    }

    private static String printNumBinary(int num) {
        StringBuilder str = new StringBuilder();
        Stack<Integer> stack = new Stack<>();

        while (num / 2 > 0) {
            stack.push(num % 2);
            num = num / 2;
        }

        while (!stack.isEmpty()) {
            str.append(stack.pop());
        }

        return str.toString();
    }

    private static int swapOddEvenBitsIntuitive(int x) {
        int mask=0b01010101010101010101010101010101;
        int odds=x & mask;
        mask=0b10101010101010101010101010101010;
        int evens= x & mask;
        odds<<=1;
        evens>>=1;
        return (odds | evens);
    }

    private static int swapOddEvenBits2(int x) {
        int bit = 0;

        while (bit < 32) {
            int even = (x >> bit) % 2;

            int odd = (x >> bit + 1) % 2;

            if (even != odd) {
                x = x ^ (int) Math.pow(2, bit);
                x = x ^ (int) Math.pow(2, bit + 1);
            }

            bit += 2;
        }

        return x;
    }
}
