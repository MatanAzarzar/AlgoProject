package Algorithms.AlgorithmicPuzzleSolver;

import Graph.*;
import Puzzles.Puzzle;
import Puzzles.SolutionOutput;

import java.util.*;
import java.util.concurrent.TimeUnit;

import Algorithms.HeuristicAlgorithms.*;

public class AlgorithmicPuzzleSolver {
    private Graph graph;
    private Map<Integer, Puzzle> states; // Vertex ID to Puzzle

    public AlgorithmicPuzzleSolver() {
        this.graph = new Graph();
        this.states = new HashMap<>();
    }

    public SolutionOutput breadthFirstSearch(Puzzle puzzle) {
        // Set start time, queue of vertex.ID and 'vistied' set
        long startTime = System.nanoTime();
        Queue<Integer> queue = new LinkedList<>(); // vertex id
        Set<Puzzle> visited = new HashSet<>();

        // Create first vertex
        int startId = graph.createAndAddVertex();
        states.put(startId, puzzle);

        // Set predeessor and add to the queue & visited
        graph.findVertexById(startId).setPredecessor(null);
        queue.offer(startId);
        visited.add(puzzle);

        while (!queue.isEmpty()) {
            int currentId = queue.poll();
            Puzzle currentPuzzle = states.get(currentId);

            // Algorithm requires a stopping condition (solutions space is exponential)
            if (currentPuzzle.isBoardSolved()) {
                SolutionOutput solution = new SolutionOutput(graph, startTime, currentId, states);
                clear();
                return solution;
            }

            // Generate possible board states
            List<Puzzle> nextStates = currentPuzzle.generatePossibleMoves();
            for (Puzzle nextPuzzle : nextStates) {
                if (!visited.contains(nextPuzzle)) { // Board state doesnt discovered yet
                    // Create the next state vertex
                    int nextId = graph.createAndAddVertex();
                    graph.connectVertices(currentId, nextId);
                    graph.findVertexById(nextId).setPredecessor(graph.findVertexById(currentId));
                    states.put(nextId, nextPuzzle);
                    visited.add(nextPuzzle);
                    queue.offer(nextId);
                }
            }
        }
        return null;
    }

    public SolutionOutput AStarSearch(Puzzle puzzle, PuzzleHeuristic heuristic) {
        // Set start time, priority queue of vertiex.ID and 'vistied' set
        long startTime = System.nanoTime();
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(
                Comparator.comparingDouble(id -> graph.findVertexById(id).getTotalCost()));
        Set<Puzzle> visited = new HashSet<>();

        // Create starting vertex
        int startId = graph.createAndAddVertex();
        Vertex startVertex = graph.findVertexById(startId);

        // Set predeessor, 'g' & 'h' scores and add to the queue & visited
        startVertex.setPredecessor(null);
        startVertex.setDistance(0);
        startVertex.setHeuristicValue(heuristic.calculate(puzzle));
        states.put(startId, puzzle);
        priorityQueue.offer(startId);
        visited.add(puzzle);

        while (!priorityQueue.isEmpty()) {
            int currentId = priorityQueue.poll();
            Vertex currentVertex = graph.findVertexById(currentId);
            Puzzle currentPuzzle = states.get(currentId);

            if (currentPuzzle.isBoardSolved()) {
                SolutionOutput solution = new SolutionOutput(graph, startTime, currentId, states);
                clear();
                return solution;
            }

            // Generate possible board states
            List<Puzzle> nextStates = currentPuzzle.generatePossibleMoves();
            for (Puzzle nextPuzzle : nextStates) {
                if (!visited.contains(nextPuzzle)) { // Board state doesnt discovered yet
                    // Create the next state vertex
                    int nextId = graph.createAndAddVertex();
                    Vertex nextVertex = graph.findVertexById(nextId);
                    graph.connectVertices(currentVertex.ID, nextVertex.ID);
                    nextVertex.setPredecessor(currentVertex);
                    int gScore = currentVertex.getDistance() + 1; // All edges have weight 1
                    nextVertex.setDistance(gScore);
                    nextVertex.setHeuristicValue(heuristic.calculate(nextPuzzle));
                    states.put(nextId, nextPuzzle);
                    visited.add(nextPuzzle);
                    // Add to the priority queue, updating the queue
                    priorityQueue.offer(nextId);
                }
            }
        }
        return null;
    }

    private void clear() {
        this.graph = new Graph();
        this.states.clear();
    }

    public static List<SolutionOutput> AlgorithmSolver(Puzzle puzzle, boolean print, TimeUnit timeUnit) {
        // Create Puzzle Graph
        AlgorithmicPuzzleSolver algorithmSolution = new AlgorithmicPuzzleSolver();
        List<SolutionOutput> solutionsList = new ArrayList<>();

        // Solve using BFS
        SolutionOutput bfsSolution = algorithmSolution.breadthFirstSearch(puzzle);
        solutionsList.add(bfsSolution);
        if (print) {
            System.out.println("------------------------------------- BFS -------------------------------------");
            bfsSolution.print(timeUnit);
        }

        // Solve using A* with Zero function
        SolutionOutput astarSolution1 = algorithmSolution.AStarSearch(puzzle, PuzzleHeuristic.Zero_Heuristic);
        solutionsList.add(astarSolution1);
        if (print) {
            System.out.println("----------------------------- AStar (Zero func) ------------------------------");
            astarSolution1.print(timeUnit);
        }

        // Solve using A* with Manhattan distance
        SolutionOutput astarSolution2 = algorithmSolution.AStarSearch(puzzle, PuzzleHeuristic.Manhattan_Distance);
        solutionsList.add(astarSolution2);
        if (print) {
            System.out.println("-----------------------------  AStar (Manhattan)  -----------------------------");
            astarSolution2.print(timeUnit);
        }

        // Solve using A* with Non-Admissible heursitic
        SolutionOutput astarSolution4 = algorithmSolution.AStarSearch(puzzle, PuzzleHeuristic.Permutations_Inversions);
        solutionsList.add(astarSolution4);
        if (print) {
            System.out.println("-------------------------- AStar (Permutation inversions) --------------------------");
            astarSolution4.print(timeUnit);
        }

        return solutionsList;
    }

}
