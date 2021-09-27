package AlgoExpert.Hard;

import sun.security.x509.DistributionPointName;

import java.util.*;
public class DijkstrasAlgorithm {
    // O(v^2 + e) time | O(v) space where v is the number of
    // vertices and e is the number of edges in the input graph
    public static int[] dijkstrasNotOptimized(int start, int[][][] edges) {
        int VERTEX_IDX = 0;
        int DISTANCE_IDX = 1;
        int numberVertices = edges.length;

        // init minDistances array to inf with the start min distance = 0
        int[] minDistancesForEachVertex = new int[edges.length];
        Arrays.fill(minDistancesForEachVertex, Integer.MAX_VALUE);

        // starting minDistance = 0
        minDistancesForEachVertex[start] = 0; // distance from start to start is 0

        Set<Integer> vertexVisited = new HashSet<>();

        // iterate through all vertices and find distances shorter than currently in minDistances array
        while (vertexVisited.size() != numberVertices) {
            // RETRIEVAL SECTION
            // find the vertex that has not been visited AND has the shortest distance
            int[] vertexWithShortestDistData = getVertexWithShortestDistance(minDistancesForEachVertex, vertexVisited);
            int vertexWithShortestDistance = vertexWithShortestDistData[VERTEX_IDX];
            int shortestDistanceFromArray = vertexWithShortestDistData[DISTANCE_IDX];

            // we found a node that goes to itself, so we have finished the algorithm
            if (shortestDistanceFromArray == Integer.MAX_VALUE) break;

            vertexVisited.add(vertexWithShortestDistance);

            // UPDATE SECTION
            // traverse all edges connected to the vertex not visited with shortest distance and check
            // if there are shorter distances than the one in the array by adding the distance to the
            // destination and compare that to what is currently in the minDistance array
            // (update the array if there is shorter distance)
            for (int[] eachEdgeForClosestVertex : edges[vertexWithShortestDistance]) {
                int dest = eachEdgeForClosestVertex[VERTEX_IDX];
                int distanceToDest = eachEdgeForClosestVertex[DISTANCE_IDX];

                if (vertexVisited.contains(dest)) continue;

                // add the edge to the vertex path and check if this
                // new path is smaller that the one in the array
                // update the array if it is smaller
                int newPathDistForCurVertex = shortestDistanceFromArray + distanceToDest;
                // found new distance < cur destination distance
                if (newPathDistForCurVertex < minDistancesForEachVertex[dest]) {
                    minDistancesForEachVertex[dest] = newPathDistForCurVertex;
                }
            }
        }

        // for any min distance that is not found (inf value) replace with -1
        // (-1 represent cannot reach that vertex)
        int[] finalDist = new int[minDistancesForEachVertex.length];
        for (int vertex = 0; vertex < minDistancesForEachVertex.length; vertex++) {
            int dist = minDistancesForEachVertex[vertex];
            // can't reach that node so put -1
            if (dist == Integer.MAX_VALUE) {
                finalDist[vertex] = -1;
            } else {
                finalDist[vertex] = dist;
            }
        }

        return finalDist;
    }

    /**
     * Starting from vertex 0, traverse the graph to initialize the minDistances array
     * (finding the vertex that is closest and its distance)
     * returns the vertex and the distance
     */
    public static int[] getVertexWithShortestDistance(int[] minDistForEachVertex, Set<Integer> vertexVisited) {
        // keep track of the shortest distance and the vertex with shortest distance
        int curMinDistSoFar = Integer.MAX_VALUE;
        int curVertex = -1; //null val

        for (int vertexIdx = 0; vertexIdx < minDistForEachVertex.length; vertexIdx++) {
            // distance to vertexIdx
            int distVertexFromMinDistancesArray = minDistForEachVertex[vertexIdx];

            // if vertexIdx visited then skip loop
            if (vertexVisited.contains(vertexIdx)) continue;

            if (distVertexFromMinDistancesArray <= curMinDistSoFar) { // note the less than or equal to, we want
                                            // to preserve the inf in case distVertexIdx is inf
                                            // (takes care of the node that is not traversable from other nodes)
                curVertex = vertexIdx;
                curMinDistSoFar = distVertexFromMinDistancesArray;
            }
        }
        return new int[] {curVertex, curMinDistSoFar};
    }

