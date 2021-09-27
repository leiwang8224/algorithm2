package AlgoExpert.VeryHard;

import java.util.*;
public class AirportConnections {
    // O(a * (a + r) + a + r + alog(a)) time | O(a + r) space where a
    // is the number of airports and r is the number of routes
    int airportConnections (List<String> airports, List<List<String>> routes, String startAirport) {
        // create the graph
        Map<String, AirportNode> airportGraph = createAirportGraph(airports, routes);
        // get all unreachable airports starting from the starting airport
        List<AirportNode> unreachableAirportNodes =
                getUnreachableAirportNodes(airportGraph,airports,startAirport);
        // assign scores to each unreachable airport(find all airports reachable from unreachable airport)
        markUnreachableConnections(airportGraph,unreachableAirportNodes);
        // get the min number of connections
        return getMinNumberofNewConnections(airportGraph, unreachableAirportNodes);
    }

    // O(a * (a + r)) time | O(a) space
    private void markUnreachableConnections(Map<String, AirportNode> airportGraph, List<AirportNode> unreachableAirportNodes) {
        // for each unreachable airport, find all unreachable connections
        for (AirportNode airportNode : unreachableAirportNodes) {
            String airport = airportNode.airport;
            List<String> unreachableConnections = new ArrayList<>();
            Set<String> visitedAirports = new HashSet<>();
            depthFirstAddUnreachableConnections(airportGraph, airport, unreachableConnections, visitedAirports);
            airportNode.unreachableConnections = unreachableConnections;
        }
    }

    private void depthFirstAddUnreachableConnections(Map<String, AirportNode> airportGraph, String airport, List<String> unreachableConnections, Set<String> visitedAirports) {
        if (airportGraph.get(airport).isReachable) return;
        if (visitedAirports.contains(airport)) return;
        visitedAirports.add(airport);
        unreachableConnections.add(airport);
        List<String> connectionsForCurrentAirport = airportGraph.get(airport).connections;
        for (String eachConnectionForCurAirport : connectionsForCurrentAirport) {
            depthFirstAddUnreachableConnections(airportGraph,
                                                eachConnectionForCurAirport,
                                                unreachableConnections,
                                                visitedAirports);
        }
    }

    // O(a + r) time | O(a + r) space
    private Map<String, AirportNode> createAirportGraph(List<String> airports, List<List<String>> routes) {
        Map<String, AirportNode> airportGraph = new HashMap<>();
        // put into graph the airports
        for (String airport : airports) {
            airportGraph.put(airport, new AirportNode(airport));
        }

        // add connections to each airport
        for (List<String> route : routes) {
            String airport = route.get(0); // first element is airport
            String connection = route.get(1); // second element is the connection
            airportGraph.get(airport).connections.add(connection);
        }
        return airportGraph;
    }

    // O(a + r) time | O(a) space
    // need to find all reachable airports before finding the unreachable airports
    // (any airports not part of the reachable airports)
    List<AirportNode> getUnreachableAirportNodes(Map<String, AirportNode> airportGraph,
                                                 List<String> airports,
                                                 String curAirport) {
        Set<String> visitedAirports = new HashSet<>();
        dfsFindAllReachableAirports(airportGraph, curAirport, visitedAirports);  // dfs populates the visitedAirports
                                                         // any unreachable airport will be identified
        List<AirportNode> unreachableAirportNodes = new ArrayList<>();
        // iterate through all the airports and find the ones that are not
        // in the visitedAirports list (unreachable)
        for (String airport : airports) {
            if (visitedAirports.contains(airport)) continue;
            AirportNode airportNode = airportGraph.get(airport);
            airportNode.isReachable = false;
            unreachableAirportNodes.add(airportNode);
        }
        return unreachableAirportNodes;
    }

    private void dfsFindAllReachableAirports(Map<String, AirportNode> airportGraph,
                                             String curAirport,
                                             Set<String> visitedAirports) {
        if (visitedAirports.contains(curAirport)) return;
        visitedAirports.add(curAirport);
        List<String> connections = airportGraph.get(curAirport).connections;
        for (String connection : connections) {
            dfsFindAllReachableAirports(airportGraph, connection, visitedAirports);
        }
    }

    // O(alog(a) + a + r) time | O(1) space
    private int getMinNumberofNewConnections(Map<String,
                                             AirportNode> airportGraph,
                                             List<AirportNode> unreachableAirportNodes) {
        unreachableAirportNodes.sort(
                (a1, a2) -> a2.unreachableConnections.size() - a1.unreachableConnections.size());
        int numberOfNewConnectionsToMake = 0;
        for (AirportNode eachUnreachableAirportNode : unreachableAirportNodes) {
            if (eachUnreachableAirportNode.isReachable) continue;
            // try to make a connection to the unreachable aiport and mark the airport as reachable
            numberOfNewConnectionsToMake++;
            for (String eachUnreachableConnection : eachUnreachableAirportNode.unreachableConnections) {
                airportGraph.get(eachUnreachableConnection).isReachable = true;
            }
        }
        return numberOfNewConnectionsToMake;
    }

    class AirportNode {
        String airport;
        List<String> connections;
        boolean isReachable;
        List<String> unreachableConnections;
        AirportNode(String airport) {
            this.airport = airport;
            connections = new ArrayList<>();
            isReachable = true;
            unreachableConnections = new ArrayList<>(); // find all unreachable connections and mark them
        }
    }
}
