package AlgoExpert.Hard;
import java.util.*;
public class DiskStacking {
    // O(n^2) time | O(n) space

    /**
     * sort by height first for the disks
     * currentDisk = array[i] for 0 <= i < length
     * prevDisk = array[j] for 0 <= j < i
     * if width(previous) < width(current) and depth(previous) < depth(current) and height (prev) < height(current)
     *     height[i] = max(heights[i], height(current) + heights[j])
     * Use array sequence to keep track of the previous disks used to stack up
     * the highest height
     * Also need to keep track of the index of max height at any one point
     */
    private static List<Integer[]> diskStacking(List<Integer[]> disks) {
        // sort by height
        // width, depth, height
        disks.sort((disk1, disk2) -> disk1[2].compareTo(disk2[2]));

        // init maxHeight array with heights from array
        int[] maxHeightArray = new int[disks.size()];
        for (int i = 0; i < disks.size(); i++) {
            maxHeightArray[i] = disks.get(i)[2];
        }

        // init sequences to MIN_VALUE
        int[] prevIdxWithMaxHeight = new int[disks.size()];
        for (int i = 0; i < disks.size(); i++) {
            prevIdxWithMaxHeight[i] = Integer.MIN_VALUE;
        }

        int maxHeightPrevIdx = 0;
        // update maxHeightIdx in outer loop, update maxHeightArray in inner loop
        for (int index = 1; index < disks.size(); index++) {
            Integer[] curDisk = disks.get(index);
            for (int prevIndex = 0; prevIndex < index; prevIndex++) {
                Integer[] prevDisk = disks.get(prevIndex);
                // for each disk before the current disk in list, check if they are smaller than current disk
                if (isPrevDiskSmallerThanCurDisk(prevDisk, curDisk)) {
                    // and if cur disk height + prev max height > current index max height
                    if (curDisk[2] + maxHeightArray[prevIndex] >= maxHeightArray[index]) {
                        // update the maxHeight array with the current disk height + the max height from prev disk
                        maxHeightArray[index] = curDisk[2] + maxHeightArray[prevIndex];
                        // keep track of the previous index that stacks up to the current height
                        prevIdxWithMaxHeight[index] = prevIndex;
                    }
                }
            }
            // update maxHeightIdx
            if (maxHeightArray[index] >= maxHeightArray[maxHeightPrevIdx]) {
                maxHeightPrevIdx = index;
            }
        }

        return buildSequence(disks, prevIdxWithMaxHeight, maxHeightPrevIdx);
    }

    private static boolean isPrevDiskSmallerThanCurDisk(Integer[] prevDisk, Integer[] curDisk) {
        return prevDisk[0] < curDisk[0] &&
                prevDisk[1] < curDisk[1] &&
                prevDisk[2] < curDisk[2];
    }

    // use sequences array to go backwards to find all the indices of disks
    private static List<Integer[]> buildSequence(List<Integer[]> disks,
                                                 int[] sequences,
                                                 int lastIdxWithMaxSum) {
        List<Integer[]> diskSequence = new ArrayList<>();
        while (lastIdxWithMaxSum != Integer.MIN_VALUE) {
            // add disk to 0th index
            diskSequence.add(0, disks.get(lastIdxWithMaxSum));
            lastIdxWithMaxSum = sequences[lastIdxWithMaxSum];
        }
        // [bottomDisk, secondBottomDisk, thirdBottomDisk ... topDisk]
        return diskSequence;
    }
}
