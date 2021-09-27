package AlgoExpert.Hard;
import java.util.*;

public class Knapsack {
    // setup table where cols = capacities and rows = items
    // capacities = 0~n (0 capacity to n) and items = 0~n (0 item to n)
    // THERE IS AN ITEM 0!
    private static List<List<Integer>> knapsackProblem(int[][] items, int capacity) {
        int WEIGHT = 1;
        int VALUE = 0;
        // table where rows are the actual items and cols are individual weights (0 to capacity)
        int[][] knapsackValues = new int[items.length + 1][capacity + 1];
        // for each item get the weight and value
        for (int rowItem = 1; rowItem < items.length + 1; rowItem++) { // starting from item 1, note there is item 0
            int curWeight = items[rowItem - 1][WEIGHT]; // need to subtract 1 due to the 0 items row
            int curValue = items[rowItem - 1][VALUE];
            // iterate through all capacities
            for (int colCapacity = 0; colCapacity < capacity + 1; colCapacity++) {
                if (curWeight > colCapacity) { // if weight surpasses capacity, take the previous item's value
                    knapsackValues[rowItem][colCapacity] = knapsackValues[rowItem - 1][colCapacity];
                } else { // can fit into knapsack, take max of previous item's value and
                         // current item value(removing the previous item weight)
                    knapsackValues[rowItem][colCapacity] = Math.max(
                            knapsackValues[rowItem - 1][colCapacity],  // pick previous item value
                            // pick current value, need to add cur value
                            // on top of the previous item added to knapsack
                            // current value + max value from the previous item with PREVIOUS CAPACITY
                            // or max value without the item + cur value
                            knapsackValues[rowItem - 1][colCapacity - curWeight] + curValue);
                }
            }
        }
        return getKnapsackItems(knapsackValues,
                                items,
                                knapsackValues[items.length][capacity]);
    }

    private static List<List<Integer>> getKnapsackItems(int[][] knapsackValues,
                                                        int[][] items,
                                                        int maxValueFromArray) {
        List<List<Integer>> sequence = new ArrayList<>();
        List<Integer> maxValue = new ArrayList<>();
        // add max value
        maxValue.add(maxValueFromArray);
        sequence.add(maxValue);
        // new list for the items that add up to the max value
        sequence.add(new ArrayList<>());
        // use backtracking to find where value between items differs
        // or when the value is maximized (line 16)
        int row = knapsackValues.length - 1;
        int col = knapsackValues[0].length - 1;

        while (row > 0) {
            // cur item value is equal to prev item value
            if (knapsackValues[row][col] == knapsackValues[row-1][col]) {
                row--;
            } else {
                // prepend to the sequence row-1 (item index)
                sequence.get(1).add(0, row - 1);
                // note the order of the next two lines are IMPORTANT
                // if order reversed then need to use items[row][1] since we are at the previous item instead of cur item
                col -= items[row-1][1]; // subtract the weight of current item (row -1) due to #rows = #items + 1
                row--;                  // move one item previous
            }
            if (col == 0) break; // once col == 0 we are at the end
        }
        return sequence;
    }

    /**
     * My Solution PASSED!
     */
    public static List<List<Integer>> knapsackProblemMySol(int[][] items, int capacity) {
        int VALUE = 0;
        int WEIGHT = 1;

        int[][] dp = new int[items.length+1][capacity+1];
        List<List<Integer>> result = new ArrayList<>();

        for (int itemIdx = 1; itemIdx < dp.length; itemIdx++) {
            int[] item = items[itemIdx-1];
            for (int capIdx = 1; capIdx < dp[0].length; capIdx++) {
                int curCap = capIdx;
                if (item[WEIGHT] <= curCap) {
                    dp[itemIdx][capIdx] = Math.max(dp[itemIdx-1][capIdx], dp[itemIdx-1][capIdx-item[WEIGHT]] + item[VALUE]);
                } else {
                    dp[itemIdx][capIdx] = dp[itemIdx-1][capIdx];
                }
            }
        }
        return buildResult(dp, items);
    }

    static List<List<Integer>> buildResult(int[][] dp, int[][] items) {
        int row = dp.length-1;
        int col = dp[0].length-1;
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        result.get(0).add(dp[row][col]);
        result.add(new ArrayList<>());
        while (row > 0) {
            if (dp[row][col] != dp[row-1][col]) {
                result.get(1).add(0, row-1);
                col = col-items[row-1][1];
                row--;
            } else {
                row--;
            }
        }
        return result;
    }
}
