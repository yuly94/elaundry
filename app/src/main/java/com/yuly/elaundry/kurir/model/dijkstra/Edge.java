package com.yuly.elaundry.kurir.model.dijkstra;

/**
 * Created by yuly nurhidayati on 09/12/16.
 */

public class Edge {

    private final Vertex source;
    private final Vertex destination;
    private final int weight;

    public Edge(Vertex source, Vertex destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Vertex getDestination() {
        return destination;
    }

    public Vertex getSource() {
        return source;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " -(" + weight + ")- " + destination;
    }

}