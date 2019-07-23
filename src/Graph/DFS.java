package Graph;

import java.util.LinkedList;
import java.util.List;
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
