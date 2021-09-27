package AlgoExpert.VeryHard;
import java.util.*;
public class TwoEdgeConnectedGraph {
    // O(v + e) time | O(v) space - where v is the number of vertices
    // and e is the number of edges in the graph
    // solve using the bridge property of graphs
    // if back edge exists then it must be a two edge connected graph (back edge connects back to the node before)
    boolean twoEdgeConnectedGraph(int[][] edges) {
        // if there are 0 edges it's a two edge connected graph (based on definition)
        if (edges.length == 0) return true;

        int[] arrivalTimes = new int[edges.length];
        Arrays.fill(arrivalTimes, -1); // init arrival times to -1 for start
        int startVertex = 0;

        if (getMinArrivalTimeOfAncestorsDFS(startVertex, -1, 0, arrivalTimes, edges) == -1)
            return false;

        return areAllVerticesVisited(arrivalTimes);
    }

    private boolean areAllVerticesVisited(int[] arrivalTimes) {
        for (int time : arrivalTimes) {
            if (time == -1) return false;
        }

        return true;
    }

    int getMinArrivalTimeOfAncestorsDFS(int curVertex, int parent, int curTime, int[] arrivalTimes, int[][] edges) {
        arrivalTimes[curVertex] = curTime;

        int minArrivalTime = curTime;

        for (int destination : edges[curVertex]) {
            if (arrivalTimes[destination] == -1) { // vertex not visited, so call dfs on all children nodes
                minArrivalTime = Math.min(minArrivalTime,
                        getMinArrivalTimeOfAncestorsDFS(destination, curVertex, curTime + 1, arrivalTimes, edges));
            } else if (destination != parent) { // vertex already visited so we will process the arrival time at vertex
                minArrivalTime = Math.min(minArrivalTime, arrivalTimes[destination]);
            }
        }

        // a bridge was detected, which means the graph isn't two edge connected
        if (minArrivalTime == curTime && parent != -1) return -1;

        return minArrivalTime;
    }
}
