package AlgoExpert.Medium;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFSGraph {
    static class Node {
        String name;
        java.util.List<Node> children = new ArrayList<>();

        Node(String name) {
            this.name = name;
        }

        // O(v + e) time | O(v) space
        java.util.List<String> bfs(java.util.List<String> array) {
            Queue<Node> queue = new LinkedList<>();
            queue.offer(this);
            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                array.add(cur.name);
                for (Node node : children) {
                    queue.add(node);
                }
            }
            return array;
        }
    }
}
