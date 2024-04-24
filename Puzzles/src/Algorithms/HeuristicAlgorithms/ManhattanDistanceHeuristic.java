package Algorithms.HeuristicAlgorithms;

import java.util.HashMap;
import java.util.Map;

import Puzzles.Puzzle;

public class ManhattanDistanceHeuristic implements PuzzleHeuristic {
    private Map<Puzzle, Integer> heuristicCache;
    private static ManhattanDistanceHeuristic instance = null;

    public ManhattanDistanceHeuristic(){
        this.heuristicCache = new HashMap<>();
    }

    public static ManhattanDistanceHeuristic get() {
        if (instance == null)
            instance = new ManhattanDistanceHeuristic();
        return instance;
    }

    @Override
    public double calculate(Puzzle puzzle) {
        // Check if the heuristic value is already in the cache
        if (heuristicCache.containsKey(puzzle)) {
            return heuristicCache.get(puzzle);
        }
        int distance = 0;
        int size = puzzle.size();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = puzzle.getBoard()[i][j];
                if (value != 0) {
                    int[] goalCoordinates = puzzle.getGoalCoordinates(value);
                    distance += Math.abs(i - goalCoordinates[0]) + Math.abs(j - goalCoordinates[1]);
                }
            }
        }

        // Cache the computed heuristic value
        heuristicCache.put(puzzle, distance);
        return distance;
    }

    @Override
    public void printCache() {
        // Print cached sequences
        heuristicCache.forEach((k, v) -> {
            System.out.println("Puzzle:\n" + k);
            System.out.println("Manhattan Distance: " + v+"\n");
        });
    }
}
