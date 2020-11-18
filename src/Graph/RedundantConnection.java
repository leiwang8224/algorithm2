package Graph;

import java.util.*;

//LC-684
// Every integer represented in the 2D-array
// will be between 1 and N, where N is the size of the input array.
public class RedundantConnection {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(findRedundantDFS(new int[][] {{1,2}, {1,3}, {2,3}})));
        System.out.println(Arrays.toString(findRedundantBFS(new int[][] {{1,2}, {2,3}, {3,4}, {1,4}, {1,5}})));
    }

    // 1. form map of index and edges
    // 2. traverse each edge using for loop and recursion, use set for visited nodes
    public static int[] findRedundantDFS(int[][] edges) {
        // map of index and edges, Set is used
        Map<Integer, Set<Integer>> map = new HashMap<>();
        // Every integer represented in the 2D-array will
        // be between 1 and N, where N is the size of the input array.
        for (int indexEdges = 1; indexEdges <= edges.length; indexEdges++) {
            map.put(indexEdges, new HashSet<>());
        }

        for (int[] edge : edges) {
            if (dfsCheckIfConnected(new HashSet<>(), map, edge[0], edge[1])) return edge;
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }

        return new int[]{-1,-1};
    }

    private static boolean dfsCheckIfConnected(Set<Integer> visited, Map<Integer, Set<Integer>> map,
                                               int src, int target) {
        // if there is connection between src and target
        if (src == target) return true;
        // need to keep track of visited nodes, add src
        visited.add(src);
        // for each target traverse the graph via dfs
        for (int targetToNextSrc : map.get(src)) {
            if (!visited.contains(targetToNextSrc)) {
                if (dfsCheckIfConnected(visited, map, targetToNextSrc, target)) return true;
            }
        }
        return false;
    }

    private static int[] findRedundantBFS(int[][] edges2DArray) {
        // map to store edges <start, ends[]>
        Map<Integer, Set<Integer>> map = new HashMap<>();

        // Every integer represented in the 2D-array will
        // be between 1 and N, where N is the size of the input array.
        for (int index = 1; index <= edges2DArray.length; index++) {
            map.put(index, new HashSet<>());
        }

        for (int[] edge : edges2DArray) {
            if (bfsCheckIfConnected(map, edge[0], edge[1])) return edge;
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }

        return new int[]{-1,-1};
    }

    private static boolean bfsCheckIfConnected(Map<Integer, Set<Integer>> map,
                                               int src, int target) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(src);
        Set<Integer> visited = new HashSet<>();
        visited.add(src);

        while (!q.isEmpty()) {
            int srcFromQueue = q.poll();
            if (srcFromQueue == target) return true;
            for (int next : map.get(srcFromQueue)) {
                if (visited.add(next)) {
                    q.offer(next);
                }
            }
        }

        return false;
    }

    /**
     * time: O(N^2)
     * space: O(N)
     * For each edge (u, v), traverse the graph with a depth-first
     * search to see if we can connect u to v. If we can, then
     * it must be the duplicate edge.
     */
    static Set<Integer> visited = new HashSet<>();
    static int MAX_EDGE_VAL = 1000;
    private static int[] findRedundantConnectionDFS(int[][] edges) {
        // form graph data struct, arraylist of arrays where each array is an edge
        ArrayList<Integer>[] graph = new ArrayList[MAX_EDGE_VAL + 1];
        Arrays.fill(graph, new ArrayList<>());
//        for (int i = 0; i <= MAX_EDGE_VAL; i++) {
//            graph[i] = new ArrayList();
//        }

        for (int[] edge: edges) {
            visited.clear();
            // if edge0 and edge1 is not empty
            if (!graph[edge[0]].isEmpty() && !graph[edge[1]].isEmpty() &&
                    dfs(graph, edge[0], edge[1])) {
                return edge;
            }
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
        throw new AssertionError();
    }

    private static boolean dfs(ArrayList<Integer>[] graph, int source, int target) {
        if (!visited.contains(source)) {
            visited.add(source);
            if (source == target) return true;
            for (int targetToNextSrc: graph[source]) {
                if (dfs(graph, targetToNextSrc, target)) return true;
            }
        }
        return false;
    }

    /**
     * O(N) where N is the number of vertices (edges) in the graph
     * O(N) space
     * @param edges
     * @return
     */
    public static int[] findRedundantConnectionUnionFind(int[][] edges) {
        DSU dsu = new DSU(MAX_EDGE_VAL + 1);
        for (int[] edge: edges) {
            if (!dsu.union(edge[0], edge[1])) return edge;
        }
        throw new AssertionError();
    }

    static class DSU {
        int[] parent;
        int[] rank;

        public DSU(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) parent[i] = i;
            rank = new int[size];
        }

        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        public boolean union(int x, int y) {
            int xr = find(x), yr = find(y);
            if (xr == yr) {
                return false;
            } else if (rank[xr] < rank[yr]) {
                parent[xr] = yr;
            } else if (rank[xr] > rank[yr]) {
                parent[yr] = xr;
            } else {
                parent[yr] = xr;
                rank[xr]++;
            }
            return true;
        }
    }
}
