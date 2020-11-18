package DP;

import java.util.HashSet;
import java.util.Set;

public class MinCostForTickets {
    public static void main(String[] args) {
        int[] days = new int[]{1,2,3,4,5,6,7,8,9,10,30,31};
        int[] costs = new int[]{2,7,15};

        System.out.println(minCostTicketsRecurse(days, costs));
        System.out.println(minCostTicketsDP(days, costs));
    }

    // time O(W) where W = 365 is the max numbered day
    // space O(W)

    /**
     * For each day, if you don't have to travel today, then it's strictly better
     * to wait to buy a pass. If you have to travel today, you have up to 3 choices:
     * you must buy either a 1-day, 7-day, or 30-day pass.
     *
     * We can express those choices as a recursion and use dynamic programming.
     * Let's say dp(i) is the cost to fulfill your travel plan from day i to the
     * end of the plan. Then, if you have to travel today, your cost is:
     *
     * dp(i)=min(dp(i+1)+costs[0],dp(i+7)+costs[1],dp(i+30)+costs[2])
     * where:
     * 1 day pass is sold for costs[0] dollars
     * 7 day pass is sold for costs[1] dollars
     * 30 day pass is sold for costs[2] dollars
     * Limitations:
     1 <= days.length <= 365
     1 <= days[i] <= 365
     days is in strictly increasing order.
     costs.length == 3
     1 <= costs[i] <= 1000
     * @param days
     * @param costs
     * @return
     */
    private static int minCostTicketsRecurse(int[] days, int[] costs) {
        Integer[] allPossibleDaysToTravel = new Integer[366];
        Set<Integer> possibleDaysToTravel = new HashSet();
        for (int day: days) possibleDaysToTravel.add(day);

        // recurse from day 1
        return findMinCostToTravel(1, costs, allPossibleDaysToTravel, possibleDaysToTravel);
    }

    private static int findMinCostToTravel(int whichDayToTravel, int[] costs, Integer[] memoWhichDayToTravel, Set<Integer> possibleDaysToTravel) {
        if (whichDayToTravel > 365)
            return 0;
        if (memoWhichDayToTravel[whichDayToTravel] != null)
            return memoWhichDayToTravel[whichDayToTravel];

        int minCostToTravel;
        if (possibleDaysToTravel.contains(whichDayToTravel)) {
            // find min number of dollars you need to travel every day in the given list of days
            minCostToTravel = Math.min(findMinCostToTravel(whichDayToTravel + 1, costs, memoWhichDayToTravel, possibleDaysToTravel) + costs[0],
                    Math.min(findMinCostToTravel(whichDayToTravel + 7, costs, memoWhichDayToTravel, possibleDaysToTravel) + costs[1],
                    findMinCostToTravel(whichDayToTravel + 30, costs, memoWhichDayToTravel, possibleDaysToTravel) + costs[2]));
        } else {
            minCostToTravel = findMinCostToTravel(whichDayToTravel + 1, costs, memoWhichDayToTravel, possibleDaysToTravel);
        }

        memoWhichDayToTravel[whichDayToTravel] = minCostToTravel;
        return minCostToTravel;
    }

    public static int minCostTicketsDP(int[] days, int[] costs) {
        Integer[] memo = new Integer[days.length];
        int[] durations = new int[]{1, 7, 30};

        return dp(0, costs, memo, days, durations);
    }

    public static int dp(int memoIndex, int[] costs, Integer[] memo, int[] days, int[] durations) {
        if (memoIndex >= days.length)
            return 0;
        if (memo[memoIndex] != null)
            return memo[memoIndex];

        int minCostToTravel = Integer.MAX_VALUE;
        int daysToTravel = memoIndex;
        for (int indexDurations = 0; indexDurations < durations.length; ++indexDurations) {
            while (daysToTravel < days.length &&
                    days[daysToTravel] < days[memoIndex] + durations[indexDurations]) {
                daysToTravel++;
            }
            minCostToTravel = Math.min(minCostToTravel,
                    dp(daysToTravel, costs, memo, days, durations) + costs[indexDurations]);
        }

        memo[memoIndex] = minCostToTravel;
        return minCostToTravel;
    }
}
