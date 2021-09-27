package AlgoExpert.VeryHard;
import java.util.*;

public class RectangleMania {
    String UP = "up";
    String RIGHT = "right";
    String DOWN = "down";
    String LEFT = "left";
    
    final int yPoint = 1;
    final int xPoint = 0;

    // O(n^2) time | O(n^2) space where n is the number of coordinates
    //DFS method
    int rectangleMania(List<Integer[]> coords) {
        // treat each coordinate as the lower left corner of the rectangle
        Map<String, Map<String, List<Integer[]>>> coordsTable = getCoordsTable(coords);
        return getRectangleCount(coords, coordsTable);
    }

    private Map<String, Map<String, List<Integer[]>>> getCoordsTable(List<Integer[]> coords) {
        Map<String, Map<String, List<Integer[]>>> coordsTable = new HashMap<>();
        // for each coordinate, treat it as the left bottom corner of rectangle
        // and try to find the pattern, up, right, down and left
        for (Integer[] eachCoord : coords) {
            Map<String, List<Integer[]>> eachCoordDirections = new HashMap<>();
            // starting from lower left corner, move up, right, down and left to find the points
            eachCoordDirections.put(UP, new ArrayList<>()); // array of coordinates
            eachCoordDirections.put(RIGHT, new ArrayList<>());
            eachCoordDirections.put(DOWN, new ArrayList<>());
            eachCoordDirections.put(LEFT, new ArrayList<>());
            for (Integer[] everyOtherCoord : coords) {
                // getCoordDirection returns empty string if everyOtherCoord == eachCoord
                String everyOtherCoordDirection = getCoordDirection(eachCoord, everyOtherCoord);
                if (eachCoordDirections.containsKey(everyOtherCoordDirection))
                    eachCoordDirections.get(everyOtherCoordDirection).add(everyOtherCoord);
            }
            String eachCoordString = coordToString(eachCoord);
            coordsTable.put(eachCoordString, eachCoordDirections);
        }
        return coordsTable;
    }

    private String getCoordDirection(Integer[] coord1, Integer[] coord2) {
        if (coord2[yPoint] == coord1[yPoint]) {
            if (coord2[xPoint] > coord1[xPoint]) {
                return RIGHT;
            } else if (coord2[xPoint] < coord1[xPoint]) {
                return LEFT;
            }
        } else if (coord2[xPoint] == coord1[xPoint]) {
            if (coord2[yPoint] > coord1[yPoint]) {
                return UP;
            } else if (coord2[yPoint] < coord1[yPoint]) {
                return DOWN;
            }
        }
        return "";
    }

    private int getRectangleCount(List<Integer[]> coords,
                                  Map<String, Map<String, List<Integer[]>>> coordsTable) {
        int rectangleCount = 0;
        for (Integer[] coord : coords) {
            rectangleCount += countRectanglesUsingClockwiseTraversalRecurse(coord, coordsTable, UP, coord);
        }
        return rectangleCount;
    }

    private int countRectanglesUsingClockwiseTraversalRecurse(Integer[] coord,
                                                              Map<String, Map<String, List<Integer[]>>> coordsTable,
                                                              String direction,
                                                              Integer[] origin) {
        String coordString = coordToString(coord);
        if (direction == LEFT) { // if bottom right coordinate of rectangle
            boolean rectangleFound = coordsTable.get(coordString).get(LEFT).contains(origin);
            return rectangleFound ? 1 : 0;
        } else {
            int rectangleCount = 0;
            String nextDirection = getNextClockwiseDirection(direction);
            for (Integer[] nextCoord : coordsTable.get(coordString).get(direction)) {
                rectangleCount += countRectanglesUsingClockwiseTraversalRecurse(nextCoord, coordsTable, nextDirection, origin);
            }
            return rectangleCount;
        }
    }

    private String getNextClockwiseDirection(String direction) {
        if (direction == UP) return RIGHT;
        if (direction == RIGHT) return DOWN;
        if (direction == DOWN) return LEFT;
        return "";
    }

    private String coordToString(Integer[] coord1) {
        return Integer.toString(coord1[xPoint]) + "-" + Integer.toString(coord1[yPoint]);
    }

    // O(n^2) time | O(n) space - where n is the number of coordinates
    int rectangleMania2(List<Integer[]> coords) {
        Map<String, Map<Integer, List<Integer[]>>> coordsTable = getCoordsTable2(coords);
        return getRectangleCount2(coords, coordsTable);
    }

    private int getRectangleCount2(List<Integer[]> coords,
                                   Map<String, Map<Integer,
                                           List<Integer[]>>> coordsTable) {
        int rectangleCount = 0;
        for (Integer[] coord : coords) {
            int lowerLeftY = coord[yPoint];
            rectangleCount += countRectangleUsingBLAndTRCornerRecurse(coord, coordsTable, UP, lowerLeftY);
        }
        return rectangleCount;
    }

