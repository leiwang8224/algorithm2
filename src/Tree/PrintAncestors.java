package Tree;

import java.util.ArrayList;

public class PrintAncestors {
    ArrayList<Integer> ancestorList = new ArrayList<>();
    public static void main(String[] args) {

    }

    private boolean printAncestors(ListNode root, int nodeData) {
        if (root == null) return false;

        if (root.getVal() == nodeData)
            return true;
        if( printAncestors(root.left,nodeData) == true ||
            printAncestors(root.right,nodeData) == true) {
            ancestorList.add(root.getVal());
            return true;
        } else {
            return false;
        }
    }
}
