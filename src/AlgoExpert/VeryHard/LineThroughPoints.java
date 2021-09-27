package AlgoExpert.VeryHard;

import java.util.HashMap;
import java.util.Map;

public class LineThroughPoints {
    // O(n^2) time | O(n) space where n is the number of points
    public int lineThroughPoints(int[][] points) {
        // we will be given at least one point
        int maxNumberOfPointsOnLine = 1;

        for (int curIdx = 0; curIdx < points.length; curIdx++) {
            int[] p1 = points[curIdx];
            // for every curIdx we have a new HashMap so that p1 (curIdx stays constant and nextIdx varies)
            // this way we know that the lines with the same slope are all the same line
            HashMap<String, Integer> slopesMap = new HashMap<>();
            // because of rounding error with double precision math we need to use a string as the key
            // and represent the decimal number using a fraction (numerator, denominator)

            for (int nextIdx = curIdx + 1; nextIdx < points.length; nextIdx++) {
                int[] p2 = points[nextIdx];
                // get slope of line via rise over run (numerator and denominator)
                int[] slopeOfLineBetweenPoints = getSlopeOfLineBetweenPoints(p1, p2);
                int rise = slopeOfLineBetweenPoints[0];
                int run = slopeOfLineBetweenPoints[1];

                // slope key is format "numerator:denominator"
                String slopeKey = createHashableKeyForRational(rise, run);
                // for the first two points in the map add 2 pts for the slope
                if (!slopesMap.containsKey(slopeKey)) {
                    slopesMap.put(slopeKey, 1);
                }
                slopesMap.put(slopeKey, slopesMap.get(slopeKey) + 1);
            }

            int currentMaxNumberOfPointsOnLine = maxSlope(slopesMap);
            maxNumberOfPointsOnLine = Math.max(maxNumberOfPointsOnLine,
                                               currentMaxNumberOfPointsOnLine);
        }

        return maxNumberOfPointsOnLine;
    }

    private int maxSlope(HashMap<String, Integer> slopes) {
        int curMax = 0;
        // for each map entry in the map, find the largest number of points with the same slope
        for (Map.Entry<String, Integer> slope : slopes.entrySet()) {
            curMax = Math.max(slope.getValue(), curMax);
        }
        return curMax;
    }

    private String createHashableKeyForRational(int numerator, int denominator) {
        return String.valueOf(numerator) + ":" + String.valueOf(denominator);
    }

    private int[] getSlopeOfLineBetweenPoints(int[] p1, int[] p2) {
        int p1x = p1[0];
        int p1y = p1[1];
        int p2x = p2[0];
        int p2y = p2[1];

        // yDiff, xDiff
        int[] slope = new int[] {1, 0}; // slope of a vertical line (1/0)

        if (p1x != p2x) { // if line is not vertical
            int xDiff = p1x - p2x;
            int yDiff = p1y - p2y;
            // function only works for positive values so need to use abs()
            int gcd = getGreatestCommonDenominator(Math.abs(xDiff), Math.abs(yDiff));
            xDiff = xDiff / gcd;
            yDiff = yDiff / gcd;
            if (xDiff < 0) { // get rid of negative num and negative denom, also moves negative denom to the numerator
                xDiff *= -1;
                yDiff *= -1;
            }
            slope = new int[] {yDiff, xDiff};
        }
        return slope;
    }

    //TODO how does this work?
    private int getGreatestCommonDenominator(int num1, int num2) {
        int a = num1;
        int b = num2;
        while (true) {
            if (a == 0) return b;
            if (b == 0) return a;
            int temp = a;
            a = b;
            b = temp % b;
        }
    }
}
