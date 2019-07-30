package Math;

//        There are 8 prison cells in a row, and each cell is
//        either occupied or vacant.
//
//        Each day, whether the cell is occupied or vacant changes
//        according to the following rules:
//
//        If a cell has two adjacent neighbors that are both occupied
//        or both vacant, then the cell becomes occupied.
//        Otherwise, it becomes vacant.
//
//        (Note that because the prison is a row, the first and the
//        last cells in the row can't have two adjacent neighbors.)
//
//        We describe the current state of the prison in the following way:
//        cells[i] == 1 if the i-th cell is occupied, else cells[i] == 0.
//
//        Given the initial state of the prison, return the state of the
//        prison after N days (and N such changes described above.)
//
//
//
//        Example 1:
//
//        Input: cells = [0,1,0,1,1,0,0,1], N = 7
//        Output: [0,0,1,1,0,0,0,0]
//        Explanation:
//        The following table summarizes the state of the prison on each day:
//        Day 0: [0, 1, 0, 1, 1, 0, 0, 1]
//        Day 1: [0, 1, 1, 0, 0, 0, 0, 0]
//        Day 2: [0, 0, 0, 0, 1, 1, 1, 0]
//        Day 3: [0, 1, 1, 0, 0, 1, 0, 0]
//        Day 4: [0, 0, 0, 0, 0, 1, 0, 0]
//        Day 5: [0, 1, 1, 1, 0, 1, 0, 0]
//        Day 6: [0, 0, 1, 0, 1, 1, 0, 0]
//        Day 7: [0, 0, 1, 1, 0, 0, 0, 0]
//
//        Example 2:
//
//        Input: cells = [1,0,0,1,0,0,1,0], N = 1000000000
//        Output: [0,0,1,1,1,1,1,0]

import java.util.Arrays;
import java.util.HashSet;

public class PrisonAfterNDays {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(prisonAfterNDays(new int[]{0,1,0,1,1,0,0,1}, 7)));
        System.out.println(Arrays.toString(prisonAfterNDaysBruteForce(new int[]{0,1,0,1,1,0,0,1}, 7)));
    }

    private static int[] prisonAfterNDaysBruteForce(int[] cells, int N) {
        for (int days = 0; days < N; days++) {
            int[] newCells = new int[cells.length];
            for (int index = 1; index < cells.length-1; index++) {
                newCells[index] = cells[index-1] == cells[index+1] ? 1 : 0;
            }
            for (int index = 0; index < cells.length; index++) {
                cells[index] = newCells[index];
            }
        }

        return cells;
    }

    /**
     * The idea here is to find the cycles that occurs as days goes on
     * @param cells
     * @param N
     * @return
     */
    private static int[] prisonAfterNDays(int[] cells, int N) {
        if (cells == null || cells.length == 0 || N <= 0) return cells;
        boolean hasCycle = false;
        int numCycles = 0;
        HashSet<String> set = new HashSet<>();

        for (int index = 0; index < N; index++) {
            int[] next = nextDay(cells);
            // convert array into string and store in set (cache of stored previous days)
            String key = Arrays.toString(next);
            // if set does not have the key, add key and increment cycles
            if (!set.contains(key)) {
                set.add(key);
                numCycles++;
            } else {
                // else key does not exist and cycle is found
                hasCycle = true;
                break;
            }
            cells = next;
        }

        // once a cycle is found, all we need to do is to iterate
        // the remainder of the days from division
        if (hasCycle) {
            N %= numCycles;
            for (int index = 0; index < N; index++) {
                cells = nextDay(cells);
            }
        }
        return cells;
    }

    private static int[] nextDay(int[] cells) {
        int[] temp = new int[cells.length];
        for(int index = 1; index < cells.length - 1; index++) {
            temp[index] = cells[index-1] == cells[index+1] ? 1 : 0;
        }

        return temp;
    }
}
