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

    }

    private List<Integer> topKFrequent(int[] nums, int k) {

        List<Integer>[] bucket = new List[nums.length + 1];
        Map<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();

        for (int n : nums) {
            frequencyMap.put(n, frequencyMap.getOrDefault(n, 0) + 1);
        }

        for (int key : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(key);
            if (bucket[frequency] == null) {
                bucket[frequency] = new ArrayList<>();
            }
            bucket[frequency].add(key);
        }

        List<Integer> res = new ArrayList<>();

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
    public List<Integer> topKFrequent2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int n: nums){
            map.put(n, map.getOrDefault(n,0)+1);
        }

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
    public List<Integer> topKFrequent3(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int n: nums){
            map.put(n, map.getOrDefault(n,0)+1);
        }

        TreeMap<Integer, List<Integer>> freqMap = new TreeMap<>();
        for(int num : map.keySet()){
            int freq = map.get(num);
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
    public List<Integer> topKFrequent4(int[] nums, int k) {

        int len = nums.length;
        int maxFreq = 0;

        // Algo - step 1:
        Map<Integer, Integer> map = new HashMap<>();

        for(int i=0; i<len; i++) {
            map.put(nums[i], map.getOrDefault(nums[i],0) + 1);

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
