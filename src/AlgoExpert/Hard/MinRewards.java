package AlgoExpert.Hard;
import java.util.stream.*;
import java.util.*;

public class MinRewards {
    // O(n ^2) time | O(n) space
    private static int minRewards(int[] scores) {
        int[] rewards = new int[scores.length];
        Arrays.fill(rewards, 1);
        for (int scoresIdx = 1; scoresIdx < scores.length; scoresIdx++) {
            int prevScoresIdx = scoresIdx - 1;
            // when increasing just
            // increment by 1 starting from prev idx (bottom of v)
            if (scores[scoresIdx] > scores[prevScoresIdx]) {
                rewards[scoresIdx] = rewards[prevScoresIdx] + 1;
            } else {
                // for the downhill trend,
                // go back and fix the scores before scoresIdx
                while (prevScoresIdx >= 0 &&
                        scores[prevScoresIdx] > scores[prevScoresIdx+1]) {
                    // take the max, we may not want to fix it if it's large enough
                    rewards[prevScoresIdx] = Math.max(rewards[prevScoresIdx],
                                                        rewards[prevScoresIdx+1] + 1);
                    prevScoresIdx--;
                }
            }
        }
        return IntStream.of(rewards).sum();
    }

    // O(n) time | O(n) space

    /**
     * get indices of the local minimums
     * for each local min expand from local min idx to get rewards
     * - for going left take the max of reward at cur id x and reward at (cur idx + 1) + 1
     * - for going right simply add 1 to the previous reward
     * take sum of the rewards array
     */
    private static int minRewardsExpandFromMin(int[] scores) {
        int[] rewards = new int[scores.length];
        Arrays.fill(rewards, 1);
        // get all local min locations
        List<Integer> localMinIdxs = getLocalMinIdxs(scores);
        // expand from local mins on both sides
        for (Integer localMinIdx : localMinIdxs) {
            expandFromLocalMinIdx(localMinIdx, scores, rewards);
        }
        return IntStream.of(rewards).sum();
    }

    private static void expandFromLocalMinIdx(Integer localMinIdx,
                                              int[] scores,
                                              int[] rewards) {
        int leftIdx = localMinIdx - 1;
        // increasing trend to the left
        // going backwards so we might be overwriting the array, use max of two indices
        while (leftIdx >= 0 && scores[leftIdx] > scores[leftIdx + 1]) {
            // only to the left when we might be overwriting the rewards array
            rewards[leftIdx] = Math.max(rewards[leftIdx], rewards[leftIdx + 1] + 1);
            leftIdx --;
        }
        int rightIdx = localMinIdx + 1;
        // increasing trend to the right
        while (rightIdx < scores.length && scores[rightIdx] > scores[rightIdx - 1]) {
            // to the right all the numbers in rewards are 1's
            rewards[rightIdx] = rewards[rightIdx - 1] + 1;
            rightIdx++;
        }
    }

    private static List<Integer> getLocalMinIdxs(int[] array) {
        List<Integer> localMinIdxs = new ArrayList<>();
        if (array.length == 1) {
            localMinIdxs.add(0);
            return localMinIdxs;
        }
        
        for (int i = 0; i < array.length; i++) {
            // corner cases for both sides of array
            // first index is the min
            if (i == 0 && array[i] < array[i + 1])
                localMinIdxs.add(i);
            // last index is the min
            if (i == array.length - 1 && array[i] < array[i-1])
                localMinIdxs.add(i);
            // if above 2 cases are not met but we are still at 0 or end of array
            if (i == 0 || i == array.length - 1)
                continue;
            // if we are at the min
            if (array[i] < array[i+1] && array[i] < array[i - 1])
                localMinIdxs.add(i);
        }
        return localMinIdxs;
    }

    // O(n) time | O(n) space
    /**
     * iterate forward and then backwards
     * iterate forward from the second index to only check the element before
     * - add 1 on top of previous element
     * iterate backward from the second to last index to only check the element after
     * - take max of cur element and the element after + 1
     */
    private static int minRewards3(int[] scores) {
        int[] rewards = new int[scores.length];
        Arrays.fill(rewards, 1);
        // start from first index
        for (int forwardIdx = 1; forwardIdx < scores.length; forwardIdx++) {
            // inc trend so add 1
            if (scores[forwardIdx] > scores[forwardIdx-1])
                rewards[forwardIdx] = rewards[forwardIdx-1] + 1;
        }
        // start from last index - 1
        for (int backwardIdx = scores.length - 2; backwardIdx >= 0; backwardIdx--) {
            // going backwards and inc trend
            if (scores[backwardIdx] > scores[backwardIdx+1]) {
                // replace existing rewards with new ones
                rewards[backwardIdx] = Math.max(rewards[backwardIdx],
                                                rewards[backwardIdx+1] + 1);
            }
        }
        return IntStream.of(rewards).sum();
    }

    /**
     * My solution
     */
    public static int minRewardsMySol(int[] scores) {
        if (scores.length == 1) return scores[0];
        int[] finalScores = new int[scores.length];
        Arrays.fill(finalScores, 1);
        List<Integer> troughPos = getTrough(scores);
        for (Integer troughIdx : troughPos) {
            int left = troughIdx - 1;
            int right = troughIdx + 1;
            while (left >= 0 && scores[left] > scores[left+1]) {
                finalScores[left] = Math.max(finalScores[left+1] + 1, finalScores[left]);
                left --;
            }
            while (right < scores.length && scores[right] > scores[right-1]) {
                finalScores[right] = finalScores[right-1] + 1;
                right++;
            }
        }
        int sum = 0;
        for (int index = 0; index < finalScores.length; index++) {
            sum += finalScores[index];
        }
        // Write your code here.
        return sum;
    }

    private static List<Integer> getTrough(int[] scores) {
        List<Integer> result = new ArrayList<>();

        for (int index = 0; index < scores.length; index++) {
            if (isTrough(scores, index)) {
                result.add(index);
            }
        }
        return result;
    }

    private static boolean isTrough(int[] array, int index) {
        if (index == 0) {
            return array[index] < array[index+1];
        }

        if (index == array.length-1) {
            return array[index] < array[index-1];
        }

        return array[index] < array[index-1] && array[index] < array[index+1];
    }
}
