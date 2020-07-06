package CTCI.RecursionAndDP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * You have a stack of n boxes, with widths w1, heights h1, and depths d1.
 * The boxes cannot be rotated and can only be stacked on top of one another
 * if each box in the stack is strictly larger than the box above it in width
 * height, and depth. Implement a method to compute the height of the tallest
 * possible stack. The height of a stack is the sum of the heights of each box.
 */
public class StackOfBoxes {
    public static void main(String[] args) {
        Box[] boxList = {new Box(6,4,4),
                         new Box(8,6,2),
                         new Box(5,3,3),
                         new Box(7,8,3),
                         new Box(4,2,2),
                         new Box(9,7,3)};

        ArrayList<Box> boxes = new ArrayList<>();
        for (Box b : boxList)
            boxes.add(b);
        int height = createStack(boxes);
        System.out.println(height);
    }

    /**
     * Imagine we had the following boxes: b1, b2, ..., bn. The biggest stack
     * that we can build with all the boxes equals the max of (biggest stack
     * with bottom b1, biggest stack with bottom b2, ... biggest stack with
     * bottom bn). That is, if we experimented with each box as a bottom and built
     * the biggest stack possible with each, we would find the biggest stack possible.
     *
     * But how would we find the biggest stack with a particular bottom? Essentially
     * the same way. We experiment with different boxes for the second level, third level, and
     * so on for each level.
     *
     * Of course, we only experiment with valid boxes. If bs is bigger than b1, then
     * there is no point in trying to build a stack that looks like {b1, bs,...}.
     * We already know b1 can't be below bs.
     *
     * We can perform a small optimization here. The requirements of this problem stipulate
     * that the lower boxes must be strictly greater than the higher boxes in all dimensions.
     * Therefore, if we sort (descending order) the boxes in a dimension - any dimension -
     * then we know we don't have to look backwards in the list. The box b1 cannot be on top
     * of box bs, since its height (or whatever dimension we sorted on) is greater than bs's
     * height.
     */

    static class Box {
        int width;
        int height;
        int depth;
        Box (int w, int h, int d) {
            width = w;
            height = h;
            depth = d;
        }

        boolean canBeUnder(Box b) {
            if (width > b.width &&
                height > b.height &&
                depth > b.depth) {
                return  true;
            }
            return false;
        }

        boolean canBeAbove(Box b) {
            if (b == null) return true;
            if (width < b.width && height < b.height && depth < b.depth)
                return true;
            return false;
        }

        public String toString() {
            return "Box(" + width + ", " + height + ", " + depth + ")";
        }
    }

    static class BoxComparator implements Comparator<Box> {

        @Override
        public int compare(Box o1, Box o2) {
            return o2.height - o1.height;
        }
    }

    private static int createStack(ArrayList<Box> boxes) {
        // first sort the boxes
        Collections.sort(boxes, new BoxComparator());
        // initialize maxHeight = 0
        int maxHeight = 0;

        // for each box, try to put it on the bottom and find overall height
        for (int boxIndice = 0; boxIndice < boxes.size(); boxIndice++) {
            // try to put different boxes on the bottom and find height
            int height = createStack(boxes, boxIndice);
            // find max height
            maxHeight = Math.max(maxHeight, height);
        }
        return maxHeight;
    }

    private static int createStack(ArrayList<Box> boxes, int bottomIndex) {
        Box bottomBox = boxes.get(bottomIndex);
        // init max height
        int maxHeight = 0;

        // iterate through boxes starting from the bottom box to the top
        for (int boxIndex = bottomIndex + 1; boxIndex < boxes.size(); boxIndex++) {
            // if current box can be above the bottom box (current box is smaller)
            // only consider if the box is smaller than the bottom box,
            // ignore the case when it's bigger (no need to worry since it will always
            // be down at the bottom)
            if (boxes.get(boxIndex).canBeAbove(bottomBox)) {
                // use the current box as the bottom box and recurse
                int height = createStack(boxes, boxIndex);
                // calculate max height
                maxHeight = Math.max(height, maxHeight);
            }
        }
        maxHeight += bottomBox.height;
        return maxHeight;
    }

    /**
     * Another more efficient way is to cache the results using memoization.
     * Because we are only mapping from an index to a height, we can just use
     * an integer array for our 'hash table'.
     *
     * Be very careful here with what each spot in the hash table represents.
     * In this code, stackMap[index] represents the tallest stack with box
     * index at the bottom. Before pulling the value from the hash table,
     * you have to ensure that box index can be placed on top of the current
     * bottom.
     *
     * It helps to keep the line that recalls from the hash table symmetric
     * with the one that inserts. For example, in this code, we recall from
     * the hash table with bottomIndex at the start of the method.
     * We insert into the hash table with bottomIndex at the end.
     */
    int createStackMemo(ArrayList<Box> boxes) {
        Collections.sort(boxes, new BoxComparator());
        int maxHeight = 0;
        // memo setup
        int[] stackMap = new int[boxes.size()];

        for (int index = 0; index < boxes.size(); index++) {
            int height = createStackMemo(boxes, index, stackMap);
            maxHeight = Math.max(maxHeight, height);
        }
        return maxHeight;
    }

    private int createStackMemo(ArrayList<Box> boxes, int bottomIndex, int[] stackMap) {
        if (bottomIndex < boxes.size() && stackMap[bottomIndex] > 0)
            return stackMap[bottomIndex];

        Box bottom = boxes.get(bottomIndex);
        int maxHeight = 0;

        for (int index = bottomIndex + 1; index < boxes.size(); index++) {
            if (boxes.get(index).canBeAbove(bottom)) {
                int height = createStackMemo(boxes, index, stackMap);
                maxHeight = Math.max(height, maxHeight);
            }
        }

        maxHeight += bottom.height;
        stackMap[bottomIndex] = maxHeight;
        return maxHeight;
    }

    /**
     * Another way of thinking about this problem is the recursive algorithm is making a choice,
     * at each step, whether to put a particular box in the stack. (We will again sort our boxes
     * in descending order by a dimension, such as height).
     *
     * First, we choose whether or not to put box 0 in the stack. Take one recursive path with box
     * 0 at the bottom and one recursive path without box 0. Return the better of the two options.
     *
     * Then, we choose whether or not to put box 1 in the stack. Take one recursive path with
     * box 1 at the bottom and one path without box 1. Return the better of the two options.
     *
     * Again, use memoization to cache the height of the tallest stack with a particular bottom.
     */
    static int createStackMemo2(ArrayList<Box> boxes) {
        Collections.sort(boxes, new BoxComparator());
        int[] stackMap = new int[boxes.size()];
        return createStackMemo2(boxes, null, 0, stackMap);
    }

    private static int createStackMemo2(ArrayList<Box> boxes, Box bottomBox, int offset, int[] stackMap) {
        if (offset >= boxes.size()) return 0; // base case

        // height with this bottom
        Box newBottom = boxes.get(offset);
        int heightWithBottom = 0;
        if (bottomBox == null || newBottom.canBeAbove(bottomBox)) {
            if (stackMap[offset] == 0) {
                stackMap[offset] = createStackMemo2(boxes, newBottom, offset+1, stackMap);
                stackMap[offset] += newBottom.height;
            }
            heightWithBottom = stackMap[offset];
        }

        // without this bottom
        int heightWithoutBottom = createStackMemo2(boxes, bottomBox, offset+1, stackMap);

        // return better of two options
        return Math.max(heightWithBottom, heightWithoutBottom);
    }

}
