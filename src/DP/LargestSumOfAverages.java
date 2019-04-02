package DP;

//We partition a row of numbers A into at most K adjacent (non-empty) groups, then our score is the sum of the average of each group. What is the largest score we can achieve?
//
//        Note that our partition must use every number in A, and that scores are not necessarily integers.
//
//        Example:
//        Input:
//        A = [9,1,2,3,9]
//        K = 3
//        Output: 20
//        Explanation:
//        The best choice is to partition A into [9], [1, 2, 3], [9]. The answer is 9 + (1 + 2 + 3) / 3 + 9 = 20.
//        We could have also partitioned A into [9, 1], [2], [3, 9], for example.
//        That partition would lead to a score of 5 + 2 + 6 = 13, which is worse.

public class LargestSumOfAverages {
    public static void main(String[] args) {
        
    }


//    Let f[i][j]be the largest sum of averages for first i + 1 numbers(A[0], A[1], ... , A[i])
//    to j groups. f[i][j] consists of two parts: first j-1 groups' averages and the last group's
//    average. Considering the last group, its last number must be A[i] and its first number can
//    be from A[0] to A[i]. Suppose the last group starts from A[p+1], we can easily get the average
//    form A[p+1] to A[i]. The sum of first j-1 groups' average is f[p][j-1] which we have got before.
//    So now we can write the DP equation:
//    f[i][j] = max {f[p][j-1] + (A[p+1] + A[p+2] + ... + A[i]) / (i - p)}, p = 0,1,...,i-1
    private double largestSumOfAverages(int[] array, int k) {
        if (k == 0 || array.length == 0)
            return 0;

        int l = array.length;
        double[][] f = new double[l][k+1];
        double[] s = new double[l + 1];

        for (int i = 1; i <= l; i ++) {
            s[i] = s[i - 1] + array[i - 1];
            f[i - 1][1] = s[i] / i;
        }

        for(int j = 2; j <= k; j ++) {
            for (int i = 0; i < l; i++) {
                double max = Double.MIN_VALUE;
                for (int p = 0; p < i; p++) {
                    double sum = f[p][j-1] + (s[i+1] - s[p+1]) / (i - p);
                    max = Double.max(sum, max);
                }
                f[i][j] = max;
            }
        }
        return f[l-1][k];
    }

    private double largestSumOfAverages2(int[] array, int k) {
        int n = array.length;
        double[][] memo = new double[n+1][n+1];
        double cur = 0;

        for (int i = 0; i < n; ++i) {
            cur += array[i];
            memo[i+1][1] = cur / (i + 1);
        }

        return search(n, k, array, memo);
    }

    private double search(int n, int k, int[] array, double[][] memo) {
        if (memo[n][k] > 0) return memo[n][k];
        if (n < k) return 0;
        double cur = 0;

        for (int i = n - 1; i > 0; --i) {
            cur += array[i];
            memo[n][k] = Math.max(memo[n][k], search(i, k-1, array, memo) + cur / (n - i));
        }
        return memo[n][k];
    }

//    Intuition
//
//    The best score partitioning A[i:] into at most K parts depends on answers to paritioning
//    A[j:] (j > i) into less parts. We can use dynamic programming as the states form a directed acyclic graph.
//
//    Algorithm
//
//    Let dp(i, k) be the best score partioning A[i:] into at most K parts.
////
////    If the first group we partition A[i:] into ends before j, then our candidate partition has score average(i, j) + dp(j, k-1)),
//      where average(i, j) = (A[i] + A[i+1] + ... + A[j-1]) / (j - i) (floating point division). We take the highest score of these,
//      keeping in mind we don't necessarily need to partition - dp(i, k) can also be just average(i, N).
////
////    In total, our recursion in the general case is dp(i, k) = max(average(i, N), max_{j > i}(average(i, j) + dp(j, k-1))).
////
////    We can calculate average a little bit faster by remembering prefix sums. If P[x+1] = A[0] + A[1] + ... + A[x], then average(i, j) = (P[j] - P[i]) / (j - i).
////
////    Our implementation showcases a "bottom-up" style of dp. Here at loop number k in our outer-most loop, dp[i] represents dp(i, k)
//      from the discussion above, and we are calculating the next layer dp(i, k+1). The end of our second loop for i = 0..N-1 represents
//      finishing the calculation of the correct value for dp(i, t), and the inner-most loop performs the calculation max_{j > i}(average(i, j) + dp(j, k)).


//    Time Complexity: O(K∗N2)O(K * N^2)O(K∗N2), where NNN is the length of A.
//
//    Space Complexity: O(N)O(N)O(N), the size of dp.

    private double largestSumOfAverages3(int[] array, int k) {
        int n = array.length;
        double[] p = new double[n + 1];

        for (int i = 0; i < n; ++i) {
            p[i+1]= p[i]+ array[i];
        }

        double[] dp = new double[n];
        for (int i = 0; i < n; ++i) {
            dp[i] = (p[n] - p[i]) / (n - i);
        }

        for (int j = 0; j < k - 1; ++j) {
            for (int i = 0; i < n; ++i) {
                for (int l = i + 1; l < n; ++l) {
                    dp[i] = Math.max(dp[i], (p[j] - p[i]) / (j - i) + dp[j]);
                }
            }

        }
        return dp[0];
    }
}
