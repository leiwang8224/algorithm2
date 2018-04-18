package Arrays;

/**
 * Created by leiwang on 4/17/18.
 */

//Given an array of integers A and let n to be its length.
//
//Assume Bk to be an array obtained by rotating the array A k positions
//clock-wise, we define a "rotation function" F on A as follow:
//
//F(k) = 0 * Bk[0] + 1 * Bk[1] + ... + (n-1) * Bk[n-1].
//
//Calculate the maximum value of F(0), F(1), ..., F(n-1).

//A = [4, 3, 2, 6]
//
//F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
//F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
//F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
//F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26
//
//        So the maximum value of F(0), F(1), F(2), F(3) is F(3) = 26.
public class RotateFunction {
    public static void main(String[] args) {

    }

//    F(k) = 0 * Bk[0] + 1 * Bk[1] + ... + (n-1) * Bk[n-1]
//    F(k-1) = 0 * Bk-1[0] + 1 * Bk-1[1] + ... + (n-1) * Bk-1[n-1]
//            = 0 * Bk[1] + 1 * Bk[2] + ... + (n-2) * Bk[n-1] + (n-1) * Bk[0]
//    Then,
//
//    F(k) - F(k-1) = Bk[1] + Bk[2] + ... + Bk[n-1] + (1-n)Bk[0]
//            = (Bk[0] + ... + Bk[n-1]) - nBk[0]
//            = sum - nBk[0]
//    Thus,
//
//    F(k) = F(k-1) + sum - nBk[0]
//    What is Bk[0]?
//
//    k = 0; B[0] = A[0];
//    k = 1; B[0] = A[len-1];
//    k = 2; B[0] = A[len-2];
//    ...
    private int maxRotateFunction(int[] A) {
        int allSum = 0;
        int len = A.length;
        int F = 0;
        for (int i = 0; i < len; i++) {
            F += i * A[i];
            allSum += A[i];
        }
        int max = F;
        for (int i = len - 1; i >= 1; i--) {
            F = F + allSum - len * A[i];
            max = Math.max(F, max);
        }
        return max;
    }
//
//    Consider we have 5 coins A,B,C,D,E
//
//    According to the problem statement
//                             F(0) = (0A) + (1B) + (2C) + (3D) + (4E)
//    F(1) = (4A) + (0B) + (1C) + (2D) + (3E)
//    F(2) = (3A) + (4B) + (0C) + (1D) + (2*E)
//
//    This problem at a glance seem like a difficult problem.
//    I am not very strong in mathematics, so this is how I visualize this problem
//
//    We can construct F(1) from F(0) by two step:
//    Step 1. taking away one count of each coin from F(0),
//    this is done by subtracting “sum” from “iteration” in the code below
//    after step 1 F(0) = (-1A) + (0B) + (1C) + (2D) + (3*E)
//
//    Step 2. Add n times the element which didn’t contributed in F(0),
//    which is A. This is done by adding "A[j-1]len" in the code below.
//    after step 2 F(0) = (4A) + (0B) + (1C) + (2D) + (3E)
//
//    At this point F(0) can be considered as F(1) and F(2) to F(4) can
//    be constructed by repeating the above steps.
//
//    Hope this explanation helps, cheers!

    public int maxRotateFunction2(int[] A) {
        if(A.length == 0){
            return 0;
        }

        int sum =0, iteration = 0, len = A.length;

        for(int i=0; i<len; i++){
            sum += A[i];
            iteration += (A[i] * i);
        }

        int max = iteration;
        for(int j=1; j<len; j++){
            // for next iteration lets remove one entry value of each entry and the prev 0 * k
            iteration = iteration - sum + A[j-1]*len;
            max = Math.max(max, iteration);
        }

        return max;
    }

//    This is essentially a Math problem.
//    Consider the array [ A, B, C, D ] with very simple coefficients as following:
//
//    f(0) = 0A + 1B + 2C + 3D
//    f(1) = 3A + 0B + 1C + 2D
//    f(2) = 2A + 3B + 0C + 1D
//    f(3) = 1A + 2B + 3C + 0D
//
//    We can see from above that:
//    f(0) -> f(1) -> f(2) -> f(3)
//    f(i) = f(i - 1) - SUM(A -> D) + N * A[i - 1]
    public int maxRotateFunction3(int[] A) {
        int n = A.length;
        int sum = 0;
        int candidate = 0;

        for (int i = 0; i < n; i++) {
            sum += A[i];
            candidate += A[i] * i;
        }
        int best = candidate;

        for (int i = 1; i < n; i++) {
            candidate = candidate - sum + A[i - 1] * n;
            best = Math.max(best, candidate);
        }
        return best;
    }
}
