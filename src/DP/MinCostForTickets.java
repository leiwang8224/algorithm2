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
        System.out.println(minCostRecurse(days, costs));
        System.out.println(minCostMemo(days, costs));
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

    private static int findMinCostToTravel(int whichDayToTravel, int[] costs, Integer[] memoWhichDayToTravel, Set<Integer> daysToTravel) {
        if (whichDayToTravel >= memoWhichDayToTravel.length)
            return 0;
        if (memoWhichDayToTravel[whichDayToTravel] != null)
            return memoWhichDayToTravel[whichDayToTravel];

        int minCostToTravel;
//      For travel days, it's a minimum of yesterday's cost plus single-day ticket, or cost for
//      8 days ago plus 7-day pass, or cost 31 days ago plus 30-day pass.
        if (daysToTravel.contains(whichDayToTravel)) {
            // find min number of dollars you need to travel every day in the given list of days
            minCostToTravel = Math.min(findMinCostToTravel(whichDayToTravel + 1, costs, memoWhichDayToTravel, daysToTravel) + costs[0],
                    Math.min(findMinCostToTravel(whichDayToTravel + 7, costs, memoWhichDayToTravel, daysToTravel) + costs[1],
                    findMinCostToTravel(whichDayToTravel + 30, costs, memoWhichDayToTravel, daysToTravel) + costs[2]));
        } else {
            // for non-travel days, the cost is the same as the previous day
            minCostToTravel = findMinCostToTravel(whichDayToTravel + 1, costs, memoWhichDayToTravel, daysToTravel);
        }

        memoWhichDayToTravel[whichDayToTravel] = minCostToTravel;
        return minCostToTravel;
    }

    /**
     * As in Approach 1, we only need to buy a travel pass on a day we intend to travel.
     *
     * Now, let dp(i) be the cost to travel from day days[i] to the end of the plan.
     * If say, j1 is the largest index such that days[j1] < days[i] + 1,
     * j7 is the largest index such that days[j7] < days[i] + 7,
     * and j30 is the largest index such that days[j30] < days[i] + 30, then we have:
     *
     * 1. form memo using Integer so you can check for null
     * 2. form array of durations
     * 3. call recursive function with starting index
     * 4. base condition: index of days to travel reaches outside of matrix size
     * 5. base condition: return the memo element if it's not null
     * 6. initialize min cost to travel and assign the current index
     * 7. loop through durations and loop through indices for traveling days
     * 8. increment current index so that we are at the element just below the added traveling days
     * 9. calculate min cost by taking min of min cost and recurse call plus the current cost
     * 10. set memo element to the min cost
     * 11. return the min cost
     *
     * dp(i)=min(dp(j1)+costs[0],dp(j7)+costs[1],dp(j30)+costs[2])
     * time: O(N) where N is the number of unique days in travel plan
     * space: O(N)
     * @param daysToTravel
     * @param costs
     * @return
     */
    public static int minCostTicketsDP(int[] daysToTravel, int[] costs) {
        int[] memo = new int[daysToTravel.length];
        java.util.Arrays.fill(memo, Integer.MAX_VALUE);
        int[] durations = new int[]{1, 7, 30};

        return findMinCostDP(0, costs, memo, daysToTravel, durations);
    }

    public static int findMinCostDP(int indexDaysToTravel, int[] costs, int[] memo, int[] daysToTravel, int[] durations) {
        // base case
        if (indexDaysToTravel >= daysToTravel.length)
            return 0;
        if (memo[indexDaysToTravel] != Integer.MAX_VALUE) // memo[] could also be Integer so just need to check whether it's null here
            return memo[indexDaysToTravel];

        // init minCostToTravel and dayToTravel
        int minCostToTravel = Integer.MAX_VALUE;
        int curIndexDaysToTravel = indexDaysToTravel;

        // for each duration add to day to travel and check if greater than
        for (int indexCostAndDuration = 0; indexCostAndDuration < durations.length; ++indexCostAndDuration) {
            // iterate from indexDaysToTravel to daysToTravel length
            for (int index = curIndexDaysToTravel; index < daysToTravel.length; index++) {
                // days[j1] < days[i] + 1 or days[j7] < days[i] + 7 or days[j30] < days[i] + 30
                // find max index i such that days[i] < days[indexDaysToTravel] + duration
                // or find the day to travel which is just less than current day + duration
                if (daysToTravel[curIndexDaysToTravel] < daysToTravel[indexDaysToTravel] + durations[indexCostAndDuration]) {
                    curIndexDaysToTravel++;
                }
            }
            minCostToTravel = Math.min(minCostToTravel,
                    // curIndexDaysToTravel is the max index in which days[curIndexDaysToTravel] < days[indexDaysToTravel] + duration
                    // recurse to find cost up to now plus the current cost
                    findMinCostDP(curIndexDaysToTravel, costs, memo, daysToTravel, durations) + costs[indexCostAndDuration]);
        }

        // store result to memo before returning the result
        memo[indexDaysToTravel] = minCostToTravel;
        return minCostToTravel;
    }

    /**
     * We track the minimum cost for each travel day. We process only travel days and store
     * {day, cost} for 7-and 30-day passes in the last7 and last30 queues. After a pass
     * 'expires', we remove it from the queue. This way, our queues only contains travel
     * days for the last 7 and 30 days, and the cheapest pass prices are in the front
     * of the queues.
     * time: O(n) where n is the number of travel days
     * space: O(38) it's a sum of duration for all pass types(1+7+30)
     * @param daysToTravel
     * @param costs
     * @return
     */
    public static int mincostTickets(int[] daysToTravel, int[] costs) {
        // using queue so that the oldest ticket is at the top.
        // to store <dayToTravel, totalcost>
        Queue<int[]> last7daysPasses = new LinkedList<>(), last30daysPasses = new LinkedList<>();

        int totalCost = 0;

        // discarding the 7 days passes and 30 days passes that expires before the last travel date
        for(int indexDaysToTravel=0; indexDaysToTravel < daysToTravel.length; indexDaysToTravel++){
            // discarding expired 7days pass
            while(!last7daysPasses.isEmpty() && last7daysPasses.peek()[0] + 7 <= daysToTravel[indexDaysToTravel]){
                last7daysPasses.poll();
            }

            // costs[1] is the cost for the 7 days pass
            last7daysPasses.offer(new int[]{daysToTravel[indexDaysToTravel], totalCost + costs[1]});

            // discarding expired 30 days pass.
            while(!last30daysPasses.isEmpty() && last30daysPasses.peek()[0] + 30 <= daysToTravel[indexDaysToTravel]){
                last30daysPasses.poll();
            }

            // costs[2] is the cost for the 30 days pass
            last30daysPasses.offer(new int[]{daysToTravel[indexDaysToTravel], totalCost + costs[2]});

            // taking the min of daily pass and current valid 7 days or 30 days pass.
            // costs[0] is the cost for the 1 day pass
            totalCost = Math.min(totalCost + costs[0], Math.min(last30daysPasses.peek()[1], last7daysPasses.peek()[1]));
        }

        return totalCost;
    }

    /**
     * Starting from the basics and build up
     */
    public static int minCostRecurse(int[] daysToTravel, int[] costs) {
        return solve(daysToTravel, costs, 0);
    }

