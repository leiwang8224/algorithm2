package CTCI.Systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * How would you design the data structures for a very large social network like Facebook or LinkedIn?
 * Describe how you would design an algorithm to show the shortest path between two people (ex:
 * Me -> Bob -> Susan -> Jason -> You).
 */
public class SocialNetwork {
    public static void main(String[] args) {
        int nPeople = 11;
        HashMap<Integer, Person> people = new HashMap<>();

        for (int index = 0; index < nPeople; index++) {
            Person p = new Person(index);
            people.put(index, p);
        }
        int[][] edges = {{1, 4}, {1, 2}, {1, 3}, {3, 2}, {4, 6}, {3, 7}, {6, 9}, {9, 10}, {5, 10}, {2, 5}, {3, 7}};

        // populate source and destination
        for (int[] edge : edges) {
            Person source = people.get(edge[0]);
            source.addFriend(edge[1]);

            Person dest = people.get(edge[1]);
            dest.addFriend(edge[0]);
        }

        int personStartingIndex = 1;
        int personEndingIndex = 10;
        LinkedList<Person> path1 = findPathBFS(people, personStartingIndex, personEndingIndex);
        LinkedList<Person> path2 = findPathBiBFS(people, personStartingIndex, personEndingIndex);
    }

    static class Person {
        private ArrayList<Integer> friends = new ArrayList<>();
        private int personID;
        private String info;

        String getInfo() {
            return info;
        }

        void setInfo(String info) {
            this.info = info;
        }

        ArrayList<Integer> getFriends() {
            return friends;
        }

        int getID() {
            return personID;
        }

        void addFriend(int id) {
            friends.add(id);
        }

        Person(int id) {
            this.personID = id;
        }
    }

    /**
     * Class that keeps track of the starting node and ending node person
     */
    static class PathNode {
        private Person person = null;
        private PathNode prevNode = null;
        PathNode(Person p, PathNode prev) {
            person = p;
            prevNode = prev;
        }

        Person getPerson() { return person; }

        LinkedList<Person> collapse(boolean startsWithRoot) {
            LinkedList<Person> path = new LinkedList<>();
            PathNode node = this;
            while (node != null) {
                if (startsWithRoot) {
                    path.addLast(node.person);
                } else {
                    path.addFirst(node.person);
                }
                node = node.prevNode;
            }
            return path;
        }
    }

    static class Machine {
        HashMap<Integer, Person> persons = new HashMap<>();
        int machineID;

        Person getPersonWithID(int personID) {
            return persons.get(personID);
        }
    }

    /**
     * BFSData contains the list of nodes to visit and a hashmap of nodes visited
     */
    static class BFSData {
        Queue<PathNode> toVisit = new LinkedList<>();
        HashMap<Integer, PathNode> visited = new HashMap<>();

        BFSData(Person root) {
            PathNode sourcePath = new PathNode(root, null);
            toVisit.add(sourcePath);
            visited.put(root.getID(), sourcePath);
        }

        boolean isFinished()  {
            return toVisit.isEmpty();
        }
    }

    /**
     * First let's forget the we are dealing with millions of users. Design this for the simple case.
     *
     * We can construct a graph by treating each person as a node and letting an edge between
     * two nodes indicate that the two users are friends.
     *
     * If I wanted to find the path between two people, I could start with one person and do a simple
     * BFS.
     *
     * Why wouldn't a DFS work well? First DFS would just find a path. It wouldn't necessarily
     * find the shortest path. Second, even if we just needed any path, it would be very inefficient.
     * Two users might be only one degree of separation apart, but I could search millions of nodes
     * in their 'subtrees' before finding this relatively immediate connection.
     *
     *
     * @param people map of person index and person
     * @param personStartingIndex index of the starting person
     * @param personEndingIndex index of the ending person
     * @return
     */
    static LinkedList<Person> findPathBFS(HashMap<Integer, Person> people,
                                          int personStartingIndex,
                                          int personEndingIndex) {
        // BFS uses a queue
        Queue<PathNode> toVisit = new LinkedList<>();
        // hashset to keep track of visited nodes
        HashSet<Integer> visited = new HashSet<>();

        // init toVisit with starting person index and null previous
        toVisit.add(new PathNode(people.get(personStartingIndex), null));
        visited.add(personStartingIndex);

        while (!toVisit.isEmpty()) {
            // get next node from toVisit
            PathNode node = toVisit.poll();
            Person person = node.getPerson();

            // if at the end index
            if (person.getID() == personEndingIndex) {
                return node.collapse(false);
            }

            // search friends
            ArrayList<Integer> friends = person.getFriends();

            // iterate through friends and find not visited friend
            for (int friendID : friends) {
                if (!visited.contains(friendID)) {
                    // visit that unvisited friend by adding to the queue
                    visited.add(friendID);
                    Person friend = people.get(friendID);
                    toVisit.add(new PathNode(friend, node));
                }
            }
        }
        return null;
        
    }

