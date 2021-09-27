package AlgoExpert.Easy;

import java.util.ArrayList;

public class DFSGraph {
    static class Node {
        String name;
        java.util.List<Node> children = new ArrayList<>();

        public Node(String name) {
            this.name = name;
        }

        private java.util.List<String> dfs(java.util.List<String> array) {
            array.add(this.name);
            for (int index = 0; index < children.size(); index++) {
                children.get(index).dfs(array);
            }

            return array;
        }
    }
}