//    if i buy one month pass , then i would be able to travel for next 30 days for free,
//    then my cost will be 30 day pass cost + cost for remaining days after 30 day
//    if i buy one week pass , then i would be able to travel for next 7 days for free,
//    then my cost will be 7 day pass cost + cost for remaining days after 30 day
//    so we can skip to next day OR , next week OR next month ,
//    so cost for i would total pay today is going to be ? ... ?
//
//    # a = cost[one day pass] + cost of next day
//    # b = cost[ week pass ] + cost of next day after week
//    # c = cost[ month pass ] + cost of next day after month
    // 1. As with any recursive function, need base condition, once index travels out bounds, return 0
    // 2. calculate the cost for a day which is a day's ticket cost plus recursive call with index + 1
    // 3. get current index and increment till we are just below current day to travel + 7
    // 4. calculate the cost for 7 days by adding cost of 7 days plus recursive call on current index
    // 5. continue incrementing current index until we are just below current day to travel + 30
    // 6. calculate the cost for a month by adding cost of 30 days plus recursive call on current index
    // 7. take the minimum of cost for one day, cost for a week and cost for a month
    private static int solve(int[] daysToTravel, int[] costs, int index) {
        if (index >= daysToTravel.length) return 0;

        // cost for one day is cost of one day ticket + cost of day thereafter
        int costForADay = costs[0] + solve(daysToTravel, costs, index+1);

        int curIndex;
        // increment from curIndex until just below the current day to travel + 7 days
        for (curIndex = index;
             curIndex < daysToTravel.length &&
                                daysToTravel[curIndex] < daysToTravel[index] + 7;
                                curIndex++);
        // cost for a week = one week ticket cost + cost of next day after week
        int costForAWeek = costs[1] + solve(daysToTravel, costs, curIndex);

        // increment from curIndex until just below the current day to travel + 30 days
        for (curIndex = index;
             curIndex < daysToTravel.length &&
                                daysToTravel[curIndex] < daysToTravel[index] + 30;
                                curIndex++);
        // cost for a month = one month ticket cost + cost of next day after one month
        int costForAMonth = costs[2] + solve(daysToTravel, costs, curIndex);

        return Math.min(costForADay, Math.min(costForAMonth, costForAWeek));
    }

    /**
     * Use memo array to build up the solution
     * 1. use a dp length of 365 + 1
     * 2. call recurse function starting at index 0
     * 3. base condition is when index reaches above the length of the dp array
     * 4. base condition 2 is when dp at index is not null, then simply return dp at index
     * 5. calculate cost for a day which is a day's ticket cost plus recursive calls on index + 1
     * 6. get current index and increment till we are just below current day to travel plus 7
     * 7. calculate the cost for 7 days by adding cost of 7 days plus recursive call on current index
     * 8. continue increment current index till we are just below current day to travel plus 30
     * 9. calculate the cost for a month by adding cost of 30 days plus recursive call on current index
     * 10. take the minimum of cost for one day, a week and a month
     */
    public static int minCostMemo(int[] daysToTravel, int[] costs) {
        Integer[] dp = new Integer[366];
        return solveMemo(daysToTravel, costs, 0, dp);
    }

    private static int solveMemo(int[] daysToTravel, int[] costs, int index, Integer[] dp) {
        if (index >= daysToTravel.length) return 0;

        if (dp[index] != null) return dp[index];

        int costForADay = costs[0] + solveMemo(daysToTravel, costs, index+1, dp);

        int curIndex;
        for (curIndex = index;
             curIndex < daysToTravel.length && daysToTravel[curIndex] < daysToTravel[index] + 7;
             curIndex++);
        int costForAWeek = costs[1] + solveMemo(daysToTravel, costs, curIndex, dp);

        for (curIndex = index;
             curIndex < daysToTravel.length && daysToTravel[curIndex] < daysToTravel[index] + 30;
             curIndex++);
        int costForAMonth = costs[2] + solveMemo(daysToTravel, costs, curIndex, dp);

        // store solution for future references
        dp[index] = Math.min(costForADay, Math.min(costForAMonth, costForAWeek));

        return dp[index];
    }

    public static int minCostTicketsDP2(int[] days, int[] costs) {
        Integer[] memo = new Integer[366];
        return minCostDP(days, costs, 0, memo);
    }
    public static int minCostDP(int[] daysToTravel, int[] costs, int startIndex, Integer[] memo) {
        if (startIndex >= daysToTravel.length) return 0;

        if (memo[startIndex] != null) return memo[startIndex];
        int costDay = costs[0] + minCostDP(daysToTravel, costs, startIndex+1, memo);

        int curIndex;
        for (curIndex = startIndex;
             curIndex < daysToTravel.length && daysToTravel[curIndex] < daysToTravel[startIndex] + 7;
             curIndex++);
        int costWeek = costs[1] + minCostDP(daysToTravel, costs, curIndex, memo);

        for (curIndex = startIndex;
            curIndex < daysToTravel.length && daysToTravel[curIndex] < daysToTravel[startIndex] + 30;
            curIndex++);
        int costMonth = costs[2] + minCostDP(daysToTravel, costs, curIndex, memo);

        memo[startIndex] = Math.min(costDay, Math.min(costWeek, costMonth));

        return memo[startIndex];

    }
}
