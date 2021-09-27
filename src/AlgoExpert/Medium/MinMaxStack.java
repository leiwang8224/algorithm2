package AlgoExpert.Medium;
import java.util.*;
public class MinMaxStack {
    public static void main(String[] args) {

    }

    /**
     * Use a list of maps to keep track of the min and max for the current index.
     */
    static class MinMaxStackImpl {
        // for each integer in the stack, keep a min and max by
        // using a list of map (list corresponds to the stack and map
        // keeps track of the min and max)
        List<Map<String, Integer>> minMaxStack = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        // O(1) time | O(1) space
        int peek() {
            return list.get(list.size() - 1);
        }

        // O(1) time | O(1) space
        int pop() {
            minMaxStack.remove(list.size() - 1);
            return list.remove(list.size() - 1);
        }

        // O(1) time | O(1) space
        void push(int number) {
            // intialize new map with the new number
            Map<String, Integer> newMinMax = new HashMap<>();
            newMinMax.put("min", number);
            newMinMax.put("max", number);
            // replace the min and max if new number is min or max
            if (minMaxStack.size() > 0) {
                // get a copy of the last min max and compare against the new number
                // replace if necessary
                Map<String, Integer> lastMinMax =
                        new HashMap<>(minMaxStack.get(minMaxStack.size()-1));
                newMinMax.replace("min", Math.min(lastMinMax.get("min"), number));
                newMinMax.replace("max", Math.max(lastMinMax.get("max"), number));
            }
            minMaxStack.add(newMinMax);
            list.add(number);
        }

        // O(1) time | O(1) space
        int getMin() {
            return minMaxStack.get(minMaxStack.size() - 1).get("min");
        }

        // O(1) time | O(1) space
        int getMax() {
            return minMaxStack.get(minMaxStack.size() - 1).get("max");
        }
    }
}
