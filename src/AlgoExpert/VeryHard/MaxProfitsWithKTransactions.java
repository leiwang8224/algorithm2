package AlgoExpert.VeryHard;

public class MaxProfitsWithKTransactions {
    // O(nk) time | O(nk) space
    public static int maxProfit(int[] prices, int numberTransactions) {
        if (prices.length == 0) return 0;
        // build solution from 0 transactions to n transactions
        int[][] profits = new int[numberTransactions+1][prices.length];
        for (int transactions = 1; transactions < profits.length; transactions++) {
            int maxProfitForCurTransactionUpToPrevDay = Integer.MIN_VALUE;
            for (int day = 1; day < profits[0].length; day++) {
                // iterate from the first day to the last day and calculate
                // the maximum profit up to the current day
                // profits[transaction-1][day-1] - prices[day-1] =>
                // cost of buying the stock on day-1 + profits gained on day-1 at the current transaction
                maxProfitForCurTransactionUpToPrevDay = Math.max(maxProfitForCurTransactionUpToPrevDay, //buy stock on current day
                                                    profits[transactions-1][day-1] - prices[day - 1]);  //profits from prev transaction/prev day - prev day price
                // take max of sell vs not sell
                profits[transactions][day] = Math.max(profits[transactions][day-1], // don't sell on this day, take max profit from prev day (do nothing)
                                                        maxProfitForCurTransactionUpToPrevDay + prices[day]); // sell on this day so take profit
                                                                                                    //in order to sell on this day we need to purchase on any one of[0, i-1] days
                                                                                                    //so we need to go back find the day to purchase the stock to maximize profit
            }
        }
        return profits[numberTransactions][prices.length-1];
    }

    // O(nk) time | O(n) space
    public static int maxProfit2(int[] prices, int k) {
        if (prices.length == 0) return 0;

        int[] evenProfits = new int[prices.length];
        int[] oddProfits = new int[prices.length];

        int[] currentProfits;
        int[] prevProfits;

        for (int transaction = 1; transaction < k + 1; transaction++) {
            int maxThusFar = Integer.MIN_VALUE;
            if (transaction % 2 == 1) { // odd number transactions
                currentProfits = oddProfits;
                prevProfits = evenProfits;
            } else { // even number transactions
                currentProfits = evenProfits;
                prevProfits = oddProfits;
            }

            for (int day = 1; day < prices.length; day++) {
                maxThusFar = Math.max(maxThusFar, prevProfits[day-1] - prices[day-1]);
                currentProfits[day] = Math.max(currentProfits[day-1], maxThusFar + prices[day]);
            }
        }
        return k % 2 == 0 ? evenProfits[prices.length - 1] : oddProfits[prices.length - 1];
    }

}
