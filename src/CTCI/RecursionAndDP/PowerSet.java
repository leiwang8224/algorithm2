package CTCI.RecursionAndDP;

import java.util.ArrayList;

/**
 * Write a method o return all subsets of a set
 */
public class PowerSet {
    public static void main(String[] args) {
         ArrayList<ArrayList<Integer>> result = generateSets(new ArrayList<Integer>(java.util.Arrays.asList(1,2,3,4)));

        for (ArrayList<Integer> list : result) {
            System.out.println(java.util.Arrays.toString(list.toArray()));
        }
    }

    /**
     * Starting with the base case: n = 0
     * There is just one subset of the empty set {}
     * Case: n = 1
     * There are two subsets of the set {a1}: {}{a1}
     * Case: n = 2
     * There are four subsets of the set {a1, a2}: {}, {a1}, {a2}, {a1, a2}
     * Case: n = 3
     * P(2) = {}, {a1}, {a2}, {a1, a2}
     * P(3) = {}, {a1}, {a2}, {a3}, {a1, a2}, {a1, a3}, {a2, a3}, {a1,a2,a3}
     *
     * The difference between the solutions for n = 3 and n = 2 is P(2) is missing
     * all the subsets containing a3:
     * P(3) - P(2) = {a3}, {a1,a3}, {a2,a3}, {a1,a2,a3}
     * How can we use P(2) to create P(3)? We can simply clone the subsets in P(2) to
     * add a3 to them:
     * P(2) = {}, {a1}, {a2}, {a1, a2}
     * P(2) + a3 = {a3}, {a1,a3}, {a2,a3}, {a1,a2,a3}
     * When merged together we make P(3)
     * @param nums
     * @return
     */
    private static ArrayList<ArrayList<Integer>> generateSets(ArrayList<Integer> nums) {
        return generateSets(nums, 0);

    }

    private static ArrayList<ArrayList<Integer>> generateSets(ArrayList<Integer> nums, int index) {
        ArrayList<ArrayList<Integer>> allSubsets;
        if (nums.size() == index) {  // base case - add empty set, when at the last index
            allSubsets = new ArrayList<ArrayList<Integer>>();
            allSubsets.add(new ArrayList<>()); // empty set
        } else {
            allSubsets = generateSets(nums, index + 1);
            int item = nums.get(index);
            ArrayList<ArrayList<Integer>> moreSubssets = new ArrayList<>();
            for (ArrayList<Integer> subset : allSubsets) {
                ArrayList<Integer> newSubset = new ArrayList<>();
                newSubset.addAll(subset);
                newSubset.add(item);
                moreSubssets.add(newSubset);
            }
            allSubsets.addAll(moreSubssets);
        }
        return allSubsets;
    }

    /**
     * Recall that when we are generating a set, we have two choices for each element:
     * 1) the element is in the set (the 'yes' state)
     * 2) the element is not in the set (the 'no' state)
     * This means each subset is a sequence of yeses /nos -ex: "yes,yes,no,no,yes,no"
     * This gives us 2^n possible subsets. How can we iterate through all possible sequences
     * of "yes" / "no" states for all elements? If each "yes" can be treated as a 1 and each
     * "no" can be treated as a 0, then each subset can be represented as a binary string.
     *
     * We can look at this problem as generating all of possible binary digits given the number
     * of digits: example: 00, 01, 10, 11,
     *
     * convertIntToSet takes an integer and puts the digit in when the corresponding boolean bit
     * is true or takes the digit out when the corresponding digit is false.
     * @param digit
     * @param set
     * @return
     */
    private static ArrayList<Integer> convertIntToSet(int digit, ArrayList<Integer> set) {
        ArrayList<Integer> subset = new ArrayList<>();
        int index = 0;
        // right shift k and with 1 to check if it's 1 subset add the number
        for (int k = digit; k > 0; k >>= 1) {
            if ((k & 1) == 1) subset.add(set.get(index));
            index++;
        }
        return subset;
    }

    private static ArrayList<ArrayList<Integer>> getSubsets(ArrayList<Integer> set) {
        ArrayList<ArrayList<Integer>> allsubsets = new ArrayList<>();
        int max = 1 << set.size(); // compute 2^n, samething as 2 ^ (set.size())
        for (int digit = 0; digit < max; digit++) {
            ArrayList<Integer> subset = convertIntToSet(digit, set);
            allsubsets.add(subset);
        }
        return allsubsets;
    }
}
