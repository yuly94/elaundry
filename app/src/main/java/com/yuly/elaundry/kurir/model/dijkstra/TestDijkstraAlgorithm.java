package com.yuly.elaundry.kurir.model.dijkstra;

import android.app.Activity;
import android.util.Log;

import com.yuly.elaundry.kurir.model.database.Lokasi;
import com.yuly.elaundry.kurir.model.database.RouteDbHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by yuly nurhidayati on 10/12/16.
 */


public class TestDijkstraAlgorithm {

    private RouteDbHelper db_rute;

    private List<Vertex> nodes;
    private List<Edge> edges;

    private Activity activity;


    public void testExcute() {

        // SqLite database handler
        db_rute = new RouteDbHelper(this.activity);

        List<Lokasi> listJarak = db_rute.getAllJarak();

        for (Lokasi jarak : listJarak) {

           // addLane("Edge_"+jarak.getId(), (int) jarak.getDari(), (int) jarak.getTujuan(), Integer.parseInt(jarak.getJarakAb()));

            Log.d("Edge : ", String.valueOf(jarak.getId()));
        }

    }


    public void testExcutex() {
        nodes = new ArrayList<Vertex>();

        edges = new ArrayList<Edge>();

        List<Lokasi> listJarak = db_rute.getAllJarak();

        for (int i = 0; i <= listJarak.size(); i++) {
            Vertex location = new Vertex("Node_" + i, "Node_" + i);
            nodes.add(location);
        }

        for (Lokasi jarak : listJarak) {

            addLane("Edge_"+jarak.getId(), (int) jarak.getDari(), (int) jarak.getTujuan(), jarak.getJarakAb());

            Log.d("Edge : ", String.valueOf(jarak.getId()));
        }

/*        addLane("Edge_0", 0, 1, 85);
        addLane("Edge_1", 0, 2, 217);
        addLane("Edge_2", 0, 4, 173);
        addLane("Edge_3", 2, 6, 186);
        addLane("Edge_4", 2, 7, 103);
        addLane("Edge_5", 3, 7, 183);
        addLane("Edge_6", 5, 8, 250);
        addLane("Edge_7", 8, 9, 84);
        addLane("Edge_8", 7, 9, 167);
        addLane("Edge_9", 4, 9, 502);
        addLane("Edge_10", 9, 10, 40);
        addLane("Edge_11", 1, 10, 600);*/

        // Lets check from location Loc_1 to Loc_10
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(nodes.get(0));
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(10));

        assertNotNull(path);
        assertTrue(path.size() > 0);

        for (Vertex vertex : path) {
            System.out.println(vertex);
        }

    }

    private void addLane(String laneId, int sourceLocNo, int destLocNo,
                         int duration) {
        Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration );
        edges.add(lane);
    }
}