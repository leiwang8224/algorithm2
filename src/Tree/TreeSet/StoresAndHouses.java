package Tree.TreeSet;

import java.util.TreeSet;

//Given two integer arrays houses and stores. Find the nearest
// store for every house. The distance between a store and a
// house is defined as abs(stores[i] - houses[j]). If there
// are multiple stores with the same min distance just output the smaller.
//
//        Example 1:
//
//        Input: houses = [2, 4, 2], stores = [5, 1, 2, 3]
//        Output: [2, 3, 2]
//        Explanation:
//        houses[0] = 2, 2 is the store which has the minimal distance to 2
//        houses[1] = 4, 3 and 5 have the same distance to 4, output 3 because 3 is smaller
//        houses[2] = 2, same as houses[0]
//        Example 2:
//
//        Input: houses = [4, 8, 1, 1], stores = [5, 3, 1, 2, 6]
//        Output: [3, 6, 1, 1]
//        Explanation:
//        houses[0] = 4, 3 and 5 have the same distance to 4 so output 3 because 3 is smaller
//        houses[1] = 8, 6 is the store which has the minimal distance to 8
//        houses[2] = 1, 1 is the store which has the minimal distance to 1
//        houses[3] = 1, same as houses[2]
//        int[] nearestStores(int[] houses, int[] stores);
public class StoresAndHouses {
    public static void main(String[] args) {
        int[] houses = new int[]{2,4,2};
        int[] stores = new int[]{5,1,2,3};

        System.out.println(java.util.Arrays.toString(nearestStores(houses,stores)));

    }

    private static int[] nearestStores(int[] houses, int[] stores) {
        TreeSet<Integer> treeSetStores = new TreeSet<>();
        int lenHouse = houses.length;
        if (stores.length == 0) return new int[0];
        for (int store : stores) treeSetStores.add(store);
        int[] result = new int[lenHouse];

        for (int index = 0; index < lenHouse; index++) {
            int curHouse = houses[index];
            //greatest element in set less than the given house
            Integer largestStoreGreaterThanCurHouse = treeSetStores.floor(curHouse);

            //least element in set greater than the given house
            Integer smallestStoreGreaterThanCurHouse = treeSetStores.ceiling(curHouse);

            if (largestStoreGreaterThanCurHouse == null || smallestStoreGreaterThanCurHouse == null) {
                // set result to smallestElementGreaterThanCurHouse
                // if largestElementGreaterThanCurHouse is null
                result[index] = largestStoreGreaterThanCurHouse == null ?
                        smallestStoreGreaterThanCurHouse :
                        largestStoreGreaterThanCurHouse;
            } else {
                // use smallest distance between the house and store
                result[index] = (curHouse - largestStoreGreaterThanCurHouse <= smallestStoreGreaterThanCurHouse - curHouse) ?
                        largestStoreGreaterThanCurHouse :
                        smallestStoreGreaterThanCurHouse;
            }
        }
        return result;
    }
}
