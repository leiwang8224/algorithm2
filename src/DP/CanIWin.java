package DP;

import java.util.HashMap;
import java.util.Map;

public class CanIWin {
    public static void main(String[] args) {
        System.out.println(canIWin(10, 1));
        System.out.println(canIWin(10, 1));

    }

    public static boolean canIWinBruteForce(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal <= maxChoosableInteger)
            return true;
        int sumMaxChoosableInteger = (1 + maxChoosableInteger) / 2 * maxChoosableInteger;
        if (sumMaxChoosableInteger < desiredTotal) {
            return false;
        }
        return canIWinFrom(maxChoosableInteger, desiredTotal, new boolean[maxChoosableInteger + 1]);
    }

    private static boolean canIWinFrom(int maxChoosableInteger, int desiredTotal, boolean[] chosen) {
        if (desiredTotal <= 0) {
            return false;
        }

        // exhaustively search all paths for winning, if no winning path found, opponent loses
        for (int i = 1; i <= maxChoosableInteger; i++) {
            if (chosen[i]) {
                continue;
            }
            chosen[i] = true;
            // check to see if opponent can win, if not, I can win
            if (!canIWinFrom(maxChoosableInteger, desiredTotal - i, chosen)) {
                chosen[i] = false;
                // I can win because opponent can not win
                return true;
            }
            chosen[i] = false;
        }
        return false;
    }


    public boolean canIWinMemo(int maxChoosableInteger, int desiredTotal) {
        Map<String, Boolean> memo; // key: chosen[] to string, value: canIWinWithSituation
                                    // return value when chosen to string is key

        if (desiredTotal <= maxChoosableInteger)
            return true;
        if (((1 + maxChoosableInteger) / 2 * maxChoosableInteger) < desiredTotal) {
            return false;
        }
        memo = new HashMap<>();

        return canIWinWithSituation(maxChoosableInteger, desiredTotal, new boolean[maxChoosableInteger + 1], memo);
    }

    private boolean canIWinWithSituation(int maxChoosableInteger, int curDesiredTotal, boolean[] chosen, Map<String, Boolean> memo) {

        if (curDesiredTotal <= 0) {
            return false;
        }

        String chosenSerialization = java.util.Arrays.toString(chosen);
        if (memo.containsKey(chosenSerialization)) {
            return memo.get(chosenSerialization);
        }

        for (int i = 1; i <= maxChoosableInteger; i++) {
            if (chosen[i]) {
                continue;
            }
            chosen[i] = true;
            if (!canIWinWithSituation(maxChoosableInteger, curDesiredTotal - i, chosen, memo)) {
                memo.put(chosenSerialization, true);
                chosen[i] = false;
                return true;
            }
            chosen[i] = false;
        }
        memo.put(chosenSerialization, false);
        return false;
    }

//    Map<Integer, Boolean> map;
//    boolean[] used;

    /**
     * form map for corresponding plays and win or lose
     * form boolean array for the play configurations
     */
    public static boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        Map<Integer, Boolean> playConfigurationMap;
        boolean[] playConfigurationBool;
        int sumMaxChoosableInteger = (1+maxChoosableInteger)*maxChoosableInteger/2;
        // only opponent can win
        if(sumMaxChoosableInteger < desiredTotal) return false;
        // only I can win
        if(desiredTotal <= 0) return true;

        playConfigurationMap = new HashMap();
        playConfigurationBool = new boolean[maxChoosableInteger+1];
        return checkIfWin(desiredTotal, playConfigurationMap, playConfigurationBool);
    }

    // simulate the game using min-max algorithm, map is used to cache <play, winning> configurations
    // everytime recursion happens the opponent is processed, updating playConfiguration and used array
