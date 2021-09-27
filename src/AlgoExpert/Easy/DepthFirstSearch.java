package AlgoExpert.Easy;

import java.util.ArrayList;

public class DepthFirstSearch {
    static class Node {
        String name;
        java.util.List<Node> children = new ArrayList<>();

        public Node (String name) {
            this.name = name;
        }

        public java.util.List<String> dfs (java.util.List<String> array) {
            array.add(this.name);
            for (int i = 0; i < children.size(); i++) {
                children.get(i).dfs(array);
            }

            return array;
        }
    }
}
