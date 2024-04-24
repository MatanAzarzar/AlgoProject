package Graph;

import java.util.*;

public class Vertex {
    public final int ID;
    private List<Vertex> adj; // Neighbors
    private Vertex predecessor;
    private int distance; // previously pathCost
    private double heuristicValue; // previously heuristicCost

    public Vertex(int id) {
        ID = id;
        this.adj = new ArrayList<>();
        this.predecessor = null;
    }
    
    public void addNeighbor(Vertex neighbor) {
        if (!adj.contains(neighbor)) {
            adj.add(neighbor);
        }
    }

    public List<Vertex> getNeighbors() {
        return Collections.unmodifiableList(new ArrayList<>(adj));
    }

    public Vertex getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Vertex predecessor) {
        this.predecessor = predecessor;
    }

    public void setDistance(int cost) {
        this.distance = cost;
    }

    public int getDistance() {
        return distance;
    }

    public void setHeuristicValue(double cost) {
        this.heuristicValue = cost;
    }

    public double getHeuristicValue() {
        return heuristicValue;
    }

    public double getTotalCost() {
        return distance + heuristicValue;
    }

    public int getID() {
        return ID; // Add this method to get the vertex ID.
    }

    @Override
    public String toString() {
        return String.valueOf(ID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vertex other = (Vertex) obj;
        return ID == other.ID;
    }

    public void clearNeighbors() {
        adj.clear();
    }
}
