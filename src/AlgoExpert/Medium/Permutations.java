package AlgoExpert.Medium;

import java.util.ArrayList;

import static java.util.Collections.swap;

public class Permutations {
    public static void main(String[] args) {

    }

    // upper bound: O(n^2*n!) time | O(n*n!) space
    // roughly O(n*n!) time | O(n*n!) space

    /**
     * Extra list var is used to store the current permutation to be added
     */
    private static java.util.List<java.util.List<Integer>> getPermutations(java.util.List<Integer> array) {
        java.util.List<java.util.List<Integer>> permutations = new ArrayList<>();
        getPermutations(array, new ArrayList<>(), permutations);
        return permutations;
    }

    private static void getPermutations(java.util.List<Integer> givenArray,
                                        java.util.List<Integer> currentPermutation,
                                        java.util.List<java.util.List<Integer>> permutations) {
        if (givenArray.size() == 0 && currentPermutation.size() > 0)
            permutations.add(currentPermutation);
        else {
            for (int index = 0; index < givenArray.size(); index++) {
                // take a copy of the current array
                java.util.List<Integer> newArray = new ArrayList<>(givenArray);
                // remove element at index
                newArray.remove(index);
                // new permutation is current permutation add the element removed from new array
                java.util.List<Integer> newPermutation = new ArrayList<>(currentPermutation);
                newPermutation.add(givenArray.get(index));
                getPermutations(newArray, newPermutation, permutations);
            }
        }
    }

    /**
     * Permutation: Changing the ordering of the numbers in the original orray
     * Main idea is to swap the indices, iterate starting from index given in the method to the end of given array
     * swap between the iterating index with the starting index
     * recurse with index + 1
     * swap it back after recursion
     */
    // O(n*n!) time | O(n*n!) space
    private static java.util.List<java.util.List<Integer>> getPermutations2(java.util.List<Integer> array) {
        java.util.List<java.util.List<Integer>> permutations = new ArrayList<>();
        getPermutations2(0, array, permutations);
        return permutations;
    }

    private static void getPermutations2(int index,
                                         java.util.List<Integer> givenArray,
                                         java.util.List<java.util.List<Integer>> permutations) {
        // base case is to add the array to permutations and return
        if (index == givenArray.size() - 1) {
            // manipulation is done in the original array so just add to the result
            // when we reach the end of the array
            permutations.add(new ArrayList<>(givenArray));  //<----THIS IS IMPORTANT!
        } else {
            // iterate from the current index up to the size of the array
            for (int curIndex = index; curIndex < givenArray.size(); curIndex++) {
                // swap elements, recurse to next index, re-swap elements to get back to original for the next swap
                swap(givenArray, index, curIndex);
                getPermutations2(index + 1, givenArray, permutations);
                swap(givenArray, index, curIndex);
            }
        }
    }
}
