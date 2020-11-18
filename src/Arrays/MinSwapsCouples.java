package Arrays;

import java.util.HashMap;

// LC-765
public class MinSwapsCouples {
    public static void main(String[] args) {
        int row[] = new int[]{0,2,1,3};
        int row1[] = new int[]{0,2,1,3};
        int row2[] = new int[]{0,2,1,3};

        System.out.println(minSwapsCouples(row));
        System.out.println(minSwapCouples3(row1));
        System.out.println(minSwapsCouples2(row2));
    }

    // time O(2n)
    // space O(n)
    // 1. form hash of <elements, indices>
    // 2. iterate through indices and calculate for the partner location and determine whether swap is needed
    // 3. perform swap and update hash with new positions (current and partner)
    private static int minSwapsCouples(int[] row) {
        HashMap<Integer, Integer> map = new HashMap<>();

        // map <elements, location>
        for (int index = 0; index < row.length; index++) {
            map.put(row[index], index);
        }

        Integer firstElement = Integer.MIN_VALUE;
        Integer secondElement = Integer.MIN_VALUE;

        int swaps = 0;
        int partnerFirstElement = 0;

        for (int currentIndex = 0; currentIndex < row.length; currentIndex++) {
            // to ensure the firstElement is processed before secondElement
            if (firstElement == Integer.MIN_VALUE) {
                firstElement = row[currentIndex];
            } else {
                secondElement = row[currentIndex];
                // 2 / 2 = 1, 3 / 2 = 1 so 2 and 3 are a pair, just move on
                if (Math.round(firstElement / 2) == Math.round(secondElement/2)) {
                    firstElement = Integer.MIN_VALUE;
                    secondElement = Integer.MIN_VALUE;
                } else { // else firstElement and secondElement is not a pair, swaps needed
                    swaps += 1;
                    // look for the partner of the first element
                    if (firstElement % 2 == 0) { // if even add 1, else odd subtract 1
                        partnerFirstElement = firstElement + 1;
                    } else {
                        partnerFirstElement = firstElement - 1;
                    }

                    // get index for the partner of first element
                    int indexFirstElementPartner = map.get(partnerFirstElement);
                    int valueInCurrentIndex = row[currentIndex];

                    // perform swap for the partner of the first element with
                    // the current element from row
                    row[currentIndex] = row[indexFirstElementPartner];
                    row[indexFirstElementPartner] = valueInCurrentIndex;

                    // update map with the new configuration after swap
                    // two elements updated, currentIndex and partner for currentIndex
                    map.put(row[currentIndex], currentIndex);
                    map.put(row[indexFirstElementPartner], indexFirstElementPartner);

                    // remember to reset the firstElement and secondElement for next iteration
                    firstElement = Integer.MIN_VALUE;
                    secondElement = Integer.MIN_VALUE;
                }
            }
        }
        return swaps;
    }

    public static int minSwapCouples3(int[] row) {
        int count = 0;
        HashMap<Integer, Integer> position = new HashMap<>();

        // put into map<value, position>
        for (int index = 0; index < row.length; index++) {
            position.put(row[index], index);
        }

        // get each element and respective partner
        for (int index = 0; index < row.length / 2; index++) {
            int curElement = row[2 * index];
            int partnerElement = row[2 * index + 1];

            // if partner not next to the element, swap and update the map
            if (Math.round(curElement /2) != Math.round(partnerElement / 2)) {
                // target partner position is calculated from the map, if even partner is +1, else odd partner is -1
                int targetPartnerPosition = curElement % 2 == 0 ? position.get(curElement+1) : position.get(curElement-1);
                int curPartnerPosition = 2 * index+1;
                swap(row, curPartnerPosition, targetPartnerPosition);
                // update the map with the new positions for the swap
                position.put(row[curPartnerPosition], curPartnerPosition);
                position.put(row[targetPartnerPosition], targetPartnerPosition);
                count++;
            }
        }
        return count;
    }

    public static int minSwapsCouples2(int[] row) {
        int res = 0, rowLength = row.length;

        int[] partner = new int[rowLength];
        int[] pos = new int[rowLength];

        for (int i = 0; i < rowLength; i++) {
            partner[i] = (i % 2 == 0 ? i + 1 : i - 1);
            pos[row[i]] = i;
        }

        for (int index = 0; index < rowLength; index++) {
            for (int partnerIndex = partner[pos[partner[row[index]]]]; index != partnerIndex; partnerIndex = partner[pos[partner[row[index]]]]) {
                swap(row, index, partnerIndex);
                swap(pos, row[index], row[partnerIndex]);
                res++;
            }
        }

        return res;
    }

    private static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
