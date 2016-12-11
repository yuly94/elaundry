package com.yuly.elaundry.kurir.model.dijkstra2;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Yuly Nurhidayati <elaundry.pe.hu>
 */
public class Dijkstra_dua {
   private static final String START = "1";
   private static final String END = "5";

   public void eksekusi (){

      Graph.Edge[] GRAPH = {
/*      new Graph.Edge("a", "b", 7),
      new Graph.Edge("a", "c", 9),
      new Graph.Edge("a", "f", 14),
      new Graph.Edge("b", "c", 10),
      new Graph.Edge("b", "d", 15),
      new Graph.Edge("c", "d", 11),
      new Graph.Edge("c", "f", 2),
      new Graph.Edge("d", "e", 6),
      new Graph.Edge("e", "f", 9),*/


   new Graph.Edge(  "1", "2", 6658),
   new Graph.Edge(  "1", "3", 8845),
   new Graph.Edge(  "1", "4", 8716),
   new Graph.Edge(  "1", "5", 9251),
   new Graph.Edge(  "2", "1", 6658),
   new Graph.Edge(  "2", "3", 5777),
   new Graph.Edge(  "2", "4", 5649),
   new Graph.Edge(  "2", "5", 4945),//
   new Graph.Edge(  "3", "2", 4609),
   new Graph.Edge( "3", "1", 7677),
   new Graph.Edge(  "3", "4", 2014),
   new Graph.Edge(  "3", "5", 3344),
   new Graph.Edge(  "4", "3", 2083), //4
   new Graph.Edge(  "4", "2", 5812),
   new Graph.Edge(  "4", "1", 8880),
   new Graph.Edge(  "4", "5", 4291),
   new Graph.Edge(  "5", "4", 4312), //3
   new Graph.Edge(  "5", "3", 3434),
   new Graph.Edge(  "5", "2", 5627),
   new Graph.Edge(  "5", "1", 19905)
   };

      Graph g = new Graph(GRAPH);
      g.dijkstra(START);
      g.printPath(END);
     // g.printAllPaths();

   }
}
 

 