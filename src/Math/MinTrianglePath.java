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
        for (int index = 0; index < input.get(height-1).size(); index++) {
            outBuffer[index] = input.get(height-1).get(index);
        }

        // move up the triangle
        for (int r = height-2; r>=0; r--) {
            ArrayList<Integer> row = input.get(r);
            for (int index = 0; index < row.size(); index++) {
                outBuffer[index] = row.get(index) + Math.min(outBuffer[index], outBuffer[index+1]);
            }
            System.out.println("for r = " + r + " outBuffer = " + Arrays.toString(outBuffer));
        }
        return outBuffer[0];
    }
}
