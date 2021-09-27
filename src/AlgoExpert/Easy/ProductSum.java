package AlgoExpert.Easy;

import java.util.ArrayList;
import java.util.List;

public class ProductSum {
    public static void main(String[] args) {

    }

    // Time = O(n) | Space = O(d) depth of array
    private static int productSum(java.util.List<Object> array) {
        return productSumHelper(array, 1);
    }

    private static int productSumHelper(List<Object> array, int mult) {
        // intialize sum to 0 everytime we go into an array
        int sum = 0;
        // iterate through each object in array w/o regard to index
        for (Object item : array) {
            // if object arraylist then call recursively
            if (item instanceof ArrayList) {
                // why do you need this step? making a copy of the list before
                // passing it to recursion?
                ArrayList<Object> list = (ArrayList<Object>) item;
                sum += productSumHelper(list, mult + 1);
            } else {
                sum += (int) item;
            }
        }
        return sum * mult;
    }
}
