package Heap;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by leiwang on 4/1/18.
 */
public class KthLargestInNArrays {
    public static void main() {
        int[][] nums = new int[][] {
                {2,3,54,2,3,2,32,23,1,3,5,23,43},
                {43,23,12,34,2,1,23,12,3,2,4,15}
        };

        System.out.println("kth largest value " + KthInArrays(nums, 3));
    }

    /**
     * @param arrays a list of array
     * @param k an integer
     * @return an integer, K-th largest element in N arrays
     */
    private static class Node{
        int value;
        int row;
        int column;
        public Node(int row, int column, int value){
            this.value = value;
            this.row = row;
            this.column = column;
        }
    }

    private static int KthInArrays(int[][] arrays, int k) {
        // Write your code here
        if(arrays == null || arrays.length == 0 || k <= 0){
            return -1;
        }

        for(int i = 0; i < arrays.length; i++){
            java.util.Arrays.sort(arrays[i]);
        }

        Queue<Node> queue = new PriorityQueue<Node>(arrays.length, new Comparator<Node>(){
            public int compare(Node a, Node b){
                return b.value - a.value;
            }
        });

        for(int i = 0; i < arrays.length; i++){
            if(arrays[i].length > 0){
                queue.offer(new Node(i, arrays[i].length - 1, arrays[i][arrays[i].length - 1]));
            }
        }

        for(int i = 0; i < k; i++){
            Node temp = queue.poll();
            if(i == k - 1){
                return temp.value;
            }
            if(temp.column > 0){
                queue.offer(new Node(temp.row, temp.column - 1, arrays[temp.row][temp.column - 1]));
            }
        }

        return -1;
    }


}
