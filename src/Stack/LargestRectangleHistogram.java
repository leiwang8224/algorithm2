package Stack;

import java.util.Stack;

/**
 * Created by leiwang on 3/31/18.
 */
public class LargestRectangleHistogram {
//    Given n non-negative integers representing the histogram's bar height
//    where the width of each bar is 1, find the area of largest rectangle in the histogram.
    public static void main(String args[]) {
        int[] histogramArr = new int[] {2,3,5,2,4,5,6,4,2,2};
        findLargestRect(histogramArr);
    }

    /**
     * * Do push all heights including 0 height.
       * i - 1 - s.peek() uses the starting index where height[s.peek() + 1] >= height[tp],
         because the index on top of the stack right now is the first index left of tp with height smaller than tp’s height.
     * @param height
     * @return
     */
    private static int findLargestRect(int[] height) {
        int len = height.length;
        // stack is used to store the indices of the histogram
        // the bars stored are always in increasing order of their heights
        Stack<Integer> s = new Stack<Integer>();
        int maxArea = 0;
        for(int i = 0; i <= len; i++){
            int h = (i == len ? 0 : height[i]);
            // if this bar is higher than what is on top of stack, push into stack
            if(s.isEmpty() || h >= height[s.peek()]){
                s.push(i);
            }else{ // the bar is lower than what is on the top, calculate the area with the top of stack as smallest
                   // smallest bar. 'i' is right index for the top and element before top in stack is left index.
                int tp = s.pop();
                // calculate area with height[tp] as smallest bar and update max area
                maxArea = Math.max(maxArea, height[tp] * (s.isEmpty() ? i : i - 1 - s.peek()));
                i--;
            }
        }
        return maxArea;
    }

}
