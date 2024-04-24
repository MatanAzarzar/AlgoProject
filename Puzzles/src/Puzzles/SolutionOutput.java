    package Puzzles;

    import java.text.DecimalFormat;
    import java.util.concurrent.TimeUnit;
    import java.util.*;

    import Graph.Graph;
import Graph.Vertex;

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
        
            // Initialize the path list here to ensure it's always set
            this.path = new ArrayList<>();
        
            // Collect path from solution to start
            List<Integer> temporaryPath = new ArrayList<>();
            int currentId = solutionVertexId;
            while (currentId != -1) {
                temporaryPath.add(currentId);
                Vertex currentVertex = this.graph.findVertexById(currentId);
                currentId = currentVertex.getPredecessor() != null ? currentVertex.getPredecessor().ID : -1;
            }
        
            // Reverse the temporary path to display from start to solution
            Collections.reverse(temporaryPath);
        
            // Build movements list in the correct order and set path
            this.movementsList = new ArrayList<>();
            for (int vertexId : temporaryPath) {
                this.movementsList.add(states.get(vertexId));
                this.path.add(vertexId); // Set the path with the correct order
            }
        }
                                
        public void print(TimeUnit timeUnit) {
            // Print solution information
            StringBuilder sb = new StringBuilder();
            sb.append("Vertices in the Graph: " + getFormattedNumber(graph.getVertexCount()) + "\n");
            sb.append("Time: " + getElapsedTime(timeUnit, elapsedTime) + " " + timeUnit.name().toLowerCase() + "\n");
            
            // Print the path in correct order by showing puzzle states from start to solution
            sb.append("Solution path:\n");
            for (int i = 0; i < movementsList.size(); i++) {
                sb.append("Step " + (i + 1) + ":\n");
                sb.append(movementsList.get(i) + "\n"); // Assuming Puzzle.toString() gives a readable state
            }
            
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
            for (int i = 0; i < movementsList.size(); i++) {
                System.out.println("Step " + (i + 1) + ":");
                System.out.println(movementsList.get(i));
            }
        }
    }
