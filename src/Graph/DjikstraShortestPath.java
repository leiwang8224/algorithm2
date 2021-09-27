package Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class DjikstraShortestPath {
//    Example:
//
//    V2
//    |      \
//    |10     \3
//    |   5    \     7
//    V0 ——-- V1 ——— V4
//      \               /
//        \8          /2
//           \      /
//              V3
//
//            v0 = Rville
//            v1 = Bville
//            v2 = Gville
//            v3 = Oville
//            v4 = Pville
//
//    Shortest Path to V3 from V0 = [Rville, Oville]
    // assumptions: all positive edges and no self loops
    public static void main(String[] args) {
        Vertex V2 = new Vertex("Gville");
        Vertex V0 = new Vertex("Rville");
        Vertex V1 = new Vertex("Bville");
        Vertex V4 = new Vertex("Pville");
        Vertex V3 = new Vertex("Oville");

        V2.adjacencies = new Edge[2];
        V0.adjacencies = new Edge[2];
        V1.adjacencies = new Edge[3];
        V4.adjacencies = new Edge[2];
        V3.adjacencies = new Edge[2];

        V2.adjacencies[0] = new Edge(V0, 10);
        V2.adjacencies[1] = new Edge(V1,3);

        V0.adjacencies[0] = new Edge(V2,10);
        V0.adjacencies[1] = new Edge(V1,5);

        V1.adjacencies[0] = new Edge(V2,3);
        V1.adjacencies[1] = new Edge(V0,5);
        V1.adjacencies[2] = new Edge(V4,7);

        V4.adjacencies[0] = new Edge(V1,7);
        V4.adjacencies[1] = new Edge(V3,2);

        V3.adjacencies[0] = new Edge(V0,8);
        V3.adjacencies[1] = new Edge(V4,2);

        List<Vertex> result = getShortestPath(V0,V3);

        for (Vertex vertex : result) {
            System.out.println(vertex.name);
        }


    }

    private static List<Vertex> getShortestPath(Vertex source, Vertex target) {
        computePaths(source);
        return getShortestPathTo(target);
    }
    private static List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<>();

        // add all of the vertex into the list
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);

        // reverse sort based on the min distance, shortest first, longest last
        Collections.reverse(path);
        return path;
    }

    // helper function to compute shortest path and store in each vertex
    private static void computePaths(Vertex source) {
        source.minDistance = 0;
        // retrieves with log(n) time
        PriorityQueue<Vertex> vertexPriorityQueue = new PriorityQueue<>();

        //BFS traversal
        vertexPriorityQueue.add(source);

        // O((v + e) * log(v))
        while (!vertexPriorityQueue.isEmpty()) {
            // this poll always returns the shortest distance vertex at log(v) time
            Vertex vertex = vertexPriorityQueue.poll();

            //visit each edge exiting vertex (adjacencies)
            for (Edge edgeInVertex : vertex.adjacencies) {
                // calculate new distance between edgeInVertex and target
                Vertex targetVertex = edgeInVertex.target;
                double edgeWeightForThisVertex = edgeInVertex.weight;
                double distanceThruVertex = vertex.minDistance + edgeWeightForThisVertex;
                if (distanceThruVertex < targetVertex.minDistance) {
                    // modify the targetVertex with new calculated minDistance and previous vertex
                    // by removing the old vertex and add new vertex with updates
                    vertexPriorityQueue.remove(targetVertex);
                    targetVertex.minDistance = distanceThruVertex;
                    // update previous with the shortest distance vertex
                    targetVertex.previous = vertex;
                    vertexPriorityQueue.add(targetVertex);// adding takes log(v) time because needs to heapify
                }
            }
        }
    }

    static class Vertex implements Comparable<Vertex> {

        public final String name;
        public Edge[] adjacencies;
        // minDistance to keep track of the minimum distances
        public double minDistance = Double.POSITIVE_INFINITY;
        public Vertex previous; // previous optimal path node

        public Vertex(String argName) {
            name = argName;
        }

        public String toString() {
            return name;
        }

        @Override
        public int compareTo(Vertex other) {
            return Double.compare(minDistance, other.minDistance);
        }
    }

    static class Edge {
        public final Vertex target; // target node of the edge
        public final double weight; // edge weight

        public Edge(Vertex target, double weight) {
            this.target = target;
            this.weight = weight;
        }
    }
}
