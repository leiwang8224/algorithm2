package Graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class DFS {
    public static void main(String[] args) {
        GraphNode rootNode = new GraphNode("apple");
        rootNode.adjacentNodes = new LinkedList<>();
        GraphNode leftNode = new GraphNode("banana");
        GraphNode rightNode = new GraphNode("mango");
        rootNode.adjacentNodes.add(leftNode);
        rootNode.adjacentNodes.add(rightNode);

        GraphNode leftLeftNode = new GraphNode("peach");
        GraphNode leftRightNode = new GraphNode("strawberry");
        GraphNode leftLeftRightNode = new GraphNode("cherry");
        
        leftNode.adjacentNodes = new LinkedList<>();
        leftNode.adjacentNodes.add(leftLeftNode);
        leftNode.adjacentNodes.add(leftRightNode);

        leftLeftNode.adjacentNodes = new LinkedList<>();
        leftLeftNode.adjacentNodes.add(leftLeftRightNode);

        leftRightNode.adjacentNodes.add(leftLeftRightNode);
        rightNode.adjacentNodes.add(leftRightNode);

        System.out.println(dfs(rootNode,"cherry"));
        System.out.println(bfs(rootNode,"cherry"));




    }

//    Example:
//          apple
//         /    \
//    banana   mango
//    /     \    /
//peach   strawberry
//    \     /
//    cherry
//
//    Find cherry ==> true
    private static boolean dfs(GraphNode rootNode, String data) {
        Stack<GraphNode> s = new Stack<>();
        s.add(rootNode);
        rootNode.visited = true;

        while (!s.isEmpty()) {
            GraphNode n = s.pop();
            if (n.data != null && n.data.equals(data)) {
                return true;
            }

            for (GraphNode adj : n.adjacentNodes) {
                if (!adj.visited) {
                    adj.visited = true;
                    s.push(adj);
                }
            }
        }
        return false;
    }

    private static boolean bfs(GraphNode rootNode, String data) {
        Queue<GraphNode> q = new LinkedList<>();

        q.offer(rootNode);

        while (!q.isEmpty()) {
            GraphNode node = q.poll();

            if (node != null) {
                if (node.data != null && node.data.equals(data)) {
                    return true;
                }

                node.visited = true;

                for (GraphNode n : node.adjacentNodes) {
                    if (n.visited == false) {
                        q.offer(n);
                    }
                }
            }
        }

        return false;
    }

    static class GraphNode {
        public String data;
        public boolean visited = false;
        public List<GraphNode> adjacentNodes = new LinkedList<>();
        public GraphNode(String data) {
            this.data = data;
        }

        public void addAdjNode(final GraphNode node) {
            adjacentNodes.add(node);
            node.adjacentNodes.add(this);
        }
    }
}
