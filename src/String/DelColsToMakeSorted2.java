package String;

//LC-955
//We are given an array A of N lowercase letter strings, all of the same length.
//
//        Now, we may choose any set of deletion indices, and for each string, we delete all the characters in those indices.
//
//        For example, if we have an array A = ["abcdef","uvwxyz"] and deletion indices {0, 2, 3}, then the final array after deletions is ["bef","vyz"].
//
//        Suppose we chose a set of deletion indices D such that after deletions, the final array has its elements in lexicographic order (A[0] <= A[1] <= A[2] ... <= A[A.length - 1]).
//
//        Return the minimum possible value of D.length.
//
//
//
//        Example 1:
//
//        Input: ["ca","bb","ac"]
//        Output: 1
//        Explanation:
//        After deleting the first column, A = ["a", "b", "c"].
//        Now A is in lexicographic order (ie. A[0] <= A[1] <= A[2]).
//        We require at least 1 deletion since initially A was not in lexicographic order, so the answer is 1.
//
//        Example 2:
//
//        Input: ["xc","yb","za"]
//        Output: 0
//        Explanation:
//        A is already in lexicographic order, so we don't need to delete anything.
//        Note that the rows of A are not necessarily in lexicographic order:
//        ie. it is NOT necessarily true that (A[0][0] <= A[0][1] <= ...)
//
//        Example 3:
//
//        Input: ["zyx","wvu","tsr"]
//        Output: 3
//        Explanation:
//        We have to delete every column.

import java.util.Arrays;

public class DelColsToMakeSorted2 {

    public static void main(String[] args) {
//        System.out.println(minDeletionSize2(new String[]{"ca","bb","ac"}));
//        System.out.println(minDeletionSize2(new String[]{"zyx","wvu","tsr"}));
//        System.out.println(minDeletionSize2(new String[]{"aa","bb","cc"}));
//        System.out.println(minDeletionSize2(new String[]{"ac","bb","ca"}));

        System.out.println("new");
        System.out.println(minDeletionSize3(new String[]{"ca","bb","ac"}));  //1
        System.out.println(minDeletionSize3(new String[]{"zyx","wvu","tsr"}));//3
        System.out.println(minDeletionSize3(new String[]{"aa","bb","cc"}));   //0
        System.out.println(minDeletionSize3(new String[]{"ac","bb","ca"}));   //0

    }

    // time O(MxN)
    public static int minDeletionSize2(String[] A) {
        int lengthArray = A.length, lengthString = A[0].length(), numDeleteNeeded = 0;

        // array used for inner loop
        boolean[] charSortedFlagForString = new boolean[lengthArray];

        // for each char in string create an array of boolean with length of array to check if lex inc
        // two for loops to iterate through each char in string and each string in array
        // iterate through each char in string
        // process 0th char 0th string, 0th char 1st string, 0th char 2nd string...
        for (int indexChar = 0; indexChar < lengthString; indexChar++) {
            // array used in the outer loop
            boolean[] tempCharSortedFlagArray = new boolean[lengthArray];
            boolean delNeededForCurColFlag = false;
            // iterate up to the -1 index to check the next element
            // iterate through each element in array
            for (int stringIndexInArray = 0; stringIndexInArray < lengthArray - 1; stringIndexInArray++) {
                // don't need to do anything if current char is lexically inc
                if (charSortedFlagForString[stringIndexInArray]) {
                    continue; // goto next iteration in inner for loop
                }

                // str[i-1] > str[i], need deleted
                if (A[stringIndexInArray].charAt(indexChar) > A[stringIndexInArray + 1].charAt(indexChar)) {
                    delNeededForCurColFlag = true;
                    numDeleteNeeded++;
                    break; // goto outer for loop, don't need to iterate the rest of strings
                }
                // not need to delete
                if (A[stringIndexInArray].charAt(indexChar) < A[stringIndexInArray + 1].charAt(indexChar)) {
                    tempCharSortedFlagArray[stringIndexInArray] = true;       // label as lexically inc
                }
            } // end looping array

            // if no deletion needed for current col, update the charSortedFlag array
            // data transformation
            if (!delNeededForCurColFlag) {
                for (int arrayIndex = 0; arrayIndex < lengthArray - 1; arrayIndex++) {
                    //[firstStringFirstCharSortStatus, firstStringSecondCharSortStatus, firstStringThirdCharSortStatus ...]
                    //[secondStringFirstCharSortStatus, secondStringSecondCharSortStatus, secondStringThirdCharSortStatus ...]
                    if (tempCharSortedFlagArray[arrayIndex]) {
                        //[firstStringFirstCharSortStatus, secondStringFirstCharSortStatus, thirdStringFirstCharSortStatus ...]
                        //[firstStringSecondCharSortStatus, secondStringSecondCharSortStatus, thirdStringSecondCharSortStatus ...]
                        charSortedFlagForString[arrayIndex] = true;
                    }
                }
            }
            System.out.println(Arrays.toString(tempCharSortedFlagArray) + " index of char = " + indexChar + " sorted flag " + Arrays.toString(charSortedFlagForString));
        }
        return numDeleteNeeded;
    }

