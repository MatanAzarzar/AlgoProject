package Puzzles;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.*;

import Graph.Graph;

public class SolutionOutput {
    private final Graph graph;
    private final long elapsedTime;
    private final List<Integer> path;
    private final List<Puzzle> movementsList;

    public SolutionOutput(Graph graph, long startTime, int solutionVertexId, Map<Integer, Puzzle> states) {
        this.graph = graph;

        // Elapsed Time
        long endTime = System.nanoTime();
        this.elapsedTime = endTime - startTime;

        // Path to Solution
        this.path = new ArrayList<>();
        int currentId = solutionVertexId;
        while (currentId != -1) {
            path.add(currentId);
            currentId = this.graph.findVertexById(currentId).getPredecessor() != null ? this.graph.findVertexById(currentId).getPredecessor().ID : -1;
        }
        // Movements to Solution
        this.movementsList = new ArrayList<>();
        int totalSteps = path.size();
        for (int i = totalSteps - 1; i >= 0; i--) {
            int vertexId = path.get(i);
            this.movementsList.add(states.get(vertexId));
        }
    }

    public void print(TimeUnit timeUnit) {
        // Print solution information
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices in the Graph: " + getFormattedNumber(graph.getVertexCount()) + "\n");
        sb.append("Time: " + getElapsedTime(timeUnit, elapsedTime) + " " + timeUnit.name().toLowerCase() + "\n");
        // Print the path in reverse order
        sb.append("Solution path: ");
        for (int i = path.size() - 1; i >= 0; i--) {
            int vertexId = path.get(i);
            sb.append(vertexId);
            if (i > 0) {
                sb.append("->");
            }
        }
        sb.append("\n");
        sb.append("Number of movements to solution: " + (path.size() - 1) + "\n");
        System.out.println(sb.toString());
    }

    public static String getFormattedNumber(int number) {
        // Create a DecimalFormat
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(number);
        return formattedNumber;
    }

    public static String getElapsedTime(TimeUnit timeUnit, long elapsedTime) {
        switch (timeUnit) {
            case SECONDS:
                double secondsWithDot = elapsedTime / 1e9;
                return String.format("%.4f", secondsWithDot);
            case MILLISECONDS:
                return String.valueOf(elapsedTime / 1_000_000);
            case MICROSECONDS:
                return String.valueOf(elapsedTime / 1_000);
            case NANOSECONDS:
                return String.valueOf(elapsedTime);
            default:
                throw new IllegalArgumentException("Invalid time unit: " + timeUnit);
        }
    }

    public Graph toGraph() {
        return graph;
    }

    public int getNumOfVertices() {
        return graph.getVertexCount();
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public int getNumOfMovements() {
        return path.size() - 1;
    }

    public void printMovesToSolution() {
        System.out.println("Moves to Solution:\n");

        for (int i = 0; i < path.size(); i++) {
            if (i == 0) {
                System.out.println("Initial Board:\n");
            } else {
                System.out.println("\nStep " + i + ":");
            }
            System.out.println(movementsList.get(i));
        }
    }

}
