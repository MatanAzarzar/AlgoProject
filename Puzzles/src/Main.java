import Puzzles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Algorithms.AlgorithmicPuzzleSolver.AlgorithmicPuzzleSolver;
import Algorithms.HeuristicAlgorithms.InversionsHeuristic;
import Algorithms.HeuristicAlgorithms.ManhattanDistanceHeuristic;
import Algorithms.HeuristicAlgorithms.PuzzleHeuristic;
import Algorithms.HeuristicAlgorithms.ZeroHeuristic;

import java.util.Scanner;

public class Main {

    private static void AverageSolution(List<SolutionOutput> solutions, TimeUnit timeUnit) {
        int avgNumOfVertices = 0, avgMomvements = 0;
        long avgElapsedTime = 0;
        for (SolutionOutput solution : solutions) {
            avgElapsedTime += solution.getElapsedTime();
            avgNumOfVertices += solution.getNumOfVertices();
            avgMomvements += solution.getNumOfMovements();
        }
        int size = solutions.size();
        long totalTime = avgElapsedTime;
        avgElapsedTime /= size;
        avgNumOfVertices /= size;
        avgMomvements /= size;

        StringBuilder sb = new StringBuilder();
        sb.append("Total time: " + SolutionOutput.getElapsedTime(timeUnit, totalTime) + "\n");
        sb.append("Average Number of vertices: " + SolutionOutput.getFormattedNumber(avgNumOfVertices) + "\n");
        sb.append("Average Time: " + SolutionOutput.getElapsedTime(timeUnit, avgElapsedTime) + " "
                + timeUnit.name().toLowerCase()
                + "\n");
        sb.append("Average Number of movements to solution: " + avgMomvements + "\n");
        System.out.println(sb.toString());
    }

