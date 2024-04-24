package Algorithms.AlgorithmicPuzzleSolver;

import Graph.*;
import Puzzles.Puzzle;
import Puzzles.Puzzle.PuzzleMovePair;
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
        long startTime = System.nanoTime();
        Queue<Integer> queue = new LinkedList<>(); // vertex id
        Set<Puzzle> visited = new HashSet<>();
    
        int startId = graph.createAndAddVertex();
        states.put(startId, puzzle);
    
        graph.findVertexById(startId).setPredecessor(null);
        queue.offer(startId);
        visited.add(puzzle);
    
        while (!queue.isEmpty()) {
            int currentId = queue.poll();
            Puzzle currentPuzzle = states.get(currentId);
    
            if (currentPuzzle.isBoardSolved()) {
                SolutionOutput solution = new SolutionOutput(graph, startTime, currentId, states);
                clear();
                return solution;
            }
    
            List<PuzzleMovePair> nextStates = currentPuzzle.generatePossibleMoves();
            for (PuzzleMovePair nextPuzzleMove : nextStates) {
                Puzzle nextPuzzle = nextPuzzleMove.puzzle;
                if (!visited.contains(nextPuzzle)) {
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
        long startTime = System.nanoTime();
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(
                Comparator.comparingDouble(id -> graph.findVertexById(id).getTotalCost()));
        Set<Puzzle> visited = new HashSet<>();
    
        int startId = graph.createAndAddVertex();
        Vertex startVertex = graph.findVertexById(startId);
    
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
    
            List<PuzzleMovePair> nextStates = currentPuzzle.generatePossibleMoves();
            for (PuzzleMovePair nextPuzzleMove : nextStates) {
                Puzzle nextPuzzle = nextPuzzleMove.puzzle;
                if (!visited.contains(nextPuzzle)) {
                    int nextId = graph.createAndAddVertex();
                    Vertex nextVertex = graph.findVertexById(nextId);
                    graph.connectVertices(currentVertex.ID, nextVertex.ID);
                    nextVertex.setPredecessor(currentVertex);
                    int gScore = currentVertex.getDistance() + 1; // Assume cost of 1 per move
                    nextVertex.setDistance(gScore);
                    nextVertex.setHeuristicValue(heuristic.calculate(nextPuzzle));
                    states.put(nextId, nextPuzzle);
                    visited.add(nextPuzzle);
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
        SolutionOutput astarSolutionZeroFunction = algorithmSolution.AStarSearch(puzzle, PuzzleHeuristic.Zero_Heuristic);
        solutionsList.add(astarSolutionZeroFunction);
        if (print) {
            System.out.println("----------------------------- AStar (Zero func) ------------------------------");
            astarSolutionZeroFunction.print(timeUnit);
        }

        // Solve using A* with Manhattan distance
        SolutionOutput astarSolutionManhattanFunction = algorithmSolution.AStarSearch(puzzle, PuzzleHeuristic.Manhattan_Distance);
        solutionsList.add(astarSolutionManhattanFunction);
        if (print) {
            System.out.println("-----------------------------  AStar (Manhattan)  -----------------------------");
            astarSolutionManhattanFunction.print(timeUnit);
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
