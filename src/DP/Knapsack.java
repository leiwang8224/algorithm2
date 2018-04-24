package DP;

public class Knapsack {
    private static int knapSackBruteForce(int W, int wt[], int val[], int n) {
        // base case
        if (n == 0 || W == 0)
            return 0;

        // if weight of the nth item is more than knapsack capacity W,
        // then this item cannot be included in the optimal solution
        if (wt[n-1] > W)
            return knapSackBruteForce(W, wt, val, n-1);

        // return the max of the two cases:
        // 1. nth item not included
        // 2. not included
        else return max(val[n-1] + knapSackBruteForce(W-wt[n-1], wt, val, n-1), knapSackBruteForce(W,wt,val,n-1));
    }


    // A utility function that returns maximum of two integers
    private static int max(int a, int b) { return (a > b)? a : b; }

    // Returns the maximum value that can be put in a knapsack of capacity W
    private static int knapSack(int W, int wt[], int val[], int n)
    {
        int i, w;
        int K[][] = new int[n+1][W+1];

        // Build table K[][] in bottom up manner
        for (i = 0; i <= n; i++)
        {
            for (w = 0; w <= W; w++)
            {
                if (i==0 || w==0)
                    K[i][w] = 0;
                else if (wt[i-1] <= w)
                    K[i][w] = max(val[i-1] + K[i-1][w-wt[i-1]],  K[i-1][w]);
                else
                    K[i][w] = K[i-1][w];
            }
        }

        return K[n][W];
    }

    public static void main(String args[]) {
        int val[] = new int[]{60,100,120};
        int weight[] = new int[]{10,20,30};
        int W = 50;
        int n = val.length;
        System.out.println(knapSack(W,weight,val,n));
        System.out.println(knapSackBruteForce(W,weight,val,n));
    }
}
