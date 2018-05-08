package Recursion;

import BitManip.BitOps;

/**
 * Created by leiwang on 5/7/18.
 */
public class FindAllSubsets {
    public static void main(String[] args) {
        findAllSubsets(8,new boolean[8],0);
        findAllSubsetArrays(0,0,new int[8],8);
        findAllSubsetBitMask(0,0,8);
        findAllSubsets(8);
    }

    private static void findAllSubsets(int num, boolean[] flags, int count) {
        if (count == num) {
            for (int i = 0; i < flags.length; i ++) {
                if (flags[i])
                    System.out.print(i);
            }
            System.out.println();
            return;
        }
        // count not in the subset
        flags[count] = false;
        findAllSubsets(num, flags, count+1);
        // count in the subset
        flags[count] = true;
        findAllSubsets(num, flags, count+1);
    }

    private static void findAllSubsetArrays(int pos, int len, int[] subset, int num) {
        if (pos == num) {
            java.util.Arrays.toString(subset);
            return;
        }
        // put current element in the subset
        subset[len] = pos;
        findAllSubsetArrays(pos + 1, len + 1, subset, num);

        // skip the current element
        findAllSubsetArrays(pos + 1, len, subset, num);
    }

    private static void findAllSubsetBitMask(int pos, int mask, int num) {
        if (pos == num) {
            for (int i = 0; i < num; i ++) {
                if (bitOn(mask, i)) {
                    System.out.printf("%d ", i);
                }
            }
            System.out.println();
            return;
        }

        // skip this element
        findAllSubsetBitMask(pos + 1, mask, num);

        // try putting it in the subset
        findAllSubsetBitMask(pos + 1, set(mask,pos), num);
    }

    private static int set(int mask, int pos) {
        return mask | (1<<pos);
    }

    private static boolean bitOn(int mask, int pos) {
        return (mask & (1 << pos)) > 0;
    }

    private static void findAllSubsets(int num) {
        // mask will iterate through all 2^num subsets
        for (int mask = 0; mask < (1 << num); mask ++) {
            for (int i = 0; i < num; i ++) {
                if (bitOn(mask, i)) {
                    System.out.printf("%d ", i);
                }
            }
            System.out.println();
        }
    }
}
