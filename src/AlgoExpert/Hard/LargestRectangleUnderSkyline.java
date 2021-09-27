package AlgoExpert.Hard;
import java.util.*;

public class LargestRectangleUnderSkyline {
    // O(n^2) time | O(1) space where n is the number of buildings
    int largestRectange(ArrayList<Integer> buildings) {
        int maxArea = 0;
        for (int pillarIdx = 0; pillarIdx < buildings.size(); pillarIdx++) {
            int curHeight = buildings.get(pillarIdx);

            int furthestLeft = pillarIdx;
            while (furthestLeft > 0 && buildings.get(furthestLeft - 1) >= curHeight) {
                furthestLeft--;
            }

            int furthestRight = pillarIdx;
            while (furthestRight < buildings.size() - 1 &&
                    buildings.get(furthestRight + 1) >= curHeight) {
                furthestRight++;
            }

            int areaWithCurBuilding = (furthestRight - furthestLeft + 1) * curHeight;
            maxArea = Math.max(areaWithCurBuilding, maxArea);
        }
        return maxArea;
    }

    // O(n) time | O(n) space
    int largestRectangle2(ArrayList<Integer> buildings) {
        Stack<Integer> pillarIndices = new Stack<>();
        int maxArea = 0;

        ArrayList<Integer> extendedBuildings = new ArrayList<>();
        extendedBuildings.add(0);
        for (int idx = 0; idx < extendedBuildings.size(); idx++) {
            int height = extendedBuildings.get(idx);
            while (!pillarIndices.isEmpty() &&
            extendedBuildings.get(pillarIndices.peek()) >= height) {
                int pillarHeight = extendedBuildings.get(pillarIndices.pop());
                int width = (pillarIndices.isEmpty()) ? idx : idx - pillarIndices.peek() - 1;
                maxArea = Math.max(width * pillarHeight, maxArea);
            }
            pillarIndices.push(idx);
        }
        return maxArea;
    }

}
