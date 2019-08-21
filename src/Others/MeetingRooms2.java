package Others;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

//Given an array of meeting time intervals consisting of start and
// end times [[s1,e1],[s2,e2],...] find the minimum number of
// conference rooms required.
public class MeetingRooms2 {
    public static void main(String[] args) {
        int[][] timesForMeeting = new int[][] {{2,15}, {36,45}, {9,29}, {16,23}, {4,9}};

        System.out.println(minMeetingRooms(timesForMeeting));

    }

    private static int minMeetingRooms(int[][] intervals) {
        // sort intervals based on starting time
        Arrays.sort(intervals, Comparator.comparing((int[] itv) -> itv[0]));

        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int count = 0;
        for (int[] interval : intervals) {
            if (heap.isEmpty()) {
                count++;
            } else {
                // if starting time is greater than the smallest ending time
                if (interval[0] >= heap.peek()) {
                    // take smallest ending time out
                    heap.poll();
                } else { // starting time is less than the smallest ending time
                    // increment count
                    count++;
                }
            }
            // put ending time in heap (sorted from largest to smallest)
            // every time poll retrieves the smallest element
            heap.offer(interval[1]);
        }
        return count;
    }
}
