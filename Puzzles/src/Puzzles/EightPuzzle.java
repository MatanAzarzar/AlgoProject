package Puzzles;

public class EightPuzzle extends Puzzle {
    private static final int SIZE = 3;
    
    public EightPuzzle(int[][] startingBoard) {
        super(SIZE, startingBoard);
    }

    public EightPuzzle(int n) {
        super(SIZE, n);
    }

    public EightPuzzle(FifteenPuzzle other){
        super(other);
    }
}
