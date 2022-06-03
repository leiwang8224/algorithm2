package AlgoExpert.Easy;

import java.util.*;
public class NonConstructibleChange {
    //O(nlogn) time | O(1) space - where n is the number of coins
    int nonConstructibleChange(int[] coins) {
        Arrays.sort(coins);

        int curChangeCreated = 0;

        for (int coin : coins) {
            if (coin > curChangeCreated + 1) {
                return curChangeCreated + 1;
            }

            curChangeCreated += coin;
        }

        return curChangeCreated+1;
    }
}
