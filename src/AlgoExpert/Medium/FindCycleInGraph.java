package AlgoExpert.Medium;

import java.util.*;
public class FindCycleInGraph {
    public static void main(String[] args) {
        int[][] edges = new int[][]{{1,3}, {2,3,4}, {0}, {}, {2,5}, {}};
        System.out.println(cycleInGraph(edges));
    }

    // ex: edges = [[1,3] mapped to node 0, [2,3,4] mapped to node 1,
    // [0] mapped to node 2, [] mapped to node 3,
    // [2,5] mapped to node 4, [] mapped to node 5]
    // 0 -> 1, 0 -> 3, 1 -> 2, 1 -> 3, ...
    // O(v + e) time | O(v) space - where v is the number of vertices
    // and e is the number of edges in the graph

    /**
     * use two bool arrays to keep track of visited nodes and nodes in stack
     */
    private static boolean cycleInGraph(int[][] edges) {
        // number of nodes is represented by the length of the edges
        int numberOfNodes = edges.length;
        boolean[] visited = new boolean[numberOfNodes];
        boolean[] currentlyInStack = new boolean[numberOfNodes];
        Arrays.fill(visited, false);
        Arrays.fill(currentlyInStack, false);

        // for each node find if there is any cycles
        for (int node = 0; node < numberOfNodes; node++) {
            if (visited[node]) {
                // if node is visited just continue on the loop
                continue;
            }

            // start recursion
            boolean containsCycle = traverseNodesFindCycle(node,
                                                edges,
                                                visited,
                                                currentlyInStack);
            // only return when there is a cycle found
            if (containsCycle) return true;

        }
        // exhausted the graph and did not find cycle
        return false;
    }

    /**
     * for each node, if not visited, visit its neighbors
     * check for:
     *  - visited
     *  - currently in stack
     */
    private static boolean traverseNodesFindCycle(int nodeIdx,
                                                  int[][] edges,
                                                  boolean[] visited,
                                                  boolean[] currentlyInStack) {
        System.out.println("node:" + nodeIdx + ",visited:" + Arrays.toString(visited) + ",stack:" + Arrays.toString(currentlyInStack));


        // label the current node as visited and in stack
        visited[nodeIdx] = true;
        currentlyInStack[nodeIdx] = true;

        // init containsCycle
        boolean containsCycle = false;
        // get neighbors for node
        int[] neighborsForCurNode = edges[nodeIdx];
        for (int neighbor : neighborsForCurNode) {
            // if not visited recursively check neighbors
            if (!visited[neighbor]) {
                containsCycle = traverseNodesFindCycle(neighbor,
                                            edges,
                                            visited,
                                            currentlyInStack);
            }

            // get containsCycle and return it if it's true, value obtained from recursive call
            if (containsCycle) {
                return true;
                // else no cycle found check if in stack
            } else if (currentlyInStack[neighbor]) { // found edge back to ancestor, return to recursive call
                // neighbor is in stack and also currently visiting
                return true;
            }
        }

        // remove from stack since we are done traversing
        currentlyInStack[nodeIdx] = false;
        // exhausted all neighbors and did not return true
        // so return not in stack
        return false;
    }

    static int WHITE = 0;  // not visited
    static int GREY = 1;   // visiting / visited
    static int BLACK = 3;  // finished

    //O(v + e) time | O(v) space - where v is the number of
    //vertices and e is the number of edges in the graph
    private static boolean cycleInGraph2(int[][] edges) {
        int numberOfNodes = edges.length;
        // fill nodes with colors
        int[] colors = new int[numberOfNodes];
        // init to white
        Arrays.fill(colors, WHITE);

        for (int node = 0; node < numberOfNodes; node++) {
            if (colors[node] != WHITE) continue;

            boolean containsCycle = traverseAndColorNodes(node,
                                                        edges,
                                                        colors);
            if (containsCycle) return true;
        }

        return false;
    }

    private static boolean traverseAndColorNodes(int node,
                                                 int[][] edges,
                                                 int[] colors) {
        colors[node] = GREY;

        int[] neighbors = edges[node];
        for (int neighbor : neighbors) {
            int neighborColor = colors[neighbor];

            // if already visiting / visited return true for cycle
            if (neighborColor == GREY) return true;

            // if finished traveling this node skip
            if (neighborColor == BLACK) continue;

            boolean containsCycle = traverseAndColorNodes(neighbor,
                                                        edges,
                                                        colors);
            // return immediately if cycle found
            if (containsCycle) return true;
        }

        // finished traveling this node, label it BLACK to indicate finished
        colors[node] = BLACK;
        // exhausted all nodes return false to indicate no cycle found
        return false;
    }
}
