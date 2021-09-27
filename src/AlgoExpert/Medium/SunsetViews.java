package AlgoExpert.Medium;

import java.util.ArrayList;
import java.util.Collections;

public class SunsetViews {
    public static void main(String[] args) {

    }

    // O(n) time | O(n) space

    /**
     * traverse from right if EAST, left if WEST
     * basically keep track of the highest building and if any higher building appears,
     * add to result
     */
    private ArrayList<Integer> sunsetViews(int[] building, String direction) {
        ArrayList<Integer> indicesWithSunset = new ArrayList<>();

        int startIdx = building.length - 1;
        int step = -1;

        if (direction.equals("WEST")) {
            startIdx = 0;
            step = 1;
        }

        int idx = startIdx;
        int runningMaxHeight = 0;

        while (idx >= 0 && idx < building.length) {
            int buildingHeight = building[idx];

            if (buildingHeight > runningMaxHeight) {
                indicesWithSunset.add(idx);
            }

            runningMaxHeight = Math.max(runningMaxHeight, buildingHeight);

            idx += step;
        }

        if (direction.equals("EAST")) {
            Collections.reverse(indicesWithSunset);
        }

        return indicesWithSunset;
    }

    // O(n) space | O(n) time

    /**
     * traverse from left if EAST, right if WEST
     * basically use a stack to keep track of the highest buildings
     * if the building in the stack is shorter then remove from stack
     * else push onto stack
     */
    private ArrayList<Integer> sunsetViewsStackImpl(int[] buildings, String direction) {
        ArrayList<Integer> candidateIndices = new ArrayList<>();

        int startIdx = buildings.length - 1;
        int step = -1;

        // normal direction, does not need to go opposing direction
        if (direction.equals("EAST")) {
            startIdx = 0;
            step = 1;
        }

        int idx = startIdx;

        // could remove more than one element, note the while loop
        while (idx >= 0 && idx < buildings.length) {
            int buildingHeight = buildings[idx];

            // if the next building is higher than the previous building then remove
            // previous building from stack
            while (candidateIndices.size() > 0 &&
            buildings[candidateIndices.get(candidateIndices.size() - 1)] <= buildingHeight) {
                candidateIndices.remove(candidateIndices.size() - 1);
            }

            // add building to stack
            candidateIndices.add(idx);
            idx += step;
        }

        if (direction.equals("WEST")) Collections.reverse(candidateIndices);

        return candidateIndices;
    }
}