    private static void runSolvingExperiment(Class<? extends Puzzle> puzzleType, int radomMoves, int numOfPuzzles,
        boolean printEachSolution, TimeUnit timeUnit) {
        AlgorithmicPuzzleSolver AlgorithmSolution = new AlgorithmicPuzzleSolver();
        List<Puzzle> randomPuzzles = new ArrayList<>();
        try {
            // Create random puzzles
            for (int i = 0; i < numOfPuzzles; i++) {
                Puzzle puzzle = puzzleType.getDeclaredConstructor(int.class).newInstance(radomMoves);
                randomPuzzles.add(puzzle);
            }

            // Solve puzzles using each algorithm
            List<SolutionOutput> solutions = new ArrayList<>();
            System.out.println("------------------------------------- BFS -------------------------------------");
            for (int i = 0; i < numOfPuzzles; i++) {
                // Solve the puzzle using BFS
                solutions.add(AlgorithmSolution.breadthFirstSearch(randomPuzzles.get(i)));
            }
            // Print solutions
            AverageSolution(solutions, timeUnit);
            solutions.clear();

            System.out.println("----------------------------- AStar (Zero func) ------------------------------");
            for (int i = 0; i < numOfPuzzles; i++) {
                // Solve the puzzle using A* with zero heuristic
                solutions.add(AlgorithmSolution.AStarSearch(randomPuzzles.get(i), PuzzleHeuristic.Zero_Heuristic));
            }
            // Print solutions
            AverageSolution(solutions, timeUnit);
            solutions.clear();

            System.out.println("-----------------------------  AStar (Manhattan)  -----------------------------");
            for (int i = 0; i < numOfPuzzles; i++) {
                // Solve the puzzle using A* with Manhattan heuristic
                solutions.add(AlgorithmSolution.AStarSearch(randomPuzzles.get(i), PuzzleHeuristic.Manhattan_Distance));
            }
            // Print solutions
            AverageSolution(solutions, timeUnit);
            solutions.clear();

            System.out.println("-------------------------- AStar (Permutation inversions) --------------------------");
            for (int i = 0; i < numOfPuzzles; i++) {
                // Solve the puzzle using A* with Permutation inversions heuristic
                solutions.add(AlgorithmSolution.AStarSearch(randomPuzzles.get(i), PuzzleHeuristic.Permutations_Inversions));
            }
            // Print solutions
            AverageSolution(solutions, timeUnit);
            solutions.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
    
            System.out.println("Choose an option:");
            System.out.println("1 - Interactively solve a 15-puzzle");
            System.out.println("2 - Run predefined tests");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over
    
            if (choice == 1) {
                interactivelySolveFifteenPuzzle(scanner);
            } else if (choice == 2) {
                runPredefinedTests();
            } else {
                System.out.println("Invalid choice");
            }
    
            scanner.close();
        }
    
        private static void interactivelySolveFifteenPuzzle(Scanner scanner) {
            System.out.println("Enter '1' to manually fill the board, or '2' to generate a random board:");
            int subChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over
    
            FifteenPuzzle puzzle15 = new FifteenPuzzle(16);
    
            if (subChoice == 1) {
                System.out.println("Enter the board layout row by row (4 numbers per row, separated by space):");
                int[][] board = new int[4][4];
                for (int i = 0; i < 4; i++) {
                    System.out.println("Enter row " + (i + 1) + " of the puzzle:");
                    for (int j = 0; j < 4; j++) {
                        board[i][j] = scanner.nextInt();
                    }
                    scanner.nextLine(); // Handle any extra input on this line
                }
                puzzle15 = new FifteenPuzzle(board); // Assumes constructor that takes a board directly
    
            } else if (subChoice == 2) {
                System.out.println("Enter the number of random moves:");
                int randomMoves = scanner.nextInt();
                puzzle15 = new FifteenPuzzle(randomMoves);
            } else {
                System.out.println("Invalid input.");
                return;
            }
    
            scanner.nextLine(); // Consume newline left-over
            System.out.println("Generated Puzzle:");
            System.out.println(puzzle15);
    
            System.out.println("Select the algorithm:");
            System.out.println("1 - BFS");
            System.out.println("2 - A* with Manhattan Distance");
            System.out.println("3 - A* with Inversion Heuristic");
            System.out.println("4 - A* with Zero Heuristic");
            int algoChoice = scanner.nextInt();
    
            AlgorithmicPuzzleSolver algorithmicPuzzleSolver = new AlgorithmicPuzzleSolver();
            SolutionOutput solution = null;
            switch (algoChoice) {
                case 1:
                    solution = algorithmicPuzzleSolver.breadthFirstSearch(puzzle15);
                    break;
                case 2:
                    solution = algorithmicPuzzleSolver.AStarSearch(puzzle15, new ManhattanDistanceHeuristic());
                    break;
                case 3:
                    solution = algorithmicPuzzleSolver.AStarSearch(puzzle15, new InversionsHeuristic());
                    break;
                case 4:
                    solution = algorithmicPuzzleSolver.AStarSearch(puzzle15, new ZeroHeuristic());
                    break;
                default:
                    System.out.println("Invalid algorithm choice");
                    return;
            }
    
            if (solution != null) {
                System.out.println("Solution found:");
                solution.print(TimeUnit.SECONDS); // Assuming Solution class has a method to print details
            } else {
                System.out.println("No solution found.");
            }
        }
    
    private static void runPredefinedTests() {

        TimeUnit timeUnit = TimeUnit.SECONDS;
        int radomMoves = 10;
        int numOfPuzzles = 50;
        int times = 5;

        // Test 1 - solve random 15-puzzle with all algorithm x5
        System.out.println("*******************\tTEST 1\t*******************");
        for (int i = 0; i < times; i++) {
            // Create random puzzle
            Puzzle puzzle15 = new FifteenPuzzle(radomMoves);
            System.out.println(puzzle15);
            // Solve using all algorithms & print solutions
            AlgorithmicPuzzleSolver.AlgorithmSolver(puzzle15, true, timeUnit);
        }

        // Test 2 - solve random 24-puzzle with all algorithm x5
        System.out.println("*******************\tTEST 2\t*******************");
        for (int i = 0; i < times; i++) {
            // Create random puzzle
            Puzzle puzzle15 = new TwentyFourPuzzle(radomMoves);
            System.out.println(puzzle15);
            // Solve using all algorithms & print solutions
            AlgorithmicPuzzleSolver.AlgorithmSolver(puzzle15, true, timeUnit);
        }

        // Test 3 - solve 50 random 15-puzzles with each algorithm
        System.out.println("*******************\tTEST 3\t*******************");
        runSolvingExperiment(FifteenPuzzle.class, radomMoves, numOfPuzzles, false, timeUnit);

        // Test 4 - solve 50 random 24-puzzles with each algorithm
        System.out.println("*******************\tTEST 4\t*******************");
        runSolvingExperiment(TwentyFourPuzzle.class, radomMoves, numOfPuzzles, false, timeUnit);
    }

}