//    Core logic:
//    We want to see if a path exists where all the branches for the opponent results in a false.
//    The opponent tries every single branch before he gives up and returns a false.
//
//    Memoization:
//    At any point in the decision tree, the path ahead is solely based on the numbers chosen so far. So we can use that to memoize the results.
//    Let's assume a test case:
//            2
//            2
//
//    First Player chooses 1
//    Second Player find that 1 is taken, so (s)he chooses 2
//    First Player turn find that desiredTotal <= 0, so the helper method return false
//    Second Player receives this false with a ! before it, so the helper method return true
//    First Player finds that after choosing 1, he received a true from the helper method, this means, choosing 1 will make second player win and him lose
//
//    First Player unchoose 1 and chooses 2
//    Second Player find that desiredTotal <= 0, so the helper method return false
//    First Player receives this false with a ! before it, so the condition is true now and Player 1 Wins

    /**
     * 1. get the current play configuration by converting the play config into integer
     * 2. if the map does not contain the play config:
     *    - for loop to iterate through all plays
     *    - if the current play has not played yet, play the current configuration
     *    - call recurse on the method and subtract from desiredTotal
     * 3. if opponent does not win
     *    - put the winning configuration in map
     *    - reset the play configuration and return true for I win (unchoose)
     *    - reset the play configuration (unchoose) for opponent win
     * 4. for loop exhausted all plays and no win so I lose, put in map
     */
    public static boolean checkIfWin(int desiredTotal, Map<Integer, Boolean> playConfigurationMap, boolean[] curPlayConfigBoolArray){
        // total is already less than or equal to 0, game over I lose
        if(desiredTotal <= 0) return false;
        // get the play configuration from last play (opponent's move)
        int playConfigOpponent = format(curPlayConfigBoolArray);
        // exhaustively try every single configuration
        if(!playConfigurationMap.containsKey(playConfigOpponent)){
            // for all legal moves, numberToPlay is updated by incrementing 1
            for(int numberToPlay=1; numberToPlay<curPlayConfigBoolArray.length; numberToPlay++){
                // for each number that is not used add to current play configuration
                // if the play has not been used yet, use it for my play. Note the play config is from opponent
                if(!curPlayConfigBoolArray[numberToPlay]){
                    // mark numberToPlay as used for opposing player
                    curPlayConfigBoolArray[numberToPlay] = true;
                    // check whether this lead to a win (i.e. the other player lose)
                    // NOTE that this is checking to see if opponent can win !!!
                    // other player will be playing numberToPlay, hence we pass in desiredTotal-numberToPlay for numberToPlay
                    if(!checkIfWin(desiredTotal-numberToPlay, playConfigurationMap, curPlayConfigBoolArray)){
                        // opponent cannot win so I can win, adding winning configuration to map
                        playConfigurationMap.put(playConfigOpponent, true);
                        // since game is over, unchoose the number played before returning
                        curPlayConfigBoolArray[numberToPlay] = false;
                        // I can win
                        return true;
                    }
                    // opponent wins, unchoose the number played
                    curPlayConfigBoolArray[numberToPlay] = false;
                }
            }
            // update the map with opponent wins, since after exhaustive search there is no solution to win
            // none of the not used number can make current play win
            playConfigurationMap.put(playConfigOpponent, false);
        }
        System.out.println(playConfigOpponent + "," + toBinString(playConfigOpponent) + "," + playConfigurationMap.get(playConfigOpponent));
        // all different play configuration exhaustively searched
        // playConfigurationMap contains this key so return it
        return playConfigurationMap.get(playConfigOpponent);
    }

    // transfer boolean[] to an Integer
    public static int format(boolean[] used){
        int num = 0;
        for(boolean b: used){
            num <<= 1;
            if(b) num |= 1;
        }
        return num;
    }

    public static String toBinString(int num) {
        StringBuilder str = new StringBuilder();
        while (num / 2 != 0) {
            str.append(num % 2 == 1 ? "1" : "0");
            num = num / 2;
        }
        return str.toString();
    }

    public boolean canIWinString(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal<=0) return true;
        if (maxChoosableInteger*(maxChoosableInteger+1)/2 < desiredTotal) return false;
        return canIWinStringHelper(desiredTotal, new int[maxChoosableInteger], new HashMap<>());
    }

    private boolean canIWinStringHelper(int total, int[] integerChosen, HashMap<String, Boolean> hashMap) {
        String curr = java.util.Arrays.toString(integerChosen);
        if (hashMap.containsKey(curr)) return hashMap.get(curr);
        for (int i = 0; i < integerChosen.length; i++) {
            if (integerChosen[i] == 0) {
                integerChosen[i] = 1;
                // opponent loses, I win
                if (total <= i+1 || !canIWinStringHelper(total-(i+1), integerChosen, hashMap)) {
                    hashMap.put(curr, true);
                    integerChosen[i] = 0;
                    return true;
                }
                integerChosen[i]=0;
            }
        }
        hashMap.put(curr, false);
        return false;
    }

    public static boolean canIWin2(int maxChoosableInteger, int desiredTotal) {
        Map<Integer, Boolean> cache = new HashMap<>();

        if (desiredTotal <= 0)
            return true;
        if (maxChoosableInteger * (maxChoosableInteger+1)/2 < desiredTotal)
            return false;
        return win(maxChoosableInteger, desiredTotal, 0, cache);
    }

    static boolean win(int max, int val, int state, Map<Integer, Boolean> cache) {
        if (val <= 0)
            return false;
        if (cache.containsKey(state))
            return cache.get(state);
        boolean ans = false;
        for (int i = 0; i < max; i++)
            if ((1 << i & state) == 0) {
                if (!win(max, val-i-1, 1 << i | state, cache)) {
                    ans = true;
                    break;
                }
            }
        cache.put(state, ans);
        return ans;
    }

    public static boolean canIWin3 (int M, int T) {
        byte[] flag;
        int sum = M * (M + 1) / 2;
        if (sum < T) return false;
        if (T <= 0) return true;
        flag = new byte[1 << M];
        return checkCanIWin3(M, T, 0, flag);
    }

    private static boolean checkCanIWin3(int M, int T, int state, byte[] flag) {
        if (T <= 0) return false;
        if (flag[state] != 0) return flag[state] == 1;

        for (int shiftDigits = 0; shiftDigits < M; ++shiftDigits) {
            if ((state & (1<<shiftDigits))>0) continue;
            if(!checkCanIWin3(M, T - (shiftDigits+1), state | (1 << shiftDigits), flag)) {
                flag[state] = 1;
                return true;
            }
        }
        flag[state] = -1;
        return false;
    }

    public static boolean canIWin4(int maxChoosableInteger, int desiredTotal) {
        Map<Integer, Boolean> cache = new HashMap<>();

        if (desiredTotal <= 0)
            return true;
        if (maxChoosableInteger * (maxChoosableInteger+1)/2 < desiredTotal)
            return false;
        return checkIfwin4(maxChoosableInteger, desiredTotal, 0, cache);
    }

    static boolean checkIfwin4(int max, int val, int state, Map<Integer, Boolean> cache) {
        if (val <= 0)
            return false;
        if (cache.containsKey(state))
            return cache.get(state);
        boolean ans = false;
        for (int i = 0; i < max; i++)
            if ((1 << i & state) == 0) {
                if (!checkIfwin4(max, val-i-1, 1 << i | state, cache)) {
                    ans = true;
                    break;
                }
            }
        cache.put(state, ans);
        return ans;
    }
}
