package Algorithms.HeuristicAlgorithms;

import Puzzles.Puzzle;

public class ZeroHeuristic implements PuzzleHeuristic {
     private static ZeroHeuristic instance = null;

    public ZeroHeuristic(){}
    
    public static ZeroHeuristic get() {
        if (instance == null)
            instance = new ZeroHeuristic();
        return instance;
    }
        @Override
    public double calculate(Puzzle puzzle) {
        // Zero heuristic (always returns 0)
        return 0;
    }
    
    @Override
    public void printCache() {
    }
}
