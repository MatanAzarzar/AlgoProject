package Algorithms.HeuristicAlgorithms;

import java.util.*;

import Puzzles.Puzzle;

public class InversionsHeuristic implements PuzzleHeuristic {
    private Map<Puzzle, Integer> heuristicCache = new HashMap<>();
    private static InversionsHeuristic instance = null;

    public InversionsHeuristic() {
        this.heuristicCache = new HashMap<>();
    }

    public static InversionsHeuristic get() {
        if (instance == null)
            instance = new InversionsHeuristic();
        return instance;
    }

    @Override
    public double calculate(Puzzle puzzle) {
        // Check if the heuristic value is already in the cache
        if (heuristicCache.containsKey(puzzle)) {
            return heuristicCache.get(puzzle);
        }

        int size = puzzle.size();
        int[] flattenedArray = new int[size * size];
        int index = 0;

        // Flatten the 2D array into a 1D array
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                flattenedArray[index] = puzzle.getBoard()[i][j];
                index++;
            }
        }


        // Count inversions
        int inversions = 0;
        for (int i = 0; i < size * size - 1; i++) {
            // Check if the last element is 0
            if (flattenedArray[i] == 0) {
                inversions += (size * size - 1 - i);
                // break;
            }
            for (int j = i + 1; j < size * size; j++) {
                if (flattenedArray[j] != 0 && flattenedArray[i] > flattenedArray[j]) {
                    // Count inversions where 0 is not involved
                    inversions++;
                }
            }

        }

        // Cache the computed heuristic value
        heuristicCache.put(puzzle, inversions);
        return inversions;
    }

    @Override
    public void printCache() {
        // Print cached sequences
        heuristicCache.forEach((k, v) -> {
            System.out.println("Puzzle:\n" + k);
            System.out.println("Permutations inversions: " + v + "\n");
        });
    }
}
