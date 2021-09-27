package AlgoExpert.VeryHard;

import java.util.*;

// Min heap holds all of the univisited nodes and their current shortest estimated distance to the end
// node.Initialize all nodes except for the start node as having a shortest estimated distance to the
// end node of infinity and also a shortest distance from the start node to themselves of inifinity.
// The start node will have a distance to itself of 0 and an estimated distance to the end node of
// its Manhattan distance to the end node. Next, write a while loop that will run until the min
// heap is empty or unti the end node is reached. At every iteration, remove the node from the top of
// the heap (the node with the shortest estimated distance to the end node), loop through all of its
// neighboring nodes, and for each neighbor, update its two distances if reaching the neighbor from
// the current node yields a shorter distance than whatever's already stored in the neighbor. Once
// you reach the end node, you will have found the shortest path to it from the start node. Note that
// you will have to keep track of which node each node came from whenever you udpate node distance.
// This is so you can construct the shortest path once you reach the end node.
// H: heaurastic (Manhattan distance for 4 directions), Euclidean distance for diagonals
// G: guess
// F: G+H
public class AStarSearch {

    class Node {
        String locationInMatrix;
        int row;
        int col;
        int value;
        int distanceFromStart;
        int estimatedDistanceToEnd;
        Node cameFrom;

        Node(int row, int col, int value) {
            this.locationInMatrix = String.valueOf(row) + '-' + String.valueOf(col);
            this.row = row;
            this.col = col;
            this.value = value;
            this.distanceFromStart = Integer.MAX_VALUE;
            this.estimatedDistanceToEnd = Integer.MAX_VALUE;
            this.cameFrom = null;
        }
    }

    int[][] aStarAlgorithm(int startRow, int startCol, int endRow, int endCol, int[][] graph) {
        List<List<Node>> nodes = initializeNodes(graph);
        Node startNode = nodes.get(startRow).get(startCol);
        Node endNode = nodes.get(endRow).get(endCol);

        startNode.distanceFromStart = 0;
        startNode.estimatedDistanceToEnd = calculateManhattanDistance(startNode, endNode);

        List<Node> nodesToVisitList = new ArrayList<>();
        nodesToVisitList.add(startNode);
        MinHeapEstimatedDistanceToEnd nodesToVisitInMinHeap = new MinHeapEstimatedDistanceToEnd(nodesToVisitList);

        while (!nodesToVisitInMinHeap.isEmpty()) {
            Node curMinDistanceNode = nodesToVisitInMinHeap.remove();

            if (curMinDistanceNode == endNode) break;

            List<Node> neighbors = getListNeighboringNodes(curMinDistanceNode, nodes);

            for (Node neighborNode : neighbors) {
                if (neighborNode.value == 1) continue; // value of 1 represent obstacle, 0 is path

                // increment distance from start = tentative distance to neighbor
                int tentativeDistanceToNeighbor = curMinDistanceNode.distanceFromStart + 1;

                if (tentativeDistanceToNeighbor >= neighborNode.distanceFromStart) continue;

                neighborNode.cameFrom = curMinDistanceNode;
                neighborNode.distanceFromStart = tentativeDistanceToNeighbor;
                // manhattan distance is the h score, tentative distance is g score
                // f score is the estimated distance to end
                neighborNode.estimatedDistanceToEnd =
                        tentativeDistanceToNeighbor + calculateManhattanDistance(neighborNode, endNode);

                if (!nodesToVisitInMinHeap.containsNode(neighborNode)) {
                    nodesToVisitInMinHeap.insert(neighborNode); // node not in heap so add
                } else {
                    nodesToVisitInMinHeap.update(neighborNode); // node in heap so update
                }
            }
        }

        return reconstructPath(endNode);
    }

    private List<List<Node>> initializeNodes(int[][] graph) {
        List<List<Node>> nodes = new ArrayList<>();
        for (int i = 0; i < graph.length; i++) {
            List<Node> nodeList = new ArrayList<>();
            nodes.add(nodeList);
            for (int j = 0; j < graph[i].length; j++) {
                nodes.get(i).add(new Node(i, j, graph[i][j]));
            }
        }
        return nodes;
    }

