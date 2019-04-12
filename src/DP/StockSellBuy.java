package DP;

//Say you have an array for which the ith element is the
// price of a given stock on day i.
//
//If you were only permitted to complete at most one transaction
// (ie, buy one and sell one share of the stock), design an algorithm to find the maximum profit.
public class StockSellBuy {
    public static void main(String[] args) {
        int[] input = new int[]{7,1,5,3,6,4};
        System.out.println("max profit is " + maxProfit(input));
        System.out.println("max profit is " + maxProfit2(input));
        System.out.println("max profit is " + maxProfit3(input));
        System.out.println("max profit is " + maxProfit2Alt(input));
        System.out.println("max profit is " + maxProfit3DP(input));
        // k is the max number of transactions allowable
        System.out.println("max profit is " + maxProfit4(2,input));

    }
    private static int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0 ;
        }
        int max = 0 ;

        // initialize the soFarMin with first element
        int sofarMin = prices[0] ;

        // iterate through prices
        for (int i = 0 ; i < prices.length ; ++i) {
            // if the current price is greater than soFarMin
            // set max = maximum of max and current price - soFarMin (what's left)
            if (prices[i] > sofarMin) {
                max = Math.max(max, prices[i] - sofarMin) ;
            } else{
                // else set the current price to be soFarMin
                sofarMin = prices[i];
            }
        }
        return  max ;
    }

    /**
     * Say you have an array for which the ith element is the price of a given stock on day i.

     Design an algorithm to find the maximum profit. You may complete as many transactions as
     you like (ie, buy one and sell one share of the stock multiple times). However, you may
     not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     * @param prices
     * @return
     */
    private static int maxProfit2(int[] prices) {
        int profit = 0, i = 0;
        while (i < prices.length) {
            // find next local minimum
            while (i < prices.length-1 && prices[i+1] <= prices[i]) i++;
            int min = prices[i++]; // need increment to avoid infinite loop for "[1]"
            // find next local maximum
            while (i < prices.length-1 && prices[i+1] >= prices[i]) i++;
            profit += i < prices.length ? prices[i++] - min : 0;
        }
        return profit;
    }

    private static int maxProfit2Alt(int[] prices) {
        int total = 0;
        for (int i=0; i< prices.length-1; i++) {
            if (prices[i+1]>prices[i]) total += prices[i+1]-prices[i];
        }
        return total;
    }

    /**
     * Same as above, design an algorithm to find the max profit.
     * You may complete at most two transactions
     * @param prices
     * @return
     */
    private static int maxProfit3(int[] prices) {
        int hold1 = Integer.MIN_VALUE, hold2 = Integer.MIN_VALUE;
        int release1 = 0, release2 = 0;
        for(int i:prices){                              // Assume we only have 0 money at first
            release2 = Math.max(release2, hold2+i);     // The maximum if we've just sold 2nd stock so far.
            hold2    = Math.max(hold2,    release1-i);  // The maximum if we've just buy  2nd stock so far.
            release1 = Math.max(release1, hold1+i);     // The maximum if we've just sold 1nd stock so far.
            hold1    = Math.max(hold1,    -i);          // The maximum if we've just buy  1st stock so far.
        }
        return release2; ///Since release1 is initiated as 0, so release2 will always higher than release1.
    }

    private static int maxProfit3DP(int[] prices) {
        // these four variables represent your profit after executing corresponding transaction
        // in the beginning, your profit is 0.
        // when you buy a stock ,the profit will be deducted of the price of stock.
        int firstBuy = Integer.MIN_VALUE, firstSell = 0;
        int secondBuy = Integer.MIN_VALUE, secondSell = 0;

        for (int curPrice : prices) {
            if (firstBuy < -curPrice) firstBuy = -curPrice; // the max profit after you buy first stock
            if (firstSell < firstBuy + curPrice) firstSell = firstBuy + curPrice; // the max profit after you sell it
            if (secondBuy < firstSell - curPrice) secondBuy = firstSell - curPrice; // the max profit after you buy the second stock
            if (secondSell < secondBuy + curPrice) secondSell = secondBuy + curPrice; // the max profit after you sell the second stock
        }

        return secondSell; // secondSell will be the max profit after passing the prices
    }

    /**
     * Design an algorithm to find the maximum profit. You may complete at most k transactions.

     Note:
     You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     * @param k
     * @param prices
     * @return
     */
    private static int maxProfit4(int k, int[] prices) {
        int len = prices.length;
        if (k >= len / 2) return quickSolve(prices);

        int[][] t = new int[k + 1][len];
        for (int i = 1; i <= k; i++) {
            int tmpMax =  -prices[0];
            for (int j = 1; j < len; j++) {
                t[i][j] = Math.max(t[i][j - 1], prices[j] + tmpMax);
                tmpMax =  Math.max(tmpMax, t[i - 1][j - 1] - prices[j]);
            }
        }
        return t[k][len - 1];
    }

    private static int quickSolve(int[] prices) {
        int len = prices.length, profit = 0;
        for (int i = 1; i < len; i++)
            // as long as there is a price gap, we gain a profit.
            if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
        return profit;
    }
}
