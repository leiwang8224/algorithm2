package AlgoExpert.Medium;

public class ArrayOfProducts {
    public static void main(String[] args) {

    }

    // O(n^2) time | O(n) space
    private static int[] arrayOfProd(int[] array) {
        int[] products = new int[array.length];

        for (int index = 0; index < array.length; index++) {
            int runningProduct = 1;
            for (int runningIndex = 0; runningIndex < array.length; runningIndex++) {
                if (index != runningIndex) runningProduct *= array[runningIndex];
            }
            products[index] = runningProduct;
        }

        return products;
    }

    // O(n) time | O(n) space
    private static int[] arrayOfProd2(int[] array) {
        int[] products = new int[array.length];
        // for each element in leftProduct and rightProduct, the product of everything
        // to the left is equal to the current element
        // leftProduct[i] = product(array[0]..array[i-1])
        // rightProduct[i] = product(array[length-1]..array[i+1])
        // result[i] = leftProduct[i] * rightProduct[i]
        int[] leftProducts = new int[array.length];
        int[] rightProducts = new int[array.length];

        // multiply each elements starting from the left side
        // cumulative product from left
        int leftRunningProduct = 1;
        for (int startIdx = 0; startIdx < array.length; startIdx++) {
            leftProducts[startIdx] = leftRunningProduct;
            leftRunningProduct *= array[startIdx];
        }

        // cumulative product from the right
        int rightRunningProduct = 1;
        for (int endIdx = array.length - 1; endIdx >= 0; endIdx--) {
            rightProducts[endIdx] = rightRunningProduct;
            rightRunningProduct *= array[endIdx];
        }

        for (int i = 0; i < array.length; i++) {
            products[i] = leftProducts[i] * rightProducts[i];
        }

        return products;
    }

    // O(n) time | O(n) space
    private int[] arrayOfProd3(int[] array) {
        int[] products = new int[array.length];

        int leftRunningProduct = 1;
        for (int i = 0; i < array.length; i++) {
            products[i] = leftRunningProduct;
            leftRunningProduct *= array[i];
        }

        int rightRunningProduct = 1;
        for (int i = array.length - 1; i >= 0; i--) {
            products[i] *= rightRunningProduct;
            rightRunningProduct *= array[i];
        }

        return products;
    }
}