    // given bottom left corner and top right corner of a rectangle,
    // it's easy to find the top left and bottom right coordinates
    // this way we avoid the clockwise traversal for coordinates
    private int countRectangleUsingBLAndTRCornerRecurse(Integer[] givenCoord,
                                                        Map<String, Map<Integer, List<Integer[]>>> coordsTable,
                                                        String direction,
                                                        int lowerLeftY) {
        if (direction == DOWN) {
            List<Integer[]> relevantCoords = coordsTable.get("x").get(givenCoord[xPoint]);
            for (Integer[] eachRelevantCoord : relevantCoords) {
                int lowerRightY = eachRelevantCoord[yPoint];
                if (lowerRightY == lowerLeftY) return 1;
            }
            return 0;
        } else {
            int rectangleCount = 0;
            if (direction == UP) {
                List<Integer[]> relevantCoords = coordsTable.get("x").get(givenCoord[xPoint]);
                for (Integer[] eachRelevantCoord : relevantCoords) {
                    boolean isAbove = eachRelevantCoord[yPoint] > givenCoord[yPoint];
                    if (isAbove) {
                        rectangleCount += countRectangleUsingBLAndTRCornerRecurse(eachRelevantCoord,
                                                                                coordsTable,
                                                                                RIGHT,
                                                                                lowerLeftY);
                    }
                }
            } else if (direction == RIGHT) {
                List<Integer[]> relevantCoords = coordsTable.get("y").get(givenCoord[yPoint]);
                for (Integer[] eachRelevantCoord : relevantCoords) {
                    boolean isRight = eachRelevantCoord[xPoint] > givenCoord[xPoint];
                    if (isRight) {
                        rectangleCount += countRectangleUsingBLAndTRCornerRecurse(eachRelevantCoord,
                                                                                coordsTable,
                                                                                DOWN,
                                                                                lowerLeftY);
                    }
                }
            }
            return rectangleCount;
        }
    }

    Map<String, Map<Integer, List<Integer[]>>> getCoordsTable2(List<Integer[]> coords) {
        Map<String, Map<Integer, List<Integer[]>>> coordsTable = new HashMap<>();
        coordsTable.put("x", new HashMap<>()); // store all coordinates given an x value
        coordsTable.put("y", new HashMap<>()); // store all coordiantes given an y value

        for (Integer[] coord : coords) {
            if (!coordsTable.get("x").containsKey(coord[xPoint])) {
                coordsTable.get("x").put(coord[xPoint], new ArrayList<>());
            }
            if (!coordsTable.get("y").containsKey(coord[yPoint])) {
                coordsTable.get("y").put(coord[yPoint], new ArrayList<>());
            }
            coordsTable.get("x").get(coord[xPoint]).add(coord);
            coordsTable.get("y").get(coord[yPoint]).add(coord);
        }
        return coordsTable;
    }

    // O(n^2) time | O(n) space where n is the number of coordinates
    int rectangleMania3(List<Integer[]> coords) {
        Set<String> coordsTable = getCoordsTable3(coords);
        return getRectangleCount3(coords, coordsTable);

    }

    private int getRectangleCount3(List<Integer[]> coords, Set<String> coordsTable) {
        int rectangleCount = 0;
        for (Integer[] eachCoord : coords) {
            for (Integer[] everyOtherCoord : coords) {
                if (!isInUpperRight(eachCoord, everyOtherCoord)) continue;
                String upperLeftCoordString = coordToString2(new Integer[]{eachCoord[xPoint], everyOtherCoord[yPoint]});
                String lowerRightCoordString = coordToString2(new Integer[]{everyOtherCoord[xPoint], eachCoord[yPoint]});
                if (coordsTable.contains(upperLeftCoordString) && coordsTable.contains(lowerRightCoordString))
                    rectangleCount++;
            }
        }
        return rectangleCount;
    }

    private String coordToString2(Integer[] coord) {
        return Integer.toString(coord[xPoint]) + "-" + Integer.toString(coord[yPoint]);
    }

    private boolean isInUpperRight(Integer[] coord1, Integer[] coord2) {
        return coord2[xPoint] > coord1[xPoint] && coord2[yPoint] > coord1[yPoint];
    }

    private Set<String> getCoordsTable3(List<Integer[]> coords) {
        Set<String> coordsTable = new HashSet<>();
        for (Integer[] coord : coords) {
            String coordString = coordToString(coord);
            coordsTable.add(coordString);
        }
        return coordsTable;
    }
}
