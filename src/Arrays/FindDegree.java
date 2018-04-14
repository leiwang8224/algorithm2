package Arrays;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leiwang on 4/13/18.
 */
//Just record the index of each number in a hash map and find the degree
// of the array. Second iteration to find the shortest index range for
// the number with the largest frequency.
public class FindDegree {
    public static void main() {
        int[] nums = new int[]{1, 2, 2, 3, 1};
    }

    private int findShortestSubArray(int[] nums) {
        int degree = 0, n = nums.length, minSize = n;
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer[]> map2 = new HashMap<>();

        for (int i=0;i<n;i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            degree = Math.max(degree, map.get(nums[i]));

            if (map2.get(nums[i]) == null) map2.put(nums[i], new Integer[2]);
            Integer[] numRange = map2.get(nums[i]);
            // put index in numRange array
            if (numRange[0] == null) numRange[0] = i;
            numRange[1] = i;
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() != degree) continue;
            Integer[] range = map2.get(entry.getKey());
            minSize = Math.min(minSize, range[1] - range[0] + 1);
        }
        return minSize;
    }

    private int findShortestSubArray2(int[] nums) {
        if (nums.length == 0 || nums == null) return 0;
        Map<Integer, int[]> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++){
            if (!map.containsKey(nums[i])){
                map.put(nums[i], new int[]{1, i, i});  // the first element in array is degree, second is first index of this key, third is last index of this key
            } else {
                int[] temp = map.get(nums[i]);
                temp[0]++;
                temp[2] = i;
            }
        }
        int degree = Integer.MIN_VALUE, res = Integer.MAX_VALUE;
        for (int[] value : map.values()){
            if (value[0] > degree){
                degree = value[0];
                res = value[2] - value[1] + 1;
            } else if (value[0] == degree){
                res = Math.min( value[2] - value[1] + 1, res);
            }
        }
        return res;
    }

    public int findShortestSubArray3(int[] nums) {
        Map<Integer, Integer> left = new HashMap(),
                right = new HashMap(), count = new HashMap();

        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (left.get(x) == null) left.put(x, i);
            right.put(x, i);
            count.put(x, count.getOrDefault(x, 0) + 1);
        }

        int ans = nums.length;
        int degree = Collections.max(count.values());
        for (int x: count.keySet()) {
            if (count.get(x) == degree) {
                ans = Math.min(ans, right.get(x) - left.get(x) + 1);
            }
        }
        return ans;
    }
}
