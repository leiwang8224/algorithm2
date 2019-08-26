package CTCI.TreesAndGraphs;

import Graph.Graph;

import java.util.LinkedList;
import java.util.Queue;

// Given a directed graph, find if there is a route
// between two nodes.
public class GraphRouteBetweenNodes {
    public static void main(String[] args) {
        GraphUtils.Graph g = new GraphUtils.Graph();
        GraphUtils.GraphNode[] graph = new GraphUtils.GraphNode[6];

        graph[0] = new GraphUtils.GraphNode("a",3);
        graph[1] = new GraphUtils.GraphNode("b",0);
        graph[2] = new GraphUtils.GraphNode("c",0);
        graph[3] = new GraphUtils.GraphNode("d",1);
        graph[4] = new GraphUtils.GraphNode("e",1);
        graph[5] = new GraphUtils.GraphNode("f",0);

        graph[0].addAdjacent(graph[1]);
        graph[0].addAdjacent(graph[2]);
        graph[0].addAdjacent(graph[3]);
        graph[3].addAdjacent(graph[4]);
        graph[4].addAdjacent(graph[5]);

        for (GraphUtils.GraphNode node : graph) {
            g.addNode(node);
        }


        System.out.println(findRoute(g, graph[3], graph[4]));

    }

    /**
     * BFS search for the route
     * @param g
     * @param node1
     * @param node2
     * @return
     */
    private static boolean findRoute(GraphUtils.Graph g,
                                     GraphUtils.GraphNode node1,
                                     GraphUtils.GraphNode node2) {
        // use a queue to perform bfs traversal
        Queue<GraphUtils.GraphNode> q = new LinkedList<>();

        // extract all of the graph nodes and init to all not visisted
        for (GraphUtils.GraphNode node : g.getNodes()) {
            node.visited = false;
        }

        // set node1 to visited, start traversal from node1
        node1.visited = true;
        q.add(node1);
        GraphUtils.GraphNode nodeOfInterest;
        while (!q.isEmpty()) {
            nodeOfInterest = q.poll();
            // extract all children and check if there is a connection
            if (nodeOfInterest != null) {
                for (GraphUtils.GraphNode childrenOfNode : nodeOfInterest.getAdjacent()) {
                    // only check if it's not visited before
                    if (childrenOfNode.visited == false) {
                        // if children is node2 then there is a connection
                        if (childrenOfNode == node2) {
                            return true;
                            // else set visited to true and move on
                            // remember to add the current node to the queue
                        } else {
                            childrenOfNode.visited = true;
                            q.add(childrenOfNode);
                        }
                    }
                }
                // this node is done so set visited to true
                nodeOfInterest.visited = true;
            }
        }
        return false;
    }
}
