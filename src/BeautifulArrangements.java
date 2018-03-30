import java.util.Arrays;

/**
 * Created by leiwang on 3/11/18.
 */
public class BeautifulArrangements {
    static int count = 0;

    public static void main(String[] args) {
        System.out.println("result = " + countArrangement(3));
    }


    public static int countArrangement(int N) {
        if (N == 0) return 0;
        helper(N, 1, new int[N + 1]);
        return count;
    }

    private static void helper(int N, int pos, int[] used) {
        System.out.println("Entering helper with used = " + Arrays.toString(used) + " N = " + N + " pos " + pos);
        if (pos > N) {
            count++;
            return;
        }

        for (int i = 1; i <= N; i++) {
            // value at index i is divisible by index i and index i is divisible by value at index i
            if (used[i] == 0 && (i % pos == 0 || pos % i == 0)) {
                used[i] = 1;
                System.out.println("before calling helper method used = " + Arrays.toString(used));
                helper(N, pos + 1, used);
                used[i] = 0;
                System.out.println("after calling helper method used = " + Arrays.toString(used));
            }
        }
    }
}