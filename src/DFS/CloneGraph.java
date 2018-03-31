package DFS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by leiwang on 3/31/18.
 */
public class CloneGraph {
    public static void main(String[] args) {
        UndirectedGraphNode head = new UndirectedGraphNode(0);

        cloneGraph(head);

    }

    private static HashMap<Integer, UndirectedGraphNode> map = new HashMap<>();
    public static UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        return clone(node);
    }

    private static UndirectedGraphNode clone(UndirectedGraphNode node) {
        if (node == null) return null;

        if (map.containsKey(node.label)) {
            return map.get(node.label);
        }
        UndirectedGraphNode clone = new UndirectedGraphNode(node.label);
        map.put(clone.label, clone);
        for (UndirectedGraphNode neighbor : node.neighbors) {
            clone.neighbors.add(clone(neighbor));
        }
        return clone;
    }

    private static class UndirectedGraphNode {
        int label;
        List<UndirectedGraphNode> neighbors;

        UndirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<>();
        }
    }

    /**
     * No Global var used in this approach
     * @param node
     * @return
     */
    public UndirectedGraphNode cloneGraph2(UndirectedGraphNode node) {
        HashMap<Integer,UndirectedGraphNode> map = new HashMap<Integer,UndirectedGraphNode>();
        return dfs(node,map);
    }
    private UndirectedGraphNode dfs(UndirectedGraphNode node, HashMap<Integer,UndirectedGraphNode> map) {
        if (node == null) return null;
        if (map.containsKey(node.label)) {
            return map.get(node.label);
        } else {
            UndirectedGraphNode clone = new UndirectedGraphNode(node.label);
            map.put(node.label,clone);
            for (int i = 0; i < node.neighbors.size(); i++) {
                clone.neighbors.add(dfs(node.neighbors.get(i), map));
            }
            return clone;
        }
    }
}
