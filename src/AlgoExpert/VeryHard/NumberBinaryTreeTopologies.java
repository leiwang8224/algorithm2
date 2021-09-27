package AlgoExpert.VeryHard;
import java.util.*;

public class NumberBinaryTreeTopologies {
    // Upper bound: O((n*(2n)!)/(n!(n+1)!)) time | O(n) space
    int numberOfBinaryTreeTopologiesRecurse(int nNumberNodes) {
        if (nNumberNodes == 0) return 1;
        int numberTreeToplogies = 0;
        // iterate through the left tree size from 0 to the number of nodes
        // and calculate the right tree size
        for (int leftTreeSize = 0; leftTreeSize < nNumberNodes; leftTreeSize++) {
            int rightTreeSize = nNumberNodes - 1 - leftTreeSize;
            int numberOfLeftSubtreeToplogies =
                    numberOfBinaryTreeTopologiesRecurse(leftTreeSize);
            int numberOfRightSubtreeTopologies =
                    numberOfBinaryTreeTopologiesRecurse(rightTreeSize);
            numberTreeToplogies +=
                    numberOfLeftSubtreeToplogies * numberOfRightSubtreeTopologies;
        }
        return numberTreeToplogies;
    }

    // O(n^2) time | O(n) space
    int numberOfBinaryTreeTopologiesMapCache(int nNumberNodes) {
        Map<Integer, Integer> cacheNumNodesToNumTopologies = new HashMap<>();
        // for 0 nodes there is 1 topology
        cacheNumNodesToNumTopologies.put(0, 1);
        return numberOfBinaryTreeTopologiesMapCache(nNumberNodes, cacheNumNodesToNumTopologies);
    }

    int numberOfBinaryTreeTopologiesMapCache(int nNumberOfNodes,
                                             Map<Integer, Integer> cacheNumNodesToNumTopologies) {
        if (cacheNumNodesToNumTopologies.containsKey(nNumberOfNodes)) {
            return cacheNumNodesToNumTopologies.get(nNumberOfNodes);
        }

        int numberOfTreeTopologies = 0;
        for (int leftTreeSize = 0; leftTreeSize < nNumberOfNodes; leftTreeSize++) {
            int rightTreeSize = nNumberOfNodes - 1 - leftTreeSize;
            int numberOfLeftTrees =
                    numberOfBinaryTreeTopologiesMapCache(leftTreeSize,
                                                  cacheNumNodesToNumTopologies);
            int numberOfRightTrees =
                    numberOfBinaryTreeTopologiesMapCache(rightTreeSize,
                                                  cacheNumNodesToNumTopologies);
            numberOfTreeTopologies += numberOfLeftTrees * numberOfRightTrees;
        }

        cacheNumNodesToNumTopologies.put(nNumberOfNodes, numberOfTreeTopologies);
        return numberOfTreeTopologies;
    }

    // O(n^2) time | O(n) space
    int numberOfBinaryTreeTopologiesIterative(int nNumberOfNodes) {
        List<Integer> cache = new ArrayList<>();
        cache.add(1);
        for (int totalTreeSize = 1; totalTreeSize < nNumberOfNodes + 1; totalTreeSize++) {
            int numberOfTrees = 0;
            for (int leftTreeSize = 0; leftTreeSize < totalTreeSize; leftTreeSize++) {
                int rightTreeSize = totalTreeSize - 1 - leftTreeSize;
                int numberOfLeftTrees = cache.get(leftTreeSize);
                int numberOfRightTrees = cache.get(rightTreeSize);
                numberOfTrees += numberOfLeftTrees * numberOfRightTrees;
            }
            cache.add(numberOfTrees);
        }
        return cache.get(nNumberOfNodes);
    }
}