    /**
     * Alternatively, I could do what's called a bidrectional BFS. This means doing two BFS,
     * one from the source and one from the destination. When the searches collide, we know
     * we have found a path.
     *
     * In the implementation we will use two classes to help us. BFSData holds the data we need
     * for a BFS, such as isVisited hash table and the toVisit queue. PathNode will represent the
     * path as we are searching it, storing each Person and the previousNode we visited in this path.
     *
     * Many people are surprised that this is faster. Some quick math can explain why.
     *
     * Suppose every person  has K friends, and node S and node D have a friend C in common.
     * - Traditional BFS from S to D: we go through roughly k + k * k nodes: each of S's k friends,
     * and then each of their k friends
     * - Bidirectional BFS: we go through 2k nodes, each of S's k friends and each of D's k friends.
     *
     * Of course, 2k is much less than k + k * k
     *
     * Generalizing this to a path of length q, we have this:
     * - BFS: O(k^q)
     * - Bidirectional BFS: O(k^q/2 + k^q/2), which is just O(k^q/2)
     *
     * If you imagine a path like A->B->C->D->E where each person has 100 friends, this is a big difference.
     * BFS will require looking at 100 million (100^4) nodes. A bidirectional BFS will require looking at
     * only 20,000 nodes (2 x 100^2)
     *
     * A bidirectional BFS will generally be faster than the traditional BFS. However, it requires actually
     * having access to both the source node and the destination nodes, which is not always the case.
     *
     * /

     / *
     * mergePath merges two BFS traversal data structure together
     * @param bfs1 path from the first
     * @param bfs2 path from the second
     * @param connection Person ID for the connecting bfs1 and bfs2
     * @return
     */
    static LinkedList<Person> mergePaths(BFSData bfs1,
                                         BFSData bfs2,
                                         int connection) {
        PathNode sourceEnd = bfs1.visited.get(connection); // end1 -> source
        PathNode destEnd = bfs2.visited.get(connection); // end2 -> dest
        LinkedList<Person> sourceToConnection = sourceEnd.collapse(false); // forward: source -> connection
        LinkedList<Person> connectionToDest = destEnd.collapse(true);  // reverse: connection -> dest
        connectionToDest.removeFirst();  // remove connection
        sourceToConnection.addAll(connectionToDest);  // add second path
        return sourceToConnection;
    }

    // search one level and return collision, if any
    static Person searchLevel(HashMap<Integer, Person> people,
                              BFSData primary,
                              BFSData secondary) {
        // we only want to search one level at a time. Count how many nodes are currently in the primary's
        // level and only do that many nodes. We will continue to add nodes to the end.
        int primaryToVisitSize = primary.toVisit.size();

        for (int index = 0; index < primaryToVisitSize; index++) {
            // pull out first node
            PathNode primaryPathNode = primary.toVisit.poll();
            int primaryPersonId = primaryPathNode.getPerson().getID();

            // check if it's already been visited in secondary
            // if it is, return the person
            if (secondary.visited.containsKey(primaryPersonId))
                return primaryPathNode.getPerson();

            // add friends to queue
            Person personFromPrimaryQueue = primaryPathNode.getPerson();
            ArrayList<Integer> friendsOfPersonPrimaryQueue = personFromPrimaryQueue.getFriends();

            // iterate friends of the person from primary queue
            for (int friendIdFromPrimaryQueue : friendsOfPersonPrimaryQueue) {
                if (!primary.visited.containsKey(friendIdFromPrimaryQueue)) {
                    Person friendFromPrimaryQueue = people.get(friendIdFromPrimaryQueue);
                    PathNode next = new PathNode(friendFromPrimaryQueue, primaryPathNode);
                    primary.visited.put(friendIdFromPrimaryQueue, next);
                    primary.toVisit.add(next);
                }
            }
        }
        return null;
    }

    static LinkedList<Person> findPathBiBFS(HashMap<Integer, Person> people, int source, int dest) {
        BFSData sourceData = new BFSData(people.get(source));
        BFSData destData = new BFSData(people.get(dest));

        while (!sourceData.isFinished() && !destData.isFinished()) {
            // search out from source
            Person collision = searchLevel(people, sourceData, destData);
            if (collision != null)
                return mergePaths(sourceData, destData, collision.getID());

            // search out from dest
            collision = searchLevel(people, destData, sourceData);
            if (collision != null)
                return mergePaths(sourceData, destData, collision.getID());
        }
        return null;
    }
}
