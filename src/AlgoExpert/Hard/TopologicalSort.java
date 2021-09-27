package AlgoExpert.Hard;

import java.util.*;

public class TopologicalSort {
    // O(j + d) time | O(j + d) space
    /**
     * Start at random node, find all prereqs and visit them (dfs)
     * - create graph data structure for the list of jobs and deps (nodes and job numbers)
     *    - JobNode
     *         - public Integer jobNumber;
     *         - public List<JobNode> prereqJobNodes;
     *         - public boolean visited;
     *         - public boolean visiting;
     *    - JobGraph
     *         - public List<JobNode> nodes;
     *         - public Map<Integer, JobNode> graphMap;
     * - create JobGraph object with the list of nodes and put it into the map
     * - connect the corresponding dependencies to the job nodes in graph
     * - traverse graph in dfs to find cycle (validate graph) and build the jobs in order
     */
    private static List<Integer> topologicalSortDFS(List<Integer> jobs, List<Integer[]> deps) {
        // translate jobs and deps into graph
        JobGraph jobGraph = convertJobsToGraph(jobs, deps);
        // recursively search the graph for cycles and build ordered jobs
        return getOrderedJobsFromGraph(jobGraph);
    }

    /**
     * Create job graph based on the given list of jobs and list of deps.
     */
    private static JobGraph convertJobsToGraph(List<Integer> jobs, List<Integer[]> deps) {
        JobGraph graph = new JobGraph(jobs);
        for (Integer[] dep : deps) {
            // dep[0] is dep and dep[1] is job
            graph.addPrereqToJobNode(dep[1], dep[0]);
        }
        return graph;
    }

    private static List<Integer> getOrderedJobsFromGraph(JobGraph graph) {
        // initialize result
        List<Integer> orderedJobs = new ArrayList<>();
        // get nodes from graph and put all nodes into list
        List<JobNode> nodesList = new ArrayList<>(graph.listOfNodes);
        // perform dfs on each node
        while (nodesList.size() > 0) {
            // get last item from list
            JobNode eachNodeFromGraph = nodesList.get(nodesList.size() - 1);
            // remove last item from list
            nodesList.remove(nodesList.size() - 1);
            // traverse dfs on that node and find if contains cycle
            boolean containsCycle = dfsFindIfCycleAndGetOrderedJobs(eachNodeFromGraph, orderedJobs);
            // if there are cycles return empty list
            if (containsCycle) return new ArrayList<>();
        }
        return orderedJobs;
    }

    /**
     * DFS recursively travel through the graph to find any cycles and return ordered jobs
     */
    private static boolean dfsFindIfCycleAndGetOrderedJobs(JobNode eachNodeFromGraph, List<Integer> orderedJobs) {
        // no cycle, skip
        if (eachNodeFromGraph.visited) return false;
        // there is a cycle
        if (eachNodeFromGraph.visiting) return true;

        eachNodeFromGraph.visiting = true;
        for (JobNode prereqNode : eachNodeFromGraph.prereqJobNodes) {
            boolean containsCycle = dfsFindIfCycleAndGetOrderedJobs(prereqNode, orderedJobs);
            if (containsCycle) return true;
        }
        eachNodeFromGraph.visited = true;
        eachNodeFromGraph.visiting = false;
        // recursion basically traverses to the node without any dependencies and
        // add that node to the start of the orderedJobs. Then recursion returns to the
        // previous node etc.
        orderedJobs.add(eachNodeFromGraph.jobNumber);

        // exhaustively searched graph and there are no cycles
        return false;
    }


    // JobGraph has List of Job nodes
    // JobGraph has Map of Job node number to JobNode
    static class JobGraph {
        // convert the nodes into iterable object (list) for easy retrieval of the list of JobNodes
        public List<JobNode> listOfNodes;
        // map the values to the nodes for easy retrieval of JobNode
        public Map<Integer, JobNode> mapOfJobToNodes;

        public JobGraph(List<Integer> jobs) {
            listOfNodes = new ArrayList<>();
            mapOfJobToNodes = new HashMap<>();
            for (Integer job : jobs) {
                mapOfJobToNodes.put(job, new JobNode(job));
                listOfNodes.add(mapOfJobToNodes.get(job));
            }
        }