    // O((v + e) * log(v)) time | O(v) space
    public static int[] dijkstrasWithHeap(int start, int[][][] edges) {
        int numberOfVertices = edges.length;

        int[] minDist = new int[numberOfVertices];
        // fill array with inf with first element = 0
        Arrays.fill(minDist, Integer.MAX_VALUE);
        // distance from vertex 0 to itself is 0
        minDist[start] = 0;

        // compose list of min distance pairs (vertex, minDistance initialized to inf)
        List<Item> minDistancesPairs = new ArrayList<>();
        for (int vertex = 0; vertex < numberOfVertices; vertex++) {
            Item item = new Item(vertex, Integer.MAX_VALUE);
            minDistancesPairs.add(item);
        }

        // convert the list into heap
        MinHeapWithMapVertexToDistances minDistancesHeap =
                new MinHeapWithMapVertexToDistances(minDistancesPairs);
        // update the start vertex to have min distance = 0
        minDistancesHeap.update(start, 0);

        // don't need the visited set anymore since we are removing
        // items from the heap
        // iterate through all the vertices and get min distance
        while (!minDistancesHeap.isEmpty()) {
            // get min distance item
            Item heapItemWithShortestDist = minDistancesHeap.remove(); // log(v)
            int vertexWithShortestDist = heapItemWithShortestDist.vertex;
            int shortestDistForVertex = heapItemWithShortestDist.distance;

            if (shortestDistForVertex == Integer.MAX_VALUE) break;

            // iterate through all edges going from the vertex
            // and add to the min distance, if shorter update
            // the heap and minDist array
            for (int[] edge: edges[vertexWithShortestDist]) {
                int dest = edge[0];
                int distanceToDest = edge[1];

                // add the distance to the current vertex
                int newPathDistance = shortestDistForVertex + distanceToDest;
                int curDestDistanceFromMinDistArray = minDist[dest];
                if (newPathDistance < curDestDistanceFromMinDistArray) {
                    // if new min distance is smaller that the distance from array,
                    // update minDist array and the heap
                    minDist[dest] = newPathDistance;
                    minDistancesHeap.update(dest, newPathDistance);
                }
            }
        }

        // update all values in minDist array values with inf to -1 (cannot reach that vertex)
        int[] finalDist = new int[minDist.length];
        for (int i = 0; i < minDist.length; i++) {
            int distance = minDist[i];
            if (distance == Integer.MAX_VALUE) {
                finalDist[i] = -1;
            } else {
                finalDist[i] = distance;
            }
        }
        return finalDist;
    }

    static class Item{
        int vertex;
        int distance;
        public Item(int vertex, int dist) {
            this.vertex = vertex;
            this.distance = dist;
        }
    }

    // can be substituted by priority queue (minHeap)
    static class MinHeapWithMapVertexToDistances {
        // holds the position in the heap that each vertex is at
        // map of <vertex, distanceToVertex>
        Map<Integer, Integer> vertexMap = new HashMap<>();
        java.util.List<Item> heap = new ArrayList<>();

        public MinHeapWithMapVertexToDistances(java.util.List<Item> array) {
            for (int i = 0; i < array.size(); i++) {
                Item item = array.get(i);
                vertexMap.put(item.vertex, item.vertex);
            }
            heap = buildHeap(array);
        }

        boolean isEmpty() {
            return heap.size() == 0;
        }

