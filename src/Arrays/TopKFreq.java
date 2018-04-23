package Arrays;

import java.util.*;

/**
 * Created by leiwang on 4/15/18.
 */

/**
 * Given a non-empty array of integers, return the k most frequent elements.

 For example,
 Given [1,1,1,2,2,3] and k = 2, return [1,2].
 Algorithm must be better than O(nlog(n))
 */
public class TopKFreq {
    public static void main(String args[]) {
        int[] nums = new int[]{1,1,1,2,2,3};
        System.out.println(topKFrequent(nums,2));
        System.out.println(topKFrequent2(nums, 2));
        System.out.println(topKFrequent3(nums, 2));
        System.out.println(topKFrequent4(nums, 2));

    }

    private static List<Integer> topKFrequent(int[] nums, int k) {
        // array of integer list
        List<Integer>[] bucket = new List[nums.length + 1];
        Map<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();

        for (int n : nums) {
            // key is the num, value is the frequency
            frequencyMap.put(n, frequencyMap.getOrDefault(n, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }

        // for each key(value), get frequency and put in bucket array
        // this sorts the values by frequency, starting from lowest to highest freq
        for (int key : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(key);
            // array of frequencies
            if (bucket[frequency] == null) {
                bucket[frequency] = new ArrayList<>();
            }
            // for a certain frequency, add corresponding value
            bucket[frequency].add(key);
        }

        List<Integer> res = new ArrayList<>();

        // starting from the largest bucket, which means the most frequent k values
        for (int pos = bucket.length - 1; pos >= 0 && res.size() < k; pos--) {
            if (bucket[pos] != null) {
                res.addAll(bucket[pos]);
            }
        }
        return res;
    }

    /**
     * max Heap method
     * @param nums
     * @param k
     * @return
     */
    public static List<Integer> topKFrequent2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int n: nums){
            map.put(n, map.getOrDefault(n,0)+1);
        }

        // priority queue has the value with highest freq on top,
        // so polling gets the most freq to least freq
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap =
                new PriorityQueue<>((a,b)->(b.getValue()-a.getValue()));
        for(Map.Entry<Integer,Integer> entry: map.entrySet()){
            maxHeap.add(entry);
        }

        List<Integer> res = new ArrayList<>();
        while(res.size()<k){
            Map.Entry<Integer, Integer> entry = maxHeap.poll();
            res.add(entry.getKey());
        }
        return res;
    }

    /**
     * Use treeMap, use frequency as the key so we can get all frequencies in order
     * @param nums
     * @param k
     * @return
     */
    public static List<Integer> topKFrequent3(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int n: nums){
            map.put(n, map.getOrDefault(n,0)+1);
        }

        TreeMap<Integer, List<Integer>> freqMap = new TreeMap<>();
        for(int num : map.keySet()){
            // map of num and freq
            int freq = map.get(num);
            // map of freq and list of nums
            if(!freqMap.containsKey(freq)){
                freqMap.put(freq, new LinkedList<>());
            }
            freqMap.get(freq).add(num);
        }

        List<Integer> res = new ArrayList<>();
        while(res.size()<k){
            Map.Entry<Integer, List<Integer>> entry = freqMap.pollLastEntry();
            res.addAll(entry.getValue());
        }
        return res;
    }


    /**
     * Use map/dictionary and store the frequency of the number and maximum
     * frequency of all the numbers. So at the end of this operation, for the
     * sample problem, map would look like this: 1 → 3, 2 → 2, 3 →1.
     * Also, maximum frequency will be 3.

     Now, since, we cannot use regular sorting approach, another thing that
     comes to mind is, bucket sort. Create a multi-storage bucket with
     (maximum frequency + 1)as its size. Now, based on frequency of the word,
     put it in the appropriate bucket level. In our example, Put 1 at level 3,
     put 2 at level 2 and put 3 at level 1.

     There might be more than one numbers with the same frequency.
     So, we can use linked list to store more than one elements at the same level.

     Now, iterate over the bucket elements and keep a counter to match with the input value k.
     * @param nums
     * @param k
     * @return
     */
    public static List<Integer> topKFrequent4(int[] nums, int k) {

        int len = nums.length;
        int maxFreq = 0;

        // Algo - step 1:
        Map<Integer, Integer> map = new HashMap<>();

        for(int i=0; i<len; i++) {
            // map of nums and freq
            map.put(nums[i], map.getOrDefault(nums[i],0) + 1);

            // get max freq
            maxFreq = Math.max(maxFreq, map.get(nums[i]));
        }

        // Algo - step 3 and 4: Create bucket of size of max Freq.

        List<Integer> [] bucketList = new LinkedList[maxFreq+1];

        for(int i=0; i<= maxFreq; i++) {
            bucketList[i] = new LinkedList<>();
        }

        // Put elements in the bucket by iterating over the map.
        for(Integer key : map.keySet()) {

            int freq = map.get(key);

            bucketList[freq].add(key);

        }

        // Algo step 5:
        int ct = 0;
        List<Integer> ans = new LinkedList<>();

        for(int i=maxFreq; i>=0; i--) {

            List<Integer> currentList = bucketList[i];

            for(Integer j: currentList) {
                if(ct < k) {
                    ans.add(j);
                    ct++;
                } else {
                    return ans;
                }
            }

        }

        return ans;


    }
}