    public static int minDeletionSize(String[] A) {
        int numWords = A.length;

        int[] posColIndex = new int[numWords];
        Arrays.fill(posColIndex, -1);

        int numCols = A[0].length();
        boolean[] deleteFlag = new boolean[numCols];

        int res = 0;
        for (int columnIdx = 0; columnIdx < numCols; ++columnIdx) {
            for (int wordIndex = 1; wordIndex < numWords; ++wordIndex) {
                if (posColIndex[wordIndex] != -1 && !deleteFlag[posColIndex[wordIndex]]) {
                    // if a position is already saved and no
                    // deletion flag goto next iteration
                    continue;
                }

                if (A[wordIndex].charAt(columnIdx) >
                        A[wordIndex - 1].charAt(columnIdx)) {
                    // record col index, it's lex increasing
                    posColIndex[wordIndex] = columnIdx;
                    continue;
                }

                if (A[wordIndex].charAt(columnIdx) <
                        A[wordIndex - 1].charAt(columnIdx)) {
                    // record the col to be deleted and add 1 to res
                    deleteFlag[columnIdx] = true;
                    ++res;
                    break;
                }
            }
        }

        return res;
    }


//    Time Complexity: O(NW), where N is the length of A, and W is the length of A[i].
//
//    Space Complexity: O(N) in additional space complexity.
    public static int minDeletionSize3(String[] A) {
        int arrayLength = A.length;
        int stringLength = A[0].length();

        // lexInc[arrayIndex] is true: we don't need to check any new A[i][j] <= A[i][j+1]
        boolean[] lexInc = new boolean[arrayLength - 1];

        int delNeeded = 0;

        search: for (int charIndex = 0; charIndex < stringLength; charIndex++) {
            System.out.println("lexInc begin loop " + Arrays.toString(lexInc));
            // evaluate whether we can keep this column
            for (int arrayIndex = 0; arrayIndex < arrayLength - 1; arrayIndex++) {
                // if previous char is not lexically increasing and current char is not lexically increasing
                if (!lexInc[arrayIndex] && A[arrayIndex].charAt(charIndex) > A[arrayIndex+1].charAt(charIndex)) {
                    // can't keep the column - delete and continue
                    delNeeded++;
                    // skips the next loop since we already have existing deletion
                    // (not lex inc and needs deletion)
                    // essentially skipping updating the "lexInc" array
                    continue search;
                }
            }

            // update 'lexInc' array, no deletion needed
            // NOTE: an example of populating the array in the end of the loop and
            // checking for the array in the beginning of the loop
            for (int arrayIndex = 0; arrayIndex < arrayLength -1; arrayIndex++) {
                if (A[arrayIndex].charAt(charIndex) < A[arrayIndex+1].charAt(charIndex))
                    // cache the previous char status, if prev char status is lex inc then the next
                    // char does not have to be lex inc. ex:"ac","bb","ca"
                    lexInc[arrayIndex] = true;
            }
            System.out.println(Arrays.toString(lexInc));
        }
        return delNeeded;
    }
}
