package AlgoExpert.VeryHard;

import java.util.*;

public class DetectArbitrage {
    // O(n^3) time | O(n^2) space - where n is the number of currencies
    boolean detectArbitrage(ArrayList<ArrayList<Double>> exchangeRates) {
        // To use exchange rates as edge weights, we must be able to add them.
        // Since log (a*b) = log(a) + log(b), we can convert all rates to
        // -log10(rate) to use them as edge weights.
        ArrayList<ArrayList<Double>> logExchangeRates = convertToLogMatrix(exchangeRates);

        // a negative weight cycle indicates an arbitrage
        return foundNegativeWeightCycle(logExchangeRates, 0);
    }

    private ArrayList<ArrayList<Double>> convertToLogMatrix(ArrayList<ArrayList<Double>> matrix) {
        ArrayList<ArrayList<Double>> newMatrix = new ArrayList<>();

        for (int row = 0; row < matrix.size(); row++) {
            ArrayList<Double> ratesAsEdges = matrix.get(row);
            newMatrix.add(new ArrayList<Double>());
            for (Double rate : ratesAsEdges) { // take the negative logarithm of the edge

                // note to use negative sign since bellman ford finds negative weight cycles
                // a * b > 1 ？
                // apply logarithm to both sides -> log(a*b) > log(1) -> log(a*b) > 0 -> log(a) + log(b) > 0
                // -[log(a) + log(b)] < 0 = [log(a) + log(b)] > 0
                newMatrix.get(row).add(-Math.log10(rate));
            }
        }

        return newMatrix;
    }

    // runs the bellman-ford algorithm to detect any negative-weight cycles
    private boolean foundNegativeWeightCycle(ArrayList<ArrayList<Double>> graph, int startVertex) {
        double[] distancesFromStart = new double[graph.size()];
        Arrays.fill(distancesFromStart, Double.MAX_VALUE); // fill with infinity
        distancesFromStart[startVertex] = 0;

        // run n-1 times to find shortest path from any vertex
        for (int unused = 0; unused < graph.size(); unused++) {
            // if no updates occurs that means there is no negative cycle
            if (!relaxEdgesAndUpdateDistances(graph, distancesFromStart)) return false;
        }

        // run one more time to find negative weight cycles
        return relaxEdgesAndUpdateDistances(graph, distancesFromStart);
    }

    // returns true if any distance was updated(return true if updated)
    private boolean relaxEdgesAndUpdateDistances(ArrayList<ArrayList<Double>> graph, double[] distances) {
        boolean updated = false;

        for (int sourceIdx = 0; sourceIdx < graph.size(); sourceIdx++) {
            ArrayList<Double> edges = graph.get(sourceIdx);
            for (int destIdx = 0; destIdx < edges.size(); destIdx++) {
                double edgeWeight = edges.get(destIdx);
                double newDistanceToDest = distances[sourceIdx] + edgeWeight;
                if (newDistanceToDest < distances[destIdx]) {
                    updated = true;
                    distances[destIdx] = newDistanceToDest;
                }
            }
        }
        return updated;
    }
}
