package CTCI.StacksAndQueue;

// implement three stacks using one array
public class StackUsingArrays {
    public static void main(String[] args) {

    }

    /**
     * Number of stacks = 3
     * Capacity of stack is passed in parameter
     * Virtual size is the index bounds for each stack
     */
    private static class FixedStack {
        private int numberOfStacks = 3;
        private int stackCapacity;
        private int[] values;
        // virtual size of the arrays, actual size of the array is bigger than this
        private int[] virtualSizes;

        FixedStack(int stackSize) {
            stackCapacity = stackSize;
            values = new int[stackSize * numberOfStacks];
            virtualSizes = new int[numberOfStacks];
        }

        void push(int stackNum, int value) {
            // increment stack pointer and then update top value
            virtualSizes[stackNum]++;
            // set the value on top of stack
            values[indexOfTop(stackNum)] = value;
        }

        // returns the index of the array on the top of the stack
        private int indexOfTop(int stackNum) {
            //offset calculated based on the number of stacks and the stack capacity for each
            int offset = stackNum * stackCapacity;
            //size of the stack indicated by the stackNum
            int size = virtualSizes[stackNum];
            //offset is calculated with 0 index
            return offset+size-1;
        }

        int pop(int stackNum) {
            int topIndex = indexOfTop(stackNum);
            int value = values[topIndex];  //get top value
            values[topIndex] = 0;          //clear the top value
            virtualSizes[stackNum]--;             //shrink the size of the array (virtually)
            return value;
        }

        int peek(int stackNum) {
            return values[indexOfTop(stackNum)];
        }

        int[] getStackValues(int stackNum) {
            int[] items = new int[virtualSizes[stackNum]];
            for (int i = 0; i < items.length; i++) {
                items[i] = values[stackNum * stackCapacity + i];
            }
            return items;
        }
    }
}