        // connect the job nodes together from the job to its corresponding prereqs
        // JobNode object contains the prereq nodes
        public void addPrereqToJobNode(Integer job, Integer prereq) {
            JobNode jobNode = getNodeFromMapAndAddIfNotInMap(job);  // get node from current graph
            JobNode prereqNode = getNodeFromMapAndAddIfNotInMap(prereq); // get node for the prereq from current graph
            jobNode.prereqJobNodes.add(prereqNode);  // add prereqs to the node from the current graph
        }
//
//        // add the job number into existing graph
//        public void addNodeToListAndGraph(Integer job) {
//            // add to map and nodes list
//            mapOfJobToNodes.put(job, new JobNode(job));
//            listOfNodes.add(mapOfJobToNodes.get(job));
//        }

        // returns the node in the graph for the job number,
        // will add node to graph if it doesn't exist
        public JobNode getNodeFromMapAndAddIfNotInMap(Integer job) {
            if (!mapOfJobToNodes.containsKey(job)) {
                mapOfJobToNodes.put(job, new JobNode(job));
                listOfNodes.add(mapOfJobToNodes.get(job));
            }
            return mapOfJobToNodes.get(job);
        }

        /// for second method ///
        public void addDepAndJobToGraph(Integer job, Integer dep) {
            JobNode jobNode = getNodeFromMapAndAddIfNotInMap(job);
            JobNode depNode = getNodeFromMapAndAddIfNotInMap(dep);
            jobNode.deps.add(depNode);

            /// for second method ///
            depNode.numOfPrereqs ++;
        }
    }

    /**
     * JobNode:
     *  - jobNumber: integer
     *  - prereqs: list of JobNodes
     *  - visited: boolean
     *  - visiting: boolean
     */
    static class JobNode {
        public Integer jobNumber;
        public List<JobNode> prereqJobNodes;
        public boolean visited;
        public boolean visiting;

        /// for second method ///
        public Integer numOfPrereqs;
        public List<JobNode> deps;

        public JobNode(Integer job) {
            this.jobNumber = job;
            prereqJobNodes = new ArrayList<>();
            visited = false;
            visiting = false;

            /// for second method ///
            deps = new ArrayList<>();
            numOfPrereqs = 0;
        }
    }

    private static List<Integer> topologicalSort(List<Integer> jobs, List<Integer[]> deps) {
        JobGraph jobGraph = createJobGraph2(jobs, deps);
        return getOrderedJobs2(jobGraph);
    }

    private static List<Integer> getOrderedJobs2(JobGraph graph) {
        List<Integer> orderedJobs = new ArrayList<>();
        List<JobNode> nodesWithNoPrereqs = new ArrayList<>();
        // find all nodes without prereqs
        for (JobNode node : graph.listOfNodes) {
            if (node.numOfPrereqs == 0) {
                nodesWithNoPrereqs.add(node);
            }
        }

        // iterate through the nodes without prereqs
        while (nodesWithNoPrereqs.size() > 0) {
            JobNode node = nodesWithNoPrereqs.get(nodesWithNoPrereqs.size() - 1);
            // remove from nodesWithNoPrereqs list and add to orderedJobs list
            nodesWithNoPrereqs.remove(nodesWithNoPrereqs.size() - 1);
            orderedJobs.add(node.jobNumber);
            // remove all dependencies on the node that is removed from nodesWithNoPrereqs
            removeDeps(node, nodesWithNoPrereqs);
        }
        boolean graphHasCycles = false;
        // if there are still prereqs left then graph has cycle (invalid DAG)
        for (JobNode node : graph.listOfNodes) {
            if (node.numOfPrereqs > 0) {
                graphHasCycles = true;
            }
        }
        return graphHasCycles ? new ArrayList<>() : orderedJobs;
    }

    private static void removeDeps(JobNode node, List<JobNode> nodesWithNoPrereqs) {
        while (node.deps.size() > 0) {
            JobNode dep = node.deps.get(node.deps.size() - 1);
            node.deps.remove(node.deps.size() - 1);
            dep.numOfPrereqs --;
            if (dep.numOfPrereqs == 0) nodesWithNoPrereqs.add(dep);
        }
    }

    private static JobGraph createJobGraph2(List<Integer> jobs, List<Integer[]> deps) {
        JobGraph graph = new JobGraph(jobs);
        for (Integer[] dep : deps) {
            graph.addDepAndJobToGraph(dep[0], dep[1]);
        }
        return graph;
    }
}
