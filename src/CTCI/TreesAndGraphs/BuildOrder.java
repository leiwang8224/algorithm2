package CTCI.TreesAndGraphs;

// Given a list of projects and a list of dependencies (list of pairs of projects, where
// the second project is dependent on the first project). All of a project's dependencies
// must be built before the project is. Find a build order that will allow the projects to be
// built. If there is no valid build order, return an error.
// ex: projects a, b, c, d, e, f
// dependencies: (a, d), (f, b), (b, d), (f, a), (d, c)
// output: f, a, b, d, c

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class BuildOrder {
    enum State {COMPLETE, PARTIAL, BLANK};

    public static void main(String[] args) {
        String[] projects = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        String[][] dependencies = {
                {"a", "b"},
                {"b", "c"},
                {"a", "c"},
                {"d", "e"},
                {"b", "d"},
                {"e", "f"},
                {"a", "f"},
                {"h", "i"},
                {"h", "j"},
                {"i", "j"},
                {"g", "j"}};

        String[] buildOrder = buildOrderWrapper(projects, dependencies);
        if (buildOrder == null) {
            System.out.println("There is circular dependencies in the projects");
        } else {
            for (String order : buildOrder) {
                System.out.println(order);
            }
        }

    }

    /**
     * Build the graph, adding the edge(a, b) if b is dependent on a.
     * Assumes a pair is listed in build order (which is the reverse
     * of dependency order). The pair (a, b) in dependencies indicates
     * that b depends on a and a must be built before a.
     * @param projects list of projects to be built
     * @param dependencies list of projects along with its dependencies
     * @return
     */
    private static Graph buildGraph(String[][] dependencies) {
        Graph graph = new Graph();
        for (String[] dependency : dependencies) {
            // second depends on the first
            String first = dependency[0];
            String second = dependency[1];
            graph.addEdge(first, second);
        }

        return graph;
    }

    /**
     * Performs DFS traversal on the graph, given that DFS goes to the deepest
     * parts of the graph to get the projects that needs to be built first
     * before other projects.
     * @param project project to be built
     * @param stack empty stack for storing the DFS nodes, (for BFS use queue)
     * @return
     */
    static boolean doDFS(Project project, Stack<Project> stack) {
        // return false if we have visited this node (project)
        // in other words, cycle is detected in the graph
        if (project.getState() == State.PARTIAL) return false; // cycle

        // if not visited, set state to visited
        if (project.getState() == State.BLANK) {
            // set state to visited
            project.setState(State.PARTIAL);
            // get list of dependencies
            ArrayList<Project> dependenciesOfProject = project.getChildren();
            // for each dependency traverse deeper
            for (Project dependency : dependenciesOfProject) {
                if (!doDFS(dependency, stack)) return false;
            }

            // set status to complete
            project.setState(State.COMPLETE);
            stack.push(project);
        }
        return true;
    }

    /**
     * Order the projects based on dependencies, returns a stack of projects
     * where the ones that needs to be built first is on top of stack to the last
     * ones to built on the bottom of the stack
     * @param projects list of projects
     * @return stacks of projects to build based on the order of the build
     */
    static Stack<Project> orderProjects(ArrayList<Project> projects) {
        Stack<Project> stack = new Stack<>();
        for (Project project : projects) {
            if (project.getState() == State.BLANK) {
                // perform DFS traversal
                if (!doDFS(project, stack)) return null;
            }
        }
        return stack;
    }

    /**
     * Convert build order of projects from stack to array of string
     * @param projects stack of projects
     * @return
     */
    static String[] convertToStringList(Stack<Project> projects) {
        String[] buildOrder = new String[projects.size()];
        for (int index = 0; index < buildOrder.length; index++)
            buildOrder[index] = projects.pop().getName();

        return buildOrder;
    }

    /**
     * First method to be called to build the graph from array of projects and dependencies
     * @param projects array of projects
     * @param dependencies 2 dim array of projects and dependencies
     * @return
     */
    static Stack<Project> findBuildOrder(String[][] dependencies) {
        // build the graph from the dependencies
        Graph graph = buildGraph(dependencies);

        return orderProjects(graph.getNodes());
    }

    static String[] buildOrderWrapper(String[] projects, String[][] dependencies) {
        Stack<Project> buildOrder = findBuildOrder(dependencies);
        if (buildOrder == null) return null;
        String[] buildOrderString = convertToStringList(buildOrder);
        return buildOrderString;
    }

    private static class Graph {
        // all of the nodes in the graph
        private ArrayList<Project> nodes = new ArrayList<>();

        // key-value pair for storing dependencies
        private HashMap<String, Project> map = new HashMap<>();

        Project getOrCreateNode(String name) {
            if (!map.containsKey(name)) {
                Project node = new Project(name);
                nodes.add(node);
                map.put(name, node);
            }

            return map.get(name);
        }

        void addEdge(String startName, String endName) {
            Project start = getOrCreateNode(startName);
            Project end = getOrCreateNode(endName);
            start.addNeighbor(end);
        }

        ArrayList<Project> getNodes() {
            return nodes;
        }
    }

    private static class Project {
        private ArrayList<Project> children = new ArrayList<>();
        private HashMap<String, Project> map = new HashMap<>();
        private String name;
        private State state = State.BLANK;

        Project(String n) {
            name = n;
        }

        String getName() {
            return name;
        }

        void addNeighbor(Project node) {
            if (!map.containsKey(node.getName())) {
                children.add(node);
                map.put(node.getName(), node);
            }
        }

        State getState() {
            return state;
        }

        void setState(State st) {
            state = st;
        }

        ArrayList<Project> getChildren() {
            return children;
        }

    }


}
