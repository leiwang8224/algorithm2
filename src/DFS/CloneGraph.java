package DFS;

import java.util.*;

/**
 * Created by leiwang on 3/31/18.
 */
public class CloneGraph {
    public static void main(String[] args) {
        UndirectedGraphNode head = new UndirectedGraphNode(0);

//        printGraph(head.label,head);
        UndirectedGraphNode clonedHead = cloneGraph(head);
//        printGraph(clonedHead.label,clonedHead);
        UndirectedGraphNode clonedHead2 = cloneGraph2(head);
//        printGraph(clonedHead2.label,clonedHead2);
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

//        void printGraph(int v) {
//            boolean visited[] = new boolean[neighbors.size()];
//            dfsUtil(v,visited);
//        }
//
//        private void dfsUtil(int v, boolean[] visited) {
//            visited[v] = true;
//            System.out.println(v);
//
//            ListIterator<Integer> i = neighbors.get(v).listI();
//        }
    }

    /**
     * No Global var used in this approach
     * @param node
     * @return
     */
    public static UndirectedGraphNode cloneGraph2(UndirectedGraphNode node) {
        HashMap<Integer,UndirectedGraphNode> map = new HashMap<Integer,UndirectedGraphNode>();
        return dfs(node,map);
    }
    private static UndirectedGraphNode dfs(UndirectedGraphNode node, HashMap<Integer,UndirectedGraphNode> map) {
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

//    private static void printGraph(int startingNode, UndirectedGraphNode node) {
//        boolean visited[] = new boolean[startingNode];
//        dfsUtil(startingNode, visited, node);
//    }
//
//    private static void dfsUtil(int startingNode, boolean[] visited, UndirectedGraphNode node) {
//        visited[startingNode] = true;
//        System.out.println(startingNode);
//
//        ListIterator<UndirectedGraphNode> i = node.neighbors.listIterator();
//        while(i.hasNext()) {
//            int n = i.next().label;
//            if (!visited[n])
//                dfsUtil(n, visited, i.next());
//        }
//    }
}
