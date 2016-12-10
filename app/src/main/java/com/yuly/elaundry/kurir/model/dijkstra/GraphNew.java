package com.yuly.elaundry.kurir.model.dijkstra;

/**
 * Created by anonymous on 09/12/16.
 */

import java.util.List;

public class GraphNew {

    private final List<Edge> edges;

    public GraphNew(List<Edge> edges) {

        this.edges = edges;
    }

    public List<Edge> getEdges() {
        return edges;
    }

}