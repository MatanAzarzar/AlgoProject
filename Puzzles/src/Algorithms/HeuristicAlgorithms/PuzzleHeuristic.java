package Algorithms.HeuristicAlgorithms;

import Puzzles.Puzzle;

public interface PuzzleHeuristic {
    double calculate(Puzzle puzzle);
    void printCache();

    // heuristic functions
    public static ZeroHeuristic Zero_Heuristic = ZeroHeuristic.get();
    public static ManhattanDistanceHeuristic Manhattan_Distance = ManhattanDistanceHeuristic.get();
    public static InversionsHeuristic Permutations_Inversions = InversionsHeuristic.get();
}
