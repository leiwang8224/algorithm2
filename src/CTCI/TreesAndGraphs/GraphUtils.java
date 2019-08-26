package CTCI.TreesAndGraphs;

public class GraphUtils {
    static class Graph{
        static int MAX_VERTICES = 6;
        private GraphNode vertices[];
        int count;
        Graph() {
            vertices = new GraphNode[MAX_VERTICES];
            count = 0;
        }

        void addNode(GraphNode x) {
            if (count < vertices.length) {
                vertices[count] = x;
                count++;
            }
        }

        GraphNode[] getNodes() {
            return vertices;
        }
    }

    static class GraphNode {
        // Graph Node children
        private GraphNode adjacent[];
        // Number of children
        int adjacentCount;
        // Actual node value
        private String vertex;
        boolean visited;

        /**
         * Graph Node init
         * @param vertex: value of the node
         * @param adjacentLength: number of children
         */
        GraphNode(String vertex, int adjacentLength) {
            this.vertex = vertex;
            adjacentCount = 0;
            adjacent = new GraphNode[adjacentLength];
        }

        /**
         * Add children
         * @param x
         */
        void addAdjacent(GraphNode x) {
            if (adjacentCount < adjacent.length) {
                 this.adjacent[adjacentCount] = x;
                 adjacentCount++;
            }
        }

        /**
         * Return all children
         * @return
         */
        GraphNode[] getAdjacent() {
            return adjacent;
        }

        /**
         * Return the value of the graph node
         * @return
         */
        String getVertex() {
            return vertex;
        }
    }
}
