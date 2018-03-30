import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leiwang on 3/11/18.
 */
public class CombinationSum3 {
//    Find all possible combinations of k numbers that add up to a number n,
//    given that only numbers from 1 to 9 can be used and each combination should be a unique set of numbers.
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        findComboSum(result, new ArrayList<>(), 3, 3, 1);
        for (ArrayList<Integer> subList : result) {
            System.out.println(Arrays.toString(subList.toArray()));
        }

    }

    private static void findComboSum(ArrayList<ArrayList<Integer>> result, ArrayList<Integer> subList, int kNumbers, int remain, int startIndex) {
        System.out.println("entering generateSumCombo remain = " + remain  + " subList = " + Arrays.toString(subList.toArray()));

//        if (remain < 0) return;
        if (remain == 0 && subList.size() == kNumbers) {
            result.add(new ArrayList<>(subList));
            return;
        }
            for (int i = startIndex; i <= 9; i ++) {
                subList.add(i);
                System.out.println("subList after add " + Arrays.toString(subList.toArray()));

                findComboSum(result,subList,kNumbers,remain-i,i+1);
                subList.remove(subList.size()-1);
                System.out.println("subList after remove " + Arrays.toString(subList.toArray()));

            }
    }


}

