package Stack;

import java.util.Stack;

/**
 * Created by leiwang on 4/17/18.
 */
//Given n non-negative integers representing an elevation
//map where the width of each bar is 1, compute how much
//water it is able to trap after raining.

//Input: [0,1,0,2,1,0,1,3,2,1,2,1]
//        Output: 6
public class TrappingRainWater {
    public static void main(String[] args) {
        int[] nums = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trapBruteForce(nums));
        System.out.println(trapDP(nums));
        System.out.println(trapStack(nums));
        System.out.println(trapWithPointers(nums));
    }

//    Do as directed in question. For each element in the array,
//    we find the maximum ipSegment of water it can trap after the rain,
//    which is equal to the minimum of maximum height of bars on both
//    the sides minus its own height.
//    Algorithm:
//    Initialize ans=0ans=0
//    Iterate the array from left to right:
//    Initialize max_left=0max_left=0 and max_right=0max_right=0
//    Iterate from the current element to the beginning of array updating:
//    max_left=max(max_left,height[j])max_left=max(max_left,height[j])
//    Iterate from the current element to the end of array updating:
//    max_right=max(max_right,height[j])max_right=max(max_right,height[j])
//    Add min(max_left,max_right)−height[i]min(max_left,max_right)−height[i] to \text{ans}ans
    // Time O(n2)
    // Space O(1)
    private static int trapBruteForce(int[] height) {
        int ans = 0;
        int size = height.length;
        for (int i = 1; i < size-1; i ++) {
            int maxLeft = 0, maxRight = 0;
            for (int j = i; j >= 0; j--) {
                // search the left part for max bar size
                maxLeft = Math.max(maxLeft, height[j]);
            }
            for (int j = i; j < size; j ++) {
                // search the right part for max bar size
                maxRight = Math.max(maxRight, height[j]);
            }

            ans += Math.min(maxLeft, maxRight) - height[i];
        }
        return ans;
    }

    //Time: O(n)
    //Space: O(n)
    private static int trapDP(int[] height) {
        if (height == null)
            return 0;

        int ans = 0;
        int size = height.length;
        int[] leftMax = new int[size];
        int[] rightMax = new int[size];

        leftMax[0] = height[0];

        for (int i = 1; i < size; i++) {
            leftMax[i] = Math.max(height[i], leftMax[i-1]);
        }
        rightMax[size-1] = height[size-1];
        for (int i = size - 2; i >= 0; i--) {
            rightMax[i] = Math.max(height[i], rightMax[i+1]);
        }
        for (int i = 1; i < size - 1; i ++) {
            ans += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return ans;
    }

//    Algorithm
//
//    Use stack to store the indices of the bars.
//    Iterate the array:
//    While stack is not empty and \text{height}[current]>\text{height}[st.top()]height[current]>height[st.top()]
//    It means that the stack element can be popped. Pop the top element as \text{top}top.
//    Find the distance between the current element and the element at top of stack,
//    which is to be filled. \text{distance} = \text{current} - \text{st.top}() - 1distance=current−st.top()−1
//    Find the bounded height
//    bounded_height=min(height[current],height[st.top()])−height[top]bounded_height=min(height[current],
//    height[st.top()])−height[top]
//    Add resulting trapped water to answer ans+=distance∗bounded_heightans+=distance∗bounded_height
//    Push current index to top of the stack
//    Move \text{current}current to the next position
    // Time O(n)
    // Space O(n)
    private static int trapStack(int[] height) {
        int ans = 0, current = 0;
        Stack<Integer> stack = new Stack<>();
        while (current < height.length) {
            while (!stack.isEmpty() &&
                    height[current] > height[stack.peek()]) {
                int top = stack.peek();
                stack.pop();
                if (stack.isEmpty())
                    break;
                int distance = current - stack.peek() - 1;
                int boundedHeight = Math.min(height[current],
                                            height[stack.peek()]) -
                                            height[top];
                ans += distance * boundedHeight;
            }
            stack.push(current++);
        }
        return ans;
    }

//    Algorithm
//
//    Initialize \text{left}left pointer to 0 and \text{right}right pointer to size-1
//    While \text{left}< \text{right}left<right, do:
//    If \text{height[left]}height[left] is smaller than \text{height[right]}height[right]
//    If height[left]>=left_maxheight[left]>=left_max, update left_maxleft_max
//    Else add left_max−height[left]left_max−height[left] to \text{ans}ans
//    Add 1 to \text{left}left.
//            Else
//    If height[right]>=right_maxheight[right]>=right_max, update right_maxright_max
//    Else add right_max−height[right]right_max−height[right] to \text{ans}ans
//    Subtract 1 from \text{right}right.
    // Time O(n)
    // Space O(1)
    private static int trapWithPointers(int[] height) {
        int left = 0, right = height.length - 1;
        int ans = 0;
        int leftMax = 0, rightMax = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax)
                    leftMax = height[left];
                else
                    ans+=leftMax - height[left];
                left++;
            } else {
                if (height[right] >= rightMax)
                    rightMax = height[right];
                else
                    ans+=rightMax - height[right];
                right--;
            }
        }
        return ans;
    }
}
