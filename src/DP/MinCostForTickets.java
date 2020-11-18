package DP;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
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
     * For each travel day, we can buy a one-day ticket, or use 7-day
     * or 30-day pass as if we would have purchased it 7 or 30 days ago.
     * We need to track rolling costs for at least 30 days back, and
     * use them to pick the cheapest option for the next travel day.
     *
     * Here, we can use two approaches: track cost for all calendar days,
     * or process only travel days. The first approach is simpler to
     * implement, but it's slower. Since the problem is limited to one calendar
     * year, it does not make much of a difference; for a generalized problem
     * I would recommend the second approach.
     *
     * 1. Track calendar days
     *
     * We track the minimum cost for all calendar days in dp. For non-travel days,
     * the cost stays the same as for the previous day. For travel days,
     * it's a minimum of yesterday's cost plus single-day ticket, or cost for
     * 8 days ago plus 7-day pass, or cost 31 days ago plus 30-day pass.
     * time: O(W), where W is 365 is the max numbered days
     * space: O(W)
     */
    private static int minCostTicketsRecurse(int[] days, int[] costs) {
        Integer[] allPossibleDaysToTravel = new Integer[366];
        Set<Integer> setOfPossibleDaysTravel = new HashSet();
        // move days into a set
        for (int day: days) setOfPossibleDaysTravel.add(day);

        // recurse from day 1
        return findMinCostToTravel(1, costs, allPossibleDaysToTravel, setOfPossibleDaysTravel);
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

    /**
     * time: O(N) where N is the number of unique days in travel plan
     * space: O(N)
     * @param days
     * @param costs
     * @return
     */
    public static int minCostTicketsDP(int[] days, int[] costs) {
        Integer[] memo = new Integer[days.length];
        int[] durations = new int[]{1, 7, 30};

        return findMinCostDP(0, costs, memo, days, durations);
    }

    public static int findMinCostDP(int memoIndex, int[] costs, Integer[] memo, int[] days, int[] durations) {
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
                    findMinCostDP(daysToTravel, costs, memo, days, durations) + costs[indexDurations]);
        }

        memo[memoIndex] = minCostToTravel;
        return minCostToTravel;
    }

    /**
     * We track the minimum cost for each travel day. We process only travel days and store
     * {day, cost} for 7-and 30-day passes in the last7 and last30 queues. After a pass
     * 'expires', we remove it from the queue. This way, our queues only contains travel
     * days for the last 7 and 30 days, and the cheapest pass prices are in the front
     * of the queues.
     * @param days
     * @param costs
     * @return
     */
    public static int mincostTickets(int[] days, int[] costs) {
        // using queue so that the oldest ticket is at the top.
        Queue<int[]> last7days = new LinkedList<>(), last30days = new LinkedList<>();

        int totalCost = 0;
        for(int i=0; i < days.length; i++){
            // discarding expired 7days pass
            while(!last7days.isEmpty() && last7days.peek()[0] + 7 <= days[i]){
                last7days.poll();
            }

            last7days.offer(new int[]{days[i], totalCost + costs[1]});

            // discarding expired 30 days pass.
            while(!last30days.isEmpty() && last30days.peek()[0] + 30 <= days[i]){
                last30days.poll();
            }

            last30days.offer(new int[]{days[i], totalCost + costs[2]});

            // taking the min of daily pass and current valid 7 days or 30 days pass.
            totalCost = Math.min(totalCost + costs[0], Math.min(last30days.peek()[1], last7days.peek()[1]));
        }

        return totalCost;
    }
}