    private int[][] reconstructPath(Node endNode) {
        if (endNode.cameFrom == null) return new int[][]{};

        Node curNode = endNode;
        List<List<Integer>> pathWithCoords = new ArrayList<>();

        while (curNode != null) {
            List<Integer> nodeData = new ArrayList<>();

            nodeData.add(curNode.row);
            nodeData.add(curNode.col);
            pathWithCoords.add(nodeData);
            curNode = curNode.cameFrom; // backtrack from current node to the beginning
        }

        // convert path to return type int[][] and reverse
        int[][] resultingPositions = new int[pathWithCoords.size()][2];
        for (int i = 0; i < resultingPositions.length; i++) {
            resultingPositions[i][0] = pathWithCoords.get(resultingPositions.length - 1 - i).get(0);
            resultingPositions[i][1] = pathWithCoords.get(resultingPositions.length - 1 - i).get(1);
        }

        return resultingPositions;
    }

    private List<Node> getListNeighboringNodes(Node node, List<List<Node>> nodes) {
        List<Node> neighbors = new ArrayList<>();

        int numRows = nodes.size();
        int numCols = nodes.get(0).size();

        int row = node.row;
        int col = node.col;

        if (row < numRows - 1) {
            // DOWN
            neighbors.add(nodes.get(row + 1).get(col));
        }

        if (row > 0) {
            // UP
            neighbors.add(nodes.get(row - 1).get(col));
        }

        if (col < numCols - 1) {
            // RIGHT
            neighbors.add(nodes.get(row).get(col + 1));
        }

        if (col > 0) {
            // LEFT
            neighbors.add(nodes.get(row).get(col - 1));
        }

        return neighbors;
    }

    private int calculateManhattanDistance(Node curNode, Node endNode) {
        int curRow = curNode.row;
        int curCol = curNode.col;
        int endRow = endNode.row;
        int endCol = endNode.col;

        return Math.abs(curRow - endRow) + Math.abs(curCol - endCol);
    }

    class MinHeapEstimatedDistanceToEnd {
        List<Node> heap = new ArrayList<>();
        Map<String, Integer> nodePositionsInHeap = new HashMap<>();

        MinHeapEstimatedDistanceToEnd(List<Node> array) {
            for (int i = 0; i < array.size(); i++) {
                Node node = array.get(i);
                nodePositionsInHeap.put(node.locationInMatrix, i);
            }
            heap = buildHeap(array);
        }

        private List<Node> buildHeap(List<Node> array) {
            int firstParent = (array.size() - 2) / 2;
            for (int curIdx = firstParent + 1; curIdx >= 0; curIdx--) {
                siftDown(curIdx, array.size() - 1, array);
            }
            return array;
        }

        private void siftDown(int curIdx, int endIdx, List<Node> array) {
            int childOneIdx = curIdx * 2 + 1;
            while (childOneIdx <= endIdx) {
                int childTwoIdx = curIdx * 2 + 2 <= endIdx ? curIdx * 2 + 2 : -1;
                int idxToSwap;
                if (childTwoIdx != -1 &&
                        array.get(childTwoIdx).estimatedDistanceToEnd <
                                heap.get(childOneIdx).estimatedDistanceToEnd) {
                    idxToSwap = childTwoIdx;
                } else {
                    idxToSwap = childOneIdx;
                }

                if (array.get(idxToSwap).estimatedDistanceToEnd <
                        array.get(curIdx).estimatedDistanceToEnd) {
                    swap(curIdx, idxToSwap);
                    curIdx = idxToSwap;
                    childOneIdx = curIdx * 2 + 1;
                } else {
                    return;
                }
            }
        }

        void siftUp(int curIdx) {
            int parentIdx = (curIdx - 1) / 2;
            while (curIdx > 0 &&
                    heap.get(curIdx).estimatedDistanceToEnd <
                            heap.get(parentIdx).estimatedDistanceToEnd) {
                swap(curIdx, parentIdx);
                curIdx = parentIdx;
                parentIdx = (curIdx - 1) / 2;
            }
        }

        Node remove() {
            if (isEmpty()) return null;

            swap(0, heap.size() - 1);
            Node node = heap.get(heap.size() - 1);
            heap.remove(heap.size() - 1);
            nodePositionsInHeap.remove(node.locationInMatrix);
            siftDown(0, heap.size() - 1, heap);
            return node;
        }

        void insert(Node node) {
            heap.add(node);
            nodePositionsInHeap.put(node.locationInMatrix, heap.size() - 1);
            siftUp(heap.size() - 1);
        }

        void swap(int i, int j) {
            nodePositionsInHeap.put(heap.get(i).locationInMatrix, j);
            nodePositionsInHeap.put(heap.get(j).locationInMatrix, i);
            Node temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }

        boolean containsNode(Node node) {
            return nodePositionsInHeap.containsKey(node.locationInMatrix);
        }

        void update(Node node) {
            siftUp(nodePositionsInHeap.get(node.locationInMatrix));
        }

        boolean isEmpty() {
            return heap.size() == 0;
        }
    }



}
