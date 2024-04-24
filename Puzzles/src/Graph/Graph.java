package Graph;

import java.util.*;

public class Graph {
    private List<Vertex> vertexList;
    private int vertexCount;

    public Graph() {
        this.vertexList = new ArrayList<>();
        this.vertexCount = 0;
    }

    public int createAndAddVertex() {
        vertexCount++;
        vertexList.add(new Vertex(vertexCount));
        return vertexCount;
    }

    public void connectVertices(Integer v1, Integer v2) {
        if (v1-1 < 0 || v1-1 >= vertexList.size() || v2-1 < 0 || v2-1 >= vertexList.size()) {
            throw new IllegalArgumentException("Vertex not found in the graph.");
        }
        vertexList.get(v1 - 1).addNeighbor(vertexList.get(v2 - 1));
        vertexList.get(v2 - 1).addNeighbor(vertexList.get(v1 - 1));
    }

    public Map<Integer, Integer> getVertexDistances() {
        Map<Integer, Integer> distances = new HashMap<>();
        for (Vertex vertex : vertexList) {
            distances.put(vertex.ID, vertex.getDistance());
        }
        return distances;
    }

    public Map<Integer, Integer> getPredecessors() {
        Map<Integer, Integer> predecessors = new HashMap<>();
        for (Vertex vertex : vertexList) {
            predecessors.put(vertex.ID, (vertex.getPredecessor() != null ? vertex.getPredecessor().ID : -1));
        }
        return predecessors;
    }

    public Vertex findVertexById(int id) {
        if (id-1 < 0 || id-1 >= vertexList.size()) {
            throw new IllegalArgumentException("Vertex not found in the graph: "+id);
        }
        return vertexList.get(id - 1);
    }

    public Collection<Vertex> listVertices() {
        return new ArrayList<>(vertexList);
    }

    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public String toString() {
        StringBuilder graphToString = new StringBuilder();
        for (Vertex vertex : vertexList) {
            graphToString.append("(").append(vertex).append(")");
            if (!vertex.getNeighbors().isEmpty()) {
                graphToString.append(" -> { ");
                for (int j = 0; j < vertex.getNeighbors().size(); j++) {
                    graphToString.append(vertex.getNeighbors().get(j));
                    if (j < vertex.getNeighbors().size() - 1) {
                        graphToString.append(",");
                    }
                }
                graphToString.append(" }");
            }
            graphToString.append("\n");
        }
        return graphToString.toString();
    }

    public void resetGraph() {
        for (Vertex vertex : vertexList) {
            vertex.clearNeighbors();
        }
        vertexList.clear();
        vertexCount = 0;
    }
}
