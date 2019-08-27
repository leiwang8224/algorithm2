package CTCI.TreesAndGraphs;

import java.util.ArrayList;
import java.util.LinkedList;

import CTCI.TreesAndGraphs.TreeUtils.TreeNode;

public class ListOfDepthTree {
    public static void main(String[] args) {
        printListOfList(getListEachLevelBFS(TreeUtils.generateTree()));

        ArrayList<LinkedList<TreeNode>> result = new ArrayList<>();
        getListEachLevelDFS(TreeUtils.generateTree(),result, 0);
        printListOfList(result);
    }

    private static void printListOfList (ArrayList<LinkedList<TreeNode>> list) {
         for (LinkedList<TreeNode> l : list) {
             while (!l.isEmpty()) {
                 System.out.print(l.poll().val + "->");
             }
             System.out.println();
         }
    }

    
    private static ArrayList<LinkedList<TreeNode>> getListEachLevelBFS(TreeNode root) {
        ArrayList<LinkedList<TreeNode>> result = new ArrayList<>();

        LinkedList<TreeNode> currentLevelQueue = new LinkedList<>();

        currentLevelQueue.offer(root);

        while(!currentLevelQueue.isEmpty()) {
            result.add(currentLevelQueue);  // add previous level

            // make a copy of previous level
            LinkedList<TreeNode> previousLevelQueue = currentLevelQueue; // goto next level

            // make room for the current level
            currentLevelQueue = new LinkedList<TreeNode>();

            // traverse the previous level to get current level children
            for (TreeNode prevLevelNode : previousLevelQueue) {
                // visit the children and cache the nodes information
                if (prevLevelNode.left != null) currentLevelQueue.add(prevLevelNode.left);
                if (prevLevelNode.right != null) currentLevelQueue.add(prevLevelNode.right);
            }
        }

        return result;
    }

    private static void getListEachLevelDFS(TreeNode root, ArrayList<LinkedList<TreeNode>> lists, int level) {
        if (root == null)
            return;
        LinkedList<TreeNode> currentLevelList = null;

        if (lists.size() == level) { // level not contained in list
            currentLevelList = new LinkedList<TreeNode>();
            // levels are always traversed in order.
            // if this is the first time we have visited level i,
            // we must have seen levels - through i - 1.
            // We can therefore safely add the level at the end
            lists.add(currentLevelList);
        } else {
            currentLevelList = lists.get(level);
        }

        currentLevelList.add(root);

        // dfs traversal, root, left then right
        getListEachLevelDFS(root.left, lists, level+1);
        getListEachLevelDFS(root.right, lists, level+1);
    }
}