        // o(n) time | O(1) space
        // - get first parent index from the bottom
        // - iterate from first parent idx backwards to 0 and sift down each element
        private List<Item> buildHeap(List<Item> array) {
            // first formula needs to be memorized
            // first parent node from the bottom up
            int firstParentIdxFromBottom = (array.size() - 2) / 2;

            // iterate from first parent index back to first index,
            // siftdown each element
            for (int curIdx = firstParentIdxFromBottom; curIdx >= 0; curIdx --) {
                siftDown(curIdx, array.size()-1, array);
            }
            return array;
        }

        // O(log(n)) time | O(1) space
        // - need to determine which of the two children are smaller
        // - perform swap with the smaller of two children
        // - update curIdx and child one idx for next iteration
        private void siftDown(int curIdx, int endIdx, List<Item> array) {
            // get child one index of curIdx
            int childOneIdx = curIdx * 2 + 1;
            // childOneIdx gets updated by the next child one (greater than childOne)
            while (childOneIdx <= endIdx) {
                // find child two index if it exists, if child two index is outside of the bounds
                // then it doesn't exist
                int childTwoIdx = curIdx * 2 + 2 <= endIdx ? curIdx * 2 + 2 : -1;
                // find index to swap
                int idxSmallestOfTwoChildren;
                // set idxToSwap to the less of child one and child two index
                // because we want to push the number at curIdx down to the smallest idx
                if (childTwoIdx != -1 && array.get(childTwoIdx).distance <
                        array.get(childOneIdx).distance) {
                    idxSmallestOfTwoChildren = childTwoIdx;
                } else {
                    idxSmallestOfTwoChildren = childOneIdx;
                }

                // if smallest of two children is smaller than curIdx element
                // perform swap (idxSmallestOfTwoChildren > curIdx)
                if (array.get(idxSmallestOfTwoChildren).distance < array.get(curIdx).distance) {
                    swap(array, curIdx, idxSmallestOfTwoChildren);
                    // set curIdx to the smallest idx for next while loop iteration
                    curIdx = idxSmallestOfTwoChildren;
                    // calculate childOne idx for next iteration
                    childOneIdx = curIdx * 2 + 1;
                } else {
                    return;
                }
            }
        }

        // O(log(n)) time | O(1) space
        private void siftUp(int curIdx) {
            // get index of parent, which is less than curIdx
            int parentIdx = (curIdx - 1) / 2;
            // iterate upwards (backwards) and perform swap
            while (curIdx > 0 && heap.get(curIdx).distance < heap.get(parentIdx).distance) {
                // swap and move curIdx up to parent idx
                swap(heap, curIdx, parentIdx);
                curIdx = parentIdx;
                // calculate the next parent idx
                parentIdx = (curIdx - 1) / 2;
            }
        }

        private Item peek() {
            return heap.get(0);
        }

        // removes the head node (first element of list)
        // always swap the first element and last element first
        // then remove the last element
        // perform sift down operation
        private Item remove() {
            // swap beginning and end elements
            swap(heap, 0, heap.size() - 1);
            // set value to remove to the end of the list
            Item valueToRemove = heap.get(heap.size() - 1);
            int vertex = valueToRemove.vertex;
            // remove the last item from list
            heap.remove(heap.size() - 1);
            vertexMap.remove(vertex);
            // call siftdown to down propagate from 0 indice
            siftDown(0, heap.size()-1, heap);
            return valueToRemove;
        }

//        // add to the list and sift up from the last element
//        private void insert(Item value) {
//            heap.add(value);
//            // sift up from the last element since it was
//            // added to the tail of list
//            siftUp(heap.size() - 1, heap);
//        }
        void update (int vertex, int value) {
            heap.set(vertexMap.get(vertex), new Item(vertex, value));
            siftUp(vertexMap.get(vertex));
        }

        private void swap(List<Item> array, int i, int j) {
            vertexMap.put(heap.get(i).vertex, j);
            vertexMap.put(heap.get(j).vertex, i);
            Item temp = heap.get(j);
            heap.set(j, heap.get(i));
            heap.set(i, temp);
        }
    }
}
