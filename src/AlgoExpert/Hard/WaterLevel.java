package AlgoExpert.Hard;

public class WaterLevel {
    // O(n) time | O(n) space

    /**
     * for each index starting from left, find the highest pillar to the left of the index
     * for each index starting from right, find the highest pillar to the right of the index
     */
    private static int waterArea(int[] heights) {
        int[] combinedMaxes = new int[heights.length];
        // first array approach from the left and take the max
        int leftMax = 0;
        // first index is always 0, then take max
        for (int i = 0; i < heights.length; i++) {
            int heightFromLeft = heights[i];
            combinedMaxes[i] = leftMax;
            leftMax = Math.max(leftMax, heightFromLeft);
        }

        // second array approach from the right and take the min
        int rightMax = 0;
        for (int i = heights.length - 1; i >= 0; i--) {
            int heightFromRight = heights[i];
            // minHeight = min(leftMax, rightMax)
            int minHeight = Math.min(rightMax, combinedMaxes[i]);
            // third array
            if (heightFromRight < minHeight) {
                // we can store water here since cur height is less than the min height
                combinedMaxes[i] = minHeight - heightFromRight;
            } else {
                // no water there
                combinedMaxes[i] = 0;
            }
            rightMax = Math.max(rightMax, heightFromRight);
        }

        int total = 0;
        for (int i = 0; i < heights.length; i++) {
            total+= combinedMaxes[i];
        }
        return total;
    }

    // O(n) time | O(1) space
    /**
     * keep running max for the left side and right side
     * if the current pillar height is smaller than the running max then
     * calculate the left side surface area = maxHeight - curHeight
     * else calculate the right side surface area
     */
    private static int waterArea2(int[] heights) {
        if (heights.length == 0) return 0;

        int leftIdx = 0;
        int rightIdx = heights.length - 1;
        int runningLeftMax = heights[leftIdx];
        int runningRightMax = heights[rightIdx];
        int surfaceArea = 0;

        while (leftIdx < rightIdx) {
            // pushing smaller heights to the right
            if (heights[leftIdx] < heights[rightIdx]) {
                leftIdx ++;
                // take the max between max pillar height
                // before the index and the cur index
                runningLeftMax = Math.max(runningLeftMax, heights[leftIdx]);
                // leftMax - cur Height
                surfaceArea += runningLeftMax - heights[leftIdx];
            } else {  // pushing higher heights to the left
                rightIdx --;
                // take the max between max pillar height
                // before the index and the cur index
                runningRightMax = Math.max(runningRightMax, heights[rightIdx]);
                // rightMax - cur Height
                surfaceArea += runningRightMax - heights[rightIdx];
            }
        }
        return surfaceArea;
    }

}
