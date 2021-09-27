package AlgoExpert.Hard;

public class ValidateThreeNodes {

    static class BST {
        public int value;
        public BST left = null;
        public BST right = null;

        public BST(int value) {
            this.value = value;
        }
    }

    // O(h) time | O(h) space - where h is the height of the tree
    boolean validateThreeNodes(BST nodeOne, BST nodeTwo, BST nodeThree) {
        if (isDescendant(nodeTwo, nodeOne)) {
            return isDescendant(nodeThree, nodeTwo);
        }

        if (isDescendant(nodeTwo, nodeThree)) {
            return isDescendant(nodeOne, nodeTwo);
        }

        return false;
    }

    private boolean isDescendant(BST node, BST target) {
        if (node == null) return false;

        if (node == target) return true;

        return (target.value < node.value)
                ? isDescendant(node.left, target)
                : isDescendant(node.right, target);
    }

    //O(h) time | O(1) space
    boolean validateThreeNodes2(BST nodeOne, BST nodeTwo, BST nodeThree) {
        if(isDescendant2(nodeTwo, nodeOne)) {
            return isDescendant2(nodeThree, nodeTwo);
        }

        if (isDescendant2(nodeTwo, nodeThree)) {
            return isDescendant2(nodeOne, nodeTwo);
        }

        return false;
    }

    boolean isDescendant2(BST node, BST target) {
        while (node != null && node != target) {
            node = (target.value < node.value) ?
                    node.left : node.right;
        }

        return node == target;
    }

    // O(d) time | O(1) space where d is distance between nodeOne and nodeThree
    boolean validateThreeNodes3(BST nodeOne, BST nodeTwo, BST nodeThree) {
        BST searchOne = nodeOne;
        BST searchTwo = nodeThree;

        while (true) {
            boolean foundThreeFromOne = searchOne == nodeThree;
            boolean foundOneFromThree = searchTwo == nodeOne;
            boolean foundNodeTwo = (searchOne == nodeTwo) ||
                    (searchTwo == nodeTwo);
            boolean finishedSearching = (searchOne == null) && (searchTwo == null);

            if (foundThreeFromOne ||
                    foundOneFromThree ||
                    foundNodeTwo ||
                    finishedSearching) {
                break;
            }

            if (searchOne != null) {
                searchOne = (searchOne.value > nodeTwo.value) ?
                        searchOne.left : searchOne.right;
            }

            if (searchTwo != null) {
                searchTwo = (searchTwo.value > nodeTwo.value) ?
                        searchTwo.left : searchTwo.right;
            }
        }

        boolean foundNodeFromOther = (searchOne == nodeThree) ||
                (searchTwo == nodeOne);

        boolean foundNodeTwo = (searchOne == nodeTwo) ||
                (searchTwo == nodeTwo);

        if (!foundNodeTwo || foundNodeFromOther) return false;

        return searchForTarget(nodeTwo,
                (searchOne == nodeTwo) ? nodeThree : nodeOne);
    }

    private boolean searchForTarget(BST node, BST target) {
        while (node != null && node != target) {
            node = (target.value < node.value) ?
                    node.left : node.right;
        }

        return node == target;
    }
}
