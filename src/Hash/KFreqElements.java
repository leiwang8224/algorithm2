package Hash;

import java.util.*;

/**
 * Created by leiwang on 4/2/18.
 */

/**
 * Return top k frequent elements
 */
public class KFreqElements {
    public static void main(String[] args) {
        int[] nums = new int[] {1,2,3,2,1,2,3,3,3,2,1,2};
        List<Integer> result = topKFrequent(nums,2);
        for (Integer integer : result)
            System.out.println(integer);
    }

    private static class Pair {
        int num;
        int count;
        public Pair(int num, int count) {
            this.num = num;
            this.count = count;
        }
    }
    private static List<Integer> topKFrequent(int[] nums, int k) {
        //count the frequency for each element
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int num: nums){
            if(map.containsKey(num)){
                map.put(num, map.get(num)+1);
            }else{
                map.put(num, 1);
            }
        }

        // create a min heap
        PriorityQueue<Pair> queue = new PriorityQueue<Pair>(new Comparator<Pair>(){
            public int compare(Pair a, Pair b){
                return a.count-b.count;
            }
        });

        //maintain a heap of size k.
        for(Map.Entry<Integer, Integer> entry: map.entrySet()){
            Pair p = new Pair(entry.getKey(), entry.getValue());
            queue.offer(p);
            if(queue.size()>k){
                queue.poll();
            }
        }

        //get all elements from the heap
        List<Integer> result = new ArrayList<Integer>();
        while(queue.size()>0){
            result.add(queue.poll().num);
        }
        //reverse the order
        Collections.reverse(result);

        return result;
    }
}
