package Puzzles;

public class FifteenPuzzle extends Puzzle {
    private static final int SIZE = 4;

    public FifteenPuzzle(int[][] startingBoard) {
        super(SIZE, startingBoard);
    }

    public FifteenPuzzle(int n) {
        super(SIZE, n);
    }

    public FifteenPuzzle(FifteenPuzzle other){
        super(other);
    }

}
