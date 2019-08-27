package CTCI.TreesAndGraphs;

import java.util.ArrayList;
import java.util.LinkedList;

import CTCI.TreesAndGraphs.TreeUtils.TreeNode;

/**
 * A binary search tree was created by traversing through an array from left to right
 * and inserting each element. Given a BST with distinct elements, print all possible
 * arrays that could have led to this tree.
 *
 * The root must always be inserted first. Any sequence of insertions that produces
 * its left subtree can be paired with any sequence of insertions that produces the
 * right subtree. Additionally, the two sequences can be interleaved arbitrarily.
 */
public class BSTSequencesToArray {
    public static void main(String[] args) {
        ArrayList<LinkedList<Integer>> result = getAllSequences(TreeUtils.generateBST());
        for (LinkedList<Integer> lst : result) {
            for (Integer num : lst) {
                System.out.print(num + ",");
            }
            System.out.println();
        }
    }

    //TODO not understanding this

    /**
     * ex: array1: {1,2}
     *     array2: {3,4}
     * weaved: {1,2,3,4}, {1,3,2,4}, {1,3,4,2},
     *         {3,1,2,4}, {3,1,4,2}, {3,4,1,2}
     * soln for given {1,2,3} and {4,5,6}
     * - Prepend a 1 to all weaves of {2,3} and {4,5,6}
     * - Prepend a 4 to all weaves of {1,2,3} and {5,6}
     * Works something like this:
     * weave(first, second, prefix):
     *  weave({1,2},{3,4},{})           remove 1 for this call and put in prefix
     *    weave({2},{3,4},{1})          remove 2 for this call and put in prefix
     *      weave({},{3,4},{1,2})       first list is empty so return (unwrap recursion)
     *         {1,2,3,4}                add prefix to the second list since first list is empty
     *      weave({2},{4},{1,3})
     *        weave({},{4},{1,3,2})
     *           {1,3,2,4}
     *        weave({2},{},{1,3,4})
     *           {1,3,4,2}
     *    weave({1,2},{4},{3})          note 1 is put back in for another recursion
     *      weave({2},{4},{3,1})
     *        weave({},{4},{3,1,2})
     *          {3,1,2,4}
     *        weave({2},{},{3,1,4})
     *          {3,1,4,2}
     *      weave({1,2},{},{3,4})
     *        {3,4,1,2}
     *
     * Take note that we need to remove 1 from {1,2} and recurse. We need
     * to be careful about modifying this list, since a later recursive call
     * (eg.weave({1,2},{4},{3}) might need the 1 still in {1,2}.
     * @param first
     * @param second
     * @param results
     * @param prefix
     */
    static void weaveLists(LinkedList<Integer> first,
                           LinkedList<Integer> second,
                           ArrayList<LinkedList<Integer>> results,
                           LinkedList<Integer> prefix) {
        // one list is empty. add the remainder to [a cloned] prefix and
        // store result
        if (first.size() == 0 || second.size() == 0) {
            LinkedList<Integer> result = new LinkedList<>();
            for (Integer num : prefix) {
                result.add(num);
            }
            result.addAll(first);  // first or second has 0 items
            result.addAll(second);
            results.add(result);
            return;
        }

        //recurse with head of first added to the prefix. Removing the
        //head will damage first, so we will need to put it back where
        //we found it afterwards
        int headFirst = first.removeFirst();
        // prefix add the headFirst element and recurse
        prefix.addLast(headFirst);
        // recurse with prefix adding the headFirst element, prefix grows
        // first shrinks
        weaveLists(first, second, results, prefix);
        // remove the last element from prefix after recursion returns
        prefix.removeLast();
        // recover the first list
        first.addFirst(headFirst);

        //do the same thing with second, damaging and then restoring
        //the list.
        int headSecond = second.removeFirst();
        // prefix add the headSecond element and recurse
        prefix.addLast(headSecond);
        // recurse
        weaveLists(first, second, results, prefix);
        // remove the last element from prefix
        prefix.removeLast();
        // recover the second list
        second.addFirst(headSecond);
    }

    static ArrayList<LinkedList<Integer>> getAllSequences(TreeNode node) {
        ArrayList<LinkedList<Integer>> results = new ArrayList<>();

        if (node == null) {
            // add empty list if node is null
            results.add(new LinkedList<Integer>());
            return results;
        }

        // put the node in the prefix
        LinkedList<Integer> prefix = new LinkedList<>();
        prefix.add(node.val);

        //recurse on left and right subtrees
        ArrayList<LinkedList<Integer>> leftSeq = getAllSequences(node.left);
        ArrayList<LinkedList<Integer>> rightSeq = getAllSequences(node.right);

        //weave together each list from the left and right side
        //iterate through left and right (nested for loop to weave together)
        for (LinkedList<Integer> left : leftSeq) {
            for (LinkedList<Integer> right : rightSeq) {
                ArrayList<LinkedList<Integer>> combined = new ArrayList<>();
                weaveLists(left, right, combined, prefix);
                results.addAll(combined);
            }
        }

        return results;
    }


}
