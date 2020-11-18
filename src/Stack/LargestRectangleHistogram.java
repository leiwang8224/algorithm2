package Stack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by leiwang on 3/31/18.
 */
public class LargestRectangleHistogram {
//    Given n non-negative integers representing the histogram's bar height
//    where the width of each bar is 1, find the area of largest rectangle in the histogram.
    public static void main(String args[]) {
        int[] histogramArr = new int[] {2,3,5,2,4,5,6,4,2,2};
//        System.out.println(findLargestRect(histogramArr));
        System.out.println(largestRectangleArea(histogramArr));
        System.out.println(largestRectangle(histogramArr));
        System.out.println(findLargestRect(histogramArr));
    }

    /**
     * * Do push all heights including 0 height.
       * i - 1 - s.peek() uses the starting index where height[s.peek() + 1] >= height[tp],
         because the index on top of the stack right now is the first index left of tp with height smaller than tp’s height.
     * @param height
     * @return
     */
    private static int findLargestRect(int[] height) {
        int lengthInstagram = height.length;
        // stack is used to store the indices of the histogram
        // the bars stored are always in increasing order of their heights
        Stack<Integer> s = new Stack<Integer>();
        int maxArea = 0;
        for(int indexHistogram = 0; indexHistogram <= lengthInstagram; indexHistogram++){
            // heightCurrentBar reset to 0 if at end of instagram, else set to height of current index
            int heightCurrentBar = (indexHistogram == lengthInstagram ? 0 : height[indexHistogram]);
            // if this bar is higher than what is on top of stack, push into stack
            if(s.isEmpty() || heightCurrentBar >= height[s.peek()]){
                System.out.println("pushing on to stack " + indexHistogram);
                s.push(indexHistogram);
            }else{ // the bar is lower than what is on the top, calculate the area with the top of stack as smallest
                   // smallest bar. 'i' is right index for the top and element before top in stack is left index.
                int lastIndexFromStack = s.pop(); //this is the higher bar than the current bar
                // calculate area with height[lastIndexFromStack] as smallest bar and update max area
                maxArea = Math.max(maxArea,
                                   height[lastIndexFromStack] * (s.isEmpty() ? indexHistogram : indexHistogram - 1 - s.peek()));
                indexHistogram--;
            }
        }
        return maxArea;
    }

//    Iterate through each height
//    If a short height is reached, remove any larger previous heights from stack
//    Compute max possible area of removed height
//    We can extend the current "short" height's start index to the left if larger heights are there
//    Add this height and it's start index to stack
//    After iteration If stack is non-empty:
//    These heights were extended to the end of the histogram
//    Compute max area they could create, where width = (length of histogram - start index of this height)
    private static int largestRectangleArea(int[] heights) {
        int maxArea = 0;
        List<int[]> stack = new ArrayList<>(); // list of pair: [index, height]

        for (int indexHistogram = 0; indexHistogram < heights.length; indexHistogram++) {
            int startIndexArea = indexHistogram;

            // stack size greater than 0 and last element of stack height > current index height
            // or next bar is lower than the previous bar (reached a shorter height than previous)
            while (stack.size() > 0 && stack.get(stack.size() - 1)[1] > heights[indexHistogram]) {
                // get prev bar height
                int height = stack.get(stack.size() - 1)[1];
                // get prev bar index
                int width = indexHistogram - stack.get(stack.size() - 1)[0];
                maxArea = Math.max(maxArea, height * width);
                startIndexArea = stack.get(stack.size() - 1)[0];
                // remove the larger previous heights from stack
                stack.remove(stack.size() - 1);
            }
            // add to stack
            stack.add(new int[]{startIndexArea, heights[indexHistogram]});
        }
        for (int indexStack = 0; indexStack < stack.size(); indexStack++) {
            int height = stack.get(indexStack)[1], start = stack.get(indexStack)[0];
            int area = height * (heights.length - start);
            maxArea = Math.max(area, maxArea);
        }
        return maxArea;
    }

    private static int largestRectangle(int[] heights) {
        int[] width = new int[heights.length];
        int HEIGHT_INDEX = 0;
        int INDEX_INDEX = 1;

        LinkedList<int[]> stack = new LinkedList<>();
        // [height, index]
        stack.push(new int[]{-1, heights.length});

        java.util.Arrays.fill(width, 0);

        // iterate from the back to front to calculate the width array
        for (int backwardIndex=heights.length-1; backwardIndex>=0; backwardIndex--) {
            int[] top = stack.peek();

            // go backwards until we hit a height that is lower than current one
            while (top[HEIGHT_INDEX] >= heights[backwardIndex]) {
                stack.pop();
                top = stack.peek();
            }

            //backwardIndex is on a bar lower than current one
            width[backwardIndex] = width[backwardIndex] + top[INDEX_INDEX] - backwardIndex;
            stack.push(new int[]{heights[backwardIndex], backwardIndex});
        }

        stack = new LinkedList<>();
        stack.push(new int[]{-1, -1});

        // iterate from the front to back to calculate the width array
        for (int forwardIndex=0; forwardIndex<heights.length; forwardIndex++) {
            int[] top = stack.peek();

            // go forward until we hit a height that is lower than current one
            while (top[HEIGHT_INDEX] >= heights[forwardIndex]) {
                stack.pop();
                top = stack.peek();
            }

            // Need to subtract 1 here because otherwise we'll be counting the bar itself twice
            width[forwardIndex] = width[forwardIndex] + forwardIndex-top[INDEX_INDEX]-1;
            stack.push(new int[]{heights[forwardIndex], forwardIndex});
        }

        int result = 0;

        for (int i=0; i<heights.length; i++) {
            result = Math.max(result, heights[i]*width[i]);
        }

        return result;
    }

}
