package Graph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by leiwang on 3/24/18.
 */
public class Graph {
    private int numVertices;
    private LinkedList<Integer> adj[];

    public Graph(int v) {
        numVertices = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; i ++) {
            adj[i] = new LinkedList<>();
        }
    }

    void addEdge(int v, int w) {
        adj[v].add(w);
    }

    void topologicalSortUtil(int v, boolean visited[], Stack stack) {
        visited[v] = true;
        Integer i;

        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> it = adj[v].iterator();
        while (it.hasNext()) {
            i = it.next();
            if (!visited[i]) {
                topologicalSortUtil(i,visited,stack);
            }
            stack.push(new Integer(v));
        }
    }

    void topologicalSort() {
        Stack stack = new Stack();
        // mark all the vertices as not visited
        boolean visited[] = new boolean[numVertices];
        for (int i = 0;i < numVertices; i ++) {
            visited[i] = false;
        }

        for (int i = 0; i < numVertices; i ++) {
            if (visited[i] == false) {
                topologicalSortUtil(i, visited,stack);
            }
        }

        while (stack.empty()==false) {
            System.out.print(stack.pop() + " ");
        }

    }

    public static void main (String args[]) {
        Graph g = new Graph(6);
        g.addEdge(5,2);
        g.addEdge(5,0);
        g.addEdge(4,0);
        g.addEdge(4,1);
        g.addEdge(2,3);
        g.addEdge(3,1);

        g.topologicalSort();

    }
}
