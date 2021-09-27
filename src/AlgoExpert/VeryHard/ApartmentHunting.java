package AlgoExpert.VeryHard;
import java.util.*;

public class ApartmentHunting {
    // O(b^2*r) time | O(b) space where b is the number of blocks
    // and r is the number of requirements
    public int apartmentHunting(List<Map<String, Boolean>> blocks, String[] reqs) {
        int[] maxDistancesToClosestReq = new int[blocks.size()];
        Arrays.fill(maxDistancesToClosestReq, Integer.MIN_VALUE);

        for (int curIdx = 0; curIdx < blocks.size(); curIdx++) {
            for (String req : reqs) {
                int closestDistanceToCurReq = Integer.MAX_VALUE;
                for (int otherIdx = 0; otherIdx < blocks.size(); otherIdx++) {
                    if (blocks.get(otherIdx).get(req)) { // if cur req exists for cur block
                        closestDistanceToCurReq = Math.min(closestDistanceToCurReq,
                                                            distanceBetween(curIdx, otherIdx));
                    }
                }
                // which closest building is furthest away from current location?
                maxDistancesToClosestReq[curIdx] = Math.max(maxDistancesToClosestReq[curIdx],
                                                            closestDistanceToCurReq);
            }
        }

        return getIdxOfMinValue(maxDistancesToClosestReq);
    }

    private int getIdxOfMinValue(int[] array) {
        int idxAtMinValue = 0;
        int minValue = Integer.MAX_VALUE;

        for (int i = 0; i < array.length; i++) {
            int currentValue = array[i];
            if (currentValue < minValue) {
                minValue = currentValue;
                idxAtMinValue = i;
            }
        }
        return idxAtMinValue;
    }

    int distanceBetween(int a, int b) {
        return Math.abs(a - b);
    }

    // O(br) time | O(br) space - where b is the number of blocks and
    // r is the number of requirements
    public int apartmentHunting2(List<Map<String, Boolean>> blocks, String[] reqs) {
        // array dims = req.length x blocks.size()
        int[][] minDistancesFromBlocks = new int[reqs.length][];
        for (int reqIdx = 0; reqIdx < reqs.length; reqIdx++) {
            // precompute the minDistances to all requirements
            minDistancesFromBlocks[reqIdx] = getMinDistancesForCurReq(blocks, reqs[reqIdx]); // return array of size blocks.size()
        }

        int[] maxDistancesAtBlocks = getMaxDistancesFromClosestBlocks(blocks, minDistancesFromBlocks, reqs);
        return getIdxOfMinValue(maxDistancesAtBlocks);
    }

    private int[] getMaxDistancesFromClosestBlocks(List<Map<String, Boolean>> blocks,
                                                   int[][] minDistancesFromBlocks,
                                                   String[] reqs) {
        int[] maxDistancesAtBlocks = new int[blocks.size()];
        // [0, 0, 1, 1, 0] gym
        // [2, 3, 1, 0, 0] school
        // [0, 4, 1, 1, 0] store
        // idx 0 -> [0, 2, 0]
        // idx 1 -> [0, 3, 4]
        for (int blockIdx = 0; blockIdx < blocks.size(); blockIdx++) {
            int[] minDistancesAtCurBlock = new int[minDistancesFromBlocks.length];
            for (int reqIdx = 0; reqIdx < reqs.length; reqIdx++) {
                minDistancesAtCurBlock[reqIdx] = minDistancesFromBlocks[reqIdx][blockIdx]; // req in rows, block in cols
            }
            maxDistancesAtBlocks[blockIdx] = arrayMax(minDistancesAtCurBlock);
        }
        return maxDistancesAtBlocks;
    }

    private int arrayMax(int[] array) {
        int max = array[0];
        for (int a : array) {
            if (a > max) max = a;
        }
        return max;
    }

    // returns array of size blocks.size()
    private int[] getMinDistancesForCurReq(List<Map<String, Boolean>> blocks, String req) {
        int[] minDistances = new int[blocks.size()];
        int closestReqIdx = Integer.MAX_VALUE;
        // [gym, gym, store, school, gym] min distance for gym
        // [0,   0,   1,     2,      0] forward (only looks at elements before)
        // [0,   0,   1,     1,      0] backward revision with min() (only looks at elements after)
        // iterate from left to right
        for (int forwardIdx = 0; forwardIdx < blocks.size(); forwardIdx++) {
            if (blocks.get(forwardIdx).get(req)) // if current block contains the requirement then it is the closest
                closestReqIdx = forwardIdx;
            // calculate the min distance to the last closestReqIdx
            minDistances[forwardIdx] = distanceBetween(forwardIdx, closestReqIdx);
        }

        for (int backwardIdx = blocks.size() - 1; backwardIdx >= 0; backwardIdx--) {
            if (blocks.get(backwardIdx).get(req))
                closestReqIdx = backwardIdx;
            minDistances[backwardIdx] = Math.min(minDistances[backwardIdx],
                                                distanceBetween(backwardIdx, closestReqIdx));
        }
        return minDistances;
    }
}
