package Arrays;

import java.util.Deque;

//LC-862
public class ShortestSubArraySum {
    public static void main(String[] args) {
        System.out.println(shortestSubarray(new int[] {2, -1, 2},3));
    }



//    Basic idea, for array starting at every A[i], find the shortest one with sum at least K.
//    In my solution, for cumulative_sum[i], find the smallest j that cumsum[j] - cumsum[i] >= K.
//    1. while loop 1 is used to move start index forward when the window meets criteria
//    2. while loop 2 is used to keep the queue in monotonic increasing
    // time O(n)
    // space O(n)
    public static int shortestSubarray(int[] A, int K) {
        int lengthOfA = A.length;
        int[] cumulativeSum  = new int[lengthOfA+ 1];
        int shortestSubArrayLength = cumulativeSum.length;

        for(int index = 0; index < lengthOfA; index++)
            cumulativeSum[index + 1] = cumulativeSum[index] + A[index];
        // note that the deque start indices does not have to be contiguous
        Deque<Integer> allPossibleStartIndicesQueue = new java.util.LinkedList();
        for(int endIndex = 0; endIndex < cumulativeSum.length; endIndex++){
            // condition is satisfied so we want to move the start index forward
            while(allPossibleStartIndicesQueue.size() > 0 &&
                    cumulativeSum[endIndex] - cumulativeSum[allPossibleStartIndicesQueue.peek()] >= K){
                System.out.println("first loop queue remove front of queue:" + allPossibleStartIndicesQueue.toString() + " cumSumIndex = " + endIndex + " queueFrontIndex = " + allPossibleStartIndicesQueue.peekFirst());
                // if the cum sum is at least K, poll from queue until cum sum is less than K
                // Note that the values of the startIndex will not necessarily be contiguous
                // startIndex will be jumping around, pollFirst() will increment the startIndex
                // hence we use the min function to keep track of the shortest length
                shortestSubArrayLength = Math.min(shortestSubArrayLength,
                        endIndex - allPossibleStartIndicesQueue.poll());
            }
            // the index from the queue i in A[i] is smaller than the last number, so drop last number
            // to maintain monotonic increasing queue
            while(allPossibleStartIndicesQueue.size() > 0 &&
                    cumulativeSum[endIndex] <= cumulativeSum[allPossibleStartIndicesQueue.peekLast()]){
                System.out.println("second loop queue remove from the end of queue:" + allPossibleStartIndicesQueue.toString() + " cumSumIndex = " + endIndex + " queueEndIndex = " + allPossibleStartIndicesQueue.peekLast());
                // drop the cum sum at the end since they are too large, (cum sum is from smallest to largest)
                allPossibleStartIndicesQueue.pollLast();
            }

            // add potential future start pointer, note it's the endIndex added to queue
            allPossibleStartIndicesQueue.offer(endIndex);
            System.out.println("added to the queue:" + allPossibleStartIndicesQueue.toString());
        }

        return shortestSubArrayLength == cumulativeSum.length ? -1: shortestSubArrayLength;
    }

    private int shrotestSubArray(int[] A, int K) {
        int N = A.length;
        long[] P = new long[N+1];
        for (int i = 0; i < N; ++i)
            P[i+1] = P[i] + (long) A[i];

        // Want smallest y-x with P[y] - P[x] >= K
        int ans = N+1; // N+1 is impossible
        Deque<Integer> monoq = new java.util.LinkedList(); //opt(y) candidates, as indices of P

        for (int y = 0; y < P.length; ++y) {
            // Want opt(y) = largest x with P[x] <= P[y] - K;
            while (!monoq.isEmpty() && P[y] <= P[monoq.getLast()])
                monoq.removeLast();
            while (!monoq.isEmpty() && P[y] >= P[monoq.getFirst()] + K)
                ans = Math.min(ans, y - monoq.removeFirst());

            monoq.addLast(y);
        }

        return ans < N+1 ? ans : -1;
    }

    public int shortestSubarray3(int[] A, int K) {
        int lengthA = A.length, runningSum = 0;
        int[] prefixSum = new int[lengthA+1];

        for (int i = 1; i <= lengthA; i++) {
            runningSum += A[i-1];
            prefixSum[i] = runningSum;
        }

        int ans = lengthA+1;
        Deque<Integer> allPossibleStartIndices = new java.util.LinkedList<>();
        allPossibleStartIndices.add(0);

        for (int endIdx = 1; endIdx <= lengthA; endIdx++) {
            // iterate through all possible start indices and verify if valid
            while (!allPossibleStartIndices.isEmpty() &&
                    prefixSum[endIdx] - prefixSum[allPossibleStartIndices.peekFirst()] >= K) {
                int startIndex = allPossibleStartIndices.pollFirst();
                // add to ans with the valid solution
                ans = Math.min(ans, endIdx - startIndex);
            }

            // check to see if valid sum using tail of queue
            while (!allPossibleStartIndices.isEmpty() &&
                    prefixSum[allPossibleStartIndices.peekLast()] >= prefixSum[endIdx]) {
                allPossibleStartIndices.pollLast();
            }

            allPossibleStartIndices.addLast(endIdx); // add the endIdx to queue, since it may used as a start index later
        }

        return ans == lengthA+1 ? -1 : ans;
    }

    public int shortestSubarray2(int[] A, int K) {
        //Shortest Subarray with Sum at Least K
        int[] sum = new int[A.length+1];
        for(int i=0;i<A.length;i++){
            sum[i+1]=sum[i]+A[i];
        }
        Deque<Integer> deque = new java.util.LinkedList<Integer>();
        //the key of this stack is to make sure that smaller value will be on top,
        //why ? because any element >= k is valid, so the safest way is start from small  element.
        //if value is the same or larger, make sure larger index will remained in the stack
        //why ? because we are looking for shortest length which is valid.
        //just keep this in mind, then we can understand the following cases easily
        deque.offer(0);
        int minLen = Integer.MAX_VALUE;
        for(int i=1;i<sum.length;i++){
            //case 1:
            //current element is smaller than or equal peekFirst
            //just put on the peekFirst, because it is small with a larger index
            //that is exactly what we need!
            if(!deque.isEmpty() && sum[i] <= sum[deque.peekFirst()]){
                deque.offerFirst(i);
            }else{
                //case 2:
                //current element is larger than peekFirst
                //first we will try to update minLen;
                while(!deque.isEmpty() && sum[i]-sum[deque.peekFirst()]>=K){
                    minLen = Math.min(minLen, i-deque.pollFirst());
                }
                //when we leave first while loop, it means we have following three case:
                if(deque.isEmpty()){
                    // case 2.1: the stack is empty!
                    deque.offerLast(i);
                }else if(sum[deque.peekFirst()] >= sum[i]){
                    // case 2.2: current element is smaller than peekFirst,
                    //just put on the peekFirst, because it is small with a larger index
                    //that is exactly what we need!
                    deque.offerFirst(i);
                }else{
                    // case 2.3: sum[i]-sum[deque.peekFirst()] < K,
                    //since current element is still larger than peekFirst, so we can not put it on the peekFirst
                    //but we can pollLast, as long as it is smaller than peekLast, and with a larger index
                    //exactly what we need!
                    while(!deque.isEmpty() && sum[i]<=sum[deque.peekLast()]){
                        deque.pollLast();
                    }
                    deque.offerLast(i);
                }
            }
        }
        return minLen==Integer.MAX_VALUE?-1:minLen;
    }
}
