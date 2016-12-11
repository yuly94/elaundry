package com.yuly.elaundry.kurir.model.dijkstra3;

/**
 * Created by anonymous on 09/12/16.
 */

import java.util.List;

public class Graph {

    private final List<Edge> edges;

    public Graph(List<Edge> edges) {

        this.edges = edges;
    }

    public List<Edge> getEdges() {
        return edges;
    }

}