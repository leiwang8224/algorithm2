package Math;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Given a 'triangle' as an ArrayList of ArrayLists of integers,
 * with each list representing a level of the triangle, find the
 * minimum sum achieved by following a top-down path and adding
 * the integer at each level along the path. Movement is restricted
 * to adjacent numbers from the top to the bottom.
 *
 * - You can only traverse through adjacent nodes while moving up or down the triangle.
 * - An adjacent node is defined as a node that is reached by moving down and left or
 * down and right from a level. For eg, in the triangle shown below, if you are at the
 * digit 3 in the second row, its adjacent nodes are 5 and 6
 */
public class MinTrianglePath {
    public static void main(String[] args) {
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        ArrayList<Integer> list3 = new ArrayList<>();
        ArrayList<Integer> list4 = new ArrayList<>();

        list1.add(1);
        list2.add(2);
        list2.add(3);
        list3.add(4);
        list3.add(5);
        list3.add(6);
        list4.add(7);
        list4.add(8);
        list4.add(9);
        list4.add(10);

        ArrayList<Integer> list5 = new ArrayList<>();
        ArrayList<Integer> list6 = new ArrayList<>();
        ArrayList<Integer> list7 = new ArrayList<>();
        ArrayList<Integer> list8 = new ArrayList<>();

        list5.add(1);
        list6.add(1);
        list6.add(0);
        list7.add(1);
        list7.add(2);
        list7.add(3);
        list8.add(7);
        list8.add(2);
        list8.add(3);
        list8.add(1);
        //
        //     [1],
        //   [2, 3],
        //  [4, 5, 6],
        //[7, 8, 9, 10],

        ArrayList<ArrayList<Integer>> inputArray = new ArrayList<>();
        inputArray.add(list1);
        inputArray.add(list2);
        inputArray.add(list3);
        inputArray.add(list4);

        System.out.println(minTriangleDepth(inputArray));

        //         1
        //        1 0
        //       1 2 3
        //      7 2 3 1
        inputArray.clear();
        inputArray.add(list5);
        inputArray.add(list6);
        inputArray.add(list7);
        inputArray.add(list8);

        System.out.println(minTriangleDepth(inputArray));
    }
//
//    Use a buffer array that is the length of the base of the triangle.
//    Fill this buffer array with the last row of the triangle. Now, move up the triangle,
//    and at each level, iterate across the level and add to the base buffer the minimum
//    sum of the element directly above. Finally, return the first element of the buffer.
    private static int minTriangleDepth(ArrayList<ArrayList<Integer>> input) {
        int height = input.size();
        int[] outBuffer = new int[input.get(height-1).size()];

        // init with the values from the bottom of the triangle
        // note that the last index will not get updated as we traverse
        // upwards in the triangle. Therefore the first element will
        // always be the final answer
        for (int index = 0; index < input.get(height-1).size(); index++) {
            outBuffer[index] = input.get(height-1).get(index);
        }

        // move up the triangle and overwrite outBuffer with the min of the row
        // below the current row added on top of current row number of interest
        for (int r = height-2; r>=0; r--) {
            ArrayList<Integer> row = input.get(r);
            for (int index = 0; index < row.size(); index++) {
                // take min of output buffer indices next to each other
                // and add to current row with index
                System.out.println("set index " + index + " with min of " + outBuffer[index] + " and " + outBuffer[index+1]);
                outBuffer[index] = row.get(index) + Math.min(outBuffer[index], outBuffer[index+1]);
            }
            System.out.println("for r = " + r + " outBuffer = " + Arrays.toString(outBuffer));
        }

        // the leftmost value is the smallest value
        return outBuffer[0];
    }
}
