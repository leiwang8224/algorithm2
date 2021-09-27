package AlgoExpert.Medium;

public class GetYongestCommonAncestor {
    public static void main(String[] args) {

    }

    private static AncestralTree getYoungestCommonAncestor(AncestralTree topAncestor,
                                                           AncestralTree descendantOne,
                                                           AncestralTree descendantTwo) {
        int depthOne = getDescendantDepth(descendantOne, topAncestor);
        int depthTwo = getDescendantDepth(descendantTwo, topAncestor);

        if (depthOne > depthTwo) {
            return backtrackAncestralTree(descendantOne,
                                        descendantTwo,
                                    depthOne - depthTwo);
        } else {
            return backtrackAncestralTree(descendantTwo,
                                        descendantOne,
                                    depthTwo - depthOne);
        }
    }

    private static AncestralTree backtrackAncestralTree(AncestralTree lowerDescendant,
                                                        AncestralTree higherDescendant,
                                                        int diff) {
        while (diff > 0) {
            lowerDescendant = lowerDescendant.ancestor;
            diff--;
        }
        while(lowerDescendant != higherDescendant) {
            lowerDescendant = lowerDescendant.ancestor;
            higherDescendant = higherDescendant.ancestor;
        }

        return lowerDescendant;
    }

    private static int getDescendantDepth(AncestralTree descendant,
                                          AncestralTree topAncestor) {
        int depth = 0;
        while (descendant != topAncestor) {
            depth++;
            descendant = descendant.ancestor;
        }

        return depth;
    }

    static class AncestralTree {
        public char name;
        public AncestralTree ancestor;

        AncestralTree(char name) {
            this.name = name;
            this.ancestor = null;
        }
    }
}
